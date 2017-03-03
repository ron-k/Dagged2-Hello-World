package com.example.dagger2_hello_world.greeting;

import android.support.annotation.NonNull;

import com.example.model.Greeting;

/**
 * Created by ABiS on 2017-03-03.
 */

class Presentor {
    private final View view;
    @NonNull
    private final GreetingModel greetingModel;
    @NonNull
    private final UserAgeModel userAgeModel;

    Presentor(@NonNull View view, @NonNull GreetingModel model, @NonNull UserAgeModel userAgeModel) {
        this.view = view;
        this.greetingModel = model;
        this.userAgeModel = userAgeModel;
    }

    public void onActivityResumed() {
        final Greeting greeting;
        if (userAgeModel.isUserNewbie()) {
            greeting = greetingModel.getGreetingForNewbie();
        } else {
            greeting = greetingModel.getGreetingForRegular(userAgeModel.getUserAgeMillis());
        }
        view.updateGreeting(greeting);
    }

    interface View {
        void updateGreeting(@NonNull Greeting greeting);
    }
}

