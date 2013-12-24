package hsfsoft.civiltool.legislatorcaller;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SettingDistLegislatorExecuteActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_done_layout);

        LegislatorDB helper = new LegislatorDB(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        
        Bundle extraData = getIntent().getExtras();
        if(extraData != null){
	        String legislatorList = "";
	        String DIST_NAME="";
	        //���o�]�w����
        	String SETTING_TYPE = (String) extraData.getCharSequence("SETTING_TYPE");
        	//�Y�̿�ϳ]�w
        	if(SETTING_TYPE.equalsIgnoreCase("BY_DIST")){
            	String KEY_ID=(String) extraData.getCharSequence("LEGIST_DIST_ID");
            	DIST_NAME=(String) extraData.getCharSequence("DIST_NAME");
            	
            	//�d�߸ӿ�ϥߩe�W��
            	Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id="+KEY_ID,null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        String legislatorId = "";
		        if(legislatorCount>0){
		        	for(int i=0;i<legislatorCount;i++){
		        		if(i>0)
		        		{
		        			legislatorList = legislatorList+"�B";
		        		}
		        		legislatorList = legislatorList + c.getString(0);
		        		legislatorId = c.getString(1);
		        		c.moveToNext();
		        	}
		        }
		        c.close();
		        db.close();
		        
		        //�]�w���
				db = helper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("legislator_dist_id",KEY_ID);
				//�Y�ӿ�ϥߩe�u���@�H
				if(legislatorCount==1)
				{
					values.put("current_district_legislator_name",legislatorList);
					values.put("current_district_legislator_id",legislatorId);
				}
				else
				{
					values.put("current_district_legislator_name","");
					values.put("current_district_legislator_id","");
				}
				values.put("province_in_record", "");
				values.put("city_in_record", "");
				values.put("dist_in_record", "");
				values.put("village_in_record", "");				
				//��s��ϳ]�w
				db.update("PERSONAL_SETTING",values,"userid='1'",null);
				db.close();

				//�ܧ�e�����
				TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        if(Integer.parseInt(KEY_ID)>2100){
			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j���");
		        }
		        else
		        {
			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j");
		        }
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
        	}
        	//�Y�̥ߩe�]�w
        	else
        	{
            	String KEY_ID=(String) extraData.getCharSequence("LEGIST_DIST_ID");
            	String LEGISLATOR_NAME=(String) extraData.getCharSequence("LEGIST_NAME");
            	String LEGISLATOR_ID=(String) extraData.getCharSequence("LEGIST_ID");
            	
            	//�d�߸ӥߩe���ݿ��
            	Cursor c = db.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+KEY_ID,null,null,null,null);
		        c.moveToFirst();
		        DIST_NAME = c.getString(0);
		        c.close();

            	//�d�߸ӿ�ϥߩe�W��
            	c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id="+KEY_ID,null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        if(legislatorCount>0){
		        	for(int i=0;i<legislatorCount;i++){
		        		if(i>0)
		        		{
		        			legislatorList = legislatorList+"�B";
		        		}
		        		legislatorList = legislatorList + c.getString(0);
		        		c.moveToNext();
		        	}
		        }
		        c.close();
		        db.close();
		        
				db = helper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("legislator_dist_id",KEY_ID);
				//�Y�ӿ�ϥߩe�u���@�H
				if(legislatorCount==1)
				{
					values.put("current_district_legislator_name",LEGISLATOR_NAME);
					values.put("current_district_legislator_id",LEGISLATOR_ID);
				}
				else
				{
					values.put("current_district_legislator_name","");
					values.put("current_district_legislator_id","");
				}
				values.put("province_in_record", "");
				values.put("city_in_record", "");
				values.put("dist_in_record", "");
				values.put("village_in_record", "");				
				//��s��ϳ]�w
				db.update("PERSONAL_SETTING",values,"userid='1'",null);
				db.close();

				//�ܧ�e�����
				TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        if(Integer.parseInt(KEY_ID)>2100){
			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j���");
		        }
		        else
		        {
			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j");
		        }
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
        	}
        }
        else
        {
        	db.close();
        }
        
    }

    //������ Activity 
	public void exitThisActivity(View v) {
    	super.finish();
	}
	
    //�\���涵�س]�w
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
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