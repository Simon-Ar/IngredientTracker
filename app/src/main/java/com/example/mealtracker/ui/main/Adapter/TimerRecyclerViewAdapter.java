package com.example.mealtracker.ui.main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.IngredientItem;

import java.util.ArrayList;

public class TimerRecyclerViewAdapter extends RecyclerView.Adapter<TimerRecyclerViewAdapter.TimerViewHolder> {

    ArrayList<IngredientItem> mIngredients = new ArrayList<>();

    public TimerRecyclerViewAdapter(ArrayList<IngredientItem> mIngredients) {
        this.mIngredients = mIngredients;
    }


    @NonNull
    @Override
    public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_timer_home,parent,false);
        TimerViewHolder tvh = new TimerViewHolder(v);
        return tvh;
    }

    public void onBindViewHolder(@NonNull TimerViewHolder holder, int position) {
        mIngredients.get(position);



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TimerViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout mTitleBar;
        public LinearLayout mCard;
        public TextView mName;
        public ImageView mIcon;
        public TextView mDays;
        public  TextView mHours;

        public TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleBar = itemView.findViewById(R.id.timerTitleCard);
            mCard = itemView.findViewById(R.id.timerCard);
            mName = itemView.findViewById(R.id.ingredientName);
            mIcon = itemView.findViewById(R.id.ingredientIcon);
            mDays = itemView.findViewById(R.id.timerDays);
            mHours = itemView.findViewById(R.id.timerHours);

        }

    }
}
