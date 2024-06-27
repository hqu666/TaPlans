package com.hijiyam_koubou.taplans.ui.target_setting;

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

import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentTargetSettingBinding;

import kotlin.text.UStringsKt;

public class TargetPlanFragment extends Fragment {

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

    private String tEventName;             //  予定の名称
    private String tEventSound;           // アラーム音
    private String tArarmTime1;           // アラーム時刻1
    private Boolean ta100c = false;               // アラーム時刻1 の日曜
    private Boolean ta101c = false;          // アラーム時刻1 の月曜
    private Boolean ta102c = false;        // アラーム時刻1 の火曜
    private Boolean ta103c = false;         // アラーム時刻1 の水曜
    private Boolean ta104c = false;           // アラーム時刻1 の木曜
    private Boolean ta105c = false;            // アラーム時刻1 の金曜
    private Boolean ta106c = false;            // アラーム時刻1 の土曜

    private String tArarmTime2;           // アラーム時刻2
    private Boolean ta200c = false;             // アラーム時刻2 の日曜
    private Boolean ta201c = false;            // アラーム時刻2 の月曜
    private Boolean ta202c = false;            // アラーム時刻2 の火曜
    private Boolean ta203c = false;               // アラーム時刻2 の水曜
    private Boolean ta204c = false;              // アラーム時刻2 の木曜
    private Boolean ta205c = false;          // アラーム時刻2 の金曜
    private Boolean ta206c = false;              // アラーム時刻2 の土曜

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
        TargetPlanViewModel targetPlaniewModel =
                new ViewModelProvider(this).get(TargetPlanViewModel.class);

        binding = FragmentTargetSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTargetSetting;

        tEventNameEt = binding.tEventNameEt;             //  予定の名称
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
                String dbMsg = "[afterTextChanged]";
                try {
                    tEventName= s.toString();
                    dbMsg += "予定の名称=" + tEventName;
                    setStrPref("tEventName",tEventName);
                    myLog(TAG , dbMsg);
                } catch (Exception er) {
                    myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                }
            }
        });
        tEventNameEt.setText(tEventName);

        tEventSoundEt = binding.tEventSoundEt;           // アラーム音
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
                    tEventSound= s.toString();
                    dbMsg += "アラーム音=" + tEventSound;
                    setStrPref("tEventSound",tEventSound);
                    myLog(TAG , dbMsg);
                } catch (Exception er) {
                    myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                }
            }
        });
        tEventSoundEt.setText(tEventSound);

        tArarmTime1Et = binding.tArarmTime1Et;           // アラーム時刻1
        tArarmTime1Et.addTextChangedListener(new TextWatcher() {
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
                String dbMsg = "[tArarmTime1Et]";
                try {
                    tArarmTime1= s.toString();
                    dbMsg += "アラーム時刻1=" + tArarmTime1;
                    setStrPref("tArarmTime1",tArarmTime1);
                    myLog(TAG , dbMsg);
                } catch (Exception er) {
                    myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                }
            }
        });
        tArarmTime1Et.setText(tArarmTime1);

        ta100cBox = binding.ta100cBox;               // アラーム時刻1 の日曜
        ta100cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta100cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta100c=chkbox.isChecked();
                    dbMsg += "アラーム時刻1 の日曜=" + ta100c;
                    setBoolPref("ta100c",ta100c);
                    if(ta100c) {
                        if(ta200c) {
                            ta200c =false;
                            ta200cBox.setChecked(ta200c);
                            setBoolPref("ta200c",ta200c);
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
        ta101cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta101cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta101c=chkbox.isChecked();
                    dbMsg += "アラーム時刻1 の月曜=" + ta101c;
                    setBoolPref("ta101c",ta101c);
                    if(ta101c) {
                        if(ta201c) {
                            ta201c =false;
                            ta201cBox.setChecked(ta201c);
                            setBoolPref("ta201c",ta201c);
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
        ta102cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta102cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta102c=chkbox.isChecked();
                    dbMsg += "アラーム時刻1 の火曜=" + ta102c;
                    setBoolPref("ta102c",ta102c);
                    if(ta102c) {
                        if(ta202c) {
                            ta202c =false;
                            ta202cBox.setChecked(ta202c);
                            setBoolPref("ta202c",ta202c);
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
        ta103cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta103cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta103c=chkbox.isChecked();
                    dbMsg += "アラーム時刻1 の水曜=" + ta103c;
                    setBoolPref("ta103c",ta103c);
                    if(ta103c) {
                        if(ta203c) {
                            ta203c =false;
                            ta203cBox.setChecked(ta203c);
                            setBoolPref("ta203c",ta203c);
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
        ta104cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta104cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta104c=chkbox.isChecked();
                    dbMsg += "アラーム時刻1 の木曜=" + ta104c;
                    setBoolPref("ta104c",ta104c);
                    if(ta104c) {
                        if(ta204c) {
                            ta204c =false;
                            ta204cBox.setChecked(ta204c);
                            setBoolPref("ta204c",ta204c);
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
        ta105cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta105cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta105c=chkbox.isChecked();
                    dbMsg += "アラーム時刻1 の金曜=" + ta105c;
                    setBoolPref("ta105c",ta105c);
                    if(ta105c) {
                        if(ta205c) {
                            ta205c =false;
                            ta205cBox.setChecked(ta205c);
                            setBoolPref("ta205c",ta205c);
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
        ta106cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta106cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta106c=chkbox.isChecked();
                    dbMsg += "アラーム時刻1 の土曜=" + ta106c;
                    setBoolPref("ta105c",ta106c);
                    if(ta106c) {
                        if(ta206c) {
                            ta206c =false;
                            ta206cBox.setChecked(ta206c);
                            setBoolPref("ta206c",ta206c);
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
                    tArarmTime1= s.toString();
                    dbMsg += "アラーム時刻2=" + tArarmTime2;
                    setStrPref("tArarmTime2",tArarmTime2);
                    myLog(TAG , dbMsg);
                } catch (Exception er) {
                    myErrorLog(TAG , dbMsg + ";でエラー発生；" + er);
                }
            }
        });
        tArarmTime2Et.setText(tArarmTime2);

        ta200cBox = binding.ta200cBox;               // アラーム時刻2 の日曜
        ta200cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta200cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta200c=chkbox.isChecked();
                    dbMsg += "アラーム時刻2 の日曜=" + ta200c;
                    setBoolPref("ta200c",ta200c);
                    if(ta200c) {
                        if(ta100c) {
                            ta100c =false;
                            ta100cBox.setChecked(ta100c);
                            setBoolPref("ta100c",ta100c);
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
        ta201cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta201cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta201c=chkbox.isChecked();
                    dbMsg += "アラーム時刻2 の月曜=" + ta201c;
                    setBoolPref("ta201c",ta201c);
                    if(ta201c) {
                        if(ta101c) {
                            ta101c =false;
                            ta101cBox.setChecked(ta101c);
                            setBoolPref("ta101c",ta101c);
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
        ta202cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta202cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta202c=chkbox.isChecked();
                    dbMsg += "アラーム時刻2 の火曜=" + ta202c;
                    setBoolPref("ta202c",ta202c);
                    if(ta202c) {
                        if(ta102c) {
                            ta102c =false;
                            ta102cBox.setChecked(ta102c);
                            setBoolPref("ta102c",ta102c);
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
        ta203cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta203cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta203c=chkbox.isChecked();
                    dbMsg += "アラーム時刻2 の水曜=" + ta203c;
                    setBoolPref("ta203c",ta203c);
                    if(ta203c) {
                        if(ta103c) {
                            ta103c =false;
                            ta103cBox.setChecked(ta103c);
                            setBoolPref("ta103c",ta103c);
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
        ta204cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta204cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta204c=chkbox.isChecked();
                    dbMsg += "アラーム時刻2 の木曜=" + ta204c;
                    setBoolPref("ta204c",ta204c);
                    if(ta204c) {
                        if(ta104c) {
                            ta104c =false;
                            ta104cBox.setChecked(ta104c);
                            setBoolPref("ta104c",ta104c);
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
        ta205cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta205cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta205c=chkbox.isChecked();
                    dbMsg += "アラーム時刻2 の金曜=" + ta205c;
                    setBoolPref("ta205c",ta205c);
                    if(ta205c) {
                        if(ta105c) {
                            ta105c =false;
                            ta105cBox.setChecked(ta105c);
                            setBoolPref("ta105c",ta105c);
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
        ta206cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            // チェック状態が変更された時のハンドラ
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String TAG = "onCheckedChanged";
                String dbMsg = "[ta206cBox]";
                try {
                    CheckBox chkbox=(CheckBox)buttonView;
                    ta206c=chkbox.isChecked();
                    dbMsg += "アラーム時刻2 の土曜=" + ta206c;
                    setBoolPref("ta206c",ta206c);
                    if(ta206c) {
                        if(ta106c) {
                            ta106c =false;
                            ta106cBox.setChecked(ta106c);
                            setBoolPref("ta106c",ta106c);
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