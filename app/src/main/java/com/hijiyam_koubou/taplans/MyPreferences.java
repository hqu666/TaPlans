package com.hijiyam_koubou.taplans;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

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
    public CheckBoxPreference set_default_sp;
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
            Map<String, ?> inPref = sharedPref.getAll();
            dbMsg += inPref.size() + "件" ;
            dbMsg += "," + con.getResources().getString(R.string.pref_calender_account) +"=" + calenderAccount ;
            calenderAccount = sharedPref.getString("calendar_account", "your_account@gmail.com");
            dbMsg += ">>" + calenderAccount ;

            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_background)+"；現在=" + sundayBackground ;
            sundayBackground = sharedPref.getString("sundayBackground", String.valueOf(con.getResources().getColor(R.color.satuday_background)));
            dbMsg += ">>" + sundayBackground ;

            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_text_color) +"=" + sundayTextColor ;
            sundayTextColor = sharedPref.getString("sundayTextColor", String.valueOf(con.getResources().getColor(R.color.sunday_text_color)));
            dbMsg += ">>" + sundayTextColor ;

            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_background) +"=" + satudayBackground ;
            satudayBackground = sharedPref.getString("satudayBackground", String.valueOf(con.getResources().getColor(R.color.satuday_background)));         //"#EDEDFF"
            dbMsg += ">>" + satudayBackground ;

            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_background) +"=" + satudayTextColor ;
            satudayTextColor = sharedPref.getString("satudayTextColor", String.valueOf(con.getResources().getColor(R.color.satuday_text_color)));         //"#0000FF"
            dbMsg += ">>" + satudayTextColor ;

            dbMsg += "," + con.getResources().getString(R.string.pref_default_background) +"=" + defaultBackground ;
            defaultBackground = sharedPref.getString("defaultBackground", String.valueOf(con.getResources().getColor(R.color.default_background)));         //"#FFFFFF"
            dbMsg += ">>" + defaultBackground ;

            dbMsg += "," + con.getResources().getString(R.string.pref_default_text_color) +"=" + defaultTextColor ;
            defaultTextColor = sharedPref.getString("defaultTextColor", String.valueOf(con.getResources().getColor(R.color.default_text_color)));         //"#000000"
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

    /**
     *　全項目のサマリーを書き直す
     * **/
    public void setAllSummary(Context con) {
        final String TAG = "setAllSummary";
        String dbMsg = "[MyPreferences]";
        try {
            if(sharedPref == null){
                dbMsg += ",DefaultSharedPreferences読込み";
                sharedPref = PreferenceManager.getDefaultSharedPreferences(con);            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            }
            Map<String, ?> inPref = sharedPref.getAll();
            dbMsg += inPref.size() + "件" ;

            dbMsg += "," + con.getResources().getString(R.string.pref_calender_account) +"=" + calenderAccount ;
            calenderAccount = sharedPref.getString("calendar_account", "your_account@gmail.com");
            dbMsg += ">>" + calenderAccount ;
            calenderAccount_etp.setSummary(calenderAccount);     //登録するカレンダーのアカウント     android:defaultValue="your@gmail.com"

            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_background) +"=" + sundayBackground ;
            sundayBackground = sharedPref.getString("sundayBackground", String.valueOf(con.getResources().getString(R.string.satuday_background)));
            dbMsg += ">>" + sundayBackground ;
            sundayBackground_etp.setSummary(sundayBackground);     //    defaultValue="#FDE7E7" "@color/sunday_background"

            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_text_color) +"=" + sundayTextColor ;
            sundayTextColor = sharedPref.getString("sundayTextColor", String.valueOf(con.getResources().getString(R.string.sunday_text_color)));
            dbMsg += ">>" + sundayTextColor ;
            sundayTextColor_etp.setSummary(sundayTextColor);     //    defaultValue="#ff0000" "@color/sunday_text_color"

            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_background) +"=" + satudayBackground ;
            satudayBackground = sharedPref.getString("satudayBackground", String.valueOf(con.getResources().getString(R.string.satuday_background)));         //"#EDEDFF"
            dbMsg += ">>" + satudayBackground ;
            satudayBackground_etp.setSummary(satudayBackground);     //"@color/satuday_background"

            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_text_color) +"=" + satudayTextColor ;
            satudayTextColor = sharedPref.getString("satudayTextColor", String.valueOf(con.getResources().getString(R.string.satuday_text_color)));         //"#0000FF"
            dbMsg += ">>" + satudayTextColor ;
            satudayTextColor_etp.setSummary(satudayTextColor);     //"@color/satuday_text_color"

            dbMsg += "," + con.getResources().getString(R.string.pref_default_background) +"=" + defaultBackground ;
            defaultBackground = sharedPref.getString("defaultBackground", String.valueOf(con.getResources().getString(R.string.default_background)));         //"#FFFFFF"
            dbMsg += ">>" + defaultBackground ;
            defaultBackground_etp.setSummary(defaultBackground);     //"@color/default_background"

            dbMsg += "," + con.getResources().getString(R.string.pref_default_text_color) +"=" + defaultTextColor ;
            defaultTextColor = sharedPref.getString("defaultTextColor", String.valueOf(con.getResources().getString(R.string.default_text_color)));         //"#000000"
            dbMsg += ">>" + defaultTextColor ;
            defaultTextColor_etp.setSummary(defaultTextColor);     //"@color/default_text_color"

            dbMsg += "," + con.getResources().getString(R.string.pref_set_default) +"=" + sharedPref.getBoolean("set_default", false);    ;
            set_default_sp.setChecked(false);

            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
     }

     /**
      * 色指定を初期化する
      * */
    public void setColorDefault(Context con) {
        final String TAG = "setColorDefault";
        String dbMsg = "[MyPreferences]";
        try {
            if(sharedPref == null){
                dbMsg += ",DefaultSharedPreferences読込み";
                sharedPref = PreferenceManager.getDefaultSharedPreferences(con);            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
                myEditor = sharedPref.edit();
            }

            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_background) + "=" + sundayBackground;
            sundayBackground = String.valueOf(con.getResources().getString(R.string.satuday_background));
            dbMsg += ">>" + sundayBackground ;
            myEditor.putString("sundayBackground", sundayBackground);
            sundayBackground_etp.setSummary(sundayBackground);

            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_text_color) +"=" + sundayTextColor ;
            sundayTextColor = String.valueOf(con.getResources().getString(R.string.sunday_text_color));
            dbMsg += ">>" + sundayTextColor ;
            sundayTextColor_etp.setSummary(sundayTextColor);     //    defaultValue="#ff0000" "@color/sunday_text_color"
            myEditor.putString("sundayTextColor", sundayTextColor);
            sundayTextColor_etp.setSummary(sundayTextColor);

            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_background) +"=" + satudayBackground ;
            satudayBackground = String.valueOf(con.getResources().getString(R.string.satuday_background));         //"#EDEDFF"
            dbMsg += ">>" + satudayBackground ;
            myEditor.putString("satudayBackground", satudayBackground);
            satudayBackground_etp.setSummary(satudayBackground);

            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_text_color) +"=" + satudayTextColor ;
            satudayTextColor = String.valueOf(con.getResources().getString(R.string.satuday_text_color));         //"#0000FF"
            dbMsg += ">>" + satudayTextColor ;
            myEditor.putString("satudayTextColor", satudayTextColor);
            satudayTextColor_etp.setSummary(satudayTextColor);     //"@color/default_text_color"

            dbMsg += "," + con.getResources().getString(R.string.pref_default_background) +"=" + defaultBackground ;
            defaultBackground = String.valueOf(con.getResources().getString(R.string.default_background));         //"#FFFFFF"
            dbMsg += ">>" + defaultBackground ;
            myEditor.putString("defaultBackground", defaultBackground);
            defaultBackground_etp.setSummary(defaultBackground);

            dbMsg += "," + con.getResources().getString(R.string.pref_default_text_color) +"=" + defaultTextColor ;
            defaultTextColor = String.valueOf(con.getResources().getString(R.string.default_text_color));         //"#000000"
            dbMsg += ">>" + defaultTextColor ;
            myEditor.putString("defaultTextColor", defaultTextColor);
            defaultTextColor_etp.setSummary(defaultTextColor);

            myEditor.commit();
            setAllSummary(getApplicationContext());

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
            sundayBackground = String.valueOf(getResources().getString(R.string.satuday_background));
            dbMsg += "," + getResources().getString(R.string.pref_sunday_background) + sundayBackground ;
            sundayTextColor = String.valueOf(getResources().getString(R.string.sunday_text_color));
            dbMsg += "," + getResources().getString(R.string.pref_sunday_text_color) + sundayTextColor ;
            satudayBackground = String.valueOf(getResources().getString(R.string.satuday_background));         //"#EDEDFF"
            dbMsg += "," + getResources().getString(R.string.pref_satuday_background) + satudayBackground ;
            satudayTextColor = String.valueOf(getResources().getString(R.string.satuday_text_color));         //"#0000FF"
            dbMsg += "," + getResources().getString(R.string.pref_satuday_text_color) + satudayTextColor ;
            defaultBackground = String.valueOf(getResources().getString(R.string.default_background));         //"#FFFFFF"
            dbMsg += "," + getResources().getString(R.string.pref_default_background) + defaultBackground ;
            defaultTextColor = String.valueOf(getResources().getString(R.string.default_text_color));         //"#000000"
            dbMsg += "," + getResources().getString(R.string.pref_default_text_color) + defaultTextColor ;

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
            sundayBackground_etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[sundayBackground]";
                    try {
                        dbMsg += "," + getResources().getString(R.string.pref_sunday_background) + sundayBackground ;
                        String summary = (String) newValue;
                        dbMsg += ">>" + summary;
                        myEditor.putString("sundayBackground", summary);
                        myEditor.commit();
                        sundayBackground = summary;
                        preference.setSummary(summary);
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });

            sundayBackground_etp = (EditTextPreference) findPreference("sundayBackground");     //    defaultValue="#ff0000" "@color/sunday_text_color"
            sundayTextColor_etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[sundayTextColor_etp]";
                    try {
                        dbMsg += "," + getResources().getString(R.string.pref_sunday_text_color) + sundayTextColor ;
                        String summary = (String) newValue;
                        dbMsg += ">>" + summary;
                        myEditor.putString("sundayTextColor", summary);
                        myEditor.commit();
                        sundayTextColor = summary;
                        preference.setSummary(summary);
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });

            satudayBackground_etp = (EditTextPreference) findPreference("satudayBackground");     //"@color/satuday_background"
            satudayBackground_etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[satudayBackground_etp]";
                    try {
                        satudayBackground = String.valueOf(getResources().getColor(R.color.satuday_background));         //"#EDEDFF"
                        String summary = (String) newValue;
                        dbMsg += ">>" + summary;
                        myEditor.putString("satudayBackground", summary);
                        myEditor.commit();
                        satudayBackground = summary;
                        preference.setSummary(summary);
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });

            satudayTextColor_etp = (EditTextPreference) findPreference("satudayBackground");     //"@color/satuday_text_color"
            satudayTextColor_etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[satudayTextColor_etp]";
                    try {
                        dbMsg += "," + getResources().getString(R.string.pref_satuday_text_color) + satudayTextColor ;
                        String summary = (String) newValue;
                        dbMsg += ">>" + summary;
                        myEditor.putString("satudayTextColor", summary);
                        myEditor.commit();
                        satudayTextColor = summary;
                        preference.setSummary(summary);
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });

            defaultBackground_etp = (EditTextPreference) findPreference("defaultBackground");     //"@color/default_background"
            defaultBackground_etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[defaultBackground_etp]";
                    try {
                        dbMsg += "," + getResources().getString(R.string.pref_default_background) + defaultBackground ;
                        String summary = (String) newValue;
                        dbMsg += ">>" + summary;
                        myEditor.putString("defaultBackground", summary);
                        myEditor.commit();
                        defaultBackground = summary;
                        preference.setSummary(summary);
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });

            defaultBackground_etp = (EditTextPreference) findPreference("defaultBackground");     //"@color/default_text_color"
            defaultTextColor_etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[defaultTextColor_etp]";
                    try {
                        dbMsg += "," + getResources().getString(R.string.pref_default_text_color) + defaultTextColor ;
                        String summary = (String) newValue;
                        dbMsg += ">>" + summary;
                        myEditor.putString("defaultTextColor", summary);
                        myEditor.commit();
                        defaultTextColor = summary;
                        preference.setSummary(summary);
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });

            defaultBackground_etp = (EditTextPreference) findPreference("set_default");
            set_default_sp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final String TAG = "onPreferenceChange";
                    String dbMsg = "[set_default_sp]";
                    try {
                        dbMsg += "newValue=" + newValue;
                        boolean nBool= (boolean) newValue;
                        if(nBool){
//                            myEditor.clear();
                            setColorDefault(getApplicationContext());
 //                            readPref(getApplicationContext());
//                            setAllSummary(getApplicationContext());
                         //   set_default_sp.wait(1000);
                            set_default_sp.setChecked(false);
                        }
                        myLog(TAG, dbMsg);
                    } catch (Exception e) {
                        myErrorLog(TAG ,  dbMsg + "で" + e);
                    }
                    return true;	//	更新の適用
                }
            });
            set_default_sp.setChecked(false);


            tDates_lp = (ListPreference) findPreference("tDates");


            readPref(getApplicationContext());
            setAllSummary(getApplicationContext());
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