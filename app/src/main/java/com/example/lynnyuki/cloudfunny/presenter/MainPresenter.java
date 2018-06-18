package com.example.lynnyuki.cloudfunny.presenter;


import android.Manifest;
import android.content.Context;

import com.example.lynnyuki.cloudfunny.config.Constants;
import com.example.lynnyuki.cloudfunny.model.bean.WeatherBean;
import com.example.lynnyuki.cloudfunny.model.http.WeatherApi;
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
 * Main Activity Presenter
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {


    private WeatherApi weatherApi;

    private RxPermissions rxPermissions;

    private Context context;

    @Inject
    public MainPresenter( RxPermissions rxPermissions, Context context,WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
        this.rxPermissions = rxPermissions;
        this.context = context;
    }


    /**
     * 获取权限
     */
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

    /**
     * 获取天气数据
     * @param location
     */
    @Override
    public void getWeather(String location) {
        addSubscribe(weatherApi.getWeather(location, Constants.WEATHER_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<WeatherBean>(context, mView) {
                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        mView.showWeather(weatherBean);
                    }
                }));
    }

    /**
     *判断是否是夜间模式
     */
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