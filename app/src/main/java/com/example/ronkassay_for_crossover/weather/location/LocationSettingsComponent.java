package com.example.ronkassay_for_crossover.weather.location;

import dagger.Subcomponent;

/**
 * Created by Ron Kassay on 2017-04-01.
 */
@Subcomponent(modules = LocationSettingsModule.class)
public interface LocationSettingsComponent {

    void inject(LocationSettingsActivity locationSettingsActivity);
}
