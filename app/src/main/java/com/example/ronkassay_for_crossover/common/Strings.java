package com.example.ronkassay_for_crossover.common;

import android.support.annotation.Nullable;

/**
 * Created by ABiS on 2017-04-01.
 */

public class Strings {
    private Strings() {
        //non instantiable
    }

    public static boolean isEmptyOrNull(@Nullable CharSequence string) {
        return string == null || string.length() == 0;
    }
}
