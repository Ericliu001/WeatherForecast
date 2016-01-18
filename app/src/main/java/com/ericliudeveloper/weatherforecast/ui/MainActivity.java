package com.ericliudeveloper.weatherforecast.ui;

import android.os.Bundle;

import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.framework.UpdatableView;
import com.ericliudeveloper.weatherforecast.model.HomepageModel;


public class MainActivity extends BaseActivity {

    private String viewTag = this.getClass().getSimpleName() + ".view";
    private String modelTag = this.getClass().getSimpleName() + ".model";

    DisplayWeatherInfoFragment mUpdatableView;
    HomepagePresenter mPresenter;
    HomepageModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModel = (HomepageModel) getFragmentManager().findFragmentByTag(modelTag);
        if (mModel == null) {
            mModel = new HomepageModel();
            getFragmentManager().beginTransaction().add(mModel, modelTag).commit();
        }


        if (savedInstanceState == null) {
            mUpdatableView = new DisplayWeatherInfoFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, mUpdatableView, viewTag).commit();

        } else {
            mUpdatableView = (DisplayWeatherInfoFragment) getFragmentManager().findFragmentByTag(viewTag);
        }

        setupPresenter(mModel, mUpdatableView);
    }

    private void setupPresenter(HomepageModel model, UpdatableView updatableView) {
         mPresenter = new HomepagePresenter();

        mPresenter.setUpdatableView(updatableView);
        mPresenter.setModel(model);

        updatableView.setPresenter(mPresenter);
        model.setPresenter(mPresenter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadInitialData();

    }
}
