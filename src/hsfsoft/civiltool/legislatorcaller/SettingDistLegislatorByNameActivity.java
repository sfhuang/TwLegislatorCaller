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

public class SettingDistLegislatorByNameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_dist_legislator_by_name_layout);

        LegislatorDB helper = new LegislatorDB(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

        //�d�ߥߩe�M��
        Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_id","legislator_name","legislator_dist_id"},"legislator_dist_id<>9999",null,null,null,null);
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
  
        	//�إߥߩe�m�W����M��
        	TableLayout layout = (TableLayout)findViewById(R.id.tableLegislatorList);
            for (int rowS=0; rowS<rowCount; rowS++) { 
                  TableRow tr = new TableRow(this); 
                       for (int colS=0; colS<colCount; colS++) {
                    	   if(showedCount<dataCount)
                    	   {
	       	                   Button b = new Button (this);
	       	                   TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	       	                   params.weight = 1.0f;
	       	                   b.setLayoutParams(params);
	       	                   b.setTextSize(18);
	       	                   b.setText(c.getString(1));
	       	                   final String theLegislatorId = c.getString(0);
	       	                   final String theLegislatorName = c.getString(1);
	       	                   final String theLegislatorDistId = c.getString(2);
	       	                   //������U�����]�w��ϥ\��
	       	                   b.setOnClickListener(new Button.OnClickListener(){
	       	                	   public void onClick(View v){
	       	                		   Intent intent=new Intent(SettingDistLegislatorByNameActivity.this,SettingDistLegislatorExecuteActivity.class);
	       	                		   //�x�s�ߩeID
	       	                		   intent.putExtra("LEGIST_ID",theLegislatorId);
	       	                		   //�N�]�w�����]�w���̥ߩe�m�W
	       	                		   intent.putExtra("SETTING_TYPE","BY_NAME");
	       	                		   //�x�s�ߩe�m�W
	       	                		   intent.putExtra("LEGIST_NAME",theLegislatorName);
	       	                		   //�x�s���ID
	       	                		   intent.putExtra("LEGIST_DIST_ID",theLegislatorDistId);
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
        	c.close();
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
    			"�i���ߩe�m�W�����s�j\n�i�N�ӥߩe�]����ϥߩe�C";

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