package com.example.mealtracker.ui.main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.Adapter.IngredientRecyclerViewAdapter;
import com.example.mealtracker.ui.main.IngredientItem;
import com.example.mealtracker.ui.main.MainActivity;

import java.util.ArrayList;

public class IngredientFragment extends Fragment implements SearchView.OnQueryTextListener {

    private final ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    private IngredientRecyclerViewAdapter adapter;

    public IngredientFragment() {
        // Required empty public constructor
    }

    public static IngredientFragment newInstance(String param1, String param2) {
        IngredientFragment fragment = new IngredientFragment();
        return fragment;
    }

    private void initRecyclerView(View view){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewIngredients);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new IngredientRecyclerViewAdapter(MainActivity.mIngredients);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        android.widget.SearchView editSearch = view.findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);
        initRecyclerView(view);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }
}