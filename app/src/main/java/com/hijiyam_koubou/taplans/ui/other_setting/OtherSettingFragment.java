package com.hijiyam_koubou.taplans.ui.other_setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.hijiyam_koubou.taplans.databinding.FragmentSlideshowBinding;
import com.hijiyam_koubou.taplans.databinding.FragmentOtherSettingBinding;

public class OtherSettingFragment extends Fragment {

//    private FragmentSlideshowBinding binding;
    private FragmentOtherSettingBinding binding;


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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}