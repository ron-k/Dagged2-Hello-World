package com.example.ronkassay_for_crossover.weather.display;

import com.example.ronkassay_for_crossover.weather.City;
import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.TestWeatherApiCall;
import com.example.ronkassay_for_crossover.weather.WeatherDatum;
import com.example.ronkassay_for_crossover.weather.WeatherInfo;
import com.example.ronkassay_for_crossover.weather.WeatherModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Ron Kassay on 2017-04-01.
 */
public class WeatherDisplayPresenterImplTest {

    private WeatherDisplayPresenter.View view;
    private WeatherModel model;
    private LocationInfo locationInfo;
    private WeatherDisplayPresenterImpl presenter;
    private TestWeatherApiCall testCall;

    @Before
    public void setUp() throws Exception {
        view = mock(WeatherDisplayPresenter.View.class);
        model = mock(WeatherModel.class);
        locationInfo = mock(LocationInfo.class);
        presenter = new WeatherDisplayPresenterImpl(view, model, locationInfo);
        testCall = mock(TestWeatherApiCall.class);
        when(model.getLatestWeatherInfo(ArgumentMatchers.<LocationInfo>any(), anyBoolean())).thenReturn(testCall);
    }

    @Test
    public void onFabClicked() throws Exception {
        presenter.onFabClicked();

        verify(view).navigateToSettingsScreen();
    }

    @Test
    public void onActivityStarted() throws Exception {
        presenter.onActivityStarted();
        verifyRetrievalStarted();
    }

    @Test
    public void whenModelSendsData_shouldDisplayIt() throws Exception {
        presenter.retrieveWeatherInfo();
        Callback<WeatherInfo> callback = verifyRetrievalStarted();

        WeatherInfo weatherInfo = new WeatherInfo();
        WeatherDatum now = mock(WeatherDatum.class);
        WeatherDatum fc1 = mock(WeatherDatum.class);
        WeatherDatum fc2 = mock(WeatherDatum.class);
        weatherInfo.list = Arrays.asList(now, fc1, fc2);
        City city = new City();
        String expectedCity = "city1";
        city.name = expectedCity;
        String expectedCountry = "country2";
        city.country = expectedCountry;
        weatherInfo.city = city;

        callback.onResponse(null, Response.success(weatherInfo));
        verify(view).displayWeatherInfo(now);
        verify(view).displayForecast(Arrays.asList(fc1, fc2));
        verify(view).displayLocationInfo(new LocationInfo(expectedCity, expectedCountry));
        verifyNoMoreInteractions(model, view);
    }


    @Test
    public void whenModelFails_shouldDisplayError() throws Exception {
        presenter.retrieveWeatherInfo();
        Callback<WeatherInfo> callback = verifyRetrievalStarted();

        callback.onFailure(null, mock(Throwable.class));
        verify(view).showError(anyString());

        verifyNoMoreInteractions(model, view);
    }

    @Test
    public void whenModelFails2_shouldDisplayError() throws Exception {
        presenter.retrieveWeatherInfo();
        Callback<WeatherInfo> callback = verifyRetrievalStarted();

        callback.onResponse(null, Response.<WeatherInfo>error(404, ResponseBody.create(MediaType.parse("text/plain"), "")));
        verify(view).showError(anyString());

        verifyNoMoreInteractions(model, view);
    }

    private Callback<WeatherInfo> verifyRetrievalStarted() {
        verify(model).getLatestWeatherInfo(locationInfo, true);

        //noinspection unchecked - no any other way
        ArgumentCaptor<Callback<WeatherInfo>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(testCall).enqueue(argumentCaptor.capture());

        return argumentCaptor.getValue();
    }

}