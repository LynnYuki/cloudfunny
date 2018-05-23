package com.example.lynnyuki.cloudfunny.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * SharePreferences管理类
 */

public class SharePrefManager {

    private static final String SHAREDPREFERENCES_NAME = "my_sp";

    private SharedPreferences SPfres;

    @Inject
    public SharePrefManager(Context context) {
        SPfres = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    /**
     * 设置夜间模式
     *
     * @param event
     */
    public void setNightMode(boolean event) {
        SPfres.edit().putBoolean("nightmode", event).apply();
    }

    public boolean getNightMode() {
        return SPfres.getBoolean("nightmode", false);
    }

    public void setLocalMode(int localMode){
        SPfres.edit().putInt("localMode", localMode).apply();
    }

    public int getLocalMode(){
        return SPfres.getInt("localMode", 0);
    }
}
