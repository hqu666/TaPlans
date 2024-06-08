package com.hijiyam_koubou.taplans.ui.other_setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OtherSettingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OtherSettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}