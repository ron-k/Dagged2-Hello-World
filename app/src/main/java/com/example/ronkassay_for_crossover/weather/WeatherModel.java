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
    private WeatherInfo lastKnownInfo = null;


    @Inject
    WeatherModel(@NonNull WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @NonNull
    public Call<WeatherInfo> getLatestWeatherInfo(@NonNull LocationInfo locationInfo) {
        final Call<WeatherInfo> apiCall = weatherApi.getWeatherByCityAndCountry(convertToQuery(locationInfo));
        new CallWrapper(apiCall);
        return apiCall;
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

        @NonNull
        private Response<WeatherInfo> fixResponseUsingLastKnown(Response<WeatherInfo> result) {
            if (result.isSuccessful()) {
                lastKnownInfo = result.body();
            }
            if (lastKnownInfo != null) {
                return Response.success(lastKnownInfo);
            } else {
                return result;
            }
        }

        @Override
        public void enqueue(final Callback<WeatherInfo> callback) {
            apiCall.enqueue(
                    new Callback<WeatherInfo>() {
                        @Override
                        public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                            callback.onResponse(call, fixResponseUsingLastKnown(response));
                        }

                        @Override
                        public void onFailure(Call<WeatherInfo> call, Throwable t) {
                            callback.onFailure(call, t);
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
