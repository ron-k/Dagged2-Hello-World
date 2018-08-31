package com.example.dagger2_hello_world.greeting;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.dagger2_hello_world.ApplicationScope;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 */

public class UserAgeModel {
    static final int NEWBIE_AGE_THRESHOLD_MINUTES = 3;
    private final SharedPreferences sharedPreferences;
    private final String spkey_firstRunTime = "spkey.UserAgeModel.firstRunTime";

    UserAgeModel(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        setUserAgeIfAbsent();
    }

    @Inject
    UserAgeModel(@Named(ApplicationScope.TAG) Context context) {
        this(context.getSharedPreferences(GreetingScope.TAG, Context.MODE_PRIVATE));
    }

    public long getUserAgeMillis() {
        return (now() - getFirstRunTimeMillis());
    }

    protected long now() {
        return System.currentTimeMillis();
    }

    private long getFirstRunTimeMillis() {
        return sharedPreferences.getLong(spkey_firstRunTime, now());
    }

    private void setUserAgeIfAbsent() {
        if (!sharedPreferences.contains(spkey_firstRunTime)) {
            setFirstTimeRun(now());
        }
    }

    void setFirstTimeRun(long firstTimeRun) {
        sharedPreferences.edit().putLong(spkey_firstRunTime, firstTimeRun).apply();
    }

    public boolean isUserNewbie() {
        return TimeUnit.MILLISECONDS.toMinutes(getUserAgeMillis()) < NEWBIE_AGE_THRESHOLD_MINUTES;
    }
}
