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
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowCallHistoryActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_history_layout);

        TableLayout tableToken = (TableLayout)findViewById(R.id.callingHistoryTable);
        
        final LegislatorDB helper = new LegislatorDB(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        //�d���p������
       	Cursor c = db.query("CALLING_RECORD", new String[]{"called_legislator_id","calling_datetime","called_number"},"calling_record_id>0",null,null,null,"calling_record_id desc");
       	c.moveToFirst();
       	int recordCount = c.getCount();
       	//�Y�S���p������
       	if(recordCount==0)
       	{
       		TextView newTextView = new TextView (this);
	        newTextView.setTextSize(18);
	        newTextView.setText("�L����");
	        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	        newTextView.setGravity(Gravity.CENTER);
	        newTextView.setLayoutParams(lp);
	        newTextView.setBackgroundColor(Color.rgb(230, 230, 230));
	        tableToken.addView(newTextView);
       	}
       	//�Y���p������
       	else
       	{
       		String[] legislator_List = getResources().getStringArray(R.array.Legislator_list_array);

   			TextView newTextView;
       		TableRow newTableRow;
       		Button newButton;
       		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
   			newTableRow=new TableRow(this);
   			newTableRow.setLayoutParams(lp);
   			
   			//��ܡu�R�������v������C
   			newTextView=new TextView(this);
   			newTableRow.addView(newTextView);
   			newTextView=new TextView(this);
   			newTableRow.addView(newTextView);
        	newButton = new Button(this);
        	newButton.setText("�R������");
        	newButton.setOnClickListener(new Button.OnClickListener(){
            	public void onClick(View v){
            		SQLiteDatabase db2 = helper.getWritableDatabase();
        			String sqlDELETE_HISTORY = "DELETE FROM CALLING_RECORD WHERE called_legislator_id>0";
        			db2.execSQL(sqlDELETE_HISTORY);
            		db2.close();
            		finish();
            		startActivity(getIntent());
            	}
            });
   			newTableRow.addView(newButton);
   			tableToken.addView(newTableRow);

   			//��ܼ��D�C
   			newTableRow=new TableRow(this);
   			newTextView=new TextView(this);
   			newTextView.setTextSize(18);
   			newTextView.setText("�@�ߩe�@");
            newTextView.setGravity(Gravity.CENTER);
            newTextView.setBackgroundColor(Color.rgb(255, 200, 200));
   			newTableRow.addView(newTextView);
   			
   			newTextView=new TextView(this);
   			newTextView.setTextSize(16);
   			newTextView.setText("�p���ɶ�");
            newTextView.setGravity(Gravity.CENTER);
            newTextView.setBackgroundColor(Color.rgb(200, 255, 200));
   			newTableRow.addView(newTextView);

   			newTextView=new TextView(this);
   			newTextView.setTextSize(16);
   			newTextView.setText("�������X");
            newTextView.setGravity(Gravity.CENTER);
            newTextView.setBackgroundColor(Color.rgb(200, 200, 255));
   			newTableRow.addView(newTextView);

   			tableToken.addView(newTableRow);

   			//����p������
       		for(int i=0;i<recordCount;i++)
       		{
       			//�p����H
       			newTableRow=new TableRow(this);
       			newTextView=new TextView(this);
       			newTextView.setTextSize(18);
       			newTextView.setText(legislator_List[Integer.parseInt(c.getString(0))]);
                newTextView.setGravity(Gravity.CENTER);
       			newTableRow.addView(newTextView);
       			
       			//�p���ɶ�
       			newTextView=new TextView(this);
       			newTextView.setText(c.getString(1));
                newTextView.setGravity(Gravity.CENTER);
       			newTableRow.addView(newTextView);

       			//��������
       			final String LEGIST_ID = c.getString(0);
            	final String numberToDial = c.getString(2).replace("-", "");
            	final String numberToShow = c.getString(2);
            	newButton = new Button(this);
            	newButton.setText(c.getString(2));
            	newButton.setOnClickListener(new Button.OnClickListener(){
                	public void onClick(View v){
                		//�����ɧ�s�p������
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
            	newTableRow.addView(newButton);
       			tableToken.addView(newTableRow);
       			c.moveToNext();
       		}
       	}
       	c.close();
        db.close();
    }

    //�q�ܵ����ɭ��s��z�� Activity �H��s���
    @Override
    protected void onResume() {

       super.onResume();
       this.onCreate(null);
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
    			"�i�R�������j\n�R���Ҧ��p�������C\n"+
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