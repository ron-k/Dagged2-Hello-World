package com.example.ronkassay_for_crossover.weather;

import android.content.Context;

import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.R;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Ron Kassay on 2017-04-02.
 */

class WeatherAnalyzerParameters {
    private final int extremeColdThreshold;
    private final int extremeHeatThreshold;


    @Inject
    public WeatherAnalyzerParameters(@Named(Application.TAG) Context context) {
        this(
                context.getResources().getInteger(R.integer.weather_analyzer_cold_threshold_celsius),
                context.getResources().getInteger(R.integer.weather_analyzer_heat_threshold_celsius)
        );
    }

    public WeatherAnalyzerParameters(int extremeColdThreshold, int extremeHeatThreshold) {
        this.extremeColdThreshold = extremeColdThreshold;
        this.extremeHeatThreshold = extremeHeatThreshold;
    }

    public int getExtremeColdThreshold() {
        return extremeColdThreshold;
    }

    public int getExtremeHeatThreshold() {
        return extremeHeatThreshold;
    }
}
