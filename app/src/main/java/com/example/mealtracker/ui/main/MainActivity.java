package com.example.mealtracker.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.Fragment.IngredientFragment;
import com.example.mealtracker.ui.main.Fragment.MainFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    private AnimatedBottomBar bottomBar;
    private MainFragment mainFragment = new MainFragment();
    private MealsFragment mealsFragment = new MealsFragment();
    private TimerFragment timerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Calendar today = Calendar.getInstance();
        bottomBar = findViewById(R.id.navBar);
        FragmentManager fm = getSupportFragmentManager();

        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                switch(i){
                    case 1:
                        MainFragment mainFragment = new MainFragment();
                        fm.beginTransaction()
                                .replace(R.id.frmMain,mainFragment)
                                .commit();
                        break;
                    case 0:
                        IngredientFragment ingredientFragment = new IngredientFragment();
                        fm.beginTransaction()
                                .replace(R.id.frmMain,ingredientFragment)
                                .commit();

                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });

        Log.i("Main Activity", this.toString());
        tomorrow.add(Calendar.DATE,1);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Chicken")
        );
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Carrot")
        );
        tomorrow.add(Calendar.DATE,2);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Beef")
        );
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Celery")
        );
        tomorrow.add(Calendar.DATE,3);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Onion")
        );
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Spinach")
        );
        tomorrow.add(Calendar.DATE,2);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Pork")
        );
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Fish")
        );
        mIngredients.sort(IngredientItem::compareTo);
        timerFragment = new TimerFragment(mIngredients.size());
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frmMain, mainFragment)
                .commit();

    }

}