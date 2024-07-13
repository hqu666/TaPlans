package com.hijiyam_koubou.taplans.ui.target_setting;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.hijiyam_koubou.taplans.GoogleCalendarColors;
import com.hijiyam_koubou.taplans.MainActivity;
import com.hijiyam_koubou.taplans.R;
import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentTargetSettingBinding;
import com.hijiyam_koubou.taplans.soundItem;

import java.util.List;

public class TargetPlanFragment extends Fragment {
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

    private FragmentTargetSettingBinding binding;
    private Ringtone ringtone;

    /**
     * 指定されたKeyのStringPreferenceを作成/更新
     * */
    public void setStrPref(String key,String wStr) {
        //テキスト変更後
        final String TAG = "setStrPref";
        String dbMsg = "[TargetPlanFragment]";
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
        String dbMsg = "[TargetPlanFragment]";
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
                gcaIsPrivateCB.setEnabled(true);            //予定を限定公開にする"/>
            }else{
                gcaSubjectET.setEnabled(false);         //予定の名称と同じ"
                gcaStarttimeBT.setEnabled(false);          //開始時刻
                gcaEndtimeBT.setEnabled(false);         //終了時刻
                gcaEnddateSW.setEnabled(false);            //終日
                gcaDescriptionET.setEnabled(false);             //説明・メモ
                gcaLocationET.setEnabled(false);             //予定の場所
                gcaColorIdSP.setEnabled(false);         //予定の色
                gcaIsPrivateCB.setEnabled(false);            //予定を限定公開にする"/>
            }
            myLog(TAG , dbMsg);
        } catch (Exception er) {
            myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final String TAG = "onCreateView";
        String dbMsg = "[TargetPlanFragment]";
        View root = null;
        try {
            TargetPlanViewModel targetPlaniewModel =
                    new ViewModelProvider(this).get(TargetPlanViewModel.class);
            binding = FragmentTargetSettingBinding.inflate(inflater, container, false);
            root = binding.getRoot();
            final TextView textView = binding.textTargetSetting;

            Context con = getActivity();
            pClass=(MainActivity)inflater.getContext();
            dbMsg += "," + con.getResources().getString(R.string.sett_event_name) +"=" +pClass.tEventName;

            tEventNameEt = binding.tEventNameEt;           // 予定の名称
            dbMsg += ",予定の名称="+pClass.tEventName;
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
                            pClass.tEventName= s.toString();
                            dbMsg += pClass.tEventName;
                            setStrPref("tEventName",pClass.tEventName);
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
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"名称=" + pClass.tEventSoundName ;
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + pClass.tEventSoundURi ;
          //  spPosision=pClass.soundNameList.indexOf((String)pClass.tEventSoundName );

            int spPosition = (Integer)soundArrayAdapter.getPosition((String)pClass.tEventSoundName );
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
                            dbMsg += ",名称＝" + pClass.tEventSoundName + ",URI=" + pClass.tEventSoundURi ;
                            soundItem sItem = pClass.setSoundItem(position,"tEventSoundName","tEventSoundURi");
                            dbMsg += ">>"+ pClass.tEventSoundName + "," + pClass.tEventSoundURi ;
                            tSoundPlayBT.setVisibility(View.VISIBLE);
                            if(ringtone!=null && ringtone.isPlaying()){
                                dbMsg += ringtone.getTitle(getActivity()) + "を" + ringtone.getVolume() + "から";
                                ringtone.stop();
                                dbMsg += ">>停止";
                            }
                            ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(pClass.tEventSoundURi));
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
                        dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + pClass.tEventSoundURi ;
                        if(pClass.tEventSoundURi != null){
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

            if(pClass.tEventSoundURi == null){
                tSoundPlayBT.setVisibility(View.GONE );
            }

            dbMsg += "アラーム時刻1="+ pClass.tArarmTime1;
            tArarmTime1BT = binding.tArarmTime1BT;           // アラーム時刻1
            tArarmTime1BT.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tArarmTime1BT]";
                    try {
                        dbMsg += "アラーム時刻1=" +  pClass.tArarmTime1;
                        pClass.showTimePicker("tArarmTime1",pClass.tArarmTime1,tArarmTime1BT);
//                        tArarmTime1BT.setText(TargetPlanFragment.this.prefValue);
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
                        dbMsg += "アラーム時刻1 の日曜=" + pClass.ta100c;
                        pClass.setWeekCheck(chBox,isChecked,"ta100c",ta200cBox,"ta200c",ta300cBox,"ta300c" );
                        if(isChecked){
                            pClass.ta100c=true;
                            pClass.ta200c=false;
                            pClass.ta300c=false;
                        }else{
                            pClass.ta100c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta101c",ta201cBox,"ta201c",ta301cBox,"ta301c" );
                        if(isChecked){
                            pClass.ta101c=true;
                            pClass.ta201c=false;
                            pClass.ta301c=false;
                        }else{
                            pClass.ta101c=false;
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
                    String dbMsg = "[ta102cBox]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chBox,isChecked,"ta102c",ta202cBox,"ta202c",ta302cBox,"ta302c" );
                        if(isChecked){
                            pClass.ta102c=true;
                            pClass.ta202c=false;
                            pClass.ta302c=false;
                        }else{
                            pClass.ta102c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta103c",ta203cBox,"ta203c",ta303cBox,"ta303c" );
                        if(isChecked){
                            pClass.ta103c=true;
                            pClass.ta203c=false;
                            pClass.ta303c=false;
                        }else{
                            pClass.ta103c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta104c",ta204cBox,"ta204c",ta304cBox,"ta304c" );
                        if(isChecked){
                            pClass.ta104c=true;
                            pClass.ta204c=false;
                            pClass.ta304c=false;
                        }else{
                            pClass.ta104c=false;
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
                        pClass.setWeekCheck(chkbox,isChecked,"ta105c",ta205cBox,"ta205c",ta305cBox,"ta305c" );
                        if(isChecked){
                            pClass.ta105c=true;
                            pClass.ta205c=false;
                            pClass.ta305c=false;
                        }else{
                            pClass.ta105c=false;
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
                        pClass.setWeekCheck(chkbox,isChecked,"ta106c",ta206cBox,"ta206c",ta306cBox,"ta306c" );
                        if(isChecked){
                            pClass.ta106c=true;
                            pClass.ta206c=false;
                            pClass.ta306c=false;
                        }else{
                            pClass.ta106c=false;
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
                        pClass.setWeekCheck(chkbox,isChecked,"ta107c",ta207cBox,"ta207c",ta307cBox,"ta307c" );
                        if(isChecked){
                            pClass.ta107c=true;
                            pClass.ta207c=false;
                            pClass.ta307c=false;
                        }else{
                            pClass.ta107c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            dbMsg += "アラーム時刻2="+ pClass.tArarmTime2;
            tArarmTime2BT = binding.tArarmTime2BT;           // アラーム時刻2
            tArarmTime2BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tArarmTime1BT]";
                    try {
                        dbMsg += "アラーム時刻2=" +  pClass.tArarmTime2;
                        pClass.showTimePicker("tArarmTime2",pClass.tArarmTime2,tArarmTime2BT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            pClass.setButtonText(tArarmTime2BT,pClass.tArarmTime2);

            ta200cBox = binding.ta200cBox;               // アラーム時刻2 の日曜
            ta200cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta200cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chkbox,isChecked,"ta200c",ta100cBox,"ta100c",ta300cBox,"ta300c" );
                        if(isChecked){
                            pClass.ta200c=true;
                            pClass.ta100c=false;
                            pClass.ta300c=false;
                        }else{
                            pClass.ta200c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta201c",ta101cBox,"ta101c",ta301cBox,"ta301c" );
                        if(isChecked){
                            pClass.ta201c=true;
                            pClass.ta101c=false;
                            pClass.ta301c=false;
                        }else{
                            pClass.ta201c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta202c",ta102cBox,"ta102c",ta302cBox,"ta302c" );
                        if(isChecked){
                            pClass.ta202c=true;
                            pClass.ta102c=false;
                            pClass.ta302c=false;
                        }else{
                            pClass.ta202c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta203c",ta103cBox,"ta103c",ta303cBox,"ta303c" );
                        if(isChecked){
                            pClass.ta203c=true;
                            pClass.ta103c=false;
                            pClass.ta303c=false;
                        }else{
                            pClass.ta203c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta204c",ta104cBox,"ta104c",ta304cBox,"ta304c" );
                        if(isChecked){
                            pClass.ta204c=true;
                            pClass.ta104c=false;
                            pClass.ta304c=false;
                        }else{
                            pClass.ta204c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta205c",ta105cBox,"ta105c",ta305cBox,"ta305c" );
                        if(isChecked){
                            pClass.ta205c=true;
                            pClass.ta105c=false;
                            pClass.ta305c=false;
                        }else{
                            pClass.ta205c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta206c",ta106cBox,"ta106c",ta306cBox,"ta306c" );
                        if(isChecked){
                            pClass.ta206c=true;
                            pClass.ta106c=false;
                            pClass.ta306c=false;
                        }else{
                            pClass.ta206c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta207c",ta107cBox,"ta107c",ta307cBox,"ta307c" );
                        if(isChecked){
                            pClass.ta207c=true;
                            pClass.ta107c=false;
                            pClass.ta307c=false;
                        }else{
                            pClass.ta207c=false;
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            dbMsg += "アラーム時刻3="+ pClass.tArarmTime3;
            tArarmTime3BT = binding.tArarmTime3BT;           // アラーム時刻2
            tArarmTime3BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tArarmTime3BT]";
                    try {
                        dbMsg += "アラーム時刻3=" +  pClass.tArarmTime3;
                        pClass.showTimePicker("tArarmTime3",pClass.tArarmTime3,tArarmTime3BT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            pClass.setButtonText(tArarmTime3BT,pClass.tArarmTime3);

            ta300cBox = binding.ta300cBox;               // アラーム時刻2 の日曜
            ta300cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta300cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.setWeekCheck(chkbox,isChecked,"ta300c",ta100cBox,"ta100c",ta200cBox,"ta200c" );
                        if(isChecked){
                            pClass.ta300c=true;
                            pClass.ta100c=false;
                            pClass.ta200c=false;
                        }else{
                            pClass.ta300c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta301c",ta101cBox,"ta101c",ta201cBox,"ta201c" );
                        if(isChecked){
                            pClass.ta301c=true;
                            pClass.ta101c=false;
                            pClass.ta201c=false;
                        }else{
                            pClass.ta301c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta302c",ta102cBox,"ta102c",ta202cBox,"ta202c" );
                        if(isChecked){
                            pClass.ta302c=true;
                            pClass.ta102c=false;
                            pClass.ta202c=false;
                        }else{
                            pClass.ta302c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta303c",ta103cBox,"ta103c",ta203cBox,"ta203c" );
                        if(isChecked){
                            pClass.ta303c=true;
                            pClass.ta103c=false;
                            pClass.ta203c=false;
                        }else{
                            pClass.ta303c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta304c",ta104cBox,"ta104c",ta204cBox,"ta204c" );
                        if(isChecked){
                            pClass.ta304c=true;
                            pClass.ta104c=false;
                            pClass.ta204c=false;
                        }else{
                            pClass.ta304c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta305c",ta105cBox,"ta105c",ta205cBox,"ta205c" );
                        if(isChecked){
                            pClass.ta305c=true;
                            pClass.ta105c=false;
                            pClass.ta205c=false;
                        }else{
                            pClass.ta305c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta306c",ta106cBox,"ta106c",ta206cBox,"ta206c" );
                        if(isChecked){
                            pClass.ta306c=true;
                            pClass.ta106c=false;
                            pClass.ta206c=false;
                        }else{
                            pClass.ta306c=false;
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
                        pClass.setWeekCheck(chBox,isChecked,"ta307c",ta107cBox,"ta107c",ta207cBox,"ta207c" );
                        if(isChecked){
                            pClass.ta307c=true;
                            pClass.ta107c=false;
                            pClass.ta207c=false;
                        }else{
                            pClass.ta307c=false;
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
                        dbMsg += ",Googleカレンダー連携=" + pClass.googleCalAlignment;
                        dbMsg += ">>" + isChecked;
                        pClass.googleCalAlignment=isChecked;
                        pClass.setBoolPref("googleCalAlignment",isChecked);
                        setGoogleCalItems(isChecked);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            googleCalAlignmentSW.setChecked(pClass.googleCalAlignment);
            setGoogleCalItems(pClass.googleCalAlignment);

            gcaSubjectET = binding.gcaSubjectET;
            dbMsg += ",登録する名称="+pClass.gcaSubject;
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
                            pClass.gcaSubject= s.toString();
                            dbMsg += pClass.gcaSubject;
                            setStrPref("gcaSubject",pClass.gcaSubject);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            gcaSubjectET.setText(pClass.gcaSubject);

            gcaStarttimeBT = binding.gcaStarttimeBT;         //開始時刻
            gcaStarttimeBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[gcaStarttimeBT]";
                    try {
                        dbMsg += "開始時刻=" +  pClass.gcaStarttime;
                        pClass.showTimePicker("gcaStarttime",pClass.gcaStarttime,gcaStarttimeBT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            gcaEndtimeBT = binding.gcaEndtimeBT;          //終了時刻
            gcaEndtimeBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[gcaEndtimeBT]";
                    try {
                        dbMsg += "終了時刻=" +  pClass.gcaEndtime;
                        pClass.showTimePicker("gcaEndtime",pClass.gcaEndtime,gcaEndtimeBT);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            gcaEnddateSW = binding.gcaEnddateSW;            //終日
            gcaEnddateSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[gcaEnddateSW]";
                    try {
                        Switch chBox=(Switch)buttonView;
                        dbMsg += ",終日=" + pClass.gcaEnddate;
                        dbMsg += ">>" + isChecked;
                        pClass.setBoolPref("gcaEnddate",isChecked);
                        pClass.gcaEnddate=isChecked;
                        if(isChecked){
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
            });

            gcaDescriptionET = binding.gcaDescriptionET;
            dbMsg += ",説明・メモ="+pClass.gcaDescription;
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
                            pClass.gcaDescription= s.toString();
                            dbMsg += pClass.gcaDescription;
                            setStrPref("gcaDescription",pClass.gcaDescription);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            gcaDescriptionET.setText(pClass.gcaDescription);

            gcaLocationET = binding.gcaLocationET;            //予定の場所
            dbMsg += "予定の場所="+pClass.gcaLocation;
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
                            pClass.gcaLocation= s.toString();
                            dbMsg += pClass.gcaLocation;
                            pClass.setStrPref("gcaLocation",pClass.gcaLocation);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            gcaLocationET.setText(pClass.gcaLocation);

            gcaColorIdSP = binding.gcaColorIdSP;         //予定の色
            ArrayAdapter colorArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, (List) pClass.colorNameList);
            gcaColorIdSP.setAdapter(soundArrayAdapter);
            dbMsg += "," + getResources().getString(R.string.gc_color) +"名称=" + pClass.tCColorName;           // 予定の色名称
            dbMsg += "," + getResources().getString(R.string.gc_color) +"URI=" + pClass.tCColorRss;           // 予定の色リソースID

            int cspPosition = (Integer)colorArrayAdapter.getPosition((String)pClass.tCColorName );
            dbMsg += "," + cspPosition +"番目";
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
                        if (gcaColorIdSP.isFocusable() == false) {
                            gcaColorIdSP.setFocusable(true);
                            dbMsg += ",初回起動";
                        }else{
                            dbMsg += ",初回以降の動作";
                            dbMsg += ",名称＝" + pClass.tCColorName + ",resID=" + pClass.tCColorRss ;
                            GoogleCalendarColors sCololr = pClass.googleCalendarColorList.get(position);
                            pClass.tCColorName = sCololr.colorName;
                            pClass.setStrPref("tCColorName",pClass.tCColorName);
                            pClass.tCColorRss = sCololr.colorResId;
                            pClass.setIntPref("tCColorRss",pClass.tCColorRss);
                            dbMsg += ">>" + pClass.tCColorName + ",resID=" + pClass.tCColorRss ;
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

            gcaIsPrivateCB = binding.gcaIsPrivateCB;            //予定を限定公開にする
            gcaIsPrivateCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[gcaIsPrivateCB]";
                    try {
                        CheckBox chBox=(CheckBox)buttonView;
                        dbMsg += ",gcaIsPrivate=" + pClass.gcaIsPrivate;
                        dbMsg += ">>" + isChecked;
                        pClass.gcaIsPrivate=isChecked;
                        pClass.setBoolPref("pClass",isChecked);
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            dbMsg += "アラーム時刻1 の日曜=" + pClass.ta100c;
            ta100cBox.setChecked(pClass.ta100c);
            dbMsg += "アラーム時刻1 の月曜=" + pClass.ta101c;
            ta101cBox.setChecked(pClass.ta101c);
            dbMsg += "アラーム時刻1 の火曜=" + pClass.ta102c;
            ta102cBox.setChecked(pClass.ta102c);
            dbMsg += "アラーム時刻1 の水曜=" + pClass.ta103c;
            ta103cBox.setChecked(pClass.ta103c);
            dbMsg += "アラーム時刻1 の木曜=" + pClass.ta104c;
            ta104cBox.setChecked(pClass.ta104c);
            dbMsg += "アラーム時刻1 の金曜=" + pClass.ta105c;
            ta105cBox.setChecked(pClass.ta105c);
            dbMsg += "アラーム時刻1 の土曜=" + pClass.ta106c;
            ta106cBox.setChecked(pClass.ta106c);
            dbMsg += "アラーム時刻1 の祝日=" + pClass.ta107c;
            ta107cBox.setChecked(pClass.ta107c);

            dbMsg += "アラーム時刻2 の日曜=" + pClass.ta200c;
            ta200cBox.setChecked(pClass.ta200c);
            dbMsg += "アラーム時刻2 の月曜=" + pClass.ta201c;
            ta201cBox.setChecked(pClass.ta201c);
            dbMsg += "アラーム時刻2 の火曜=" + pClass.ta202c;
            ta202cBox.setChecked(pClass.ta202c);
            dbMsg += "アラーム時刻2 の水曜=" + pClass.ta203c;
            ta203cBox.setChecked(pClass.ta203c);
            dbMsg += "アラーム時刻2 の木曜=" + pClass.ta204c;
            ta204cBox.setChecked(pClass.ta204c);
            dbMsg += "アラーム時刻2 の金曜=" + pClass.ta205c;
            ta205cBox.setChecked(pClass.ta205c);
            dbMsg += "アラーム時刻2 の土曜=" + pClass.ta206c;
            ta206cBox.setChecked(pClass.ta206c);
            dbMsg += "アラーム時刻1 の祝日=" + pClass.ta207c;
            ta207cBox.setChecked(pClass.ta207c);

            dbMsg += "アラーム時刻3 の日曜=" + pClass.ta300c;
            ta300cBox.setChecked(pClass.ta300c);
            dbMsg += "アラーム時刻3 の月曜=" + pClass.ta301c;
            ta301cBox.setChecked(pClass.ta301c);
            dbMsg += "アラーム時刻3 の火曜=" + pClass.ta302c;
            ta302cBox.setChecked(pClass.ta302c);
            dbMsg += "アラーム時刻3 の水曜=" + pClass.ta303c;
            ta303cBox.setChecked(pClass.ta303c);
            dbMsg += "アラーム時刻3 の木曜=" + pClass.ta304c;
            ta304cBox.setChecked(pClass.ta304c);
            dbMsg += "アラーム時刻3 の金曜=" + pClass.ta305c;
            ta305cBox.setChecked(pClass.ta305c);
            dbMsg += "アラーム時刻3 の土曜=" + pClass.ta306c;
            ta306cBox.setChecked(pClass.ta306c);
            dbMsg += "アラーム時刻3 の祝日=" + pClass.ta307c;
            ta307cBox.setChecked(pClass.ta307c);

            tEventNameEt.setFocusable(true);
            targetPlaniewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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
        String dbMsg = "[TargetSettingFragment]";
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