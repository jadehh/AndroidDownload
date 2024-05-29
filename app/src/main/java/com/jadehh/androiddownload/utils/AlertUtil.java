package com.jadehh.androiddownload.utils;


import android.widget.Toast;

import com.jadehh.androiddownload.App;
import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.common.AppManager;

import org.xutils.x;



public class AlertUtil {
    private static Toast lt;
    public static void showLoading(){
        lt = new Toast(AppManager.getAppManager().currentActivity());
        lt.show();
    }

    public static void hideLoading(){

    }
}
