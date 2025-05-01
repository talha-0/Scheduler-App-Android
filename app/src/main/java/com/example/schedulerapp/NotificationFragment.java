package com.example.schedulerapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    private ListView notificationListView;
    private Button addDummyBtn;
    private NotificationDbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListView = view.findViewById(R.id.notificationListView);
        addDummyBtn = view.findViewById(R.id.addDummyNotificationBtn);
        dbHelper = new NotificationDbHelper(getContext());

        addDummyBtn.setOnClickListener(v -> {
            dbHelper.insertNotification("This is a test notification at " + System.currentTimeMillis());
            loadNotifications();
        });

        loadNotifications();
        return view;
    }

    private void loadNotifications() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notifications ORDER BY datetime DESC", null);
        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
            String datetime = cursor.getString(cursor.getColumnIndexOrThrow("datetime"));
            list.add("🔔 " + message + "\n" + datetime);
        }
        cursor.close();
        notificationListView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list));
    }
}
