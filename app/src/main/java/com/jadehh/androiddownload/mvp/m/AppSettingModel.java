package com.jadehh.androiddownload.mvp.m;

import com.jadehh.androiddownload.mvp.e.AppSettingEntity;

import java.util.List;


public interface AppSettingModel {
    List<AppSettingEntity> findAllSetting();
    void saveOrUploadSteeing(AppSettingEntity setting);
    void setSavePath(String path);
    AppSettingEntity getSavePath();
    void setDownCount(String count);
    AppSettingEntity getDownCount();
    AppSettingEntity getMobileNet();
    void setMobileNet(String net);
    AppSettingEntity getDownNotify();
    void setDownNotify(String notify);

}
