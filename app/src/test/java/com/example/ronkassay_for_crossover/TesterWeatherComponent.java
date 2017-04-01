package com.example.ronkassay_for_crossover;

import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.WeatherModule;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayComponent;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayModule;

/**
 * Created by ABiS on 2017-04-01.
 */
class TesterWeatherComponent implements WeatherComponent {
    private final WeatherModule weatherModule;

    TesterWeatherComponent(WeatherModule weatherModule) {
        this.weatherModule = weatherModule;
    }

    @Override
    public WeatherDisplayComponent plus(WeatherDisplayModule weatherDisplayModule) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LocationInfo locationInfo() {
        return weatherModule.getLocationInfo();
    }
}
