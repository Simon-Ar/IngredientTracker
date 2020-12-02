package com.example.mealtracker.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealtracker.Meal;
import com.example.mealtracker.MealList;
import com.example.mealtracker.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MealsFragment extends Fragment {
    protected static final String ACTIVITY_NAME = "MealList Activity";
    ArrayList<Meal> meals = new ArrayList<Meal>();
    MealList mealList = new MealList(new Hashtable<String, Meal>());
    ListView meal_list_view;
    MealAdapter meal_adapter;

    public static MealsFragment newInstance() {
        return new MealsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.meals_fragment, container, false);

        meal_adapter = new MealAdapter(getContext());
        meal_list_view = (ListView) result.findViewById(R.id.meal_list_view);
        meal_list_view.setAdapter(meal_adapter);

        Hashtable<String, Meal> meal_table = new Hashtable<String, Meal>();

        String meal_1_name = "Chicken Soup";
        String meal_2_name = "Macaroni and Cheese";

        int time_1 = 90;
        int time_2 = 30;

        int size_1 = 6;
        int size_2 = 4;

        Hashtable<Integer, String> recipe_1 = new Hashtable<Integer, String>();
        Hashtable<Integer, String> recipe_2 = new Hashtable<Integer, String>();

        recipe_1.put(1, "Fill large pot with water.");
        recipe_1.put(2, "Add chicken bones, chicken meat, carrots, celery, and onions to water.");
        recipe_1.put(3, "Let the water boil for 1 hour.");
        recipe_1.put(4, "Remove the meat and bones after 1 hour.");
        recipe_1.put(5, "Strain liquid and re-insert into pot.");
        recipe_1.put(6, "Add salt and pepper to taste.");

        recipe_2.put(1, "Boil the macaroni in lightly salted water for 6 minutes.");
        recipe_2.put(2, "Drain the macaroni and let rest.");
        recipe_2.put(3, "Heat up a stick of butter in a pan until melted.");
        recipe_2.put(4, "Add cream to butter once melted.");
        recipe_2.put(5, "Mix the cream with butter and let it simmer.");
        recipe_2.put(6, "Add grated cheddar cheese and black pepper.");
        recipe_2.put(7, "Mix until the sauce can coat the back of a spoon.");
        recipe_2.put(8, "In a casserole pan, add the macaroni and the sauce and mix well.");
        recipe_2.put(9, "Top the mixture with shredded cheddar cheese.");
        recipe_2.put(9, "Bake in an oven at 300F until top is golden brown.");
        recipe_2.put(10, "Let cool for 10 minutes before serving.");

        Meal meal_1 = new Meal(meal_1_name, time_1, size_1, recipe_1);
        Meal meal_2 = new Meal(meal_2_name, time_2, size_2, recipe_2);

        meal_table.put(meal_1_name, meal_1);
        meal_table.put(meal_2_name, meal_2);

        add_meals(meal_table);

        meal_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = getLayoutInflater();
                View custom_view = inflater.inflate(R.layout.meal_dialog, null);

                Meal selected_meal = meals.get(position);

                ArrayList<String> recipe_strings = new ArrayList<String>();

                List<Integer> keys = Collections.list(selected_meal.getRecipe().keys());
                Collections.sort(keys);

                for (Integer key: keys) {
                    recipe_strings.add("Step " + key + ": " + selected_meal.getRecipe().get(key));
                }

                for (String step : recipe_strings) {
                    Log.i(ACTIVITY_NAME, step);
                }

                ArrayAdapter<String> recipe_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, recipe_strings);
                ListView recipe_list = (ListView) custom_view.findViewById(R.id.dialog_meal_recipe);
                recipe_list.setAdapter(recipe_adapter);

                TextView dialog_meal_name = (TextView) custom_view.findViewById(R.id.dialog_meal_name);
                TextView dialog_meal_time = (TextView) custom_view.findViewById(R.id.dialog_meal_time);
                TextView dialog_meal_size = (TextView) custom_view.findViewById(R.id.dialog_meal_size);

                String dialog_name_text = "<b><u>" + selected_meal.getName() + "</u></b>";

                dialog_meal_name.setText(Html.fromHtml(dialog_name_text));
                dialog_meal_time.setText(Integer.toString(selected_meal.getTime()));
                dialog_meal_size.setText(Integer.toString(selected_meal.getSize()));

                AlertDialog.Builder custom_builder = new AlertDialog.Builder(getActivity());
                custom_builder.setView(custom_view)
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        return result;
    }

    public void add_meals(Hashtable<String, Meal> meal_table) {
        Set<String> keys = meal_table.keySet();
        for (String key: keys) {
            meals.add(meal_table.get(key));
        }
        meal_adapter.notifyDataSetChanged();
    }

    private class MealAdapter extends ArrayAdapter<Meal> {
        public MealAdapter(@NonNull Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return meals.size();
        }

        public Meal getItem(int position) {
            return meals.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.meal_layout, null);
            TextView meal_name = (TextView) result.findViewById(R.id.meal_name);
            String meal_name_bold = "<b><u>" + getItem(position).getName()+ "</u></b>";
            meal_name.setText(Html.fromHtml(meal_name_bold));
            return result;
        }
    }
}