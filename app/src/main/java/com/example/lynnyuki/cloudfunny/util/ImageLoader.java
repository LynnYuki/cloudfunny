package com.example.lynnyuki.cloudfunny.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.config.GlideApp;

/**
 * Glide图片加载工具封装
 */

public class ImageLoader {

    public static void loadDefault(Context context, ImageView imageView) {
        GlideApp.with(context)
                .load(R.drawable.ic_empty0)
                .fitCenter()
                .placeholder(R.drawable.ic_empty0)
                .error(R.drawable.ic_empty0)
                .priority(Priority.LOW)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    public static void loadAll(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .fitCenter()
                .placeholder(R.drawable.ic_empty0)
                .error(R.drawable.ic_empty0)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(imageView);
    }

    public static void loadAll(Context context, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .load(imgRes)
                .centerCrop()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadAllNoPlaceHolder(Context context, String imgUrl, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .centerCrop()
                .error(imgRes)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public static void loadAllNoPlaceHolder(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(imgUrl)
                .centerCrop()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public static void loadAllAsBitmap(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgUrl)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    public static void loadAllAsBitmap(Context context, String imgUrl, int imgRes, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(imgUrl)
                .centerCrop()
                .placeholder(imgRes)
                .error(imgRes)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .priority(Priority.LOW)
                .into(imageView);
    }
    public static void loadUnsplash(Context context,String imgUrl,ImageView imageView){
        GlideApp.with(context)
                .load(imgUrl)
                .priority(Priority.LOW)
                .fitCenter()
                .placeholder(R.drawable.ic_empty0)
                .error(R.drawable.ic_empty0)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }
}
