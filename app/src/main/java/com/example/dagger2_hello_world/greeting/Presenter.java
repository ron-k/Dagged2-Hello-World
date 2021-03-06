package com.example.dagger2_hello_world.greeting;

import android.support.annotation.NonNull;

import com.example.model.Greeting;

/**
 *
 */

interface Presenter {
    void updateGreeting();

    interface View {
        void setGreeting(@NonNull Greeting greeting);
    }
}
