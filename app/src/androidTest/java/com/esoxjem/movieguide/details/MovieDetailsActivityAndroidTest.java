package com.esoxjem.movieguide.details;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.esoxjem.movieguide.constants.Constants.MOVIE;
import static com.esoxjem.movieguide.listing.MoviesListingActivity.DETAILS_FRAGMENT;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.listing.Movie;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by pavankumar.thati on 3/8/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MovieDetailsActivityAndroidTest {

  @Rule
  public ActivityTestRule<MovieDetailsActivity> activityRule =
      new ActivityTestRule<MovieDetailsActivity>(MovieDetailsActivity.class, false, false);

  @Rule
  public IntentsTestRule<MovieDetailsActivity> intentRule =
      new IntentsTestRule<MovieDetailsActivity>(MovieDetailsActivity.class);

  MovieDetailsActivity mActivity;
  MovieDetailsFragment mFragment;
  Movie movie;

  @Before
  public void setUp() throws Exception {
    movie = new Movie();
    movie.setId("1");
    movie.setOverview("overview");
    movie.setTitle("title");

    Intent startIntent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), MovieDetailsActivity.class);
    Bundle bundle = new Bundle();
    bundle.putParcelable(MOVIE, movie);
    startIntent.putExtras(bundle);
    activityRule.launchActivity(startIntent);
    mActivity = activityRule.getActivity();
  }

  @Test
  public void onCreatePositiveTest() throws Exception {
    onView(withId(R.id.movie_details_container))
        .check(matches(not(doesNotExist())));
  }

  @Test
  public void onCreateNegativeTest() throws Exception {
    onView(withId(R.id.movie_details_container))
        .check(matches(IsNull.notNullValue()));
  }

  @Test
  public void onCreateFragmentTest() throws Exception {
    mFragment = (MovieDetailsFragment) mActivity.getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT);
    assertThat(mFragment.movieDetailsPresenter, IsNull.notNullValue());
  }

}