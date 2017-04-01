package com.example.ronkassay_for_crossover.weather.display;

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
import com.example.ronkassay_for_crossover.weather.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.WeatherInfo;

import javax.inject.Inject;

public class WeatherDisplayActivity extends AppCompatActivity implements WeatherDisplayPresenter.View {

    private final ForecastAdapter forecastAdapter = new ForecastAdapter();
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
            //start get location activity
            finish();
        } else {
            weatherComponent.plus(new WeatherDisplayModule(this)).inject(this);

            setupLayout();

            setInitialContent();
        }
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
    }

    private void setInitialContent() {
        layout.content.setLocationInfo(locationInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onActivityStarted();
    }

    @Override
    public void displayWeatherInfo(@NonNull WeatherInfo weatherInfo) {
        layout.content.setWeatherDatum(weatherInfo.list.get(0));
        forecastAdapter.setData(weatherInfo.list);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        toast(message);
    }
}
