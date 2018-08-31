package com.example.dagger2_hello_world;

import android.support.annotation.NonNull;

import dagger.internal.Preconditions;

/**
 *
 */

public class Application extends android.app.Application {
    private static Application INSTANCE;

    @NonNull
    public static Application getInstance() {
        return Preconditions.checkNotNull(INSTANCE);
    }

    @Override
    public void onCreate() {
        INSTANCE = this;
        DaggerApplicationComponent.create().inject(this);
        super.onCreate();
    }

}
