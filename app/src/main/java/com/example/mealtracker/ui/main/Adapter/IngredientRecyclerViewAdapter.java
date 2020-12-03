package com.example.mealtracker.ui.main.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.IngredientItem;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.IngredientViewHolder> {

    ArrayList<IngredientItem> mIngredients;
    ArrayList<IngredientItem> ingredientList = new ArrayList<>();

    public IngredientRecyclerViewAdapter(ArrayList<IngredientItem> mIngredients) {
        this.mIngredients = mIngredients;
        ingredientList.addAll(mIngredients);
    }


    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingredient,parent,false);
        IngredientViewHolder ivh = new IngredientViewHolder(v);
        return ivh;
    }

    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientItem mIngredient  = ingredientList.get(position);
        Context ctx = holder.itemView.getContext();
        long days = mIngredient.getTimeLeft()[0];
        Log.i("Timer Recycler Adapter",""+days);
        if (days>=6){
            holder.mCard.setBackgroundColor(ContextCompat.getColor(ctx,R.color.card_green));
        } else if (days>=3){
            holder.mCard.setBackgroundColor(ContextCompat.getColor(ctx,R.color.card_yellow));
        } else{
            holder.mCard.setBackgroundColor(ContextCompat.getColor(ctx,R.color.card_red));
       }
        DateTime entered = mIngredient.getEntered();
        holder.mName.setText(mIngredient.getName());
        holder.mEntered.setText(entered.getYear()+"/"+entered.getMonthOfYear()+"/"+entered.getDayOfMonth());
        holder.mDays.setText(""+mIngredient.getTimeLeft()[0]);
        holder.mHours.setText(""+mIngredient.getTimeLeft()[1]);

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public Filter getFilter() {
        return ingredientFilter;
    }
    private final Filter ingredientFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<IngredientItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mIngredients);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (IngredientItem item : mIngredients) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ingredientList.clear();
            ingredientList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    public static class IngredientViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout mCard;
        public TextView mName;
        public TextView mDays;
        public TextView mHours;
        public TextView mEntered;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.ingredientCard);
            mName = itemView.findViewById(R.id.ingredientName);
            mEntered = itemView.findViewById(R.id.ingredientAddedDate);
            mDays = itemView.findViewById(R.id.timerDays);
            mHours = itemView.findViewById(R.id.timerHours);

        }

    }
}
