package com.example.lynnyuki.cloudfunny.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.lynnyuki.cloudfunny.view.Image.ImageFragment;

/**
 * 图片加载ViewPager适配器
 */
public class OneFragmentPageAdapter extends FragmentPagerAdapter {

    private String[] myTitles;

    public OneFragmentPageAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.myTitles = titles;

    }

    Fragment myFragment = null;

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
        //super.destroyItem(container, position, object);
    }
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ImageFragment.KEY_ARG_POSITION, position);
        switch (position) {
            case 0:
                myFragment = ImageFragment.newInstance(bundle);
                break;
            case 1:
                myFragment = ImageFragment.newInstance(bundle);
                break;
            case 2:
                myFragment = ImageFragment.newInstance(bundle);
                break;
        }
        return myFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myTitles[position];
    }

    @Override
    public int getCount() {
        return 3;
    }



}
