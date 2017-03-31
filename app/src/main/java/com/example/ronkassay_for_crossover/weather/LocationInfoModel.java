package com.example.ronkassay_for_crossover.weather;

/**
 * Created by ABiS on 2017-03-31.
 */

public class LocationInfoModel {

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }

    private LocationInfo locationInfo = new LocationInfo("Paris", "France");

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }


}
