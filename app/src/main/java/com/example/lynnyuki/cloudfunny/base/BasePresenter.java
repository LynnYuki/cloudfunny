package com.example.lynnyuki.cloudfunny.base;

public interface BasePresenter <T extends BaseView>{
    void attachView(T view);

    void detachView();
}
