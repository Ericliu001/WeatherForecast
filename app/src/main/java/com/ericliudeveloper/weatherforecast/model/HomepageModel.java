package com.ericliudeveloper.weatherforecast.model;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.framework.Model;
import com.ericliudeveloper.weatherforecast.framework.Presenter;
import com.ericliudeveloper.weatherforecast.framework.UpdateEnum;
import com.ericliudeveloper.weatherforecast.task.MainDisplayProcessor;
import com.ericliudeveloper.weatherforecast.ui.MainActivity;

/**
 * Created by ericliu on 12/01/2016.
 */
public class HomepageModel implements Model {
    private Presenter mPresenter;
    private User mUser;
    private WeatherInfo mWeatherInfo;

    @Override
    public void startModelUpdate(UpdateEnum update, @Nullable Bundle args) {
        int id = update.getId();
        if (id == HomepageUpdateRequest.GET_USER.getId()) {

            MainDisplayProcessor.refreshMainDisplay(mPresenter.retrieveContext());

        } else if (id == HomepageUpdateRequest.GET_WEATHERINFO.getId()) {

        }

    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }


    public enum HomepageUpdateRequest implements UpdateEnum {
        GET_USER,
        GET_WEATHERINFO;


        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}
