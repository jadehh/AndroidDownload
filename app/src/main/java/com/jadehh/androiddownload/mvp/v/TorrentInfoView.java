package com.jadehh.androiddownload.mvp.v;

import com.jadehh.androiddownload.mvp.e.TorrentInfoEntity;

import java.util.List;


public interface TorrentInfoView {
    void initTaskListView(List<TorrentInfoEntity> list);
    void itemClick(int index);
    void startTaskSuccess();
    void startTaskFail(String msg);
    boolean getIsDown();
    void playerViedo(TorrentInfoEntity te);
}
