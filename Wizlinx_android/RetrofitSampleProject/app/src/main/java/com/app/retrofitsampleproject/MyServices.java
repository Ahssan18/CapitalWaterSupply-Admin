package com.app.retrofitsampleproject;

import com.app.retrofitsampleproject.Models.Responce;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyServices {

    @GET("users")
   public Call<Responce> getData();
}
