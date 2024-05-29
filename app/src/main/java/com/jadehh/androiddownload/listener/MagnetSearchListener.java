package com.jadehh.androiddownload.listener;

import com.jadehh.androiddownload.mvp.e.MagnetInfo;

import java.util.List;


public interface MagnetSearchListener {
    void success(List<MagnetInfo> info);
    void fail(String error);
}
