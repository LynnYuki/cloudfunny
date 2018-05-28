package com.example.lynnyuki.cloudfunny.dagger.module;

import android.app.Activity;

import com.example.lynnyuki.cloudfunny.dagger.qualifier.WeatherURL;
import com.example.lynnyuki.cloudfunny.model.http.WeatherApi;
import com.tbruyelle.rxpermissions2.RxPermissions;

import com.example.lynnyuki.cloudfunny.dagger.scope.ActivityScope;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Main Activity 模块
 */

@Module
public class MainActivityModule {

    private Activity activity;

    public MainActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    RxPermissions provideRxPermissions(Activity activity) {
        return new RxPermissions(activity);
    }

    @WeatherURL
    @Provides
    @ActivityScope
    Retrofit provideWeatherRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(WeatherApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ActivityScope
    WeatherApi provideWeatherApi(@WeatherURL Retrofit retrofit) {
        return retrofit.create(WeatherApi.class);
    }
}
