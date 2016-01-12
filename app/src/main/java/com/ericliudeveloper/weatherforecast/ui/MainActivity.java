package com.ericliudeveloper.weatherforecast.ui;

import android.os.Bundle;

import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.framework.Presenter;
import com.ericliudeveloper.weatherforecast.framework.UpdatableView;


public class MainActivity extends BaseActivity {

    private String tag = getClass().getName();

    DisplayWeatherInfoFragment mFragment;
    HomepagePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            mFragment = new DisplayWeatherInfoFragment();
            mPresenter = new HomepagePresenter();
            getFragmentManager().beginTransaction().add(mPresenter, "presenter").commit();
            getFragmentManager().beginTransaction().replace(R.id.container, mFragment, tag).commit();
        } else {
            mFragment = (DisplayWeatherInfoFragment) getFragmentManager().findFragmentByTag(tag);
            mPresenter = (HomepagePresenter) getFragmentManager().findFragmentByTag("presenter");
        }

        setupPresenter(mPresenter, mFragment);
    }

    private void setupPresenter(Presenter presenter, UpdatableView updatableView) {

        presenter.setUpdatableView(updatableView);
        updatableView.setPresenter(presenter);
    }


}
