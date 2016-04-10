package com.ericliudeveloper.weatherforecast.ui.homepage;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ericliudeveloper.weatherforecast.MyApplication;
import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.entity.StatusDAO;
import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.UserDAO;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.entity.WeatherinfoDAO;
import com.ericliudeveloper.weatherforecast.mvp_framework.Presenter;
import com.ericliudeveloper.weatherforecast.mvp_framework.RequestStatus;
import com.ericliudeveloper.weatherforecast.mvp_framework.ViewModel;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;
import com.ericliudeveloper.weatherforecast.task.MainDisplayProcessor;

import java.util.UUID;

/**
 * Created by ericliu on 12/01/2016.
 */
public class HomepageModel extends Fragment implements ViewModel, LoaderManager.LoaderCallbacks<Cursor> {


    private Presenter mPresenter;
    private User mUser = null;
    private RequestStatus userRequestStatus = RequestStatus.NOT_STARTED;

    private WeatherInfo mWeatherInfo = null;
    private RequestStatus weatherRequestStatus = RequestStatus.NOT_STARTED;

    public WeatherInfo getmWeatherInfo() {
        return mWeatherInfo;
    }


    public User getUser() {
        return mUser;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onInitialModelUpdate(int presenterId, @Nullable Bundle args) {
        onStartModelUpdate(0, HomepagePresenter.HomepageQueryRequest.GET_USER, args);

        onStartModelUpdate(0, HomepagePresenter.HomepageQueryRequest.REFRESH_WEATHER, args);

    }

    @Override
    public void onStartModelUpdate(int presenterId, QueryEnum update, @Nullable Bundle args) {
        int id = update.getId();
        if (id == HomepagePresenter.HomepageQueryRequest.GET_USER.getId()) {

            MainDisplayProcessor.refreshUser(getActivity(), "");
            userRequestStatus = RequestStatus.LOADING;

        } else if (id == HomepagePresenter.HomepageQueryRequest.REFRESH_WEATHER.getId()) {
            // unique transaction id
            String transactionId = UUID.randomUUID().toString();

            MainDisplayProcessor.refreshUser(getActivity(), transactionId);
            userRequestStatus = RequestStatus.LOADING;
            MainDisplayProcessor.refreshWeatherInfo(getActivity(), transactionId);
            weatherRequestStatus = RequestStatus.LOADING;
        }

    }

    @Override
    public RequestStatus getRequestStatus(QueryEnum update) {
        int id = update.getId();

        if (HomepagePresenter.HomepageQueryRequest.GET_USER.getId() == id) {
            return userRequestStatus;
        } else if (HomepagePresenter.HomepageQueryRequest.REFRESH_WEATHER.getId() == id) {
            return weatherRequestStatus;
        } else {
            throw new IllegalArgumentException("the request is not handled");
        }



    }

    @Override
    public void setPresenter(int presenterId, Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader<Cursor> loader = null;
        Uri uri = ProviderContract.MainDisplays.CONTENT_URI;
        String[] projection = ProviderContract.MainDisplays.PROJECTION;
        loader = new CursorLoader(MyApplication.getApplication(), uri, projection, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            DBConstants.SyncStatus syncStatus = StatusDAO.getSyncStatusFromCursor(data);
            if (syncStatus.equals(DBConstants.SyncStatus.PULLING) || syncStatus.equals(DBConstants.SyncStatus.PUSHING)) {
                // still in progress


            } else if (syncStatus.equals(DBConstants.SyncStatus.SYNCED)) {
                mWeatherInfo = WeatherinfoDAO.getObjectFromCursor(data);
                if (mWeatherInfo != null) {
                    mPresenter.onUpdateComplete(this, HomepagePresenter.HomepageQueryRequest.REFRESH_WEATHER);
                    weatherRequestStatus = RequestStatus.SUCESS;
                }

                mUser = UserDAO.getObjectFromCursor(data);
                if (mUser != null && !TextUtils.isEmpty(mUser.getName())) {
                    mPresenter.onUpdateComplete(this, HomepagePresenter.HomepageQueryRequest.GET_USER);
                    userRequestStatus = RequestStatus.SUCESS;
                }
            }

        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
