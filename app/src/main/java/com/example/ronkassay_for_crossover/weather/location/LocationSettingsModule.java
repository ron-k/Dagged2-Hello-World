package com.example.ronkassay_for_crossover.weather.location;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ABiS on 2017-04-01.
 */
@Module
public class LocationSettingsModule {

    private final LocationSettingsPresenter.View view;

    public LocationSettingsModule(@NonNull LocationSettingsPresenter.View view) {
        this.view = view;
    }

    @Provides
    @NonNull
    LocationSettingsPresenter locationSettingsPresenter(LocationSettingsPresenterImpl impl) {
        return impl;
    }

    @Provides
    @NonNull
    public LocationSettingsPresenter.View getView() {
        return view;
    }

}
