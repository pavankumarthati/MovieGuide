package com.esoxjem.movieguide;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.esoxjem.movieguide.favorites.FavoritesModule;
import com.esoxjem.movieguide.network.NetworkModule;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * @author arun
 */
public class BaseApplication extends Application
{
    private AppComponent appComponent;

    public static BaseApplication get(Context context)
    {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        StrictMode.enableDefaults();
        initAppComponent();
        initTimber();
    }

    private void initTimber() {
      if (BuildConfig.DEBUG) {
        Timber.plant(new DebugTree());
      }
    }

    public AppComponent initAppComponent()
    {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .favoritesModule(new FavoritesModule())
                .build();

        return appComponent;
    }

    public AppComponent getAppComponent()
    {
        return appComponent;
    }

}
