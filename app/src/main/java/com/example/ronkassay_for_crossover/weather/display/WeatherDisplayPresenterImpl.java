package com.example.ronkassay_for_crossover.weather.display;

import android.support.annotation.NonNull;

import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.WeatherInfo;
import com.example.ronkassay_for_crossover.weather.WeatherModel;

import java.io.InvalidObjectException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ABiS on 2017-03-31.
 */

class WeatherDisplayPresenterImpl implements WeatherDisplayPresenter {
    @NonNull
    private final View view;
    @NonNull
    private final WeatherModel model;
    @NonNull
    private final LocationInfo locationInfo;
    private final Callback<WeatherInfo> retrievalCallback = new Callback<WeatherInfo>() {
        @Override
        public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
            if (response.isSuccessful()) {
                onWeatherInfoRetrieved(response.body());
            } else {
                onWeatherInfoFailedRetrieval(new InvalidObjectException("response was not successful"));
            }
        }

        @Override
        public void onFailure(Call<WeatherInfo> call, Throwable t) {
            onWeatherInfoFailedRetrieval(t);
        }
    };

    private void onWeatherInfoRetrieved(@NonNull WeatherInfo weatherInfo) {
        view.displayWeatherInfo(weatherInfo);
    }

    private void onWeatherInfoFailedRetrieval(Throwable t) {
        view.showError("Oops! ");
    }

    @Inject
    WeatherDisplayPresenterImpl(@NonNull View view, @NonNull WeatherModel model, @NonNull LocationInfo locationInfo) {
        this.view = view;
        this.model = model;
        this.locationInfo = locationInfo;
    }

    @Override
    public void onFabClicked() {

    }

    @Override
    public void onActivityStarted() {
        retrieveWeatherInfo();
    }

    private void retrieveWeatherInfo() {
        model.getLatestWeatherInfo(locationInfo).enqueue(retrievalCallback);
    }
}
