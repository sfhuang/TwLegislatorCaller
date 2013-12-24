package hsfsoft.civiltool.legislatorcaller;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowCommitteeLegislatorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_legislator_by_committee_layout);

        LegislatorDB helper = new LegislatorDB(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

        String[] committee_List;
        committee_List = getResources().getStringArray(R.array.Committee_List_array);
        String[] legislator_List;
        legislator_List = getResources().getStringArray(R.array.Legislator_list_array);
        Bundle extraData = getIntent().getExtras();
        if(extraData != null){
        	TextView textViewToken = (TextView)findViewById(R.id.textviewCommitteeName);
        	String COMMITTEE_ID=(String) extraData.getCharSequence("COMMITTEE_ID");
        	
        	//�d�ߩe���|�e���W��
        	Cursor c = db.query("COMMITTEE_LEGISLATOR_PAIR",new String[]{"legislator_id","convener"}, "committee_id="+COMMITTEE_ID,null,null,null,null);
        	c.moveToFirst();

        	int colCount = 3;
        	int dataCount = c.getCount();
        	int showedCount = 0;
        	int reminder = dataCount%colCount;
        	int rowCount = 0;

        	if(reminder==0)
        		rowCount=dataCount/colCount;
        	else
        		rowCount=((dataCount-reminder)/colCount)+1;
  
        	//�إߩe������M��
        	TableLayout layout = (TableLayout)findViewById(R.id.tableLegislatorList);
            for (int rowS=0; rowS<rowCount; rowS++) { 
                  TableRow tr = new TableRow(this);
                  String convenerText = "";
                       for (int colS=0; colS<colCount; colS++) {
                    	   if(showedCount<dataCount)
                    	   {
	       	                   Button b = new Button (this);
	       	                   TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	       	                   params.weight = 1.0f;
	       	                   b.setLayoutParams(params);
	       	                   b.setTextSize(18);
	       	                   if(c.getString(1).equalsIgnoreCase("1")){
	       	                	   convenerText="��";
	       	                   }
	       	                   else{
	       	                	   convenerText="";
	       	                   }   
	       	                   b.setText(convenerText+legislator_List[Integer.parseInt(c.getString(0))]);
	       	                   final String theLegislatorId = c.getString(0);
	       	                   //���U�����i�J�Ӹөe����ƭ���
	       	                   b.setOnClickListener(new Button.OnClickListener(){
	       	                	   public void onClick(View v){
	       	                		   Intent intent=new Intent(ShowCommitteeLegislatorActivity.this,ShowLegislatorDataActivity.class);
	       	                		   intent.putExtra("LEGIST_ID",theLegislatorId);
	       	                		   startActivity(intent);
	       	                	   }
	       	                   });
	       	                   tr.addView(b);
	       	                   c.moveToNext();
	       	                   showedCount++;
                    	   }
                       }
            	  layout.addView(tr); 
            }
        	textViewToken.setText(committee_List[Integer.parseInt(COMMITTEE_ID)]);
        	c.close();
        }
        db.close();
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
    			"�i���ߩe�m�W�����s�j\n�i�d�ݸӥߩe�p����T�C\n�m�W�e���[ �� ���O�l���e���C";

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