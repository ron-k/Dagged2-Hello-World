package com.example.dagger2_hello_world;

import android.app.Application;

import dagger.Component;

@ApplicationScope
@Component(modules = ApplicationModule.class)
interface ApplicationComponent {
    void inject(Application application);
}