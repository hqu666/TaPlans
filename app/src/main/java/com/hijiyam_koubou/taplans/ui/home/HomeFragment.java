package com.hijiyam_koubou.taplans.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hijiyam_koubou.taplans.CalendarMembers;
import com.hijiyam_koubou.taplans.MainActivity;
import com.hijiyam_koubou.taplans.MyPreferences;
import com.hijiyam_koubou.taplans.R;
import com.hijiyam_koubou.taplans.ScheduleItems;
import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {


    private MainActivity pClass;
    private FragmentHomeBinding binding;

    private Spinner yearSpinner;
    private ArrayAdapter<String> yearAadapter;
    private int targetYear;
    private Spinner monthSpinner ;

    /**
     * 月戻しボタン
     * */
    private ImageButton rewButton;
    /**
     * 月送りボタン
     * */
    private  ImageButton ffButton;
    private ArrayList<CheckBox> dayChecks;
    private ArrayList<TextView> dayTexts;

    private int targetMonth;
    private int targetDay;

    private ArrayList<CalendarMembers> calendarMembers;
    private ArrayList<String> targetDayLidt;

    private TextView textView;


    /**
     * Dateクラスによる現在時表示>>表示対象日
     * */
    public Date targetDate;
    /**
    *デフォルトのCalendarオブジェクト
    * */
    public Calendar cal;
    /**
     * 日曜日の背景色：RGB
     * */
    public int rBGSunDay;
    public int rBGSatuDay;
    public int rBGWeekDay;
    /**当月外*/
    public int rBGbaMonth;

    private View root;


    /**
     * 起動時、本日の日付を取得して年スライダーを設定する
     * */
    public void wakeUpMethod() {
        final String TAG = "wakeUpMethod";
        String dbMsg = "[HomeFragment]";
        try {
            //現在
            this.targetDate = new Date();
            dbMsg += ",起動時は"+ targetDate.toString();
            this.cal = Calendar.getInstance();
            int tYear = this.cal.get(Calendar.YEAR);
            dbMsg += ","+ tYear + "年";
            // 年リスト項目を作成する
            yearAadapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
            for (int i=(tYear-2); i < (tYear+4); i++) {
                yearAadapter.add(""+ (i));
            }
            dbMsg += ",年リスト＝"+ yearAadapter.getCount() + "件";
            yearSpinner.setAdapter(yearAadapter);

            int tMonth = this.cal.get(Calendar.MONTH)+1;
            dbMsg += tMonth + "月";
            // 月リスト項目を作成する
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
            for (int i=0; i < 12; i++) {
                adapter.add(""+ (i+1));
            }
            dbMsg += ",月リスト＝"+ adapter.getCount() + "件";
            monthSpinner.setAdapter(adapter);

            int tDate = this.cal.get(Calendar.DATE);
            dbMsg += tDate + "日";
            int tDOW = this.cal.get(Calendar.DAY_OF_WEEK);
            dbMsg += "(" + tDOW + ")";
            int tDOM = this.cal.get(Calendar.DAY_OF_MONTH);
            dbMsg += tDOM + "時";
            int tMinute = this.cal.get(Calendar.MINUTE);
            dbMsg += tMinute + "分";
            int tSecond = this.cal.get(Calendar.SECOND);
            dbMsg += tSecond + "秒";
            myLog(TAG , dbMsg);
            setTargetDate(cal);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    public void setTargetDate(Calendar setCal) {
        final String TAG = "setTargetDate";
        String dbMsg = "[HomeFragment]";
        try {
            targetYear = setCal.get(Calendar.YEAR);
            dbMsg += ","+ targetYear + "年";
            int ysPisition = yearAadapter.getPosition("" + targetYear);
            dbMsg += "は"+ ysPisition + "番目,";
            dbMsg += ",isFocusable=" + yearSpinner.isFocusable() ;
            if (yearSpinner.isFocusable() == false) {
                yearSpinner.setSelection(ysPisition);
                yearSpinner.setFocusable(false);
            }
            targetMonth = setCal.get(Calendar.MONTH)+1;
            dbMsg +=targetMonth + "月";
            dbMsg += ",isFocusable=" + monthSpinner.isFocusable() ;
            if (monthSpinner.isFocusable() == false) {
                monthSpinner.setSelection(targetMonth-1);
                monthSpinner.setFocusable(false);
            }

            targetDay = setCal.get(Calendar.DATE);
            dbMsg += targetDay + "日";
            int tDOW = setCal.get(Calendar.DAY_OF_WEEK);
            dbMsg += "(" + tDOW + ")";
            int tDOM = setCal.get(Calendar.DAY_OF_MONTH);
            dbMsg += tDOM + "時";
            int tMinute = setCal.get(Calendar.MINUTE);
            dbMsg += tMinute + "分";
            int tSecond = setCal.get(Calendar.SECOND);
            dbMsg += tSecond + "秒";

            calendarMembers = new ArrayList<>();
            Calendar vCalStart = Calendar.getInstance();
            vCalStart.set(targetYear, targetMonth-1, 1);
            dbMsg += "\n開始日" + vCalStart.get(Calendar.YEAR) +"年"+ (vCalStart.get(Calendar.MONTH)+1) + "月" +vCalStart.get(Calendar.DATE) + "日";
            int targetStartDOW = vCalStart.get(Calendar.DAY_OF_WEEK)-1;    // Calendar.SUNDAY=1～ Calendar.SATURDAY=7
            dbMsg += "の曜日は=" + targetStartDOW;
            vCalStart.add(Calendar.DATE, -targetStartDOW);
            dbMsg += ",開始=" + (vCalStart.get(Calendar.MONTH) +1)+ "月" +vCalStart.get(Calendar.DATE) + "日から";
            textView.setText(dbMsg);

            for (int i=0; i < 42; i++) {            //c34cBox　まで
                CalendarMembers cMember=new CalendarMembers();
                cMember.cDate = vCalStart;
                int rMonth = cMember.cDate.get(Calendar.MONTH) +1;
                String rDay = cMember.cDate.get(Calendar.DATE) +"";
                int rDOW = cMember.cDate.get(Calendar.DAY_OF_WEEK) ;

                dbMsg += "("+ i + ")" + rMonth + "月" +rDay+ "日;" + rDOW;
                calendarMembers.add(cMember);
                CheckBox tCheckBox = dayChecks.get(i);
                tCheckBox.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        setTargetDays(v);

                   }
                });
                tCheckBox.setText(rDay);
                TextView tTextView = dayTexts.get(i);
                if(targetMonth == rMonth){
                    tCheckBox.setTextSize(20.0F);
                    if(rDOW == Calendar.SUNDAY){
                        dbMsg += "[日曜日]";
                        tCheckBox.setBackgroundColor(rBGSunDay);
                        tTextView.setBackgroundColor(rBGSunDay);
                    }else if(rDOW == Calendar.SATURDAY){
                        dbMsg += "[土曜日]";
                        tCheckBox.setBackgroundColor(rBGSatuDay);
                        tTextView.setBackgroundColor(rBGSatuDay);
                    }else{
                        tCheckBox.setBackgroundColor(rBGWeekDay);
                        tTextView.setBackgroundColor(rBGWeekDay);
                    }
                }else{
                    tCheckBox.setTextSize(10.0F);
                    tCheckBox.setBackgroundColor(rBGbaMonth);
                    tTextView.setBackgroundColor(rBGbaMonth);
                }

                  vCalStart.add(Calendar.DATE,1);
            }
            dbMsg += ",calendarMembers=" + calendarMembers.size() + "件";

            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     * 表示年月変更
     * */
    public void setYearMonth(boolean isAdd) {
        final String TAG = "setYearMonth";
        String dbMsg = "[HomeFragment]";
        try {
            dbMsg += ",現在" + targetYear +"年"+targetMonth + "月";
            if(isAdd){
                dbMsg += "に加算";
                this.cal.add(Calendar.MONTH, 1);
            }else{
                dbMsg += "から減算";
                this.cal.add(Calendar.MONTH, -1);
            }
            targetYear = this.cal.get(Calendar.YEAR);
            targetMonth = this.cal.get(Calendar.MONTH)+1;
            dbMsg += "＞＞" + targetYear +"年"+targetMonth + "月";
            yearSpinner.setFocusable(false);
            monthSpinner.setFocusable(false);
            setTargetDate(this.cal);
            int startYear = Integer.parseInt(yearAadapter.getItem(0));
            int endYear = Integer.parseInt(yearAadapter.getItem(yearAadapter.getCount()-1));
            dbMsg += ",対象年" + startYear +"～"+endYear + "月";
            if((targetYear == startYear)&&(targetMonth == 1)){
                rewButton.setVisibility(View.GONE);
            }else{
                rewButton.setVisibility(View.VISIBLE);
            }
            if((targetYear == endYear)&&(targetMonth == 12)){
                ffButton.setVisibility(View.GONE);
            }else{
                ffButton.setVisibility(View.VISIBLE);
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     * ここのチェックボックス操作
     * */
    public void setTargetDays(View v) {
        final String TAG = "setTargetDays";
        String dbMsg = "[HomeFragment]";
        try {
            CheckBox cBox = (CheckBox) v;
            int dayInt = Integer.parseInt(cBox.getText().toString());
            dbMsg += ","+ dayInt + "日";                   //taplans:id/c03cBox
            int cID = cBox.getId();
            dbMsg += "[" + cID + "]";               //c00cBox ～　c41cBox
            String rResourceName = cBox.getResources().getResourceName(cID);
            dbMsg += ",ResourceName="+ rResourceName;                   //taplans:id/c03cBox
            ColorDrawable rBackground = (ColorDrawable) cBox.getBackground();
            int colorInt = rBackground.getColor();
            dbMsg += ",colorInt="+ colorInt+"(rBGbaMonth="+ rBGbaMonth + ")";
            int yearInt = Integer.parseInt(yearSpinner.getSelectedItem().toString());
            int monthInt = Integer.parseInt(monthSpinner.getSelectedItem().toString());
            dbMsg += ",当月は="+ yearInt + "年" + monthInt + "月";
            if(colorInt==rBGbaMonth){
                if((0 < dayInt)&&(dayInt < 7) ){
                    dbMsg += ",次月";
                    monthInt=monthInt+1;
                    if(12<monthInt){
                        monthInt=1;
                        yearInt=yearInt+1;
                        dbMsg += ">>"+ yearInt + "年" + monthInt + "月";
                    }
                }else if((25 < dayInt)&&(dayInt < 31) ){
                    dbMsg += ",前月";
                    monthInt=monthInt-1;
                    if(12<monthInt){
                        monthInt=12;
                        yearInt=yearInt-1;
                        dbMsg += ">>"+ yearInt + "年" + monthInt + "月";
                    }
                }
            }
            String selectedDayStr = yearInt + "/";
            if(monthInt < 10){
                selectedDayStr += "0" + monthInt + "/";
            }else{
                selectedDayStr += monthInt + "/";
            }
            if(dayInt < 10){
                selectedDayStr += "0" + dayInt + "/";
            }else{
                selectedDayStr += dayInt;
            }
            dbMsg += ",dateStr=" + selectedDayStr;
            int index = targetDayLidt.indexOf(selectedDayStr);
            dbMsg += ",index=" + index;
            if(cBox.isChecked()) {
                // チェックされた状態の時の処理を記述
                dbMsg += "＞＞登録操作";
                if(index < 0){
                    targetDayLidt.add( selectedDayStr);
                }else{
                    dbMsg += "＞＞登録済み";
                }
            }else {
                // チェックされていない状態の時の処理を記述
                dbMsg += "＞＞削除操作";
                if(-1 < index){
                    targetDayLidt.remove( index);
                }else{
                    dbMsg += "＞＞登録されていない";
                }
            }
            Collections.sort(targetDayLidt);
            dbMsg += ",targetDayLidt=" + targetDayLidt.size() + "件";
            dbMsg += ",targetDayLidt=" + targetDayLidt.toString();
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }


    /**
     * チェックされた日をリスト化してプリファレンスに書き込む
     * */
    public void saveData() {
        final String TAG = "saveData";
        String dbMsg = "[HomeFragment]";
        try {
            dbMsg += ",targetDayLidt=" + targetDayLidt.size() + "件";
            String wStr = targetDayLidt.toString();
//            JSONObject jsonObj = new JSONObject(targetDayLidt.toString());
//            JSONArray items = jsonObj.getJSONArray("users");
//
//            for (int i = 0; i < targetDayLidt.size(); i++) {
//                json.put("tDay",targetDayLidt.get(i));
//            }
//            dbMsg += ",json=" + json.toString();
//            JSONデータの読み取り（Java） https://mjeeeey.hatenablog.com/entry/2020/07/13/215929
//            AndroidでJSONを使う http://blog.chatlune.jp/2019/04/03/post-1187/

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor myEditor = sharedPref.edit();
            myEditor.putString("targetDays", wStr);
            myEditor.apply();

            myLog(TAG , dbMsg);
//        }catch(JSONException ej){
//            myErrorLog(TAG , dbMsg + ";でエラー発生；" + ej);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }


    //ライフサイクル//////////////////////////////////////////////////////////////
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final String TAG = "onCreateView";
        String dbMsg = "[HomeFragment]";
        View root = null;
        try {
            Context con = getActivity();
            pClass=(MainActivity)inflater.getContext();
            dbMsg += "," + con.getResources().getString(R.string.pref_calender_account) +"=" +pClass.calenderAccount;
            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_background) +"=" + pClass.sundayBackground ;
            rBGSunDay = Color.parseColor(pClass.sundayBackground );             //"#FDE7E7"
            dbMsg += "＞rBGSunDay＞" + rBGSunDay;
            dbMsg += "," + con.getResources().getString(R.string.pref_sunday_text_color) +"=" + pClass.sundayTextColor ;
            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_background) +"=" + pClass.satudayBackground ;
            rBGSatuDay = Color.parseColor(pClass.satudayBackground );           //"#EDEDFF"
            dbMsg += "＞rBGSatuDay＞" + rBGSatuDay;
            dbMsg += "," + con.getResources().getString(R.string.pref_satuday_text_color) +"=" + pClass.satudayTextColor ;
            dbMsg += "," + con.getResources().getString(R.string.pref_default_background) +"=" + pClass.defaultBackground ;
            rBGWeekDay = Color.parseColor(pClass.defaultBackground );           //"#FFFFFF"
            dbMsg += "＞rBGWeekDay＞" + rBGWeekDay;
            dbMsg += "," + con.getResources().getString(R.string.pref_default_text_color) +"=" + pClass.defaultTextColor ;
            rBGbaMonth = Color.parseColor("#FFDCDCDC");
            dbMsg += ",当月外=" + rBGbaMonth;

            HomeViewModel homeViewModel =
                    new ViewModelProvider(this).get(HomeViewModel.class);

            binding = FragmentHomeBinding.inflate(inflater, container, false);
            root = binding.getRoot();

            textView = binding.textHome;

            dbMsg += "," + con.getResources().getString(R.string.pref_tDates) +"=" + pClass.targetDays ;
            String rStr = pClass.targetDays.replace("[","");
            rStr = rStr.replace("}","");
            String[] dList = rStr.split(", ");
            targetDayLidt= new ArrayList<>();
            for (int i = 0; i < dList.length; i++) {
                targetDayLidt.add(dList[i]);
            }
            dbMsg += ">>" + targetDayLidt.toString() ;


            dayChecks = new ArrayList<>();           //CheckBox
            dayChecks.add(binding.c00cBox);
            dayChecks.add(binding.c01cBox);
            dayChecks.add(binding.c02cBox);
            dayChecks.add(binding.c03cBox);
            dayChecks.add(binding.c04cBox);
            dayChecks.add(binding.c05cBox);
            dayChecks.add(binding.c06cBox);
            dayChecks.add(binding.c07cBox);
            dayChecks.add(binding.c08cBox);
            dayChecks.add(binding.c09cBox);
            dayChecks.add(binding.c10cBox);
            dayChecks.add(binding.c11cBox);
            dayChecks.add(binding.c12cBox);
            dayChecks.add(binding.c13cBox);
            dayChecks.add(binding.c14cBox);
            dayChecks.add(binding.c15cBox);
            dayChecks.add(binding.c16cBox);
            dayChecks.add(binding.c17cBox);
            dayChecks.add(binding.c18cBox);
            dayChecks.add(binding.c19cBox);
            dayChecks.add(binding.c20cBox);
            dayChecks.add(binding.c21cBox);
            dayChecks.add(binding.c22cBox);
            dayChecks.add(binding.c23cBox);
            dayChecks.add(binding.c24cBox);
            dayChecks.add(binding.c25cBox);
            dayChecks.add(binding.c26cBox);
            dayChecks.add(binding.c27cBox);
            dayChecks.add(binding.c28cBox);
            dayChecks.add(binding.c29cBox);
            dayChecks.add(binding.c30cBox);
            dayChecks.add(binding.c31cBox);
            dayChecks.add(binding.c32cBox);
            dayChecks.add(binding.c33cBox);
            dayChecks.add(binding.c34cBox);
            dayChecks.add(binding.c35cBox);
            dayChecks.add(binding.c36cBox);
            dayChecks.add(binding.c37cBox);
            dayChecks.add(binding.c38cBox);
            dayChecks.add(binding.c39cBox);
            dayChecks.add(binding.c40cBox);
            dayChecks.add(binding.c41cBox);

            dayTexts = new ArrayList<>();           //CheckBox
            dayTexts.add(binding.c00tx);
            dayTexts.add(binding.c01tx);
            dayTexts.add(binding.c02tx);
            dayTexts.add(binding.c03tx);
            dayTexts.add(binding.c04tx);
            dayTexts.add(binding.c05tx);
            dayTexts.add(binding.c06tx);
            dayTexts.add(binding.c07tx);
            dayTexts.add(binding.c08tx);
            dayTexts.add(binding.c09tx);
            dayTexts.add(binding.c10tx);
            dayTexts.add(binding.c11tx);
            dayTexts.add(binding.c12tx);
            dayTexts.add(binding.c13tx);
            dayTexts.add(binding.c14tx);
            dayTexts.add(binding.c15tx);
            dayTexts.add(binding.c16tx);
            dayTexts.add(binding.c17tx);
            dayTexts.add(binding.c18tx);
            dayTexts.add(binding.c19tx);
            dayTexts.add(binding.c20tx);
            dayTexts.add(binding.c21tx);
            dayTexts.add(binding.c22tx);
            dayTexts.add(binding.c23tx);
            dayTexts.add(binding.c24tx);
            dayTexts.add(binding.c25tx);
            dayTexts.add(binding.c26tx);
            dayTexts.add(binding.c27tx);
            dayTexts.add(binding.c28tx);
            dayTexts.add(binding.c29tx);
            dayTexts.add(binding.c30tx);
            dayTexts.add(binding.c31tx);
            dayTexts.add(binding.c32tx);
            dayTexts.add(binding.c33tx);
            dayTexts.add(binding.c34tx);
            dayTexts.add(binding.c35tx);
            dayTexts.add(binding.c36tx);
            dayTexts.add(binding.c37tx);
            dayTexts.add(binding.c38tx);
            dayTexts.add(binding.c39tx);
            dayTexts.add(binding.c40tx);
            dayTexts.add(binding.c41tx);

            yearSpinner = binding.yearSpinner;
            monthSpinner = binding.monthSpinner;
            // 初回起動時の対応
            yearSpinner.setFocusable(false);
            monthSpinner.setFocusable(false);
            wakeUpMethod();
            yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final String TAG = "onItemSelected";
                    String dbMsg = "[yearSpinner]";
                    try {
                        // 初回起動時の動作
                        if (yearSpinner.isFocusable() == false) {
                            // if (mIsFirstBoot) {
                            yearSpinner.setFocusable(true);
                            // mIsFirstBoot = false;
                            dbMsg += "初回起動";
                            return;
                        }else{
                            dbMsg += "初回以降;position~" + position;
                            int selYear = Integer.parseInt((String) yearSpinner.getSelectedItem().toString());
                            dbMsg += "," + selYear + "年";
                            cal.set(selYear, targetMonth, targetDay);
                            //                 cal.add(Calendar.YEAR, targetYear-selYear);
                            dbMsg += ">" + cal.get(Calendar.YEAR)+ "年";
                            setTargetDate(cal);
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    final String TAG = "onNothingSelected";
                    String dbMsg = "[yearSpinner]";
                    try {
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final String TAG = "onItemSelected";
                    String dbMsg = "[monthSpinner]";
                    try {
                        // 初回起動時の動作
                        if (monthSpinner.isFocusable() == false) {
                            // if (mIsFirstBoot) {
                            monthSpinner.setFocusable(true);
                            // mIsFirstBoot = false;
                            dbMsg += "初回起動";
                            return;
                        }else{
                            dbMsg += ",現在" + targetMonth + "月";
                            dbMsg += ">>position=" + position;
                            int selMonth = Integer.parseInt((String) monthSpinner.getSelectedItem().toString());
                            dbMsg += "," + selMonth + "月";
                            cal.set(targetYear, selMonth-1, targetDay);
                            dbMsg += ">" + (cal.get(Calendar.MONTH)+1)+ "月";
                            monthSpinner.setFocusable(false);
                            setTargetDate(cal);
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    final String TAG = "onNothingSelected";
                    String dbMsg = "[monthSpinner]";
                    try {
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            rewButton = binding.rewButton;
            rewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[rewButton]";
                    try {
                        setYearMonth(false);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ffButton = binding.ffButton;
            ffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[ffButton]";
                    try {
                        setYearMonth(true);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ImageButton saveButton = binding.saveButton;
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[saveButton]";
                    try {
                        saveData();
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

//            homeViewModel.getText().observe(getViewLifecycl
//            eOwner(), textView::setText);
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
        String dbMsg = "[HomeFragment]";
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