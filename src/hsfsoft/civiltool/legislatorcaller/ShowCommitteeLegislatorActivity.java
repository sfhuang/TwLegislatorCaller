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
        	
        	//查詢委員會委員名單
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
  
        	//建立委員按鍵清單
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
	       	                	   convenerText="※";
	       	                   }
	       	                   else{
	       	                	   convenerText="";
	       	                   }   
	       	                   b.setText(convenerText+legislator_List[Integer.parseInt(c.getString(0))]);
	       	                   final String theLegislatorId = c.getString(0);
	       	                   //按下按鍵後進入該該委員資料頁面
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
    			"【有立委姓名的按鈕】\n可查看該立委聯絡資訊。\n姓名前面加 ※ 的是召集委員。";

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