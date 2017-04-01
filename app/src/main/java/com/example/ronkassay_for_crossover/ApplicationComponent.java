package com.example.ronkassay_for_crossover;

import android.support.annotation.Nullable;

import com.example.ronkassay_for_crossover.weather.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.WeatherModule;
import com.squareup.picasso.Picasso;

import dagger.Component;

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(Application.GlobalLogic application);

    Picasso imageRetriever();

    @Nullable
    WeatherComponent plus(WeatherModule weatherModule);
}