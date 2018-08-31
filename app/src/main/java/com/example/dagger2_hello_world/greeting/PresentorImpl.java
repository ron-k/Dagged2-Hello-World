package com.example.dagger2_hello_world.greeting;

import android.support.annotation.NonNull;

import com.example.model.Greeting;

import javax.inject.Inject;

/**
 *
 */

class PresentorImpl implements Presentor {
    private final View view;
    @NonNull
    private final GreetingModel greetingModel;
    @NonNull
    private final UserAgeModel userAgeModel;

    @Inject
    PresentorImpl(@NonNull View view, @NonNull GreetingModel greetingModel, @NonNull UserAgeModel userAgeModel) {
        this.view = view;
        this.greetingModel = greetingModel;
        this.userAgeModel = userAgeModel;
    }

    @Override
    public void updateGreeting() {
        final Greeting greeting;
        if (userAgeModel.isUserNewbie()) {
            greeting = greetingModel.getGreetingForNewbie();
        } else {
            greeting = greetingModel.getGreetingForRegular(userAgeModel.getUserAgeMillis());
        }
        view.setGreeting(greeting);
    }

}
