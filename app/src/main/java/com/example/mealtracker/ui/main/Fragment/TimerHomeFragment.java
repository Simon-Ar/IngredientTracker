package com.example.mealtracker.ui.main.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealtracker.R;

public class TimerHomeFragment extends Fragment {

    public TimerHomeFragment() {
        // Required empty public constructor
    }

    public static TimerHomeFragment newInstance(String param1, String param2) {
        TimerHomeFragment fragment = new TimerHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer_home, container, false);
    }
}