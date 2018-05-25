package com.example.lynnyuki.cloudfunny.contract;

import com.example.lynnyuki.cloudfunny.base.BasePresenter;
import com.example.lynnyuki.cloudfunny.base.BaseView;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuBean;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuContentBean;

/**
 * 知乎 Contract
 */

public interface ZhiHuContract {

    interface View extends BaseView {

        /**
         * 成功获取数据
         *
         * @param zhiHuBean
         */
        void showZhiHuData(ZhiHuBean zhiHuBean);

        /**
         *  显示具体内容
         */
        void showZhiHuContent(ZhiHuContentBean zhiHuContentBean);
        /**
         * 获取数据失败
         */
        void failGetData();
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取知乎日报列表
         */
        void getZhiHuData();

        /**
         * 获取某条新闻具体内容
         */
        void getZhiHuContent(int id);

        /**
         * 获取指定日期之前知乎日报列表
         */

        void getZhiHuBefore(String date);
    }

}
