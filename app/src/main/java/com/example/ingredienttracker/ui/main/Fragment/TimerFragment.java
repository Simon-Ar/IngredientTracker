package com.example.ingredienttracker.ui.main.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class TimerFragment extends Fragment {

    int length;
    private FloatingActionButton mFab;
    private AlertDialog alertDialog;
    private DatePickerDialog picker;
    private TimerRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public TimerFragment(int ingredient_length) {
        this.length = ingredient_length;
    }

    public static TimerFragment newInstance(int size) {
        return new TimerFragment(size);
    }


    private void initRecyclerView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView = view.findViewById(R.id.recyclerViewTimers);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimerRecyclerViewAdapter(MainActivity.mIngredients, length);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TimerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                new DataRequestDelete().execute(MainActivity.mIngredients.get(position));
                MainActivity.mIngredients.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeRemoved(position, 1);

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

    public void addListeners(Context ctx) {
        mFab.setOnClickListener(v -> {
            Calendar today = Calendar.getInstance();
            AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View dialogView = layoutInflater.inflate(R.layout.input_dialog, null);
            alert.setView(dialogView);
            alert.setTitle("Add Ingredient");
            Calendar c = Calendar.getInstance();
            TextView date = dialogView.findViewById(R.id.date);
            date.setText(String.format("%d/%d/%d",
                    today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH)));
            TextView name = dialogView.findViewById(R.id.inputName);
            name.setText("Chicken");
            Button mBtnCreate = dialogView.findViewById(R.id.btnCreate);
            Button mBtnCancel = dialogView.findViewById(R.id.btnCancel);
            alertDialog = alert.create();
            alertDialog.show();

            date.setOnClickListener(v12 -> {
                picker = new DatePickerDialog(ctx, (view, year, month, dayOfMonth) -> {
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    date.setText(String.format("%d/%d/%d",
                            c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                picker.show();
            });

            mBtnCreate.setOnClickListener(v13 -> {
                IngredientItem newIngredient = new IngredientItem(today, c, name.getText().toString());
                new DataRequestCreate().execute(newIngredient);
                MainActivity.mIngredients.add(newIngredient);
                Collections.sort(MainActivity.mIngredients);
                int index = MainActivity.mIngredients.indexOf(newIngredient);
                updateLength();
                adapter = new TimerRecyclerViewAdapter(MainActivity.mIngredients, length);
                recyclerView.setAdapter(adapter);
                alertDialog.dismiss();
            });

            mBtnCancel.setOnClickListener(v1 -> alertDialog.dismiss());

        });

    }

    public void updateLength() {
        this.length = MainActivity.mIngredients.size();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.timer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class DataRequestDelete extends AsyncTask<IngredientItem, Integer, String> {

        @Override
        protected String doInBackground(IngredientItem... items) {
            OkHttpClient client = new OkHttpClient();
            try {
                URL url = new URL("http://192.168.1.181:8080/api/v1/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
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


    public class DataRequestCreate extends AsyncTask<IngredientItem, Integer, String> {

        @Override
        protected String doInBackground(IngredientItem... items) {
            OkHttpClient client = new OkHttpClient();
            try {
                URL url = new URL("http://192.168.1.181:8080/api/v1/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                String entered = items[0].toString().split(" ")[2].substring(0, 10);
                String expiry = items[0].toString().split(" ")[4].substring(0, 10);
                String s = "{\"user_id\":\"" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "\",\"ingredients\":[{\"name\":\"" + items[0].getName() + "\"," + "\"entered\":\"" + entered + "\"," + "\"expiry\":\"" + expiry + "\"}]}";
                JSONObject obj = new JSONObject(s);
                Log.i("info", obj.toString());
                Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), obj.toString())).build();
                client.newCall(request).execute();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return "Nothing";
        }
    }
}