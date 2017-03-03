package com.example.dagger2_hello_world.greeting;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dagger2_hello_world.R;
import com.example.model.Greeting;

import java.util.concurrent.TimeUnit;

/**
 * Created by ABiS on 2017-03-03.
 */

public class GreetingModel {
    private final Context context;
    private final UserAgeModel userAgeModel;

    public GreetingModel(@NonNull Context context, @NonNull UserAgeModel userAgeModel) {
        this.context = context;
        this.userAgeModel = userAgeModel;
    }

    @NonNull
    public Greeting getGreeting() {
        final String greetingText;
        if (userAgeModel.isUserNewbie()) {
            greetingText = context.getString(R.string.greeting_text_newbie);

        } else {
            String durationString = context.getString(R.string.duration_minutes, TimeUnit.MILLISECONDS.toMinutes(userAgeModel.getUserAgeMillis()));
            greetingText = context.getString(R.string.greeting_text_returning, durationString);
        }
        Greeting out = new Greeting(greetingText);
        return out;
    }

}
