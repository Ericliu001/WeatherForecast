package com.ericliudeveloper.weatherforecast.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ericliudeveloper.weatherforecast.framework.Model;
import com.ericliudeveloper.weatherforecast.framework.Presenter;
import com.ericliudeveloper.weatherforecast.framework.UpdatableView;
import com.ericliudeveloper.weatherforecast.framework.UpdateEnum;
import com.ericliudeveloper.weatherforecast.framework.UserActionEnum;
import com.ericliudeveloper.weatherforecast.model.HomepageModel;

/**
 * Created by ericliu on 12/01/2016.
 */
public class HomepagePresenter extends Fragment implements Presenter {
    private Model mModel;
    private UpdatableView mUpdatableView;


    public HomepagePresenter() {
        mModel = new HomepageModel();
        mModel.setPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void setModel(Model model) {
        mModel = model;
    }

    @Override
    public void setUpdatableView(UpdatableView view) {
        mUpdatableView = view;
    }

    @Override
    public void loadInitialData() {


        mModel.startModelUpdate(HomepageModel.HomepageUpdateRequest.GET_USER, null);
        mModel.startModelUpdate(HomepageModel.HomepageUpdateRequest.GET_WEATHERINFO, null);

    }

    @Override
    public Context retrieveContext() {
        return getActivity();
    }

    @Override
    public void onUpdateComplete(Model model, UpdateEnum update) {
        mUpdatableView.displayData(model, update);
    }

    @Override
    public void onUserAction(UserActionEnum action, @Nullable Bundle args) {
        int actionId = action.getId();

        if (actionId == DisplayWeatherInfoFragment.HomepageUserAction.REFRESH.getId()) {
            mModel.startModelUpdate(HomepageModel.HomepageUpdateRequest.GET_WEATHERINFO, null);
        }
    }
}
