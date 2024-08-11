package com.hijiyam_koubou.taplans;

import android.Manifest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

/**
 * 一時的なデータを保持
 * */
public class MainViewModel extends ViewModel {

    public MutableLiveData<ArrayList<AlarmItem>> alarmItemList;
    public MutableLiveData<String> alarmItemJson;

//    public MainViewModel() {
//        alarmItemList = new MutableLiveData<>();
//    }


    public void setAlarnLiostJsom(String receveStr) {
        final String TAG = "setAlarnLiostJsom";
        String dbMsg = "[MainViewModel]";
        try {
            dbMsg += "receveStr="  + receveStr.length()+ "文字";
            if(alarmItemJson == null){
                dbMsg += "生成";
                alarmItemJson = new MutableLiveData<String> (receveStr);
                alarmItemJson.setValue("");
            }else{
                dbMsg += "," + receveStr.substring(0,50) + "～" + receveStr.substring(receveStr.length()-50,receveStr.length()) ;
            }
            alarmItemJson.setValue(receveStr);
            if(alarmItemJson != null){
                dbMsg += ">>" + alarmItemJson.getValue().substring(0,50) + "～" + alarmItemJson.getValue().substring(alarmItemJson.getValue().length()-50,alarmItemJson.getValue().length());
                dbMsg += ":" + alarmItemJson.getValue().length()+ "文字";
            }
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    public String getAlarnLiostJsom() {
        final String TAG = "getAlarnLiostJsom";
        String dbMsg = "[MainViewModel]";
        try {
            if(alarmItemJson == null){
                dbMsg += "生成";
                alarmItemJson = new MutableLiveData<String> ();
                alarmItemJson.setValue("");
            }else{
                if(alarmItemJson.getValue().length()>50   ){
                    dbMsg += ">>" + alarmItemJson.getValue().substring(0,50) + "～" + alarmItemJson.getValue().substring(alarmItemJson.getValue().length()-50,alarmItemJson.getValue().length());
                }
                dbMsg += ":" + alarmItemJson.getValue().length()+ "文字";
            }
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
        return alarmItemJson.getValue();
    }

    public void setList(ArrayList<AlarmItem> receveItemList) {
        final String TAG = "setList";
        String dbMsg = "[MainViewModel]";
        try {
            dbMsg += "receveItemList=" + receveItemList.get(0).dateStr + "～" + receveItemList.get(receveItemList.size()-1).dateStr + ":" + receveItemList.size()+ "件";
//            if(alarmItemList == null){
//                dbMsg += ",alarmItemList生成";
                alarmItemList = new MutableLiveData<ArrayList<AlarmItem>> (receveItemList);
//            }else{
//                ArrayList<AlarmItem> bList = alarmItemList.getValue();
//                if(bList != null){
//                    dbMsg += "、alarmItemList=" + alarmItemList.getValue().size()+ "件";
//                }else{
//                    dbMsg += "、alarmItemList=null";
//                }
//            }
//            alarmItemList.setValue(receveItemList);


            if(alarmItemList != null){
                dbMsg += ">>" + alarmItemList.getValue().get(0).dateStr + "～" + alarmItemList.getValue().get(alarmItemList.getValue().size()-1).dateStr + ":" + alarmItemList.getValue().size()+ "件";
            }
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }

    }

     public ArrayList<AlarmItem> getList() {
         final String TAG = "getList";
         String dbMsg = "[MainViewModel]";
         try {
             if(alarmItemList == null){
                 alarmItemList = new MutableLiveData<>();
             }else{
                 dbMsg += ">>" + alarmItemList.getValue().get(0).dateStr + "～" + alarmItemList.getValue().get(alarmItemList.getValue().size()-1).dateStr ;
                 dbMsg += ":" + alarmItemList.getValue().size()+ "件";
             }
             myLog(TAG, dbMsg);
         } catch (Exception e) {
             myErrorLog(TAG ,  dbMsg + "で" + e);
         }
         return alarmItemList.getValue();
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