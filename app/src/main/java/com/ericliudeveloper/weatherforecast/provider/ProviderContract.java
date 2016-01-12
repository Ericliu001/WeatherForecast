package com.ericliudeveloper.weatherforecast.provider;

import android.net.Uri;

import com.ericliudeveloper.weatherforecast.database.DBConstants;

/**
 * Created by liu on 17/06/15.
 */
public class ProviderContract {


    public static final String CONTENT_AUTHORITY = "com.ericliudeveloper.weatherforecast.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_WEATHERINFOS = "weatherinfos";
    public static final String PATH_USERS = "users";
    public static final String PATH_MAINDISPLAYS = "maindisplays";
    public static final String PATH_STATUS = "status";


    public static class WeatherInfos implements DBConstants.WeatherInfoColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHERINFOS).build();

        public static Uri buildWeatherInfoUri(String weatherInfoId) {
            return CONTENT_URI.buildUpon().appendPath(weatherInfoId).build();
        }

        public static String[] PROJECTION = {
                COL_ROWID
                , COL_LONGITUDE
                , COL_LATITUDE
                , COL_TIMEZONE
                , COL_TIME
                , COL_SUMMARY
                , COL_TEMPERATURE
                , COL_HUMIDITY
        };
    }


    public static class Users implements DBConstants.UserColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build();

        public static Uri buildUserUri(String userId) {
            return CONTENT_URI.buildUpon().appendPath(userId).build();
        }

        public static String[] PROJECTION = {
                COL_ROWID
                , COL_NAME
                , COL_SEX
                , COL_AGE
        };
    }


    public static class MainDisplays implements DBConstants.TableAndView, DBConstants.StatusColumns, DBConstants.WeatherInfoColumns, DBConstants.UserColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MAINDISPLAYS).build();

        public static String[] PROJECTION = {
                COL_ROWID
                , COL_SYNC_STATUS
                , COL_NAME
                , COL_SEX
                , COL_AGE
                , COL_LONGITUDE
                , COL_LATITUDE
                , COL_TIMEZONE
                , COL_TIME
                , COL_SUMMARY
                , COL_TEMPERATURE
                , COL_HUMIDITY
        };
    }


    public static class Status implements DBConstants.TableAndView, DBConstants.StatusColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATUS).build();

        public static final String[] PROJECTION = {
                COL_ROWID
                , COL_TRANSACTION_ID
                , COL_SYNC_STATUS
                , COL_REQUEST_RESULT
        };

    }
}
