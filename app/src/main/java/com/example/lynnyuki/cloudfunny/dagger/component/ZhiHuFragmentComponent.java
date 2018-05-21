package com.example.lynnyuki.cloudfunny.dagger.component;

import com.example.lynnyuki.cloudfunny.dagger.module.ZhiHuFragmentModule;
import com.example.lynnyuki.cloudfunny.dagger.scope.FragmentScope;
import com.example.lynnyuki.cloudfunny.view.ZhiHu.ZhiHuFragment;
import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class,
            modules = ZhiHuFragmentModule.class)
public interface ZhiHuFragmentComponent {
        void inject (ZhiHuFragment zhiHuFragment);
}
