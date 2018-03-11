package com.esoxjem.movieguide.details;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esoxjem.movieguide.Api;
import com.esoxjem.movieguide.BaseApplication;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.constants.Constants;
import com.esoxjem.movieguide.listing.Movie;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.esoxjem.movieguide.R.id.trailers_label;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment implements IMovieDetailsView, View.OnClickListener
{
    @Inject
    IMovieDetailsPresenter movieDetailsPresenter;

    @BindView(R.id.movie_poster)
    ImageView poster;
    @BindView(R.id.movie_name)
    TextView title;
    @BindView(R.id.movie_year)
    TextView releaseDate;
    @BindView(R.id.movie_rating)
    TextView rating;
    @BindView(R.id.movie_description)
    TextView overview;
    @BindView(trailers_label)
    TextView label;
    @BindView(R.id.trailers_container)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.trailers)
    LinearLayout trailers;
    @BindView(R.id.reviews_label)
    TextView reviews;
    @BindView(R.id.reviews)
    LinearLayout reviewsContainer;
    @BindView(R.id.favorite)
    FloatingActionButton favorite;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    Movie movie;

    public MovieDetailsFragment()
    {
        // Required empty public constructor
    }

    public static MovieDetailsFragment getInstance(@NonNull Movie movie)
    {
        Bundle args = new Bundle();
        args.putParcelable(Constants.MOVIE, movie);
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(args);
        return movieDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        BaseApplication.get(getContext()).getAppComponent().plus(new DetailsModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, rootView);
        setToolbar();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null)
        {
            Movie movie = (Movie) getArguments().get(Constants.MOVIE);
            if (movie != null)
            {
                this.movie = movie;
                movieDetailsPresenter.showDetails((movie));
                movieDetailsPresenter.showFavoriteButton(movie);
            }
        }
    }

    private void setToolbar()
    {
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        if (toolbar != null)
        {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null)
            {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else
        {
            // Don't inflate. Tablet is in landscape mode.
        }
    }

    @Override
    public void showDetails(Movie movie)
    {
        Glide.with(getContext()).load(Api.BACKDROP_PATH + movie.getBackdropPath()).into(poster);
        title.setText(movie.getTitle());
        releaseDate.setText(String.format(getString(R.string.release_date), movie.getReleaseDate()));
        rating.setText(String.format(getString(R.string.rating), String.valueOf(movie.getVoteAverage())));
        overview.setText(movie.getOverview());
        movieDetailsPresenter.showTrailers(movie);
        movieDetailsPresenter.showReviews(movie);
    }

    @Override
    public void showTrailers(List<Video> trailers)
    {
        if (trailers.isEmpty())
        {
            label.setVisibility(View.GONE);
            this.trailers.setVisibility(View.GONE);
            horizontalScrollView.setVisibility(View.GONE);

        } else
        {
            label.setVisibility(View.VISIBLE);
            this.trailers.setVisibility(View.VISIBLE);
            horizontalScrollView.setVisibility(View.VISIBLE);

            this.trailers.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            for (Video trailer : trailers)
            {
                ViewGroup thumbContainer = (ViewGroup) inflater.inflate(R.layout.video, this.trailers, false);
                ImageView thumbView = (ImageView) thumbContainer.findViewById(R.id.video_thumb);
                thumbView.setTag(Video.getUrl(trailer));
                thumbView.requestLayout();
                thumbView.setOnClickListener(this);
                Glide.with(getContext())
                        .load(Video.getThumbnailUrl(trailer))
                        .override(R.dimen.video_width, R.dimen.video_height)
                        .centerCrop()
                        .placeholder(R.color.colorPrimary)
                        .into(thumbView);
                this.trailers.addView(thumbContainer);
            }
        }
    }

    @Override
    public void showReviews(List<Review> reviews)
    {
        if (reviews.isEmpty())
        {
            this.reviews.setVisibility(View.GONE);
            reviewsContainer.setVisibility(View.GONE);
        } else
        {
            this.reviews.setVisibility(View.VISIBLE);
            reviewsContainer.setVisibility(View.VISIBLE);

            reviewsContainer.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            for (Review review : reviews)
            {
                ViewGroup reviewContainer = (ViewGroup) inflater.inflate(R.layout.review, reviewsContainer,
                        false);
                TextView reviewAuthor = (TextView) reviewContainer.findViewById(R.id.review_author);
                TextView reviewContent = (TextView) reviewContainer.findViewById(R.id.review_content);
                reviewAuthor.setText(review.getAuthor());
                reviewContent.setText(review.getContent());
                reviewContent.setOnClickListener(this);
                reviewsContainer.addView(reviewContainer);
            }
        }
    }

    @Override
    public void showFavorited()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            favorite.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_favorite_white_24dp, getContext().getTheme()));
        } else
        {
            favorite.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
        }
    }

    @Override
    public void showUnFavorited()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            favorite.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp, getContext().getTheme()));
        } else
        {
            favorite.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
        }
    }

    @OnClick(R.id.favorite)
    public void onFavouriteClicked()
    {
        onFavoriteClick();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.video_thumb:
                onThumbnailClick(view);
                break;

            case R.id.review_content:
                onReviewClick((TextView) view);
                break;

            default:
                break;
        }
    }

    private void onReviewClick(TextView view)
    {
        if (view.getMaxLines() == 5)
        {
            view.setMaxLines(500);
        } else
        {
            view.setMaxLines(5);
        }
    }

    private void onThumbnailClick(View view)
    {
        String videoUrl = (String) view.getTag();
        Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(playVideoIntent);
    }

    private void onFavoriteClick()
    {
        movieDetailsPresenter.onFavoriteClick(movie);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        movieDetailsPresenter.destroy();
    }
}
