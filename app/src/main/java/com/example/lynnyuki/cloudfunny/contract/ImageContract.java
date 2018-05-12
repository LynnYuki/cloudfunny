package com.example.lynnyuki.cloudfunny.contract;

import com.example.lynnyuki.cloudfunny.base.BaseView;
import com.example.lynnyuki.cloudfunny.base.BasePresenter;

public interface ImageContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 下载图片
         *
         * @param url
         */
        void download(String url);

        /**
         * 设置壁纸
         *
         * @param url
         */
        void setWallpaper(String url);
    }
}
