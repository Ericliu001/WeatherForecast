package com.ericliudeveloper.weatherforecast.mvp_framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by ericliu on 27/01/2016.
 */
public class StubPresenter implements Presenter {
    @Override
    public void setViewModel(ViewModel viewModel) {

    }

    @Override
    public void setDisplayView(DisplayView view) {

    }

    @Override
    public void loadInitialData(Bundle args) {

    }

    @Override
    public void onUpdateComplete(ViewModel viewModel, ViewModel.QueryEnum query, boolean isConfigurationChange) {

    }

    @Override
    public void onUserAction(UserActionEnum action, @Nullable Bundle args) {

    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onViewDestroyed() {

    }
}
