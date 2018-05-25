package com.example.lynnyuki.cloudfunny.dagger.component;

import com.example.lynnyuki.cloudfunny.dagger.module.MainActivityModule;
import com.example.lynnyuki.cloudfunny.dagger.scope.ActivityScope;
import com.example.lynnyuki.cloudfunny.view.MainActivity;

import dagger.Component;


/**
 * Main Activity 组件
 */
@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = MainActivityModule.class )
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

}
