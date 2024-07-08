package com.hijiyam_koubou.taplans.ui.target_setting;

import android.app.TimePickerDialog;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hijiyam_koubou.taplans.MainActivity;
import com.hijiyam_koubou.taplans.R;
import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentTargetSettingBinding;
import com.hijiyam_koubou.taplans.soundItem;

import java.lang.reflect.Field;
import java.util.Calendar;

import kotlin.text.UStringsKt;

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
    private Button tArarmTime2BT;          // アラーム時刻2

    private CheckBox ta200cBox;               // アラーム時刻2 の日曜
    private CheckBox ta201cBox;               // アラーム時刻2 の月曜
    private CheckBox ta202cBox;               // アラーム時刻2 の火曜
    private CheckBox ta203cBox;               // アラーム時刻2 の水曜
    private CheckBox ta204cBox;               // アラーム時刻2 の木曜
    private CheckBox ta205cBox;               // アラーム時刻2 の金曜
    private CheckBox ta206cBox;               // アラーム時刻2 の土曜

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
     * 指定されたKeyのBooleanPreferenceを作成/更新
     * */
    public void setBoolPref(String key,Boolean wBool) {
        //テキスト変更後
        final String TAG = "setBoolPref";
        String dbMsg = "[TargetPlanFragment]";
        try {
            dbMsg += "key=" + key;
            dbMsg += ",wBool=" + wBool;
            if(sharedPref == null){
                sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());            //	this.getSharedPreferences(this, MODE_PRIVATE);		//
            }
            if(myEditor == null){
                myEditor = sharedPref.edit();
            }
            myEditor.putBoolean(key, wBool);
            boolean ret = myEditor.commit();
            dbMsg += ",commit=" + ret;
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
            ArrayAdapter soundArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, pClass.soundNameList);
            tEventSoundSP.setAdapter(soundArrayAdapter);
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"名称=" + pClass.tEventSoundName ;
            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + pClass.tEventSoundURi ;

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
                            // if (mIsFirstBoot) {
                            tEventSoundSP.setFocusable(true);
                            dbMsg += ",初回起動";
                            // mIsFirstBoot = false;
                          //  return;
                        }else{
                            dbMsg += ",初回以降の動作";
                            Adapter sAdapter = parent.getAdapter();
                            pClass.tEventSoundName = (String) sAdapter.getItem(position);
                            dbMsg += ",名称＝" + pClass.tEventSoundName ;
                            pClass.setStrPref("tEventSoundName",pClass.tEventSoundName);

                            soundItem selItem = pClass.soundItemArrayList.get(position);
                            dbMsg += "[" + selItem.index+"]" ;
                            dbMsg += selItem.uri;

                            pClass.tEventSoundURi = selItem.uri;            //selItem.getClass().getField("uri").toString();
                            dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + pClass.tEventSoundURi ;
                            pClass.setStrPref("tEventSoundURi",pClass.tEventSoundURi);
                            tSoundPlayBT.setVisibility(View.VISIBLE);
                        }
//                        if(ringtone.isPlaying()){
                            ringtone.stop();
//                            dbMsg += ">>停止";
//                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            tSoundPlayBT = binding.tSoundPlayBT;
            tSoundPlayBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String TAG = "onClick";
                    String dbMsg = "[tSoundPlayBT]";
                    try {
                        dbMsg += "," + getResources().getString(R.string.set_alarm_sound) +"URI=" + pClass.tEventSoundURi ;
                        if(pClass.tEventSoundURi != null){
                            ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(pClass.tEventSoundURi));
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
            dbMsg += "アラーム時刻1 の日曜=" + pClass.ta100c;
            ta100cBox.setChecked(pClass.ta100c);
            ta100cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta100cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta100c=chkbox.isChecked();
                        dbMsg += "アラーム時刻1 の日曜=" + pClass.ta100c;
                        setBoolPref("ta100c",pClass.ta100c);
                        if(pClass.ta100c) {
                            if(pClass.ta200c) {
                                pClass.ta200c =false;
                                ta200cBox.setChecked(pClass.ta200c);
                                setBoolPref("ta200c",pClass.ta200c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta101cBox = binding.ta101cBox;               // アラーム時刻1 の月曜
            dbMsg += "アラーム時刻1 の月曜=" + pClass.ta101c;
            ta101cBox.setChecked(pClass.ta101c);
            ta101cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta101cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta101c=chkbox.isChecked();
                        dbMsg += "アラーム時刻1 の月曜=" + pClass.ta101c;
                        setBoolPref("ta101c",pClass.ta101c);
                        if(pClass.ta101c) {
                            if(pClass.ta201c) {
                                pClass.ta201c =false;
                                ta201cBox.setChecked(pClass.ta201c);
                                setBoolPref("ta201c",pClass.ta201c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta102cBox = binding.ta102cBox;               // アラーム時刻1 の火曜
            dbMsg += "アラーム時刻1 の火曜=" + pClass.ta102c;
            ta102cBox.setChecked(pClass.ta102c);
            ta102cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta102cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta102c=chkbox.isChecked();
                        dbMsg += "アラーム時刻1 の火曜=" + pClass.ta102c;
                        setBoolPref("ta102c",pClass.ta102c);
                        if(pClass.ta102c) {
                            if(pClass.ta202c) {
                                pClass.ta202c =false;
                                ta202cBox.setChecked(pClass.ta202c);
                                setBoolPref("ta202c",pClass.ta202c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta103cBox = binding.ta103cBox;               // アラーム時刻1 の水曜
            dbMsg += "アラーム時刻1 の水曜=" + pClass.ta103c;
            ta103cBox.setChecked(pClass.ta103c);
            ta103cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta103cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta103c=chkbox.isChecked();
                        dbMsg += "アラーム時刻1 の水曜=" + pClass.ta103c;
                        setBoolPref("ta103c",pClass.ta103c);
                        if(pClass.ta103c) {
                            if(pClass.ta203c) {
                                pClass.ta203c =false;
                                ta203cBox.setChecked(pClass.ta203c);
                                setBoolPref("ta203c",pClass.ta203c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta104cBox = binding.ta104cBox;               // アラーム時刻1 の木曜
            dbMsg += "アラーム時刻1 の木曜=" + pClass.ta104c;
            ta104cBox.setChecked(pClass.ta104c);
            ta104cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta104cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta104c=chkbox.isChecked();
                        dbMsg += "アラーム時刻1 の木曜=" + pClass.ta104c;
                        setBoolPref("ta104c",pClass.ta104c);
                        if(pClass.ta104c) {
                            if(pClass.ta204c) {
                                pClass.ta204c =false;
                                ta204cBox.setChecked(pClass.ta204c);
                                setBoolPref("ta204c",pClass.ta204c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta105cBox = binding.ta105cBox;               // アラーム時刻1 の金曜
            dbMsg += "アラーム時刻1 の金曜=" + pClass.ta105c;
            ta105cBox.setChecked(pClass.ta105c);
            ta105cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta105cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta105c=chkbox.isChecked();
                        dbMsg += "アラーム時刻1 の金曜=" + pClass.ta105c;
                        setBoolPref("ta105c",pClass.ta105c);
                        if(pClass.ta105c) {
                            if(pClass.ta205c) {
                                pClass.ta205c =false;
                                ta205cBox.setChecked(pClass.ta205c);
                                setBoolPref("ta205c",pClass.ta205c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta106cBox = binding.ta106cBox;               // アラーム時刻1 の土曜
            dbMsg += "アラーム時刻1 の土曜=" + pClass.ta106c;
            ta106cBox.setChecked(pClass.ta106c);
            ta106cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta106cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta106c=chkbox.isChecked();
                        dbMsg += "アラーム時刻1 の土曜=" + pClass.ta106c;
                        setBoolPref("ta105c",pClass.ta106c);
                        if(pClass.ta106c) {
                            if(pClass.ta206c) {
                                pClass.ta206c =false;
                                ta206cBox.setChecked(pClass.ta206c);
                                setBoolPref("ta206c",pClass.ta206c);
                            }
                        }else {
                            dbMsg += "解除";
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
            dbMsg += "アラーム時刻2 の日曜=" + pClass.ta200c;
            ta200cBox.setChecked(pClass.ta200c);
            ta200cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta200cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta200c=chkbox.isChecked();
                        dbMsg += "アラーム時刻2 の日曜=" + pClass.ta200c;
                        setBoolPref("ta200c",pClass.ta200c);
                        if(pClass.ta200c) {
                            if(pClass.ta100c) {
                                pClass.ta100c =false;
                                ta100cBox.setChecked(pClass.ta100c);
                                setBoolPref("ta100c",pClass.ta100c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta201cBox = binding.ta201cBox;               // アラーム時刻2 の月曜
            dbMsg += "アラーム時刻2 の月曜=" + pClass.ta201c;
            ta201cBox.setChecked(pClass.ta201c);
            ta201cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta201cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta201c=chkbox.isChecked();
                        dbMsg += "アラーム時刻2 の月曜=" + pClass.ta201c;
                        setBoolPref("ta201c",pClass.ta201c);
                        if(pClass.ta201c) {
                            if(pClass.ta101c) {
                                pClass.ta101c =false;
                                ta101cBox.setChecked(pClass.ta101c);
                                setBoolPref("ta101c",pClass.ta101c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta202cBox = binding.ta202cBox;               // アラーム時刻2 の火曜
            dbMsg += "アラーム時刻2 の火曜=" + pClass.ta202c;
            ta202cBox.setChecked(pClass.ta202c);
            ta202cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta202cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta202c=chkbox.isChecked();
                        dbMsg += "アラーム時刻2 の火曜=" + pClass.ta202c;
                        setBoolPref("ta202c",pClass.ta202c);
                        if(pClass.ta202c) {
                            if(pClass.ta102c) {
                                pClass.ta102c =false;
                                ta102cBox.setChecked(pClass.ta102c);
                                setBoolPref("ta102c",pClass.ta102c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta203cBox = binding.ta203cBox;               // アラーム時刻2 の水曜
            dbMsg += "アラーム時刻2 の水曜=" + pClass.ta203c;
            ta203cBox.setChecked(pClass.ta203c);
            ta203cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta203cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta203c=chkbox.isChecked();
                        dbMsg += "アラーム時刻2 の水曜=" + pClass.ta203c;
                        setBoolPref("ta203c",pClass.ta203c);
                        if(pClass.ta203c) {
                            if(pClass.ta103c) {
                                pClass.ta103c =false;
                                ta103cBox.setChecked(pClass.ta103c);
                                setBoolPref("ta103c",pClass.ta103c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta204cBox = binding.ta204cBox;               // アラーム時刻2 の木曜
            dbMsg += "アラーム時刻2 の木曜=" + pClass.ta204c;
            ta204cBox.setChecked(pClass.ta204c);
            ta204cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta204cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta204c=chkbox.isChecked();
                        dbMsg += "アラーム時刻2 の木曜=" + pClass.ta204c;
                        setBoolPref("ta204c",pClass.ta204c);
                        if(pClass.ta204c) {
                            if(pClass.ta104c) {
                                pClass.ta104c =false;
                                ta104cBox.setChecked(pClass.ta104c);
                                setBoolPref("ta104c",pClass.ta104c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta205cBox = binding.ta205cBox;               // アラーム時刻2 の金曜
            dbMsg += "アラーム時刻2 の金曜=" + pClass.ta205c;
            ta205cBox.setChecked(pClass.ta205c);
            ta205cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta205cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta205c=chkbox.isChecked();
                        dbMsg += "アラーム時刻2 の金曜=" + pClass.ta205c;
                        setBoolPref("ta205c",pClass.ta205c);
                        if(pClass.ta205c) {
                            if(pClass.ta105c) {
                                pClass.ta105c =false;
                                ta105cBox.setChecked(pClass.ta105c);
                                setBoolPref("ta105c",pClass.ta105c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

            ta206cBox = binding.ta206cBox;               // アラーム時刻2 の土曜
            dbMsg += "アラーム時刻2 の土曜=" + pClass.ta206c;
            ta206cBox.setChecked(pClass.ta206c);
            ta206cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                // チェック状態が変更された時のハンドラ
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final String TAG = "onCheckedChanged";
                    String dbMsg = "[ta206cBox]";
                    try {
                        CheckBox chkbox=(CheckBox)buttonView;
                        pClass.ta206c=chkbox.isChecked();
                        dbMsg += "アラーム時刻2 の土曜=" + pClass.ta206c;
                        setBoolPref("ta206c",pClass.ta206c);
                        if(pClass.ta206c) {
                            if(pClass.ta106c) {
                                pClass.ta106c =false;
                                ta106cBox.setChecked(pClass.ta106c);
                                setBoolPref("ta106c",pClass.ta106c);
                            }
                        }else {
                            dbMsg += "解除";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });

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