package com.jadehh.androiddownload.mvp.p;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;

import java.util.List;


public interface DownloadIngPresenter {
    List<DownloadTaskEntity> getDownloadingTaskList();
    void startTask(DownloadTaskEntity task);
    void stopTask(DownloadTaskEntity task);
    void  deleTask(DownloadTaskEntity task,Boolean deleFile);
    void refreshData();
    void stopLoop();
    void clearHandler();
}
