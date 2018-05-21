package com.example.lynnyuki.cloudfunny.model.http;



import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuBean;
import com.example.lynnyuki.cloudfunny.model.bean.ZhiHuContentBean;


import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * 知乎日报
 */

public interface ZhiHuApi {

    String HOST = "https://news-at.zhihu.com/";

    /**
     * 最新消息最新消息
     *
     * @return
     */
    @GET("api/4/news/latest")
    Flowable<ZhiHuBean> getZhiHu();

    @GET("api/4/news/{newsId}")
    Flowable<ZhiHuContentBean> getZhiHuContent(@Path("newsId")int id);

    @GET("/api/4/news/before/{date}")
    Flowable<ZhiHuBean> getZhiHuBefore(@Path("date") String date);
}
