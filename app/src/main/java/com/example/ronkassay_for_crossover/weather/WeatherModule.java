package com.example.ronkassay_for_crossover.weather;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.ronkassay_for_crossover.weather.fetch.WeatherApi;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by Ron Kassay on 2017-03-31.
 */
@Singleton
@Module
public class WeatherModule {
    private final LocationInfo locationInfo;

    public WeatherModule(@NonNull LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }

    @Provides
    static WeatherApi getWeatherApi(@NonNull OkHttpClient appHttpClient, @NonNull Retrofit.Builder baseBuilder) {
        OkHttpClient httpClientWithAuthorization = appHttpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl newUrl = request.url().newBuilder().addQueryParameter("appid", "84e435b5ada0a9f3a4fb6a38a11a4821").build();
                Request newRequest = request.newBuilder().url(newUrl).build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = baseBuilder
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(httpClientWithAuthorization)
                .build();
        WeatherApi service = retrofit.create(WeatherApi.class);
        return service;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    @Provides
    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    @Provides
    WeatherAnalyzer weatherAnalyzerPresentor(@NonNull WeatherAnalyzerImpl impl) {
        return impl;
    }


    @Provides
    WeatherAnalyzer.View displayUsingNotification(@NonNull WeatherAnalyzerNotification weatherAnalyzerNotification) {
        return weatherAnalyzerNotification;
    }
}
