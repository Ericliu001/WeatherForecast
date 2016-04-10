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
    private ProgressBar pbUsername, pbUserSex, pbUserAge;


    private TextView tvLatitude, tvLongitude, tvTimezone, tvSummary, tvTemperature;
    private ProgressBar pbLatitude, pbLongitude, pbTimezone, pbSummary, pbTemperature;

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

        pbUsername = (ProgressBar) root.findViewById(R.id.pbUsername);
        pbUserSex = (ProgressBar) root.findViewById(R.id.pbUserSex);
        pbUserAge = (ProgressBar) root.findViewById(R.id.pbUserAge);

        tvLatitude = (TextView) root.findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) root.findViewById(R.id.tvLongitude);
        tvTimezone = (TextView) root.findViewById(R.id.tvTimezone);
        tvSummary = (TextView) root.findViewById(R.id.tvSummary);
        tvTemperature = (TextView) root.findViewById(R.id.tvTemperature);

        pbLatitude = (ProgressBar) root.findViewById(R.id.pbLatitude);
        pbLongitude = (ProgressBar) root.findViewById(R.id.pbLongitude);
        pbTimezone = (ProgressBar) root.findViewById(R.id.pbTimezone);
        pbSummary = (ProgressBar) root.findViewById(R.id.pbSummary);
        pbTemperature = (ProgressBar) root.findViewById(R.id.pbTemperature);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated();
        if (savedInstanceState == null) {
            mPresenter.loadInitialData(null, false);
        } else {
            mPresenter.loadInitialData(null, true);
        }
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

    private void hideUserFields() {
        tvUsername.setVisibility(View.INVISIBLE);
        tvSex.setVisibility(View.INVISIBLE);
        tvAge.setVisibility(View.INVISIBLE);

    }

    private void showUserFields() {
        tvUsername.setVisibility(View.VISIBLE);
        tvSex.setVisibility(View.VISIBLE);
        tvAge.setVisibility(View.VISIBLE);

    }

    private void refreshUserDisplay(User user) {
        String name = user.getName();
        tvUsername.setText(name);


        String sex = user.getSex();
        tvSex.setText(sex);

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
    public void displayData(Object element, Presenter.RefreshDisplayEnum refreshDisplay) {
        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.START_USER_PROGRESS_BAR.getId()) {

            changeProgressbarsVisibilityInUserFields(View.VISIBLE);

            return;
        }


        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.STOP_USER_PROGRESS_BAR.getId()) {

            // stop progress bar
            changeProgressbarsVisibilityInUserFields(View.INVISIBLE);

            return;
        }

        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.DISPLAY_USER.getId()) {

            // display the user
            User user = (User) element;
            refreshUserDisplay(user);

            return;
        }

        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.DISPLAY_WEATHER.getId()) {

            // display weatherInfo
            WeatherInfo weatherInfo = (WeatherInfo) element;
            refreshWeatherDisplay(weatherInfo);

            return;
        }

        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.HIDE_USER_FIELDS.getId()) {


            // clear user fields
            hideUserFields();
            return;
        }

        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.SHOW_USER_FIELDS.getId()) {


            // date update complete, show user fields again
            showUserFields();
            return;

        }


        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.HIDE_WEATHERINFO_FIELDS.getId()) {
            hideWeatherInfoFields();

            return;
        }


        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.SHOW_WEATHERINFO_FIELDS.getId()) {
            showWeatherInfoFields();

            return;
        }


        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.START_WEATHERINFO_PROGRESS_BAR.getId()) {

            changeVisibilityOfProgressbarsInWeatherInfoFields(View.VISIBLE);
            return;
        }

        if (refreshDisplay.getId() == HomepagePresenter.HomepageRefreshDisplay.STOP_WEATHERINFO_PROGRESS_BAR.getId()) {
            changeVisibilityOfProgressbarsInWeatherInfoFields(View.INVISIBLE);
            return;
        }


    }

    private void changeVisibilityOfProgressbarsInWeatherInfoFields(int visible) {
        pbLatitude.setVisibility(visible);
        pbLongitude.setVisibility(visible);
        pbTimezone.setVisibility(visible);
        pbSummary.setVisibility(visible);
        pbTemperature.setVisibility(visible);

    }

    private void showWeatherInfoFields() {
        tvLatitude.setVisibility(View.VISIBLE);
        tvLongitude.setVisibility(View.VISIBLE);
        tvTimezone.setVisibility(View.VISIBLE);
        tvSummary.setVisibility(View.VISIBLE);
        tvTemperature.setVisibility(View.VISIBLE);
    }

    private void hideWeatherInfoFields() {
        tvLatitude.setVisibility(View.INVISIBLE);
        tvLongitude.setVisibility(View.INVISIBLE);
        tvTimezone.setVisibility(View.INVISIBLE);
        tvSummary.setVisibility(View.INVISIBLE);
        tvTemperature.setVisibility(View.INVISIBLE);
    }

    private void changeProgressbarsVisibilityInUserFields(int visible) {
        // start progress bar
        pbUsername.setVisibility(visible);
        pbUserSex.setVisibility(visible);
        pbUserAge.setVisibility(visible);
    }


    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestroyed();
    }
}
