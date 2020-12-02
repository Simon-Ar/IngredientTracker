package com.example.mealtracker.ui.main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mealtracker.R;

public class MainFragment extends Fragment {

    View myFragment;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myFragment =  inflater.inflate(R.layout.main_fragment, container, false);
        MealsHomeFragment mealsHomeFragment = new MealsHomeFragment();
        TimerHomeFragment timerHomeFragment = new TimerHomeFragment();
        timerHomeFragment.setArguments(getArguments());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frmHomeMeals,mealsHomeFragment);
                ft.replace(R.id.frmHomeTimer,timerHomeFragment);
                ft.commit();


        return myFragment;
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