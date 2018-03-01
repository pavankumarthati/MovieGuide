package com.esoxjem.movieguide.details;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.listing.Movie;
import com.google.common.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by pavankumar.thati on 2/27/18.
 */
public class MovieDetailsPresenterTest {

  @Mock
  private IMovieDetailsView view;

  @Mock
  private IFavoritesInteractor favoritesInteractor;

  @Mock
  private IMovieDetailsEndpoint movieDetailsEndpoint;

  private MovieDetailsPresenter movieDetailsPresenter;
  private TestScheduler testScheduler;



  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    testScheduler = new TestScheduler();
    movieDetailsPresenter = new MovieDetailsPresenter(movieDetailsEndpoint,
        favoritesInteractor, testScheduler, testScheduler, view);
  }

  @Test
  public void showTrailersTest() throws Exception {
    Movie movie = new Movie();
    movie.setId("1");
    movie.setOverview("overview");
    movie.setTitle("title");

    VideoViewModel videoViewModel = new VideoViewModel();
    videoViewModel.setId("1");
    videoViewModel.setVideos(new ArrayList<>());

    when(movieDetailsEndpoint.getMovieTrailers(movie.getId())).thenReturn(Observable.just(videoViewModel));

    movieDetailsPresenter.showTrailers(movie);

    testScheduler.triggerActions();
    verify(movieDetailsEndpoint).getMovieTrailers(movie.getId());

    assertThat(view, IsNot.not(equalTo(null)));

    verify(view).showTrailers(any(ArrayList.class));
  }

  @Test
  public void showReviewsTest() throws Exception {
    Movie movie = new Movie();
    movie.setId("1");
    movie.setOverview("overview");
    movie.setTitle("title");

    ReviewViewModel reviewViewModel = new ReviewViewModel();

    reviewViewModel.setMovieId(movie.getId());
    reviewViewModel.setPageNumber(1);
    reviewViewModel.setReviews(new ArrayList<>());

    when(movieDetailsEndpoint.getMovieReviews(movie.getId())).thenReturn(Observable.just(reviewViewModel));

    movieDetailsPresenter.showReviews(movie);

    testScheduler.triggerActions();

    verify(movieDetailsEndpoint).getMovieReviews(movie.getId());

    verify(view).showReviews(any(ArrayList.class));
  }

  @Test
  public void showFavouriteButtonTest() throws Exception {
    Movie movie = new Movie();
    movie.setId("1");
    movie.setOverview("overview");
    movie.setTitle("title");

    when(favoritesInteractor.isFavorite(movie.getId())).thenReturn(true);

    movieDetailsPresenter.showFavoriteButton(movie);

    verify(view).showFavorited();
  }

  @Test
  public void onFavouriteClickTest() throws Exception {
    Movie movie = new Movie();
    movie.setId("1");
    movie.setOverview("overview");
    movie.setTitle("title");

    when(favoritesInteractor.isFavorite(movie.getId())).thenReturn(true);

    movieDetailsPresenter.onFavoriteClick(movie);

    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    verify(favoritesInteractor).unFavorite(captor.capture());

    assertThat(captor.getValue(), Is.is(movie.getId()));

    verify(view).showUnFavorited();
  }


}