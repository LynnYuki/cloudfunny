package com.example.lynnyuki.cloudfunny.presenter;

import android.content.Context;

import com.example.lynnyuki.cloudfunny.base.BaseSubscriber;
import com.example.lynnyuki.cloudfunny.base.RxPresenter;
import com.example.lynnyuki.cloudfunny.contract.ZhiHuContract;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuBean;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuContentBean;
import com.example.lynnyuki.cloudfunny.model.http.ZhiHuApi;


import javax.inject.Inject;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 知乎Presenter
 */
public class ZhiHuPresenter extends RxPresenter<ZhiHuContract.View> implements ZhiHuContract.Presenter {
    private ZhiHuApi zhiHuApi;
    private Context context;


    @Inject
    public ZhiHuPresenter(ZhiHuApi zhiHuApi,Context context){
        this.zhiHuApi= zhiHuApi;
        this.context = context;

    }
    @Override
    public void getZhiHuData() {
        addSubscribe(zhiHuApi.getZhiHu()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new BaseSubscriber<ZhiHuBean>(context,mView){
                @Override
                public  void onNext(ZhiHuBean zhiHuBean){
                mView.showZhiHuData(zhiHuBean);
            }
                @Override
                public void onError(Throwable t){
                super.onError(t);
                mView.failGetData();
            }
        }));

    }

    @Override
    public void getZhiHuContent(int id) {

        addSubscribe(zhiHuApi.getZhiHuContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ZhiHuContentBean>(context,mView){
                    @Override
                    public  void onNext(ZhiHuContentBean zhiHuContentBean){
                        mView.showZhiHuContent(zhiHuContentBean);

                    }
                    @Override
                    public void onError(Throwable t){
                        super.onError(t);
                        mView.failGetData();
                    }
                }));

    }

    @Override
    public void getZhiHuBefore(String date) {
        addSubscribe(zhiHuApi.getZhiHuBefore(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new BaseSubscriber<ZhiHuBean>(context,mView){
                @Override
                public  void onNext(ZhiHuBean zhiHuBean){
                    mView.showZhiHuData(zhiHuBean);
                }
                @Override
                public void onError(Throwable t){
                    super.onError(t);
                    mView.failGetData();
                }
            }));

    }


}
