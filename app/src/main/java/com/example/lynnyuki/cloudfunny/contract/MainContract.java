package com.example.lynnyuki.cloudfunny.contract;

import com.example.lynnyuki.cloudfunny.base.BasePresenter;
import com.example.lynnyuki.cloudfunny.base.BaseView;
import com.example.lynnyuki.cloudfunny.model.bean.WeatherBean;


/**
 * Main Activity Contract
 */

public interface MainContract {

    interface View extends BaseView {

        /**
         * 天气数据
         *
         * @param weatherBean
         */
        void showWeather(WeatherBean weatherBean);
        /**
         * 未获取权限，弹出提示框
         */
        void showPermissionDialog();

        /**
         * 获取权限成功
         */
        void getPermissionSuccess();

        /**
         * 是否改变
         */
        void changeDayOrNight(boolean changed);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 拉取天气权限
         */
        void getWeather(String location);

        /**
         * 检查权限
         */
        void checkPermissions();


        /**
         * 设置白天/夜间模式
         */
        void setDayOrNight();
    }
}
