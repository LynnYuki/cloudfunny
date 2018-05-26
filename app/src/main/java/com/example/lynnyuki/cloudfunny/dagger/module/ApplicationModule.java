package com.example.lynnyuki.cloudfunny.dagger.module;

import android.content.Context;

import com.example.lynnyuki.cloudfunny.config.CloudFunnyApplication;
import com.example.lynnyuki.cloudfunny.model.db.LikeBeanGreenDaoManager;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 全局APP模块
 */

@Module
public class ApplicationModule {

    private final CloudFunnyApplication application;

    public ApplicationModule(CloudFunnyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    SharePrefManager provideSharePrefManager(Context context) {
        return new SharePrefManager(context);
    }

    @Provides
    @Singleton
    LikeBeanGreenDaoManager provideGreenDaoManager(Context context){
        return new LikeBeanGreenDaoManager(context);
    }
}