package com.example.lynnyuki.cloudfunny.util;

import android.content.Context;

import com.example.lynnyuki.cloudfunny.model.bean.WeatherDetailBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiarh on 2017/11/1.
 */

public class WeatherUtil {

    private static Map<String, WeatherDetailBean> weatherBeanMap;

    public static void init(Context context) {
        weatherBeanMap = new HashMap<>();
        List<WeatherDetailBean> detailBeans = new Gson().fromJson(AppFileUtil.readAssetsValue(context, "weather.json")
                , new TypeToken<List<WeatherDetailBean>>() {
                }.getType());
        for (WeatherDetailBean bean : detailBeans) {
            weatherBeanMap.put(bean.getCode(), bean);
        }
    }

    public static String getImageUrl(String code) {
        return weatherBeanMap.get(code).getIcon();
    }
}
