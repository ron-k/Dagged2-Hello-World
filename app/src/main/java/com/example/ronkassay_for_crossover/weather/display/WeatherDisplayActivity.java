package com.example.ronkassay_for_crossover.weather.display;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.ronkassay_for_crossover.R;
import com.example.ronkassay_for_crossover.databinding.ActivityWeatherDisplayBinding;
import com.example.ronkassay_for_crossover.weather.DaggerWeatherComponent;
import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.WeatherInfo;

import javax.inject.Inject;

public class WeatherDisplayActivity extends AppCompatActivity implements WeatherDisplayPresenter.View {

    @Inject
    WeatherDisplayPresenter presenter;

    @Inject
    LocationInfo locationInfo;

    private ActivityWeatherDisplayBinding layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeatherComponent weatherComponent = DaggerWeatherComponent.create();
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
        layout = DataBindingUtil.setContentView(this, R.layout.activity_weather_display);
        setSupportActionBar(layout.toolbar);

        layout.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFabClicked();
            }
        });
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
        String text = weatherInfo.toString();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        toast(message);
    }
}
