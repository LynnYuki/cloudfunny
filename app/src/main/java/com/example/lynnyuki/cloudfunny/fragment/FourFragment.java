package com.example.lynnyuki.cloudfunny.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends BaseFragment {


    public FourFragment() {
        // Required empty public constructor
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    protected void initListener(View view) {

    }

}
