package com.example.lynnyuki.cloudfunny.config;

import java.io.File;

/**
 * 常量
 */
public class Constants {
    public static final String PATH_DATA = CloudFunnyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_ZhiHu = 1;
    public static final int TYPE_KaiYan = 2;
    //Unsplash API
    public static final String CLIENT_ID = "b85ff30608ec972c8b81da4ea49580d0b9dc02653af9882bc7468ce3a4a04957";
    // 开眼视频
//    public static final String EYEPETIZER_UDID = "79a95dc6b649489383e976b5b97d129f6d592fad";
    public static final String EYEPETIZER_UDID = "26868b32e808498db32fd51fb422d00175e179df";

}
