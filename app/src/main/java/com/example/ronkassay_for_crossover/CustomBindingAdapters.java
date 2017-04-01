package com.example.ronkassay_for_crossover;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by ABiS on 2017-04-01.
 */

public class CustomBindingAdapters {

    @BindingAdapter("bind:formattedDate")
    public static void format(@NonNull TextView textView, @Nullable Date date) {
        String text;
        if (date == null) {
            text = "";
        } else {
            java.text.DateFormat dateTimeInstance = java.text.DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
            text = dateTimeInstance.format(date);
        }
        textView.setText(text);
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(@NonNull ImageView imageView, @Nullable String url) {
        if (url != null) {
            Picasso picasso = Application.getInstance().getApplicationComponent().imageRetriever();
            picasso.load(url).into(imageView);
        }
    }
}
