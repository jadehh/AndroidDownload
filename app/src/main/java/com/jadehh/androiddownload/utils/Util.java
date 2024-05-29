package com.jadehh.androiddownload.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.jadehh.androiddownload.ui.common.Const;
import org.xutils.x;
import es.dmoral.toasty.Toasty;


public class Util {

    public static void alert(Activity activity, String msg, int msgType){
        if(Const.ERROR_ALERT==msgType) {
            Toasty.error(activity, msg, Toast.LENGTH_SHORT, true).show();

        }else if(Const.SUCCESS_ALERT==msgType) {
            Toasty.success(activity, msg, Toast.LENGTH_SHORT, true).show();
            ;
        }else if(Const.WARNING_ALERT==msgType) {
            Toasty.warning(activity, msg, Toast.LENGTH_SHORT, true).show();

        }
    }
    public static boolean checkApkExist( String packageName){
        if (StringUtil.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = x.app().getApplicationContext().getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void putTextIntoClip(String text){
        ClipboardManager clipboardManager = (ClipboardManager) x.app().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(text, text);
        clipboardManager.setPrimaryClip(clipData);
    }

    public static String getFileSuffix(String name){
        return name.substring(name.lastIndexOf(".") + 1).toUpperCase();
    }


}
