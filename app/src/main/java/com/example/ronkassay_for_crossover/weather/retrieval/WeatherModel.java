package com.example.ronkassay_for_crossover.weather.retrieval;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.R;
import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.WeatherInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ron Kassay on 2017-03-31.
 */

public class WeatherModel {
    @NonNull
    private final WeatherApi weatherApi;
    @NonNull
    private final WeatherAnalyzer weatherAnalyzer;
    @Nullable
    private WeatherInfo lastKnownWeatherInfo = null;
    private long cacheWriteTime = 0;

    private final long cachingPeriodMillis;

    @Inject
    WeatherModel(@NonNull WeatherApi weatherApi, @NonNull WeatherAnalyzer weatherAnalyzer, @Named(Application.TAG) Context context) {
        this(weatherApi, weatherAnalyzer, TimeUnit.MINUTES.toMillis(context.getResources().getInteger(R.integer.weather_model_caching_period_minutes)));
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    WeatherModel(@NonNull WeatherApi weatherApi, @NonNull WeatherAnalyzer weatherAnalyzer, long cachingPeriodMillis) {
        this.weatherApi = weatherApi;
        this.weatherAnalyzer = weatherAnalyzer;
        this.cachingPeriodMillis = cachingPeriodMillis;
    }

    @NonNull
    public Call<WeatherInfo> getLatestWeatherInfo(@NonNull LocationInfo locationInfo, boolean useCache) {
        final Call<WeatherInfo> apiCall = weatherApi.getWeatherByCityAndCountry(convertToQuery(locationInfo));
        return new CallWrapper(apiCall, useCache);
    }

    private String convertToQuery(LocationInfo locationInfo) {
        return locationInfo.getCity() + "," + locationInfo.getCountry();
    }

    private void setLastKnownWeatherInfo(@Nullable WeatherInfo newWeatherInfo) {
        if (newWeatherInfo != null && !newWeatherInfo.equals(lastKnownWeatherInfo)) {
            weatherAnalyzer.onWeatherUpdated(newWeatherInfo);
        }
        lastKnownWeatherInfo = newWeatherInfo;
    }


    private class CallWrapper implements Call<WeatherInfo> {
        private final Call<WeatherInfo> apiCall;
        private final boolean useCache;

        CallWrapper(Call<WeatherInfo> apiCall, boolean useCache) {
            this.apiCall = apiCall;
            this.useCache = useCache;
        }

        @Override
        public Response<WeatherInfo> execute() throws IOException {
            if (useCache && isCacheValid() && lastKnownWeatherInfo != null) {
                return Response.success(lastKnownWeatherInfo);
            } else {
                return fixResponseUsingLastKnown(apiCall.execute());
            }
        }

        @Nullable
        private Response<WeatherInfo> fixResponseUsingLastKnown(@Nullable Response<WeatherInfo> currentResponse) {
            final Response<WeatherInfo> out;
            if (currentResponse != null && currentResponse.isSuccessful()) {
                setLastKnownWeatherInfo(currentResponse.body());
                cacheWriteTime = now();
                out = currentResponse;
            } else {
                if (lastKnownWeatherInfo != null) {
                    out = Response.success(lastKnownWeatherInfo);
                } else {
                    out = currentResponse;
                }
            }
            return out;
        }

        @Override
        public void enqueue(final Callback<WeatherInfo> callback) {
            if (useCache && isCacheValid() && lastKnownWeatherInfo != null) {
                    callback.onResponse(CallWrapper.this, Response.success(lastKnownWeatherInfo));
            } else {
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
            return new CallWrapper(apiCall.clone(), useCache);
        }

        @Override
        public Request request() {
            return apiCall.request();
        }
    }

    private boolean isCacheValid() {
        long now = now();
        return now - cacheWriteTime < cachingPeriodMillis && now > cacheWriteTime;
    }

    protected long now() {
        return SystemClock.uptimeMillis();
    }
}
