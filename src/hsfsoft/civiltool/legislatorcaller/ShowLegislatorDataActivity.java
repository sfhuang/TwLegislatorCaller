package hsfsoft.civiltool.legislatorcaller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class ShowLegislatorDataActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_legislator_data_layout);

        final LegislatorDB helper = new LegislatorDB(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        
        String[] party_List, committee_List;
        party_List = getResources().getStringArray(R.array.Party_List_array);
        committee_List = getResources().getStringArray(R.array.Committee_List_array);

        Bundle extraData = getIntent().getExtras();
        if(extraData != null){
        	final String LEGIST_ID=(String) extraData.getCharSequence("LEGIST_ID");

//�d�߰򥻸��
        	Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_type_id","legislator_dist_id","legislator_party_id"}, "legislator_id="+LEGIST_ID,null,null,null,null);
        	c.moveToFirst();
        	
        	TextView textviewToken = (TextView)findViewById(R.id.textviewLegislatorName);
        	textviewToken.setText(c.getString(0));
        	textviewToken = (TextView)findViewById(R.id.textviewPartyName);
        	textviewToken.setText(party_List[Integer.parseInt(c.getString(3))]);

        	String LEGIST_DIST_ID = c.getString(2);

        	c.close();

//�d�߿�ϸ��
        	c = db.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"}, "legislator_dist_id="+LEGIST_DIST_ID,null,null,null,null);
        	c.moveToFirst();

        	textviewToken = (TextView)findViewById(R.id.textviewLegislatorTypeContent);
        	textviewToken.setText(c.getString(0));

        	c.close();

//�d�ߩe���|���
        	c = db.query("COMMITTEE_LEGISLATOR_PAIR", new String[]{"committee_id","convener"}, "legislator_id="+LEGIST_ID,null,null,null,null);
        	c.moveToFirst();
        	int committeeCount;
        	committeeCount = c.getCount();
        	String committeeString="";
        	for (int i=0;i<committeeCount;i++){
        		if(i>0)
        		{
        			committeeString = committeeString + "�B";
        		}
        		if(c.getString(1).equalsIgnoreCase("1"))
        		{
        			committeeString = committeeString + "�i�l�j";
        		}
       		committeeString = committeeString + committee_List[Integer.parseInt(c.getString(0))];
       		c.moveToNext();
        	}

        	textviewToken = (TextView)findViewById(R.id.textviewCommitteeContent);
        	textviewToken.setText(committeeString);
        	c.close();
        	
//�d�߰�|��s���p�����
        	c = db.query("legislator_contact_spot", new String[]{"contact_tel","contact_fax","contact_add"},"legislator_id="+LEGIST_ID+" AND contact_spot_name=\"��|��s��\"",null,null,null,null);
        	c.moveToFirst();
        	
        	//��|�ǯu���
        	textviewToken = (TextView)findViewById(R.id.textviewFaxCongressContent);
        	textviewToken.setText(c.getString(1));
        	//��|�a�}���
        	textviewToken = (TextView)findViewById(R.id.textviewAddCongressContent);
        	textviewToken.setText(c.getString(2));
        	
        	//��|���X�ߧY������
        	final String numberToDial = c.getString(0).replace("-", "");
        	final String numberToShow = c.getString(0);
            Button buttonCDL = (Button)findViewById(R.id.buttonDialNowCongress);
            buttonCDL.setText("�ߧY���G"+c.getString(0));
            buttonCDL.setOnClickListener(new Button.OnClickListener(){
            	public void onClick(View v){
            		SQLiteDatabase db2 = helper.getWritableDatabase();
            		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm",Locale.TAIWAN);
            		String currentDateandTime = sdf.format(new Date());
        			String sqlCALLING_HISTORY = "INSERT INTO CALLING_RECORD (called_legislator_id,calling_datetime,called_number) VALUES(\""+LEGIST_ID+"\",\""+currentDateandTime+"\",\""+numberToShow+"\")";
        			db2.execSQL(sqlCALLING_HISTORY);
            		db2.close();

        			Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" +numberToDial));
                    startActivity(intent);
            	}
            });
            c.close();

            //�d�ߦU�a�A�ȳB�p�����
            c = db.query("legislator_contact_spot", new String[]{"contact_spot_name","contact_tel", "contact_fax", "contact_add"}, "legislator_id="+LEGIST_ID+" AND contact_spot_name<>\"��|��s��\"", null, null, null,null);
            c.moveToFirst();
            int otherOfficeCount=0;
            otherOfficeCount = c.getCount();
            TableLayout tableToken = (TableLayout)findViewById(R.id.tablelayoutServiceOffice);
            //�Y����L�A�ȳB
            if(otherOfficeCount>0){
            	for(int i=0;i<otherOfficeCount;i++){
                    TableRow tableRowToken = new TableRow(this);

                    //�A�ȳB�W��
                    TextView newTextView = new TextView (this);
	                newTextView.setTextSize(18);
	                newTextView.setText(c.getString(0));
	                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	                newTextView.setGravity(Gravity.CENTER);
	                newTextView.setLayoutParams(lp);
	                newTextView.setBackgroundColor(Color.rgb(200, 200, 200));
	                tableToken.addView(newTextView);

                    //�A�ȳB�q��
	                newTextView = new TextView (this);
		                newTextView.setTextSize(18);
		                newTextView.setText("�q�ܡG");
		                tableRowToken.addView(newTextView);

		                //�A�ȳB�q�ܥߧY������
		                final String numberServiceToDial,numberServiceToShow;
		                numberServiceToDial = c.getString(1).replace("-", "");
		                numberServiceToShow = c.getString(1);
		                buttonCDL = new Button (this);
		                buttonCDL.setText("�ߧY���G"+c.getString(1));
		                buttonCDL.setTextSize(18);
		                buttonCDL.setOnClickListener(new Button.OnClickListener(){
		                	public void onClick(View v){

		                		SQLiteDatabase db2 = helper.getWritableDatabase();
		                		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm",Locale.TAIWAN);
		                		String currentDateandTime = sdf.format(new Date());
		            			String sqlCALLING_HISTORY = "INSERT INTO CALLING_RECORD (called_legislator_id,calling_datetime,called_number) VALUES(\""+LEGIST_ID+"\",\""+currentDateandTime+"\",\""+numberServiceToShow+"\")";
		            			db2.execSQL(sqlCALLING_HISTORY);
		                		db2.close();
		                		
		                        Intent intent = new Intent();
		                        intent.setAction(Intent.ACTION_CALL);
		                        intent.setData(Uri.parse("tel:" +numberServiceToDial));
		                        startActivity(intent);
		                	}
		                });
		                tableRowToken.addView(buttonCDL);
	                tableToken.addView(tableRowToken);
	                
	                //�A�ȳB�ǯu
	                tableRowToken = new TableRow(this);
		                newTextView = new TextView(this);
		                newTextView.setTextSize(18);
		                newTextView.setText("�ǯu�G");
		                tableRowToken.addView(newTextView);
		                newTextView = new TextView(this);
		                newTextView.setTextSize(18);
		                newTextView.setText(c.getString(2));
		                tableRowToken.addView(newTextView);
	                tableToken.addView(tableRowToken);
	                
	                //�A�ȳB�a�}
	                tableRowToken = new TableRow(this);
		                newTextView = new TextView(this);
		                newTextView.setTextSize(18);
		                newTextView.setText("�a�}�G");
		                tableRowToken.addView(newTextView);
		                newTextView = new TextView(this);
		                newTextView.setTextSize(14);
		                newTextView.setText(c.getString(3));
		                tableRowToken.addView(newTextView);
	                tableToken.addView(tableRowToken);
	                c.moveToNext();
            	}
            	c.close();
                db.close();
            }
            //�Y�L��L�A�ȳB
            else
            {
                TextView newTextView = new TextView (this);
	                newTextView.setTextSize(18);
	                newTextView.setText("�L");
	                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	                newTextView.setGravity(Gravity.CENTER);
	                newTextView.setLayoutParams(lp);
	                tableToken.addView(newTextView);
	            	c.close();
	                db.close();
           }
        }
    }

    //������ Activity 	
    public void exitThisActivity(View v) {
    	super.finish();
	}

    //�\���涵�س]�w    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 1, 0,"�ϥλ���");
    	menu.add(0, 2, 0,"�`�N�ƶ�");
        return true;
    }
    
    //�\����U���ؤ��e�]�w
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    		case 1:
    			String dialogText = 
    			"�i���q�ܸ��X�����s�j\n�i���Ӹ��X�C";

    			new AlertDialog.Builder(this)
    	    	.setTitle("�ϥλ���")
    	    	.setMessage(dialogText)
    	    	.setPositiveButton("�T�w",null)
    	    	.show();    			
    			return true;

    		case 2:
    			dialogText = 
    			"1.�ߪk�e���p����T�Y���ۥߪk�|�����A�@�̤��O�Ҩ䥿�T�ʡC\n"+
    	    	"2.���{��������O�W�ϥΡA�q�ܥ�����O�W�Ҥ��~�ॿ�T�����C\n"+
    	    	"3.�����q�ܮɷ|�ϥΨ��ʳq�H�A�ȡA�ϥΪ̥����ۦ�t��q�ܶO�C\n"+
    	    	"4.�@�̹�ϥΪ̨ϥΥ��{���ҥͤ@����G�]�]�A����p§���^���t�d���C\n"+
    	    	"5.���{���ϥΪ̥H�O�W�~�������C\n"+
    	    	"6.�ϥΪ̶ȱo���ӤH�D��Q�γ~�ϥΥ��{���C\n"+
    	    	"7.�D�g�@�̮ѭ��\�i�A���o��@���{���ά��f�V�u�{�C\n"+
    	    	"8.�u���ؤH���@�M��v����D�g�@�̮ѭ��\�i���o�ϥΥ��{���A�H�����V�@�̤�I���� 1 �d�����l�`���v�C\n";

    			new AlertDialog.Builder(this)
    	    	.setTitle("�`�N�ƶ�")
    	    	.setMessage(dialogText)
    	    	.setPositiveButton("�T�w",null)
    	    	.show();    			
    			return true;
    	}
		return false;
    }
    
}