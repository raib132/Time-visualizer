package com.example.raimu_000.time_organizer_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntriesDBH extends SQLiteOpenHelper {
    public static final String ID = "ID";
    public static final String TABLE_NAME = "ActivityEntries";
    public static final String COLUMMN_ACTIVITY = "Activity";
    public static final String COLUMN_TIME = "Time";
    public static final String COLUMN_TIMESTAMP = "Timestamp";
    public static final String COLUMN_DATE = "Date";

    public static final String MAX_EX = "max_ex";
    public static final String MAX_ST = "max_st";
    public static final String MAX_RE = "max_re";
    public static final String MAX_LE = "max_le";
    public static final String MAX_OTH = "max_oth";

    public static final String DATABASE_NAME = "ActivityEntries.db";
    public static final int DATABASE_VERSION = 3;

    public EntriesDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
              + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
              + COLUMMN_ACTIVITY + " TEXT NOT NULL, "
              + COLUMN_TIME + " INTEGER NOT NULL, "
              + COLUMN_DATE + " TEXT, "
              + COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
              + MAX_EX + " INTEGER, "
              + MAX_ST + " INTEGER, "
              + MAX_RE + " INTEGER, "
              + MAX_LE + " INTEGER, "
              + MAX_OTH + " INTEGER);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
    }
}
