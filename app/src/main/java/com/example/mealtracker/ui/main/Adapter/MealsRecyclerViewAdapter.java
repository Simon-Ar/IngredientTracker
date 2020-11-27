package com.example.mealtracker.ui.main.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealtracker.R;

import java.util.ArrayList;

public class MealsRecyclerViewAdapter extends RecyclerView.Adapter<MealsRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImgURL = new ArrayList<>();
    private Context mContext;

    public MealsRecyclerViewAdapter(ArrayList<String> mNames, ArrayList<String> mImgURL, Context mContext) {
        this.mNames = mNames;
        this.mImgURL = mImgURL;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_meals,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mImgURL.get(position))
                .into(holder.mealsImg);

        holder.mealsName.setText(mNames.get(position));

        holder.mealsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on an image: "+mNames.get(position));
                Toast.makeText(mContext,"Image works",Toast.LENGTH_SHORT);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealsImg;
        TextView mealsName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealsImg = itemView.findViewById(R.id.mealItemImg);
            mealsName = itemView.findViewById(R.id.mealItemName);
        }
    }
}
