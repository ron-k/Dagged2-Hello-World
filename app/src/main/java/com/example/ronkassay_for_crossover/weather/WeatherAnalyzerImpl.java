package com.example.ronkassay_for_crossover.weather;

import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by Ron Kassay on 2017-04-02.
 */

class WeatherAnalyzerImpl implements WeatherAnalyzer {
    private final WeatherAnalyzer.View view;
    private final int extremeColdThreshold;
    private final int extremeHeatThreshold;
    private int maxLookahead;

    @Inject
    WeatherAnalyzerImpl(@NonNull View view, @NonNull WeatherAnalyzerParameters parameters) {
        this.view = view;
        this.maxLookahead = parameters.getMaxLookahead();
        this.extremeColdThreshold = parameters.getExtremeColdThreshold();
        this.extremeHeatThreshold = parameters.getExtremeHeatThreshold();
    }

    @Override
    public void onWeatherUpdated(@NonNull WeatherInfo weatherInfo) {
        boolean foundExtreme = false;
        for (WeatherDatum weatherDatum : weatherInfo.list.subList(0, Math.min(weatherInfo.list.size(), maxLookahead))) {
            if (foundExtreme = checkExtremeHeat(weatherDatum)) {
                view.showExtremeHeat(weatherDatum);
            } else if (foundExtreme = checkExtremeCold(weatherDatum)) {
                view.showExtremeCold(weatherDatum);
            }
            if (foundExtreme) {
                break;
            }
        }
        if (!foundExtreme) {
            view.clear();
        }
    }

    private boolean checkExtremeCold(WeatherDatum weatherDatum) {
        return weatherDatum.main.temp < extremeColdThreshold;
    }

    private boolean checkExtremeHeat(WeatherDatum weatherDatum) {
        return weatherDatum.main.temp > extremeHeatThreshold;
    }
}
