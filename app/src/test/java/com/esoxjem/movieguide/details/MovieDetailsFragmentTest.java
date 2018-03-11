package com.esoxjem.movieguide.details;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.constants.Constants;
import com.esoxjem.movieguide.favorites.IFavoritesInteractor;
import com.esoxjem.movieguide.listing.Movie;
import io.reactivex.schedulers.TestScheduler;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

/**
 * Created by pavankumar.thati on 3/2/18.
 */
@RunWith(RobolectricTestRunner.class)
public class MovieDetailsFragmentTest {

  MovieDetailsFragment movieDetailsFragment;

  @Mock
  MovieDetailsPresenter movieDetailsPresenter;

  TestScheduler testScheduler;
  Movie movie;

  @Before
  public void setUp() throws Exception {
    movie = new Movie();
    movie.setTitle("title");
    movie.setId("1");

    Bundle args = new Bundle();
    args.putParcelable(Constants.MOVIE, movie);
    movieDetailsFragment = new MovieDetailsFragment();
    movieDetailsFragment.setArguments(args);
    testScheduler = new TestScheduler();
    MockitoAnnotations.initMocks(this);
    movieDetailsFragment.movieDetailsPresenter = movieDetailsPresenter;
  }

  @Test
  public void onCreateTest() throws Exception {
    SupportFragmentTestUtil.startFragment(movieDetailsFragment, MovieDetailsActivity.class);

    assertThat(movieDetailsFragment.movieDetailsPresenter, IsNull.notNullValue());
    assertThat(((ColorDrawable)movieDetailsFragment.collapsingToolbarLayout.getContentScrim()).getColor(),
        IsNull.notNullValue());
    assertThat(movieDetailsFragment.collapsingToolbarLayout.getTitle(),
        Is.is(RuntimeEnvironment.application.getString(R.string.movie_details)));

    assertThat(movieDetailsFragment.collapsingToolbarLayout.isTitleEnabled(), Is.is(true));
    InOrder inOrder = Mockito.inOrder(movieDetailsPresenter);
    ArgumentCaptor<Movie> argumentCaptor = ArgumentCaptor.forClass(Movie.class);
    inOrder.verify(movieDetailsPresenter).showDetails(argumentCaptor.capture());
    assertThat(argumentCaptor.getValue().getId(), Is.is("1"));
    inOrder.verify(movieDetailsPresenter).showFavoriteButton(argumentCaptor.capture());
    assertThat(argumentCaptor.getValue().getTitle(), Is.is("title"));

    assertThat(((AppCompatActivity)movieDetailsFragment.getActivity()).getSupportActionBar(), IsNull.notNullValue());
  }

  @Test
  public void showDetailsTest() throws Exception {
    SupportFragmentTestUtil.startFragment(movieDetailsFragment, MovieDetailsActivity.class);
    assertThat(movieDetailsFragment.movieDetailsPresenter, IsNull.notNullValue());

    movieDetailsFragment.showDetails(movie);
    assertThat(movieDetailsFragment.releaseDate.getText(),
        Is.is(String.format(RuntimeEnvironment.application.getString(R.string.release_date), movie.getReleaseDate())));

    InOrder inOrder = Mockito.inOrder(movieDetailsPresenter);
    inOrder.verify(movieDetailsPresenter).showTrailers(movie);
    inOrder.verify(movieDetailsPresenter).showReviews(movie);
  }

}