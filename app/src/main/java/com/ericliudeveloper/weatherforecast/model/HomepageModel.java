package com.ericliudeveloper.weatherforecast.model;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.framework.Model;
import com.ericliudeveloper.weatherforecast.framework.Presenter;
import com.ericliudeveloper.weatherforecast.framework.UpdateEnum;
import com.ericliudeveloper.weatherforecast.task.MainDisplayProcessor;

import java.util.UUID;

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

            // unique transaction id
            String transactionId = UUID.randomUUID().toString();

            MainDisplayProcessor.refreshUser(mPresenter.retrieveContext(), transactionId);
            MainDisplayProcessor.refreshWeatherInfo(mPresenter.retrieveContext(), transactionId);

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
