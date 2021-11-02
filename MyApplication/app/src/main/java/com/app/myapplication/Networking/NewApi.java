package com.app.myapplication.Networking;

import com.app.myapplication.Models.Responce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewApi {
    @GET("top-headlines")
    Call<Responce> getNews(
            @Query("sources") String sources,
            @Query("apiKey") String apikey);

}
