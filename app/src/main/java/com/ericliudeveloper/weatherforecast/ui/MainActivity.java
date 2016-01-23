package com.ericliudeveloper.weatherforecast.ui;

import android.os.Bundle;

import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.mvp_framework.DisplayView;
import com.ericliudeveloper.weatherforecast.ui.homepage.DisplayWeatherInfoFragment;
import com.ericliudeveloper.weatherforecast.ui.homepage.HomepageModel;
import com.ericliudeveloper.weatherforecast.ui.homepage.HomepagePresenter;


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

    private void setupPresenter(HomepageModel model, DisplayView displayView) {
        mPresenter = new HomepagePresenter(0, displayView, model);
    }


}
