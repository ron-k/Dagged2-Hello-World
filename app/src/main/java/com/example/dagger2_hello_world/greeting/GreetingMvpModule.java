package com.example.dagger2_hello_world.greeting;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.dagger2_hello_world.ApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 *
 */
@Module
class GreetingMvpModule {

    @Provides
    public GreetingActivity getActivity() {
        return activity;
    }

    private final GreetingActivity activity;

    GreetingMvpModule(@NonNull GreetingActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Provides
    Presenter getPresenter(@NonNull PresenterImpl presenterImpl) {
        return presenterImpl;
    }

    @Provides
    Presenter.View getView(@NonNull GreetingActivity activity) {
        return activity;
    }

    @Provides
    @Named(GreetingScope.TAG)
    Context activityContext(@NonNull GreetingActivity activity) {
        return activity;
    }

    @Provides
    @Reusable
    SharedPreferences getSharedPreferences(@NonNull @Named(ApplicationScope.TAG) Context context) {
        return context.getSharedPreferences(GreetingScope.TAG, Context.MODE_PRIVATE);
    }
}
