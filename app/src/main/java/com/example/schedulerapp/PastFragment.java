package com.example.schedulerapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PastFragment extends Fragment {
    private ListView pastTaskList;
    private TaskDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);
        pastTaskList = view.findViewById(R.id.pastTaskList);
        dbHelper = new TaskDatabaseHelper(getContext());

        loadPastTasks();
        return view;
    }

    private void loadPastTasks() {
        ArrayList<String> pastTaskTitles = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE datetime < datetime('now') ORDER BY datetime DESC", null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String datetime = cursor.getString(cursor.getColumnIndexOrThrow("datetime"));
            pastTaskTitles.add("⏳ " + datetime + "\n" + title);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pastTaskTitles);
        pastTaskList.setAdapter(adapter);
        cursor.close();
    }
}
