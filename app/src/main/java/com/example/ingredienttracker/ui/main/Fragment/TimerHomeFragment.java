package com.example.ingredienttracker.ui.main.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredienttracker.R;
import com.example.ingredienttracker.ui.main.Adapter.TimerRecyclerViewAdapter;
import com.example.ingredienttracker.ui.main.IngredientItem;
import com.example.ingredienttracker.ui.main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class TimerHomeFragment extends Fragment {

    private final ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    int limit;
    private FloatingActionButton mFab;
    private AlertDialog alertDialog;
    private DatePickerDialog picker;
    private TimerRecyclerViewAdapter adapter;
    private boolean home;
    private RecyclerView recyclerView;

    public TimerHomeFragment(int limit, boolean home) {
        this.limit = limit;
    }


    private void initRecyclerView(View view){
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView = view.findViewById(R.id.recyclerViewTimers);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimerRecyclerViewAdapter(MainActivity.mIngredients, limit);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TimerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                MainActivity.mIngredients.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeRemoved(position,1);

            }

            @Override
            public void onDetailsClick(int position) {

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFab = view.findViewById(R.id.addFav);
        addListeners(view.getContext());
        initRecyclerView(view);
    }

    public void addListeners(Context ctx){
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar today = Calendar.getInstance();
                AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                View dialogView= layoutInflater.inflate(R.layout.input_dialog,null);
                alert.setView(dialogView);
                alert.setTitle("Add Ingredient");
                Calendar c = Calendar.getInstance();
                TextView date = dialogView.findViewById(R.id.date);
                date.setText(String.format("%d/%d/%d",
                        today.get(Calendar.YEAR),today.get(Calendar.MONTH)+1,today.get(Calendar.DAY_OF_MONTH)));
                TextView name = dialogView.findViewById(R.id.inputName);
                name.setText("Chicken");
                Button mBtnCreate = dialogView.findViewById(R.id.btnCreate);
                Button mBtnCancel = dialogView.findViewById(R.id.btnCancel);
                alertDialog = alert.create();
                alertDialog.show();

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picker = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                c.set(Calendar.YEAR,year);
                                c.set(Calendar.MONTH,month);
                                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                date.setText(String.format("%d/%d/%d",
                                        c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH)));
                            }
                        },today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH));
                        picker.show();
                    }
                });

                mBtnCreate.setOnClickListener(v1 -> {
                    IngredientItem newIngredient = new IngredientItem(today,c,name.getText().toString());
                    MainActivity.mIngredients.add(newIngredient);
                    Collections.sort(MainActivity.mIngredients);
                    int index = MainActivity.mIngredients.indexOf(newIngredient);
                    updateLength();
                    adapter = new TimerRecyclerViewAdapter(MainActivity.mIngredients, limit);
                    recyclerView.setAdapter(adapter);
                    alertDialog.dismiss();
                });

                mBtnCancel.setOnClickListener(v12 -> alertDialog.dismiss());

            }
        });
    }

    public void updateLength(){
        if (!home)
            this.limit = MainActivity.mIngredients.size();
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