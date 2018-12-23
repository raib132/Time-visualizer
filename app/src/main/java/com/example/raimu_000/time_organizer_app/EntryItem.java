package com.example.raimu_000.time_organizer_app;

public class EntryItem {
    String mActivity;
    int mTime;

    public EntryItem(String activity, int time){
       mActivity = activity;
       mTime = time;
    }

    public String getmActivity() {
        return mActivity;
    }

    public int getmTime() {
        return mTime;
    }
}
