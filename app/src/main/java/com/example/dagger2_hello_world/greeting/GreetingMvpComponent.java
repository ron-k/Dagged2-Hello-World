package com.example.dagger2_hello_world.greeting;

import com.example.dagger2_hello_world.ApplicationModule;

import dagger.Component;

/**
 *
 */

@GreetingScope
@Component(modules = {ApplicationModule.class, GreetingMvpModule.class})
interface GreetingMvpComponent {

    void inject(GreetingActivity greetingActivity);
}
