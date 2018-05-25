package com.example.lynnyuki.cloudfunny.view.Eyepetizer;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.adapter.EyepetizerHotFragmentPagerAdapter;
import com.example.lynnyuki.cloudfunny.base.BaseActivity;


import java.util.Objects;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * 热门视频排行
 */

public class EyepetizerHotActivity extends BaseActivity {

    private static final String TAG = "EypetizerHotActivity";

    private EyepetizerHotFragmentPagerAdapter eyepetizerHotFragmentPagerAdapter;
    @BindView(R.id.viewpager)
    ViewPager myViewPager;
    @BindView(R.id.tab_layout)
    TabLayout myTablayout;
    @BindArray(R.array.hot_tittles)
    String[] myHotTitles;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return  R.layout.activity_eyepetizer_hot;
    }

    @Override
    protected void initialize() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("热门排行");

        eyepetizerHotFragmentPagerAdapter = new EyepetizerHotFragmentPagerAdapter(getSupportFragmentManager(),myHotTitles);
        myViewPager.setAdapter(eyepetizerHotFragmentPagerAdapter);
        myTablayout.setupWithViewPager(myViewPager);
        myViewPager.setOffscreenPageLimit(3);

    }
}