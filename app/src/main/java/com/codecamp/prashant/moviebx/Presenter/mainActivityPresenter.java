package com.codecamp.prashant.moviebx.Presenter;

import com.codecamp.prashant.moviebx.model.movie;

import java.util.List;

public interface mainActivityPresenter {

    interface presenter{

        void requestDataFromServer(int value);

    }
    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    interface MainView {

        void showProgress();
        void hideProgress();

        void setDataToRecyclerView(List<movie> noticeArrayList);

        void onResponseFailure(Throwable throwable);

    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetNoticeIntractor {

        interface OnFinishedListener {
            void onFinished(List<movie> noticeArrayList);
            void onFailure(Throwable t);
        }

        void getNoticeArrayList(int value,OnFinishedListener onFinishedListener);
    }
}
