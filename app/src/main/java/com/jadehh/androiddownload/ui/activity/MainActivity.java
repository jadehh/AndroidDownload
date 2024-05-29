package com.jadehh.androiddownload.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


import com.github.gzuliyujiang.filepicker.ExplorerConfig;
import com.github.gzuliyujiang.filepicker.FilePicker;
import com.github.gzuliyujiang.filepicker.annotation.ExplorerMode;
import com.github.gzuliyujiang.filepicker.contract.OnFilePickedListener ;
import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.ui.base.BaseActivity;

import com.cocosw.bottomsheet.BottomSheet;
import com.jadehh.androiddownload.ui.common.Const;
import com.jadehh.androiddownload.utils.Util;

import java.io.File;
import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnFilePickedListener  {
    private ExplorerConfig explorerConfig = null;
    private BottomSheet.Builder bottomSheetBuilder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFileExplore();
        initBottomMenu();
    }

    private void initFileExplore() {
        explorerConfig = new ExplorerConfig(this);
        explorerConfig.setRootDir(new File("sdcard"));
        explorerConfig.setLoadAsync(false);
        explorerConfig.setExplorerMode(ExplorerMode.FILE);
        explorerConfig.setOnFilePickedListener(this);
    }
    private void initBottomMenu() {
        bottomSheetBuilder = new BottomSheet.Builder(this)
                .title(R.string.new_download).
                sheet(R.menu.down_source).
                listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.qr:

                                break;
                            case R.id.url:
                                Intent intent = new Intent(MainActivity.this, UrlDownLoadActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.bt:
                                FilePicker picker = new FilePicker(MainActivity.this);
                                picker.setExplorerConfig(explorerConfig);
                                picker.show();
                                break;

                        }
                    }
                });
    }

    @Event(value = R.id.add_download)
    private void searchClick(View view) {
        bottomSheetBuilder.show();
    }

    @Override
    public void onFilePicked(@NonNull File file) {
        if("TORRENT".equals(file.getAbsolutePath())) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("torrentPath", file.getAbsolutePath());
            startActivity(intent);
        }else{
            Util.alert(MainActivity.this,"选择的文件不是种子文件", Const.ERROR_ALERT);
        }
    }
}