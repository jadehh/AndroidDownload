package com.jadehh.androiddownload.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.mvp.p.UrlDownLoadPresenter;
import com.jadehh.androiddownload.mvp.p.UrlDownLoadPresenterImp;
import com.jadehh.androiddownload.mvp.v.UrlDownLoadView;
import com.jadehh.androiddownload.ui.base.BaseActivity;
import com.jadehh.androiddownload.common.Const;
import com.jadehh.androiddownload.utils.Util;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_url_download)
public class UrlDownLoadActivity extends BaseActivity implements UrlDownLoadView {
    @ViewInject(R.id.url_input)
    private EditText urlInput;
    private UrlDownLoadPresenter urlDownLoadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTopBarTitle(R.string.new_download);
        urlDownLoadPresenter = new UrlDownLoadPresenterImp(this);
    }

    @Event(value = R.id.start_download)
    private void startDownloadClick(View view) {
        String url =  urlInput.getText().toString().trim();
        urlDownLoadPresenter.startTask(urlInput.getText().toString().trim());
    }

    @Override
    public void addTaskSuccess() {
        // Intent intent =new Intent(UrlDownLoadActivity.this,DownloadManagementActivity.class);
        // startActivity(intent);
        finish();
    }

    @Override
    public void addTaskFail(String msg) {
        Util.alert(this, msg, Const.ERROR_ALERT);
    }
}
