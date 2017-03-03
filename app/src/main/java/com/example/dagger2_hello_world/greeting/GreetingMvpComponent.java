package com.example.dagger2_hello_world.greeting;

import com.example.dagger2_hello_world.ApplicationModule;

import dagger.Component;

/**
 * Created by ABiS on 2017-03-03.
 */

@GreetingScope
@Component(modules = {ApplicationModule.class, GreetingMvpModule.class})
interface GreetingMvpComponent {

    void inject(GreetingActivity greetingActivity);
}
