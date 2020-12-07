package com.example.ingredienttracker.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ingredienttracker.R;
import com.example.ingredienttracker.ui.main.Fragment.IngredientFragment;
import com.example.ingredienttracker.ui.main.Fragment.MainFragment;
import com.example.ingredienttracker.ui.main.Fragment.MealsFragment;
import com.example.ingredienttracker.ui.main.Fragment.TimerHomeFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nl.joery.animatedbottombar.AnimatedBottomBar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    private AnimatedBottomBar bottomBar;
    private MaterialToolbar toolbar;
    private final MainFragment mainFragment = new MainFragment();
    public Fragment current;
    private final String TAG = "Main Activity";
    private ArrayList<AnimatedBottomBar.Tab> tabs;
    private final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Calendar today = Calendar.getInstance();
        bottomBar = findViewById(R.id.navBar);
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
                } else if (tab1 == tabs.get(4)) {
                    MealsFragment mealsFragment = new MealsFragment();
                    fm.beginTransaction()
                            .remove(current)
                            .replace(R.id.frmMain, mealsFragment)
                            .commit();
                    current = mealsFragment;
                } else if (tab1 == tabs.get(3)) {
                    TimerHomeFragment timerFragment = new TimerHomeFragment(mIngredients.size(),false);
                    fm.beginTransaction()
                            .remove(current)
                            .replace(R.id.frmMain, timerFragment)
                            .commit();
                    current = timerFragment;
                }
            }
        });

        tomorrow.add(Calendar.DATE, 1);
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Chicken")
//        );
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Carrot")
//        );
//        tomorrow.add(Calendar.DATE, 2);
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Beef")
//        );
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Celery")
//        );
//        tomorrow.add(Calendar.DATE, 3);
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Onion")
//        );
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Spinach")
//        );
//        tomorrow.add(Calendar.DATE, 2);
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Pork")
//        );
//        mIngredients.add(
//                new IngredientItem(today, tomorrow, "Fish")
//        );

        try {
            new DataRequest().execute(new URL("http://192.168.1.181:8080/api/v1/users/123"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mIngredients.sort(IngredientItem::compareTo);
        fm.beginTransaction()
                .replace(R.id.frmMain, mainFragment)
                .commit();
        current = mainFragment;
    }

    public void getDetails(int position){
        IngredientFragment ingredientFragment = new IngredientFragment();
        fm.beginTransaction()
                .remove(current)
                .replace(R.id.frmMain, ingredientFragment)
                .commit();
        current = ingredientFragment;
        SearchView searcher = findViewById(R.id.search);
        if (position != 0) {
            searcher.setQuery(mIngredients.get(position).getName(),true);
        }
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

    public class DataRequest extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(urls[0]).build();
            try (Response response = client.newCall(request).execute()) {
                String result = response.body().string();
                JSONArray obj = new JSONArray(result);
                for (int i = 0; i < obj.length(); i++) {
                    JSONObject j = obj.getJSONObject(i);
                    String name = j.getString("name");
                    String entered = j.getString("entered");
                    String expiry = j.getString("expiry");
                    Log.i("test2", name + " " + entered + " " + expiry);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                    Calendar today = Calendar.getInstance();
                    Calendar tomorrow = Calendar.getInstance();

                    today.setTime(sdf.parse(entered));
                    tomorrow.setTime(sdf.parse(expiry));
                    mIngredients.add(new IngredientItem(today, tomorrow, name));
                }
                return result;
            } catch (IOException | JSONException | ParseException e) {
                e.printStackTrace();
            }
            return "Nothing";
        }
    }
}
