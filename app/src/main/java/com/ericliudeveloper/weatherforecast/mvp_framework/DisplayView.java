package com.ericliudeveloper.weatherforecast.mvp_framework;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface DisplayView {

    void displayData(Object element, Presenter.RefreshDisplayEnum refreshDisplay);

    void setPresenter(Presenter presenter);



}
