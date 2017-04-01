package com.example.ronkassay_for_crossover;

import android.support.annotation.Nullable;

import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.LocationInfoModel;
import com.example.ronkassay_for_crossover.weather.WeatherComponent;
import com.example.ronkassay_for_crossover.weather.WeatherModule;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayComponent;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayModule;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ABiS on 2017-04-01.
 */
public class GlobalLogicTest {

    private Application.GlobalLogic globalLogic;
    private LocationInfoModel model;

    @Before
    public void setUp() throws Exception {
        Application application = mock(Application.class);
        ApplicationComponent applicationComponent = new TesterApplicationComponent();
        when(application.getApplicationComponent()).thenReturn(applicationComponent);

        model = mock(LocationInfoModel.class);
        globalLogic = new Application.GlobalLogic(application, model);
    }

    @Test
    public void noLocation_noWeatherComponent() throws Exception {
        when(model.getLocationInfo()).thenReturn(null);

        WeatherComponent weatherComponent = globalLogic.getWeatherComponent();

        Assert.assertNull(weatherComponent);
    }

    @Test
    public void haveLocation_haveWeatherComponent() throws Exception {
        LocationInfo locationInfo = mock(LocationInfo.class);
        when(model.getLocationInfo()).thenReturn(locationInfo);

        WeatherComponent weatherComponent = globalLogic.getWeatherComponent();

        Assert.assertNotNull(weatherComponent);
        LocationInfo actualLocationInfo = weatherComponent.locationInfo();
        assertEquals(locationInfo, actualLocationInfo);
    }

    @Test
    public void locationChanges_weatherControllerChanges() throws Exception {
        haveLocation_haveWeatherComponent();
        haveLocation_haveWeatherComponent();
    }

    @Test
    public void locationLost_noWeatherComponent() throws Exception {
        haveLocation_haveWeatherComponent();
        noLocation_noWeatherComponent();
    }

    @Test
    public void locationRecovers_haveWeatherComponent() throws Exception {
        noLocation_noWeatherComponent();
        haveLocation_haveWeatherComponent();
    }

    private static class TesterApplicationComponent implements ApplicationComponent {
        @Override
        public void inject(Application.GlobalLogic application) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Picasso imageRetriever() {
            throw new UnsupportedOperationException();
        }

        @Nullable
        @Override
        public WeatherComponent plus(final WeatherModule weatherModule) {
            return new TesterWeatherComponent(weatherModule);
        }

        private static class TesterWeatherComponent implements WeatherComponent {
            private final WeatherModule weatherModule;

            TesterWeatherComponent(WeatherModule weatherModule) {
                this.weatherModule = weatherModule;
            }

            @Override
            public WeatherDisplayComponent plus(WeatherDisplayModule weatherDisplayModule) {
                throw new UnsupportedOperationException();
            }

            @Override
            public LocationInfo locationInfo() {
                return weatherModule.getLocationInfo();
            }
        }
    }
}