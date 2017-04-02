package com.example.ronkassay_for_crossover.weather;

import android.support.annotation.NonNull;

/**
 * Created by Ron Kassay on 2017-04-02.
 */

interface WeatherAnalyzer {

    void onWeatherUpdated(@NonNull WeatherInfo weatherInfo);


    interface View {
        void showExtremeCold(@NonNull WeatherDatum weatherDatum);

        void showExtremeHeat(@NonNull WeatherDatum weatherDatum);

        void clear();
    }
}
