package com.hijiyam_koubou.taplans.ui.other_setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.hijiyam_koubou.taplans.databinding.FragmentSlideshowBinding;
import com.hijiyam_koubou.taplans.CustomAdapter;
import com.hijiyam_koubou.taplans.GoogleCalendarColors;
import com.hijiyam_koubou.taplans.MainActivity;
import com.hijiyam_koubou.taplans.R;
import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentOtherSettingBinding;
import com.hijiyam_koubou.taplans.databinding.FragmentTargetSettingBinding;
import com.hijiyam_koubou.taplans.soundItem;
import com.hijiyam_koubou.taplans.ui.target_setting.TargetPlanViewModel;

import java.util.List;

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
     * 指定されたKeyのStringPreferenceを作成/更新
     * */
    public void setStrPref(String key,String wStr) {
        //テキスト変更後
        final String TAG = "setStrPref";
        String dbMsg = "[OtherPlanFragment]";
        try {
            dbMsg += "key=" + key;
            dbMsg += ",wStr=" + wStr;
            if(sharedPref == null){
                sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            }
            if(myEditor == null){
                myEditor = sharedPref.edit();
            }
            myEditor.putString(key, wStr);
            boolean ret = myEditor.commit();
            dbMsg += ",commit=" + ret;
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     * Googleカレンダー関連項目の有効化制御
     * */
    public void setGoogleCalItems(Boolean isEnabled) {
        //テキスト変更後
        final String TAG = "googleCalAlignmentSW";
        String dbMsg = "[OtherPlanFragment]";
        try {
            dbMsg += "isEnabled=" + isEnabled;
            if(isEnabled){
                gcaSubjectET.setEnabled(true);         //予定の名称と同じ"
                gcaStarttimeBT.setEnabled(true);          //開始時刻
                gcaEndtimeBT.setEnabled(true);         //終了時刻
                gcaEnddateSW.setEnabled(true);            //終日
                gcaDescriptionET.setEnabled(true);             //説明・メモ
                gcaLocationET.setEnabled(true);             //予定の場所
                gcaColorIdSP.setEnabled(true);         //予定の色
                //    gcaIsPrivateCB.setEnabled(true);            //予定を限定公開にする"/>
            }else{
                gcaSubjectET.setEnabled(false);         //予定の名称と同じ"
                gcaStarttimeBT.setEnabled(false);          //開始時刻
                gcaEndtimeBT.setEnabled(false);         //終了時刻
                gcaEnddateSW.setEnabled(false);            //終日
                gcaDescriptionET.setEnabled(false);             //説明・メモ
                gcaLocationET.setEnabled(false);             //予定の場所
                gcaColorIdSP.setEnabled(false);         //予定の色
                //   gcaIsPrivateCB.setEnabled(false);            //予定を限定公開にする"/>
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    /**
     * Googleカレンダー開始・終了の有効化制御
     * */
    public void setGoogleCalSETimes(Boolean isEnabled) {
        //テキスト変更後
        final String TAG = "setGoogleCalSETimes";
        String dbMsg = "[OtherPlanFragment]";
        try {
            dbMsg += "isEnabled=" + isEnabled;
            if(isEnabled){
                gcaStarttimeBT.setEnabled(false);          //開始時刻
                gcaEndtimeBT.setEnabled(false);         //終了時刻
            }else{
                gcaStarttimeBT.setEnabled(true);          //開始時刻
                gcaEndtimeBT.setEnabled(true);         //終了時刻
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }


    /**
     * Googleカレンダーの色を反映させる部分
     * */
    public void setGoogleColorMoniter(int selPosition) {
        //テキスト変更後
        final String TAG = "setGoogleColorMoniter";
        String dbMsg = "[OtherPlanFragment]";
        try {
            dbMsg += "selPosition=" + selPosition;
            GoogleCalendarColors selColor =  pClass.googleCalendarColorList.get(selPosition);
            dbMsg += "[" + selColor.colorId+ "]" + selColor.colorName;
            dbMsg += "," + selColor.colorResId+"," + selColor.HEXStr+"," + selColor.FontColorStr;;
            gcaSubjectET.setBackgroundColor(Color.parseColor(selColor.HEXStr));
            gcaSubjectET.setTextColor(Color.parseColor(selColor.FontColorStr));
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final String TAG = "onCreateView";
        String dbMsg = "[OtherPlanFragment]";
        View root = null;
        try {
            OtherSettingViewModel otherSettingViewModel =
                    new ViewModelProvider(this).get(OtherSettingViewModel.class);
            binding = FragmentOtherSettingBinding.inflate(inflater, container, false);
            root = binding.getRoot();
            final TextView textView = binding.textSlideshow;

            Context con = getActivity();
            pClass=(MainActivity)inflater.getContext();
            dbMsg += "," + con.getResources().getString(R.string.sett_event_name) +"=" +pClass.ohEventName;

            tEventNameEt = binding.tEventNameEt;           // 予定の名称
            dbMsg += ",予定の名称="+pClass.ohEventName;
            tEventNameEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //テキスト変更前
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //テキスト変更中
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //テキスト変更後
                    final String TAG = "afterTextChanged";
                    String dbMsg = "[tEventNameEt]";
                    try {
                        dbMsg += "予定の名称=";
                        if(s.toString() != null){
                            pClass.ohEventName= s.toString();
                            dbMsg += pClass.ohEventName;
                            setStrPref("ohEventName",pClass.ohEventName);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            tEventNameEt.setText(pClass.tEventName);

            tEventSoundSP = binding.tEventSoundSP;           // アラーム音
            tSoundPlayBT = binding.tSoundPlayBT;
            ArrayAdapter soundArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, pClass.soundNameList);
            tEventSoundSP.setAdapter(soundArrayAdapter);
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"名称=" + pClass.ohEventSoundName ;
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + pClass.ohEventSoundURi ;
            //  spPosision=pClass.soundNameList.indexOf((String)pClass.ohEventSoundName );

            int spPosition = (Integer)soundArrayAdapter.getPosition((String)pClass.ohEventSoundName );
            dbMsg += "," + spPosition +"番目";
            tEventSoundSP.setSelection(spPosition);
            tEventSoundSP.setFocusable(false);
            tEventSoundSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final String TAG = "onItemSelected";
                    String dbMsg = "[tEventSoundSP]";
                    try {
                        dbMsg += "id=" + id;
                        dbMsg += ",position=" + position;
                        // 初回起動時の動作
                        if (tEventSoundSP.isFocusable() == false) {
                            tEventSoundSP.setFocusable(true);
                            dbMsg += ",初回起動";
                        }else{
                            dbMsg += ",初回以降の動作";
                            dbMsg += ",名称＝" + pClass.ohEventSoundName + ",URI=" + pClass.ohEventSoundURi ;
                            soundItem sItem = pClass.setSoundItem(position,"ohEventSoundName","ohEventSoundURi");
                            dbMsg += ">>"+ pClass.ohEventSoundName + "," + pClass.ohEventSoundURi ;
                            tSoundPlayBT.setVisibility(View.VISIBLE);
                            if(ringtone!=null && ringtone.isPlaying()){
                                dbMsg += ringtone.getTitle(getActivity()) + "を" + ringtone.getVolume() + "から";
                                ringtone.stop();
                                dbMsg += ">>停止";
                            }
                            ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(pClass.ohEventSoundURi));
                            dbMsg += ringtone.getTitle(getActivity()) + "を" + ringtone.getVolume() + "で作成";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            tSoundPlayBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tSoundPlayBT]";
                    try {
                        dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + pClass.ohEventSoundURi ;
                        if(pClass.ohEventSoundURi != null){
                            dbMsg += ringtone.getTitle(getActivity()) + "を" + ringtone.getVolume() + "で";
                            if(ringtone.isPlaying()){
                                ringtone.stop();
                                dbMsg += ">>停止";
                            }else{
                                ringtone.play();
                                dbMsg += ">>再生";
                            }
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            if(pClass.ohEventSoundURi == null){
                tSoundPlayBT.setVisibility(View.GONE );
            }

            dbMsg += "アラーム時刻1="+ pClass.ohArarmTime1;
            tArarmTime1BT = binding.tArarmTime1BT;           // アラーム時刻1
            tArarmTime1BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tArarmTime1BT]";
                    try {
                        dbMsg += "アラーム時刻1=" +  pClass.ohArarmTime1;
                        pClass.showTimePicker("ohArarmTime1",pClass.ohArarmTime1,tArarmTime1BT);
//                        tArarmTime1BT.setText(OtherSettingFragment.this.prefValue);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            pClass.setButtonText(tArarmTime1BT,pClass.tArarmTime1);

            ta100cBox = binding.ta100cBox;               // アラーム時刻1 の日曜
            ta100cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta100cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        dbMsg += "アラーム時刻1 の日曜=" + pClass.oh100c;
                        pClass.setWeekCheck(chBox,isChecked,"oh100c",ta200cBox,"oh200c",ta300cBox,"oh300c" );
                        if(isChecked){
                            pClass.oh100c=true;
                            pClass.oh200c=false;
                            pClass.oh300c=false;
                        }else{
                            pClass.oh100c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta101cBox = binding.ta101cBox;               // アラーム時刻1 の月曜
            ta101cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta101cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        dbMsg += "アラーム時刻1 の月曜を" + isChecked + "に";
                        pClass.setWeekCheck(chBox,isChecked,"oh101c",ta201cBox,"oh201c",ta301cBox,"oh301c" );
                        if(isChecked){
                            pClass.oh101c=true;
                            pClass.oh201c=false;
                            pClass.oh301c=false;
                        }else{
                            pClass.oh101c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta102cBox = binding.ta102cBox;               // アラーム時刻1 の火曜
            ta102cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[oh102cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh102c",ta202cBox,"oh202c",ta302cBox,"oh302c" );
                        if(isChecked){
                            pClass.oh102c=true;
                            pClass.oh202c=false;
                            pClass.oh302c=false;
                        }else{
                            pClass.oh102c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta103cBox = binding.ta103cBox;               // アラーム時刻1 の水曜
            ta103cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta103cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh103c",ta203cBox,"oh203c",ta303cBox,"oh303c" );
                        if(isChecked){
                            pClass.oh103c=true;
                            pClass.oh203c=false;
                            pClass.oh303c=false;
                        }else{
                            pClass.oh103c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta104cBox = binding.ta104cBox;               // アラーム時刻1 の木曜
            ta104cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta104cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh104c",ta204cBox,"oh204c",ta304cBox,"oh304c" );
                        if(isChecked){
                            pClass.oh104c=true;
                            pClass.oh204c=false;
                            pClass.oh304c=false;
                        }else{
                            pClass.oh104c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta105cBox = binding.ta105cBox;               // アラーム時刻1 の金曜
            ta105cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta105cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chkbox,isChecked,"oh105c",ta205cBox,"oh205c",ta305cBox,"oh305c" );
                        if(isChecked){
                            pClass.oh105c=true;
                            pClass.oh205c=false;
                            pClass.oh305c=false;
                        }else{
                            pClass.oh105c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta106cBox = binding.ta106cBox;               // アラーム時刻1 の土曜
            ta106cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta106cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chkbox,isChecked,"oh106c",ta206cBox,"oh206c",ta306cBox,"oh306c" );
                        if(isChecked){
                            pClass.oh106c=true;
                            pClass.oh206c=false;
                            pClass.oh306c=false;
                        }else{
                            pClass.oh106c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta107cBox = binding.ta107cBox;
            ta107cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta106cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chkbox,isChecked,"oh107c",ta207cBox,"oh207c",ta307cBox,"oh307c" );
                        if(isChecked){
                            pClass.oh107c=true;
                            pClass.oh207c=false;
                            pClass.oh307c=false;
                        }else{
                            pClass.oh107c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            dbMsg += "アラーム時刻2="+ pClass.ohArarmTime2;
            tArarmTime2BT = binding.tArarmTime2BT;           // アラーム時刻2
            tArarmTime2BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tArarmTime1BT]";
                    try {
                        dbMsg += "アラーム時刻2=" +  pClass.ohArarmTime2;
                        pClass.showTimePicker("ohArarmTime2",pClass.ohArarmTime2,tArarmTime2BT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            pClass.setButtonText(tArarmTime2BT,pClass.ohArarmTime2);

            ta200cBox = binding.ta200cBox;               // アラーム時刻2 の日曜
            ta200cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta200cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chkbox,isChecked,"oh200c",ta100cBox,"oh100c",ta300cBox,"oh300c" );
                        if(isChecked){
                            pClass.oh200c=true;
                            pClass.oh100c=false;
                            pClass.oh300c=false;
                        }else{
                            pClass.oh200c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta201cBox = binding.ta201cBox;               // アラーム時刻2 の月曜
            ta201cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta201cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh201c",ta101cBox,"oh101c",ta301cBox,"oh301c" );
                        if(isChecked){
                            pClass.oh201c=true;
                            pClass.oh101c=false;
                            pClass.oh301c=false;
                        }else{
                            pClass.oh201c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta202cBox = binding.ta202cBox;               // アラーム時刻2 の火曜
            ta202cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta202cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh202c",ta102cBox,"oh102c",ta302cBox,"oh302c" );
                        if(isChecked){
                            pClass.oh202c=true;
                            pClass.oh102c=false;
                            pClass.oh302c=false;
                        }else{
                            pClass.oh202c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta203cBox = binding.ta203cBox;               // アラーム時刻2 の水曜
            ta203cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta203cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh203c",ta103cBox,"oh103c",ta303cBox,"oh303c" );
                        if(isChecked){
                            pClass.oh203c=true;
                            pClass.oh103c=false;
                            pClass.oh303c=false;
                        }else{
                            pClass.oh203c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta204cBox = binding.ta204cBox;               // アラーム時刻2 の木曜
            ta204cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta204cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh204c",ta104cBox,"oh104c",ta304cBox,"oh304c" );
                        if(isChecked){
                            pClass.oh204c=true;
                            pClass.oh104c=false;
                            pClass.oh304c=false;
                        }else{
                            pClass.oh204c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta205cBox = binding.ta205cBox;               // アラーム時刻2 の金曜
            ta205cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta205cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh205c",ta105cBox,"oh105c",ta305cBox,"oh305c" );
                        if(isChecked){
                            pClass.oh205c=true;
                            pClass.oh105c=false;
                            pClass.oh305c=false;
                        }else{
                            pClass.oh205c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta206cBox = binding.ta206cBox;               // アラーム時刻2 の土曜
            ta206cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta206cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh206c",ta106cBox,"oh106c",ta306cBox,"oh306c" );
                        if(isChecked){
                            pClass.oh206c=true;
                            pClass.oh106c=false;
                            pClass.oh306c=false;
                        }else{
                            pClass.oh206c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta207cBox = binding.ta207cBox;
            ta207cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta106cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh207c",ta107cBox,"oh107c",ta307cBox,"oh307c" );
                        if(isChecked){
                            pClass.oh207c=true;
                            pClass.oh107c=false;
                            pClass.oh307c=false;
                        }else{
                            pClass.oh207c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            dbMsg += "アラーム時刻3="+ pClass.ohArarmTime3;
            tArarmTime3BT = binding.tArarmTime3BT;           // アラーム時刻2
            tArarmTime3BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tArarmTime3BT]";
                    try {
                        dbMsg += "アラーム時刻3=" +  pClass.ohArarmTime3;
                        pClass.showTimePicker("ohArarmTime3",pClass.ohArarmTime3,tArarmTime3BT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            pClass.setButtonText(tArarmTime3BT,pClass.ohArarmTime3);

            ta300cBox = binding.ta300cBox;               // アラーム時刻2 の日曜
            ta300cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta300cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chkbox,isChecked,"oh300c",ta100cBox,"oh100c",ta200cBox,"oh200c" );
                        if(isChecked){
                            pClass.oh300c=true;
                            pClass.oh100c=false;
                            pClass.oh200c=false;
                        }else{
                            pClass.oh300c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta301cBox = binding.ta301cBox;               // アラーム時刻2 の月曜
            ta301cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta301cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh301c",ta101cBox,"oh101c",ta201cBox,"oh201c" );
                        if(isChecked){
                            pClass.oh301c=true;
                            pClass.oh101c=false;
                            pClass.oh201c=false;
                        }else{
                            pClass.oh301c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta302cBox = binding.ta302cBox;               // アラーム時刻2 の火曜
            ta302cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta302cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh302c",ta102cBox,"oh102c",ta202cBox,"oh202c" );
                        if(isChecked){
                            pClass.oh302c=true;
                            pClass.oh102c=false;
                            pClass.oh202c=false;
                        }else{
                            pClass.oh302c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta303cBox = binding.ta303cBox;               // アラーム時刻2 の水曜
            ta303cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta303cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh303c",ta103cBox,"oh103c",ta203cBox,"oh203c" );
                        if(isChecked){
                            pClass.oh303c=true;
                            pClass.oh103c=false;
                            pClass.oh203c=false;
                        }else{
                            pClass.oh303c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta304cBox = binding.ta304cBox;               // アラーム時刻2 の木曜
            ta304cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta304cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh304c",ta104cBox,"oh104c",ta204cBox,"oh204c" );
                        if(isChecked){
                            pClass.oh304c=true;
                            pClass.oh104c=false;
                            pClass.oh204c=false;
                        }else{
                            pClass.oh304c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta305cBox = binding.ta305cBox;               // アラーム時刻2 の金曜
            ta305cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta305cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh305c",ta105cBox,"oh105c",ta205cBox,"oh205c" );
                        if(isChecked){
                            pClass.oh305c=true;
                            pClass.oh105c=false;
                            pClass.oh205c=false;
                        }else{
                            pClass.oh305c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta306cBox = binding.ta306cBox;               // アラーム時刻2 の土曜
            ta306cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta306cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh306c",ta106cBox,"oh106c",ta206cBox,"oh206c" );
                        if(isChecked){
                            pClass.oh306c=true;
                            pClass.oh106c=false;
                            pClass.oh206c=false;
                        }else{
                            pClass.oh306c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta307cBox = binding.ta307cBox;
            ta307cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta307cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"oh307c",ta107cBox,"oh107c",ta207cBox,"oh207c" );
                        if(isChecked){
                            pClass.oh307c=true;
                            pClass.oh107c=false;
                            pClass.oh207c=false;
                        }else{
                            pClass.oh307c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            googleCalAlignmentSW=binding.googleCalAlignmentSW;      //Googleカレンダー連携
            googleCalAlignmentSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[googleCalAlignmentSW]";
                    try {
                        Switch chBox=(Switch)buttonView;
                        dbMsg += ",Googleカレンダー連携=" + pClass.ohGCAlignment;
                        dbMsg += ">>" + isChecked;
                        pClass.ohGCAlignment=isChecked;
                        pClass.setBoolPref("ohGCAlignment",isChecked);
                        setGoogleCalItems(isChecked);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            gcaSubjectET = binding.gcaSubjectET;
            dbMsg += ",登録する名称="+pClass.ohGCSubject;
            gcaSubjectET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //テキスト変更前
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //テキスト変更中
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //テキスト変更後
                    final String TAG = "afterTextChanged";
                    String dbMsg = "[gcaSubjectET]";
                    try {
                        dbMsg += "登録する名称=";
                        if(s.toString() != null){
                            pClass.ohGCSubject= s.toString();
                            dbMsg += pClass.ohGCSubject;
                            setStrPref("ohGCSubject",pClass.ohGCSubject);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            gcaSubjectET.setText(pClass.ohGCSubject);

            gcaStarttimeBT = binding.gcaStarttimeBT;         //開始時刻
            gcaStarttimeBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[gcaStarttimeBT]";
                    try {
                        dbMsg += "開始時刻=" +  pClass.ohGCStarttime;
                        pClass.showTimePicker("ohGCStarttime",pClass.ohGCStarttime,gcaStarttimeBT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            pClass.setButtonText(gcaStarttimeBT,pClass.ohGCStarttime);

            gcaEndtimeBT = binding.gcaEndtimeBT;          //終了時刻
            gcaEndtimeBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[gcaEndtimeBT]";
                    try {
                        dbMsg += "終了時刻=" +  pClass.ohGCEndtime;
                        pClass.showTimePicker("ohGCEndtime",pClass.ohGCEndtime,gcaEndtimeBT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            pClass.setButtonText(gcaEndtimeBT,pClass.ohGCEndtime);

            gcaEnddateSW = binding.gcaEnddateSW;            //終日
            gcaEnddateSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[gcaEnddateSW]";
                    try {
                        Switch chBox=(Switch)buttonView;
                        dbMsg += ",終日=" + pClass.ohGCEnddate;
                        dbMsg += ">>" + isChecked;
                        pClass.setBoolPref("ohGCEnddate",isChecked);
                        pClass.ohGCEnddate=isChecked;
                        setGoogleCalSETimes(isChecked);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            gcaDescriptionET = binding.gcaDescriptionET;
            dbMsg += ",説明・メモ="+pClass.ohGCDescription;
            gcaDescriptionET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //テキスト変更前
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //テキスト変更中
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //テキスト変更後
                    final String TAG = "afterTextChanged";
                    String dbMsg = "[gcaDescriptionET]";
                    try {
                        dbMsg += "説明・メモ=";
                        if(s.toString() != null){
                            pClass.ohGCDescription= s.toString();
                            dbMsg += pClass.ohGCDescription;
                            setStrPref("ohGCDescription",pClass.ohGCDescription);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            gcaDescriptionET.setText(pClass.ohGCDescription);

            gcaLocationET = binding.gcaLocationET;            //予定の場所
            dbMsg += "予定の場所="+pClass.ohGCLocation;
            gcaLocationET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //テキスト変更前
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //テキスト変更中
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //テキスト変更後
                    final String TAG = "afterTextChanged";
                    String dbMsg = "[gcaLocationET]";
                    try {
                        dbMsg += "予定の場所=";
                        if(s.toString() != null){
                            pClass.ohGCLocation= s.toString();
                            dbMsg += pClass.ohGCLocation;
                            pClass.setStrPref("ohGCLocation",pClass.ohGCLocation);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            gcaLocationET.setText(pClass.ohGCLocation);

            gcaColorIdSP = binding.gcaColorIdSP;         //予定の色
            ArrayAdapter colorArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, (List) pClass.colorNameList);
//            gcaColorIdSP.setAdapter(colorArrayAdapter);
            String[] colorNameArray =pClass.colorNameList.toArray(new String[pClass.colorNameList.size()]);
            CustomAdapter customAdapter = new CustomAdapter( getActivity(), android.R.layout.simple_spinner_dropdown_item, colorNameArray, pClass.googleCalendarColorList );
            gcaColorIdSP.setAdapter(customAdapter);

            dbMsg += "," + getResources().getString(R.string.gc_color) +"名称=" + pClass.ohGCColorName;           // 予定の色名称
            dbMsg += "," + getResources().getString(R.string.gc_color) +"rID=" + pClass.ohGCColorRss;           // 予定の色リソースID
            int cspPosition = pClass.colorNameList.indexOf((String)pClass.ohGCColorName);
            //  int cspPosition = (Integer)colorArrayAdapter.getPosition((String)pClass.ohGCColorName );
            dbMsg += "," + cspPosition +"番目を選択";
            gcaColorIdSP.setSelection(cspPosition);
            gcaColorIdSP.setFocusable(false);
            gcaColorIdSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final String TAG = "onItemSelected";
                    String dbMsg = "[gcaColorIdSP]";
                    try {
                        dbMsg += "id=" + id;
                        dbMsg += ",position=" + position;
                        // 初回起動時の動作
                        if (!gcaColorIdSP.isFocusable()) {
                            gcaColorIdSP.setFocusable(true);
                            dbMsg += ",初回起動";
                        }else{
                            dbMsg += ",初回以降の動作";
                            dbMsg += ",名称＝" + pClass.ohGCColorName + ",resID=" + pClass.ohGCColorRss ;
                            GoogleCalendarColors sCololr = pClass.googleCalendarColorList.get(position);
                            pClass.ohGCColorName = sCololr.colorName;
                            pClass.setStrPref("ohGCColorName",pClass.ohGCColorName);
                            pClass.ohGCColorRss = sCololr.colorResId;
                            pClass.setIntPref("ohGCColorRss",pClass.ohGCColorRss);
                            dbMsg += ">>" + pClass.ohGCColorName + ",resID=" + pClass.ohGCColorRss ;
                            setGoogleColorMoniter(position);
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            setGoogleColorMoniter(cspPosition);

//            gcaIsPrivateCB = binding.gcaIsPrivateCB;            //予定を限定公開にする
//            gcaIsPrivateCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                // チェック状態が変更された時のハンドラ
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    final String TAG = "onCheckedChanged";
//                    String dbMsg = "[gcaIsPrivateCB]";
//                    try {
//                        CheckBox chBox=(CheckBox)buttonView;
//                        dbMsg += ",gcaIsPrivate=" + pClass.gcaIsPrivate;
//                        dbMsg += ">>" + isChecked;
//                        pClass.gcaIsPrivate=isChecked;
//                        pClass.setBoolPref("pClass",isChecked);
//                        myLog(TAG , dbMsg);
//                    } catch (Exception er) {
//                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
//                    }
//                }
//            });
//            gcaIsPrivateCB.setChecked(pClass.gcaIsPrivate);

            dbMsg += "アラーム時刻1 の日曜=" + pClass.oh100c;
            ta100cBox.setChecked(pClass.oh100c);
            dbMsg += "アラーム時刻1 の月曜=" + pClass.oh101c;
            ta101cBox.setChecked(pClass.oh101c);
            dbMsg += "アラーム時刻1 の火曜=" + pClass.oh102c;
            ta102cBox.setChecked(pClass.oh102c);
            dbMsg += "アラーム時刻1 の水曜=" + pClass.oh103c;
            ta103cBox.setChecked(pClass.oh103c);
            dbMsg += "アラーム時刻1 の木曜=" + pClass.oh104c;
            ta104cBox.setChecked(pClass.oh104c);
            dbMsg += "アラーム時刻1 の金曜=" + pClass.oh105c;
            ta105cBox.setChecked(pClass.oh105c);
            dbMsg += "アラーム時刻1 の土曜=" + pClass.oh106c;
            ta106cBox.setChecked(pClass.oh106c);
            dbMsg += "アラーム時刻1 の祝日=" + pClass.oh107c;
            ta107cBox.setChecked(pClass.oh107c);

            dbMsg += "アラーム時刻2 の日曜=" + pClass.oh200c;
            ta200cBox.setChecked(pClass.oh200c);
            dbMsg += "アラーム時刻2 の月曜=" + pClass.oh201c;
            ta201cBox.setChecked(pClass.oh201c);
            dbMsg += "アラーム時刻2 の火曜=" + pClass.oh202c;
            ta202cBox.setChecked(pClass.oh202c);
            dbMsg += "アラーム時刻2 の水曜=" + pClass.oh203c;
            ta203cBox.setChecked(pClass.oh203c);
            dbMsg += "アラーム時刻2 の木曜=" + pClass.oh204c;
            ta204cBox.setChecked(pClass.oh204c);
            dbMsg += "アラーム時刻2 の金曜=" + pClass.oh205c;
            ta205cBox.setChecked(pClass.oh205c);
            dbMsg += "アラーム時刻2 の土曜=" + pClass.oh206c;
            ta206cBox.setChecked(pClass.oh206c);
            dbMsg += "アラーム時刻1 の祝日=" + pClass.oh207c;
            ta207cBox.setChecked(pClass.oh207c);

            dbMsg += "アラーム時刻3 の日曜=" + pClass.oh300c;
            ta300cBox.setChecked(pClass.oh300c);
            dbMsg += "アラーム時刻3 の月曜=" + pClass.oh301c;
            ta301cBox.setChecked(pClass.oh301c);
            dbMsg += "アラーム時刻3 の火曜=" + pClass.oh302c;
            ta302cBox.setChecked(pClass.oh302c);
            dbMsg += "アラーム時刻3 の水曜=" + pClass.oh303c;
            ta303cBox.setChecked(pClass.oh303c);
            dbMsg += "アラーム時刻3 の木曜=" + pClass.oh304c;
            ta304cBox.setChecked(pClass.oh304c);
            dbMsg += "アラーム時刻3 の金曜=" + pClass.oh305c;
            ta305cBox.setChecked(pClass.oh305c);
            dbMsg += "アラーム時刻3 の土曜=" + pClass.oh306c;
            ta306cBox.setChecked(pClass.oh306c);
            dbMsg += "アラーム時刻3 の祝日=" + pClass.oh307c;
            ta307cBox.setChecked(pClass.oh307c);

            gcaEnddateSW.setChecked(pClass.ohGCEnddate);
            setGoogleCalSETimes(pClass.ohGCEnddate);
            googleCalAlignmentSW.setChecked(pClass.ohGCAlignment);
            setGoogleCalItems(pClass.ohGCAlignment);

            tEventNameEt.setFocusable(true);
//            OtherSettingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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