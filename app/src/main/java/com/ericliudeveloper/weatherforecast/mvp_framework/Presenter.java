package com.ericliudeveloper.weatherforecast.mvp_framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface Presenter {

    void setViewModel(ViewModel viewModel);

    void setDisplayView(DisplayView view);


    /**
     * this method is used to load the data for the first time when the View is just loaded into the screen.
     * @param args
     */
    void loadInitialData(Bundle args);

    /**
     * a callback for ViewModel to pass back the data when it's loaded.
     * @param viewModel
     * @param query
     * @param isConfigurationChange - a boolean to indicate if this method is called due to a re-creation of Views as a result
     *                              of a configuration change.
     */
    void onUpdateComplete(ViewModel viewModel, ViewModel.QueryEnum query, boolean isConfigurationChange);

    void onUserAction(UserActionEnum action, @Nullable Bundle args);


    void onViewCreated();
    /**
     * de-reference the DisplayView and ViewModel so they can be garbage collected
     */
    void onViewDestroyed();

    /**
     * Represents an action in the {@link DisplayView} performed by the user, for example when the
     * user clicks a specific button.
     */
    interface UserActionEnum {

        int getId();

    }

    /**
     * Created by ericliu on 20/01/2016.
     */
    interface RefreshDisplayEnum {
        int getId();
    }
}
