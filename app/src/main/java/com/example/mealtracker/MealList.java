package com.example.mealtracker;

import java.util.Hashtable;

public class MealList {
    Hashtable<String, Meal> meal_list;

    public MealList(Hashtable<String, Meal> meals) {
        this.meal_list = meals;
    }

    public Hashtable<String, Meal> getMeals() {
        return this.meal_list;
    }

    public void setMeals(Hashtable<String, Meal> meals) {
        this.meal_list = meals;
    }

    public void replaceMeal(String name, Meal meal) {
        this.meal_list.replace(name, meal);
    }

    public void addMeal(String name, Meal meal) {
        this.meal_list.put(name, meal);
    }

    public void removeMeal(String name) {
        this.meal_list.remove(name);
    }
}
