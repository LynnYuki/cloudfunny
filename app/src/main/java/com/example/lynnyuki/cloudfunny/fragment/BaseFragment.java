package com.example.lynnyuki.cloudfunny.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.net.NetworkInfo;

public abstract class BaseFragment extends Fragment {
    private static final String TAG="BaseFragment";
    private Unbinder myBinder;
    View view;
    public BaseFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        view = initView(inflater,container,savedInstanceState);
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
        Log.d(TAG,"销毁");
    }

    @Override
    public void setMenuVisibility(boolean menuVisibility){
        super.setMenuVisibility(menuVisibility);
        if (getView()!= null){
            getView().setVisibility(menuVisibility ? View.VISIBLE:View.INVISIBLE);
            Log.d(TAG,"可见");
        }
    }


    public void onAattach(Context context){
        super.onAttach(context);
        Log.d(TAG,"onAttach");
    }


}
