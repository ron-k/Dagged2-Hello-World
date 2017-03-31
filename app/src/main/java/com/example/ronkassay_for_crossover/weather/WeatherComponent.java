package com.example.ronkassay_for_crossover.weather;

import com.example.ronkassay_for_crossover.ApplicationModule;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayComponent;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayModule;

import dagger.Component;

/**
 * Created by ABiS on 2017-03-31.
 */
@Component(modules = {ApplicationModule.class, WeatherModule.class})
public interface WeatherComponent {
    WeatherDisplayComponent plus(WeatherDisplayModule weatherDisplayModule);

}
