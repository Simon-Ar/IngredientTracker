package com.example.ingredienttracker.ui.main.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredienttracker.R;
import com.example.ingredienttracker.ui.main.Adapter.IngredientRecyclerViewAdapter;
import com.example.ingredienttracker.ui.main.IngredientItem;
import com.example.ingredienttracker.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IngredientFragment extends Fragment implements SearchView.OnQueryTextListener {

    private final ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    private IngredientRecyclerViewAdapter adapter;

    public IngredientFragment() {
        // Required empty public constructor
    }

    public static IngredientFragment newInstance() {
        IngredientFragment fragment = new IngredientFragment();
        return fragment;
    }

    private void initRecyclerView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewIngredients);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new IngredientRecyclerViewAdapter(MainActivity.mIngredients);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new IngredientRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                new DataRequest().execute(MainActivity.mIngredients.get(position));
                MainActivity.mIngredients.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.resetList(MainActivity.mIngredients);
            }

            @Override
            public void onDetailClick(int position) {

            }
        });
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

    public class DataRequest extends AsyncTask<IngredientItem, Integer, String> {

        @Override
        protected String doInBackground(IngredientItem... items) {
            OkHttpClient client = new OkHttpClient();
            try {
                URL url = new URL("https://evening-falls-76333.herokuapp.com/api/v1/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                String entered = items[0].toString().split(" ")[2].substring(0, 10);
                String expiry = items[0].toString().split(" ")[4].substring(0, 10);
                String s = "{\"user_id\":\"" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "\",\"ingredients\":[{\"name\":\"" + items[0].getName() + "\"," + "\"entered\":\"" + entered + "\"," + "\"expiry\":\"" + expiry + "\"}]}";
                JSONObject obj = new JSONObject(s);
                Log.i("info", obj.toString());
                Request request = new Request.Builder().url(url).delete(RequestBody.create(MediaType.get("application/json; charset=utf-8"), obj.toString())).build();
                client.newCall(request).execute();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return "Nothing";
        }
    }

}