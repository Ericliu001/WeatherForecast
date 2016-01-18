package com.ericliudeveloper.weatherforecast.model;

import android.app.Fragment;
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
public class HomepageModel extends Fragment implements Model {
    private Presenter mPresenter;
    private User mUser;
    private WeatherInfo mWeatherInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void startModelUpdate(UpdateEnum update, @Nullable Bundle args) {
        int id = update.getId();
        if (id == HomepageUpdateRequest.GET_USER.getId()) {

            MainDisplayProcessor.refreshUser(getActivity(), "");

        } else if (id == HomepageUpdateRequest.REFRESH_ALL.getId()) {
            // unique transaction id
            String transactionId = UUID.randomUUID().toString();

            MainDisplayProcessor.refreshUser(getActivity(), transactionId);
            MainDisplayProcessor.refreshWeatherInfo(getActivity(), transactionId);
        }

    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }


    public enum HomepageUpdateRequest implements UpdateEnum {
        GET_USER,
        REFRESH_ALL;


        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}
