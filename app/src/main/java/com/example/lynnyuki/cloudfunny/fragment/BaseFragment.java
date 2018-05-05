package com.example.lynnyuki.cloudfunny.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private Unbinder myBinder;

    public BaseFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view = initView(inflater,container,savedInstanceState);
        myBinder = ButterKnife.bind(this,view);
        initListener(view);
        return view;
    }
    protected  abstract void  initListener(View view);
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(myBinder == null){
            myBinder.unbind();
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisibility){
        super.setMenuVisibility(menuVisibility);
        if (getView()!= null){
            getView().setVisibility(menuVisibility ? View.VISIBLE:View.INVISIBLE);

        }
    }

}
