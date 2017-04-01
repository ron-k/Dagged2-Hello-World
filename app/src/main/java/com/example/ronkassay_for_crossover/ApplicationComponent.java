package com.example.ronkassay_for_crossover;

import android.app.Application;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

import dagger.Component;

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(Application application);

    @NonNull
    Picasso imageRetriever();
}