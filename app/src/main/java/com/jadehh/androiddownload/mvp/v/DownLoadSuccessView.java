package com.jadehh.androiddownload.mvp.v;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;

import java.util.List;


public interface DownLoadSuccessView {
    void initTaskListView(List<DownloadTaskEntity> list);
    void deleTask(DownloadTaskEntity task);
    void openFile(DownloadTaskEntity task);
    void refreshData();
    void alert(String msg, int msgType);
}
