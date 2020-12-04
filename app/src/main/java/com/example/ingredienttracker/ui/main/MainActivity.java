package com.example.ingredienttracker.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ingredienttracker.R;
import com.example.ingredienttracker.ui.main.Fragment.IngredientFragment;
import com.example.ingredienttracker.ui.main.Fragment.MainFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    private AnimatedBottomBar bottomBar;
    private final MainFragment mainFragment = new MainFragment();
    private Fragment current;
    private final String TAG = "Main Activity";
    private ArrayList<AnimatedBottomBar.Tab> tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Calendar today = Calendar.getInstance();
        bottomBar = findViewById(R.id.navBar);
        FragmentManager fm = getSupportFragmentManager();
        Calendar tomorrow = Calendar.getInstance();
        tabs = bottomBar.getTabs();

        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }

            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                if (tab1 == tabs.get(0)) {
                    MainFragment mainFragment = new MainFragment();
                    fm.beginTransaction()
                            .remove(current)
                            .replace(R.id.frmMain, mainFragment)
                            .commit();
                    current = mainFragment;
                } else if (tab1 == tabs.get(1)) {
                    IngredientFragment ingredientFragment = new IngredientFragment();
                    fm.beginTransaction()
                            .remove(current)
                            .replace(R.id.frmMain, ingredientFragment)
                            .commit();
                    current = ingredientFragment;
                } else if (tab1 == tabs.get(2)) {
                    SupportMapFragment mapFragment = new SupportMapFragment();
                    mapFragment.getMapAsync(MainActivity.this);
                    fm.beginTransaction()
                            .remove(current)
                            .replace(R.id.frmMain, mapFragment)
                            .commit();
                    current = mapFragment;
                } else if (tab1 == tabs.get(3)) {
                    MealsFragment mealsFragment = new MealsFragment();
                    fm.beginTransaction()
                            .remove(current)
                            .replace(R.id.frmMain, mealsFragment)
                            .commit();
                    current = mealsFragment;
                } else if (tab1 == tabs.get(4)) {
                    TimerFragment timerFragment = new TimerFragment(mIngredients.size());
                    fm.beginTransaction()
                            .remove(current)
                            .replace(R.id.frmMain, timerFragment)
                            .commit();
                    current = timerFragment;
                }
            }
        });

        tomorrow.add(Calendar.DATE, 1);
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Chicken")
        );
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Carrot")
        );
        tomorrow.add(Calendar.DATE, 2);
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Beef")
        );
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Celery")
        );
        tomorrow.add(Calendar.DATE, 3);
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Onion")
        );
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Spinach")
        );
        tomorrow.add(Calendar.DATE, 2);
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Pork")
        );
        mIngredients.add(
                new IngredientItem(today, tomorrow, "Fish")
        );
        mIngredients.sort(IngredientItem::compareTo);
        fm.beginTransaction()
                .replace(R.id.frmMain, mainFragment)
                .commit();
        current = mainFragment;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO add more interactivity to the map
        GoogleMapOptions options = new GoogleMapOptions()
                .liteMode(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}