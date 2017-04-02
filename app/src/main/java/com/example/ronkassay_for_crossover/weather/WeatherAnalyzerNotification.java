package com.example.ronkassay_for_crossover.weather;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;

import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.CustomBindingAdapters;
import com.example.ronkassay_for_crossover.R;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayActivity;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ABiS on 2017-04-02.
 */

class WeatherAnalyzerNotification implements WeatherAnalyzer.View {
    private static final int NOTIFICATION_ID_WEATHER_ANALYZER = 42;

    private final Context context;
    private final NotificationManager notificationManager;

    @Inject
    WeatherAnalyzerNotification(@Named(Application.TAG) Context context, @NonNull NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    @Override
    public void showExtremeCold(@NonNull WeatherDatum weatherDatum) {
        createAndShowWarning(weatherDatum);
    }

    @Override
    public void showExtremeHeat(@NonNull WeatherDatum weatherDatum) {
        createAndShowWarning(weatherDatum);
    }

    @Override
    public void clear() {
        notificationManager.cancel(NOTIFICATION_ID_WEATHER_ANALYZER);
    }

    private void createAndShowWarning(WeatherDatum weatherDatum) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_warning);
        builder.setContentTitle(context.getString(R.string.weather_analyzer_extreme_title));
        String temperatureString = context.getString(R.string.weather_display_text_temp, weatherDatum.main.temp);
        builder.setContentText(context.getString(R.string.weather_analyzer_extreme_content_text, CustomBindingAdapters.formatDate(weatherDatum.getDate()), temperatureString));


        Intent intent = new Intent(context, WeatherDisplayActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID_WEATHER_ANALYZER, notification);
    }

}
