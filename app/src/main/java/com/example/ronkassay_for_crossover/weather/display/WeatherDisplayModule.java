package com.example.ronkassay_for_crossover.weather.display;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ron Kassay on 2017-03-31.
 */
@Module
public class WeatherDisplayModule {
    private final WeatherDisplayPresenter.View view;

    public WeatherDisplayModule(@NonNull WeatherDisplayPresenter.View view) {
        this.view = view;
    }

    @Provides
    @NonNull
    public WeatherDisplayPresenter.View getView() {
        return view;
    }

    @Provides
    @NonNull
    public WeatherDisplayPresenter getWeatherDisplayPresenter(@NonNull WeatherDisplayPresenterImpl impl) {
        return impl;
    }

}
