package com.example.ronkassay_for_crossover;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.example.ronkassay_for_crossover.database.MainDatabase;
import com.example.ronkassay_for_crossover.weather.location.KnownLocation;
import com.example.ronkassay_for_crossover.weather.location.LocationInfoModel;
import com.example.ronkassay_for_crossover.weather.location.LocationInfoModelImpl;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.NOTIFICATION_SERVICE;

@Module
@Singleton
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @NonNull
    Application application() {
        return application;
    }

    @Provides
    @NonNull
    @Named(Application.TAG)
    Context context() {
        return application;
    }

    @Provides
    @NonNull
    OkHttpClient getHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @NonNull
    static Retrofit.Builder getRetrofitBaseBuilder(@NonNull OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient);
    }

    @Provides
    @NonNull
    Picasso getImageRetriever() {
        return Picasso.with(application);
    }

    @Provides
    @NonNull
    @Singleton
    LocationInfoModel getLocationInfoModel(LocationInfoModelImpl impl) {
        return impl;
    }


    @Provides
    @NonNull
    RuntimeExceptionDao<KnownLocation, Long> getKnownLocationDao(@NonNull MainDatabase mainDatabase) {
        return mainDatabase.getRuntimeExceptionDao(KnownLocation.class);
    }


    @Provides
    @NonNull
    Handler getHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    @NonNull
    NotificationManager getNotificationManager() {
        return (NotificationManager) application.getSystemService(NOTIFICATION_SERVICE);
    }
}