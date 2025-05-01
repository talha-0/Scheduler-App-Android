package com.example.schedulerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeUtil {
    public static void applyTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        boolean dark = prefs.getBoolean("dark_mode", false);
        if (dark) {
            context.setTheme(R.style.Theme_SchedulerApp_Dark);
        } else {
            context.setTheme(R.style.Theme_SchedulerApp);
        }
    }
}
