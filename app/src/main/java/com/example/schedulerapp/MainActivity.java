package com.example.schedulerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private final String[] tabTitles = {"Schedule", "Past", "Notify", "Profile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load theme from SharedPreferences if needed (for profile tab dark mode)
        ThemeUtil.applyTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Set the ViewPager Adapter
        viewPager2.setAdapter(new ViewPagerAdapter(this));

        // Attach tabs
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();

    }
}
