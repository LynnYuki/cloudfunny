package com.example.lynnyuki.cloudfunny.model.http;

import com.example.lynnyuki.cloudfunny.model.bean.VideoBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 开眼视频API
 */

public interface EyepetizerApi {

    String HOST = "http://baobab.kaiyanapp.com/";

    /**
     * 日常视频
     *
     * @param page
     * @return
     */
    @GET("api/v5/index/tab/allRec")
    Flowable<VideoBean> getDailyVideo(@Query("page") int page, @Query("udid") String udid);

    /**
     * 获取热门视频
     *
     * @param strategy
     * @return
     */
    @GET("api/v4/rankList/videos")
    Flowable<VideoBean> getHotVideo(@Query("strategy") String strategy, @Query("vc") String vc, @Query("deviceModel") String deviceModel);



}

