package com.example.ingredienttracker.ui.main;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Calendar;

public class IngredientItem implements Comparable<IngredientItem> {



    DateTime entered;
    DateTime expiry;
    String name;
    Period period;
    Days days;
    Hours hours;

    public IngredientItem(Calendar entered, Calendar expiry, String name) {
        this.entered = new DateTime(entered);
        this.expiry = new DateTime(expiry);
        this.name = name;
        period = new Period(this.entered,this.expiry, PeriodType.days());
        days = period.toStandardWeeks().toStandardDays();
        hours = period.toStandardHours();

    }

    public DateTime getEntered() {
        return entered;
    }

    public DateTime getExpiry() {
        return expiry;
    }

    public String getName() {
        return name;
    }

    public long[] getTimeLeft(){
        return new long[]{period.getDays()+(period.getWeeks()*7),period.getHours()};
    }

    @Override
    public String toString() {
        return "IngredientItem{" +
                " entered= " + entered +
                ", expiry= " + expiry +
                ", name= '" + name + '\'' +
                ", period= " + getTimeLeft()[0] + " " + getTimeLeft()[1] +
                '}';
    }

    @Override
    public int compareTo(IngredientItem o) {
        if(this.days.getDays()<o.days.getDays())
            return -1;
        else if (this.days.getDays()>o.days.getDays())
            return 1;
        else
            return 0;
    }
}
