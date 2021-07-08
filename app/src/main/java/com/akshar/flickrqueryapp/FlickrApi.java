package com.akshar.flickrqueryapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
* Assuming that your base url to your API is https://your.api.com,
* the request using the FlickrApi interface from above and calling the getTasks method
* results in this request url: https://your.api.com/tasks?sort=value-of-order-parameter
*
* */

public interface FlickrApi {
    @GET(".")
    Call<FlickrApiResult> getImages(
            @Query("method") String apiMethod,
            @Query("api_key") String apiKey,
            @Query("text") String searchQuery,
            @Query("per_page") Integer perPage,
            @Query("page") Integer page,
            @Query("format") String format,
            @Query("nojsoncallback") Integer noJsonCallback
            );
}
