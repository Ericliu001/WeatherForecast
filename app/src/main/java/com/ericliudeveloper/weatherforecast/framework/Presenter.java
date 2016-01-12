package com.ericliudeveloper.weatherforecast.framework;

import android.content.Context;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface Presenter extends UpdatableView.UserActionListener, Model.OnUpdateCompleteListener {

    void setModel(Model model);

    void setUpdatableView(UpdatableView view);

    Context retrieveContext();


}
