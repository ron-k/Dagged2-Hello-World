package com.example.ronkassay_for_crossover;

import android.app.Application;

import dagger.Component;

@ApplicationScope
@Component(modules = ApplicationModule.class)
interface ApplicationComponent {
    void inject(Application application);
}