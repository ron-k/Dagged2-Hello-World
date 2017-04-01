package com.example.ronkassay_for_crossover;

import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    @Provides
    @NonNull
    static Application application() {
        return Application.getInstance();
    }

    @Provides
    @NonNull
    @Named(ApplicationScope.TAG)
    static Context context() {
        return Application.getInstance();
    }

    @Provides
    @NonNull
    static OkHttpClient getHttpClient() {
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
    static Picasso getImageRetriever(@NonNull Application application) {
        return Picasso.with(application);
    }

}