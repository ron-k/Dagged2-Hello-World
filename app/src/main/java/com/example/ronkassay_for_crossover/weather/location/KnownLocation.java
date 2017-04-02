package com.example.ronkassay_for_crossover.weather.location;

import com.example.ronkassay_for_crossover.database.MainDatabase;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ron Kassay on 2017-04-01.
 */

@DatabaseTable(tableName = MainDatabase.Schema.KnownLocationTable.TABLE_NAME)
public class KnownLocation {
    @DatabaseField(id = true, columnName = MainDatabase.Schema.KnownLocationTable.COLUMN_ID)
    private long _id;
    @DatabaseField(index = true, columnName = MainDatabase.Schema.KnownLocationTable.COLUMN_NAME)
    private String name;
    @DatabaseField(index = true, columnName = MainDatabase.Schema.KnownLocationTable.COLUMN_COUNTRY)
    private String country;

    public KnownLocation() {
        this(0, null, null);
    }

    public KnownLocation(long _id, String name, String country) {
        this._id = _id;
        this.name = name;
        this.country = country;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
