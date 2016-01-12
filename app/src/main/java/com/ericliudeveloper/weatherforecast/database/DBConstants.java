package com.ericliudeveloper.weatherforecast.database;

import android.provider.BaseColumns;

/**
 * Created by liu on 17/06/15.
 */
public final class DBConstants {
    private DBConstants(){}


    public enum SyncStatus {
        SYNCED, PUSHING, PULLING, STAGED, FAILED;
    }


    public enum Flag {
        DISPLAYING, NOT_DISPLAY;
    }


    public interface TableAndView{
        String TABLE_STATUS = "status";
        String TABLE_WEATHERINFO = "weatherinfo";
        String TABLE_USER = "user";
        String VIEW_MAIN_DISPLAY = "mainDisplay";
    }



    public interface CommonColumns extends BaseColumns{
        String COL_TRANSACTION_ID = "transaction_id";
        String COL_ROWID = _ID;
    }


    public interface StatusColumns extends CommonColumns {
        String COL_SYNC_STATUS = "sync_status";
        String COL_REQUEST_RESULT = "request_result";
        String COL_FLAG = "flag";
    }

    public interface WeatherInfoColumns extends  CommonColumns{
        String COL_LONGITUDE = "longitude";
        String COL_LATITUDE = "latitude";
        String COL_TIMEZONE = "timezone";
        String COL_TIME = "time";
        String COL_SUMMARY = "summary";
        String COL_TEMPERATURE = "temperature";
        String COL_HUMIDITY = "humidity";
    }


    public interface UserColumns extends CommonColumns{
        String COL_NAME = "name";
        String COL_SEX = "sex";
        String COL_AGE = "age";
    }
}
