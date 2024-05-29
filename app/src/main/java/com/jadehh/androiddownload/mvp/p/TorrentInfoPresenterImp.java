package com.jadehh.androiddownload.mvp.p;

import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.common.Const;
import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;
import com.jadehh.androiddownload.mvp.e.TorrentInfoEntity;
import com.jadehh.androiddownload.mvp.m.DownLoadModel;
import com.jadehh.androiddownload.mvp.m.DownLoadModelImp;
import com.jadehh.androiddownload.mvp.m.TaskModel;
import com.jadehh.androiddownload.mvp.m.TaskModelImp;
import com.jadehh.androiddownload.mvp.v.TorrentInfoView;
import com.jadehh.androiddownload.utils.FileTools;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;

import org.xutils.x;

import java.io.File;
import java.util.List;


public class TorrentInfoPresenterImp implements TorrentInfoPresenter {
    private TorrentInfoView torrentInfoView;
    private String torrentPath;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    private List<TorrentInfoEntity> list=null;
    public TorrentInfoPresenterImp(TorrentInfoView torrentInfoView,String torrentPath){
        this.torrentInfoView=torrentInfoView;
        this.torrentPath=torrentPath;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
        list=downLoadModel.getTorrentInfo(torrentPath);
        torrentInfoView.initTaskListView(list);

    }

    @Override
    public void startTask(List<TorrentInfoEntity> checkList) {
        //String path=task.getLocalPath()+ File.separator+task.getmFileName();
//        TorrentInfo torrentInfo= XLTaskHelper.instance(x.app().getApplicationContext()).getTorrentInfo(torrentPath);
        TorrentInfo torrentInfo = new TorrentInfo(new File(""));
        List<DownloadTaskEntity> tasks=taskModel.findTaskByHash(torrentInfo.mInfoHash);
        if(tasks!=null && tasks.size()>0){
            DownloadTaskEntity task=tasks.get(0);
            if(!FileTools.exists(task.getLocalPath()+ File.separator+task.getmFileName())) {
                downLoadModel.startTorrentTask(task);
                torrentInfoView.startTaskSuccess();
            }else if(task.getmTaskStatus()== Const.DOWNLOAD_CONNECTION
                    || task.getmTaskStatus()== Const.DOWNLOAD_LOADING
                    || task.getmTaskStatus()== Const.DOWNLOAD_FAIL
                    || task.getmTaskStatus()== Const.DOWNLOAD_STOP
                    || task.getmTaskStatus()== Const.DOWNLOAD_WAIT){
                torrentInfoView.startTaskFail(x.app().getString(R.string.task_earlier_has));
            }else if(task.getmTaskStatus()== Const.DOWNLOAD_SUCCESS){
                torrentInfoView.startTaskFail(x.app().getString(R.string.task_earlier_success));
            }
        }else{
            downLoadModel.startTorrentTask(torrentPath);
            torrentInfoView.startTaskSuccess();
        }
    }
}
