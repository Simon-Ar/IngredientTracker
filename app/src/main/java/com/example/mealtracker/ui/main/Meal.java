package com.example.mealtracker.ui.main;

import java.util.Hashtable;

public class Meal {
    String meal_name;
    int time_to_cook;
    int serving_size;
    Hashtable<Integer, String> recipe;

    public Meal(String name, int time, int size, Hashtable<Integer, String> recipe) {
        this.meal_name = name;
        this.time_to_cook = time;
        this.serving_size = size;
        this.recipe = recipe;
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
}
