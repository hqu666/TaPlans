package com.hijiyam_koubou.taplans;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String TAG = "onReceive";
        String dbMsg = "[AlarmReceiver]";
        try {
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
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
/*
指定時刻や定期的に実行するアラームの実装方法              https://pg.akihiro-takeda.com/android-alarm/
Androidアプリ開発サンプルコード | 目覚まし時計        https://mura-hiro.com/android-dev-sample-alarm-clock/
アラームの作り方                https://qiita.com/petitviolet/items/89a0166f4167753d8844
 */