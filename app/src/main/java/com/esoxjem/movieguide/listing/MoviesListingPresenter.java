package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.IoScheduler;
import com.esoxjem.movieguide.MainScheduler;
import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.sorting.SortType;
import com.esoxjem.movieguide.sorting.SortingOptionStore;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * @author arun
 */
public class MoviesListingPresenter implements IMoviesListingPresenter
{
    private IMoviesListingView view;
    private CompositeDisposable compositeDisposable;
    private IMovieListingEndpoint movieListingEndpoint;
    private SortingOptionStore sortingOptionStore;
    private IFavoritesInteractor favoritesInteractor;
    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    public MoviesListingPresenter(IMovieListingEndpoint movieListingEndpoint, SortingOptionStore sortingOptionStore,
        IFavoritesInteractor favoritesInteractor, Scheduler ioScheduler, Scheduler mainScheduler,
        IMoviesListingView view)
    {
        this.movieListingEndpoint = movieListingEndpoint;
        this.sortingOptionStore = sortingOptionStore;
        this.favoritesInteractor = favoritesInteractor;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void destroy()
    {
        view = null;
        compositeDisposable.clear();
    }

    @Override
    public void displayMovies()
    {
        int selectedOption = sortingOptionStore.getSelectedOption();
        if (selectedOption == SortType.MOST_POPULAR.getValue())
        {
            loadPopularMovies();
        } else if (selectedOption == SortType.HIGHEST_RATED.getValue())
        {
            loadHighestRatedMovies();
        } else
        {
            loadFavouriteMovies();
        }
    }

    private void loadPopularMovies()
    {
        compositeDisposable.add(movieListingEndpoint.fetchPopularMovies()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe(disposable -> {
                    if (isViewAttached())
                    {
                        view.loadingStarted();
                    }
                })
                .subscribe(this::onMoviewsResult, this::onError));
    }

    private void onMoviewsResult(MovieViewModel movieViewModel)
    {
        if (isViewAttached())
        {
            view.showMovies(movieViewModel.getMovies());
        } else
        {
            // do nothing
        }
    }


    private void onError(Throwable throwable)
    {
        if (isViewAttached())
        {
            view.loadingFailed(throwable.getMessage());
        } else
        {
            // do nothing
        }
    }

    private void loadHighestRatedMovies()
    {
        compositeDisposable.add(movieListingEndpoint.fetchHighestRatedMovies()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe(disposable -> {
                    if (isViewAttached())
                    {
                        view.loadingStarted();
                    }
                })
                .subscribe(this::onMoviewsResult, this::onError));
    }

    private void loadFavouriteMovies()
    {
        view.showMovies(favoritesInteractor.getFavorites());
    }

    private boolean isViewAttached()
    {
        return view != null;
    }
}
