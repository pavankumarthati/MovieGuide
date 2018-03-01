package com.esoxjem.movieguide.listing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.sorting.SortType;
import com.esoxjem.movieguide.sorting.SortingOptionStore;
import io.reactivex.schedulers.TestScheduler;
import java.util.ArrayList;
import java.util.Observable;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by pavankumar.thati on 2/27/18.
 */
public class MoviesListingPresenterTest {

  @Mock
  IMoviesListingView view;

  @Mock
  IMovieListingEndpoint movieListingEndpoint;

  @Mock
  IFavoritesInteractor favoritesInteractor;

  @Mock
  SortingOptionStore sortingOptionStore;

  MoviesListingPresenter moviesListingPresenter;
  TestScheduler testScheduler;


  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    testScheduler = new TestScheduler();
    moviesListingPresenter = new MoviesListingPresenter(movieListingEndpoint, sortingOptionStore, favoritesInteractor, testScheduler, testScheduler, view);
  }


  @Test
  public void testPopularMovies() throws Exception {
    when(sortingOptionStore.getSelectedOption()).thenReturn(SortType.MOST_POPULAR.getValue());

    Movie movie = new Movie();
    movie.setTitle("title");
    movie.setId("1");

    MovieViewModel movieViewModel = new MovieViewModel();
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(movie);
    movieViewModel.setMovies(movies);
    movieViewModel.setPageNumber(1);
    when(movieListingEndpoint.fetchPopularMovies()).thenReturn(io.reactivex.Observable.just(movieViewModel));

    moviesListingPresenter.displayMovies();
    testScheduler.triggerActions();

    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).loadingStarted();
    inOrder.verify(view).showMovies(movieViewModel.getMovies());
  }

  @Test
  public void testHightestRatedMovies() throws Exception {
    when(sortingOptionStore.getSelectedOption()).thenReturn(SortType.HIGHEST_RATED.getValue());

    Movie movie = new Movie();
    movie.setTitle("title");
    movie.setId("1");

    MovieViewModel movieViewModel = new MovieViewModel();
    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(movie);
    movieViewModel.setMovies(movies);
    movieViewModel.setPageNumber(1);

    when(movieListingEndpoint.fetchHighestRatedMovies()).thenReturn(io.reactivex.Observable.just(movieViewModel));

    moviesListingPresenter.displayMovies();

    testScheduler.triggerActions();

    assertThat(view, IsNull.notNullValue());

    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).loadingStarted();
    inOrder.verify(view).showMovies(movieViewModel.getMovies());
  }

  @Test
  public void testFavouritesMovies() throws Exception {
    when(sortingOptionStore.getSelectedOption()).thenReturn(SortType.FAVORITES.getValue());

    Movie movie = new Movie();
    movie.setTitle("title");
    movie.setId("1");

    ArrayList<Movie> movies = new ArrayList<>();
    movies.add(movie);

    when(favoritesInteractor.getFavorites()).thenReturn(movies);

    moviesListingPresenter.displayMovies();

    verify(view).showMovies(movies);

  }


}