package com.example.ronkassay_for_crossover.weather;

import android.support.annotation.Nullable;

/**
 * Created by ABiS on 2017-03-31.
 */

public class LocationInfoModel {

    public LocationInfoModel() {
        setLocationInfo(new LocationInfo("Kiev", "Ukraine"));
    }

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }

    private LocationInfo locationInfo;

    @Nullable
    public LocationInfo getLocationInfo() {
//        LocationInfo locationInfo = new Random().nextBoolean() ? null : new LocationInfo("Kiev", "Ukraine");
        return locationInfo;
    }


}
