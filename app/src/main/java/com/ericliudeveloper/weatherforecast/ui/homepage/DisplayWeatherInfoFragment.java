package com.ericliudeveloper.weatherforecast.ui.homepage;


import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.mvp_framework.Presenter;
import com.ericliudeveloper.weatherforecast.mvp_framework.DisplayView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayWeatherInfoFragment extends Fragment implements DisplayView {
    private Presenter mPresenter;

    private TextView tvUsername, tvSex, tvAge;
    private TextView tvLatitude, tvLongitude, tvTimezone, tvSummary, tvTemperature;
    private ProgressBar pbMain;

    private WeatherInfo mWeatherInfo = null;
    private User mUser = null;

    public DisplayWeatherInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_display_weather_info, container, false);
        setupViews(root);
        return root;
    }

    private void setupViews(View root) {
        tvUsername = (TextView) root.findViewById(R.id.tvUsername);
        tvSex = (TextView) root.findViewById(R.id.tvSex);
        tvAge = (TextView) root.findViewById(R.id.tvAge);


        tvLatitude = (TextView) root.findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) root.findViewById(R.id.tvLongitude);
        tvTimezone = (TextView) root.findViewById(R.id.tvTimezone);
        tvSummary = (TextView) root.findViewById(R.id.tvSummary);
        tvTemperature = (TextView) root.findViewById(R.id.tvTemperature);

        pbMain = (ProgressBar) root.findViewById(R.id.pbMain);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadInitialData(null);
    }

    private void refreshWeatherDisplay(WeatherInfo info) {
        String lat = info.getLatitude();
        if (!TextUtils.isEmpty(lat)) {
            tvLatitude.setText(lat);
        }

        String lon = info.getLongitude();
        if (!TextUtils.isEmpty(lon)) {
            tvLongitude.setText(lon);
        }

        String timezone = info.getTimezone();
        if (!TextUtils.isEmpty(timezone)) {
            tvTimezone.setText(timezone);
        }


        String summary = info.getSummary();
        if (!TextUtils.isEmpty(summary)) {
            tvSummary.setText(summary);
        }

        String temperature = info.getTemperature();
        if (!TextUtils.isEmpty(temperature)) {
            tvTemperature.setText(temperature);
        }
    }

    private void refreshUserDisplay(User user) {
        String name = user.getName();
        if (!TextUtils.isEmpty(name)) {
            tvUsername.setText(name);
        } else {
            tvUsername.setText("");
        }


        String sex = user.getSex();
        if (!TextUtils.isEmpty(sex)) {
            tvSex.setText(sex);
        } else {
            tvSex.setText("");
        }

        int age = user.getAge();
        if (age >= 0) {
            tvAge.setText(String.valueOf(age));
        } else {
            tvAge.setText("");
        }


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            mPresenter.onUserAction(HomepagePresenter.HomepageUserAction.REFRESH_BUTTON_CLICKED, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void displayData(DisplayUnit unit, Presenter.RefreshDisplayEnum refreshDisplay) {
        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.START_PROGRESS_BAR.getId()) {

            // start progress bar
            pbMain.setVisibility(View.VISIBLE);



        } else if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.STOP_PROGRESS_BAR.getId()) {

            // stop progress bar
            pbMain.setVisibility(View.INVISIBLE);



        } else if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.DISPLAY_USER.getId()) {

            // display the user
            User user = ((HomepagePresenter.UserDisplayUnit) unit).user;
            refreshUserDisplay(user);



        } else if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.DISPLAY_WEATHER.getId()) {

            // display weatherInfo
            WeatherInfo weatherInfo = ((HomepagePresenter.WeatherInfoDisplayUnit) unit).weatherInfo;
            refreshWeatherDisplay(weatherInfo);



        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }


}
