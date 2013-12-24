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
    	
    	//設定「國內無戶籍」按鍵功能
        Button buttonToken = (Button)findViewById(R.id.buttonLiveAbroad);
        buttonToken.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				SQLiteDatabase db = helper.getReadableDatabase();
				//查詢僑居國外國民選區立委名單
		        Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id=2400",null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        String legislatorList = "";
		        String legislatorId = "";
		        if(legislatorCount>0){
		        	for(int i=0;i<legislatorCount;i++){
		        		if(i>0)
		        		{
		        			legislatorList = legislatorList+"、";
		        		}
		        		legislatorList = legislatorList + c.getString(0);
		        		legislatorId = c.getString(1);
		        		c.moveToNext();
		        	}
		        }
		        c.close();
				db.close();

				//將選區設定為僑居國外國民
				db = helper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("legislator_dist_id","2400");
				//若選區只有一名立委
				if(legislatorCount==1)
				{
					values.put("current_district_legislator_name",legislatorList);
					values.put("current_district_legislator_id",legislatorId);
					values.put("province_in_record", "");
					values.put("city_in_record", "");
					values.put("dist_in_record", "");
					values.put("village_in_record", "");
				}
				//若選區有數名立委
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
		        //設定顯示畫面
		        TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        textviewToken.setText("您的選區是【僑居國外國民】選區");
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("您的選區立委是【"+legislatorList+"】");
			}
        });
    }

    public void selectIfNative(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_ifnative_layout);

    	final LegislatorDB helper = new LegislatorDB(this);
    	
    	//設定「平地原住民」按鍵功能
    	Button buttonToken = (Button)findViewById(R.id.buttonIsPlainNative);
        buttonToken.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
		        SQLiteDatabase db = helper.getWritableDatabase();

		        //將選區設定為平地原住民
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
		        //查詢平地原住民選區立委名單
		        db = helper.getReadableDatabase();
		        Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name"},"legislator_dist_id=2200",null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        String legislatorList = "";
		        if(legislatorCount>0){
		        	for(int i=0;i<legislatorCount;i++){
		        		if(i>0)
		        		{
		        			legislatorList = legislatorList+"、";
		        		}
		        		legislatorList = legislatorList + c.getString(0);
		        		c.moveToNext();
		        	}
		        }
		        c.close();
		        db.close();
		        //設定顯示畫面
		        TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        textviewToken.setText("您的選區是【平地原住民】選區");
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("您的選區立委是【"+legislatorList+"】");
			}
        });

        buttonToken = (Button)findViewById(R.id.buttonIsMountNative);
        buttonToken.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
		        SQLiteDatabase db = helper.getWritableDatabase();

		        //將選區設定為山地原住民
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
		        //查詢山地原住民選區立委名單
		        db = helper.getReadableDatabase();
		        Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name"},"legislator_dist_id=2300",null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
		        String legislatorList = "";
		        if(legislatorCount>0){
		        	for(int i=0;i<legislatorCount;i++){
		        		if(i>0)
		        		{
		        			legislatorList = legislatorList+"、";
		        		}
		        		legislatorList = legislatorList + c.getString(0);
		        		c.moveToNext();
		        	}
		        }
		        c.close();
		        db.close();
		        //設定顯示畫面
		        TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        textviewToken.setText("您的選區是【山地原住民】選區");
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("您的選區立委是【"+legislatorList+"】");
			}
        });
    }
    
    //前往依戶籍地設定選區畫面
    public void backPreviousPage(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_layout);
	}

    //前往是否為原住民選擇畫面
	public void backIsNativePage(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_ifnative_layout);
	}
	
	//前往縣市列表
	public void goCityList(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_citylevel_layout);
		
		LegislatorDB helper = new LegislatorDB(this);
        SQLiteDatabase db = helper.getReadableDatabase();
		
        //查詢縣市清單
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

    	//顯示縣市按鍵清單
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
   	                		   //若該縣市本身不是一個選區，則前往鄉鎮市區層級名單
   	                		   if(LEGIS_DIS_ID.length()<1)
   	                		   {
   	                			   Province_Global_Id=PROVINCE_ID;
   	        	                   City_Global_Id=CITY_ID;
   	                			   goDistList(v);
   	                		   }
   	                		   //若該縣市本身是一個選區，則將該縣市設定為使用者選區
   	                		   else if(Integer.parseInt(LEGIS_DIS_ID)>0)
   	                		   {
   	                			   setContentView(R.layout.setting_done_layout);

   	                			   SQLiteDatabase db2 = helper2.getReadableDatabase();
   	                		       String legislatorList = "";
   	                		       String DIST_NAME="";

   	                		       //查詢該選區名稱
   	                		       Cursor c = db2.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		       c.moveToFirst();
   	                		       DIST_NAME = c.getString(0);
   	                		       c.close();
   	                		       
   	                		       //查詢該選區立委名單
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
   	                			        		legislatorList = legislatorList+"、";
   	                					   }
   	                					   legislatorList = legislatorList + c.getString(0);
   	                					   legislatorId = c.getString(1);
   	                					   c.moveToNext();
   	                			        }
   	                			   }
   	                			   c.close();
   	                			   db2.close();
   	                			        
   	                			   //設定該選區為使用者選區
   	                			   db2 = helper2.getWritableDatabase();
   	                			   ContentValues values=new ContentValues();
   	                			   values.put("legislator_dist_id",LEGIS_DIS_ID);
   	                			   //若該選區立委只有一人
   	                			   if(legislatorCount==1)
   	                			   {
   	                				   values.put("current_district_legislator_name",legislatorList);
   	                				   values.put("current_district_legislator_id",legislatorId);
   	                				   values.put("province_in_record", Province_Global_Id);
   	                				   values.put("city_in_record", City_Global_Id);
   	                				   values.put("dist_in_record", "");
   	                				   values.put("village_in_record", "");
   	                			   }
   	                			   //若該選區立委有數人
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

   	                			   //更新畫面顯示
   	                			   TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
   	                			   if(Integer.parseInt(LEGIS_DIS_ID)>2100)
   	                			   {
   	                			        textviewToken.setText("您的選區是【"+DIST_NAME+"】選區");
   	                			   }
   	                			   else
   	                			   {
   	                			        textviewToken.setText("您的選區是【"+DIST_NAME+"】");
   	                			   }
   	                			   textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
   	                			   textviewToken.setText("您的選區立委是【"+legislatorList+"】");
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

	//前往鄉鎮市區層級清單
	public void goDistList(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_distlevel_layout);
		LegislatorDB helper = new LegislatorDB(this);
        SQLiteDatabase db = helper.getReadableDatabase();
		
        //查詢相關縣市的鄉鎮市區
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

    	//顯示鄉鎮市區按鍵清單
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
   	                		   //若該鄉鎮市區並非全境皆屬於一個選區，前往村里清單
   	                		   if(LEGIS_DIS_ID.length()<1)
   	                		   {
   	                			   District_Global_Id=DIST_ID;
   	                			   goVillageList(v);
   	                		   }
   	                		   //若該鄉鎮市區全境皆屬於一個選區
   	                		   else if(Integer.parseInt(LEGIS_DIS_ID)>0)
   	                		   {
   	                			   setContentView(R.layout.setting_done_layout);

   	                			   SQLiteDatabase db2 = helper2.getReadableDatabase();
   	                		       String legislatorList = "";
   	                		       String DIST_NAME="";

   	                		       //查詢該選區名稱
   	                		       Cursor c = db2.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		       c.moveToFirst();
   	                		       DIST_NAME = c.getString(0);
   	                		       c.close();
   	                		       
   	                		       //查詢該選區立委名單
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
   	                			        		legislatorList = legislatorList+"、";
   	                					   }
   	                					   legislatorList = legislatorList + c.getString(0);
   	                					   legislatorId = c.getString(1);
   	                					   c.moveToNext();
   	                			        }
   	                			   }
   	                			   c.close();
   	                			   db2.close();
   	                			        
   	                			   //設定所屬選區
   	                			   db2 = helper2.getWritableDatabase();
   	                			   ContentValues values=new ContentValues();
   	                			   values.put("legislator_dist_id",LEGIS_DIS_ID);
   	                			   //若該選區立委只有一人
   	                			   if(legislatorCount==1)
   	                			   {
   	                				   values.put("current_district_legislator_name",legislatorList);
   	                				   values.put("current_district_legislator_id",legislatorId);
   	                				   values.put("province_in_record", Province_Global_Id);
   	                				   values.put("city_in_record", City_Global_Id);
   	                				   values.put("dist_in_record", DIST_ID);
   	                				   values.put("village_in_record", "");
   	                			   }
   	                			   //若該選區立委有數人
   	                			   else
   	                			   {
   	                				   values.put("current_district_legislator_name","");
   	                				   values.put("current_district_legislator_id","");
   	                				   values.put("province_in_record", Province_Global_Id);
   	                				   values.put("city_in_record", City_Global_Id);
   	                				   values.put("dist_in_record", DIST_ID);
   	                				   values.put("village_in_record", "");
   	                			   }
   	                			   //更新使用者選區資料
   	                			   db2.update("PERSONAL_SETTING",values,"userid='1'",null);
   	                			   db2.close();

   	                			   //變更畫面顯示
   	                			   TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
   	                			   if(Integer.parseInt(LEGIS_DIS_ID)>2100)
   	                			   {
   	                			        textviewToken.setText("您的選區是【"+DIST_NAME+"】選區");
   	                			   }
   	                			   else
   	                			   {
   	                			        textviewToken.setText("您的選區是【"+DIST_NAME+"】");
   	                			   }
   	                			   textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
   	                			   textviewToken.setText("您的選區立委是【"+legislatorList+"】");
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

	//前往村里清單
	public void goVillageList(View v){
		setContentView(R.layout.setting_dist_legislator_by_add_villagelevel_layout);

		LegislatorDB helper = new LegislatorDB(this);
        SQLiteDatabase db = helper.getReadableDatabase();
		
        //查詢鄉鎮市區下的村里清單
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

    	//建立村里按鍵清單
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

   	                		   //查詢該村裡所屬選區名稱
   	                		   Cursor c = db2.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+LEGIS_DIS_ID,null,null,null,null);
   	                		   c.moveToFirst();
   	                		   DIST_NAME = c.getString(0);
   	                		   c.close();
   	                		       
   	                		   //查詢該選區立委名單
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
   	                					   legislatorList = legislatorList+"、";
   	               					   }
   	                				   legislatorList = legislatorList + c.getString(0);
   	                				   legislatorId = c.getString(1);
   	                				   c.moveToNext();
   	                			   }
   	                		   }
   	                		   c.close();
   	                		   db2.close();
   	                			        
   	                		   //設定使用者選區
   	                		   db2 = helper2.getWritableDatabase();
   	                		   ContentValues values=new ContentValues();
   	                		   values.put("legislator_dist_id",LEGIS_DIS_ID);
   	                		   //若該選區立委只有一人
   	               			   if(legislatorCount==1)
   	               			   {
   	               				   values.put("current_district_legislator_name",legislatorList);
   	               				   values.put("current_district_legislator_id",legislatorId);
                				   values.put("province_in_record", Province_Global_Id);
                				   values.put("city_in_record", City_Global_Id);
                				   values.put("dist_in_record", District_Global_Id);
                				   values.put("village_in_record", VILLAGE_ID);
   	               			   }
   	                		   //若該選區立委超過一人
	                		   else
	                		   {
	                			   values.put("current_district_legislator_name","");
	                			   values.put("current_district_legislator_id","");
	                			   values.put("province_in_record", Province_Global_Id);
	                			   values.put("city_in_record", City_Global_Id);
	                			   values.put("dist_in_record", District_Global_Id);
	                			   values.put("village_in_record", VILLAGE_ID);
	                		   }
   	               			   //更新選區設定
   	               			   db2.update("PERSONAL_SETTING",values,"userid='1'",null);
   	               			   db2.close();

   	               			   //變更畫面顯示
   	               			   TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
   	               			   textviewToken.setText("您的選區是【"+DIST_NAME+"】");
   	               			   textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
                   			   textviewToken.setText("您的選區立委是【"+legislatorList+"】");
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
	
	//結束此 Activity
    public void exitThisActivity(View v) {
    	super.finish();
	}
	
    //功能選單項目設定
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 1, 0,"使用說明");
    	menu.add(0, 2, 0,"注意事項");
        return true;
    }
    
    //功能選單各項目內容設定
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    		case 1:
    			String dialogText = 
    			"【國內無戶籍】\n可將僑外立委設為自己的選區立委。\n"+
    	    	"【平地原住民】\n可將平地原住民立委設為自己的選區立委。\n"+
    	    	"【山地原住民】\n可將山地原住民立委設為自己的選區立委。\n"+
    			"【縣市按鈕】\n可將該縣市所屬立委設為選區立委或開啟鄉鎮市區選單。\n"+
    			"【鄉鎮市區按鈕】\n可將該鄉鎮市區所屬立委設為選區立委或開啟村里選單。\n"+
    			"【村里按鈕】\n可將該村里所屬立委設為自己的選區立委。\n";

    			new AlertDialog.Builder(this)
    	    	.setTitle("使用說明")
    	    	.setMessage(dialogText)
    	    	.setPositiveButton("確定",null)
    	    	.show();    			
    			return true;

    		case 2:
    			dialogText = 
    			"1.立法委員聯絡資訊係取自立法院網站，作者不保證其正確性。\n"+
    	    	"2.本程式必須於臺灣使用，電話必須於臺灣境內才能正確撥打。\n"+
    	    	"3.撥打電話時會使用到行動通信服務，使用者必須自行負擔通話費。\n"+
    	    	"4.作者對使用者使用本程式所生一切後果（包括收到小禮物）不負責任。\n"+
    	    	"5.本程式使用者以臺灣居民為限。\n"+
    	    	"6.使用者僅得基於個人非營利用途使用本程式。\n"+
    	    	"7.非經作者書面許可，不得改作本程式或為逆向工程。\n"+
    	    	"8.「中華人民共和國」國民非經作者書面許可不得使用本程式，違者應向作者支付美金 1 千元之損害賠償。\n";

    			new AlertDialog.Builder(this)
    	    	.setTitle("注意事項")
    	    	.setMessage(dialogText)
    	    	.setPositiveButton("確定",null)
    	    	.show();    			
    			return true;
    	}
		return false;
    }
    
}