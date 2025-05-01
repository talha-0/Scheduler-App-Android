package com.example.schedulerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleFragment extends Fragment {
    private EditText titleInput, descInput;
    private Button dateTimeBtn, saveBtn;
    private TextView selectedDateTime;
    private ListView taskListView;

    private String pickedDateTime = "";
    private TaskDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        titleInput = view.findViewById(R.id.titleInput);
        descInput = view.findViewById(R.id.descInput);
        dateTimeBtn = view.findViewById(R.id.pickDateTimeBtn);
        selectedDateTime = view.findViewById(R.id.selectedDateTime);
        saveBtn = view.findViewById(R.id.saveBtn);
        taskListView = view.findViewById(R.id.taskList);

        dbHelper = new TaskDatabaseHelper(getContext());

        dateTimeBtn.setOnClickListener(v -> pickDateTime());
        saveBtn.setOnClickListener(v -> saveTask());

        loadUpcomingTasks();

        return view;
    }

    private void pickDateTime() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view, year, month, day) -> {
            TimePickerDialog timePicker = new TimePickerDialog(getContext(), (timeView, hour, minute) -> {
                calendar.set(year, month, day, hour, minute);
                pickedDateTime = DateFormat.format("yyyy-MM-dd HH:mm", calendar).toString();
                selectedDateTime.setText(pickedDateTime);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePicker.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }

    private void saveTask() {
        String title = titleInput.getText().toString();
        String desc = descInput.getText().toString();

        if (title.isEmpty() || pickedDateTime.isEmpty()) {
            Toast.makeText(getContext(), "Enter title and date/time", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO tasks (title, description, datetime, status) VALUES (?, ?, ?, ?)",
                new Object[]{title, desc, pickedDateTime, "upcoming"});

        titleInput.setText("");
        descInput.setText("");
        selectedDateTime.setText("");
        pickedDateTime = "";

        loadUpcomingTasks();
    }

    private void loadUpcomingTasks() {
        ArrayList<String> taskTitles = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE datetime > datetime('now') ORDER BY datetime ASC", null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String datetime = cursor.getString(cursor.getColumnIndexOrThrow("datetime"));
            taskTitles.add("🕒 " + datetime + "\n" + title);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, taskTitles);
        taskListView.setAdapter(adapter);
        cursor.close();
    }
}
