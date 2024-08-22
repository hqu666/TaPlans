package com.hijiyam_koubou.taplans.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hijiyam_koubou.taplans.Util;
import com.hijiyam_koubou.taplans.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final String TAG = "onCreateView";
        String dbMsg = "[GalleryFragment]";
        View root = null;
        try {
            GalleryViewModel galleryViewModel =
                    new ViewModelProvider(this).get(GalleryViewModel.class);

            binding = FragmentGalleryBinding.inflate(inflater, container, false);
            root = binding.getRoot();

            ListView gCalLV = binding.gCalLV;
            
            

            final TextView textView = binding.textGallery;
            galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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
        String dbMsg = "[GalleryFragment]";
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

/*
①C:\Program Files\Android\Android Studio\jbr\bin\keytool.exe にたどり着くには
PS C:\> cd "C:\Program Files\Android\Android Studio\jbr\bin"


 .\keytool -exportcert -alias androiddebugkey -keystore path-to-debug-or-production

 -keystore -list -v

 .\keytool -exportcert -alias androiddebugkey -keystore path-to-debug-or-production


 .\keytool -list -v -keystore ~/.android/debug.keystore





debug.keystoreの作成
 .\keytool -genkey -v -keystore android/app/debug.keystore -storepass android -alias androiddebugkey -keypass android -dname "CN=Android Debug,O=Android,C=US"
 -keyalgオプションを指定する必要があります。



名前      My Project 64394
ID      enduring-palace-433014-e0
API キー  AIzaSyCh7OlwR7eXklfuw8ip1R_Sk9zevNvZo1g
パッケージ名  com.hijiyam_koubou.taplans
APIキー   AIzaSyCh7OlwR7eXklfuw8ip1R_Sk9zevNvZo1g
*


* */