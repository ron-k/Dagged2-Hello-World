package com.example.ronkassay_for_crossover.weather;

/**
 * Created by ABiS on 2017-03-31.
 */

public class LocationInfo {
    private final String city;
    private final String country;

    public LocationInfo(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
