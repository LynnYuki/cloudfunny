package com.example.lynnyuki.cloudfunny.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lynnyuki.cloudfunny.fragment.BlankFragment;

public class OneFragmentPageAdapter extends FragmentPagerAdapter {

    private String[] myTitles;

    public OneFragmentPageAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.myTitles = titles;

    }

    Fragment myFragment = null;

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(BlankFragment.KEY_ARG_POSITION, position);
        switch (position) {
            case 0:
                myFragment = BlankFragment.newInstance(bundle);
                break;
            case 1:
                myFragment = BlankFragment.newInstance(bundle);
                break;
            case 2:
                myFragment = BlankFragment.newInstance(bundle);
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
