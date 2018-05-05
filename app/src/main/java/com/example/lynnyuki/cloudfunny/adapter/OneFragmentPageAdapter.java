package com.example.lynnyuki.cloudfunny.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lynnyuki.cloudfunny.fragment.BlankFragment;

public class OneFragmentPageAdapter extends FragmentPagerAdapter {

    private String[] myTitles;
    public OneFragmentPageAdapter(FragmentManager fm,String[] titles){
        super(fm);
        this.myTitles = titles;

    }
    Fragment myFragment = null;
    @Override
    public Fragment getItem(int position ){
        switch (position){
            case 0:
                myFragment = new BlankFragment();
                break;
            case 1:
                myFragment = new BlankFragment();
                break;
            case 2:
                myFragment = new BlankFragment();
                break;
        }
        return myFragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return myTitles[position];
    }

    @Override
    public int getCount(){
        return 3;
    }


}
