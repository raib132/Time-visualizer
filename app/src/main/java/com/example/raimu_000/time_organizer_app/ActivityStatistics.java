package com.example.raimu_000.time_organizer_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ActivityStatistics {

    private SQLiteDatabase db;
    private EntriesDBH dbHelper;

    public int SUM_EX;
    public int SUM_RE;
    public int SUM_ST;
    public int SUM_LE;
    public int SUM_OTH;
    public int MAX_SUM;

    public ActivityStatistics(Context context){
      dbHelper = new EntriesDBH(context);
      db = dbHelper.getReadableDatabase();
    }

   /* private int setSumEx(){
        int max;
        Cursor cursor;

        return max;
    }
   */
}
