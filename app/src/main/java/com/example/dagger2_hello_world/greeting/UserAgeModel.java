package com.example.dagger2_hello_world.greeting;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 *
 */

public class UserAgeModel {
    static final int NEWBIE_AGE_THRESHOLD_MINUTES = 3;
    private final SharedPreferences sharedPreferences;
    private final String spkey_firstRunTime = "spkey.UserAgeModel.firstRunTime";

    @Inject
    UserAgeModel(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        setUserAgeIfAbsent();
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
