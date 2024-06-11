package com.hijiyam_koubou.taplans.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentHomeBinding;

import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private Spinner yearSpinner;
    private ArrayAdapter<String> yearAadapter;
    private int targetYear;
    private Spinner monthSpinner ;
    private int targetMonth;
    private int targetDay;

    private TextView textView;


    /**
     * Dateクラスによる現在時表示>>表示対象日
     * */
    public Date targetDate;
    /**
    *デフォルトのCalendarオブジェクト
    * */
    public Calendar cal;    //

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
            yearSpinner.setSelection(ysPisition);

            targetMonth = setCal.get(Calendar.MONTH)+1;
            dbMsg +=targetMonth + "月";
            monthSpinner.setSelection(targetMonth-1);

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
            textView.setText(dbMsg);

            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     * 表示年月変更
     * */
    public void setYearMonth() {
        final String TAG = "setYearMonth";
        String dbMsg = "[HomeFragment]";
        View root = null;
        try {
            myLog(TAG , dbMsg);
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
            HomeViewModel homeViewModel =
                    new ViewModelProvider(this).get(HomeViewModel.class);

            binding = FragmentHomeBinding.inflate(inflater, container, false);
            root = binding.getRoot();

            textView = binding.textHome;
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
                   //         cal.add(Calendar.DAY_OF_MONTH, targetMonth-selMonth);
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