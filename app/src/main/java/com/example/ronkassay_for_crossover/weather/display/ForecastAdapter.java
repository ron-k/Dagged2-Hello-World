package com.example.ronkassay_for_crossover.weather.display;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ronkassay_for_crossover.databinding.WeatherDisplayForecastItemBinding;
import com.example.ronkassay_for_crossover.weather.WeatherDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABiS on 2017-04-01.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private final List<WeatherDatum> forecastList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WeatherDisplayForecastItemBinding binding = WeatherDisplayForecastItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setWeatherDatum(forecastList.get(position));
    }

    public void setData(List<WeatherDatum> data) {
        forecastList.clear();
        forecastList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final WeatherDisplayForecastItemBinding binding;

        ViewHolder(WeatherDisplayForecastItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
