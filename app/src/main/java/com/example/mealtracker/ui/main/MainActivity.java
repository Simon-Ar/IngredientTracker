package com.example.mealtracker.ui.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mealtracker.R;
import com.example.mealtracker.ui.main.Fragment.MainFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<IngredientItem> mIngredients = new ArrayList<>();
    private FloatingActionButton mFab;
    private AlertDialog alertDialog;
    private DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        mFab = findViewById(R.id.addFav);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                View dialogView= layoutInflater.inflate(R.layout.input_dialog,null);
                alert.setView(dialogView);
                alert.setTitle("Add Ingredient");
                Calendar c = Calendar.getInstance();
                TextView date = dialogView.findViewById(R.id.date);
                date.setText(today.get(Calendar.YEAR)+"/"+today.get(Calendar.MONTH)+"/"+today.get(Calendar.DAY_OF_MONTH));
                TextView name = dialogView.findViewById(R.id.inputName);
                name.setText("Chicken");
                Button mBtnCreate = dialogView.findViewById(R.id.btnCreate);
                Button mBtnCancel = dialogView.findViewById(R.id.btnCancel);
                alertDialog = alert.create();
                alertDialog.show();

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                c.set(Calendar.YEAR,year);
                                c.set(Calendar.MONTH,month);
                                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                date.setText(c.get(Calendar.YEAR)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH));
                            }
                        },today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH));
                        picker.show();
                        }
                });

                mBtnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIngredients.add(new IngredientItem(today,c,name.getText().toString()));
                        MainActivity.this.onResume();
                        alertDialog.dismiss();
                    }
                });

                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });


        Log.i("Main Activity", this.toString());
        mIngredients.sort(IngredientItem::compareTo);
        MainFragment mainFragment = new MainFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frmMain,mainFragment)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mIngredients.sort(IngredientItem::compareTo);
        MainFragment mainFragment = new MainFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frmMain,mainFragment)
                .commit();
    }
}