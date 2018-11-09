package com.codecamp.prashant.moviebx.Services;

import com.codecamp.prashant.moviebx.model.MovieResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("discover/movie")
    Call<MovieResponse> getPopularMovies(@Query("api_key")String apiKey);

    @GET("movie/{id}")
    Call<ResponseBody> getMoviesWithID(@Path("id") int id,@Query("api_key")String apiKey);

}
