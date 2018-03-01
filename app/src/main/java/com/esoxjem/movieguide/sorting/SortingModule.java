package com.esoxjem.movieguide.sorting;

import android.content.Context;

import android.content.SharedPreferences;
import com.esoxjem.movieguide.ActivityScope;

import com.esoxjem.movieguide.SortingOptionPref;
import dagger.Module;
import dagger.Provides;

/**
 * @author pulkitkumar
 * @author Ashwini Kumar.
 */
@Module
public class SortingModule
{
    private SortingDialogFragment sortingDialogFragment;

    public SortingModule(SortingDialogFragment sortingDialogFragment)
    {
        this.sortingDialogFragment = sortingDialogFragment;
    }

    @Provides
    @ActivityScope
    SortingDialogFragment provideSortingDialogFragment()
    {
        return sortingDialogFragment;
    }

    @Provides
    @ActivityScope
    ISortingDialogInteractor providesSortingDialogInteractor(SortingOptionStore store)
    {
        return new SortingDialogInteractor(store);
    }

    @Provides
    @ActivityScope
    SortingOptionStore providesSortingOptionStore(@SortingOptionPref SharedPreferences sharedPreferences) {
        return new SortingOptionStore(sharedPreferences);
    }

    @Provides
    @ActivityScope
    ISortingDialogPresenter providePresenter(ISortingDialogInteractor interactor)
    {
        return new SortingDialogPresenter(interactor);
    }
}
