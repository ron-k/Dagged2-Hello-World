package com.example.ronkassay_for_crossover.weather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ronkassay_for_crossover.weather.fetch.WeatherApi;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ABiS on 2017-03-31.
 */

public class WeatherModel {
    @NonNull
    private final WeatherApi weatherApi;
    @Nullable
    private WeatherInfo lastKnownGoodBody = null;


    @Inject
    WeatherModel(@NonNull WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @NonNull
    public Call<WeatherInfo> getLatestWeatherInfo(@NonNull LocationInfo locationInfo) {
        final Call<WeatherInfo> apiCall = weatherApi.getWeatherByCityAndCountry(convertToQuery(locationInfo));
        return new CallWrapper(apiCall);
    }

    private String convertToQuery(LocationInfo locationInfo) {
        return locationInfo.getCity() + "," + locationInfo.getCountry();
    }

    private class CallWrapper implements Call<WeatherInfo> {
        private final Call<WeatherInfo> apiCall;

        CallWrapper(Call<WeatherInfo> apiCall) {
            this.apiCall = apiCall;
        }

        @Override
        public Response<WeatherInfo> execute() throws IOException {
            return fixResponseUsingLastKnown(apiCall.execute());
        }

        @Nullable
        private Response<WeatherInfo> fixResponseUsingLastKnown(@Nullable Response<WeatherInfo> currentResponse) {
            final Response<WeatherInfo> out;
            if (currentResponse != null && currentResponse.isSuccessful()) {
                lastKnownGoodBody = currentResponse.body();
                out = currentResponse;
            } else {
                if (lastKnownGoodBody != null) {
                    out = Response.success(lastKnownGoodBody);
                } else {
                    out = currentResponse;
                }
            }
            return out;
        }

        @Override
        public void enqueue(final Callback<WeatherInfo> callback) {
            apiCall.enqueue(
                    new Callback<WeatherInfo>() {
                        @Override
                        public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                            callback.onResponse(CallWrapper.this, fixResponseUsingLastKnown(response));
                        }

                        @Override
                        public void onFailure(Call<WeatherInfo> call, Throwable t) {
                            Response<WeatherInfo> response = fixResponseUsingLastKnown(null);
                            if (response != null) {
                                callback.onResponse(CallWrapper.this, response);
                            } else {
                                callback.onFailure(CallWrapper.this, t);
                            }
                        }
                    });
        }

        @Override
        public boolean isExecuted() {
            return apiCall.isExecuted();
        }

        @Override
        public void cancel() {
            apiCall.cancel();
        }

        @Override
        public boolean isCanceled() {
            return apiCall.isCanceled();
        }

        @SuppressWarnings("CloneDoesntCallSuperClone")
        @Override
        public Call<WeatherInfo> clone() {
            return new CallWrapper(apiCall.clone());
        }

        @Override
        public Request request() {
            return apiCall.request();
        }
    }
}
