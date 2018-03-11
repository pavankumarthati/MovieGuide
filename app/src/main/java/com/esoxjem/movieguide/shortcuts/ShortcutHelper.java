package com.esoxjem.movieguide.shortcuts;

import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION.SDK_INT;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import com.esoxjem.movieguide.BuildConfig;
import com.esoxjem.movieguide.constants.Constants;
import com.esoxjem.movieguide.details.MovieDetailsActivity;
import com.esoxjem.movieguide.listing.Movie;
import io.reactivex.functions.BooleanSupplier;
import java.util.Arrays;
import javax.inject.Inject;

/**
 * Created by pavankumar.thati on 3/11/18.
 */

public class ShortcutHelper {
  private static final String SHORTCUTHELPER_TAG = ShortcutHelper.class.getSimpleName();

  Context context;
  ShortcutManager shortcutManager;

  @Inject
  public ShortcutHelper(Context context, ShortcutManager shortcutManager) {
    this.context = context;
    this.shortcutManager = shortcutManager;
  }

  @TargetApi(value = VERSION_CODES.O)
  public void reportShortcutUsed(@NonNull String id) {
    shortcutManager.reportShortcutUsed(id);
  }


  @TargetApi(value = VERSION_CODES.N_MR1)
  public BooleanSupplier addShortcut(Movie movie) throws IllegalArgumentException, IllegalStateException {
    return () -> {
      Builder shortcutBuilder = new Builder(context, movie.getId());
      Intent intent = new Intent(context, MovieDetailsActivity.class);
      intent.setAction(Intent.ACTION_VIEW);
      intent.putExtra(Constants.MOVIE, movie);
      shortcutBuilder.setIntent(intent);
      ShortcutInfo shortcutInfo = shortcutBuilder.build();
      return shortcutManager.addDynamicShortcuts(Arrays.asList(shortcutInfo));
    };
  }

  @TargetApi(value = VERSION_CODES.N_MR1)
  public void removeShortcut(String id) {
    shortcutManager.removeDynamicShortcuts(Arrays.asList(id));
  }


}
