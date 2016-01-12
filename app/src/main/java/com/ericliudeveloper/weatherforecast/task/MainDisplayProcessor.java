package com.ericliudeveloper.weatherforecast.task;

import android.app.Activity;
import android.content.Intent;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.entity.Status;
import com.ericliudeveloper.weatherforecast.entity.StatusDAO;
import com.ericliudeveloper.weatherforecast.service.RetrieveUserService;

import java.util.UUID;

/**
 * Created by ericliu on 11/07/15.
 */
public final class MainDisplayProcessor implements DBConstants.CommonColumns {
    private MainDisplayProcessor() {
    }


    public static void refreshMainDisplay(Activity activity) {

        StatusDAO.clearDisplayingFlag();
        // unique transaction id
        String transactionId = UUID.randomUUID().toString();

        // create a Status row
        Status status = new Status();
        status.setTransactionId(transactionId);
        status.setSyncStatus(DBConstants.SyncStatus.PULLING);
        status.setFlag(DBConstants.Flag.DISPLAYING);

        StatusDAO.save(status);


        Intent getUserIntent = new Intent(activity, RetrieveUserService.class);
        getUserIntent.putExtra(COL_TRANSACTION_ID, transactionId);
        activity.startService(getUserIntent);
    }
}
