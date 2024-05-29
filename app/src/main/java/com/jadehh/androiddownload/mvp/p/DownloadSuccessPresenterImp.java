package com.jadehh.androiddownload.mvp.p;

import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.common.Const;
import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;
import com.jadehh.androiddownload.mvp.m.DownLoadModel;
import com.jadehh.androiddownload.mvp.m.DownLoadModelImp;
import com.jadehh.androiddownload.mvp.m.TaskModel;
import com.jadehh.androiddownload.mvp.m.TaskModelImp;
import com.jadehh.androiddownload.mvp.v.DownLoadSuccessView;

import org.xutils.x;

import java.util.List;


public class DownloadSuccessPresenterImp implements DownloadSuccessPresenter {
    private DownLoadSuccessView downLoadSuccessView;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    private List<DownloadTaskEntity> list;


    public DownloadSuccessPresenterImp(DownLoadSuccessView downLoadSuccessView){
        this.downLoadSuccessView=downLoadSuccessView;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
        list=taskModel.findSuccessTask();
        downLoadSuccessView.initTaskListView(list);
    }
    @Override
    public List<DownloadTaskEntity> getDownSuccessTaskList() {
        list=taskModel.findSuccessTask();
        return list;
    }



    @Override
    public void deleTask(DownloadTaskEntity task,Boolean deleFile) {
        Boolean b=downLoadModel.deleTask(task,deleFile);
        if(b) {
            downLoadSuccessView.refreshData();
            downLoadSuccessView.alert(x.app().getString(R.string.dele_success), Const.SUCCESS_ALERT);
        }else{
            downLoadSuccessView.alert(x.app().getString(R.string.dele_fail), Const.ERROR_ALERT);
        }
    }


}
