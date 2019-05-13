package com.example.dagger2_hello_world;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ApplicationModule {

    @Provides
    @NonNull
    static Application myApplication() {
        return Application.getInstance();
    }


    @Binds
    @NonNull
    @Named(ApplicationScope.TAG)
    abstract Context context(Application application);

    @Binds
    @NonNull
    abstract android.app.Application application(Application application);
}