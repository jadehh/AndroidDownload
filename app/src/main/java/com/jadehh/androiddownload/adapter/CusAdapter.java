package com.jadehh.androiddownload.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jadehh.androiddownload.view.DownLoadSuccessFrm;

import java.util.List;

public class CusAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public CusAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments=mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

