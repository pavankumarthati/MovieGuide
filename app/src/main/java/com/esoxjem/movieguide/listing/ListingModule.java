package com.esoxjem.movieguide.listing;

import android.content.Context;

import android.content.SharedPreferences;
import com.esoxjem.movieguide.ActivityScope;
import com.esoxjem.movieguide.IoScheduler;
import com.esoxjem.movieguide.MainScheduler;
import com.esoxjem.movieguide.SortingOptionPref;
import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.sorting.SortingOptionStore;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * @author pulkitkumar
 * @author Ashwini Kumar.
 */
@Module
public class ListingModule
{
    private IMoviesListingView moviesListingView;

    public ListingModule(IMoviesListingView moviesListingView)
    {
        this.moviesListingView = moviesListingView;
    }

    @Provides
    @ActivityScope
    IMoviesListingPresenter provideMovieListingPresenter(IMovieListingEndpoint movieListingEndpoint, SortingOptionStore sortingOptionStore,
        IFavoritesInteractor favoritesInteractor, @IoScheduler Scheduler ioScheduler, @MainScheduler Scheduler mainScheduler, IMoviesListingView view) {
        return new MoviesListingPresenter(movieListingEndpoint, sortingOptionStore, favoritesInteractor, ioScheduler, mainScheduler, view);
    }

    @Provides
    @ActivityScope
    IMovieListingEndpoint provideMovieListingEndpoint(Retrofit retrofit) {
        return retrofit.create(IMovieListingEndpoint.class);
    }

    @Provides
    @ActivityScope
    SortingOptionStore providesSortingOptionStore(@SortingOptionPref SharedPreferences sharedPreferences)
    {
        return new SortingOptionStore(sharedPreferences);
    }

    @Provides
    @ActivityScope
    IMoviesListingView provideMoviesListingView()
    {
        return moviesListingView;
    }

    @Provides
    @ActivityScope
    MovieListingViewHolderFactory provideViewHolderFactory()
    {
        return new MovieListingHolderFactory();
    }

    @Provides
    @ActivityScope
    MoviesListingAdapter provideListingAdapter(IMoviesListingView moviesListingView, MovieListingHolderFactory movieListingHolderFactory)
    {
        return new MoviesListingAdapter(moviesListingView, movieListingHolderFactory);
    }
}
