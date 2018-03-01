package com.esoxjem.movieguide.details;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * @author Ashwini Kumar.
 */

public class ReviewViewModel
{
    @Json(name = "id")
    private String movieId;
    @Json(name = "page")
    private int pageNumber;
    @Json(name = "results")
    private List<Review> reviews;

    public String getMovieId()
    {
        return movieId;
    }

    public List<Review> getReviews()
    {
        return reviews;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
