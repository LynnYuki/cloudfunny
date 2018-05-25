package com.example.lynnyuki.cloudfunny.base;

import android.content.Context;

import com.example.lynnyuki.cloudfunny.R;
import com.example.lynnyuki.cloudfunny.util.AppNetWorkUtil;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Subscriber基类
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private Context context;

    private BaseView view;

    protected BaseSubscriber(Context context, BaseView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!AppNetWorkUtil.isNetworkConnected(context)) {
            view.showError(context.getString(R.string.no_network));
            onComplete();
        }
    }

    @Override
    public void onError(Throwable t) {
//        view.showError(t.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
