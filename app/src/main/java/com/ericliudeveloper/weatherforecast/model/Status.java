package com.ericliudeveloper.weatherforecast.model;

import com.ericliudeveloper.weatherforecast.database.DBConstants;

/**
 * Created by ericliu on 12/07/15.
 */
public class Status {
    private String transactionId;
    private DBConstants.SyncStatus syncStatus;
    private String requestResult;
    private DBConstants.Flag flag;

    public DBConstants.Flag getFlag() {
        return flag;
    }

    public void setFlag(DBConstants.Flag flag) {
        this.flag = flag;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public DBConstants.SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(DBConstants.SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(String requestResult) {
        this.requestResult = requestResult;
    }
}
