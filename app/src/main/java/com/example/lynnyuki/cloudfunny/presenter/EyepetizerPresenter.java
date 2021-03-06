package com.example.lynnyuki.cloudfunny.presenter;
import android.content.Context;

import com.example.lynnyuki.cloudfunny.base.BaseSubscriber;
import com.example.lynnyuki.cloudfunny.base.RxPresenter;
import com.example.lynnyuki.cloudfunny.contract.EyepetizerContract;
import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;
import com.example.lynnyuki.cloudfunny.model.http.EyepetizerApi;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 开眼视频Presenter
 */

public class EyepetizerPresenter extends RxPresenter<EyepetizerContract.View> implements EyepetizerContract.Presenter {

    private EyepetizerApi eyepetizerApi;

    private Context context;

    @Inject
    public EyepetizerPresenter(EyepetizerApi eyepetizerApi, Context context) {
        this.eyepetizerApi = eyepetizerApi;
        this.context = context;
    }

    /**
     * 获取热门视频数据
     * @param page
     * @param udid
     */
    @Override
    public void getDailyVideoData(int page, String udid) {
        addSubscribe(eyepetizerApi.getDailyVideo(page, udid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<VideoBean>(context, mView) {
                    @Override
                    public void onNext(VideoBean dailyBean) {
                        mView.showDailyVideoData(dailyBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mView.failGetDailyData();
                    }
                }));
    }

    /**
     * 获取日常更新视频数据
     * @param page
     * @param udid
     * @param strategy
     * @param vc
     * @param deviceModel
     */
    @Override
    public void getVideoData(final int page, final String udid, String strategy, String vc, String deviceModel) {
        addSubscribe(eyepetizerApi.getHotVideo(strategy, vc, deviceModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<VideoBean>() {
                    @Override
                    public void accept(VideoBean hotVideoBean) throws Exception {
                        mView.showHotVideoData(hotVideoBean);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.failGetHotData();
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<VideoBean, Publisher<VideoBean>>() {
                    @Override
                    public Publisher<VideoBean> apply(VideoBean videoBean) throws Exception {
                        return eyepetizerApi.getDailyVideo(page, udid);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<VideoBean>(context, mView) {
                    @Override
                    public void onNext(VideoBean dailyBean) {
                        mView.showDailyVideoData(dailyBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mView.failGetDailyData();
                    }
                }));
    }


}