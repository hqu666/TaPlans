package com.hijiyam_koubou.taplans.ui.alarm_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlarmListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AlarmListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AlarmList fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}