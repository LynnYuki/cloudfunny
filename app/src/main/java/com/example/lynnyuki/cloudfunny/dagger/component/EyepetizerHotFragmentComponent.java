package com.example.lynnyuki.cloudfunny.dagger.component;

import com.example.lynnyuki.cloudfunny.dagger.module.EyepetizerHotFragmentModule;
import com.example.lynnyuki.cloudfunny.dagger.scope.FragmentScope;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.EyepetizerHotFragment;

import dagger.Component;

/**
 * 开眼热门视频组件
 */
@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = EyepetizerHotFragmentModule.class)
public interface EyepetizerHotFragmentComponent {

    void inject(EyepetizerHotFragment eyepetizerHotFragment);

}