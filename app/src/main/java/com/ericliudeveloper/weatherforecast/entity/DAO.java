package com.ericliudeveloper.weatherforecast.entity;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.ericliudeveloper.weatherforecast.MyApplication;

/**
 * Created by liu on 17/06/15.
 */
public interface DAO {


    ContentResolver mContentResolver = MyApplication.getAppContentResolver();

}
