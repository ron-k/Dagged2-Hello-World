package com.example.ronkassay_for_crossover.weather.location;

import android.support.annotation.Nullable;

import com.example.ronkassay_for_crossover.common.ActivityLifecycleAware;
import com.example.ronkassay_for_crossover.common.CanDisplayErrors;
import com.example.ronkassay_for_crossover.weather.LocationInfo;

/**
 * Created by Ron Kassay on 2017-04-01.
 */

public interface LocationSettingsPresenter extends ActivityLifecycleAware {
    void onClickSaveLocation(@Nullable String city, @Nullable String country);

    void onUserLeavingActivity();

    interface View extends CanDisplayErrors {
        void displayLocation(@Nullable LocationInfo location);

        void navigateToWeatherDisplayScreen(boolean locationChanged);

        void leaveThisScreen(boolean haveLocation);
    }
}
