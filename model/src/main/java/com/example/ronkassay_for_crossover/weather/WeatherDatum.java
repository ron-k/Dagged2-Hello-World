
package com.example.ronkassay_for_crossover.weather;

import java.util.ArrayList;
import java.util.Date;

public class WeatherDatum {

    public Long dt;
    public MainInfo main;
    public java.util.List<WeatherDescriptor> weather = new ArrayList<>();
    public Wind wind;

    public Date getDate() {
        return dt == null ? null : new Date(dt * 1000);
    }
}
