package com.ericliudeveloper.weatherforecast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liu on 17/06/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    // set the database name to null to enable in-memory database, no disk file will be created.
    private static final String DATABASE_NAME = null;
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TableStatus.onCreate(db);
        TableWeatherInfo.onCreate(db);
        TableUser.onCreate(db);
        ViewMainDisplay.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TableStatus.onUpgrade(db);
        TableWeatherInfo.onUpgrade(db);
        TableUser.onUpgrade(db);
        ViewMainDisplay.onUpgrade(db);
    }
}
