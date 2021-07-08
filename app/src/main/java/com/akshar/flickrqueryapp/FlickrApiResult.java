package com.akshar.flickrqueryapp;

import com.google.gson.annotations.SerializedName;

public class FlickrApiResult {
    @SerializedName("photos")
    private ImageResult photos;

    public ImageResult getPhotos() {
        return photos;
    }
}
