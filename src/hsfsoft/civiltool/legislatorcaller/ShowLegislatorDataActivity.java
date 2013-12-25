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

//查詢基本資料
        	Cursor c = db.query("LEGISLATOR_DATA", new String[]{"legislator_name","legislator_type_id","legislator_dist_id","legislator_party_id"}, "legislator_id="+LEGIST_ID,null,null,null,null);
        	c.moveToFirst();
        	
        	TextView textviewToken = (TextView)findViewById(R.id.textviewLegislatorName);
        	textviewToken.setText(c.getString(0));
        	textviewToken = (TextView)findViewById(R.id.textviewPartyName);
        	textviewToken.setText(party_List[Integer.parseInt(c.getString(3))]);

        	String LEGIST_DIST_ID = c.getString(2);

        	c.close();

//查詢選區資料
        	c = db.query("LEGISLATOR_DIST_LIST", new String[]{"legislator_dist_name"}, "legislator_dist_id="+LEGIST_DIST_ID,null,null,null,null);
        	c.moveToFirst();

        	textviewToken = (TextView)findViewById(R.id.textviewLegislatorTypeContent);
        	textviewToken.setText(c.getString(0));

        	c.close();

//查詢委員會資料
        	c = db.query("COMMITTEE_LEGISLATOR_PAIR", new String[]{"committee_id","convener"}, "legislator_id="+LEGIST_ID,null,null,null,null);
        	c.moveToFirst();
        	int committeeCount;
        	committeeCount = c.getCount();
        	String committeeString="";
        	for (int i=0;i<committeeCount;i++){
        		if(i>0)
        		{
        			committeeString = committeeString + "、";
        		}
        		if(c.getString(1).equalsIgnoreCase("1"))
        		{
        			committeeString = committeeString + "【召】";
        		}
       		committeeString = committeeString + committee_List[Integer.parseInt(c.getString(0))];
       		c.moveToNext();
        	}

        	textviewToken = (TextView)findViewById(R.id.textviewCommitteeContent);
        	textviewToken.setText(committeeString);
        	c.close();
        	
//查詢國會研究室聯絡資料
        	c = db.query("legislator_contact_spot", new String[]{"contact_tel","contact_fax","contact_add"},"legislator_id="+LEGIST_ID+" AND contact_spot_name=\"國會研究室\"",null,null,null,null);
        	c.moveToFirst();
        	
        	//國會傳真資料
        	textviewToken = (TextView)findViewById(R.id.textviewFaxCongressContent);
        	textviewToken.setText(c.getString(1));
        	//國會地址資料
        	textviewToken = (TextView)findViewById(R.id.textviewAddCongressContent);
        	textviewToken.setText(c.getString(2));
        	
        	//國會號碼立即撥按鍵
        	final String numberToDial = c.getString(0).replace("-", "");
        	final String numberToShow = c.getString(0);
            Button buttonCDL = (Button)findViewById(R.id.buttonDialNowCongress);
            buttonCDL.setText("立即撥："+c.getString(0));
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

            //查詢各地服務處聯絡資料
            c = db.query("legislator_contact_spot", new String[]{"contact_spot_name","contact_tel", "contact_fax", "contact_add"}, "legislator_id="+LEGIST_ID+" AND contact_spot_name<>\"國會研究室\"", null, null, null,null);
            c.moveToFirst();
            int otherOfficeCount=0;
            otherOfficeCount = c.getCount();
            TableLayout tableToken = (TableLayout)findViewById(R.id.tablelayoutServiceOffice);
            //若有其他服務處
            if(otherOfficeCount>0){
            	for(int i=0;i<otherOfficeCount;i++){
                    TableRow tableRowToken = new TableRow(this);

                    //服務處名稱
                    TextView newTextView = new TextView (this);
	                newTextView.setTextSize(18);
	                newTextView.setText(c.getString(0));
	                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	                newTextView.setGravity(Gravity.CENTER);
	                newTextView.setLayoutParams(lp);
	                newTextView.setBackgroundColor(Color.rgb(200, 200, 200));
	                tableToken.addView(newTextView);

                    //服務處電話
	                newTextView = new TextView (this);
		                newTextView.setTextSize(18);
		                newTextView.setText("電話：");
		                tableRowToken.addView(newTextView);

		                //服務處電話立即撥按鍵
		                final String numberServiceToDial,numberServiceToShow;
		                numberServiceToDial = c.getString(1).replace("-", "");
		                numberServiceToShow = c.getString(1);
		                buttonCDL = new Button (this);
		                buttonCDL.setText("立即撥："+c.getString(1));
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
	                
	                //服務處傳真
	                tableRowToken = new TableRow(this);
		                newTextView = new TextView(this);
		                newTextView.setTextSize(18);
		                newTextView.setText("傳真：");
		                tableRowToken.addView(newTextView);
		                newTextView = new TextView(this);
		                newTextView.setTextSize(18);
		                newTextView.setText(c.getString(2));
		                tableRowToken.addView(newTextView);
	                tableToken.addView(tableRowToken);
	                
	                //服務處地址
	                tableRowToken = new TableRow(this);
		                newTextView = new TextView(this);
		                newTextView.setTextSize(18);
		                newTextView.setText("地址：");
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
            //若無其他服務處
            else
            {
                TextView newTextView = new TextView (this);
	                newTextView.setTextSize(18);
	                newTextView.setText("無");
	                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	                newTextView.setGravity(Gravity.CENTER);
	                newTextView.setLayoutParams(lp);
	                tableToken.addView(newTextView);
	            	c.close();
	                db.close();
           }
        }
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
    			"【有電話號碼的按鈕】\n可撥該號碼。";

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