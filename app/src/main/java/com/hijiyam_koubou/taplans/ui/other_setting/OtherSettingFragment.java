package com.hijiyam_koubou.taplans.ui.other_setting;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.hijiyam_koubou.taplans.databinding.FragmentSlideshowBinding;
import com.hijiyam_koubou.taplans.MainActivity;
import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentOtherSettingBinding;
import com.hijiyam_koubou.taplans.databinding.FragmentTargetSettingBinding;

public class OtherSettingFragment extends Fragment {


    private MainActivity pClass;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor myEditor;

    private EditText tEventNameEt;             //  予定の名称
    private Spinner tEventSoundSP;           // アラーム音

    private ImageButton tSoundPlayBT;

    private Button tArarmTime1BT;
    //   private EditText tArarmTime1Et;           // アラーム時刻1
    private CheckBox ta100cBox;               // アラーム時刻1 の日曜
    private CheckBox ta101cBox;               // アラーム時刻1 の月曜
    private CheckBox ta102cBox;               // アラーム時刻1 の火曜
    private CheckBox ta103cBox;               // アラーム時刻1 の水曜
    private CheckBox ta104cBox;               // アラーム時刻1 の木曜
    private CheckBox ta105cBox;               // アラーム時刻1 の金曜
    private CheckBox ta106cBox;               // アラーム時刻1 の土曜
    private CheckBox ta107cBox;               // アラーム時刻1 の祝日

    private Button tArarmTime2BT;          // アラーム時刻2
    private CheckBox ta200cBox;               // アラーム時刻2 の日曜
    private CheckBox ta201cBox;               // アラーム時刻2 の月曜
    private CheckBox ta202cBox;               // アラーム時刻2 の火曜
    private CheckBox ta203cBox;               // アラーム時刻2 の水曜
    private CheckBox ta204cBox;               // アラーム時刻2 の木曜
    private CheckBox ta205cBox;               // アラーム時刻2 の金曜
    private CheckBox ta206cBox;               // アラーム時刻2 の土曜
    private CheckBox ta207cBox;               // アラーム時刻2 の祝日

    private Button tArarmTime3BT;          // アラーム時刻3
    private CheckBox ta300cBox;               // アラーム時刻3 の日曜
    private CheckBox ta301cBox;               // アラーム時刻3 の月曜
    private CheckBox ta302cBox;               // アラーム時刻3 の火曜
    private CheckBox ta303cBox;               // アラーム時刻3 の水曜
    private CheckBox ta304cBox;               // アラーム時刻3 の木曜
    private CheckBox ta305cBox;               // アラーム時刻3 の金曜
    private CheckBox ta306cBox;               // アラーム時刻3 の土曜
    private CheckBox ta307cBox;               // アラーム時刻3 の祝日

    //Googleカレンダー連携
    public Switch googleCalAlignmentSW;      //Googleカレンダー連携
    public EditText gcaSubjectET;         //予定の名称と同じ"
    public Button gcaStarttimeBT;          //開始時刻
    public Button gcaEndtimeBT;          //終了時刻
    public Switch gcaEnddateSW;            //終日
    public EditText gcaDescriptionET;             //説明・メモ
    public EditText gcaLocationET;            //予定の場所
    public Spinner gcaColorIdSP;         //予定の色
    public CheckBox gcaIsPrivateCB;            //予定を限定公開にする"/>

    private FragmentOtherSettingBinding binding;
    private Ringtone ringtone;
    /**
     * Viewが生成され始めた時に呼ばれるメソッド
     * */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OtherSettingViewModel  otherSettingViewModel =
                new ViewModelProvider(this).get(OtherSettingViewModel.class);

        binding = FragmentOtherSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        otherSettingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    /**
     * 中身のFragmentがひゃきされた時
     * */
    @Override
    public void onDestroyView() {
        final String TAG = "onDestroyView";
        String dbMsg = "[OtherSettingFragment]";
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