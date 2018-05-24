package com.example.lynnyuki.cloudfunny.dagger.component;

import com.example.lynnyuki.cloudfunny.dagger.module.EyepetizerFragmentModule;
import com.example.lynnyuki.cloudfunny.dagger.scope.FragmentScope;
import com.example.lynnyuki.cloudfunny.view.Eyepetizer.EyepetizerFragment;


import dagger.Component;

/**
 * Created by xiarh on 2018/2/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,
        modules = EyepetizerFragmentModule.class)
public interface EyepetizerFragmentComponent {

    void inject(EyepetizerFragment eyepetizerFragment);

}
