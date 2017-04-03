package com.example.ronkassay_for_crossover.weather.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.R;
import com.example.ronkassay_for_crossover.common.Strings;
import com.example.ronkassay_for_crossover.weather.LocationInfo;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Ron Kassay on 2017-03-31.
 */

public class LocationInfoModelImpl implements LocationInfoModel {

    private final SharedPreferences sharedPreferences;
    private final String cityKey;
    private final String countryKey;

    private LocationInfo locationInfo;

    @Inject
    LocationInfoModelImpl(@Named(Application.TAG) @NonNull Context context) {
        this(PreferenceManager.getDefaultSharedPreferences(context), context.getString(R.string.pref_locationCity_key), context.getString(R.string.pref_locationCountry_key));
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    LocationInfoModelImpl(@NonNull SharedPreferences sharedPreferences, String cityKey, String countryKey) {
        this.sharedPreferences = sharedPreferences;
        this.cityKey = cityKey;
        this.countryKey = countryKey;
        load();
    }

    private void load() {
        String country = sharedPreferences.getString(countryKey, null);
        String city = sharedPreferences.getString(cityKey, null);
        setLocationInfo(city, country);
    }

    private void store() {
        String city = null;
        String country = null;
        LocationInfo locationInfo = getLocationInfo();
        if (locationInfo != null) {
            city = locationInfo.getCity();
            country = locationInfo.getCountry();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cityKey, city);
        editor.putString(countryKey, country);
        editor.apply();
    }

    @Override
    public void setLocationInfo(@Nullable String city, @Nullable String country) {
        if (!Strings.isEmptyOrNull(city)) {
            setLocationInfo(new LocationInfo(city, country));
        } else {
            locationInfo = null;
        }
        store();
    }

    @Override
    public void setLocationInfo(@NonNull LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
        store();
    }

    @Override
    @Nullable
    public LocationInfo getLocationInfo() {
        return locationInfo;
    }


}
