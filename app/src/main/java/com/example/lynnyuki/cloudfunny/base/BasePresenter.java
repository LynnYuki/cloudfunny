package com.example.lynnyuki.cloudfunny.base;

/**
 * Presenter基接口
 * @param <T>
 */
public interface BasePresenter <T extends BaseView>{
    void attachView(T view);

    void detachView();
}
