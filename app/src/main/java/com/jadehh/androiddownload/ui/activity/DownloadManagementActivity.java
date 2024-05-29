package com.jadehh.androiddownload.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cocosw.bottomsheet.BottomSheet;
import com.coorchice.library.SuperTextView;
import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.adapter.CusAdapter;
import com.jadehh.androiddownload.common.Const;
import com.jadehh.androiddownload.mvp.p.AppConfigPresenter;
import com.jadehh.androiddownload.mvp.p.AppConfigPresenterImp;
import com.jadehh.androiddownload.mvp.p.DownloadManagementPresenter;
import com.jadehh.androiddownload.mvp.p.DownloadManagementPresenterImp;
import com.jadehh.androiddownload.mvp.v.DownloadManagementView;
import com.jadehh.androiddownload.service.DownService;
import com.jadehh.androiddownload.ui.base.BaseActivity;
import com.jadehh.androiddownload.utils.Util;
import com.jadehh.androiddownload.view.DownLoadIngFrm;
import com.jadehh.androiddownload.view.DownLoadSuccessFrm;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_download_management)
public class DownloadManagementActivity extends BaseActivity implements DownloadManagementView {
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.downloading)
    private TextView downloading;
    @ViewInject(R.id.downloadfinish)
    private TextView downloadfinish;
    @ViewInject(R.id.open_add_task_pop)
    private SuperTextView openAddTaskPopBtn;
    private final List<Fragment> fragments = new ArrayList<Fragment>();

    private Intent intent = null;
    private BottomSheet.Builder bottomSheet = null;
    private static final int REQUEST_CODE_CHOOSE = 10086;
    private static final int REQUEST_CODE_SCAN = 10010;


    private DownloadManagementPresenter downloadManagementPresenter;
    private AppConfigPresenter appConfigPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, DownService.class);
        startService(intent);
        downloadManagementPresenter = new DownloadManagementPresenterImp(this);
        appConfigPresenter = new AppConfigPresenterImp();
        initViewPage();
        initBottomMenu();

    }

    private void initViewPage() {
        DownLoadIngFrm downLoadIngFrm = new DownLoadIngFrm();
        DownLoadSuccessFrm downLoadSuccessFrm = new DownLoadSuccessFrm();
        fragments.add(downLoadIngFrm);
        fragments.add(downLoadSuccessFrm);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new CusAdapter(getSupportFragmentManager(), fragments));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                changeTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void changeTab(int index) {
        if (index == 0) {
            downloading.setTextColor(getResources().getColor(R.color.white));
            downloadfinish.setTextColor(getResources().getColor(R.color.trwhite));
        } else {
            downloading.setTextColor(getResources().getColor(R.color.trwhite));
            downloadfinish.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Event(value = R.id.downloading)
    private void downloadingClick(View view) {
        viewPager.setCurrentItem(0);
    }

    @Event(value = R.id.downloadfinish)
    private void downloadfinishClick(View view) {
        viewPager.setCurrentItem(1);
    }

    @Event(value = R.id.open_add_task_pop)
    private void addTaskClick(View view) {
        bottomSheet.show();
    }

//    @Event(value = R.id.open_setting)
//    private void appSettingClick(View view) {
//        intent = new Intent(DownloadManagementActivity.this, AppSettingActivity.class);
//        startActivity(intent);
//    }
//
//    @Event(value = R.id.open_magnet_search)
//    private void magnetSearchClick(View view) {
//        intent = new Intent(DownloadManagementActivity.this, MagnetSearchActivity.class);
//        startActivity(intent);
//    }

    private void initBottomMenu() {
        bottomSheet = new BottomSheet.Builder(this)
                .title(R.string.new_download)
                .sheet(R.menu.down_source)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.qr:
//                                intent = new Intent(DownloadManagementActivity.this, CaptureActivity.class);
//                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                                break;
                            case R.id.url:
                                intent = new Intent(DownloadManagementActivity.this, UrlDownLoadActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.bt:
//                                FilePicker.from(DownloadManagementActivity.this)
//                                        .chooseForBrowser()
//                                        //.chooseForFloder()
//                                        .isSingle()
//                                        //.setMaxCount(0)
//                                        //.setFileTypes("TORRENT")
//                                        .requestCode(REQUEST_CODE_CHOOSE)
//                                        .start();
                                break;

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        if (requestCode == REQUEST_CODE_CHOOSE) {
//            ArrayList<EssFile> fileList = data.getParcelableArrayListExtra(com.ess.filepicker.util.Const.EXTRA_RESULT_SELECTION);
//            String suffix = fileList.get(0).getName().substring(fileList.get(0).getName().lastIndexOf(".") + 1).toUpperCase();
//            if ("TORRENT".equals(suffix)) {
//                Intent intent = new Intent(this, TorrentInfoActivity.class);
//                intent.putExtra("torrentPath", fileList.get(0).getAbsolutePath());
//                intent.putExtra("isDown", true);
//                startActivity(intent);
//            } else {
//                Util.alert(DownloadManagementActivity.this, "选择的文件不是种子文件", Const.ERROR_ALERT);
//            }
//
//        } else if (requestCode == REQUEST_CODE_SCAN) {
//            final String content = data.getStringExtra(Constant.CODED_CONTENT);
//            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
//                    .setTopColorRes(R.color.colorMain)
//                    .setIcon(R.drawable.ic_success)
//                    .setButtonsColorRes(R.color.colorMain)
//                    .setTitle("创建任务")
//                    .setMessage(content)
//                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            downloadManagementPresenter.startTask(content);
//                        }
//                    })
//                    .show();
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addTaskSuccess() {
        // Intent intent =new Intent(UrlDownLoadActivity.this,DownloadManagementActivity.class);
        // startActivity(intent);
        // finish();
    }

    @Override
    public void addTaskFail(String msg) {
        Util.alert(this, msg, Const.ERROR_ALERT);
    }

    @Override
    public void updataApp(String version, final String url, String content) {
//        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
//                .setTopColorRes(R.color.colorMain)
//                .setIcon(R.drawable.ic_success)
//                .setButtonsColorRes(R.color.colorMain)
//                .setTitle("App有更新")
//                .setMessage("当前版本：" + AppConfigUtil.getLocalVersionName() + "，最新版本：" + version +
//                        "\n" + content)
//                .setPositiveButton("确定下载更新", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        downloadManagementPresenter.startTask(url);
//                    }
//                })
//                .show();
    }
}
