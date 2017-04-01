package com.example.ronkassay_for_crossover.weather;

import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayComponent;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayModule;

import dagger.Subcomponent;

/**
 * Created by ABiS on 2017-03-31.
 */
@Subcomponent(modules = {WeatherModule.class})
public interface WeatherComponent {
    WeatherDisplayComponent plus(WeatherDisplayModule weatherDisplayModule);

    LocationInfo locationInfo();
}