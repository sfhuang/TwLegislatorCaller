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

        //�d�ߨϥΪ̿�ϳ]�w
        Cursor c = db.query("PERSONAL_SETTING", new String[]{"current_district_legislator_id","legislator_dist_id"}, "userid=1", null, null, null, null);
        c.moveToFirst();
        final String LEGIST_ID;
        //�Y���]�w���
        if(c.getCount()>0)
        {
        	//�Y�w�]�w��ϥB�ӿ�ϥu���@�W�ߩe
	        if(c.getString(0)!=null&&c.getString(0).length()>0)
	        {
	        	LEGIST_ID = c.getString(0);
	            c.close();
	        	Intent intent=new Intent(MainActivity.this,ShowLegislatorDataActivity.class);
	            intent.putExtra("LEGIST_ID",LEGIST_ID);
	            startActivity(intent);
	        }
        	//�Y�w�]�w���
	        else if(c.getString(1)!=null&&c.getString(1).length()>0)
	        {
	        	//�d�߸ӿ�ϥߩe
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
	  
	        	//�إ߿�ϥߩe���s���
	        	TableLayout layout = (TableLayout)findViewById(R.id.tableLegislatorList);
	            for (int rowS=0; rowS<rowCount; rowS++) { 
	            	//tr���@�C�e���A�ΨӦs����s
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
	        //�Y�|���]�w��ϡG�i�J��ϳ]�w���
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
	
	//�}�Ҥ����ϥߩe�W��
	public void changeToCountryLegislator(View v){
		Intent intent=new Intent(MainActivity.this,ShowCountryLegislatorActivity.class);
        startActivity(intent);
	}

	//�}�Ҥ��ϥߩe�W��
	public void changeToDistLegislator(View v){
		Intent intent=new Intent(MainActivity.this,ShowDistLegislatorActivity.class);
        startActivity(intent);
	}
	
	//�e���e���|����A�إߦU����ƥ�]���U��i��ܬ����e���|�e���W��^
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
	
	//�e���F�һ���A�إߦU����ƥ�]���U��i��ܬ����F�ҩe���W��^
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
	
	//�}���p������
	public void changeToCallHistory(View v){
		Intent intent = new Intent(MainActivity.this,ShowCallHistoryActivity.class);
		startActivity(intent);
	}

	//�i�J��ϳ]�w�e���]�f�t���䪺 onClick�^
	public void changeToPersonalSetting(View v){
		setContentView(R.layout.personal_setting_layout);
	}
	
	//�^��D�e���]�f�t���䪺 onClick�^
	public void backPreviousPage(View v){
		setContentView(R.layout.activity_main);
	}

	//�������� Activity�]�f�t���䪺 onClick�^
    public void exitThisActivity(View v) {
		setContentView(R.layout.activity_main);
	}
    
    //�����{���]�f�t���䪺 onClick�^
    public void exitApp(View v) {
		super.finish();
	}
	
    //�}�Ҩ̥ߩe�W�ٳ]�w��ϥߩe�e���]�f�t�]�w��ϸ����W���䪺 onClick�^
    public void settingByName(View v){
		Intent intent=new Intent(MainActivity.this,SettingDistLegislatorByNameActivity.class);
        startActivity(intent);
    }

    //�}�Ҩ̿�ϳ]�w��ϥߩe�e���]�f�t�]�w��ϸ����W���䪺 onClick�^
    public void settingByDist(View v){
		Intent intent=new Intent(MainActivity.this,SettingDistLegislatorByDistActivity.class);
        startActivity(intent);
    }

    //�}�Ҩ̤��y�a�]�w��ϥߩe�e���]�f�t�]�w��ϸ����W���䪺 onClick�^
    public void settingByAddress(View v){
		Intent intent=new Intent(MainActivity.this,SettingDistLegislatorByAddressActivity.class);
        startActivity(intent);
    }
    
    //�\���涵�س]�w
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 0, 0,"���󥻵{��");
    	menu.add(0, 2, 0,"�`�N�ƶ�");
    	menu.add(0, 1, 0,"�ϥλ���");
		return true;
    }
    
    //�\����U���ؤ��e�]�w
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    		case 0:
    			String dialogText = 
    			"�i�W�١j�A�÷d�ڥ��z-�ߩe���u\n"+
    			"�i�{�������j1.0\n"+
    			"�i�{���s���j�R�O�{�� No.001\n"+
    			"�i�D�n�γ~�j�����q�ܺʷ��ߩe\n"+
    			"�i�{���@�̡jSF.Huang\n"+
    			"�i��������j2013.12.24\n"+
    			"�i��s����j�|����s\n"+
    			"�i��ƨӷ��j�ߪk�|��������\n"+
    			"�i�A�δ��O�j�Ģ����Ģ��|��";

    			new AlertDialog.Builder(this)
    	    	.setTitle("���󥻵{��")
    	    	.setMessage(dialogText)
    	    	.setPositiveButton("�T�w",null)
    	    	.show();    			
    			return true;
    		case 1:
    			dialogText = 
    			"�i�p�����ݿ�ϥߩe�j�Y�w�]�w��ϥߩe�i�s�����ݿ�ϥߩe�C\n"+
    			"�i�p�����ϥߩe�j�q���ϥߩe�W�����p����H�C\n"+
    			"�i�p�������ϥߩe�j�q�����ϥߩe�W�����p����H�C\n"+
    			"�i�p���F�ҥߩe�j�q�U�F�ҥߩe�W�����p����H�C\n"+
    			"�i�p���e���|�ߩe�j�q�U�e���|�ߩe�W�����p����H�C\n"+
    			"�i�L�h�p�������j�d�ݤ��p���L�h���p�����ߩe�C\n"+
    			"�i�]�w��ϥߩe�j�]�w�������ݿ�Ϫ��ߩe�C";

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
    
    //�����w�� back �䪺�����{���\��
    @Override
    public void onBackPressed() {
		setContentView(R.layout.activity_main);
    }
}
