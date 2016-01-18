package com.ericliudeveloper.weatherforecast.framework;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface UpdatableView<M> {

    void displayData(M model, UpdateEnum update);

    void setPresenter(Presenter presenter);


}
