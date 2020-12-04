package com.example.ingredienttracker.ui.main.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredienttracker.R;
import com.example.ingredienttracker.ui.main.Adapter.MealsRecyclerViewAdapter;

import java.util.ArrayList;

public class MealsHomeFragment extends Fragment {
    private final ArrayList<String> mNames = new ArrayList<>();
    private final ArrayList<String> mImgURL = new ArrayList<>();

    private void getImages(){
        mImgURL.add("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimages.media-allrecipes.com%2Fuserphotos%2F5762688.jpg");
        mNames.add("No Fail Pie Crust I");

        mImgURL.add("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimages.media-allrecipes.com%2Fuserphotos%2F7058432.jpg");
        mNames.add("Homemade Whipped Cream");

        mImgURL.add("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimages.media-allrecipes.com%2Fuserphotos%2F1077113.jpg");
        mNames.add("Snowballs II");

        mImgURL.add("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimages.media-allrecipes.com%2Fuserphotos%2F8559802.jpg");
        mNames.add("Super Easy Polish Cabbage Rolls");

        initRecyclerView(getView());

    }

    private void initRecyclerView(View view){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMeals);
        recyclerView.setLayoutManager(layoutManager);
        MealsRecyclerViewAdapter adapter = new MealsRecyclerViewAdapter(mNames,mImgURL,getContext());
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getImages();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}