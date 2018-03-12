package com.esoxjem.movieguide.listing;

import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.RecyclerView;

/**
 * Created by pavankumar.thati on 3/11/18.
 */

public class RecyclerViewIdlingResource implements IdlingResource {


  private final RecyclerView recyclerView;
  private ResourceCallback resourceCallback;
  private boolean isIdle;

  public RecyclerViewIdlingResource(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
  }

  @Override
  public String getName() {
    return "RecyclerView Idling Resource";
  }

  @Override
  public boolean isIdleNow() {
    /*if (isIdle) {
      return true;
    }

    mainLooperCheck();

    if (isIdle) {
      resourceCallback.onTransitionToIdle();
    }

    return isIdle;*/

    if (recyclerView != null && recyclerView.getAdapter().getItemCount() > 0) {
      if (resourceCallback != null) {
        resourceCallback.onTransitionToIdle();
      }
      return true;
    }
    return false;
  }

  private void mainLooperCheck() {
    if (recyclerView.getAdapter() != null &&
        recyclerView.getAdapter().getItemCount() != 0 &&
        recyclerView.getContext() != null &&
        recyclerView.getContext().getMainLooper().getQueue() != null &&
        recyclerView.getContext().getMainLooper().getQueue().isIdle()) {
      isIdle = true;
    } else {
      isIdle = false;

    }
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.resourceCallback = callback;
  }

}
