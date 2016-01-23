package com.ericliudeveloper.weatherforecast.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;

/**
 * Created by ericliu on 12/07/15.
 */
public class StatusDAO implements DAO, DBConstants.StatusColumns{

    /**
     *
     * @param @Nullable cursor
     * @return
     */
    public static DBConstants.SyncStatus getSyncStatusFromCursor(Cursor cursor) {
        if (cursor == null) {
            return  null;
        }

        String statusString = cursor.getString(cursor.getColumnIndexOrThrow(COL_SYNC_STATUS));
        return DBConstants.SyncStatus.valueOf(statusString);
    }


    public static ContentValues getContentValuesFromObject(Status status) {
        if (status == null) {
            return  null;
        }

        ContentValues values = new ContentValues();
        values.put(COL_TRANSACTION_ID, status.getTransactionId());
        values.put(COL_SYNC_STATUS, status.getSyncStatus().toString());
        values.put(COL_REQUEST_RESULT, status.getRequestResult());
        values.put(COL_FLAG, status.getFlag().toString());
        return values;
    }


    public static void save(Status status) {
        if (status == null) {
            return;
        }

        String transactionId = status.getTransactionId();
        if (!TextUtils.isEmpty(transactionId)) {
            Cursor retrievedCursor = mContentResolver.query(ProviderContract.Status.CONTENT_URI, ProviderContract.Status.PROJECTION, COL_TRANSACTION_ID + "=?", new String[]{transactionId}, null );
            if (retrievedCursor.getCount() > 0) {
                update(status);
            } else {
                insert(status);
            }
        }
    }

    public static void insert(Status status) {
        ContentValues values = getContentValuesFromObject(status);
        mContentResolver.insert(ProviderContract.Status.CONTENT_URI, values);
    }

    public static void update(Status status) {
        String transactionId = status.getTransactionId();

        ContentValues values = getContentValuesFromObject(status);
        mContentResolver.update(ProviderContract.Status.CONTENT_URI, values, COL_TRANSACTION_ID + "=?", new String[]{transactionId});
    }

    public static void clearDisplayingFlag() {
        // remove any displaying flag in the records
        ContentValues values = new ContentValues();
        values.put(DBConstants.StatusColumns.COL_FLAG, DBConstants.Flag.NOT_DISPLAY.toString());
        mContentResolver.update(ProviderContract.Status.CONTENT_URI, values, null, null);
    }

    public static void setFlagToAll(DBConstants.Flag flag) {
        // set flag to all records in the DB
        ContentValues values = new ContentValues();
        values.put(DBConstants.StatusColumns.COL_FLAG, flag.toString());
        mContentResolver.update(ProviderContract.Status.CONTENT_URI, values, null, null);
    }
}
