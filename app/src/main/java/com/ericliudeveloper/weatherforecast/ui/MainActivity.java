package com.ericliudeveloper.weatherforecast.ui;

import android.os.Bundle;

import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.framework.UpdatableView;


public class MainActivity extends BaseActivity {

    private String tag = getClass().getName();

    DisplayWeatherInfoFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            mFragment = new DisplayWeatherInfoFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, mFragment, tag).commit();
            setupPresenter(mFragment);
        } else {
            mFragment = (DisplayWeatherInfoFragment) getFragmentManager().findFragmentByTag(tag);
        }

    }

    private void setupPresenter(UpdatableView updatableView) {
        HomepagePresenter presenter = new HomepagePresenter();
        presenter.setUpdatableView(updatableView);
        updatableView.setPresenter(presenter);
    }


}
