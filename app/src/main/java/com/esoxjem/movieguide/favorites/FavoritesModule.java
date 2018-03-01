package com.esoxjem.movieguide.favorites;

import static com.esoxjem.movieguide.constants.Constants.FAVOURITE_PREF;

import android.content.Context;

import android.content.SharedPreferences;
import com.esoxjem.movieguide.FavoritePref;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author pulkitkumar
 * @author Ashwini Kumar.
 */
@Module
public class FavoritesModule
{
    @Provides
    @Singleton
    IFavoritesInteractor provideFavouritesInteractor(FavoritesStore store) {
        return new FavoritesInteractor(store);
    }

    @Provides
    @Singleton
    FavoritesStore provideFavoritesStore(@FavoritePref SharedPreferences sharedPreferences) {
        return new FavoritesStore(sharedPreferences);
    }
}
