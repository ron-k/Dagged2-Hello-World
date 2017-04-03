package com.example.ronkassay_for_crossover.weather.display;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.R;
import com.example.ronkassay_for_crossover.databinding.WeatherDisplayActivityBinding;
import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.WeatherDatum;
import com.example.ronkassay_for_crossover.weather.location.LocationSettingsActivity;
import com.example.ronkassay_for_crossover.weather.retrieval.WeatherComponent;

import java.util.List;

import javax.inject.Inject;

public class WeatherDisplayActivity extends AppCompatActivity implements WeatherDisplayPresenter.View {

    private static final String EXTRA_LOCATION_CHANGED = "EXTRA_LOCATION_CHANGED";
    @Inject
    ForecastAdapter forecastAdapter;
    @Inject
    WeatherDisplayPresenter presenter;

    @Inject
    LocationInfo locationInfo;

    private WeatherDisplayActivityBinding layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeatherComponent weatherComponent = Application.getInstance().getWeatherComponent();
        if (weatherComponent == null) {
            navigateToSettingsScreen();
            finish();
        } else {
            weatherComponent.plus(new WeatherDisplayModule(this)).inject(this);

            setupLayout();

            displayLocationInfo(locationInfo);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(EXTRA_LOCATION_CHANGED, false)) {
            recreate();
        }
    }


    public static void launchIt(@NonNull Activity activity, boolean isLocationChanged) {
        Intent intent = new Intent(activity, WeatherDisplayActivity.class);
        intent.putExtra(EXTRA_LOCATION_CHANGED, isLocationChanged);
        activity.startActivity(intent);
    }

    private void setupLayout() {
        layout = DataBindingUtil.setContentView(this, R.layout.weather_display_activity);
        setSupportActionBar(layout.toolbar);

        layout.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFabClicked();
            }
        });
        RecyclerView forecastContainer = layout.content.forecastContainer;
        forecastContainer.setHasFixedSize(true);
        forecastContainer.setLayoutManager(new LinearLayoutManager(this));
        forecastContainer.setAdapter(forecastAdapter);
        View.OnClickListener navigateToSettingsOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSettingsScreen();
            }
        };
        layout.content.txtCity.setOnClickListener(navigateToSettingsOnClick);
        layout.content.txtCountry.setOnClickListener(navigateToSettingsOnClick);
    }

    @Override
    public void displayLocationInfo(@NonNull LocationInfo locationInfo) {
        layout.content.setLocationInfo(locationInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onActivityStarted();
    }

    @Override
    public void displayWeatherInfo(WeatherDatum currentWeather) {
        layout.content.setWeatherDatum(currentWeather);
    }

    @Override
    public void displayForecast(List<WeatherDatum> forecastData) {
        forecastAdapter.setData(forecastData);
    }

    @Override
    public void navigateToSettingsScreen() {
        startActivity(new Intent(this, LocationSettingsActivity.class));
    }


    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        toast(message);
    }
}
