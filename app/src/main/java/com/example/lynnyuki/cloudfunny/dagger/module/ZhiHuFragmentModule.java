package com.example.lynnyuki.cloudfunny.dagger.module;

import dagger.Module;


import com.example.lynnyuki.cloudfunny.dagger.scope.FragmentScope;
import com.example.lynnyuki.cloudfunny.dagger.qualifier.ZhiHuURL;
import com.example.lynnyuki.cloudfunny.model.http.ZhiHuApi;


import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ZhiHuFragmentModule {

    @ZhiHuURL
    @Provides
    @FragmentScope
    Retrofit provideZhiHuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(ZhiHuApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Provides
    @FragmentScope
    ZhiHuApi provideZhiHuApi(@ZhiHuURL Retrofit retrofit) {
        return retrofit.create(ZhiHuApi.class);
    }
}
