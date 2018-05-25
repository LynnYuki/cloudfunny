package com.example.lynnyuki.cloudfunny.contract;

import com.example.lynnyuki.cloudfunny.base.BasePresenter;
import com.example.lynnyuki.cloudfunny.base.BaseView;
import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;

/**
 * 视频Contract
 */

public interface EyepetizerContract {

    interface View extends BaseView {
        /**
         * 成功获取视频数据
         *
         * @param dailyBean
         */
        void showDailyVideoData(VideoBean dailyBean);

        /**
         * 获取数据失败
         */
        void failGetDailyData();

        /**
         * 成功获取热门视频数据
         *
         * @param hotBean
         */
        void showHotVideoData(VideoBean hotBean);

        /**
         * 获取数据失败
         */
        void failGetHotData();

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取热门和每日视频
         *
         * @param page
         * @param udid
         */
        void getVideoData(int page, String udid, String strategy, String vc, String deviceModel);

        /**
         * 获取每日视频
         *
         * @param page
         * @param udid
         */
        void getDailyVideoData(int page, String udid);

    }
}
