package com.example.ronkassay_for_crossover.weather.fetch;

import com.example.ronkassay_for_crossover.weather.WeatherInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ABiS on 2017-03-31.
 */
// http://samples.openweathermap.org/data/2.5/forecast?id=524901&appid=b1b15e88fa797225412429c1c50c122a1
public interface WeatherApi {

    // http://api.openweathermap.org/data/2.5/weather?id=2172797
    @GET("weather")
    Call<WeatherInfo> getWeatherByCityId(@Query("id") String cityId);


    // http://api.openweathermap.org/data/2.5/weather?q=London,uk
    @GET("weather")
    Call<WeatherInfo> getWeatherByCityAndCountry(@Query("q") String query);

}
