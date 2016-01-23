package com.ericliudeveloper.weatherforecast.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ericliu on 11/07/15.
 */
public class TableUser implements DBConstants.TableAndView, DBConstants.UserColumns {

    private static final String TABLE_CREATE =
            " create table " + TABLE_USER + " ( "
                    + COL_ROWID + " integer primary key autoincrement, "
                    + COL_TRANSACTION_ID + " text, "
                    + COL_NAME + " text not null, "
                    + COL_SEX + " text, "
                    + COL_AGE + " integer "
                    + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

}
