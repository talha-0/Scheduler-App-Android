package com.example.schedulerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotificationDbHelper extends SQLiteOpenHelper {
    public NotificationDbHelper(Context context) {
        super(context, "notifications.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notifications (id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, datetime TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notifications");
        onCreate(db);
    }

    public void insertNotification(String msg) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO notifications (message, datetime) VALUES (?, datetime('now'))", new Object[]{msg});
    }
}
