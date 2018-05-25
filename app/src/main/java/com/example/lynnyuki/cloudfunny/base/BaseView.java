package com.example.lynnyuki.cloudfunny.base;

/**
 * View基接口
 */
public interface BaseView {

    void showMsg(CharSequence msg);

    void showError(CharSequence error);

    void showEmptyView();

    void showErrorView();

    void startLoading();

    void stopLoading();
}
