package com.hijiyam_koubou.taplans.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            this.targetDate = new Date();
            dbMsg += ",起動時は"+ targetDate.toString();
            this.cal = Calendar.getInstance();
            int tYear = this.cal.get(Calendar.YEAR);
            dbMsg += ","+ tYear + "年";
            int tMonth = this.cal.get(Calendar.MONTH)+1;
            dbMsg += tMonth + "月";
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
            int tYear = setCal.get(Calendar.YEAR);
            dbMsg += ","+ tYear + "年";
            int tMonth = setCal.get(Calendar.MONTH)+1;
            dbMsg += tMonth + "月";
            int tDate = setCal.get(Calendar.DATE);
            dbMsg += tDate + "日";
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
            wakeUpMethod();

//            homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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