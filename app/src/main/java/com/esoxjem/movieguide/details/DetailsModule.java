package com.esoxjem.movieguide.details;

import com.esoxjem.movieguide.ActivityScope;
import com.esoxjem.movieguide.IoScheduler;
import com.esoxjem.movieguide.MainScheduler;
import com.esoxjem.movieguide.favorites.IFavoritesInteractor;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import javax.inject.Named;
import retrofit2.Retrofit;

/**
 * @author pulkitkumar
 * @author Ashwini Kumar.
 */
@Module
public class DetailsModule
{
    private IMovieDetailsView movieDetailsFragment;

    public DetailsModule(MovieDetailsFragment movieDetailsFragment)
    {
        this.movieDetailsFragment = movieDetailsFragment;
    }

    @Provides
    @ActivityScope
    IMovieDetailsView provideMovieDetailsFragment()
    {
        return movieDetailsFragment;
    }


    @Provides
    @ActivityScope
    IMovieDetailsPresenter providePresenter(IMovieDetailsEndpoint movieDetailsEndpoint,
                                            IFavoritesInteractor favoritesInteractor,
        @IoScheduler Scheduler ioScheduler, @MainScheduler Scheduler mainScheduler, IMovieDetailsView view)
    {
        return new MovieDetailsPresenter(movieDetailsEndpoint, favoritesInteractor, ioScheduler, mainScheduler, view);
    }

    @Provides
    @ActivityScope
    IMovieDetailsEndpoint provideMovieDetailsEndpoint(Retrofit retrofit)
    {
        return retrofit.create(IMovieDetailsEndpoint.class);
    }
}
