package com.hijiyam_koubou.taplans;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.hijiyam_koubou.taplans.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public MyPreferences myPref;

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

    public long alarmTimeMillis = 0;
    public AlarmManager alarmManager;
    /**
     * ツールバー右のメニューアイコンからメニューを表示
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューを拡張します。これにより、アクション バーが存在する場合に項目が追加されます。
        getMenuInflater().inflate(R.menu.main, menu);
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setSupportActionBar(binding.appBarMain.toolbar);
            binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .setAnchorView(R.id.fab).show();
                }
            });
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_target_plan, R.id.nav_target_setting, R.id.nav_other_setting)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = getPendingIntent();
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarmTimeMillis, null), pendingIntent);


            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    private PendingIntent getPendingIntent() {
        PendingIntent pInt =null;
        return pInt;
    }

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