package com.esoxjem.movieguide.favorites;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.esoxjem.movieguide.listing.Movie;
import dagger.internal.Beta;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by pavankumar.thati on 2/27/18.
 */
public class FavoritesInteractorTest {

  @Mock
  private FavoritesStore favoritesStore;

  private FavoritesInteractor favoritesInteractor;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    favoritesInteractor = new FavoritesInteractor(favoritesStore);
  }

  @Test
  public void getFavorites() throws Exception {

    ArrayList<Movie> movieArrayList = new ArrayList<>();
    Movie movie = new Movie();
    movie.setId("1");
    movie.setOverview("overview");
    movie.setTitle("title");

    movieArrayList.add(movie);
    assertThat(favoritesStore, IsNull.notNullValue());
    when(favoritesStore.getFavorites()).thenReturn(movieArrayList);

    List<Movie> list = favoritesInteractor.getFavorites();

    assertThat(list, Is.is(movieArrayList));

  }

  @Test
  public void testUnFavorite() throws Exception {
    String id = "id";
    favoritesInteractor.unFavorite(id);
    verify(favoritesStore).unfavorite(id);
  }

}