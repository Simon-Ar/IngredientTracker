package com.example.mealtracker.ui.main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealtracker.R;

import java.util.ArrayList;
import java.util.Date;

public class TimerRecyclerViewAdapter extends RecyclerView.Adapter<TimerRecyclerViewAdapter.TimerViewHolder> {

    //ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    ArrayList<String> mNames = new ArrayList<>();
    ArrayList<Date> mDates = new ArrayList<>();

    @NonNull
    @Override
    public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_timer_home,parent,false);
        TimerViewHolder tvh = new TimerViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TimerViewHolder holder, int position) {

    }

    public void onBindViewHolder(@NonNull TimerRecyclerViewAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TimerViewHolder extends RecyclerView.ViewHolder{



        public TimerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
