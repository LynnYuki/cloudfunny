package com.example.lynnyuki.cloudfunny.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;

import com.kc.unsplash.Unsplash;

import butterknife.ButterKnife;
import butterknife.Unbinder;



public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private  Unbinder myUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        myUnbinder = ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null){
            Log.d(TAG,"创建");
        }
    }


    public abstract int getLayoutId();

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        myUnbinder.unbind();
        Log.d(TAG,"销毁");
    }



}
