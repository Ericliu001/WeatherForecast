package com.ericliudeveloper.weatherforecast.mvp_framework;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface DisplayView {

    void displayData(DisplayUnit object, Presenter.RefreshDisplayEnum refreshDisplay);

    void setPresenter(Presenter presenter);


    /**
     * An unit representing an data object to be displayed.
     */
    interface DisplayUnit {
    }
}
