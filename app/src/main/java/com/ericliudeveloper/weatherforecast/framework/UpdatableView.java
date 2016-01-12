package com.ericliudeveloper.weatherforecast.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface UpdatableView<M> {

    void displayData(M model, UpdateEnum update);

    void setUserActionListener(UserActionListener listener);


    /**
     * A listener for events fired off by a {@link Model}
     */
    interface UserActionListener {

        /**
         * Called when the user has performed an {@code action}, with data to be passed
         * as a {@link Bundle} in {@code args}.
         * <p/>
         * Add the constants used to store values in the bundle to the Model implementation class
         * as final static protected strings.
         */
        void onUserAction(UserActionEnum action, @Nullable Bundle args);
    }
}
