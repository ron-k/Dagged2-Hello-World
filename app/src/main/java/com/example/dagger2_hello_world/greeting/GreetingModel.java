package com.example.dagger2_hello_world.greeting;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dagger2_hello_world.R;
import com.example.model.Greeting;

import java.util.concurrent.TimeUnit;

/**
 * Created by ABiS on 2017-03-03.
 */

class GreetingModel {
    private final Context context;

    GreetingModel(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    public Greeting getGreetingForNewbie() {
        String greetingText = context.getString(R.string.greeting_text_newbie);
        Greeting out = new Greeting(greetingText);
        return out;
    }

    @NonNull
    public Greeting getGreetingForRegular(long userAgeMillis) {
        String durationString = context.getString(R.string.duration_minutes, TimeUnit.MILLISECONDS.toMinutes(userAgeMillis));
        String greetingText = context.getString(R.string.greeting_text_returning, durationString);
        Greeting out = new Greeting(greetingText);
        return out;
    }

}
