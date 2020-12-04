package com.example.ingredienttracker.ui.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class Meal {
    String meal_name;
    int time_to_cook;
    int serving_size;
    Hashtable<Integer, String> recipe;
    ArrayList<IngredientItem> ingredients;

    public Meal(String name, int time, int size, Hashtable<Integer, String> recipe, ArrayList<IngredientItem> ingredients) {
        this.meal_name = name;
        this.time_to_cook = time;
        this.serving_size = size;
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    public String getName() {
        return this.meal_name;
    }

    public int getTime() {
        return this.time_to_cook;
    }

    public int getSize() {
        return this.serving_size;
    }

    public Hashtable<Integer, String> getRecipe() {
        return this.recipe;
    }

    public ArrayList<String> getRecipeSteps() {
        ArrayList<String> recipe_strings = new ArrayList<String>();
        List<Integer> keys = Collections.list(this.recipe.keys());
        Collections.sort(keys);

        for (Integer key: keys) {
            recipe_strings.add("Step " + key + ": " + this.recipe.get(key));
        }

        return recipe_strings;
    }

    public String getStep(int key) { return this.recipe.get(key); }

    public ArrayList<IngredientItem> getIngredients() { return this.ingredients; }

    public Boolean hasIngredient(String name) {
        for (IngredientItem ingredient : this.ingredients) {
            if (name.equalsIgnoreCase(ingredient.getName())) return true;
        }
        return false;
    }

    public ArrayList<String> getIngredientNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (IngredientItem ingredient : this.ingredients) {
            names.add(ingredient.getName());
        }

        Collections.sort(names);

        return names;
    }

    public void setName(String name) {
        this.meal_name = name;
    }

    public void setTime(int time) {
        this.time_to_cook = time;
    }

    public void setSize(int size) {
        this.serving_size = size;
    }

    public void setRecipe(Hashtable<Integer, String> recipe) {
        this.recipe = recipe;
    }

    public void replaceStep(int key, String step) {
        this.recipe.replace(key, step);
    }

    public void addStep(int key, String step) {
        this.recipe.put(key, step);
    }

    public void removeStep(int key) {
        this.recipe.remove(key);
    }

    public void setIngredients(ArrayList<IngredientItem> ingredients) { this.ingredients = ingredients; }

    public void addIngredient(IngredientItem item) { this.ingredients.add(item); }
}
