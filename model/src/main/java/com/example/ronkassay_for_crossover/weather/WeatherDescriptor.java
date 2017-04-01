
package com.example.ronkassay_for_crossover.weather;


public class WeatherDescriptor {

    public Integer id;
    public String main;
    public String description;
    public String icon;

    public String getIconUrl() {
        if (icon != null) {
            return "http://openweathermap.org/img/w/" + icon + ".png";
        }
        return null;
    }

}
