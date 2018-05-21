package com.example.lynnyuki.cloudfunny.config;

import java.io.File;

public class Constants {
    public static final String PATH_DATA = CloudFunnyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    // 微信精选Key
    public static final String WEIXIN_KEY = "b5e884acc5263701bb57b68078912c2c";
    public static final int TYPE_ZhiHu = 1;
}
