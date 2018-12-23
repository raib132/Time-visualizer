package com.example.raimu_000.time_organizer_app;

import android.provider.BaseColumns;

public final class EntriesTableContract {

    private EntriesTableContract(){

    }

    public static class TableEntry implements BaseColumns{
      public static final String ID = "ID";
      public static final String TABLE_NAME = "ActivityEntries";
      public static final String COLUMMN_ACTIVITY = "Activity";
      public static final String COLUMN_TIME = "Time";
      public static final String COLUMN_TIMESTAMP = "Timestamp";
      public static final String COLUMN_DATE = "Date";
      public static final String MAX_EX = "max_ex";
      public static final String MAX_ST = "max_st";
      public static final String MAX_LE = "max_le";
      public static final String MAX_RE = "max_re";
      public static final String MAX_OTH = "max_oth";
    }
}
