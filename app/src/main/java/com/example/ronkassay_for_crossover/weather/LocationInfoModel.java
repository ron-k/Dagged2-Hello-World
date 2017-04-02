package com.example.ronkassay_for_crossover.weather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Ron Kassay on 2017-04-01.
 */

public interface LocationInfoModel {
    void setLocationInfo(@Nullable String city, @Nullable String country);

    void setLocationInfo(@NonNull LocationInfo locationInfo);

    @Nullable
    LocationInfo getLocationInfo();
}
