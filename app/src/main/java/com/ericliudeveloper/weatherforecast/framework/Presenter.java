package com.ericliudeveloper.weatherforecast.framework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface Presenter  {

    void setModel(Model model);

    void setUpdatableView(UpdatableView view);

    void loadInitialData();

    void onUpdateComplete(Model model, UpdateEnum update);

    void onUserAction(UserActionEnum action, @Nullable Bundle args);

    Context retrieveContext();


}
