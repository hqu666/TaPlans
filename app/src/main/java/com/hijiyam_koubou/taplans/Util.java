package com.hijiyam_koubou.taplans;

import android.util.Log;

public class Util {

    public static boolean debugNow = true;

    public static void myLog(String TAG , String dbMsg) {
        try {
            if ( debugNow ) {
                if(23 < TAG.length()){     				//APIL24以上（Android 7.0 Nougat 以上)
                    TAG = TAG.substring(0,22);
                }
                Log.w(TAG , dbMsg + "");
            }
        } catch (Exception er) {
            Log.e(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    public static boolean errorCheckNow = true;

    public static void myErrorLog(String TAG , String dbMsg) {
        if ( errorCheckNow ) {
            if(23 < TAG.length()){
                TAG = TAG.substring(0,22);
            }
            Log.e(TAG , dbMsg + "");
        }
    }


}
