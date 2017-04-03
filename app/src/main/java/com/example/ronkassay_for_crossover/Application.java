package com.example.ronkassay_for_crossover;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.location.LocationInfoModel;
import com.example.ronkassay_for_crossover.weather.retrieval.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.retrieval.WeatherModule;

import javax.inject.Inject;

import dagger.internal.Preconditions;

/**
 * Created by Ron Kassay on 2017-03-03.
 */

public class Application extends android.app.Application {
    public static final String TAG = "Application";
    private static Application INSTANCE;

    private ApplicationComponent applicationComponent;

    private final GlobalLogic globalLogic = new GlobalLogic(this);

    @NonNull
    public static Application getInstance() {
        return Preconditions.checkNotNull(INSTANCE);
    }

    @Override
    public void onCreate() {
        INSTANCE = this;
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(globalLogic);
        super.onCreate();
    }

    @NonNull
    public ApplicationComponent getApplicationComponent() {
        return Preconditions.checkNotNull(applicationComponent);
    }

    @Nullable
    public WeatherComponent getWeatherComponent() {
        return globalLogic.getWeatherComponent();
    }


    static class GlobalLogic {
        private final Application application;

        @Inject
        LocationInfoModel locationInfoModel;

        private WeatherComponent weatherComponent;

        GlobalLogic(Application application) {
            this.application = application;
        }

        @RestrictTo(RestrictTo.Scope.TESTS)
        GlobalLogic(Application application, LocationInfoModel locationInfoModel) {
            this.application = application;
            this.locationInfoModel = locationInfoModel;
        }

        @Nullable
        WeatherComponent getWeatherComponent() {
            LocationInfo locationInfo = locationInfoModel.getLocationInfo();

            if (locationInfo == null || weatherComponent != null && !weatherComponent.locationInfo().equals(locationInfo)) {
                weatherComponent = null;
            }

            if (locationInfo != null) {
                if (weatherComponent == null) {
                    ApplicationComponent applicationComponent = application.getApplicationComponent();
                    weatherComponent = applicationComponent.plus(new WeatherModule(locationInfo));
                    return weatherComponent;
                }
            }
            return weatherComponent;
        }
    }


}
