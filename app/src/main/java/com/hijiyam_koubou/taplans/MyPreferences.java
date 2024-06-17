package com.hijiyam_koubou.taplans;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 設定画面
 * */
@SuppressWarnings("deprecation")
public class MyPreferences extends PreferenceActivity {
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor myEditor;

    public EditTextPreference calenderAccount_etp;     //登録するカレンダーのアカウント     android:defaultValue="your@gmail.com"
    public EditTextPreference sundayBackground_etp;     //    defaultValue="#FDE7E7" "@color/sunday_background"
    public EditTextPreference sundayTextColor_etp;     //    defaultValue="#ff0000" "@color/sunday_text_color"
    public EditTextPreference satudayBackground_etp;     //"@color/satuday_background"
    public EditTextPreference satudayTextColor_etp;     //"@color/satuday_text_color"
    public EditTextPreference defaultBackground_etp;     //"@color/default_background"
    public EditTextPreference defaultTextColor_etp;     //"@color/default_text_color"
    public SwitchPreference set_default_sp;
    public ListPreference tDates_lp;

    public Locale locale;

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

    public void readPref(Context con) {
        final String TAG = "readPref";
        String dbMsg = "[MyPreferences]";
        try {
            dbMsg += "MyPreference読込み";
            if(sharedPref == null){
                dbMsg += ",DefaultSharedPreferences読込み";
                sharedPref = PreferenceManager.getDefaultSharedPreferences(con);            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            }
            dbMsg += ",登録するカレンダーのアカウント=" + calenderAccount ;
            calenderAccount = sharedPref.getString("calendar_account", "your_account@gmail.com");
            dbMsg += ">>" + calenderAccount ;
            dbMsg += "," + getResources().getString(R.string.pref_sunday_background) +"=" + sundayBackground ;
            sundayBackground = sharedPref.getString("sundayBackground", String.valueOf(getResources().getColor(R.color.satuday_background)));
            dbMsg += ">>" + sundayBackground ;
            dbMsg += "," + getResources().getString(R.string.pref_sunday_text_color) +"=" + sundayTextColor ;
            sundayTextColor = sharedPref.getString("sundayTextColor", String.valueOf(getResources().getColor(R.color.sunday_text_color)));
            dbMsg += ">>" + sundayTextColor ;
            dbMsg += "," + getResources().getString(R.string.pref_satuday_background) +"=" + satudayBackground ;
            satudayBackground = sharedPref.getString("satudayBackground", String.valueOf(getResources().getColor(R.color.satuday_background)));         //"#EDEDFF"
            dbMsg += ">>" + satudayBackground ;
            dbMsg += "," + getResources().getString(R.string.pref_satuday_background) +"=" + satudayTextColor ;
            satudayTextColor = sharedPref.getString("satudayTextColor", String.valueOf(getResources().getColor(R.color.satuday_text_color)));         //"#0000FF"
            dbMsg += ">>" + satudayTextColor ;
            dbMsg += "," + getResources().getString(R.string.pref_default_background) +"=" + defaultBackground ;
            defaultBackground = sharedPref.getString("defaultBackground", String.valueOf(getResources().getColor(R.color.default_background)));         //"#FFFFFF"
            dbMsg += ">>" + defaultBackground ;
            dbMsg += "," + getResources().getString(R.string.pref_default_text_color) +"=" + defaultTextColor ;
            defaultTextColor = sharedPref.getString("defaultTextColor", String.valueOf(getResources().getColor(R.color.default_text_color)));         //"#000000"
            dbMsg += ">>" + defaultTextColor ;

            /*
     <ListPreference
        android:defaultValue="1"
        android:entries="@android:array/postalAddressTypes"
        android:entryValues="@android:array/postalAddressTypes"
        android:key="tDates"
        android:title="@string/pref_tDates"
        />
            * */

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
        String dbMsg = "[MyPreferences]";
        try {
             dbMsg += "MyPreferencesy読込み";
            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            myEditor = sharedPref.edit();
            Locale locale = Locale.getDefault();        // アプリで使用されているロケール情報を取得
            dbMsg += "locale="+locale;
            sundayBackground = String.valueOf(getResources().getColor(R.color.satuday_background));
            sundayTextColor = String.valueOf(getResources().getColor(R.color.sunday_text_color));
            satudayBackground = String.valueOf(getResources().getColor(R.color.satuday_background));         //"#EDEDFF"
            satudayTextColor = String.valueOf(getResources().getColor(R.color.satuday_text_color));         //"#0000FF"
            defaultBackground = String.valueOf(getResources().getColor(R.color.default_background));         //"#FFFFFF"
            defaultTextColor = String.valueOf(getResources().getColor(R.color.default_text_color));         //"#000000"

            addPreferencesFromResource(R.xml.pref);

            calenderAccount_etp = (EditTextPreference) findPreference("calendar_account");     //登録するカレンダーのアカウント     android:defaultValue="your@gmail.com"
            calenderAccount_etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[calenderAccount]";
                    try {
                        dbMsg += ",登録するカレンダーのアカウント=" + calenderAccount ;
                        String summary = (String) newValue;
                        dbMsg += ">>" + summary;
                        preference.setSummary(summary);
                        myEditor.putString("calendar_account", summary);
                        myEditor.commit();
                        calenderAccount = summary;
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });

            sundayBackground_etp = (EditTextPreference) findPreference("sundayBackground");     //    defaultValue="#FDE7E7" "@color/sunday_background"
            sundayTextColor_etp = (EditTextPreference) findPreference("sundayBackground");     //    defaultValue="#ff0000" "@color/sunday_text_color"
            satudayBackground_etp = (EditTextPreference) findPreference("satudayBackground");     //"@color/satuday_background"
            satudayTextColor_etp = (EditTextPreference) findPreference("satudayBackground");     //"@color/satuday_text_color"
            defaultBackground_etp = (EditTextPreference) findPreference("defaultBackground");     //"@color/default_background"
            defaultTextColor_etp = (EditTextPreference) findPreference("defaultBackground");     //"@color/default_text_color"
            set_default_sp = (SwitchPreference) findPreference("set_default");
            tDates_lp = (ListPreference) findPreference("tDates");

            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
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