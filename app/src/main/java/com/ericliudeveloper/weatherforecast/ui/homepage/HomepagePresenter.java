package com.ericliudeveloper.weatherforecast.ui.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.mvp_framework.DisplayView;
import com.ericliudeveloper.weatherforecast.mvp_framework.Presenter;
import com.ericliudeveloper.weatherforecast.mvp_framework.StubPresenter;
import com.ericliudeveloper.weatherforecast.mvp_framework.ViewModel;


/**
 * Created by ericliu on 12/01/2016.
 */
public class HomepagePresenter implements Presenter {
    private ViewModel mModel;
    private DisplayView mDisplayView;

    private int mPresenterId = -1;


    public HomepagePresenter(int presenterId, DisplayView displayView, ViewModel viewModel) {
        this.mPresenterId = presenterId;
        mDisplayView = displayView;
        mModel = viewModel;

        mDisplayView.setPresenter(this);
        mModel.setPresenter(0, new StubPresenter());
    }


    @Override
    public void setViewModel(ViewModel model) {
        mModel = model;
    }


    public void setDisplayView(DisplayView view) {
        mDisplayView = view;
    }


    @Override
    public void loadInitialData(Bundle args) {
        mDisplayView.displayData(null, HomepageRefreshDisplay.START_USER_PROGRESS_BAR);
        mDisplayView.displayData(null, HomepageRefreshDisplay.START_WEATHERINFO_PROGRESS_BAR);
        mModel.onInitialModelUpdate(0, null);
    }


    @Override
    public void onUpdateComplete(ViewModel model, ViewModel.QueryEnum query, boolean isConfigurationChange) {

        if (query.getId() == HomepageQueryRequest.GET_USER.getId()) {


            User user = ((HomepageModel) model).getUser();
            mDisplayView.displayData(user, HomepageRefreshDisplay.DISPLAY_USER);

            if (!isConfigurationChange) {
                // do not stop the progressbar if this method is called due to the Activity re-creation
                mDisplayView.displayData(null, HomepageRefreshDisplay.SHOW_USER_FIELDS);
                mDisplayView.displayData(null, HomepageRefreshDisplay.STOP_USER_PROGRESS_BAR);
            }

            return;

        }

        if (query.getId() == HomepageQueryRequest.REFRESH_WEATHER.getId()) {


            WeatherInfo weatherInfo = ((HomepageModel) model).getmWeatherInfo();
            mDisplayView.displayData(weatherInfo, HomepageRefreshDisplay.DISPLAY_WEATHER);

            if (!isConfigurationChange) {

                mDisplayView.displayData(null, HomepageRefreshDisplay.SHOW_WEATHERINFO_FIELDS);
                mDisplayView.displayData(null, HomepageRefreshDisplay.STOP_WEATHERINFO_PROGRESS_BAR);
            }

            return;

        }


    }

    @Override
    public void onUserAction(UserActionEnum action, @Nullable Bundle args) {
        int actionId = action.getId();

        if (actionId == HomepageUserAction.REFRESH_BUTTON_CLICKED.getId()) {
            mDisplayView.displayData(null, HomepageRefreshDisplay.START_USER_PROGRESS_BAR);
            mDisplayView.displayData(null, HomepageRefreshDisplay.HIDE_USER_FIELDS);
            mModel.onStartModelUpdate(0, HomepageQueryRequest.GET_USER, null);


            mDisplayView.displayData(null, HomepageRefreshDisplay.START_WEATHERINFO_PROGRESS_BAR);
            mDisplayView.displayData(null, HomepageRefreshDisplay.HIDE_WEATHERINFO_FIELDS);
            mModel.onStartModelUpdate(0, HomepageQueryRequest.REFRESH_WEATHER, null);
        }
    }

    @Override
    public void onViewCreated() {
        mModel.setPresenter(mPresenterId, this);
    }

    @Override
    public void onViewDestroyed() {
        mModel.setPresenter(mPresenterId, new StubPresenter());
    }


    public enum HomepageQueryRequest implements ViewModel.QueryEnum {
        GET_USER,
        REFRESH_WEATHER,;


        @Override
        public int getId() {
            return this.ordinal();
        }
    }


    public enum HomepageUserAction implements UserActionEnum {
        REFRESH_BUTTON_CLICKED;


        @Override
        public int getId() {
            return this.ordinal();
        }
    }


    public enum HomepageRefreshDisplay implements RefreshDisplayEnum {
        START_USER_PROGRESS_BAR, STOP_USER_PROGRESS_BAR, DISPLAY_WEATHER, DISPLAY_USER, HIDE_USER_FIELDS, SHOW_USER_FIELDS, START_WEATHERINFO_PROGRESS_BAR, STOP_WEATHERINFO_PROGRESS_BAR, HIDE_WEATHERINFO_FIELDS, SHOW_WEATHERINFO_FIELDS;

        @Override
        public int getId() {
            return this.ordinal();
        }
    }


}


