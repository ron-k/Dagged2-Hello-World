
package com.example.ronkassay_for_crossover.weather;


public class MainInfo {

    public Float temp;
    public Float tempMin;
    public Float tempMax;

    public Integer humidity;


    public boolean hasMinMax() {
        return tempMin != null && tempMax != null;
    }
}
