package com.hijiyam_koubou.taplans.ui.target_setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hijiyam_koubou.taplans.MainActivity;
import com.hijiyam_koubou.taplans.R;
import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentTargetSettingBinding;

import kotlin.text.UStringsKt;

public class TargetPlanFragment extends Fragment {
    private MainActivity pClass;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor myEditor;

    private EditText tEventNameEt;             //  予定の名称
    private EditText tEventSoundEt;           // アラーム音
    private EditText tArarmTime1Et;           // アラーム時刻1
    private CheckBox ta100cBox;               // アラーム時刻1 の日曜
    private CheckBox ta101cBox;               // アラーム時刻1 の月曜
    private CheckBox ta102cBox;               // アラーム時刻1 の火曜
    private CheckBox ta103cBox;               // アラーム時刻1 の水曜
    private CheckBox ta104cBox;               // アラーム時刻1 の木曜
    private CheckBox ta105cBox;               // アラーム時刻1 の金曜
    private CheckBox ta106cBox;               // アラーム時刻1 の土曜
    private EditText tArarmTime2Et;           // アラーム時刻2

    private CheckBox ta200cBox;               // アラーム時刻2 の日曜
    private CheckBox ta201cBox;               // アラーム時刻2 の月曜
    private CheckBox ta202cBox;               // アラーム時刻2 の火曜
    private CheckBox ta203cBox;               // アラーム時刻2 の水曜
    private CheckBox ta204cBox;               // アラーム時刻2 の木曜
    private CheckBox ta205cBox;               // アラーム時刻2 の金曜
    private CheckBox ta206cBox;               // アラーム時刻2 の土曜

//    private String tEventName;             //  予定の名称
//    private String tEventSound;           // アラーム音
//    private String tArarmTime1;           // アラーム時刻1
//    private Boolean ta100c = false;               // アラーム時刻1 の日曜
//    private Boolean ta101c = false;          // アラーム時刻1 の月曜
//    private Boolean ta102c = false;        // アラーム時刻1 の火曜
//    private Boolean ta103c = false;         // アラーム時刻1 の水曜
//    private Boolean ta104c = false;           // アラーム時刻1 の木曜
//    private Boolean ta105c = false;            // アラーム時刻1 の金曜
//    private Boolean ta106c = false;            // アラーム時刻1 の土曜
//
//    private String tArarmTime2;           // アラーム時刻2
//    private Boolean ta200c = false;             // アラーム時刻2 の日曜
//    private Boolean ta201c = false;            // アラーム時刻2 の月曜
//    private Boolean ta202c = false;            // アラーム時刻2 の火曜
//    private Boolean ta203c = false;               // アラーム時刻2 の水曜
//    private Boolean ta204c = false;              // アラーム時刻2 の木曜
//    private Boolean ta205c = false;          // アラーム時刻2 の金曜
//    private Boolean ta206c = false;              // アラーム時刻2 の土曜

    private FragmentTargetSettingBinding binding;

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
        final String TAG = "setStrPref";
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

            tEventSoundEt = binding.tEventSoundEt;           // アラーム音
            dbMsg += "アラーム音="+ pClass.tEventSound;
            tEventSoundEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //テキスト変更後
                    final String TAG = "afterTextChanged";
                    String dbMsg = "[tEventSoundEt]";
                    try {
                        dbMsg += "アラーム音=";
                        if(s.toString() != null){
                            pClass.tEventSound= s.toString();
                            dbMsg += pClass.tEventSound;
                            setStrPref("tEventSound",pClass.tEventSound);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            tEventSoundEt.setText(pClass.tEventSound);

            tArarmTime1Et = binding.tArarmTime1Et;           // アラーム時刻1
            dbMsg += "アラーム時刻1="+ pClass.tArarmTime1;
            tArarmTime1Et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    final String TAG = "onTextChanged";
//                    String dbMsg = "[tArarmTime1Et]";
//                    try {
//                        dbMsg += "アラーム時刻1="+ s.toString();;
//                        dbMsg += ","+ start + "～"+ before + ",count=" + count;
//
////                        if(s.toString() != null){
////                            pClass.tArarmTime1= s.toString();
////                            dbMsg += pClass.tArarmTime1;
////                            setStrPref("tArarmTime1",pClass.tArarmTime1);
////                        }else{
////                            dbMsg += "null";
////                        }
//                        myLog(TAG , dbMsg);
//                    } catch (Exception er) {
//                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
//                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //テキスト変更後
                    final String TAG = "afterTextChanged";
                    String dbMsg = "[tArarmTime1Et]";
                    try {
                        dbMsg += "アラーム時刻1=";
                        if(s.toString() != null){
                            pClass.tArarmTime1= s.toString();
                            dbMsg += pClass.tArarmTime1;
                            setStrPref("tArarmTime1",pClass.tArarmTime1);
                        }else{
                            dbMsg += "null";
                        }
                         myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            tArarmTime1Et.setText(pClass.tArarmTime1);

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

            tArarmTime2Et = binding.tArarmTime2Et;           // アラーム時刻2
            dbMsg += "アラーム時刻2="+ pClass.tArarmTime2;
            tArarmTime2Et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //テキスト変更後
                    final String TAG = "afterTextChanged";
                    String dbMsg = "[tArarmTime2Et]";
                    try {
                        dbMsg += "アラーム時刻2=";
                        if(s.toString() != null){
                            pClass.tArarmTime2= s.toString();
                            dbMsg += pClass.tArarmTime2;
                            setStrPref("tArarmTime2",pClass.tArarmTime2);
                        }else{
                            dbMsg += "null";
                        }
                        myLog(TAG , dbMsg);
                    } catch (Exception er) {
                        myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                    }
                }
            });
            tArarmTime2Et.setText(pClass.tArarmTime2);

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