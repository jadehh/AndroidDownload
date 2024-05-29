package com.jadehh.androiddownload.mvp.p;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;

import java.util.List;


public interface DownloadSuccessPresenter {
    List<DownloadTaskEntity> getDownSuccessTaskList();
    void  deleTask(DownloadTaskEntity task, Boolean deleFile);
}
