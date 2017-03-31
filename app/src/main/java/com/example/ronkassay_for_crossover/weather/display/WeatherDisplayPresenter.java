package com.example.ronkassay_for_crossover.weather.display;

import android.support.annotation.NonNull;

import com.example.ronkassay_for_crossover.weather.WeatherInfo;

/**
 * Created by ABiS on 2017-03-31.
 */

interface WeatherDisplayPresenter {
    void onFabClicked();

    void onActivityStarted();

    interface View {

        void displayWeatherInfo(@NonNull WeatherInfo weatherInfo);

        void showError(String message);
    }
}
