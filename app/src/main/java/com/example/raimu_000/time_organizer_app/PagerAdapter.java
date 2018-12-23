package com.example.raimu_000.time_organizer_app;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Feed_tab feedTab = new Feed_tab();
                return feedTab;
            case 1:
                Activity_Tab activityTab = new Activity_Tab();
                return activityTab;
            default:
                return null;
        }
    }

    String Feed = new String("Feed");
    String Activity = new String("Activity");


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       switch(position){
           case 0:
               return Feed;
           case 1:
               return Activity;
           default:
               return null;
       }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
