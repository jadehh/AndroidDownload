package com.jadehh.androiddownload.mvp.p;

import com.jadehh.androiddownload.mvp.e.TorrentInfoEntity;

import java.util.List;


public interface TorrentInfoPresenter {
    void startTask(List<TorrentInfoEntity> checkList);
}
