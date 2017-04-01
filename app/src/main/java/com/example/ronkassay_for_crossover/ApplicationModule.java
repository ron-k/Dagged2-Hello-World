package com.example.ronkassay_for_crossover;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.ronkassay_for_crossover.database.MainDatabase;
import com.example.ronkassay_for_crossover.weather.LocationInfoModel;
import com.example.ronkassay_for_crossover.weather.LocationInfoModelImpl;
import com.example.ronkassay_for_crossover.weather.location.KnownLocation;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    @Named(ApplicationScope.TAG)
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

}