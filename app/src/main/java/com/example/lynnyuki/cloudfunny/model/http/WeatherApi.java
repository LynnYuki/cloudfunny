package com.example.lynnyuki.cloudfunny.model.http;

import com.example.lynnyuki.cloudfunny.model.bean.WeatherBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 和风天气预报
 *
 */

public interface WeatherApi {

    String HOST = "https://free-api.heweather.com/";

    /**
     * 获取最新实时天气
     *
     * @param location
     * @param key
     * @return
     */
    @GET("s6/weather/now")
    //没有数据就填 . 或者 /
    Flowable<WeatherBean> getWeather(@Query("location") String location, @Query("key") String key);
}
