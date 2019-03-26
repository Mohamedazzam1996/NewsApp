package com.example.mazzam.newsapp.Api;

import com.example.mazzam.newsapp.Api.NewsResponse.NewsResponse;
import com.example.mazzam.newsapp.Api.NewsSourcesResponse.SourcesResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
public interface Services {
    @GET("sources")
    retrofit2.Call<SourcesResponse> getNewsSources(
            @Query("country")String country ,
            @Query("apiKey") String apiKey);
    @GET("everything")
    retrofit2.Call<NewsResponse> getNewsFromSources(
            @Query("sources") String sourceId,
            @Query("apiKey") String apiKey);

}
