package com.hijiyam_koubou.taplans;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AlarmItemAdapter extends ArrayAdapter<AlarmItem> {
    private MainActivity pClass;
    private int _layoutId;
    private ArrayList<AlarmItem> alarmItemList;
    private LayoutInflater _inflater;
    private int rBGSunDay;
    private int rBGSatuDay;
    private int rBGWeekDay;
    private int rBGotherDayBG;
    private int rBGotherDayTC;

    public AlarmItemAdapter(@NonNull Context context, int resource,ArrayList<AlarmItem> alarmItemList) {
        super(context, resource);
                final String TAG = "AlarmItemAdapter";
        String dbMsg = "[AlarmItemAdapter]";
        try {
            this._inflater = LayoutInflater.from(context);
            this._layoutId = resource;
            this.alarmItemList =alarmItemList;
            this.pClass = (MainActivity) context;
            dbMsg += ",alarmItemList=" + this.alarmItemList.size() + "件";
            this.rBGSunDay = Color.parseColor("#FDE7E7");             //
            this.rBGSatuDay = Color.parseColor("#EDEDFF");           //
            this.rBGWeekDay = Color.parseColor("#FFFFFF");           //
            this.rBGotherDayBG = Color.parseColor("#DDDDDD");           //対象外の背景
            this.rBGotherDayTC = Color.parseColor("#222222");           //対象外の文字色
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }

    }


    @Override
    public int getCount() {
        final String TAG = "getCount";
        String dbMsg = "[AlarmItemAdapter]";
        int retInt =0;          //0のままだとgetViewが呼ばれない
        try {
            if(this.alarmItemList != null){
                retInt = this.alarmItemList.size();
            }
            dbMsg += "retInt=" + retInt + "件";
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retInt;
    }

    @Override
    public AlarmItem getItem(int position) {
        final String TAG = "getItem";
        String dbMsg = "[AlarmItemAdapter]";
        AlarmItem retObject=null;
        try {
            dbMsg = "[" + position + "]";
            retObject = (AlarmItem) this.alarmItemList.get(position);
            dbMsg = "dateStr" + retObject.dateStr;

            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retObject;
    }

    /**
     * 指定された位置のデータを取得
     * */
    @Override
    public long getItemId(int position) {
        final String TAG = "getItemId";
        String dbMsg = "[AlarmItemAdapter]";
        long retlong =0;
        try {
            dbMsg = "[" + position + "]";
            if(position > -1){
                retlong =position;
            }
            dbMsg = ",retlong=" + retlong;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retlong;
    }

    /**
     * ビューの生成と再利用を行います。
     * データをバインドする
     * */
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {

        final String TAG = "getView";
        String dbMsg = "[AlarmItemAdapter]";
        try {
            dbMsg += "[" + position + "]";
            AlarmItem gcItem = this.alarmItemList.get(position);
            String wStr = gcItem.isTarget + ";" + gcItem.dateStr + " :" + gcItem.timeStr+ " " + gcItem.DayOfTheWeek+ ";" + gcItem.comment;
          //  String wStr = gcItem.get("isTarget") + ";" + gcItem.get("dateStr") + " :" + gcItem.get("timeStr")+ " " + gcItem.get("DayOfTheWeek");
            dbMsg += ";" + wStr;
            if( convertView == null ) {
                convertView = _inflater.inflate(R.layout.alarm_list_item, null);
//                LayoutInflater inflater = LayoutInflater.from( getContext() );
//                convertView = inflater.inflate( android.R.layout.simple_spinner_item, parent, false );
                //** android.R.layout.sinple_spinner_item で inflate **//
            }
//            RadioButton target_bt = convertView.findViewById(R.id.target_bt);
            TextView date_tv = convertView.findViewById(R.id.date_tv);
            TextView time_tv = convertView.findViewById(R.id.time_tv);
            TextView dow_tv = convertView.findViewById(R.id.dow_tv);
            TextView memo_tv = convertView.findViewById(R.id.memo_tv);
            date_tv.setText(gcItem.dateStr);
            time_tv.setText(gcItem.timeStr);
            String dayOfTheWeek = this.pClass.dowDisplay.get(gcItem.DayOfTheWeek);
            dow_tv.setText(dayOfTheWeek);
            memo_tv.setText(gcItem.comment);
            if(gcItem.DayOfTheWeek == Calendar.SUNDAY){
                dbMsg += "[日曜日]";
                convertView.setBackgroundColor(rBGSunDay);
            }else if(gcItem.DayOfTheWeek == Calendar.SATURDAY){
                dbMsg += "[土曜日]";
                convertView.setBackgroundColor(rBGSatuDay);
            }else if(! gcItem.isTarget){
                dbMsg += "対象外";
                convertView.setBackgroundColor(rBGotherDayBG);
            }else{
                convertView.setBackgroundColor(rBGWeekDay);
            }

            if(gcItem.isTarget){

            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return Objects.requireNonNull(convertView);
    }

    /////////////////////////////////////////////////////////////
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
* Spinnerの文字色をJavaで指定する     https://qiita.com/ztrehagem/items/aa76f07f96deb3bf4784
*
* */