package com.example.mealtracker.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.Fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        MainFragment mainFragment = new MainFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frmMain,mainFragment)
                .commit();
    }
}