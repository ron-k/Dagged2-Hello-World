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
 * Created by Ron Kassay on 2017-04-01.
 */

public class CustomBindingAdapters {

    @BindingAdapter("formattedDate")
    public static void format(@NonNull TextView textView, @Nullable Date date) {
        String text = formatDate(date);
        textView.setText(text);
    }

    @NonNull
    public static String formatDate(@Nullable Date date) {
        String text;
        if (date == null) {
            text = "";
        } else {
            DateFormat dateTimeInstance = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
            text = dateTimeInstance.format(date);
        }
        return text;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(@NonNull ImageView imageView, @Nullable String url) {
        if (url != null) {
            Picasso picasso = Application.getInstance().getApplicationComponent().imageRetriever();
            picasso.load(url).into(imageView);
        }
    }
}
