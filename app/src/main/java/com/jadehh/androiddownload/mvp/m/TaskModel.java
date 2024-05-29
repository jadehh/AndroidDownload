package com.jadehh.androiddownload.mvp.m;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;

import java.util.List;


public interface TaskModel {
    List<DownloadTaskEntity> findAllTask();
    List<DownloadTaskEntity> findTaskByUrl(String url);
    List<DownloadTaskEntity> findTaskByHash(String hash);
    DownloadTaskEntity findTaskById(int id);
    List<DownloadTaskEntity> findLoadingTask();
    List<DownloadTaskEntity> findDowningTask();
    List<DownloadTaskEntity> findSuccessTask();
    DownloadTaskEntity upDataTask(DownloadTaskEntity task);
    Boolean deleTask(DownloadTaskEntity task);
}
