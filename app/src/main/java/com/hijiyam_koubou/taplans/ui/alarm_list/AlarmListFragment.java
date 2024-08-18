package com.hijiyam_koubou.taplans.ui.alarm_list;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.hijiyam_koubou.taplans.AlarmItem;
import com.hijiyam_koubou.taplans.AlarmItemAdapter;
import com.hijiyam_koubou.taplans.MainActivity;
import com.hijiyam_koubou.taplans.R;
import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentAlarmListBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlarmListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private MainActivity pClass;
    private FragmentAlarmListBinding binding;

    public ListView alarmLV;
    private AlarmItem[] readArray;

    public int readAlarmList( ArrayList<AlarmItem> setItemList) {
        final String TAG = "readAlarmList";
        String dbMsg = "[AlarmListFragment]";
        int retInt = -1;
        try {
            retInt= setItemList.size();
            dbMsg += ",retInt=" + retInt + "件";
            if(retInt>0){
                AlarmItemAdapter alarmItemAdapter = new AlarmItemAdapter(
                        this.getActivity(),
                        R.layout.alarm_list_item,
                        setItemList
                );
                alarmLV.setAdapter(alarmItemAdapter);
                dbMsg += ",getCount=" + alarmLV.getCount() + "件";
            }else{
                dbMsg += ">>リスト無し";
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return retInt;
    }


    public AlarmItem setItem;
    private int tIndex;
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        final String TAG = "onItemClick";
        String dbMsg = "[AlarmListFragment]";
        try {
            dbMsg += "[" + position + "]" + pClass.alarmItemList.get(position).dateStr;
            setItem = pClass.alarmItemList.get(position);
            dbMsg += setItem.dateStr + " " + setItem.timeStr + ":" + setItem.isTarget;
            tIndex=position;
        //    pClass.showTimePicker("",wItem.timeStr,null);
            String[] hourMinitStrs = setItem.timeStr.split(":");
            int hour = Integer.parseInt(hourMinitStrs[0].trim());
            int minute = Integer.parseInt(hourMinitStrs[1].trim());
            TimePickerDialog dialog = new TimePickerDialog(
                    getActivity(),
                    new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //                    final String TAG = "onTextChanged";
                            final String TAG = "onTimeSet";
                            String dbMsg = "[onItemClick]";
                            try {
                                String wTimeStr = hourOfDay + ":" + minute;
                                if(hourOfDay<10){
                                    wTimeStr = "0" + hourOfDay;
                                }
                                if(minute<10){
                                    wTimeStr += ":0" + hourOfDay;
                                }else{
                                     wTimeStr += ":" + minute;
                                }
                                dbMsg += "指定された時刻="+ ","+ wTimeStr;
                                setItem.timeStr =wTimeStr;
                                setItem.isNotChangeable =true;
                                AlarmItem retItem = pClass.renewalAlam(setItem);
                                String tStr = retItem.dateStr + " " + retItem.timeStr + ":" + retItem.comment + ":変更不可" + retItem.isNotChangeable;
//                                Thread.sleep(1000);
                                Toast.makeText(getActivity(), tStr, Toast.LENGTH_LONG ).show();
                                Integer lSize = readAlarmList(pClass.alarmItemList);
                                dbMsg += ",作成したリストの行数=" + lSize;
                                alarmLV.setSelection(tIndex);
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

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final String TAG = "onCreateView";
        String dbMsg = "[AlarmListFragment]";
        View root = null;
        try {
            pClass=(MainActivity)inflater.getContext();
            AlarmListViewModel galleryViewModel =
                    new ViewModelProvider(this).get(AlarmListViewModel.class);

            binding = FragmentAlarmListBinding.inflate(inflater, container, false);
            root = binding.getRoot();

            alarmLV = binding.alarmLV;
            int alarmLVId = alarmLV.getId();
            dbMsg += ",alarmLVId=" + alarmLVId;
            Integer lSize = readAlarmList(pClass.alarmItemList);
            dbMsg += ",作成したリストの行数=" + lSize;
            alarmLV.setOnItemClickListener(this);

            final TextView textView = binding.textAlarmList;
            galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            textView.setText(lSize + "件");
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
        return root;
    }


    /**
     * 中身のFragmentがひゃきされた時
     * */
    @Override
    public void onDestroyView() {
        final String TAG = "onDestroyView";
        String dbMsg = "[AlarmListFragment]";
        View root = null;
        try {
            super.onDestroyView();
            binding = null;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
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