package com.example.lynnyuki.cloudfunny.presenter;


import android.Manifest;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.example.lynnyuki.cloudfunny.base.BaseSubscriber;
import com.example.lynnyuki.cloudfunny.base.RxBus;
import com.example.lynnyuki.cloudfunny.base.RxPresenter;
import com.example.lynnyuki.cloudfunny.contract.MainContract;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiarh on 2017/9/25.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {



    private RxPermissions rxPermissions;

    private Context context;

    @Inject
    public MainPresenter( RxPermissions rxPermissions, Context context) {
        this.rxPermissions = rxPermissions;
        this.context = context;
    }



    @Override
    public void checkPermissions() {
        addSubscribe(rxPermissions.request(Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        // 当所有权限都允许之后，返回true
                        if (aBoolean) {
                            mView.getPermissionSuccess();
                        }
                        // 只要有一个权限禁止，返回false，
                        // 下一次申请只申请没通过申请的权限
                        else {
                            mView.showPermissionDialog();
                        }
                    }
                }));
    }


    @Override
    public void setDayOrNight() {
        addSubscribe(RxBus.getInstance().register(Integer.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<Integer>(context, mView) {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer == 1000) {
                            mView.changeDayOrNight(true);
                        }
                    }
                }));
    }
}