package com.example.dagger2_hello_world.greeting;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by ABiS on 2017-03-03.
 */

class FeatureModule {
    final static String TAG = "greeting";

    private FeatureModule() {
        //non instantiable
    }

    @NonNull
    public static Presentor getPresentor(@NonNull Presentor.View view, @NonNull Context context) {
        return new Presentor(view, new GreetingModel(context), UserAgeModel.create(context));
    }
}
