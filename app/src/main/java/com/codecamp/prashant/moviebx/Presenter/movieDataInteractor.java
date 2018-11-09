package com.codecamp.prashant.moviebx.Presenter;

import android.util.Log;

import com.codecamp.prashant.moviebx.BuildConfig;
import com.codecamp.prashant.moviebx.Services.Client;
import com.codecamp.prashant.moviebx.Services.Service;
import com.codecamp.prashant.moviebx.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class movieDataInteractor implements mainActivityPresenter.GetNoticeIntractor {
    @Override
    public void getNoticeArrayList( final OnFinishedListener onFinishedListener) {

        /** Create handle for the RetrofitInstance interface*/
        Service service = Client.getClient().create(Service.class);

        /** Call the method with parameter in the interface to get the notice data*/
        Call<MovieResponse>call=service.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                onFinishedListener.onFinished(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                onFinishedListener.onFailure(t);
                Log.e("Error"," "+t.getMessage());

            }
        });

    }
}
