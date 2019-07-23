package com.codecamp.prashant.moviebx.Presenter;

import com.codecamp.prashant.moviebx.View.MainActivity;
import com.codecamp.prashant.moviebx.model.movie;

import java.util.List;

public class mainActivityImp implements mainActivityPresenter.presenter,mainActivityPresenter.GetNoticeIntractor.OnFinishedListener {

    private mainActivityPresenter.MainView mainView;
    private mainActivityPresenter.GetNoticeIntractor getNoticeIntractor;

    public mainActivityImp(mainActivityPresenter.MainView mainView, mainActivityPresenter.GetNoticeIntractor getNoticeIntractor) {
        this.mainView = mainView;
        this.getNoticeIntractor = getNoticeIntractor;
    }

    @Override
    public void requestDataFromServer(int value) {
        mainView.showProgress();
        getNoticeIntractor.getNoticeArrayList(value,this);

    }

    @Override
    public void onFinished(List<movie> noticeArrayList) {
        if(mainView != null){
            mainView.setDataToRecyclerView(noticeArrayList);
            mainView.hideProgress();
        }

    }

    @Override
    public void onFailure(Throwable t) {

        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }
}
