package com.jadehh.androiddownload.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.jadehh.androiddownload.R;
import com.permissionx.guolindev.PermissionX;


public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initPermission();

    }


    public void onPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                goHome();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent,0);
            }
        }
    }


    private void goHome() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void initPermission() {
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.INTERNET)
                .onExplainRequestReason((scope, deniedList) -> {
                    scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel");
                }).request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        onPermission();
                    } else {
                        Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show();
                    }
                });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    goHome();
                } else {
                    onPermission();
                }
            }
        }
    }
}
