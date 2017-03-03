package com.example.dagger2_hello_world.greeting;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dagger2_hello_world.R;
import com.example.model.Greeting;

/**
 * Created by ABiS on 2017-03-03.
 */

public class GreetingModel {
    private final Context context;

    public GreetingModel(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    public Greeting getGreeting() {
        return new Greeting(context.getString(R.string.greeting_text));
    }
}
