package com.example.mealtracker.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

                alert.setTitle("Title");
                alert.setMessage("Message");
                final EditText input = new EditText(MainActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        Log.i("Main Activity", this.toString());
        tomorrow.add(Calendar.DATE,1);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Chicken")
        );
        tomorrow.add(Calendar.DATE,2);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Beef")
        );
        tomorrow.add(Calendar.DATE,3);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Onion")
        );
        tomorrow.add(Calendar.DATE,2);
        mIngredients.add(
                new IngredientItem(today,tomorrow,"Pork")
        );

        mIngredients.sort(IngredientItem::compareTo);

        MainFragment mainFragment = new MainFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frmMain,mainFragment)
                .commit();

    }
}