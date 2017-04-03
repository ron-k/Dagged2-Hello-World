package com.example.ronkassay_for_crossover.weather.location;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ronkassay_for_crossover.weather.LocationInfo;

/**
 * Created by Ron Kassay on 2017-04-01.
 */

public interface LocationInfoModel {
    void setLocationInfo(@Nullable String city, @Nullable String country);

    void setLocationInfo(@NonNull LocationInfo locationInfo);

    @Nullable
    LocationInfo getLocationInfo();
}
