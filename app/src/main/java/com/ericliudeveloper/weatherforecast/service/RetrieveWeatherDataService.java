package com.ericliudeveloper.weatherforecast.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.RemoteException;
import android.util.JsonReader;
import android.util.Log;

import com.ericliudeveloper.weatherforecast.MyApplication;
import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.entity.Status;
import com.ericliudeveloper.weatherforecast.entity.StatusDAO;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.entity.WeatherinfoDAO;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;
import com.ericliudeveloper.weatherforecast.util.LastLocationFinder;
import com.ericliudeveloper.weatherforecast.util.NetworkConstants;
import com.ericliudeveloper.weatherforecast.util.Parser;
import com.ericliudeveloper.weatherforecast.util.RestfulUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class RetrieveWeatherDataService extends IntentService implements NetworkConstants, DBConstants.WeatherInfoColumns {


    private static final String TAG = "REST";

    public RetrieveWeatherDataService() {
        super("RetrieveWeatherDataService");
    }

    private String mTransactionId;

    @Override
    protected void onHandleIntent(Intent intent) {

        mTransactionId = intent.getStringExtra(COL_TRANSACTION_ID);

        Location location = getMyLocation();
        if (location != null) {
            String gpsCoordinates = location.getLatitude() + "," + location.getLongitude();

            retrieveWeatherData(gpsCoordinates);
        }

    }

    private Location getMyLocation() {
        return LastLocationFinder.getLastBestLocation(1000, 5000);
    }

    /**
     * Handle action retrieve weather data in the provided background thread with the provided
     * parameters.
     */
    private void retrieveWeatherData(String gpsCoordinates) {


        String urlString = "https://api.forecast.io/forecast/69b4c892f57f9e7cefc65fc4d1fcb941/" + gpsCoordinates;

        Uri uri = Uri.parse(urlString);
        try {
            RestfulUtil.sendRequest(uri, null, new RestfulUtil.ResponseHandler() {
                @Override
                public void handleResponse(BufferedReader in) throws IOException {
                    JsonReader reader = new JsonReader(in);
                    WeatherInfo info = Parser.readWeatherInfo(reader);
                    if (info != null) {

                        try {
                            mergeWeatherInfoToLocalDB(info);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (OperationApplicationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeWeatherInfoToLocalDB(WeatherInfo info) throws RemoteException, OperationApplicationException {
        ContentResolver resolver = MyApplication.getApplication().getContentResolver();
        Uri weatherinfoUri = ProviderContract.WeatherInfos.CONTENT_URI;
        String[] projection = ProviderContract.WeatherInfos.PROJECTION;


        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        Cursor cursor = resolver.query(weatherinfoUri, projection, null, null, null);
        if (cursor != null) {
            // delete all existing entries with the time_stamp current
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ROWID));
                Uri deleteUri = ProviderContract.WeatherInfos.buildWeatherInfoUri(Long.toString(id));
                Log.i(TAG, "Scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
            }

        }

        ContentValues insertValues = WeatherinfoDAO.getContentValuesFromObject(info);
        insertValues.put(COL_TRANSACTION_ID, mTransactionId);
        Uri uri = resolver.insert(weatherinfoUri, insertValues);

        ContentProviderOperation.Builder insertBuilder = ContentProviderOperation.newInsert(weatherinfoUri);
        insertBuilder.withValues(insertValues);

        batch.add(insertBuilder.build());



        resolver.applyBatch(ProviderContract.CONTENT_AUTHORITY, batch);

        updateStatusRow();


        Log.i(TAG, "updated : " + uri);
    }

    private void updateStatusRow() {
       StatusDAO.clearDisplayingFlag();

        Status status = new Status();
        status.setTransactionId(mTransactionId);
        status.setFlag(DBConstants.Flag.DISPLAYING);
        status.setSyncStatus(DBConstants.SyncStatus.SYNCED);
        status.setRequestResult("200");

        StatusDAO.save(status);
    }


}
