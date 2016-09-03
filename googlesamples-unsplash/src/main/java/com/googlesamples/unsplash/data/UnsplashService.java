package com.googlesamples.unsplash.data;

import com.googlesamples.unsplash.data.model.Photo;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;

public interface UnsplashService{

  String ENDPOINT = "https://unsplash.it";

  @GET("/list")
  void getFeed(Callback<List<Photo>> callback);


}