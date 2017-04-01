
package com.example.ronkassay_for_crossover.weather;

import java.util.ArrayList;
import java.util.Date;

public class WeatherDatum {

    public Long dt;
    public MainInfo main;
    public java.util.List<WeatherDescriptor> weather = new ArrayList<WeatherDescriptor>();
    public Cloudiness clouds;
    public Wind wind;

    public Date dtTxt() {
        return dt == null ? null : new Date(dt * 1000);
    }
}
