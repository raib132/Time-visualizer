package com.example.raimu_000.time_organizer_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private EntriesDBH dbHelper;
    private SQLiteDatabase mDatabase;
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private int ButtonState;
    public int TimeValue;
    private MyAdapter adapter;

    private FloatingActionButton startBtn;
    private FloatingActionButton resetBtn;
    private FloatingActionMenu addBtn;

    private String TAG = "APP_DATABASE_INFO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        dbHelper = new EntriesDBH(this);

        ArrayList<EntryItem> al = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        startBtn = findViewById(R.id.startBtn);
        resetBtn = findViewById(R.id.restartBtn);
        addBtn = findViewById(R.id.addBtn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chronometer = findViewById(R.id.stopWatch);

        ButtonState = 1; //play

        addBtn.setEnabled(false);
        resetBtn.setEnabled(false);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Button state 1 = play, 0 = stop.

                switch(ButtonState){
                    case 0 :
                        stop(chronometer);
                        addBtn.setEnabled(true);
                        resetBtn.setEnabled(true);
                        ButtonState = 1;
                        startBtn.setImageDrawable(null);
                        startBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        break;
                    case 1 :
                        start(chronometer);
                        resetBtn.setEnabled(false);
                        addBtn.setEnabled(false);
                        ButtonState = 0;
                        startBtn.setImageDrawable(null);
                        startBtn.setImageResource(R.drawable.ic_pause_black_24dp);
                        break;
                }


            }
        });


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(TimeValue);
                reset(chronometer);

            }
        });

        com.github.clans.fab.FloatingActionButton exerciseBtn = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.exerciseBtn);
        com.github.clans.fab.FloatingActionButton leisureBtn = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.leisureBtn);
        com.github.clans.fab.FloatingActionButton readingBtn = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.readingBtn);
        com.github.clans.fab.FloatingActionButton studyingBtn = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.studyingBtn);
        com.github.clans.fab.FloatingActionButton otherBtn = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.OtherBtn);

        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("Exercise", TimeValue);
                Toast.makeText(getApplicationContext(), "Entry added", Toast.LENGTH_SHORT).show();
            }
        });

        readingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("Exercise", TimeValue);
                Toast.makeText(getApplicationContext(), "Entry added", Toast.LENGTH_SHORT).show();
            }
        });

        studyingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("Exercise", TimeValue);
                Toast.makeText(getApplicationContext(), "Entry added", Toast.LENGTH_SHORT).show();
            }
        });

        leisureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("Exercise", TimeValue);
                Toast.makeText(getApplicationContext(), "Entry added", Toast.LENGTH_SHORT).show();
            }
        });

        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem("Exercise", TimeValue);
                Toast.makeText(getApplicationContext(), "Entry added", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void start(View v){
       if(!running){
           chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
           chronometer.start();
           running = true;
       }
    }

    public void stop(View v){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            TimeValue = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) / 60;
        }
    }

    public void reset(View v){
       chronometer.setBase(SystemClock.elapsedRealtime());
       pauseOffset = 0;
    }

    public void addItem(String activity, int time){
        mDatabase = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(EntriesTableContract.TableEntry.COLUMMN_ACTIVITY, activity);
        cv.put(EntriesTableContract.TableEntry.COLUMN_TIME, time);

        mDatabase.insert(EntriesTableContract.TableEntry.TABLE_NAME, null, cv);
    }


}