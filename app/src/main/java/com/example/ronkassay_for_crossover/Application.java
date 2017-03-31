package com.example.ronkassay_for_crossover;

import android.support.annotation.NonNull;

import dagger.internal.Preconditions;

/**
 * Created by ABiS on 2017-03-03.
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
