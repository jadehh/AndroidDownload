package com.jadehh.androiddownload.listener;

public interface HttpResultListener {
    void onSuccess(String result);
    void onError(Throwable ex);
}
