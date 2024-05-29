package com.jadehh.androiddownload.mvp.v;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;

import java.util.List;


public interface DownLoadIngView {
    void startTask(DownloadTaskEntity task);
    void sopTask(DownloadTaskEntity task);
    void openFile(DownloadTaskEntity task);
    void deleTask(DownloadTaskEntity task);
    void refreshData( List<DownloadTaskEntity> tasks);
    void alert(String msg,int msgType);
}
