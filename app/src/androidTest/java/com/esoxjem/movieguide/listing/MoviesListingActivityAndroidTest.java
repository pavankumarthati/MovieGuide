package com.esoxjem.movieguide.listing;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.RxImmediateSchedulerRule;
import com.esoxjem.movieguide.details.MovieDetailsActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.rx2.idler.Rx2Idler;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayList;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by pavankumar.thati on 3/3/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MoviesListingActivityAndroidTest  {

  @ClassRule
  public static final RxImmediateSchedulerRule scheduleRule = new RxImmediateSchedulerRule();

  @Rule
  public ActivityTestRule<MoviesListingActivity> moviesListingActivityRule =
      new ActivityTestRule<MoviesListingActivity>(MoviesListingActivity.class, true, false);

  @Rule
  public IntentsTestRule<MoviesListingActivity> intentsTestRule =
      new IntentsTestRule<>(MoviesListingActivity.class);

  MoviesListingActivity moviesListingActivity;

  /*
    @BeforeClass
    public static void setupClass() throws Exception {
      RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("Rxjava 2.0 Io Scheduler"));
      RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("Rxjava 2.0 computation Scheduler"));
      RxJavaPlugins.setInitNewThreadSchedulerHandler(Rx2Idler.create("Rxjava 2.0 new thread Scheduler"));
      RxJavaPlugins.setInitSingleSchedulerHandler(Rx2Idler.create("Rxjava 2.0 single scheduler Scheduler"));
      RxAndroidPlugins.setInitMainThreadSchedulerHandler(Rx2Idler.create("rxjava 2.0 mainscheduler"));
    }
   */

  @Before
  public void setup() throws Exception {
    Intent startIntent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), MoviesListingActivity.class);
    startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    moviesListingActivityRule.launchActivity(startIntent);
    moviesListingActivity = moviesListingActivityRule.getActivity();
  }


  @Test
  public void onCreateTest() throws Exception {
    onView(withId(R.id.toolbar)).check(ViewAssertions.matches(IsNull.notNullValue()));
    assertThat(moviesListingActivity.toolbar.getTitle(), Is.is(moviesListingActivity.getString(R.string.movie_guide)));
    onView(withId(R.id.movie_details_container)).check(doesNotExist());
    onView(withId(R.id.action_sort)).check(ViewAssertions.matches(IsNull.notNullValue()));
  }

  @Test
  public void onMovieClickedTest() throws Exception {
    Movie movie = new Movie();
    movie.setId("1");
    movie.setOverview("overview");
    movie.setTitle("title");

    moviesListingActivity.onMovieClicked(movie);
    intended(allOf(isInternal(), hasComponent("com.esoxjem.movieguide.details.MovieDetailsActivity")));
  }

  @Test
  public void fragmentNonNullCheck() throws Exception {
    MoviesListingFragment moviesListingFragment = (MoviesListingFragment) moviesListingActivity.
        getSupportFragmentManager().findFragmentById(R.id.fragment_listing);
    assertThat(moviesListingFragment, IsNull.notNullValue());
  }

  @Test
  public void displayMoviesTest() throws Exception {
    MoviesListingFragment moviesListingFragment = (MoviesListingFragment) moviesListingActivity.
        getSupportFragmentManager().findFragmentById(R.id.fragment_listing);

    RecyclerView view = moviesListingFragment.getView().findViewById(R.id.movies_listing);
    assertThat(view.getAdapter(), IsNull.notNullValue());
    assertThat(view.getAdapter().getItemCount(), IsNot.not(0));
  }

}