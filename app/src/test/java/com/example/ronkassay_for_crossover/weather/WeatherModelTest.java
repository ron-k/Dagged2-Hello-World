package com.example.ronkassay_for_crossover.weather;

import com.example.ronkassay_for_crossover.weather.fetch.WeatherApi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by ABiS on 2017-04-01.
 */
public class WeatherModelTest {

    private WeatherModel model;
    private WeatherApi api;
    private TestWeatherApiCall callInternal;

    @Before
    public void setUp() throws Exception {
        api = mock(WeatherApi.class);
        model = new WeatherModel(api);
        callInternal = mock(TestWeatherApiCall.class);
        when(api.getWeatherByCityAndCountry(anyString())).thenReturn(callInternal);
    }


    @Test
    public void verifyApiIsCalled() throws Exception {
        callApiAndGetCallback();
    }

    @Test
    public void onSuccess_ReturnTheResponse() throws Exception {
        Call<WeatherInfo> callExternal = callApiAndGetCallback();

        Callback<WeatherInfo> cbExternal = mock(TestCallback.class);
        callExternal.enqueue(cbExternal);

        //noinspection unchecked - no any other way
        ArgumentCaptor<Callback<WeatherInfo>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(callInternal).enqueue(argumentCaptor.capture());
        Callback<WeatherInfo> cbInternal = argumentCaptor.getValue();


        WeatherInfo weatherInfo = mock(WeatherInfo.class);
        Response<WeatherInfo> response = Response.success(weatherInfo);
        cbInternal.onResponse(callInternal, response);

        verify(cbExternal).onResponse(callExternal, response);
        verifyNoMoreInteractions(cbExternal);
    }


    @Test
    public void onFailureAfterSuccess_ReturnTheLastGoodResponse() throws Exception {

        //first call - success
        Call<WeatherInfo> callExternal = callApiAndGetCallback();

        Callback<WeatherInfo> cbExternal = mock(TestCallback.class);
        callExternal.enqueue(cbExternal);

        //noinspection unchecked - no any other way
        ArgumentCaptor<Callback<WeatherInfo>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(callInternal).enqueue(argumentCaptor.capture());
        reset(callInternal);
        Callback<WeatherInfo> cbInternal = argumentCaptor.getValue();

        WeatherInfo expectedWeatherInfo = mock(WeatherInfo.class);
        Response<WeatherInfo> response = Response.success(expectedWeatherInfo);
        cbInternal.onResponse(callInternal, response);

        verify(cbExternal).onResponse(callExternal, response);

        // second call - failure

        callExternal = callApiAndGetCallback();

        cbExternal = mock(TestCallback.class);
        callExternal.enqueue(cbExternal);

        //noinspection unchecked - no any other way
        argumentCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(callInternal).enqueue(argumentCaptor.capture());
        reset(callInternal);
        cbInternal = argumentCaptor.getValue();

        Throwable t = mock(Throwable.class);
        cbInternal.onFailure(callInternal, t);

        //noinspection unchecked - no any other way
        ArgumentCaptor<Response<WeatherInfo>> responseCaptor = ArgumentCaptor.forClass(Response.class);
        verify(cbExternal).onResponse(eq(callExternal), responseCaptor.capture());

        Response<WeatherInfo> value = responseCaptor.getValue();
        WeatherInfo actualWeatherInfo = value.body();
        Assert.assertEquals(actualWeatherInfo, expectedWeatherInfo);

        //third call - success
        callExternal = callApiAndGetCallback();

        cbExternal = mock(TestCallback.class);
        callExternal.enqueue(cbExternal);

        //noinspection unchecked - no any other way
        argumentCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(callInternal).enqueue(argumentCaptor.capture());
        reset(callInternal);
        cbInternal = argumentCaptor.getValue();

        expectedWeatherInfo = mock(WeatherInfo.class);
        response = Response.success(expectedWeatherInfo);
        cbInternal.onResponse(callInternal, response);

        verify(cbExternal).onResponse(callExternal, response);
    }


    @Test
    public void onFirstFailure_ReturnFailure() throws Exception {
        Call<WeatherInfo> callExternal = callApiAndGetCallback();

        Callback<WeatherInfo> cbExternal = mock(TestCallback.class);
        callExternal.enqueue(cbExternal);

        //noinspection unchecked - no any other way
        ArgumentCaptor<Callback<WeatherInfo>> argumentCaptor = ArgumentCaptor.forClass(Callback.class);
        verify(callInternal).enqueue(argumentCaptor.capture());
        Callback<WeatherInfo> cbInternal = argumentCaptor.getValue();

        Throwable t = mock(Throwable.class);
        cbInternal.onFailure(callInternal, t);

        verify(cbExternal).onFailure(callExternal, t);
        verifyNoMoreInteractions(cbExternal);
    }

    private Call<WeatherInfo> callApiAndGetCallback() {
        String expectedCity = "city1";
        String expectedCountry = "country1";
        LocationInfo locationInfo = new LocationInfo(expectedCity, expectedCountry);

        Call<WeatherInfo> callExternal = model.getLatestWeatherInfo(locationInfo);

        verify(api, atLeastOnce()).getWeatherByCityAndCountry(expectedCity + "," + expectedCountry);


        return callExternal;
    }

    interface TestCallback extends Callback<WeatherInfo> {
    }
}