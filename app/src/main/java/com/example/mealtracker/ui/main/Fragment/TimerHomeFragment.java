package com.example.mealtracker.ui.main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.Adapter.TimerRecyclerViewAdapter;
import com.example.mealtracker.ui.main.IngredientItem;
import com.example.mealtracker.ui.main.MainActivity;

import java.util.ArrayList;

public class TimerHomeFragment extends Fragment {

    private final ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    int limit;

    public TimerHomeFragment(int limit) {
        this.limit = limit;
    }

    public static TimerHomeFragment newInstance(String param1, String param2, int max) {
        TimerHomeFragment fragment = new TimerHomeFragment(max);
        return fragment;
    }

    private void initRecyclerView(View view){
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTimers);
        recyclerView.setLayoutManager(layoutManager);
        TimerRecyclerViewAdapter adapter = new TimerRecyclerViewAdapter(MainActivity.mIngredients, limit);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
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