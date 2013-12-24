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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class SettingDistLegislatorByAddressActivity extends Activity {
	public String Province_Global_Id,City_Global_Id,District_Global_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_dist_legislator_by_add_layout);

    	final LegislatorDB helper = new LegislatorDB(this);
    	
    	//�]�w�u�ꤺ�L���y�v����\��
        Button buttonToken = (Button)findViewById(R.id.buttonLiveAbroad);
        buttonToken.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				SQLiteDatabase db = helper.getReadableDatabase();
				//�d�߹��~��~�����ϥߩe�W��
		        Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id=2400",null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        String legislatorList = "";
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

				//�N��ϳ]�w�����~��~���
				db = helper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("legislator_dist_id","2400");
				//�Y��ϥu���@�W�ߩe
				if(legislatorCount==1)
				{
					values.put("current_district_legislator_name",legislatorList);
					values.put("current_district_legislator_id",legislatorId);
					values.put("province_in_record", "");
					values.put("city_in_record", "");
					values.put("dist_in_record", "");
					values.put("village_in_record", "");
				}
				//�Y��Ϧ��ƦW�ߩe
				else
				{
					values.put("current_district_legislator_name","");
					values.put("current_district_legislator_id","");
					values.put("province_in_record", "");
					values.put("city_in_record", "");
					values.put("dist_in_record", "");
					values.put("village_in_record", "");
				}
				db.update("PERSONAL_SETTING",values,"userid='1'",null);
				setContentView(R.layout.setting_done_layout);
		        db.close();
		        //�]�w��ܵe��
		        TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        textviewToken.setText("�z����ϬO�i���~��~����j���");
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
			}
        });
    }

    public void selectIfNative(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_ifnative_layout);

    	final LegislatorDB helper = new LegislatorDB(this);
    	
    	//�]�w�u���a�����v����\��
    	Button buttonToken = (Button)findViewById(R.id.buttonIsPlainNative);
        buttonToken.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
		        SQLiteDatabase db = helper.getWritableDatabase();

		        //�N��ϳ]�w�����a����
		        ContentValues values=new ContentValues();
				values.put("legislator_dist_id","2200");
				values.put("current_district_legislator_name","");
				values.put("current_district_legislator_id","");
				values.put("province_in_record", "");
				values.put("city_in_record", "");
				values.put("dist_in_record", "");
				values.put("village_in_record", "");

				db.update("PERSONAL_SETTING",values,"userid='1'",null);
				setContentView(R.layout.setting_done_layout);
		        db.close();
		        //�d�ߥ��a������ϥߩe�W��
		        db = helper.getReadableDatabase();
		        Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name"},"legislator_dist_id=2200",null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        String legislatorList = "";
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
		        //�]�w��ܵe��
		        TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        textviewToken.setText("�z����ϬO�i���a�����j���");
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
			}
        });

        buttonToken = (Button)findViewById(R.id.buttonIsMountNative);
        buttonToken.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
		        SQLiteDatabase db = helper.getWritableDatabase();

		        //�N��ϳ]�w���s�a����
		        ContentValues values=new ContentValues();
				values.put("legislator_dist_id","2300");
				values.put("current_district_legislator_name","");
				values.put("current_district_legislator_id","");
				values.put("province_in_record", "");
				values.put("city_in_record", "");
				values.put("dist_in_record", "");
				values.put("village_in_record", "");

				db.update("PERSONAL_SETTING",values,"userid='1'",null);
				setContentView(R.layout.setting_done_layout);
		        db.close();
		        //�d�ߤs�a������ϥߩe�W��
		        db = helper.getReadableDatabase();
		        Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name"},"legislator_dist_id=2300",null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        String legislatorList = "";
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
		        //�]�w��ܵe��
		        TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        textviewToken.setText("�z����ϬO�i�s�a�����j���");
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
			}
        });
    }
    
    //�e���̤��y�a�]�w��ϵe��
    public void backPreviousPage(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_layout);
	}

    //�e���O�_��������ܵe��
	public void backIsNativePage(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_ifnative_layout);
	}
	
	//�e�������C��
	public void goCityList(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_citylevel_layout);
		
		LegislatorDB helper = new LegislatorDB(this);
        SQLiteDatabase db = helper.getReadableDatabase();
		
        //�d�߿����M��
        Cursor c = db.query("CITY_LIST",new String[]{"city_id","city_name","legislator_dist_id","province_id"},"province_id<2",null,null,null,null);
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

    	//��ܿ�������M��
    	TableLayout layout = (TableLayout)findViewById(R.id.tableCityList);
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
   	                   
   	                   final String CITY_ID =c.getString(0);
   	                   final String PROVINCE_ID =c.getString(3);
   	                   final String LEGIS_DIS_ID = c.getString(2);
   	                   final LegislatorDB helper2 = new LegislatorDB(this);

   	                   b.setOnClickListener(new Button.OnClickListener()
   	                   {
   	                	   public void onClick(View v)
   	                	   {
   	                		   //�Y�ӿ����������O�@�ӿ�ϡA�h�e���m���ϼh�ŦW��
   	                		   if(LEGIS_DIS_ID.length()<1)
   	                		   {
   	                			   Province_Global_Id=PROVINCE_ID;
   	        	                   City_Global_Id=CITY_ID;
   	                			   goDistList(v);
   	                		   }
   	                		   //�Y�ӿ��������O�@�ӿ�ϡA�h�N�ӿ����]�w���ϥΪ̿��
   	                		   else if(Integer.parseInt(LEGIS_DIS_ID)>0)
   	                		   {
   	                			   setContentView(R.layout.setting_done_layout);

   	                			   SQLiteDatabase db2 = helper2.getReadableDatabase();
   	                		       String legislatorList = "";
   	                		       String DIST_NAME="";

   	                		       //�d�߸ӿ�ϦW��
   	                		       Cursor c = db2.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		       c.moveToFirst();
   	                		       DIST_NAME = c.getString(0);
   	                		       c.close();
   	                		       
   	                		       //�d�߸ӿ�ϥߩe�W��
   	                		       c = db2.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		       c.moveToFirst();
   	                			   int legislatorCount = c.getCount();
   	                			   String legislatorId = "";
   	                			   if(legislatorCount>0)
   	                			   {
   	                				   for(int i=0;i<legislatorCount;i++)
   	                				   {
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
   	                			   db2.close();
   	                			        
   	                			   //�]�w�ӿ�Ϭ��ϥΪ̿��
   	                			   db2 = helper2.getWritableDatabase();
   	                			   ContentValues values=new ContentValues();
   	                			   values.put("legislator_dist_id",LEGIS_DIS_ID);
   	                			   //�Y�ӿ�ϥߩe�u���@�H
   	                			   if(legislatorCount==1)
   	                			   {
   	                				   values.put("current_district_legislator_name",legislatorList);
   	                				   values.put("current_district_legislator_id",legislatorId);
   	                				   values.put("province_in_record", Province_Global_Id);
   	                				   values.put("city_in_record", City_Global_Id);
   	                				   values.put("dist_in_record", "");
   	                				   values.put("village_in_record", "");
   	                			   }
   	                			   //�Y�ӿ�ϥߩe���ƤH
   	                			   else
   	                			   {
   	                				   values.put("current_district_legislator_name","");
   	                				   values.put("current_district_legislator_id","");
   	                				   values.put("province_in_record", Province_Global_Id);
   	                				   values.put("city_in_record", City_Global_Id);
   	                				   values.put("dist_in_record", "");
   	                				   values.put("village_in_record", "");
   	                			   }
   	                			   db2.update("PERSONAL_SETTING",values,"userid='1'",null);
   	                			   db2.close();

   	                			   //��s�e�����
   	                			   TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
   	                			   if(Integer.parseInt(LEGIS_DIS_ID)>2100)
   	                			   {
   	                			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j���");
   	                			   }
   	                			   else
   	                			   {
   	                			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j");
   	                			   }
   	                			   textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
   	                			   textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
   	                	       }
   	                	       else
   	                	       {
   	                			   goDistList(v);
   	                	       }
   	                	   	}
   	                   }
   	                   );
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

	public void backCityList(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_citylevel_layout);
	}

	//�e���m���ϼh�ŲM��
	public void goDistList(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_distlevel_layout);
		LegislatorDB helper = new LegislatorDB(this);
        SQLiteDatabase db = helper.getReadableDatabase();
		
        //�d�߬����������m����
        Cursor c = db.query("DIST_LIST",new String[]{"dist_id","dist_name","legislator_dist_id"},"city_id="+City_Global_Id,null,null,null,null);
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

    	//��ܶm���ϫ���M��
    	TableLayout layout = (TableLayout)findViewById(R.id.tableDistList);
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
   	                   
   	                   final String DIST_ID =c.getString(0);

   	                   final String LEGIS_DIS_ID = c.getString(2);

   	                   final LegislatorDB helper2 = new LegislatorDB(this);

   	                   b.setOnClickListener(new Button.OnClickListener()
   	                   {
   	                	   public void onClick(View v)
   	                	   {
   	                		   //�Y�Ӷm���ϨëD���Ҭ��ݩ�@�ӿ�ϡA�e�������M��
   	                		   if(LEGIS_DIS_ID.length()<1)
   	                		   {
   	                			   District_Global_Id=DIST_ID;
   	                			   goVillageList(v);
   	                		   }
   	                		   //�Y�Ӷm���ϥ��Ҭ��ݩ�@�ӿ��
   	                		   else if(Integer.parseInt(LEGIS_DIS_ID)>0)
   	                		   {
   	                			   setContentView(R.layout.setting_done_layout);

   	                			   SQLiteDatabase db2 = helper2.getReadableDatabase();
   	                		       String legislatorList = "";
   	                		       String DIST_NAME="";

   	                		       //�d�߸ӿ�ϦW��
   	                		       Cursor c = db2.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		       c.moveToFirst();
   	                		       DIST_NAME = c.getString(0);
   	                		       c.close();
   	                		       
   	                		       //�d�߸ӿ�ϥߩe�W��
   	                		       c = db2.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		       c.moveToFirst();
   	                			   int legislatorCount = c.getCount();
   	                			   String legislatorId = "";
   	                			   if(legislatorCount>0)
   	                			   {
   	                				   for(int i=0;i<legislatorCount;i++)
   	                				   {
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
   	                			   db2.close();
   	                			        
   	                			   //�]�w���ݿ��
   	                			   db2 = helper2.getWritableDatabase();
   	                			   ContentValues values=new ContentValues();
   	                			   values.put("legislator_dist_id",LEGIS_DIS_ID);
   	                			   //�Y�ӿ�ϥߩe�u���@�H
   	                			   if(legislatorCount==1)
   	                			   {
   	                				   values.put("current_district_legislator_name",legislatorList);
   	                				   values.put("current_district_legislator_id",legislatorId);
   	                				   values.put("province_in_record", Province_Global_Id);
   	                				   values.put("city_in_record", City_Global_Id);
   	                				   values.put("dist_in_record", DIST_ID);
   	                				   values.put("village_in_record", "");
   	                			   }
   	                			   //�Y�ӿ�ϥߩe���ƤH
   	                			   else
   	                			   {
   	                				   values.put("current_district_legislator_name","");
   	                				   values.put("current_district_legislator_id","");
   	                				   values.put("province_in_record", Province_Global_Id);
   	                				   values.put("city_in_record", City_Global_Id);
   	                				   values.put("dist_in_record", DIST_ID);
   	                				   values.put("village_in_record", "");
   	                			   }
   	                			   //��s�ϥΪ̿�ϸ��
   	                			   db2.update("PERSONAL_SETTING",values,"userid='1'",null);
   	                			   db2.close();

   	                			   //�ܧ�e�����
   	                			   TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
   	                			   if(Integer.parseInt(LEGIS_DIS_ID)>2100)
   	                			   {
   	                			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j���");
   	                			   }
   	                			   else
   	                			   {
   	                			        textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j");
   	                			   }
   	                			   textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
   	                			   textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
   	                	        }
   	                	        else
   	                	        {
   	                			   goVillageList(v);
   	                	        }
   	                	   	}
   	                   }
   	                   );
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

	//�e�������M��
	public void goVillageList(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_villagelevel_layout);

		LegislatorDB helper = new LegislatorDB(this);
        SQLiteDatabase db = helper.getReadableDatabase();
		
        //�d�߶m���ϤU�������M��
        Cursor c = db.query("VILLAGE_LIST",new String[]{"village_id","village_name","legislator_dist_id"},"dist_id="+District_Global_Id,null,null,null,null);
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

    	//�إߧ�������M��
    	TableLayout layout = (TableLayout)findViewById(R.id.tableVillageList);
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
   	                   
   	                   final String VILLAGE_ID =c.getString(0);

   	                   final String LEGIS_DIS_ID = c.getString(2);

   	                   final LegislatorDB helper2 = new LegislatorDB(this);

   	                   b.setOnClickListener(new Button.OnClickListener()
   	                   {
   	                	   public void onClick(View v)
   	                	   {
                			   setContentView(R.layout.setting_done_layout);

                			   SQLiteDatabase db2 = helper2.getReadableDatabase();
   	                		   String legislatorList = "";
   	                		   String DIST_NAME="";

   	                		   //�d�߸ӧ��̩��ݿ�ϦW��
   	                		   Cursor c = db2.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		   c.moveToFirst();
   	                		   DIST_NAME = c.getString(0);
   	                		   c.close();
   	                		       
   	                		   //�d�߸ӿ�ϥߩe�W��
   	                		   c = db2.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		   c.moveToFirst();
   	                		   int legislatorCount = c.getCount();
   	                		   String legislatorId = "";
   	                		   if(legislatorCount>0)
   	                		   {
   	                			   for(int i=0;i<legislatorCount;i++)
   	                			   {
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
   	                		   db2.close();
   	                			        
   	                		   //�]�w�ϥΪ̿��
   	                		   db2 = helper2.getWritableDatabase();
   	                		   ContentValues values=new ContentValues();
   	                		   values.put("legislator_dist_id",LEGIS_DIS_ID);
   	                		   //�Y�ӿ�ϥߩe�u���@�H
   	               			   if(legislatorCount==1)
   	               			   {
   	               				   values.put("current_district_legislator_name",legislatorList);
   	               				   values.put("current_district_legislator_id",legislatorId);
                				   values.put("province_in_record", Province_Global_Id);
                				   values.put("city_in_record", City_Global_Id);
                				   values.put("dist_in_record", District_Global_Id);
                				   values.put("village_in_record", VILLAGE_ID);
   	               			   }
   	                		   //�Y�ӿ�ϥߩe�W�L�@�H
	                		   else
	                		   {
	                			   values.put("current_district_legislator_name","");
	                			   values.put("current_district_legislator_id","");
	                			   values.put("province_in_record", Province_Global_Id);
	                			   values.put("city_in_record", City_Global_Id);
	                			   values.put("dist_in_record", District_Global_Id);
	                			   values.put("village_in_record", VILLAGE_ID);
	                		   }
   	               			   //��s��ϳ]�w
   	               			   db2.update("PERSONAL_SETTING",values,"userid='1'",null);
   	               			   db2.close();

   	               			   //�ܧ�e�����
   	               			   TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
   	               			   textviewToken.setText("�z����ϬO�i"+DIST_NAME+"�j");
   	               			   textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
                   			   textviewToken.setText("�z����ϥߩe�O�i"+legislatorList+"�j");
   	                	   }
   	                   }
   	                   );
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
    			"�i�ꤺ�L���y�j\n�i�N���~�ߩe�]���ۤv����ϥߩe�C\n"+
    	    	"�i���a�����j\n�i�N���a�����ߩe�]���ۤv����ϥߩe�C\n"+
    	    	"�i�s�a�����j\n�i�N�s�a�����ߩe�]���ۤv����ϥߩe�C\n"+
    			"�i�������s�j\n�i�N�ӿ������ݥߩe�]����ϥߩe�ζ}�Ҷm���Ͽ��C\n"+
    			"�i�m���ϫ��s�j\n�i�N�Ӷm���ϩ��ݥߩe�]����ϥߩe�ζ}�ҧ������C\n"+
    			"�i�������s�j\n�i�N�ӧ������ݥߩe�]���ۤv����ϥߩe�C\n";

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