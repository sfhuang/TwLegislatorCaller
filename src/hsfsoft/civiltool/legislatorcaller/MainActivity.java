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
import android.widget.TableRow;
import android.widget.TableLayout.LayoutParams;

public class MainActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LegislatorDB helper = new LegislatorDB(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        db.close();
    }

	public void callDistLegislator(View v){
		
        LegislatorDB helper = new LegislatorDB(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

        //查詢使用者選區設定
        Cursor c = db.query("PERSONAL_SETTING", new String[]{"current_district_legislator_id","legislator_dist_id"}, "userid=1", null, null, null, null);
        c.moveToFirst();
        final String LEGIST_ID;
        //若有設定資料
        if(c.getCount()>0)
        {
        	//若已設定選區且該選區只有一名立委
	        if(c.getString(0)!=null&&c.getString(0).length()>0)
	        {
	        	LEGIST_ID = c.getString(0);
	            c.close();
	        	Intent intent=new Intent(MainActivity.this,ShowLegislatorDataActivity.class);
	            intent.putExtra("LEGIST_ID",LEGIST_ID);
	            startActivity(intent);
	        }
        	//若已設定選區
	        else if(c.getString(1)!=null&&c.getString(1).length()>0)
	        {
	        	//查詢該選區立委
	    		setContentView(R.layout.contact_by_dist_layout);
	    		Cursor c2 = db.query("LEGISLATOR_DATA",new String[]{"legislator_id","legislator_name"}, "legislator_dist_id="+c.getString(1),null,null,null,null);
	    		c.close();
	        	c2.moveToFirst();
	
	        	int colCount = 3;
	        	int dataCount = c2.getCount();
	        	int showedCount = 0;
	        	int reminder = dataCount%colCount;
	        	int rowCount = 0;
	
	        	if(reminder==0)
	        		rowCount=dataCount/colCount;
	        	else
	        		rowCount=((dataCount-reminder)/colCount)+1;
	  
	        	//建立選區立委按鈕選單
	        	TableLayout layout = (TableLayout)findViewById(R.id.tableLegislatorList);
	            for (int rowS=0; rowS<rowCount; rowS++) { 
	            	//tr為一列容器，用來存放按鈕
	                  TableRow tr = new TableRow(this);
	                       for (int colS=0; colS<colCount; colS++) {
	                    	   if(showedCount<dataCount)
	                    	   {
		       	                   Button b = new Button (this);
		       	                   TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		       	                   params.weight = 1.0f;
		       	                   b.setLayoutParams(params);
		       	                   b.setTextSize(18);
		       	                   b.setText(c2.getString(1));
		       	                   final String theLegislatorId = c2.getString(0);
		       	                   b.setOnClickListener(new Button.OnClickListener(){
		       	                	   public void onClick(View v){
		       	                		   Intent intent=new Intent(MainActivity.this,ShowLegislatorDataActivity.class);
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
	            c2.close();
	        }
	        //若尚未設定選區：進入選區設定選單
	        else
	        {
	            c.close();
	        	changeToPersonalSetting(v);
	        }
	        
        }
        else
        {
            c.close();
        	changeToPersonalSetting(v);
        }
        db.close();
	}
	
	//開啟不分區立委名單
	public void changeToCountryLegislator(View v){
		Intent intent=new Intent(MainActivity.this,ShowCountryLegislatorActivity.class);
        startActivity(intent);
	}

	//開啟分區立委名單
	public void changeToDistLegislator(View v){
		Intent intent=new Intent(MainActivity.this,ShowDistLegislatorActivity.class);
        startActivity(intent);
	}
	
	//前往委員會輕單，建立各按鍵事件（按下後可顯示相關委員會委員名單）
	public void changeToByCommitteeLayout(View v){
		setContentView(R.layout.contact_by_committee_layout);

		Button buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE0);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "0");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE1);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "1");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE2);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "2");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE3);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "3");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE4);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "4");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE5);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "5");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE6);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "6");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE7);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "7");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE8);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "8");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE9);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "9");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE10);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "10");
				startActivity(intent);
			}
		});

		buttonToken = (Button)findViewById(R.id.buttonCOMMITTEE11);
		buttonToken.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowCommitteeLegislatorActivity.class);
				intent.putExtra("COMMITTEE_ID", "11");
				startActivity(intent);
			}
		});
	}
	
	//前往政黨輕單，建立各按鍵事件（按下後可顯示相關政黨委員名單）
	public void changeToByPartyLayout(View v){
		setContentView(R.layout.contact_by_party_layout);

		Button buttonKMT = (Button)findViewById(R.id.buttonKMT);
		buttonKMT.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowPartyLegislatorActivity.class);
				intent.putExtra("PARTY_ID", "1");
				startActivity(intent);
			}
		});

		Button buttonDDP = (Button)findViewById(R.id.buttonDDP);
		buttonDDP.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowPartyLegislatorActivity.class);
				intent.putExtra("PARTY_ID", "2");
				startActivity(intent);
			}
		});

		Button buttonTSU = (Button)findViewById(R.id.buttonTSU);
		buttonTSU.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowPartyLegislatorActivity.class);
				intent.putExtra("PARTY_ID", "3");
				startActivity(intent);
			}
		});

		Button buttonPFP = (Button)findViewById(R.id.buttonPFP);
		buttonPFP.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowPartyLegislatorActivity.class);
				intent.putExtra("PARTY_ID", "4");
				startActivity(intent);
			}
		});

		Button buttonNPSU = (Button)findViewById(R.id.buttonNPSU);
		buttonNPSU.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowPartyLegislatorActivity.class);
				intent.putExtra("PARTY_ID", "5");
				startActivity(intent);
			}
		});

		Button buttonNOPARTY = (Button)findViewById(R.id.buttonNOPARTY);
		buttonNOPARTY.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ShowPartyLegislatorActivity.class);
				intent.putExtra("PARTY_ID", "0");
				startActivity(intent);
			}
		});
	}
	
	//開啟聯絡紀錄
	public void changeToCallHistory(View v){
		Intent intent = new Intent(MainActivity.this,ShowCallHistoryActivity.class);
		startActivity(intent);
	}

	//進入選區設定畫面（搭配按鍵的 onClick）
	public void changeToPersonalSetting(View v){
		setContentView(R.layout.personal_setting_layout);
	}
	
	//回到主畫面（搭配按鍵的 onClick）
	public void backPreviousPage(View v){
		setContentView(R.layout.activity_main);
	}

	//關閉相關 Activity（搭配按鍵的 onClick）
    public void exitThisActivity(View v) {
		setContentView(R.layout.activity_main);
	}
    
    //結束程式（搭配按鍵的 onClick）
    public void exitApp(View v) {
		super.finish();
	}
	
    //開啟依立委名稱設定選區立委畫面（搭配設定選區葉面上按鍵的 onClick）
    public void settingByName(View v){
		Intent intent=new Intent(MainActivity.this,SettingDistLegislatorByNameActivity.class);
        startActivity(intent);
    }

    //開啟依選區設定選區立委畫面（搭配設定選區葉面上按鍵的 onClick）
    public void settingByDist(View v){
		Intent intent=new Intent(MainActivity.this,SettingDistLegislatorByDistActivity.class);
        startActivity(intent);
    }

    //開啟依戶籍地設定選區立委畫面（搭配設定選區葉面上按鍵的 onClick）
    public void settingByAddress(View v){
		Intent intent=new Intent(MainActivity.this,SettingDistLegislatorByAddressActivity.class);
        startActivity(intent);
    }
    
    //功能選單項目設定
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 0, 0,"關於本程式");
    	menu.add(0, 2, 0,"注意事項");
    	menu.add(0, 1, 0,"使用說明");
		return true;
    }
    
    //功能選單各項目內容設定
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    		case 0:
    			String dialogText = 
    			"【名稱】你亂搞我打爆-立委熱線\n"+
    			"【程式版本】1.0\n"+
    			"【程式編號】愛臺程式 No.001\n"+
    			"【主要用途】撥打電話監督立委\n"+
    			"【程式作者】SF.Huang\n"+
    			"【完成日期】2013.12.24\n"+
    			"【更新日期】尚未更新\n"+
    			"【資料來源】立法院公眾網站\n"+
    			"【適用期別】第８屆第４會期";

    			new AlertDialog.Builder(this)
    	    	.setTitle("關於本程式")
    	    	.setMessage(dialogText)
    	    	.setPositiveButton("確定",null)
    	    	.show();    			
    			return true;
    		case 1:
    			dialogText = 
    			"【聯絡所屬選區立委】若已設定選區立委可連絡所屬選區立委。\n"+
    			"【聯絡分區立委】從分區立委名單選取聯絡對象。\n"+
    			"【聯絡不分區立委】從不分區立委名單選取聯絡對象。\n"+
    			"【聯絡政黨立委】從各政黨立委名單選取聯絡對象。\n"+
    			"【聯絡委員會立委】從各委員會立委名單選取聯絡對象。\n"+
    			"【過去聯絡紀錄】查看及聯絡過去曾聯絡的立委。\n"+
    			"【設定選區立委】設定本身所屬選區的立委。";

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
    
    //取消硬體 back 鍵的關閉程式功能
    @Override
    public void onBackPressed() {
		setContentView(R.layout.activity_main);
    }
}
