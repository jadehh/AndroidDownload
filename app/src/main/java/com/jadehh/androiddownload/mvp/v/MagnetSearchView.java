package com.jadehh.androiddownload.mvp.v;

import com.jadehh.androiddownload.mvp.e.MagnetInfo;

import java.util.List;


public interface MagnetSearchView {
    void refreshData(List<MagnetInfo> info);
    void moreOption(MagnetInfo magnet);
}
