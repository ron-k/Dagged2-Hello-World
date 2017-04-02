package com.example.ronkassay_for_crossover.weather;

/**
 * Created by Ron Kassay on 2017-03-31.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationInfo that = (LocationInfo) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return country != null ? country.equals(that.country) : that.country == null;

    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
