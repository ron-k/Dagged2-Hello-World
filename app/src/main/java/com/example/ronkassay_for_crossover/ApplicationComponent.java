package com.example.ronkassay_for_crossover;

import android.support.annotation.RestrictTo;

import com.example.ronkassay_for_crossover.weather.location.KnownLocation;
import com.example.ronkassay_for_crossover.weather.location.LocationSettingsComponent;
import com.example.ronkassay_for_crossover.weather.location.LocationSettingsModule;
import com.example.ronkassay_for_crossover.weather.retrieval.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.retrieval.WeatherModule;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(Application.GlobalLogic application);

    Picasso imageRetriever();

    WeatherComponent plus(WeatherModule module);

    LocationSettingsComponent plus(LocationSettingsModule module);

    @RestrictTo(RestrictTo.Scope.TESTS)
    RuntimeExceptionDao<KnownLocation, Long> knownLocationsDAO();

}