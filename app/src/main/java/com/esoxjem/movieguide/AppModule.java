package com.esoxjem.movieguide;

import static com.esoxjem.movieguide.constants.Constants.FAVOURITE_PREF;
import static com.esoxjem.movieguide.constants.Constants.SORTING_OPTION_PREF;

import android.app.Application;
import android.content.Context;

import android.content.SharedPreferences;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author pulkitkumar
 * @author arunsasidharan
 */
@Module
public class AppModule
{
    private Application app;

    public AppModule(Application application)
    {
        app = application;
    }

    @Provides
    @Singleton
    public Context provideContext()
    {
        return app;
    }

    @Provides
    @Singleton
    @IoScheduler
    public Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @MainScheduler
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @FavoritePref
    public SharedPreferences provideSharePref(Context context) {
        return context.getApplicationContext().getSharedPreferences(FAVOURITE_PREF, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    @SortingOptionPref
    public SharedPreferences provideSortingOptionPref(Context context) {
        return context.getApplicationContext().getSharedPreferences(SORTING_OPTION_PREF, Context.MODE_PRIVATE);
    }

}
