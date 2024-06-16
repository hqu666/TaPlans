package com.hijiyam_koubou.taplans;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.Locale;

public class MyPreferences extends PreferenceActivity {
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor myEditor;
    Locale locale;

    //ライフサイクル//////////////////////////////////////////////////////////////
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String TAG = "showPref";
        String dbMsg = "[MainActivity]";
        try {
             dbMsg += "MyPreferencesy読込み";
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            myEditor = sharedPref.edit();
            Locale locale = Locale.getDefault();        // アプリで使用されているロケール情報を取得
            //  dbMsg= "locale="+locale;/////////////////////////////////////

            addPreferencesFromResource(R.xml.pref);
            //setContentView(R.layout.activity_pref);
//        getFragmentManager()
//                .beginTransaction()
//                .replace(android.R.id.content,new PrefFragment())
//                .commit();
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
     }
    public static class PrefFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
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