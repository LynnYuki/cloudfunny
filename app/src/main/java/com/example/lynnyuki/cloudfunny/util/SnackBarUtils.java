package com.example.lynnyuki.cloudfunny.util;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.lynnyuki.cloudfunny.R;


/**
 * 消息弹出工具类
 */

public class SnackBarUtils {

    public static void show(View view, CharSequence msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        ((TextView) (snackbar.getView().findViewById(R.id.snackbar_text))).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void show(View view, int resId, Context context) {
        show(view, context.getResources().getText(resId));
    }
}
