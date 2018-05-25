package com.example.lynnyuki.cloudfunny.dagger.module;

import com.example.lynnyuki.cloudfunny.dagger.qualifier.EyepetizerURL;
import com.example.lynnyuki.cloudfunny.dagger.scope.FragmentScope;
import com.example.lynnyuki.cloudfunny.model.http.EyepetizerApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 开眼视频模块
 */

@Module
public class EyepetizerFragmentModule {

    @EyepetizerURL
    @Provides
    @FragmentScope
    Retrofit provideEyepetizerRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return builder
                .baseUrl(EyepetizerApi.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @FragmentScope
    EyepetizerApi provideEyepetizerApi(@EyepetizerURL Retrofit retrofit) {
        return retrofit.create(EyepetizerApi.class);
    }
}
