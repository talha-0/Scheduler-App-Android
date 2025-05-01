package com.example.schedulerapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText nameEdit, emailEdit;
    private Switch darkModeSwitch;
    private Button saveBtn;

    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        prefs = requireContext().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);

        nameEdit = view.findViewById(R.id.nameEdit);
        emailEdit = view.findViewById(R.id.emailEdit);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        saveBtn = view.findViewById(R.id.saveProfileBtn);

        // Load saved data
        nameEdit.setText(prefs.getString("name", ""));
        emailEdit.setText(prefs.getString("email", ""));
        darkModeSwitch.setChecked(prefs.getBoolean("dark_mode", false));

        darkModeSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            requireActivity().recreate();
        });

        saveBtn.setOnClickListener(v -> {
            prefs.edit()
                    .putString("name", nameEdit.getText().toString())
                    .putString("email", emailEdit.getText().toString())
                    .apply();
        });

        return view;
    }
}
