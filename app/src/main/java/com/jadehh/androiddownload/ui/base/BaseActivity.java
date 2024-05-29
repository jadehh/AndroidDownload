package com.jadehh.androiddownload.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.coorchice.library.SuperTextView;
import com.jadehh.androiddownload.R;
import com.jadehh.androiddownload.ui.common.AppManager;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class BaseActivity extends AppCompatActivity {
    private TextView topBarTitle=null;
    private SuperTextView closeView=null;
    private SuperTextView rightView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        x.view().inject(this);
    }
    protected void setTopBarTitle(int title) {
        if(topBarTitle==null) {
            topBarTitle = findViewById(R.id.topBarTitle);
        }
        topBarTitle.setText(title);
    }
    protected void setTopBarTitle(String title) {
        if(topBarTitle==null) {
            topBarTitle = findViewById(R.id.topBarTitle);
        }
        topBarTitle.setText(title);
    }

    protected void setTopCloseText(String text) {
        if(closeView==null) {
            closeView = findViewById(R.id.close_view);
        }
        closeView.setText(text);
    }

    protected SuperTextView getRightView(){
        if(rightView==null) {
            rightView = findViewById(R.id.right_view);
        }
        return rightView;
    }

    protected void hideCloseView(){
        if(closeView==null){
            closeView=findViewById(R.id.close_view);
        }
        closeView.setVisibility(View.GONE);
    }

    @Event(value = R.id.close_view)
    private void closeView(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
