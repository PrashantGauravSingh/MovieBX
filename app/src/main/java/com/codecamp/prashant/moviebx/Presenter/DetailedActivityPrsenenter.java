package com.codecamp.prashant.moviebx.Presenter;

import com.codecamp.prashant.moviebx.model.movie;

import org.json.JSONObject;

import java.util.List;

public interface DetailedActivityPrsenenter {

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/

    interface SecondView{

        void getMovieData();

        void loadDataOnView(JSONObject object);
    }

}
