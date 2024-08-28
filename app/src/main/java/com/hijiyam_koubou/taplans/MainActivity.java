package com.hijiyam_koubou.taplans;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.hijiyam_koubou.taplans.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.google.api.services.calendar.model.Calendar;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity  implements EasyPermissions.PermissionCallbacks {

    public MainViewModel mainViewModel;
    public MyPreferences myPref;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor myEditor;
    /**起動動作へ**/
    public static int to_wakeUp=1;

    /**読み書き可能な領域*/
    public String fileDir;              // + File.separator + wFile.getName();
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
    public String otherBackground = "#DDDDDD";            //">#FFFFFF</color>対象外の背景
    public String otherTextColor = "#222222";            //">#000000</color>対象外の文字色
    /**
     * 前後2か月の対象日リスト：Googleカレンダー非連動時の対策
     * */
    private ArrayList<Date> tDates;
    public String targetDays;
    /**アラームリスト*/
    public SharedPreferences alameListDatas;
    public SharedPreferences.Editor alEditor;
    /**アラーム用テキストファイル*/
    public File alarmFile;
    public String alFileName = "alarm_list";
    public ArrayList<AlarmItem> alarmItemList;
    public ArrayList<String> dowDisplay;
    public String alarmListFileName;           // = fileDir + File.separator + "taplans_alarm_list";
    public ArrayList<String> targetWakeUpTime;
    public ArrayList<String> otherWakeUpTime;

    public ByDayOfTheWeekStr tBDQTWeek;
    public ByDayOfTheWeekStr ohBDQTWeek;
    /**
     * 設定する予定
     * */
    public String tEventName;             //  予定の名称
    public String tEventSoundName;           // アラーム名
    public String tEventSoundURi;           // アラームURI

    public String tArarmTime1 = "06 : 00";           // アラーム時刻1
    public Boolean ta100c = false;               // アラーム時刻1 の日曜
    public Boolean ta101c = false;          // アラーム時刻1 の月曜
    public Boolean ta102c = false;        // アラーム時刻1 の火曜
    public Boolean ta103c = false;         // アラーム時刻1 の水曜
    public Boolean ta104c = false;           // アラーム時刻1 の木曜
    public Boolean ta105c = false;            // アラーム時刻1 の金曜
    public Boolean ta106c = false;            // アラーム時刻1 の土曜
    public Boolean ta107c = false;            // アラーム時刻1 の祝日

    public String tArarmTime2 = "07 : 00";           // アラーム時刻2
    public Boolean ta200c = false;             // アラーム時刻2 の日曜
    public Boolean ta201c = false;            // アラーム時刻2 の月曜
    public Boolean ta202c = false;            // アラーム時刻2 の火曜
    public Boolean ta203c = false;               // アラーム時刻2 の水曜
    public Boolean ta204c = false;              // アラーム時刻2 の木曜
    public Boolean ta205c = false;          // アラーム時刻2 の金曜
    public Boolean ta206c = false;              // アラーム時刻2 の土曜
    public Boolean ta207c = false;            // アラーム時刻2 の祝日

    public String tArarmTime3 = "08 : 00";           // アラーム時刻3
    public Boolean ta300c = false;             // アラーム時刻3 の日曜
    public Boolean ta301c = false;            // アラーム時刻3 の月曜
    public Boolean ta302c = false;            // アラーム時刻3 の火曜
    public Boolean ta303c = false;               // アラーム時刻3の水曜
    public Boolean ta304c = false;              // アラーム時刻3 の木曜
    public Boolean ta305c = false;          // アラーム時刻3 の金曜
    public Boolean ta306c = false;              // アラーム時刻3 の土曜
    public Boolean ta307c = false;            // アラーム時刻3 の祝日

    // 設定予定以外の日
    public String ohEventName;             //  予定の名称
    public String ohEventSoundName;           // アラーム名
    public String ohEventSoundURi;           // アラームURI

    public String ohArarmTime1 = "06 : 30";           // アラーム時刻1
    public Boolean oh100c = false;               // アラーム時刻1 の日曜
    public Boolean oh101c = false;          // アラーム時刻1 の月曜
    public Boolean oh102c = false;        // アラーム時刻1 の火曜
    public Boolean oh103c = false;         // アラーム時刻1 の水曜
    public Boolean oh104c = false;           // アラーム時刻1 の木曜
    public Boolean oh105c = false;            // アラーム時刻1 の金曜
    public Boolean oh106c = false;            // アラーム時刻1 の土曜
    public Boolean oh107c = false;            // アラーム時刻1 の祝日

    public String ohArarmTime2 = "07 : 30";           // アラーム時刻2
    public Boolean oh200c = false;             // アラーム時刻2 の日曜
    public Boolean oh201c = false;            // アラーム時刻2 の月曜
    public Boolean oh202c = false;            // アラーム時刻2 の火曜
    public Boolean oh203c = false;               // アラーム時刻2 の水曜
    public Boolean oh204c = false;              // アラーム時刻2 の木曜
    public Boolean oh205c = false;          // アラーム時刻2 の金曜
    public Boolean oh206c = false;              // アラーム時刻2 の土曜
    public Boolean oh207c = false;            // アラーム時刻2 の祝日

    public String ohArarmTime3 = "08 : 30";           // アラーム時刻3
    public Boolean oh300c = false;             // アラーム時刻3 の日曜
    public Boolean oh301c = false;            // アラーム時刻3 の月曜
    public Boolean oh302c = false;            // アラーム時刻3 の火曜
    public Boolean oh303c = false;               // アラーム時刻3の水曜
    public Boolean oh304c = false;              // アラーム時刻3 の木曜
    public Boolean oh305c = false;          // アラーム時刻3 の金曜
    public Boolean oh306c = false;              // アラーム時刻3 の土曜
    public Boolean oh307c = false;            // アラーム時刻3 の祝日

    //Googleカレンダー連携
    public Boolean ohGCAlignment;      //Googleカレンダー連携
    public String ohGCSubject;         //登録する名称"
    public String ohGCStarttime = "09 : 00";           //開始時刻
    public String ohGCEndtime = "18 : 00";          //終了時刻
    public Boolean ohGCEnddate;            //終日
    public String ohGCDescription;             //説明・メモ
    public String ohGCLocation;            //予定の場所
    public String ohGCColorName;           // 予定の色名称
    public int ohGCColorRss;           // 予定の色リソースID


    public String timeSeparator= " : ";
    public String prefName;
    public String prefValue;
    public Button targetButton;

    //Googleカレンダー連携
    public Boolean googleCalAlignment;      //Googleカレンダー連携
    public String gcaSubject;         //登録する名称"
    public String gcaStarttime = "09 : 00";           //開始時刻
    public String gcaEndtime = "18 : 00";          //終了時刻
    public Boolean gcaEnddate;            //終日
    public String gcaDescription;             //説明・メモ
    public String gcaLocation;            //予定の場所
    public String tCColorName;           // 予定の色名称
    public int tCColorRss;           // 予定の色リソースID
//    public Boolean gcaIsPrivate;            //予定を限定公開にする

    public ArrayList<GoogleCalendarColors> googleCalendarColorList;
    public ArrayList<String> colorNameList;

    /*
    * Android から"Google Calendar API"を使って、Googleカレンダーに新規カレンダーを追加する      https://qiita.com/couzie/items/ce8f7780f9a722b2a87d
    * https://developers.google.com/calendar/api/v3/reference/acl?hl=ja
    * */


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
            alEditor.apply();
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     * 指定されたKeyのIntPreferenceを作成/更新
     * */
    public void setIntPref(String key,int wInt) {
        //テキスト変更後
        final String TAG = "setIntPref";
        String dbMsg = "[TargetPlanFragment]";
        try {
            dbMsg += "key=" + key;
            dbMsg += ",wStr=" + wInt;
            if(sharedPref == null){
                sharedPref = PreferenceManager.getDefaultSharedPreferences(this);            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            }
            if(myEditor == null){
                myEditor = sharedPref.edit();
            }
            myEditor.putInt(key, wInt);
            boolean ret = myEditor.commit();
            dbMsg += ",commit=" + ret;
            alEditor.apply();
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
            alEditor.apply();
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

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
                                if(!prefName.isEmpty()){
                                    dbMsg += " >prefName> "+ MainActivity.this.prefName + "へ記録";
                                    setStrPref(MainActivity.this.prefName,MainActivity.this.prefValue);
                                }
                                if(targetButton != null){
                                    MainActivity.this.targetButton.setText(MainActivity.this.prefValue);
                                }
                                myLog(TAG , dbMsg);
                            } catch (Exception er) {
                                myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                            }
                        }
                    },
                    hour,minute,true);
            dialog.show();
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    public ArrayList<soundItem> soundItemArrayList;
    public ArrayList<String> soundNameList;
    /**端末に登録してあるアラーム音をリストアップ*/
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
                soundItemArrayList.add(item);
                soundNameList.add(item.title);
            }
            dbMsg += "\n"+ soundItemArrayList.size() +"件=" ;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return soundItemArrayList;
    }

    /**
     * 指定されたポジションに登録されているサウンドるソースの情報を返す
     * */
    public soundItem getAlarmsItem(int position) {
        final String TAG = "getAlarmsItem";
        String dbMsg = "[MainActivity]";
        soundItem selItem=null;
        try {
            selItem = soundItemArrayList.get(position);
            dbMsg += "[" + selItem.index+"]" ;
            dbMsg += selItem.uri;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return selItem;
    }

    /**
     * Spinnerで選択したアイテムからアラーム音を設定する
     * */
    public  soundItem setSoundItem(int position,String prefName,String prefURi) {
        final String TAG = "setSoundItem";
        String dbMsg = "[MainActivity]";
        soundItem selItem=null;
        try {
            dbMsg +=  position+"番目のアイテムを" + prefName + "へ";
            selItem = soundItemArrayList.get(position);
            dbMsg += ",名称＝" + selItem.title ;
            setStrPref(prefName,(String) selItem.title);
            if(prefName.equals("tEventSoundName")){
                dbMsg += ",設定側";
                tEventSoundName = (String) selItem.title;
            }else if(prefName.equals("ohEventSoundName")){
                dbMsg += ",未設定側";
                ohEventSoundName = (String) selItem.title;
            }
            dbMsg += "[" + selItem.index+"]" ;
            dbMsg += selItem.uri;
            dbMsg += ">>URI=" + tEventSoundURi ;         //"," + getActivity().getString(R.string.set_alarm_sound) +
            setStrPref(prefURi,selItem.uri);
            if(prefURi.equals("tEventSoundURi")){
                tEventSoundURi = selItem.uri;            //selItem.getClass().getField("uri").toString();
            }else if(prefURi.equals("ohEventSoundURi")){
                ohEventSoundURi = selItem.uri;
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return selItem;
    }

    /**
     * 対象/非対称設定画面の曜日チェックボックスのトグル
     * */
    public void setWeekCheck(CheckBox targetCB,Boolean checkState,String prefName ,
                             CheckBox cBox2, String prefName2,
                             CheckBox cBox3, String prefName3
                             ) {
        final String TAG = "setWeekCheck";
        String dbMsg = "[MainActivity]";
        try {
            dbMsg += "targetCB=" + targetCB.getAccessibilityClassName();
            dbMsg += ">>" + checkState;
            setBoolPref(prefName,checkState);
            dbMsg += "," + prefName + "を";
            if(checkState) {
                dbMsg += "設定";
            //    Boolean cBox2SVal = cBox2.isChecked();
                if(cBox2.isChecked()) {
                    dbMsg += "," + prefName2;
                    //              pClass.ta200c =false;
                    cBox2.setChecked(false);
                    setBoolPref(prefName2,false);
                    dbMsg += "を解除";
                }
                if(cBox3.isChecked()) {
                    dbMsg += "と" + prefName2;
                    //              pClass.ta200c =false;
                    cBox3.setChecked(false);
                    setBoolPref(prefName3,false);
                    dbMsg += "を解除";
                }
                String boxNoStr = prefName.substring(2, prefName.length() - 1);
                dbMsg += ",boxNoStr=" + boxNoStr;
                int dowInt = Integer.parseInt(prefName.substring(prefName.length() - 2, prefName.length() - 1));
                if(dowInt == 7){
                    dowInt = -1;
                }
                dbMsg += ",dowInt=" + dowInt;
                dbMsg += "=" +dowDisplay.get(dowInt+1) + "曜日";

                String setTimeStr = tArarmTime1;
                if(prefName.startsWith("ta")){              //ta100c
                    dbMsg += "\n対象側";
                    if(boxNoStr.startsWith("10")) {
                        setTimeStr = tArarmTime1;
                    }else if(boxNoStr.startsWith("20")){
                        setTimeStr = tArarmTime2;
                    }else if(boxNoStr.startsWith("30")){
                        setTimeStr = tArarmTime3;
                    }
                    targetWakeUpTime.set(dowInt+1,setTimeStr);
                    String targetWakeUpTimeStr = targetWakeUpTime.toString();
                    dbMsg += ",targetWakeUpTime=" + targetWakeUpTimeStr;
                    setStrPref("targetWakeUpTime", targetWakeUpTimeStr);
                }else if(prefName.startsWith("oh")){        //oh100c
                    dbMsg += "\n非対象側";
                    if(boxNoStr.startsWith("10")) {
                        setTimeStr = ohArarmTime1;
                    }else if(boxNoStr.startsWith("20")){
                        setTimeStr = ohArarmTime2;
                    }else if(boxNoStr.startsWith("30")){
                        setTimeStr = ohArarmTime3;
                    }
                    otherWakeUpTime.set(dowInt+1,setTimeStr);
                    String otherWakeUpTimeStr = otherWakeUpTime.toString();
                    dbMsg += ",otherWakeUpTime=" + otherWakeUpTimeStr;
                    setStrPref("otherWakeUpTimeStr", otherWakeUpTimeStr);
                }
                dbMsg += ",setTimeStr=" + setTimeStr;

                //      myPref.readPref(this);
            }else {
                dbMsg += "解除";
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     *　曜日ごとの起床時刻
     * */
    public void setWeakUpTIme(String timeStr, ByDayOfTheWeekStr hangar, String dayOfWeek ) {
        final String TAG = "setWeakUpTIme";
        String dbMsg = "[MainActivity]";
        try {
            dbMsg += hangar.toString()+ "を" + timeStr;
            dbMsg += "," + dayOfWeek;           //+ "は" + hangar.DOTWPosition.indexOf(dayOfWeek) + "番目";
        //    dbMsg += "," + dayOfWeek+ "は" + hangar.DOTWPosition.indexOf(dayOfWeek) + "番目";
            if(dayOfWeek.equals("sunday")) {
                hangar.sunday = timeStr;
                dbMsg += "日曜日";
            }else if(dayOfWeek.equals("monday")) {
                hangar.monday = timeStr;
                dbMsg += "月曜日";
            }else if(dayOfWeek.equals("tuesday")) {
                hangar.tuesday = timeStr;
                dbMsg += "火曜日";
            }else if(dayOfWeek.equals("wednesday")) {
                hangar.wednesday = timeStr;
                dbMsg += "水曜日";
            }else if(dayOfWeek.equals("thursday")) {
                hangar.thursday = timeStr;
                dbMsg += "木曜日";
            }else if(dayOfWeek.equals("friday")) {
                hangar.friday = timeStr;
                dbMsg += "金曜日";
            }else if(dayOfWeek.equals("saturday")) {
                hangar.saturday = timeStr;
                dbMsg += "土曜日";
            }else if(dayOfWeek.equals("holiday")) {
                hangar.holiday = timeStr;
                dbMsg += "祝日";
            }

            dbMsg += ">>"+ hangar.toString();

            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }


    /**　保存してあるアラームリストを取得する*/
    public ArrayList<AlarmItem> getAlarmList() {
        final String TAG = "getAlarmList";
        String dbMsg = "[MainActivity]";
        ArrayList<AlarmItem>retList = null;

        try {
            retList = new ArrayList<AlarmItem>();
            String fName = getPackageName() +"_"+ alFileName;
            dbMsg += ",プリファレンス=" + fName;

            if(alameListDatas == null){
//                Context storageContext = ContextCompat.createDeviceProtectedStorageContext(this);
                alameListDatas = getApplicationContext().getSharedPreferences(fName,MODE_PRIVATE);
                dbMsg += "　>　開く　>　";
               // alameListDatas=this.getSharedPreferences(fName,MODE_PRIVATE);
                Map<String, ?> inPref = alameListDatas.getAll();
                dbMsg += "=" + inPref.size() + "件" ;
                for(Map.Entry<String, ?> entry : inPref.entrySet()){
                    String keyName = entry.getKey();
                    String settingStr = entry.getValue().toString();
              //      dbMsg += "\n" + keyName + " = " + settingStr;
                }
            }
            alEditor = alameListDatas.edit();
            String alarm_list = alameListDatas.getString("alarm_list", "");
            dbMsg += ",alarm_list=" + alarm_list.length() + "文字" ;
            if(alarm_list.length()>50){
                dbMsg += alarm_list.substring(0,20) + "～" + alarm_list.substring(alarm_list.length()-20,alarm_list.length());
            }

            if(alarm_list.equals("")){
                // テキストファイル
                if(alarmFile.exists()){
                    String filename = alarmFile.getName();
                    dbMsg += "\n" + filename;
                    FileInputStream fis = this.openFileInput(filename);
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis, StandardCharsets.UTF_8);
                    StringBuilder stringBuilder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        String line = reader.readLine();            //テキスト行を読み込みます。
                        while (line != null) {
                            stringBuilder.append(line).append('\n');
                            line += reader.readLine();
                        }
                    //    dbMsg += ",line=" + line.length() + "文字" ;
                        alarm_list=line;
                    } catch (IOException e) {
                        // Error occurred when opening raw file for reading.
                    } finally {
                        String contents = stringBuilder.toString();
                        alarm_list=contents;
                    }
                    dbMsg += ">>alarm_list=" + alarm_list.length() + "文字" ;
                }else{
                    dbMsg += ",ファイル未作成" ;
                }
            }

            if(! alarm_list.equals("")){
                dbMsg += ",alarm_list=" + alarm_list.substring(0,50) + "～"+ alarm_list.substring(alarm_list.length()-50,alarm_list.length()) ;
                alarmItemList = new ArrayList<AlarmItem>() ;
                JSONArray jArray = new JSONArray(alarm_list);
                for (int i = 0; i < jArray.length(); ++ i) {
                    JSONObject jsonObj = jArray.getJSONObject(i);
                    Iterator<String> jKeys = jsonObj.keys();
                    AlarmItem alarmItem = new AlarmItem();
                    alarmItem.dateStr = jsonObj.getString("dateStr");
                    alarmItem.timeStr = jsonObj.getString("timeStr").trim();
                    alarmItem.isTarget = jsonObj.getBoolean("isTarget");
                    alarmItem.DayOfTheWeek = jsonObj.getInt("DayOfTheWeek");
                    dbMsg += "\n[" +alarmItemList.size() + "]"+ alarmItem.dateStr+" " + alarmItem.timeStr+";" + alarmItem.isTarget+";" + dowDisplay.get(alarmItem.DayOfTheWeek);
                    boolean isRepair = false;
                    if(! jsonObj.isNull("comment")){
                        alarmItem.comment = jsonObj.getString("comment");
                        dbMsg += ",comment=" + alarmItem.comment;
                        if(alarmItem.comment.isEmpty()){
                            isRepair = true;
                        }
                    }else{
                        isRepair = true;
                    }
                    if(isRepair){
                        dbMsg += ",isTarget="+alarmItem.isTarget;
                        if(alarmItem.isTarget){
                            alarmItem.timeStr = targetWakeUpTime.get(alarmItem.DayOfTheWeek);
                            alarmItem.comment = tEventName;
                        }else{
                            alarmItem.timeStr = otherWakeUpTime.get(alarmItem.DayOfTheWeek);
                            alarmItem.comment = ohEventName;
                        }
                        dbMsg += ",timeStr="+alarmItem.timeStr+ ",comment="+alarmItem.comment;
                    }

                    if(! jsonObj.isNull("isNotChangeable")){
                        alarmItem.isNotChangeable = jsonObj.getBoolean("isNotChangeable");
                        dbMsg += ",isNotChangeable=" + alarmItem.isNotChangeable;
                    }
                    alarmItemList.add(alarmItem);
                }
                dbMsg += ",alarmItemList=" + alarmItemList.size()+"件";
                mainViewModel.setList(alarmItemList);
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retList;
    }

    /**検索直後のAlarmItemインデックス*/
    public int alarmItemIndex =-1;
    /**
     * 指定された日付文字に該当するAlarmItemを返す
     * */
    public AlarmItem fromAlarmList(String dateStr) {
        final String TAG = "fromAlarmList";
        String dbMsg = "[MainActivity]";
        AlarmItem retItem = null;

        try {
            dbMsg += ",dateStr=" + dateStr;
            dbMsg += ",alarmItemList=" + alarmItemList.size()+"件";
            if(! alarmItemList.isEmpty()){
                for (int i = 0; i < alarmItemList.size(); ++ i) {
                    retItem=new AlarmItem();
                    retItem = alarmItemList.get(i);
                    if(retItem.dateStr.equals(dateStr)){
                        alarmItemIndex = i;
                        dbMsg += ",該当インデックス=" + alarmItemIndex;
                        break;
                    }else{
                        retItem=null;
                    }
                }
            }
            if(retItem !=null){
                dbMsg += "," + retItem.dateStr + " "  + retItem.timeStr + ".isTarget="  + retItem.isTarget + ".DayOfTheWeek="  + retItem.DayOfTheWeek ;
            }else{
                myLog(TAG , dbMsg);
            }
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retItem;
    }

     /**
     * 指定された日のアラームを更新
     * */
    public AlarmItem renewalAlam(AlarmItem setItem) {
        //テキスト変更後
        final String TAG = "renewalAlam";
        String dbMsg = "[MainActivity]";
        AlarmItem retItem = null;
        try {
            dbMsg += ",更新対象=" + setItem.dateStr;
            int listIndex = 0;
            for (AlarmItem rItem:alarmItemList) {
                if(rItem.dateStr.equals(setItem.dateStr)){
                    break;
                }
                listIndex++;
            }
            dbMsg += "("+listIndex+")";
            AlarmItem oldItem=alarmItemList.get(listIndex);
            dbMsg += oldItem.dateStr + " " + oldItem.timeStr + "=" +  oldItem.isTarget + ":" + oldItem.comment + ",isNotChangeable=" + oldItem.isNotChangeable;
            if(! oldItem.isNotChangeable){
                alarmItemList.set(listIndex,setItem);
            }
            retItem = alarmItemList.get(listIndex);
            dbMsg += "\n" + retItem.dateStr + " " + retItem.timeStr + ":" + retItem.comment + ":" + retItem.isNotChangeable;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retItem;
    }


    /**
     * 本日以降のアラームを一括更新
     * */
    public void renewalAllAlam() {
        //テキスト変更後
        final String TAG = "renewalAllAlam";
        String dbMsg = "[MainActivity]";
        try {
            //現在
            Date toDay = new Date();
            dbMsg += ",本日=" + toDay;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String toDayStr = dateFormat.format(toDay);
            dbMsg += ",toDayStr=" + toDayStr;
            int listIndex = 0;
            for (AlarmItem rItem:alarmItemList) {
                if(rItem.dateStr.equals(toDayStr)){
                    break;
                }
                listIndex++;
            }
            dbMsg += "("+listIndex+")";
            String listEnd = alarmItemList.get(alarmItemList.size() - 1).dateStr;
            dbMsg += "～"+listEnd;

            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }


    @SuppressLint("WorldReadableFiles")
    public void saveAlamList(String wStr) {
        //テキスト変更後
        final String TAG = "saveAlamList";
        String dbMsg = "[MainActivity]";
        try {
            dbMsg += ",wStr=" + wStr.substring(0,50) +"～"+wStr.substring(wStr.length()-70,wStr.length())+"\n";
            String fName = getPackageName() + "_" + alFileName;
            dbMsg += ",fName=" + fName;
            if(alameListDatas == null){
//                Context storageContext = ContextCompat.createDeviceProtectedStorageContext(this);
                alameListDatas = getApplicationContext().getSharedPreferences(fName, MODE_PRIVATE);
//                alameListDatas=this.getSharedPreferences(fName, MODE_PRIVATE);
                dbMsg += "読み出し";
            }
            if(alEditor == null){
                alEditor = alameListDatas.edit();
            }
            Map<String, ?> inPref = alameListDatas.getAll();
            dbMsg += ",該当Pref　commit前=" + inPref.size() + "件" ;
            alEditor.putString("alarm_list", wStr);
            boolean ret = alEditor.commit();
            alEditor.apply();
            dbMsg += ",commit=" + ret;
            inPref = alameListDatas.getAll();
            dbMsg += "=" + inPref.size() + "件" ;
            //このエディターから編集中の SharedPreferences オブジェクトに設定の変更をコミットします。これにより、要求された変更がアトミックに実行され、SharedPreferences に現在あるものがすべて置き換えられます。

         //   if(alarmFile.exists()){
                dbMsg += "\nテキスト更新前=" + alarmFile.length();
                String filename = alarmFile.getName();
                dbMsg += ",filename=" + filename;
                try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
                    fos.write(wStr.getBytes());     //toByteArray()
                    dbMsg += "＞＞" + alarmFile.length();
                } catch (IOException er) {
                    myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                }

         //   }

            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    //Googleカレンダー//////////////////////////////////////////////////////////////
     private TextView mOutputText;
    private Button mCallApiButton;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String BUTTON_TEXT = "Call Google Calendar API";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {Manifest.permission_group.CALENDAR};


    GoogleAccountCredential mCredential;

    /**
     * Google Calendar API の呼び出しの事前条件を確認し、条件を満たしていればAPIを呼び出す。
     *
     * 事前条件：
     * - 有効な Google Play Services がインストールされていること
     * - 有効な Google アカウントが選択されていること
     * - 端末がインターネット接続可能であること
     *
     * 事前条件を満たしていない場合には、ユーザーに説明を表示する。
     */
    private void getResultsFromApi() throws GeneralSecurityException, IOException {
        if (!isGooglePlayServicesAvailable()) {
            // Google Play Services が無効な場合
            acquireGooglePlayServices();
        }
        else if (mCredential.getSelectedAccountName() == null) {
            // 有効な Google アカウントが選択されていない場合
            chooseAccount();
        }
        else if (!isDeviceOnline()) {
            // 端末がインターネットに接続されていない場合
            mOutputText.setText("No network connection available.");
        }
        else {
            new MakeRequestTask(mCredential).execute();
        }
    }

    /**
     * 端末に Google Play Services がインストールされ、アップデートされているか否かを確認する。
     *
     * @return 利用可能な Google Play Services がインストールされ、アップデートされている場合にはtrueを、
     * そうでない場合にはfalseを返す。
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }


    /**
     * ユーザーにダイアログを表示して、Google Play Services を利用可能な状態に設定するように促す。
     * ただし、ユーザーが解決できないようなエラーの場合には、ダイアログを表示しない。
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /**
     * 有効な Google Play Services が見つからないことをエラーダイアログで表示する。
     *
     * @param connectionStatusCode Google Play Services が無効であることを示すコード
     */
    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                MainActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES
        );
        dialog.show();
    }

    /**
     * Google　Calendar API の認証情報を使用するGoogleアカウントを設定する。
     *
     * 既にGoogleアカウント名が保存されていればそれを使用し、保存されていなければ、
     * Googleアカウントの選択ダイアログを表示する。
     *
     * 認証情報を用いたGoogleアカウントの設定には、"GET_ACCOUNTS"パーミッションを
     * 必要とするため、必要に応じてユーザーに"GET_ACCOUNTS"パーミッションを要求する
     * ダイアログが表示する。
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() throws GeneralSecurityException, IOException {
        // "GET_ACCOUNTS"パーミッションを取得済みか確認する
        if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
            // SharedPreferencesから保存済みGoogleアカウントを取得する
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Googleアカウントの選択を表示する
                // GoogleAccountCredentialのアカウント選択画面を使用する
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        }
        else {
            // ダイアログを表示して、ユーザーに"GET_ACCOUNTS"パーミッションを要求する
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * 非同期で　Google Calendar API の呼び出しを行うクラス。
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, String> {

        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) throws GeneralSecurityException, IOException {
           // HttpTransport transport = AndroidHttp.newCompatibleTransport();           //org
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar
                    .Builder(transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Google Calendar API を呼び出すバックグラウンド処理。
         *
         * @param params 引数は不要
         */
        @Override
        protected String doInBackground(Void... params) {
            try {
                return createCalendar();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * 選択されたGoogleアカウントに対して、新規にカレンダーを追加する。
         *
         * @return 作成したカレンダーのID
         * @throws IOException
         */
        private String createCalendar() throws IOException {
            // 新規にカレンダーを作成する
            com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
            // カレンダーにタイトルを設定する
            calendar.setSummary("CalendarTitle");
            // カレンダーにタイムゾーンを設定する
            calendar.setTimeZone("Asia/Tokyo");

            // 作成したカレンダーをGoogleカレンダーに追加する
            com.google.api.services.calendar.model.Calendar createdCalendar = mService.calendars().insert(calendar).execute();
            String calendarId = createdCalendar.getId();

            // カレンダー一覧から新規に作成したカレンダーのエントリを取得する
            CalendarListEntry calendarListEntry = mService.calendarList().get(calendarId).execute();

            // カレンダーのデフォルトの背景色を設定する
            calendarListEntry.setBackgroundColor("#ff0000");

            // カレンダーのデフォルトの背景色をGoogleカレンダーに反映させる
            CalendarListEntry updatedCalendarListEntry =
                    mService.calendarList()
                            .update(calendarListEntry.getId(), calendarListEntry)
                            .setColorRgbFormat(true)
                            .execute();

            // 新規に作成したカレンダーのIDを返却する
            return calendarId;
        }

        @Override
        protected void onPreExecute() {
            mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(String output) {
            mProgress.hide();
            if (output == null || output.isEmpty()) {
                mOutputText.setText("No results returned.");
            } else {
                mOutputText.setText("Calendar created using the Google Calendar API: " + output);
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("The following error occurred:\n" + mLastError.getMessage());
                }
            } else {
                mOutputText.setText("Request cancelled.");
            }
        }
    }


    /**
     * アカウント選択や認証など、呼び出し先のActivityから戻ってきた際に呼び出される。
     *
     * @param requestCode Activityの呼び出し時に指定したコード
     * @param resultCode  呼び出し先のActivityでの処理結果を表すコード
     * @param data        呼び出し先のActivityでの処理結果のデータ
     */
    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    try {
                        getResultsFromApi();
                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;

            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                  //  AccountManager am = AccountManager.get(this);
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        try {
                            getResultsFromApi();
                        } catch (GeneralSecurityException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                break;

            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    try {
                        getResultsFromApi();
                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
        }
    }

    /**
     * Android 6.0 (API 23) 以降にて、実行時にパーミッションを要求した際の結果を受け取る。
     *
     * @param requestCode  requestPermissions(android.app.Activity, String, int, String[])
     *                     を呼び出した際に渡した　request code
     * @param permissions  要求したパーミッションの一覧
     * @param grantResults 要求したパーミッションに対する承諾結果の配列
     *                     PERMISSION_GRANTED または PERMISSION_DENIED　が格納される。
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 現在、端末がネットワークに接続されているかを確認する。
     *
     * @return ネットワークに接続されている場合にはtrueを、そうでない場合にはfalseを返す。
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * 要求したパーミッションがユーザーに承諾された際に、EasyPermissionsライブラリから呼び出される。
     *
     * @param requestCode 要求したパーミッションに関連した request code
     * @param list        要求したパーミッションのリスト
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // 何もしない
    }

    /**
     * 要求したパーミッションがユーザーに拒否された際に、EasyPermissionsライブラリから呼び出される。
     *
     * @param requestCode 要求したパーミッションに関連した request code
     * @param list        要求したパーミッションのリスト
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // 何もしない
    }



    /**
     * Googleカレンダーの色設定リストを作成する
     * */
    public int setGoogleCalendarColors() {
        //テキスト変更後
        final String TAG = "setGoogleCalendarColors";
        String dbMsg = "[MainActivity]";
        int lastIndex=0;
        try {
            googleCalendarColorList = new  ArrayList<GoogleCalendarColors>();
            colorNameList = new  ArrayList<String>();

            GoogleCalendarColors GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 0;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_base);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_base);
            GoogleCalendarColor.HEXStr = "#FFFFFF";
            GoogleCalendarColor.RGBStr = "255,255,255";
            GoogleCalendarColor.FontColorStr = "#000000";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 1;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_lavender);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_lavender);
            GoogleCalendarColor.HEXStr = "#7986CB";
            GoogleCalendarColor.RGBStr = "121, 134, 203";
            GoogleCalendarColor.FontColorStr = "#FFFFFFFF";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 2;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_sage);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_sage);
            GoogleCalendarColor.HEXStr = "#33B679";
            GoogleCalendarColor.RGBStr = "51, 182, 121";
            GoogleCalendarColor.FontColorStr = "#FF000000";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 3;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_grapes);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_grapes);
            GoogleCalendarColor.HEXStr = "#8E24AA";
            GoogleCalendarColor.RGBStr = "142, 36, 170";
            GoogleCalendarColor.FontColorStr = "#FFFFFFFF";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 4;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_flamingo);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_flamingo);
            GoogleCalendarColor.HEXStr = "#E67C73";
            GoogleCalendarColor.RGBStr = "230, 124, 115";
            GoogleCalendarColor.FontColorStr = "#FF000000";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 5;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_banana);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_banana);
            GoogleCalendarColor.HEXStr = "#F6BF26";
            GoogleCalendarColor.RGBStr = "246, 191, 38";
            GoogleCalendarColor.FontColorStr = "#FF000000";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 6;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_orange);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_orange);
            GoogleCalendarColor.HEXStr = "#F4511E";
            GoogleCalendarColor.RGBStr = "244, 81, 30";
            GoogleCalendarColor.FontColorStr = "#FF000000";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 7;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_peacock);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_peacock);
            GoogleCalendarColor.HEXStr = "#039BE5";
            GoogleCalendarColor.RGBStr = "3, 155, 229";
            GoogleCalendarColor.FontColorStr = "#FFFFFFFF";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 8;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_graphite);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_graphite);
            GoogleCalendarColor.HEXStr = "#616161";
            GoogleCalendarColor.RGBStr = "97, 97, 97";
            GoogleCalendarColor.FontColorStr = "#FFFFFFFF";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 9;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_blueberry);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_blueberry);
            GoogleCalendarColor.HEXStr = "#3F51B5";
            GoogleCalendarColor.RGBStr = "63, 81, 181";
            GoogleCalendarColor.FontColorStr = "#FFFFFFFF";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 10;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_basil);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_basil);
            GoogleCalendarColor.HEXStr = "#0B8043";
            GoogleCalendarColor.RGBStr = "11, 128, 67";
            GoogleCalendarColor.FontColorStr = "#FFFFFFFF";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);

            GoogleCalendarColor = new GoogleCalendarColors();
            GoogleCalendarColor.colorId = 11;
            GoogleCalendarColor.colorName = getResources().getString(R.string.gcc_tomato);
            GoogleCalendarColor.colorResId = getColor(R.color.gcc_tomato);
            GoogleCalendarColor.HEXStr = "#D50000";
            GoogleCalendarColor.RGBStr = "213, 0, 0";
            GoogleCalendarColor.FontColorStr = "#FFFFFFFF";
            googleCalendarColorList.add(GoogleCalendarColor);
            colorNameList.add(GoogleCalendarColor.colorName);
            lastIndex=googleCalendarColorList.size();
            dbMsg += "googleCalendarColorList=" + lastIndex + "件";
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return lastIndex;
    }
    //ライフサイクル////////////////////////////////////////////////////////////Googleカレンダー//

    protected void wakeUp() {
        final String TAG = "wakeUp";
        String dbMsg = "[MainActivity]";
        try {

            setGoogleCalendarColors();
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
            this.tEventSoundName = myPref.tEventSoundName;
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"名称=" + tEventSoundName ;
            this.tEventSoundURi = myPref.tEventSoundURi;
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + tEventSoundURi ;
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
            this.ta107c = myPref.ta107c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"1の祝日=" + ta107c ;

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
            this.ta207c = myPref.ta207c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"2の祝日=" + ta207c ;

            if(myPref.tArarmTime3.contains(":")){
                this.tArarmTime3 = myPref.tArarmTime3;
            }
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3=" + tArarmTime3 ;
            this.ta300c = myPref.ta300c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3の日曜=" + ta300c ;
            this.ta301c = myPref.ta301c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3の月曜=" + ta301c ;
            this.ta302c = myPref.ta302c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3の火曜=" + ta302c ;
            this.ta303c = myPref.ta303c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3の水曜=" + ta303c ;
            this.ta304c = myPref.ta304c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3の木曜=" + ta304c ;
            this.ta305c = myPref.ta305c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time)  +"3の金曜=" + ta305c ;
            this.ta306c = myPref.ta306c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3の土曜=" + ta306c ;
            this.ta307c = myPref.ta307c;
            dbMsg += "," + getResources().getString(R.string.sett_alarm_time) +"3の祝日=" + ta307c ;

            //Googleカレンダー連携
            this.googleCalAlignment = myPref.googleCalAlignment;
            dbMsg += ",Googleカレンダー連携=" + googleCalAlignment ;
            this.gcaSubject = myPref.gcaSubject;
            dbMsg += ",登録する名称=" + gcaSubject ;
            this.gcaStarttime = myPref.gcaStarttime;
            dbMsg += ",開始時刻=" + gcaStarttime ;
            this.gcaEndtime = myPref.gcaEndtime;
            dbMsg += ",終了時刻=" + gcaEndtime ;
            this.gcaEnddate = myPref.gcaEnddate;
            dbMsg += ",終日=" + gcaEnddate ;
            this.gcaDescription = myPref.gcaDescription;
            dbMsg += ",説明・メモ=" + gcaDescription ;
            this.gcaLocation = myPref.gcaLocation;
            dbMsg += ",予定の場所=" + gcaLocation ;
            this.tCColorName = myPref.tCColorName;
            dbMsg += ",予定の色名称=" + tCColorName ;
            this.tCColorRss = myPref.tCColorRss;
            dbMsg += ",予定の色リソースID=" + tCColorRss ;
//            this.gcaIsPrivate = myPref.gcaIsPrivate;
//            dbMsg += ",予定を限定公開にする=" + gcaIsPrivate ;
            // 設定予定以外の日
            this.ohEventName = myPref.ohEventName;
            dbMsg += ",設定予定以外の名称=" + ohEventName ;
            this.ohEventSoundName = myPref.ohEventSoundName;
            dbMsg += ",アラーム名=" + ohEventSoundName ;
            this.ohEventSoundURi = myPref.ohEventSoundURi;
            dbMsg += ",アラームURI=" + ohEventSoundURi ;
            this.ohArarmTime1 = myPref.ohArarmTime1;
            dbMsg += ",アラーム時刻1=" + ohArarmTime1 ;
            this.oh100c = myPref.oh100c;
            dbMsg += ",の日曜=" + oh100c ;
            this.oh101c = myPref.oh101c;
            dbMsg += ",の月曜=" + oh101c ;
            this.oh102c = myPref.oh102c;
            dbMsg += ",の火曜=" + oh102c ;
            this.oh103c = myPref.oh103c;
            dbMsg += ",の水曜=" + oh103c ;
            this.oh104c = myPref.oh104c;
            dbMsg += ",の木曜=" + oh104c ;
            this.oh105c = myPref.oh105c;
            dbMsg += ",の金曜=" + oh105c ;
            this.oh106c = myPref.oh106c;
            dbMsg += ",の土曜=" + oh106c ;
            this.oh107c = myPref.oh107c;
            dbMsg += ",の祝日=" + oh107c ;

            this.ohArarmTime2 = myPref.ohArarmTime2;
            dbMsg += ",アラーム時刻2=" + ohArarmTime2 ;
            this.oh200c = myPref.oh200c;
            dbMsg += ",の日曜=" + oh200c ;
            this.oh201c = myPref.oh201c;
            dbMsg += ",の月曜=" + oh201c ;
            this.oh202c = myPref.oh202c;
            dbMsg += ",の火曜=" + oh202c ;
            this.oh203c = myPref.oh203c;
            dbMsg += ",の水曜=" + oh203c ;
            this.oh204c = myPref.oh204c;
            dbMsg += ",の木曜=" + oh204c ;
            this.oh205c = myPref.oh205c;
            dbMsg += ",の金曜=" + oh205c ;
            this.oh206c = myPref.oh206c;
            dbMsg += ",の土曜=" + oh206c ;
            this.oh207c = myPref.oh207c;
            dbMsg += ",の祝日=" + oh207c ;

            this.ohArarmTime3 = myPref.ohArarmTime3;
            dbMsg += ",アラーム時刻3=" + ohArarmTime3 ;
            this.oh300c = myPref.oh300c;
            dbMsg += ",の日曜=" + oh300c ;
            this.oh301c = myPref.oh301c;
            dbMsg += ",の月曜=" + oh301c ;
            this.oh302c = myPref.oh302c;
            dbMsg += ",の火曜=" + oh302c ;
            this.oh303c = myPref.oh303c;
            dbMsg += ",の水曜=" + oh303c ;
            this.oh304c = myPref.oh304c;
            dbMsg += ",の木曜=" + oh304c ;
            this.oh305c = myPref.oh305c;
            dbMsg += ",の金曜=" + oh305c ;
            this.oh306c = myPref.oh306c;
            dbMsg += ",の土曜=" + oh306c ;
            this.oh307c = myPref.oh307c;
            dbMsg += ",の祝日=" + oh307c ;
//            //Googleカレンダー連携
            this.ohGCAlignment = myPref.ohGCAlignment;
            dbMsg += ",Googleカレンダー連携=" + ohGCAlignment ;
            this.ohGCSubject = myPref.ohGCSubject;
            dbMsg += ",登録する名称=" + ohGCSubject ;
            this.ohGCStarttime = myPref.ohGCStarttime;
            dbMsg += ",開始時刻=" + ohGCStarttime ;
            this.ohGCEndtime = myPref.ohGCEndtime;
            dbMsg += ",終了時刻=" + ohGCEndtime ;
            this.ohGCEnddate = myPref.ohGCEnddate;
            dbMsg += ",終日=" + ohGCEnddate ;
            this.ohGCDescription = myPref.ohGCDescription;
            dbMsg += ",説明・メモ=" + ohGCDescription ;
            this.ohGCLocation = myPref.ohGCLocation;
            dbMsg += ",予定の場所=" + ohGCLocation ;
            this.ohGCColorName = myPref.ohGCColorName;
            dbMsg += ",予定の色名称=" + ohGCColorName ;
            this.ohGCColorRss = myPref.ohGCColorRss;
            dbMsg += ",予定の色リソースID=" + ohGCColorRss ;
            this.targetWakeUpTime = myPref.targetWakeUpTime;
            dbMsg += ",対象の起床時刻=" + targetWakeUpTime.toString() ;
            this.otherWakeUpTime = myPref.otherWakeUpTime;
            dbMsg += ",対象外の起床時刻=" + otherWakeUpTime.toString() ;

            dowDisplay = new ArrayList<String>();
            dowDisplay.add(getResources().getString(R.string.comm_holiday));
            dowDisplay.add(getResources().getString(R.string.comm_sunday));
            dowDisplay.add(getResources().getString(R.string.comm_monday));
            dowDisplay.add(getResources().getString(R.string.comm_tuesday));
            dowDisplay.add(getResources().getString(R.string.comm_wednesday));
            dowDisplay.add(getResources().getString(R.string.comm_thursday));
            dowDisplay.add(getResources().getString(R.string.comm_friday));
            dowDisplay.add(getResources().getString(R.string.comm_saturday));

            File inStrage = getFilesDir();
            dbMsg += "\n内部=" + inStrage.getPath() ;
            alarmFile = new File(this.getFilesDir(),  alFileName+".txt");
            dbMsg += "、アラーム用テキストファイル=" + alarmFile.getPath();           // + File.separator + alarmFile.getName();
            if(!alarmFile.exists()){
                dbMsg += ">>未作成";
            }

//            File exStrage = getExternalFilesDir("DIRECTORY_DOCUMENTS");
//            dbMsg += ",外部=" + exStrage.getPath() ;

            File[] externalFilesDirs = this.getExternalFilesDirs("DIRECTORY_DOCUMENTS");
            dbMsg += ",externalFilesDirs=" + externalFilesDirs.length + "件";
            for (int i = 0; i < externalFilesDirs.length; ++ i) {
                File wFile =externalFilesDirs[i];
                String wFileName = wFile.getName();
                fileDir = wFile.getPath();              // + File.separator + wFile.getName();
                dbMsg += ",fileDir=" + fileDir;
                String alarmListFileName = fileDir + File.separator + "taplans_alarm_list";
                dbMsg += ",alarmListFileName=" + alarmListFileName;
                File chFile = new File(alarmListFileName);
                if(chFile.exists()){
                    dbMsg += "有り";
                }
            }
//            String chFileName = "/storage/emulated/0/Android/data/com.hijiyam_koubou.taplans";          //_alarm_list";
//            dbMsg += ",chFileName=" + chFileName;
//            File chFile = new File(chFileName);
//            if(chFile.exists()){
//                dbMsg += "有り";
//            }

            tBDQTWeek = new ByDayOfTheWeekStr();
            ohBDQTWeek = new ByDayOfTheWeekStr();

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

//            if(alarmItemList == null){
//                alarmItemList = new ArrayList<AlarmItem>();
//                alarmItemList=mainViewModel.getList().getValue();
//                dbMsg += ",alarmItemList=" + alarmItemList.size() + "件";
//            }
            loadAlarms();
            // Google Calendar API の呼び出しのための認証情報を初期化する                          202408
            mCredential = GoogleAccountCredential.usingOAuth2(
                    getApplicationContext(),
                    Arrays.asList(SCOPES)
            ).setBackOff(new ExponentialBackOff());
            dbMsg += ",mCredential=" + mCredential.toString();

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
                    renewalAllAlam();
                    Snackbar.make(view, "本日以降のアラームを一括更新します。", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .setAnchorView(R.id.fab).show();
                }
            });

//            alarmManager = (AlarmManagerAlarmManager) getSystemService(Context.ALARM_SERVICE);
//            PendingIntent pendingIntent = getPendingIntent();
//            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarmTimeMillis, null), pendingIntent);
//

            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }


    private final ActivityResultLauncher<String[]> requestPermissionsLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    (Map<String, Boolean> grantStates) -> {
                        final String TAG = "ActivityResultLauncher";
                        String dbMsg = "[MainActivity]";
                        try {
                            boolean allPass = true;
                            ArrayList<String> unPass = new ArrayList<String>();
                            for (Map.Entry<String, Boolean> grantState : grantStates.entrySet()) {
                                dbMsg += "," + grantState.getKey() + "=" + grantState.getValue();
                                Boolean grant = grantState.getValue();
                                if(! grant){
                                    allPass = false;
                                    dbMsg += ">>未許可" ;
                                    unPass.add(grantState.getKey());
                                }
                                //   Timber.d(grantState.getKey() + " - " + grantState.getValue());
                            }
                            int unPassSize = unPass.size();
                            dbMsg += ">>未許可" +unPassSize + "件";
                            wakeUp();
//                            if(allPass){
//                                wakeUp();
//                            }else{
//                                String[] PERMISSIONS = unPass.toArray(new String[unPassSize]);
//                                dbMsg += ">>再表示";
//                                checkMyPermission(PERMISSIONS);
//                            }
                            myLog(TAG , dbMsg);
                        } catch (Exception er) {
                            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                        }
                    });
//ActivityResultContracts.RequestMultiplePermissions チートシート  ActivityResultContracts.RequestMultiplePermissions チートシート

    private ActivityResultLauncher<String> SingleRequestPermissionLauncher  =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                final String TAG = "singlerequestPermissionLauncher";
                String dbMsg = "[MainActivity]";
                try {

                    if (isGranted) {
                        dbMsg = "権限が付与されました。アプリでアクションまたはワークフローを続行します。";
                    } else {
                        // この機能にはユーザーが拒否した権限が必要であるため、使用できないことをユーザーに説明します。
                        // 同時に、ユーザーの決定を尊重します。
                        // ユーザーに決定を変更するよう説得するために、システム設定にリンクしないでください。
                    }
                    myLog(TAG, dbMsg);
                } catch (Exception e) {
                    myErrorLog(TAG ,  dbMsg + "で" + e);
                }


            });

    public void checkMyPermission(String[] PERMISSIONS) {
        final String TAG = "checkMyPermission";
        String dbMsg = "[MainActivity]";
        try {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {                //(初回起動で)全パーミッションの許諾を取る
                dbMsg += "許諾確認";
                boolean isNeedParmissionReqest = false;
                for ( String permissionName : PERMISSIONS ) {
                    dbMsg += "," + permissionName;
                    int checkResalt = checkSelfPermission(permissionName);
                    dbMsg += "=" + checkResalt;
                    if ( checkResalt != PackageManager.PERMISSION_GRANTED ) {//許可されていなければ -1 いれば 0
                        isNeedParmissionReqest = true;
                    }
                }
                dbMsg += "、許諾が必要=" + isNeedParmissionReqest;
                if ( isNeedParmissionReqest ) {
                    dbMsg += "::許諾処理へ";
                    requestPermissionsLauncher.launch(PERMISSIONS);

//                    for ( String permissionName : PERMISSIONS ) {
//                        SingleRequestPermissionLauncher.launch(permissionName);
//                    }
//                      new AlertDialog.Builder(MainActivity.this)
//                            .setTitle( getResources().getString(R.string.permission_titol) )
//                            .setMessage( getResources().getString(R.string.permission_msg))
//                            .setPositiveButton(android.R.string.ok , new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog , int which) {
//                                    ActivityCompat.requestPermissions(MainActivity.this, permissions, to_wakeUp);
//                             //       requestPermissions(PERMISSIONS ,to_wakeUp);
//                                }
//                            })
//                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog , int which) {
//                       //             quitMe();
//                                }
//                            })
//                            .create().show();
                }else{
                    dbMsg += "::許諾済み";
                    wakeUp();
                }
            } else{
                dbMsg += "::許諾操作不要";
                wakeUp();
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String TAG = "onCreate";
        String dbMsg = "[MainActivity]";
        try {
            mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

            String[] PERMISSIONS = {
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        android.Manifest.permission.WRITE_SETTINGS
            };
            checkMyPermission(PERMISSIONS);
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

