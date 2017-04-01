package com.example.ronkassay_for_crossover;

import android.support.annotation.Nullable;

import com.example.ronkassay_for_crossover.weather.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.WeatherModule;
import com.example.ronkassay_for_crossover.weather.location.KnownLocation;
import com.example.ronkassay_for_crossover.weather.location.LocationSettingsComponent;
import com.example.ronkassay_for_crossover.weather.location.LocationSettingsModule;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;

/**
 * Created by ABiS on 2017-04-01.
 */
class TesterApplicationComponent implements ApplicationComponent {
    @Override
    public void inject(Application.GlobalLogic application) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Picasso imageRetriever() {
        throw new UnsupportedOperationException();
    }


    @Nullable
    @Override
    public WeatherComponent plus(final WeatherModule weatherModule) {
        return new TesterWeatherComponent(weatherModule);
    }

    @Override
    public LocationSettingsComponent plus(LocationSettingsModule module) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RuntimeExceptionDao<KnownLocation, Long> knownLocationsDAO() {
        throw new UnsupportedOperationException();
    }

}