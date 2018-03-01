package com.esoxjem.movieguide.details;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * @author Ashwini Kumar.
 */

public class VideoViewModel
{
    @Json(name = "id")
    private String id;
    @Json(name = "results")
    private List<Video> videos;

    public List<Video> getVideos()
    {
        return videos;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
