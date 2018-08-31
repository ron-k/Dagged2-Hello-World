package com.example.dagger2_hello_world.greeting;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
class GreetingMvpModule {
    private final Presenter.View view;

    GreetingMvpModule(@NonNull Presenter.View view) {
        this.view = view;
    }

    @Provides
    public Presenter.View getView() {
        return view;
    }

    @NonNull
    @Provides
    Presenter getPresenter(@NonNull PresenterImpl presenterImpl) {
        return presenterImpl;
    }
}
