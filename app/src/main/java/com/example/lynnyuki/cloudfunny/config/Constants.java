package com.example.lynnyuki.cloudfunny.config;

import java.io.File;

public class Constants {
    public static final String PATH_DATA = CloudFunnyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_ZhiHu = 1;
    public static final int TYPE_KaiYan = 2;

}
