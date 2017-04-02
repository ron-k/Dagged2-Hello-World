package com.example.ronkassay_for_crossover.weather.display;

import android.support.annotation.NonNull;

import com.example.ronkassay_for_crossover.common.ActivityLifecycleAware;
import com.example.ronkassay_for_crossover.common.CanDisplayErrors;
import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.WeatherDatum;

import java.util.List;

/**
 * Created by Ron Kassay on 2017-03-31.
 */

interface WeatherDisplayPresenter extends ActivityLifecycleAware {
    void onFabClicked();

    interface View extends CanDisplayErrors {

        void displayLocationInfo(@NonNull LocationInfo locationInfo);

        void displayWeatherInfo(WeatherDatum currentWeather);

        void displayForecast(List<WeatherDatum> forecastData);

        void navigateToSettingsScreen();
    }
}
