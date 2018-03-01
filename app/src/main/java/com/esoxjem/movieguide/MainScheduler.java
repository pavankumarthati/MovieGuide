package com.esoxjem.movieguide;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

/**
 * Created by pavankumar.thati on 2/27/18.
 */

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface MainScheduler {

}
