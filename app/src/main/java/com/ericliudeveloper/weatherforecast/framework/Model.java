package com.ericliudeveloper.weatherforecast.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Eric Liu on 12/01/2016.
 */
public interface Model {
    /**
     * Updates this Model according to a user {@code action} and {@code args}.
     * <p />
     * Add the constants used to store values in the bundle to the Model implementation class as
     * final static protected strings.
     *
     * @return true if successful.
     */
    boolean startModelUpdate(UserActionEnum action, @Nullable Bundle args);
    
    interface OnUpdateCompleteListener{
        void onUpdateComplete(Model model, UpdateEnum update);
    }
}
