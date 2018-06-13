package com.example.lynnyuki.cloudfunny.config;

import java.io.File;

/**
 * 常量
 */
public class Constants {
    public static final String PATH_DATA = CloudFunnyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    //设置收藏类型
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_ZHI_HU = 1;
    public static final int TYPE_KAI_YAN = 2;
    // 和风天气Key
    public static final String WEATHER_KEY = "ba75e2ae21814e53a9b1061ba6f5ffe0";
    //Unsplash API
    public static final String CLIENT_ID = "200a84b63c9925c3b7b08fae399b4143e09558a32e762270a1a65bf057b64bff";
    // 开眼视频
//  public static final String EYEPETIZER_UDID = "79a95dc6b649489383e976b5b97d129f6d592fad";
    public static final String EYEPETIZER_UDID = "26868b32e808498db32fd51fb422d00175e179df";

}
