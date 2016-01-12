package com.ericliudeveloper.weatherforecast.model;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.framework.Model;
import com.ericliudeveloper.weatherforecast.framework.UpdateEnum;

/**
 * Created by ericliu on 12/01/2016.
 */
public class HomepageModel implements Model {
    private User mUser;
    private WeatherInfo mWeatherInfo;

    @Override
    public boolean startModelUpdate(UpdateEnum update, @Nullable Bundle args) {
        
        return false;
    }


    public enum HomepageUpdateRequest implements UpdateEnum{
        GET_USER,
        GET_WEATHERINFO;


        @Override
        public int getId(){
            return this.ordinal();
        }
    }
}
