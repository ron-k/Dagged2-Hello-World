package com.example.ronkassay_for_crossover.weather.retrieval;

import android.support.annotation.NonNull;

import com.example.ronkassay_for_crossover.weather.WeatherDatum;
import com.example.ronkassay_for_crossover.weather.WeatherInfo;

/**
 * Created by Ron Kassay on 2017-04-02.
 */

public interface WeatherAnalyzer {

    void onWeatherUpdated(@NonNull WeatherInfo weatherInfo);


    interface View {
        void showExtremeCold(@NonNull WeatherDatum weatherDatum);

        void showExtremeHeat(@NonNull WeatherDatum weatherDatum);

        void clear();
    }
}
