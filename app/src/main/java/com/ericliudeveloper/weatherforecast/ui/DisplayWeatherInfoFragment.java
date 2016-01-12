package com.ericliudeveloper.weatherforecast.ui;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
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

import com.ericliudeveloper.weatherforecast.MyApplication;
import com.ericliudeveloper.weatherforecast.R;
import com.ericliudeveloper.weatherforecast.database.DBConstants;
import com.ericliudeveloper.weatherforecast.entity.StatusDAO;
import com.ericliudeveloper.weatherforecast.entity.User;
import com.ericliudeveloper.weatherforecast.entity.UserDAO;
import com.ericliudeveloper.weatherforecast.entity.WeatherInfo;
import com.ericliudeveloper.weatherforecast.entity.WeatherinfoDAO;
import com.ericliudeveloper.weatherforecast.framework.Presenter;
import com.ericliudeveloper.weatherforecast.framework.UpdatableView;
import com.ericliudeveloper.weatherforecast.framework.UpdateEnum;
import com.ericliudeveloper.weatherforecast.framework.UserActionEnum;
import com.ericliudeveloper.weatherforecast.model.HomepageModel;
import com.ericliudeveloper.weatherforecast.provider.ProviderContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayWeatherInfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, UpdatableView<HomepageModel> {
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

        getLoaderManager().initLoader(0, null, this);
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
                pbMain.setVisibility(View.VISIBLE);
            } else if (syncStatus.equals(DBConstants.SyncStatus.SYNCED)) {
                pbMain.setVisibility(View.GONE);
            }

            WeatherInfo info = WeatherinfoDAO.getObjectFromCursor(data);
            if (info != null) {
                refreshWeatherDisplay(info);
            }

            User user = UserDAO.getObjectFromCursor(data);
            if (user != null) {
                refreshUserDisplay(user);
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
            mPresenter.onUserAction(HomepageUserAction.REFRESH, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayData(HomepageModel model, UpdateEnum update) {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }


    enum HomepageUserAction implements UserActionEnum {
        REFRESH;


        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}
