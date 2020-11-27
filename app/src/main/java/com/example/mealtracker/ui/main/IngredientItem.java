package com.example.mealtracker.ui.main;

import java.util.Calendar;
import java.util.Date;

public class IngredientItem {

    Date entered;
    Date expiry;
    String name;

    public void IngredientItem(String name,String expire){

    }

    public Date getEntered() {
        return entered;
    }

    public Date getExpiry() {
        return expiry;
    }

    public String getName() {
        return name;
    }

    public String getTimeLeft(){
        return null;
    }
}
