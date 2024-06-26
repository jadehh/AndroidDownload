package com.jadehh.androiddownload.mvp.m;

import android.os.SystemClock;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;
import com.jadehh.androiddownload.mvp.e.TorrentInfoEntity;
import com.jadehh.androiddownload.common.Const;
import com.jadehh.androiddownload.utils.AppSettingUtil;
import com.jadehh.androiddownload.utils.DBTools;
import com.jadehh.androiddownload.utils.FileTools;
import com.orhanobut.logger.Logger;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.GetTaskId;
import com.xunlei.downloadlib.parameter.TorrentFileInfo;
import com.xunlei.downloadlib.parameter.TorrentInfo;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import org.xutils.ex.DbException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DownLoadModelImp implements DownLoadModel {

    private int time = 0;
    public DownLoadModelImp(){

    }
    @Override
    public Boolean startTorrentTask(DownloadTaskEntity bt) {
        String path=bt.getUrl();
        try {
            DBTools.getInstance().db().delete(bt);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return startTorrentTask(path,null);
    }

    @Override
    public Boolean startTorrentTask(String btpath) {
        return startTorrentTask(btpath,null);
    }
    @Override
    public Boolean startTorrentTask(DownloadTaskEntity bt, int[] indexs) {
        String path=bt.getLocalPath()+ File.separator+bt.getmFileName();
        return startTorrentTask(path,indexs);
    }

    private void sleep() {
        SystemClock.sleep(10);
        time += 10;
    }


    @Override
    public Boolean startUrlTask(String url) {
        DownloadTaskEntity task=new DownloadTaskEntity();
        task.setTaskType(Const.URL_DOWNLOAD);
        task.setUrl(url);
        task.setLocalPath(AppSettingUtil.getInstance().getFileSavePath());
        try {
            GetTaskId getTaskId = XLTaskHelper.get().addThunderTask(url, new File(AppSettingUtil.getInstance().getFileSavePath()));
            XLTaskInfo taskInfo = XLTaskHelper.get().getDwonloadTaskInfo(getTaskId.mTaskId);
            task.setmFileName(getTaskId.getFileName());
            task.setmFileSize(taskInfo.mFileSize);
            task.setmTaskStatus(taskInfo.mTaskStatus);
            task.setTaskId(getTaskId.mTaskId);
            task.setmDCDNSpeed(taskInfo.mAdditionalResDCDNSpeed);
            task.setmDownloadSize(taskInfo.mDownloadSize);
            task.setmDownloadSpeed(taskInfo.mDownloadSpeed);
            task.setFile(true);
            task.setCreateDate(new Date());
            DBTools.getInstance().db().saveBindingId(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean startTorrentTask(String btpath, int[] indexs) {
//        DownloadTaskEntity task=new DownloadTaskEntity();
//        TorrentInfo torrentInfo= XLTaskHelper.instance(x.app().getApplicationContext()).getTorrentInfo(btpath);
//        if(indexs==null || indexs.length<=0) {
//            int i = 0;
//            indexs = new int[torrentInfo.mSubFileInfo.length];
//            for (TorrentFileInfo torrent : torrentInfo.mSubFileInfo) {
//                indexs[i++] = torrent.mFileIndex;
//            }
//        }
//        String savePath= AppSettingUtil.getInstance().getFileSavePath();
//        if(torrentInfo.mIsMultiFiles) {
//            savePath += File.separator + torrentInfo.mMultiFileBaseFolder;
//            task.setmFileName(torrentInfo.mMultiFileBaseFolder);
//        }else{
//            if(torrentInfo.mSubFileInfo.length>1) {
//                savePath += File.separator + FileTools.getFileNameWithoutSuffix(btpath);
//                task.setmFileName(FileTools.getFileNameWithoutSuffix(btpath));
//            }else{
//                task.setmFileName(torrentInfo.mSubFileInfo[0].mFileName);
//            }
//        }
//        long taskId= 0;
//        try {
//            taskId = XLTaskHelper.instance(x.app().getApplicationContext()).addTorrentTask(btpath, savePath,indexs);
//            XLTaskInfo taskInfo = XLTaskHelper.instance(x.app().getApplicationContext()).getTaskInfo(taskId);
//            task.setLocalPath(savePath);
//            task.setFile(!torrentInfo.mIsMultiFiles);
//            task.setHash(torrentInfo.mInfoHash);
//            task.setUrl(btpath);
//            task.setmFileSize(taskInfo.mFileSize);
//            task.setmTaskStatus(taskInfo.mTaskStatus);
//            task.setTaskId(taskId);
//            task.setmDCDNSpeed(taskInfo.mAdditionalResDCDNSpeed);
//            task.setmDownloadSize(taskInfo.mDownloadSize);
//            task.setmDownloadSpeed(taskInfo.mDownloadSpeed);
//            task.setTaskType(Const.BT_DOWNLOAD);
//            task.setCreateDate(new Date());
//            DBTools.getInstance().db().saveBindingId(task);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }

    @Override
    public Boolean startTask(DownloadTaskEntity task) {
        try {
            GetTaskId taskId = new GetTaskId(task.getTaskId(),new File(task.getLocalPath()),task.getmFileName(),task.getUrl());
            if(task.getTaskType()==Const.BT_DOWNLOAD){
                Logger.t(task.getLocalPath());
                TorrentInfo torrentInfo= XLTaskHelper.get().getTorrentInfo(new File(task.getLocalPath()));
                int i=0;
                int[] indexs=new int[torrentInfo.mSubFileInfo.length];
                for(TorrentFileInfo torrent:torrentInfo.mSubFileInfo) {
                    indexs[i++]=torrent.mFileIndex;
                }
                taskId = XLTaskHelper.get().addThunderTask(task.getUrl(),new File(task.getLocalPath()));
            }else if(task.getTaskType()==Const.URL_DOWNLOAD){
                taskId = XLTaskHelper.get().addThunderTask(task.getUrl(),new File(task.getLocalPath()));
            }
            XLTaskInfo taskInfo  = XLTaskHelper.get().getDwonloadTaskInfo(taskId.mTaskId);
            task.setmFileSize(taskInfo.mFileSize);
            task.setTaskId(taskInfo.mTaskId);
            task.setmTaskStatus(taskInfo.mTaskStatus);
            DBTools.getInstance().db().saveOrUpdate(task);
            if(taskInfo.mTaskId==0)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean stopTask(DownloadTaskEntity task) {
//        try {
//            XLTaskHelper.instance(x.app().getApplicationContext()).stopTask(task.getTaskId());
//            task.setmTaskStatus(Const.DOWNLOAD_STOP);
//            task.setmDownloadSpeed(0);
//            task.setmDCDNSpeed(0);
//            DBTools.getInstance().db().saveOrUpdate(task);
//        } catch (DbException e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }

    @Override
    public Boolean deleTask(DownloadTaskEntity task, Boolean deleFile) {
        try {
            DBTools.getInstance().db().delete(task);
            if(deleFile){
                if(task.getFile()){
                    FileTools.deleteFile(task.getLocalPath()+ File.separator+task.getmFileName());
                }else{
                    FileTools.deleteDir(task.getLocalPath());
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleTask(DownloadTaskEntity task, Boolean stopTask, Boolean deleFile) {
        if(stopTask){
//            XLTaskHelper.instance(x.app().getApplicationContext()).stopTask(task.getTaskId());
        }
        return deleTask(task,deleFile);
    }

    @Override
    public List<TorrentInfoEntity> getTorrentInfo(DownloadTaskEntity bt) {
        String path=bt.getLocalPath()+ File.separator+bt.getmFileName();
        return getTorrentInfo(path);
    }

    @Override
    public List<TorrentInfoEntity> getTorrentInfo(String btpath) {
//        TorrentInfo torrentInfo= XLTaskHelper.instance(x.app().getApplicationContext()).getTorrentInfo(btpath);
        List<TorrentInfoEntity> list=new ArrayList<>();
//        for(TorrentFileInfo torrent:torrentInfo.mSubFileInfo){
//            TorrentInfoEntity tie=new TorrentInfoEntity();
//            tie.setHash(torrent.hash);
//            tie.setmFileIndex(torrent.mFileIndex);
//            tie.setmFileName(torrent.mFileName);
//            tie.setmFileSize(torrent.mFileSize);
//            tie.setmSubPath(torrent.mSubPath);
//            tie.setmRealIndex(torrent.mRealIndex);
//            tie.setPath(AppSettingUtil.getInstance().getFileSavePath()+
//                    File.separator+torrentInfo.mMultiFileBaseFolder+
//                    File.separator+torrent.mSubPath+File.separator+torrent.mFileName);
//            list.add(tie);
//        }
        return list;
    }
}
