package com.example.dagger2_hello_world.greeting;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ABiS on 2017-03-03.
 */
@Module
class GreetingMvpModule {
    private final Presentor.View view;

    GreetingMvpModule(@NonNull Presentor.View view) {
        this.view = view;
    }

    @Provides
    public Presentor.View getView() {
        return view;
    }

    @NonNull
    @Provides
    Presentor getPresentor(@NonNull PresentorImpl presentorImpl) {
        return presentorImpl;
    }
}
