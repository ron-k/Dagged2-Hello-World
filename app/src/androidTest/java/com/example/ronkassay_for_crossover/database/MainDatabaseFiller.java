package com.example.ronkassay_for_crossover.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.com.example.ronkassay_for_crossover.test.R;
import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.weather.location.KnownLocation;
import com.google.gson.Gson;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Ron Kassay on 2017-04-01.
 */
@RunWith(AndroidJUnit4.class)
public class MainDatabaseFiller {

    private RuntimeExceptionDao<KnownLocation, Long> knownLocationsDao;
    private Context testContext;

    @Before
    public void setUp() throws Exception {
        knownLocationsDao = Application.getInstance().getApplicationComponent().knownLocationsDAO();
        testContext = InstrumentationRegistry.getContext();
        knownLocationsDao.deleteBuilder().delete();
    }

    @Test
    public void fillDb() throws Exception {
        List<KnownLocation> all = new ArrayList<>();
        Gson gson = new Gson();
        InputStream inputStream = testContext.getResources().openRawResource(R.raw.city_list);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            KnownLocation location = gson.fromJson(line, KnownLocation.class);
            all.add(location);
        }

        knownLocationsDao.create(all);

    }


    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static long randomLong() {
        return new Random().nextLong();
    }
}