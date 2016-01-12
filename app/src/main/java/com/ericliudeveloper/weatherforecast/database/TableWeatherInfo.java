package com.ericliudeveloper.weatherforecast.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by liu on 17/06/15.
 */
public class TableWeatherInfo implements DBConstants.TableAndView, DBConstants.WeatherInfoColumns{

    //table creation sql string
    private static final String  TABLE_CREATE =
        " create table " + TABLE_WEATHERINFO + "  ( "
                + COL_ROWID + " integer primary key autoincrement, "
                + COL_TRANSACTION_ID + " text, "
                + COL_LONGITUDE + " text, "
                + COL_LATITUDE + " text, "
                + COL_TIMEZONE + " text, "
                + COL_TIME + " text, "
                + COL_SUMMARY + " text, "
                + COL_TEMPERATURE + " text, "
                + COL_HUMIDITY + " text "
                + " ); "
                ;






    public static void onCreate(SQLiteDatabase db){
        db.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_WEATHERINFO);
        onCreate(db);
    }

}
