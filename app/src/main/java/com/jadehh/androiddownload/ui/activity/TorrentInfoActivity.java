package com.jadehh.androiddownload.ui.activity;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coorchice.library.SuperTextView;
import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.adapter.TorrentInfoAdapter;
import com.jadehh.androiddownload.common.Const;
import com.jadehh.androiddownload.common.MessageEvent;
import com.jadehh.androiddownload.common.Msg;
import com.jadehh.androiddownload.listener.GetThumbnailsListener;
import com.jadehh.androiddownload.mvp.e.TorrentInfoEntity;
import com.jadehh.androiddownload.mvp.p.TorrentInfoPresenter;
import com.jadehh.androiddownload.mvp.p.TorrentInfoPresenterImp;
import com.jadehh.androiddownload.mvp.v.TorrentInfoView;
import com.jadehh.androiddownload.thread.GetTorrentVideoThumbnailsTask;
import com.jadehh.androiddownload.ui.base.BaseActivity;
import com.jadehh.androiddownload.utils.AlertUtil;
import com.jadehh.androiddownload.utils.Util;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_torrent_info)
public class TorrentInfoActivity extends BaseActivity implements TorrentInfoView {
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(R.id.right_view)
    private SuperTextView rightBtn;
    @ViewInject(R.id.start_download)
    private LinearLayout downLinearLayout;
    private List<TorrentInfoEntity> list;
    private List<TorrentInfoEntity> checkList=new ArrayList<>();
    private TorrentInfoAdapter torrentInfoAdapter;
    private TorrentInfoPresenter torrentInfoPresenter;
    private String torrentPath;
    private boolean isCheckAll=false;
    private boolean isDown=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBarTitle(R.string.bt_file_info);
        //rightBtn.setText(R.string.check_all);
        Intent getIntent = new Intent();
        torrentPath=getIntent.getStringExtra("torrentPath");
        isDown=getIntent.getBooleanExtra("isDown",false);
        if(isDown){
            downLinearLayout.setVisibility(View.VISIBLE);
        }
        torrentInfoPresenter=new TorrentInfoPresenterImp(this,torrentPath);
    }

    @Override
    public void initTaskListView(List<TorrentInfoEntity> list) {
        this.list=list;
        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        torrentInfoAdapter=new TorrentInfoAdapter(this,this,this.list);
        recyclerView.setAdapter(torrentInfoAdapter);
        if(!isDown){
            AlertUtil.showLoading();
            new GetTorrentVideoThumbnailsTask(new GetThumbnailsListener() {
                @Override
                public void success(Bitmap bitmap) {
                    torrentInfoAdapter.notifyDataSetChanged();
                    AlertUtil.hideLoading();
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,list);
        }
    }

    @Override
    public void itemClick(int index) {
//        TorrentInfoEntity torrent=list.get(index);
//        if(torrent.getCheck()){
//            torrent.setCheck(false);
//            isCheckAll=false;
//            rightBtn.setText(R.string.check_all);
//        }else{
//            torrent.setCheck(true);
//        }
//        torrentInfoAdapter.notifyDataSetChanged();
//        checkList();
//        setTopBarTitle(String.format(getString(R.string.check_count),list.size()+"",checkList.size()+""));

    }

    @Override
    public void startTaskSuccess() {
        EventBus.getDefault().postSticky(new MessageEvent(new Msg(Const.MESSAGE_TYPE_SWITCH_TAB, 0)));
        finish();
    }

    @Override
    public void startTaskFail(String msg) {
        Util.alert(this,msg, Const.ERROR_ALERT);
    }

    @Override
    public boolean getIsDown() {
        return isDown;
    }

    @Override
    public void playerViedo(TorrentInfoEntity te) {
//        Intent intent = new Intent(this, PlayerActivity.class);
//        intent.putExtra("videoPath", te.getPath());
//        startActivity(intent);
    }

    @Event(value = R.id.right_view)
    private void chheckAllClick(View view) {
//        for(TorrentInfoEntity torrent:list){
//            torrent.setCheck(!isCheckAll);
//        }
//        torrentInfoAdapter.notifyDataSetChanged();
//        if(isCheckAll){
//            isCheckAll=false;
//            rightBtn.setText(R.string.check_all);
//            setTopBarTitle(R.string.check_file);
//        }else{
//            isCheckAll=true;
//            rightBtn.setText(R.string.cancel_check_all);
//            setTopBarTitle(String.format(getString(R.string.check_count),list.size()+"",list.size()+""));
//        }
    }
    @Event(value = R.id.start_download)
    private void startDownClick(View view) {
        checkList();
        torrentInfoPresenter.startTask(checkList);
        //finish();
    }
    private void checkList(){
        checkList.clear();
        for(TorrentInfoEntity torrent:list){
            if(torrent.getCheck()){
                checkList.add(torrent);
            }
        }
    }

}
