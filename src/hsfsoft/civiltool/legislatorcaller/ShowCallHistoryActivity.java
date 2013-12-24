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
        //查詢聯絡紀錄
       	Cursor c = db.query("CALLING_RECORD", new String[]{"called_legislator_id","calling_datetime","called_number"},"calling_record_id>0",null,null,null,"calling_record_id desc");
       	c.moveToFirst();
       	int recordCount = c.getCount();
       	//若沒有聯絡紀錄
       	if(recordCount==0)
       	{
       		TextView newTextView = new TextView (this);
	        newTextView.setTextSize(18);
	        newTextView.setText("無紀錄");
	        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	        newTextView.setGravity(Gravity.CENTER);
	        newTextView.setLayoutParams(lp);
	        newTextView.setBackgroundColor(Color.rgb(230, 230, 230));
	        tableToken.addView(newTextView);
       	}
       	//若有聯絡紀錄
       	else
       	{
       		String[] legislator_List = getResources().getStringArray(R.array.Legislator_list_array);

   			TextView newTextView;
       		TableRow newTableRow;
       		Button newButton;
       		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
   			newTableRow=new TableRow(this);
   			newTableRow.setLayoutParams(lp);
   			
   			//顯示「刪除紀錄」按鍵表格列
   			newTextView=new TextView(this);
   			newTableRow.addView(newTextView);
   			newTextView=new TextView(this);
   			newTableRow.addView(newTextView);
        	newButton = new Button(this);
        	newButton.setText("刪除紀錄");
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

   			//顯示標題列
   			newTableRow=new TableRow(this);
   			newTextView=new TextView(this);
   			newTextView.setTextSize(18);
   			newTextView.setText("　立委　");
            newTextView.setGravity(Gravity.CENTER);
            newTextView.setBackgroundColor(Color.rgb(255, 200, 200));
   			newTableRow.addView(newTextView);
   			
   			newTextView=new TextView(this);
   			newTextView.setTextSize(16);
   			newTextView.setText("聯絡時間");
            newTextView.setGravity(Gravity.CENTER);
            newTextView.setBackgroundColor(Color.rgb(200, 255, 200));
   			newTableRow.addView(newTextView);

   			newTextView=new TextView(this);
   			newTextView.setTextSize(16);
   			newTextView.setText("撥打號碼");
            newTextView.setGravity(Gravity.CENTER);
            newTextView.setBackgroundColor(Color.rgb(200, 200, 255));
   			newTableRow.addView(newTextView);

   			tableToken.addView(newTableRow);

   			//顯示聯絡紀錄
       		for(int i=0;i<recordCount;i++)
       		{
       			//聯絡對象
       			newTableRow=new TableRow(this);
       			newTextView=new TextView(this);
       			newTextView.setTextSize(18);
       			newTextView.setText(legislator_List[Integer.parseInt(c.getString(0))]);
                newTextView.setGravity(Gravity.CENTER);
       			newTableRow.addView(newTextView);
       			
       			//聯絡時間
       			newTextView=new TextView(this);
       			newTextView.setText(c.getString(1));
                newTextView.setGravity(Gravity.CENTER);
       			newTableRow.addView(newTextView);

       			//重撥按鍵
       			final String LEGIST_ID = c.getString(0);
            	final String numberToDial = c.getString(2).replace("-", "");
            	final String numberToShow = c.getString(2);
            	newButton = new Button(this);
            	newButton.setText(c.getString(2));
            	newButton.setOnClickListener(new Button.OnClickListener(){
                	public void onClick(View v){
                		//重撥時更新聯絡紀錄
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

    //通話結束時重新整理此 Activity 以更新顯示
    @Override
    protected void onResume() {

       super.onResume();
       this.onCreate(null);
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
    			"【刪除紀錄】\n刪除所有聯絡紀錄。\n"+
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