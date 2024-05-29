package com.jadehh.androiddownload.mvp.p;

import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;
import com.jadehh.androiddownload.mvp.m.DownLoadModel;
import com.jadehh.androiddownload.mvp.m.DownLoadModelImp;
import com.jadehh.androiddownload.mvp.m.TaskModel;
import com.jadehh.androiddownload.mvp.m.TaskModelImp;
import com.jadehh.androiddownload.mvp.v.DownloadManagementView;
import com.jadehh.androiddownload.common.Const;

import org.xutils.x;

import java.util.List;


public class DownloadManagementPresenterImp implements DownloadManagementPresenter {
    private DownloadManagementView downloadManagementView;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    public DownloadManagementPresenterImp(DownloadManagementView downloadManagementView){
        this.downloadManagementView=downloadManagementView;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
    }

    @Override
    public void startTask(String url) {
        List<DownloadTaskEntity> tasks=taskModel.findTaskByUrl(url);
        if(tasks!=null && tasks.size()>0){
            DownloadTaskEntity task=tasks.get(0);
            if(task.getmTaskStatus()== Const.DOWNLOAD_CONNECTION
                    || task.getmTaskStatus()== Const.DOWNLOAD_LOADING
                    || task.getmTaskStatus()== Const.DOWNLOAD_FAIL
                    || task.getmTaskStatus()== Const.DOWNLOAD_STOP){
                downloadManagementView.addTaskFail(x.app().getString(R.string.task_earlier_has));
            }else if(task.getmTaskStatus()== Const.DOWNLOAD_SUCCESS){
                downloadManagementView.addTaskFail(x.app().getString(R.string.task_earlier_success));
            }
        }else{
            Boolean b=downLoadModel.startUrlTask(url);
            if(b)
                downloadManagementView.addTaskSuccess();
            else
                downloadManagementView.addTaskFail(x.app().getString(R.string.add_task_fail));

        }
    }
}
