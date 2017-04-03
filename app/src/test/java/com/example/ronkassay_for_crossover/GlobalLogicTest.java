package com.example.ronkassay_for_crossover;

import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.location.LocationInfoModel;
import com.example.ronkassay_for_crossover.weather.retrieval.WeatherComponent;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Ron Kassay on 2017-04-01.
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

}