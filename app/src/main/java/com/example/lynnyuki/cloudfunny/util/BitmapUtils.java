package com.example.lynnyuki.cloudfunny.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;

/**
 * Bitmap图片处理工具类
 */
public class BitmapUtils {
    public  static Bitmap  readBitMap(Context context,int resId){
        BitmapFactory.Options opt = new  BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }
}