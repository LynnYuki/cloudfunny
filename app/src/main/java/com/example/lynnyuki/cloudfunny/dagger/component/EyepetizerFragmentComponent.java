package com.example.lynnyuki.cloudfunny.dagger.component;

import com.example.lynnyuki.cloudfunny.dagger.module.EyepetizerFragmentModule;
import com.example.lynnyuki.cloudfunny.dagger.scope.FragmentScope;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.EyepetizerFragment;


import dagger.Component;

/**
 * 开眼视频组件
 */
@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = EyepetizerFragmentModule.class)
public interface EyepetizerFragmentComponent {

    void inject(EyepetizerFragment eyepetizerFragment);

}
