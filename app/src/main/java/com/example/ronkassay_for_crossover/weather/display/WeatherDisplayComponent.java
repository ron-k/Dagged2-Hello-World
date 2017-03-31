package com.example.ronkassay_for_crossover.weather.display;

import dagger.Subcomponent;

/**
 * Created by ABiS on 2017-03-31.
 */
@Subcomponent(modules = {WeatherDisplayModule.class})
public interface WeatherDisplayComponent {

    void inject(WeatherDisplayActivity activity);

}
