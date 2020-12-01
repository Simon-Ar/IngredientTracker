package com.example.mealtracker.ui.main;

import java.util.Calendar;
import java.util.Date;

public class IngredientItem {

    private final String[][] STYLES ={
            {"@drawable/ic_baseline_check_circle_24","@color/card_red","@color/card_title_red"}    ,
            {"@drawable/ic_baseline_check_circle_24","@color/card_yellow","@color/card_title_yellow"},
            {"@drawable/ic_baseline_check_circle_24","@color/card_green","@color/card_title_green"}
    };

    Calendar entered;
    Calendar expiry;
    String name;

    public IngredientItem(Calendar entered, Calendar expiry, String name) {
        this.entered = entered;
        this.expiry = expiry;
        this.name = name;
    }

    public Calendar getEntered() {
        return entered;
    }

    public Calendar getExpiry() {
        return expiry;
    }

    public String getName() {
        return name;
    }

    public Date getTimeLeft(){
        return null;
    }

    public String getIcon(){
        return null;
    }

    public String getStyle(){
       // if(getTimeLeft().getTime()<Calendar.DA)
        return null;
    }

    //TODO make this and make the timer to count how much time is left
}
