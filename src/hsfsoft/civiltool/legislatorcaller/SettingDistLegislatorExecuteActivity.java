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
	        //取得設定類型
        	String SETTING_TYPE = (String) extraData.getCharSequence("SETTING_TYPE");
        	//若依選區設定
        	if(SETTING_TYPE.equalsIgnoreCase("BY_DIST")){
            	String KEY_ID=(String) extraData.getCharSequence("LEGIST_DIST_ID");
            	DIST_NAME=(String) extraData.getCharSequence("DIST_NAME");
            	
            	//查詢該選區立委名單
            	Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id="+KEY_ID,null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
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
		        
		        //設定選區
				db = helper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("legislator_dist_id",KEY_ID);
				//若該選區立委只有一人
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
				//更新選區設定
				db.update("PERSONAL_SETTING",values,"userid='1'",null);
				db.close();

				//變更畫面顯示
				TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        if(Integer.parseInt(KEY_ID)>2100){
			        textviewToken.setText("您的選區是【"+DIST_NAME+"】選區");
		        }
		        else
		        {
			        textviewToken.setText("您的選區是【"+DIST_NAME+"】");
		        }
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("您的選區立委是【"+legislatorList+"】");
        	}
        	//若依立委設定
        	else
        	{
            	String KEY_ID=(String) extraData.getCharSequence("LEGIST_DIST_ID");
            	String LEGISLATOR_NAME=(String) extraData.getCharSequence("LEGIST_NAME");
            	String LEGISLATOR_ID=(String) extraData.getCharSequence("LEGIST_ID");
            	
            	//查詢該立委所屬選區
            	Cursor c = db.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"},"legislator_dist_id="+KEY_ID,null,null,null,null);
		        c.moveToFirst();
		        DIST_NAME = c.getString(0);
		        c.close();

            	//查詢該選區立委名單
            	c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_id"},"legislator_dist_id="+KEY_ID,null,null,null,null);
		        c.moveToFirst();
		        int legislatorCount = c.getCount();
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
		        
				db = helper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("legislator_dist_id",KEY_ID);
				//若該選區立委只有一人
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
				//更新選區設定
				db.update("PERSONAL_SETTING",values,"userid='1'",null);
				db.close();

				//變更畫面顯示
				TextView textviewToken = (TextView)findViewById(R.id.yourDistIs);
		        if(Integer.parseInt(KEY_ID)>2100){
			        textviewToken.setText("您的選區是【"+DIST_NAME+"】選區");
		        }
		        else
		        {
			        textviewToken.setText("您的選區是【"+DIST_NAME+"】");
		        }
		        textviewToken = (TextView)findViewById(R.id.yourCurrentLegislator);
		        textviewToken.setText("您的選區立委是【"+legislatorList+"】");
        	}
        }
        else
        {
        	db.close();
        }
        
    }

    //結束此 Activity 
	public void exitThisActivity(View v) {
    	super.finish();
	}
	
    //功能選單項目設定
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 2, 0,"注意事項");
        return true;
    }
    
    //功能選單各項目內容設定
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    		case 1:
    			String dialogText = 
    			"【有立委姓名的按鈕】\n可將該立委設為選區立委。";

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