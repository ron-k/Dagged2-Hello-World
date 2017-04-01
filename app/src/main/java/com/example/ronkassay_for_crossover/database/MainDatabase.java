package com.example.ronkassay_for_crossover.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.ronkassay_for_crossover.ApplicationScope;
import com.example.ronkassay_for_crossover.weather.location.KnownLocation;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ABiS on 2017-04-01.
 */

public class MainDatabase extends OrmLiteSqliteOpenHelper {

    @Inject
    public MainDatabase(@Named(ApplicationScope.TAG) @NonNull Context context) {
        super(context, "main", null, Schema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class clazz : Schema.persistedObjects) {
                TableUtils.createTable(connectionSource, clazz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        throw new AssertionError("unexpected schema: " + oldVersion);
    }


    public static class Schema {
        static final int VERSION = 1;

        static final Class[] persistedObjects = {KnownLocation.class};

        public static class KnownLocationTable {
            public static final String TABLE_NAME = "KnownLocation";

            public static final String COLUMN_ID = "_id";
            public static final String COLUMN_NAME = "name";
            public static final String COLUMN_COUNTRY = "country";

            public static final String[] PROJECTION = {COLUMN_ID, COLUMN_NAME, COLUMN_COUNTRY};
        }
    }

}
