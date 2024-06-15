package com.hijiyam_koubou.taplans.ui.target_setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TargetPlanViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TargetPlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Target fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}