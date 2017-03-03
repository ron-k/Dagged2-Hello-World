package com.example.dagger2_hello_world.greeting;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface GreetingScope {
    String TAG = "greeting";
}