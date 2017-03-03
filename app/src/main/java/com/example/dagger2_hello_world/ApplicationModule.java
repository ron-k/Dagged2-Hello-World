package com.example.dagger2_hello_world;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @NonNull
    static Application application() {
        return Application.getInstance();
    }

    @Provides
    @NonNull
    @Named(ApplicationScope.TAG)
    static Context context() {
        return Application.getInstance();
    }
}