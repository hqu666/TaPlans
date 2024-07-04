package com.hijiyam_koubou.taplans;

import android.app.AlarmManager;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.hijiyam_koubou.taplans.databinding.ActivityMainBinding;
import com.hijiyam_koubou.taplans.ui.target_setting.TargetPlanFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public MyPreferences myPref;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor myEditor;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    /**
     * 登録するカレンダーのアカウント
     * */
    public String calenderAccount;          //アカウント
    public String sundayBackground;          //">#FDE7E7</color>
    public String sundayTextColor;         //">#ff0000</color>
    public String satudayBackground;          //">#EDEDFF</color>
    public String satudayTextColor;            //">#0000FF</color>
    public String defaultBackground;            //">#FFFFFF</color>
    public String defaultTextColor;             //">#000000</color>
    /**
     * 前後2か月の対象日リスト：Googleカレンダー非連動時の対策
     * */
    private ArrayList<Date> tDates;
    public String targetDays;
    /**
     * 設定す津予定
     * */
    public String tEventName;             //  予定の名称
    public String tEventSound;           // アラーム音
    public String tArarmTime1 = "06 : 00";           // アラーム時刻1
    public Boolean ta100c = false;               // アラーム時刻1 の日曜
    public Boolean ta101c = false;          // アラーム時刻1 の月曜
    public Boolean ta102c = false;        // アラーム時刻1 の火曜
    public Boolean ta103c = false;         // アラーム時刻1 の水曜
    public Boolean ta104c = false;           // アラーム時刻1 の木曜
    public Boolean ta105c = false;            // アラーム時刻1 の金曜
    public Boolean ta106c = false;            // アラーム時刻1 の土曜

    public String tArarmTime2 = "07 : 00";           // アラーム時刻2
    public Boolean ta200c = false;             // アラーム時刻2 の日曜
    public Boolean ta201c = false;            // アラーム時刻2 の月曜
    public Boolean ta202c = false;            // アラーム時刻2 の火曜
    public Boolean ta203c = false;               // アラーム時刻2 の水曜
    public Boolean ta204c = false;              // アラーム時刻2 の木曜
    public Boolean ta205c = false;          // アラーム時刻2 の金曜
    public Boolean ta206c = false;              // アラーム時刻2 の土曜

    public long alarmTimeMillis = 0;
    public AlarmManager alarmManager;
    /**
     * ツールバー右のメニューアイコンからメニューを表示
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューを拡張します。これにより、アクション バーが存在する場合に項目が追加されます。
        final String TAG = "onCreateOptionsMenu";
        String dbMsg = "[MainActivity]";
        try {
            getMenuInflater().inflate(R.menu.main, menu);
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String TAG = "onOptionsItemSelected";
        String dbMsg = "[MainActivity]";
        boolean retBool = true;
        try {
            int itemID = item.getItemId();
            dbMsg += "[" + itemID + "]";
            CharSequence itemTitle = item.getTitle();
            dbMsg += itemTitle;
            if(itemTitle == null){
                dbMsg += ",itemTitle=null";
            }else{
                if(itemTitle.equals(getResources().getString(R.string.action_settings))){
                    dbMsg += getResources().getString(R.string.action_settings);
                    showPref();
                    retBool = true;
                }else if(itemTitle.equals(getResources().getString(R.string.action_quit))){
                    dbMsg += getResources().getString(R.string.action_quit);
                    quitMe();
                    retBool = true;
                }else{
                    retBool =  super.onOptionsItemSelected(item);
                }
                // R.id. が定数ではなくなった////////////////////////
                // 1000099=R.id.action_quitに対してgetItemIdの戻り値は2131230787が返されるの
//                switch (itemID) {
//                case  R.id.action_settings:         //1000095        R.id.action_settings
//                    dbMsg += getResources().getString(R.string.action_settings);
//                    retBool = true;
//                case 1000099:              //R.id.action_quit:
//                    dbMsg += getResources().getString(R.string.action_quit);
//                    quitMe();
//                    retBool = true;
//                default:
//                    retBool =  super.onOptionsItemSelected(item);
//            }
            }
             dbMsg += ",retBool=" + retBool;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retBool;
    }

    /**
     * ドロワーメニューをタップした時
     * */
    @Override
    public boolean onSupportNavigateUp() {
        final String TAG = "onSupportNavigateUp";
        String dbMsg = "[MainActivity]";

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

         try {
             myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    /**
     * 設定画面を表示
     * */
    public void showPref() {
        final String TAG = "showPref";
        String dbMsg = "[MainActivity]";
        try {
            dbMsg += "MyPreference読込み";
            Intent intentPRF = new Intent(MainActivity.this,MyPreferences.class);			//プリファレンス
            startActivity(intentPRF);
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    /**
     * このアプリケーションの終了動作
     * */
    public void quitMe() {
        final String TAG = "quitMe";
        String dbMsg = "[MainActivity]";
        try {
            this.finish();
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                finishAndRemoveTask();                      //アプリケーションのタスクを消去する事でデバッガーも停止する。
            } else {
                moveTaskToBack(true);                       //ホームボタン相当でアプリケーション全体が中断状態
            }

            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    /**
     * 指定されたKeyのStringPreferenceを作成/更新
     * */
    public void setStrPref(String key,String wStr) {
        //テキスト変更後
        final String TAG = "setStrPref";
        String dbMsg = "[TargetPlanFragment]";
        try {
            dbMsg += "key=" + key;
            dbMsg += ",wStr=" + wStr;
            if(sharedPref == null){
                sharedPref = PreferenceManager.getDefaultSharedPreferences(this);            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            }
            if(myEditor == null){
                myEditor = sharedPref.edit();
            }
            myEditor.putString(key, wStr);
            boolean ret = myEditor.commit();
            dbMsg += ",commit=" + ret;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     * 指定されたKeyのBooleanPreferenceを作成/更新
     * */
    public void setBoolPref(String key,Boolean wBool) {
        //テキスト変更後
        final String TAG = "setBoolPref";
        String dbMsg = "[MainActivity]";
        try {
            dbMsg += "key=" + key;
            dbMsg += ",wBool=" + wBool;
            if(sharedPref == null){
                sharedPref = PreferenceManager.getDefaultSharedPreferences(this);            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            }
            if(myEditor == null){
                myEditor = sharedPref.edit();
            }
            myEditor.putBoolean(key, wBool);
            boolean ret = myEditor.commit();
            dbMsg += ",commit=" + ret;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    public String timeSeparator= " : ";
    public String prefName;
    public String prefValue;
    public Button targetButton;

    public void setButtonText( Button tButton,String value) {
        //テキスト変更後
        final String TAG = "setButtonText";
        String dbMsg = "[MainActivity]";
        try {
            dbMsg += "tButton=" + tButton.getTransitionName();
            dbMsg += ",value=" + tButton.getText();
            dbMsg += ",>>" + value;
            tButton.setText(value);
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }


    /**
     * タイムピッカー表示
     * */
    public void showTimePicker(String prefName, String prefValue , Button tButton) {
        final String TAG = "showTimePicer";
        String dbMsg = "[MainActivity]";
        try {
            this.prefName = prefName;
            this.prefValue =prefValue;
            this.targetButton = tButton;
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            dbMsg += "既存値="+prefValue;
            if(prefValue.contains(timeSeparator)){
                String[] tStr = prefValue.split(timeSeparator);
                hour = Integer.parseInt(tStr[0]);
                minute = Integer.parseInt(tStr[1]);
            }
            this.prefValue =prefValue;

            TimePickerDialog dialog = new TimePickerDialog(
                    this,
                    new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //                    final String TAG = "onTextChanged";
                            final String TAG = "onTimeSet";
                            String dbMsg = "[showTimePicer]";
                            try {
                                dbMsg += "アラーム時刻1="+ ","+ hourOfDay + " : " + minute;
                                MainActivity.this.prefValue = "";
                                if(hourOfDay < 10){
                                    MainActivity.this.prefValue= "0" + hourOfDay + MainActivity.this.timeSeparator;
                                }else{
                                    MainActivity.this.prefValue= hourOfDay + MainActivity.this.timeSeparator;
                                }
                                if(minute < 10){
                                    MainActivity.this.prefValue += "0" + minute;
                                }else{
                                    MainActivity.this.prefValue+= minute + "";
                                }
                                dbMsg += " >> "+ MainActivity.this.prefValue;
                                setStrPref(MainActivity.this.prefName,MainActivity.this.prefValue);
                                MainActivity.this.targetButton.setText(MainActivity.this.prefValue);

                                myLog(TAG , dbMsg);
                            } catch (Exception er) {
                                myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                            }
                            //    Log.d(“test”,String.format(“%02d:%02d”, hourOfDay,minute));
                        }
                    },
                    hour,minute,true);
            dialog.show();
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    private class soundItem{
        String uriPrefix;
        String index;
        String title;    // 着信音などの名前
        String uri;
    }

    public ArrayList<soundItem> soundItemArrayList;
    public ArrayList<String> soundNameList;
    private ArrayList<soundItem> loadAlarms() {
        final String TAG = "loadAlarms";
        String dbMsg = "[MainActivity]";

        try {
            soundItemArrayList = new  ArrayList<soundItem>();
            soundNameList = new  ArrayList<String>();
            RingtoneManager manager = new RingtoneManager(this); // マネージャを作成
            manager.setType(RingtoneManager.TYPE_ALARM);              // アラーム音だけ
// manager.setType(RingtoneManager.TYPE_RINGTONE);      // 着信音だけ
// manager.setType(RingtoneManager.TYPE_ALARM);         // アラーム音だけ
// manager.setType(RingtoneManager.TYPE_NOTIFICATION);  // 通知音だけ
       //     RingtoneManager.TYPE_ALL);              // 着信音・アラーム音・通知音の全部

//カーソルを取得して、moveToNextしていく
            Cursor cursor = manager.getCursor();
            dbMsg += cursor.getCount()+"件=" ;
            while (cursor.moveToNext()) {
                soundItem item = new soundItem();
                item.index = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
                dbMsg += "\n[" + item.index +"]" ;
                item.title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);    // 着信音などの名前
                dbMsg += item.title ;
                item.uriPrefix = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
                item.uri = item.uriPrefix + "/" + item.index;                                   // ※URIはuriPrefixとindexをつなげる必要あり
                dbMsg += " : " + item.uri ;
                soundNameList.add(item.title);
            }
            dbMsg += "\n"+ soundItemArrayList.size() +"件=" ;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return soundItemArrayList;
    }


    //ライフサイクル//////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String TAG = "onCreate";
        String dbMsg = "[MainActivity]";
        try {

            myPref = new MyPreferences();
            myPref.readPref(this);
            this.calenderAccount=myPref.calenderAccount;
            dbMsg += "," + getResources().getString(R.string.pref_calender_account) +"=" + calenderAccount ;
            this.sundayBackground=myPref.sundayBackground;
            dbMsg += "," + getResources().getString(R.string.pref_sunday_background) +"=" + sundayBackground ;
            this.sundayTextColor=myPref.sundayTextColor;
            dbMsg += "," + getResources().getString(R.string.pref_sunday_text_color) +"=" + sundayTextColor ;
            this.satudayBackground=myPref.satudayBackground;
            dbMsg += "," + getResources().getString(R.string.pref_satuday_background) +"=" + satudayBackground ;
            this.satudayTextColor=myPref.satudayTextColor;
            dbMsg += "," + getResources().getString(R.string.pref_satuday_background) +"=" + satudayTextColor ;
            this.defaultBackground = myPref.defaultBackground;
            dbMsg += "," + getResources().getString(R.string.pref_default_background) +"=" + defaultBackground ;
            this.defaultTextColor = myPref.defaultTextColor;
            dbMsg += "," + getResources().getString(R.string.pref_default_text_color) +"=" + defaultTextColor ;
            this.targetDays = myPref.targetDays;
            dbMsg += "," + getResources().getString(R.string.pref_tDates) +"=" + targetDays ;

            this.tEventName = myPref.tEventName;
            dbMsg += "," + getResources().getString(R.string.sett_event_name) +"=" + tEventName ;
            this.tEventSound = myPref.tEventSound;
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"=" + tEventSound ;
            if(myPref.tArarmTime1.contains(":")){
                this.tArarmTime1 = myPref.tArarmTime1;
            }
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1=" + tArarmTime1 ;
            this.ta100c = myPref.ta100c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1の日曜=" + ta100c ;
            this.ta101c = myPref.ta101c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1の月曜=" + ta101c ;
            this.ta102c = myPref.ta102c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time)  +"1の火曜=" + ta102c;
            this.ta103c = myPref.ta103c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1の水曜=" + ta103c;
            this.ta104c = myPref.ta104c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1の木曜=" + ta104c;
            this.ta105c = myPref.ta105c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1の金曜=" + ta105c;
            this.ta106c = myPref.ta106c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1の土曜=" + ta106c ;

            if(myPref.tArarmTime2.contains(":")){
                this.tArarmTime2 = myPref.tArarmTime2;
            }
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2=" + tArarmTime2 ;
            this.ta200c = myPref.ta200c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2の日曜=" + ta200c ;
            this.ta201c = myPref.ta201c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2の月曜=" + ta201c ;
            this.ta202c = myPref.ta202c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2の火曜=" + ta202c ;
            this.ta203c = myPref.ta203c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2の水曜=" + ta203c ;
            this.ta204c = myPref.ta204c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2の木曜=" + ta204c ;
            this.ta205c = myPref.ta205c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time)  +"2の金曜=" + ta205c ;
            this.ta206c = myPref.ta206c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2の土曜=" + ta206c ;

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setSupportActionBar(binding.appBarMain.toolbar);            //メニューボタン表示
            //ここから以前のやり方////////////////////////// Androidアプリにナビゲーションドロワーを実装する
            // ナビゲーションホストを取得する
            NavHostFragment navHostFragment =(NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            // navHostFragmentのナビゲーションコントローラを取得する
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//            NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
            // アップバーのコンフィグレーションをビルドする
            AppBarConfiguration appBarConfiguration =
                    new AppBarConfiguration.Builder(navController.getGraph())
                            .setOpenableLayout((DrawerLayout)findViewById(R.id.drawer_layout))
                            .build();
            // ツールバーを取得する
        //    Toolbar toolbar = findViewById(R.id.toolbar);           //
            // ナビゲーションUIをセットアップする
            NavigationUI.setupWithNavController(binding.appBarMain.toolbar, navController, appBarConfiguration);
            //////////////////////////ここから以前のやり方//
            int toolbarH = binding.appBarMain.toolbar.getHeight();
            dbMsg += ",toolbarH=" + toolbarH;
            //ドロワーからの遷移動作
            NavigationView navigationView = binding.navView;            //(NavigationView)findViewById(R.id.my_nav_view);
            NavigationUI.setupWithNavController(navigationView, navController);

            loadAlarms();

//            DrawerLayout drawer = binding.drawerLayout;
//            NavigationView navigationView = binding.navView;
//            // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
//            mAppBarConfiguration = new AppBarConfiguration.Builder(
//                    R.id.nav_home, R.id.nav_target_plan, R.id.nav_target_setting, R.id.nav_other_setting)
//                    .setOpenableLayout(drawer)
//                    .build();
//          //  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//            NavigationUI.setupWithNavController(navigationView, navController);

            binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .setAnchorView(R.id.fab).show();
                }
            });

//            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            PendingIntent pendingIntent = getPendingIntent();
//            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarmTimeMillis, null), pendingIntent);
//

            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

//    private PendingIntent getPendingIntent() {
//        PendingIntent pInt =null;
//        return pInt;
//    }

    //////////////////////////////////////////////////////////////ライフサイクル//
    public static void myLog(String TAG , String dbMsg) {
        Util UTIL = new Util();
        UTIL.myLog(TAG , dbMsg);
    }

    public static void myErrorLog(String TAG , String dbMsg) {
        Util UTIL = new Util();
        UTIL.myErrorLog(TAG , dbMsg);
    }

}