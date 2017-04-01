package com.example.ronkassay_for_crossover.weather.location;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.Toast;

import com.example.ronkassay_for_crossover.Application;
import com.example.ronkassay_for_crossover.R;
import com.example.ronkassay_for_crossover.database.MainDatabase;
import com.example.ronkassay_for_crossover.databinding.LocationSettingsActivityBinding;
import com.example.ronkassay_for_crossover.weather.LocationInfo;
import com.example.ronkassay_for_crossover.weather.display.WeatherDisplayActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import javax.inject.Inject;

public class LocationSettingsActivity extends AppCompatActivity implements LocationSettingsPresenter.View {

    private static final String TAG = LocationSettingsActivity.class.getSimpleName();
    private LocationSettingsActivityBinding layout;
    @Inject
    RuntimeExceptionDao<KnownLocation, Long> dao;

    @Inject
    LocationSettingsPresenter presenter;

    @Inject
    MainDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Application.getInstance().getApplicationComponent().plus(new LocationSettingsModule(this)).inject(this);
        super.onCreate(savedInstanceState);
        setupLayout();
    }

    private void setupLayout() {
        layout = DataBindingUtil.setContentView(this, R.layout.location_settings_activity);
        setSupportActionBar(layout.toolbar);
        layout.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onUserLeavingActivity();
            }
        });
        cursor = database.getReadableDatabase().query(MainDatabase.Schema.KnownLocationTable.TABLE_NAME, MainDatabase.Schema.KnownLocationTable.PROJECTION, null, null, null, null, MainDatabase.Schema.KnownLocationTable.COLUMN_NAME);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.location_settins_suggestion_item, cursor, new String[]{MainDatabase.Schema.KnownLocationTable.COLUMN_NAME, MainDatabase.Schema.KnownLocationTable.COLUMN_COUNTRY}, new int[]{R.id.txtCity, R.id.txtCountry}, 0);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                String selection = MainDatabase.Schema.KnownLocationTable.COLUMN_NAME + " LIKE ?";
                String[] selectionArgs = {"%" + constraint + "%"};
                Cursor q = database.getReadableDatabase().query(MainDatabase.Schema.KnownLocationTable.TABLE_NAME,
                        MainDatabase.Schema.KnownLocationTable.PROJECTION, selection, selectionArgs,
                        null, null, MainDatabase.Schema.KnownLocationTable.COLUMN_NAME);
                return q;
            }
        });
        adapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cursor) {
                int nameIndex = cursor.getColumnIndex(MainDatabase.Schema.KnownLocationTable.COLUMN_NAME);
                int countryIndex = cursor.getColumnIndex(MainDatabase.Schema.KnownLocationTable.COLUMN_COUNTRY);
                return cursor.getString(nameIndex) + " " + cursor.getString(countryIndex);
            }
        });
        AutoCompleteTextView txtCity = layout.content.txtCity;
        txtCity.setThreshold(1);
        txtCity.setAdapter(adapter);

        txtCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
                KnownLocation data = dao.queryForId(id);
                presenter.onClickSaveLocation(data.getName(), data.getCountry());

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onActivityStarted();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLocation(@Nullable LocationInfo location) {
        layout.content.setData(location);
    }

    @Override
    public void navigateToWeatherDisplayScreen(boolean locationChanged) {
        WeatherDisplayActivity.launchIt(this, locationChanged);
    }

    @Override
    public void leaveThisScreen() {
        finish();
    }

    @Override
    public void onBackPressed() {
        presenter.onUserLeavingActivity();
    }

    @Override
    protected void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
        super.onDestroy();
    }
}
