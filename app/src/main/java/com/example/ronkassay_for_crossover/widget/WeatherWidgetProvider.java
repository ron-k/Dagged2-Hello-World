package com.example.ronkassay_for_crossover.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

/**
 * Created by ABiS on 2017-04-01.
 */

public class WeatherWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            WeatherUpdateService.startWidgetUpdate(context, appWidgetId);
        }

    }
}
