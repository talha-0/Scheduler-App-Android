package com.example.schedulerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "tasks.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "tasks";

    public TaskDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "description TEXT," +
                "datetime TEXT," +
                "status TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
