package com.example.lynnyuki.cloudfunny.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.lynnyuki.cloudfunny.view.Eyepetizer.EyepetizerHotFragment;

/**
 * 视频热门排行ViewPager适配器
 */
public class EyepetizerHotFragmentPagerAdapter extends FragmentPagerAdapter {


    private String[] myTitles;
    Fragment myFragment = null;

    public EyepetizerHotFragmentPagerAdapter (FragmentManager fm, String[] titles) {
        super(fm);
        this.myTitles = titles;

    }


    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(EyepetizerHotFragment.KEY_ARG_POSITION, position);
        switch (position) {
            case 0:
                myFragment = EyepetizerHotFragment.newInstance(bundle);
                break;
            case 1:
                myFragment = EyepetizerHotFragment.newInstance(bundle);
                break;
            case 2:
                myFragment = EyepetizerHotFragment.newInstance(bundle);
                break;
        }
        return myFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myTitles[position];
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
        //super.destroyItem(container, position, object);
    }
}
