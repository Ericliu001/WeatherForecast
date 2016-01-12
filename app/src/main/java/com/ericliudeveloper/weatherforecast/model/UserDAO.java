package com.ericliudeveloper.weatherforecast.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.entity.User;

/**
 * Created by ericliu on 11/07/15.
 */
public class UserDAO implements DAO, DBConstants.UserColumns {


    public static ContentValues getContentValuesFromObject(User user) {
        if (user == null) {
            return  null;
        }

        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_SEX, user.getSex());
        values.put(COL_AGE, user.getAge());
        return values;
    }


    public static User getObjectFromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        User user = new User();
        user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
        user.setSex(cursor.getString(cursor.getColumnIndexOrThrow(COL_SEX)));
        user.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE)));
        return user;
    }
}
