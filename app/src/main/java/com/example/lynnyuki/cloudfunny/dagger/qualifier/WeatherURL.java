package com.example.lynnyuki.cloudfunny.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * 和风天气URL
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface WeatherURL {

}
