package com.example.lynnyuki.cloudfunny.fragment;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment 基类
 */

public abstract class BaseFragment extends SupportFragment {
    private static final String TAG="BaseFragment";

    private Unbinder myBinder;

    protected View mview;

    protected Activity mActivity;

    protected Context  mContext;

    protected abstract void initialize();

    protected abstract int getLayoutId();

    protected boolean isInited = false;
    public BaseFragment(){

    }

    @Override
    public void onAttach(Context context){
        mActivity = (Activity)context;
        mContext = context;
        super.onAttach(context);
        Log.d(TAG,"onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState){
        mview = inflater.inflate(getLayoutId(),null);
        myBinder = ButterKnife.bind(this,mview);
        return mview;

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;
        initialize();

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        myBinder.unbind();
        Log.d(TAG,"销毁");
    }
    //设置Fragment可见
    @Override
    public void setMenuVisibility(boolean menuVisibility){
        super.setMenuVisibility(menuVisibility);
        if (getView()!= null){
            getView().setVisibility(menuVisibility ? View.VISIBLE:View.INVISIBLE);
            Log.d(TAG,"可见");
        }
    }




//    public void onAattach(Context context){
//        super.onAttach(context);
//        Log.d(TAG,"onAttach");
//    }


}
