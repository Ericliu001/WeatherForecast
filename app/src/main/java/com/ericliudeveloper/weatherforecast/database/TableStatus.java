package com.ericliudeveloper.weatherforecast.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ericliu on 12/07/15.
 */
public class TableStatus implements DBConstants.StatusColumns, DBConstants.TableAndView {

    private static final String TABLE_CREATE =
            " create table " + TABLE_STATUS + " ( "
                    + COL_ROWID + " integer primary key autoincrement, "
                    + COL_TRANSACTION_ID + " text not null, "
                    + COL_SYNC_STATUS + " text, "
                    + COL_REQUEST_RESULT + " text, "
                    + COL_FLAG + " text "
                    + ");";


    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_STATUS);
        onCreate(db);
    }
}
