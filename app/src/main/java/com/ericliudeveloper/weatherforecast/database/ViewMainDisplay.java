package com.ericliudeveloper.weatherforecast.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ericliu on 11/07/15.
 */
public class ViewMainDisplay implements DBConstants.StatusColumns, DBConstants.TableAndView, DBConstants.UserColumns, DBConstants.WeatherInfoColumns {


    private static final String VIEW_CREATE =
            " create view " + VIEW_MAIN_DISPLAY + " as select "
                    + TABLE_USER + "." + COL_ROWID + ","
                    + TABLE_USER + "." + COL_TRANSACTION_ID + ","
                    + TABLE_USER + "." + COL_NAME + ", "
                    + TABLE_USER + "." + COL_SEX + ", "
                    + TABLE_USER + "." + COL_AGE + ", "
                    + TABLE_WEATHERINFO + "." + COL_TRANSACTION_ID + ", "
                    + TABLE_WEATHERINFO + "." + COL_LONGITUDE + ", "
                    + TABLE_WEATHERINFO + "." + COL_LATITUDE + ", "
                    + TABLE_WEATHERINFO + "." + COL_TIMEZONE + ", "
                    + TABLE_WEATHERINFO + "." + COL_TIME + ", "
                    + TABLE_WEATHERINFO + "." + COL_SUMMARY + ","
                    + TABLE_WEATHERINFO + "." + COL_TEMPERATURE + ", "
                    + TABLE_WEATHERINFO + "." + COL_HUMIDITY + ", "

                    + TABLE_STATUS + "." + COL_TRANSACTION_ID + ", "
                    + TABLE_STATUS + "." + COL_SYNC_STATUS + ", "
                    + TABLE_STATUS + "." + COL_REQUEST_RESULT + " "

                    + " from "
                    + TABLE_STATUS
                    + " left join "
                    + TABLE_USER
                    + " on "
                    + TABLE_STATUS + "." + COL_TRANSACTION_ID + "=" + TABLE_USER + "." + COL_TRANSACTION_ID

                    + " left join "
                    + TABLE_WEATHERINFO
                    + " on "
                    + TABLE_STATUS + "." + COL_TRANSACTION_ID + "=" + TABLE_WEATHERINFO + "." + COL_TRANSACTION_ID

                    + " where "
                    + TABLE_STATUS + "." + COL_FLAG + "=" + "'" + DBConstants.Flag.DISPLAYING + "'"
                    + ";";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(VIEW_CREATE);
    }


    public static void onUpgrade(SQLiteDatabase db) {
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_MAIN_DISPLAY);
        onCreate(db);
    }
}
