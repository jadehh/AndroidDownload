package com.jadehh.androiddownload.mvp.v;


public interface DownloadManagementView {
    void addTaskSuccess();
    void addTaskFail(String msg);
    void updataApp(String version,String url,String content);
}
