package com.example.lynnyuki.cloudfunny.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;


import com.example.lynnyuki.cloudfunny.util.AppActivityTaskManager;
import com.kc.unsplash.Unsplash;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity extends SupportActivity {
    protected Activity mContext;

    private static final String TAG = "BaseActivity";

    private  Unbinder myUnbinder;

    protected abstract int getLayoutId();

    protected abstract void initialize();

    @SuppressLint("RestrictedApi")

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        myUnbinder = ButterKnife.bind(this);
        onViewCreated();
        AppActivityTaskManager.getInstance().addActivity(this);
        setTitle("");
        initialize();


    }

    protected  void onViewCreated(){

    }




    @Override
    protected  void onDestroy(){
        super.onDestroy();
//        myUnbinder.unbind();
        Log.d(TAG,"销毁");
        AppActivityTaskManager.getInstance().removeActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 回退
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
