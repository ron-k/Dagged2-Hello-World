package com.example.ronkassay_for_crossover.weather;

import android.support.annotation.NonNull;

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
 * Created by ABiS on 2017-03-31.
 */
@Singleton
@Module
public class WeatherModule {

    @Provides
    @NonNull
    WeatherApi getWeatherApi(@NonNull OkHttpClient appHttpClient, @NonNull Retrofit.Builder baseBuilder) {
        OkHttpClient httpClientWithAuthorization = appHttpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl newUrl = request.url().newBuilder().addQueryParameter("APPID", "84e435b5ada0a9f3a4fb6a38a11a4821").build();
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

    @Provides
    @NonNull
    LocationInfoModel getLocationInfoModel() {
        return new LocationInfoModel();
    }

    @Provides
    LocationInfo getLocationInfo(@NonNull LocationInfoModel locationInfoModel) {
        return locationInfoModel.getLocationInfo();
    }
}