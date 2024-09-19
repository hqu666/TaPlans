package com.hijiyam_koubou.taplans;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressDialog extends DialogFragment {
    private static final int DELAY_MILLISECOND = 450;
    private static final int SHOW_MIN_MILLISECOND = 300;

    private ProgressBar mProgressBar;
    private TextView mProgressMessage;
    private boolean mStartedShowing;
    private long mStartMillisecond;
    private long mStopMillisecond;

    private String mMessage;

    // required default constructor
    public ProgressDialog() {
        super();

        final String TAG = "ProgressDialog";
        String dbMsg = "[ProgressDialog]";
        try {

            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    public static ProgressDialog newInstance(String message) {
         final String TAG = "newInstance";
        String dbMsg = "[ProgressDialog]";
        ProgressDialog instance = null;
        try {
            dbMsg += ",message=" + message;
            instance = new ProgressDialog();

            Bundle arguments = new Bundle();
            arguments.putString("message", message);
            instance.setArguments(arguments);
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
        return instance;
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String TAG = "onCreateDialog";
        String dbMsg = "[ProgressDialog]";
        AlertDialog.Builder builder = null;
        try {
            mMessage = getArguments().getString("message");
            dbMsg += ",mMessage=" + mMessage;
            builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.dialog_progress, null));
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final String TAG = "onStart";
        String dbMsg = "[ProgressDialog]";
        try {
            mProgressBar = Objects.requireNonNull(getDialog()).findViewById(R.id.progress);
            mProgressMessage = getDialog().findViewById(R.id.progress_message);
            mProgressMessage.setText(mMessage);
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    @Override
    public void show(final FragmentManager manager, final String tag) {
        final String TAG = "show";
        String dbMsg = "[ProgressDialog]";
        try {
            dbMsg += ",tag=" + tag;
            mStartMillisecond = System.currentTimeMillis();
            mStartedShowing = false;
            mStopMillisecond = Long.MAX_VALUE;

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mStopMillisecond > System.currentTimeMillis()) {
                        showDialogAfterDelay(manager, tag);
                    }
                }
            }, DELAY_MILLISECOND);
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    private void showDialogAfterDelay(FragmentManager manager, String tag) {
        mStartedShowing = true;
        super.show(manager, tag);

        final String TAG = "showDialogAfterDelay";
        String dbMsg = "[ProgressDialog]";
        try {
            dbMsg += ",tag=" + tag;

            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    public void cancel() {
        final String TAG = "cancel";
        String dbMsg = "[ProgressDialog]";
        try {
            mStopMillisecond = System.currentTimeMillis();

            if (mStartedShowing) {
                if (mProgressBar != null) {
                    cancelWhenShowing();
                } else {
                    cancelWhenNotShowing();
                }
            }
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    private void cancelWhenShowing() {
        final String TAG = "cancelWhenShowing";
        String dbMsg = "[ProgressDialog]";
        try {
            if (mStopMillisecond < mStartMillisecond + DELAY_MILLISECOND + SHOW_MIN_MILLISECOND) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissAllowingStateLoss();
                    }
                }, SHOW_MIN_MILLISECOND);
            } else {
                dismissAllowingStateLoss();
            }
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    private void cancelWhenNotShowing() {
        final Handler handler = new Handler();
         final String TAG = "cancelWhenNotShowing";
        String dbMsg = "[ProgressDialog]";
        try {
            dbMsg += ",SHOW_MIN_MILLISECOND=" + SHOW_MIN_MILLISECOND;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissAllowingStateLoss();
                }
            }, SHOW_MIN_MILLISECOND);
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    public void setMessage(String message) {
         final String TAG = "setMessage";
        String dbMsg = "[ProgressDialog]";
        try {
            dbMsg += ",message=" + message;
            if (mProgressMessage == null) {
                mProgressMessage = Objects.requireNonNull(getDialog()).findViewById(R.id.progress_message);
            }
            mProgressMessage.setText(message);
            myLog(TAG, dbMsg);
        } catch (Exception e) {
            myErrorLog(TAG ,  dbMsg + "で" + e);
        }
    }

    //////////////////////////////////////////////////////////////ライフサイクル//
    public static void myLog(String TAG , String dbMsg) {
        Util UTIL = new Util();
        UTIL.myLog(TAG , dbMsg);
    }

    public static void myErrorLog(String TAG , String dbMsg) {
        Util UTIL = new Util();
        UTIL.myErrorLog(TAG , dbMsg);
    }
}

//2017 ProgressDialogが非推奨らしいので自分で作ってみた
// https://qiita.com/Uchikoba/items/478d604f417465700ba1