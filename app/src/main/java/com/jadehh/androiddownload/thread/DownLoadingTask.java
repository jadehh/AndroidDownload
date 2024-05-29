package com.jadehh.androiddownload.thread;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;
import com.jadehh.androiddownload.utils.DownUtil;

import java.util.List;


public class DownLoadingTask extends AsyncTask<Void,List<DownloadTaskEntity>,List<DownloadTaskEntity>> {
    public DownLoadingTask(){

    }
    @Override
    protected List<DownloadTaskEntity> doInBackground(Void... objects) {
        while (DownUtil.getInstance().isIsLoopDown()){
            DownUpdateUI.getInstance().downUpdate();
            SystemClock.sleep(1000);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(List<DownloadTaskEntity>... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<DownloadTaskEntity> downloadTaskEntities) {
        super.onPostExecute(downloadTaskEntities);
    }
}
