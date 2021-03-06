package com.esoxjem.movieguide.details;

import static com.esoxjem.movieguide.details.MovieDetailsActivity.MOVIE_DETAILS_TAG;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.esoxjem.movieguide.BaseApplication;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.RxImmediateSchedulerRule;
import com.esoxjem.movieguide.constants.Constants;
import com.esoxjem.movieguide.listing.Movie;
import com.esoxjem.movieguide.listing.MoviesListingActivity;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowView;

/**
 * Created by pavankumar.thati on 3/1/18.
 */
@RunWith(RobolectricTestRunner.class)
public class MovieDetailsActivityTest {

  @ClassRule
  public static final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
  MovieDetailsActivity activity;
  ActivityController<MovieDetailsActivity> activityController;

  @Before
  public void setUp() throws Exception {
    Movie movie = new Movie();
    movie.setTitle("title");
    movie.setId("1");

    Intent intent = new Intent();
    intent.putExtra(Constants.MOVIE, movie);
    activityController = Robolectric.buildActivity(MovieDetailsActivity.class, intent);
    activity = activityController.get();
  }

  @Test
  public void onCreateTest() throws Exception {
    activityController.create().visible();
    assertThat(activity, IsNull.notNullValue());
    assertThat((BaseApplication) RuntimeEnvironment.application, Is.is(instanceOf(BaseApplication.class)));
    assertThat(activity.findViewById(R.id.movie_details_container), IsNull.notNullValue());
    assertThat(activity.getSupportFragmentManager().findFragmentByTag(MOVIE_DETAILS_TAG), IsNull.notNullValue());
  }

  @Test
  public void onOptionsItemSelectedTest() throws Exception {
    /*activityController.create().visible();
    ShadowActivity shadowActivity = Shadows.shadowOf(activity);
    View view =  activity.findViewById(android.R.id.home);
    ShadowView shadowView = Shadows.shadowOf(view);
    shadowView.checkedPerformClick();

    Intent intent = shadowActivity.getNextStartedActivity();
    ShadowIntent shadowIntent = Shadows.shadowOf(intent);
    assertThat(shadowIntent.getIntentClass(), Is.is(MoviesListingActivity.class.getSimpleName()));*/
  }

}