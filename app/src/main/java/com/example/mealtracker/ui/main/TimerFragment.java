package com.example.mealtracker.ui.main;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.Fragment.MealsHomeFragment;
import com.example.mealtracker.ui.main.Fragment.TimerHomeFragment;

public class TimerFragment extends Fragment {
    View timer_fragment;
    int length;

    public TimerFragment(int ingredient_length) {
        this.length = ingredient_length;
    }

    public static TimerFragment newInstance(int size) {
        return new TimerFragment(size);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        timer_fragment =  inflater.inflate(R.layout.timer_fragment, container, false);
        TimerHomeFragment timerHomeFragment = new TimerHomeFragment(length);
        timerHomeFragment.setArguments(getArguments());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.timer_frame,timerHomeFragment);
        ft.commit();


        return timer_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}