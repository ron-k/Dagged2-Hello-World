package com.example.ronkassay_for_crossover.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.R;
import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.WeatherInfo;
import com.example.ronkassay_for_crossover.weather.WeatherModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;

public class WeatherUpdateService extends IntentService {
    private static final String ACTION_UPDATE_WIDGET = "com.example.ronkassay_for_crossover.weather.location.action.UPDATE_WIDGET";

    private static final String EXTRA_WIDGET_ID = "com.example.ronkassay_for_crossover.weather.location.extra.WIDGET_ID";
    private static final String EXTRA_FORCE_UPDATE = "com.example.ronkassay_for_crossover.weather.location.extra.FORCE_UPDATE";

    private static final String TAG = WeatherUpdateService.class.getSimpleName();


    public WeatherUpdateService() {
        super(TAG);
    }

    public static void startWidgetUpdate(Context context, int widgetId) {
        Intent intent = getWidgetUpdateIntent(context, widgetId, false);
        context.startService(intent);
    }

    @NonNull
    private static Intent getWidgetUpdateIntent(Context context, int widgetId, boolean forceUpdate) {
        Intent intent = new Intent(context, WeatherUpdateService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(EXTRA_WIDGET_ID, widgetId);
        intent.putExtra(EXTRA_FORCE_UPDATE, forceUpdate);
        return intent;
    }

    @Inject
    Picasso imageRetriever;

    @Inject
    WeatherModel weatherModel;

    @Inject
    LocationInfo locationInfo;

    @Inject
    Handler handler;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent() called with: intent = [" + intent + "]");
        if (intent != null) {
            WeatherComponent weatherComponent = Application.getInstance().getWeatherComponent();
            if (weatherComponent != null) {
                weatherComponent.inject(this);
            } else {
                Log.w(TAG, "onHandleIntent - no location");
                return;
            }
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                if (!intent.hasExtra(EXTRA_WIDGET_ID)) {
                    return;
                }
                handleWidgetUpdate(intent.getIntExtra(EXTRA_WIDGET_ID, 0), intent.getBooleanExtra(EXTRA_FORCE_UPDATE, false));
            }
        }
    }

    private void handleWidgetUpdate(int widgetId, boolean forceUpdate) {
        try {
            Response<WeatherInfo> response = weatherModel.getLatestWeatherInfo(locationInfo, !forceUpdate).execute();
            if (response.isSuccessful()) {
                RemoteViews views = updateViews(response.body(), widgetId);

                publishViews(views);
            }
        } catch (IOException e) {
            Log.e(TAG, "handleWidgetUpdate ", e);
        }
    }

    private void publishViews(RemoteViews views) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName component = new ComponentName(this, WeatherWidgetProvider.class);
        appWidgetManager.updateAppWidget(component, views);
    }

    private RemoteViews updateViews(final WeatherInfo response, final int widgetId) {
        final RemoteViews views = new RemoteViews(getPackageName(), R.layout.weather_widget);
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageRetriever.load(response.list.get(0).weather.get(0).getIconUrl()).into(views, R.id.imgConditionIcon, new int[]{widgetId});
            }
        });
//        views.setTextViewText(R.id.txtCity, "" + System.currentTimeMillis());
        views.setTextViewText(R.id.txtCity, response.city.name);
        views.setTextViewText(R.id.txtTemp, getString(R.string.weather_display_text_temp, response.list.get(0).main.temp));

        Intent intent = getWidgetUpdateIntent(this, widgetId, true);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.container, pendingIntent);

        return views;

    }

}
