package com.example.ronkassay_for_crossover.weather.location;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.LocationInfoModel;

import javax.inject.Inject;

/**
 * Created by Ron Kassay on 2017-04-01.
 */

class LocationSettingsPresenterImpl implements LocationSettingsPresenter {
    private final LocationInfoModel model;
    private final View view;
    private LocationInfo initialLocation;

    @Inject
    LocationSettingsPresenterImpl(@NonNull LocationInfoModel model, @NonNull View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onActivityStarted() {
        initialLocation = model.getLocationInfo();
        view.displayLocation(initialLocation);
    }

    @Override
    public void onClickSaveLocation(@Nullable String city, @Nullable String country) {
        model.setLocationInfo(city, country);
    }

    @Override
    public void onUserLeavingActivity() {
        LocationInfo currentLocation = model.getLocationInfo();
        if (currentLocation != null) {
            boolean locationChanged = !currentLocation.equals(initialLocation);
            view.navigateToWeatherDisplayScreen(locationChanged);
        }
        view.leaveThisScreen(currentLocation != null);
    }

}
