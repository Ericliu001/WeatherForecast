package com.ericliudeveloper.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.entity.Currently;
import com.ericliudeveloper.weatherforecast.entity.Status;
import com.ericliudeveloper.weatherforecast.entity.StatusDAO;
import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.UserDAO;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.entity.WeatherinfoDAO;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;
import com.ericliudeveloper.weatherforecast.provider.WeatherProvider;

import java.util.UUID;

/**
 * Created by liu on 17/06/15.
 */
public class ProviderTests extends ProviderTestCase2<WeatherProvider> implements DBConstants.CommonColumns{
    /**
     * Constructor.
     */
    public ProviderTests() {
        super(WeatherProvider.class, ProviderContract.CONTENT_AUTHORITY);
    }


    private Context mContext;
    private MockContentResolver mResolver;
    // Contains an SQLite database, used as test data
    private SQLiteDatabase mDb;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getMockContext();
        mResolver = getMockContentResolver();
    }


    Uri weatherinfoUri = ProviderContract.WeatherInfos.CONTENT_URI;
    String[] weatherinfoProjection = ProviderContract.WeatherInfos.PROJECTION;

    public void testInsertAndQueryWeatherinfo() {

        Currently currently = new Currently("1 o clock", "We are happy", "23", "40%");
        WeatherInfo.Builder builder = new WeatherInfo.Builder();
        builder.latitude("23.436353");
        builder.longitude("34.346354");
        builder.timezone("+10");
        builder.currently(currently);

        WeatherInfo info = builder.build();

        ContentValues values = WeatherinfoDAO.getContentValuesFromObject(info);

        Uri uri = mResolver.insert(weatherinfoUri, values);
        assertNotNull("returned null uri when inserting  weatherinfo", uri);


        Cursor cursor = mResolver.query(weatherinfoUri, weatherinfoProjection, null, null, null);
        if (cursor.moveToFirst()) {
            WeatherInfo retrievedInfo = WeatherinfoDAO.getObjectFromCursor(cursor);

            assertEquals("saved and retrieved weather info not consistent.", retrievedInfo.getLatitude(), info.getLatitude());
        }


    }


    Uri usersUri = ProviderContract.Users.CONTENT_URI;
    String[] usersProjection = ProviderContract.Users.PROJECTION;

    public void testInsertAndQueryUser() {
        User user = new User();
        user.setName("Mack Xu");
        user.setSex("male");
        user.setAge(26);

        ContentValues values = UserDAO.getContentValuesFromObject(user);

        Uri uri = mResolver.insert(usersUri, values);
        assertNotNull("insert user returns null uri,", uri);

        Cursor cursor = mResolver.query(usersUri, usersProjection, null, null, null);
        cursor.moveToFirst();

        User retrievedUser = UserDAO.getObjectFromCursor(cursor);

        assertEquals(user.getName(), retrievedUser.getName());
    }


    public void testMainDisplay(){

        String transactionId = UUID.randomUUID().toString();


        Status status = new Status();
        status.setTransactionId(transactionId);
        status.setSyncStatus(DBConstants.SyncStatus.PUSHING);
        status.setRequestResult("200");
        status.setFlag(DBConstants.Flag.DISPLAYING);

        ContentValues statusValues = StatusDAO.getContentValuesFromObject(status);
        Uri statusUri = mResolver.insert(ProviderContract.Status.CONTENT_URI, statusValues);

        Currently currently = new Currently("1 o clock", "We are happy", "23", "40%");
        WeatherInfo.Builder builder = new WeatherInfo.Builder();
        builder.latitude("23.436353");
        builder.longitude("34.346354");
        builder.timezone("+10");
        builder.currently(currently);

        WeatherInfo info = builder.build();

        ContentValues weathervalues = WeatherinfoDAO.getContentValuesFromObject(info);

        weathervalues.put(COL_TRANSACTION_ID, transactionId);

        Uri weatherUri = mResolver.insert(weatherinfoUri, weathervalues);

        User user = new User();
        user.setName("Mack Xu");
        user.setSex("male");
        user.setAge(26);

        ContentValues uservalues = UserDAO.getContentValuesFromObject(user);
        uservalues.put(COL_TRANSACTION_ID, transactionId);
        Uri userUri = mResolver.insert(usersUri, uservalues);




        Cursor cursor = mResolver.query(ProviderContract.MainDisplays.CONTENT_URI, ProviderContract.MainDisplays.PROJECTION, COL_TRANSACTION_ID + "=?", new String[]{transactionId}, null);
        assertNotNull("query maindisplay returns null cursor", cursor);
        Log.d("eric", " maindisplay cursor count: " + cursor.getCount());

        String[] projection = ProviderContract.WeatherInfos.PROJECTION;
        cursor.moveToFirst();
        WeatherInfo retrieveInfo = WeatherinfoDAO.getObjectFromCursor(cursor);
        assertEquals("retrieved weather not the same as inserted weather.", info.getLatitude(), retrieveInfo.getLatitude());

        cursor.close();
    }

}
