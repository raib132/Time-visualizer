package com.example.raimu_000.time_organizer_app;

import java.util.Calendar;

public class Date {
    String date;
    public Date(){
        Integer day = Calendar.DAY_OF_WEEK;
        Integer month = Calendar.MONTH;
        Integer year = Calendar.YEAR;
        date = day.toString() + "/" + month.toString() + "/" + year.toString();
    }

    public String getDate(){
        return date;
    }
}
