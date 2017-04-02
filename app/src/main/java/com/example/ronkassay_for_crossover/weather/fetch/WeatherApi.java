package com.example.ronkassay_for_crossover.weather.fetch;

import com.example.ronkassay_for_crossover.weather.WeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ron Kassay on 2017-03-31.
 */
// http://openweathermap.org/forecast5
public interface WeatherApi {

    // http://api.openweathermap.org/data/2.5/forecast?id=2172797&units=metric
    @GET("forecast?units=metric")
    Call<WeatherInfo> getWeatherByCityId(@Query("id") String cityId);


    // http://api.openweathermap.org/data/2.5/forecast?q=London,uk&units=metric
    @GET("forecast?units=metric")
    Call<WeatherInfo> getWeatherByCityAndCountry(@Query("q") String query);

}
