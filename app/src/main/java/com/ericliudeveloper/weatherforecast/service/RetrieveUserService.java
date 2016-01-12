package com.ericliudeveloper.weatherforecast.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.UserDAO;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;

import java.util.ArrayList;

/**
 * Created by ericliu on 11/07/15.
 */
public class RetrieveUserService extends IntentService implements DBConstants.CommonColumns {


    public static final String SERVICE_NAME = "RetrieveUserService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RetrieveUserService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String transactionId = intent.getStringExtra(COL_TRANSACTION_ID);

        User user = new User();
        user.setName("Eric Liu");
        user.setSex("male");
        user.setAge(25);



        ContentValues values = UserDAO.getContentValuesFromObject(user);
        // set the transaction id
        values.put(DBConstants.CommonColumns.COL_TRANSACTION_ID, transactionId);

        // bulk operation
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();

        // delete all existing rows
        ContentProviderOperation deleteAllOperation = ContentProviderOperation.newDelete(ProviderContract.Users.CONTENT_URI).build();
        batch.add(deleteAllOperation);

//        Uri uri = ProviderContract.Users.CONTENT_URI;
//        MyApplication.getAppContentResolver().update(uri, values, null, null);

        // insert the new user row
        ContentProviderOperation insertNewUserOperation = ContentProviderOperation.newInsert(ProviderContract.Users.CONTENT_URI).withValues(values).build();
        batch.add(insertNewUserOperation);

        try {
            getApplicationContext().getContentResolver().applyBatch(ProviderContract.CONTENT_AUTHORITY, batch);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }



    }
}
