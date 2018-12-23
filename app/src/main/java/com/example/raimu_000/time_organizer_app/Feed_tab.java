package com.example.raimu_000.time_organizer_app;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Feed_tab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Feed_tab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feed_tab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String [] activities =  {"Exercise", "Studying", "Reading", "Leisure"};

    public RecyclerView recyclerView;
    public SQLiteDatabase db;
    public EntriesDBH dbHelper;
    public MyAdapter mAdapter;

    public ArrayList<EntryItem> entryItems;
    public ArrayList<EntryItem> entryItemArrayList = new ArrayList<EntryItem>();

    int sumEx, sumSt, sumLe, sumRe, sumOth;

    public String columns[] = {EntriesTableContract.TableEntry.COLUMMN_ACTIVITY, EntriesTableContract.TableEntry.COLUMN_TIME};


    public Feed_tab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Feed_tab.
     */
    // TODO: Rename and change types and number of parameters
    public static Feed_tab newInstance(String param1, String param2) {
        Feed_tab fragment = new Feed_tab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_feed_tab, container, false);

        dbHelper = new EntriesDBH(getContext());

        if(Count() != 0) {

            addValues(EntriesTableContract.TableEntry.MAX_EX, sumEx());
            addValues(EntriesTableContract.TableEntry.MAX_ST, sumSt());
            addValues(EntriesTableContract.TableEntry.MAX_RE, sumRe());
            addValues(EntriesTableContract.TableEntry.MAX_LE, sumLe());
            addValues(EntriesTableContract.TableEntry.MAX_OTH, sumOth());
        }

        if(Count() != 0) {
            entryItems = getItems(entryItemArrayList);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(getContext(), entryItems);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fabButton);
        //FAB ON-CLICK LISTENER
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TimerActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public ArrayList<EntryItem> getItems(ArrayList<EntryItem> entryItems1){
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor =  db.rawQuery("SELECT ACTIVITY, TIME FROM ActivityEntries ORDER BY TIMESTAMP DESC", null);
            cursor.moveToFirst();
        EntryItem entryitem = new EntryItem(cursor.getString(cursor.getColumnIndex(EntriesTableContract.TableEntry.COLUMMN_ACTIVITY)),
                cursor.getInt(cursor.getColumnIndex(EntriesTableContract.TableEntry.COLUMN_TIME)));
        entryItems1.add(entryitem);
            while(cursor.moveToNext()) {
                EntryItem entryitem2 = new EntryItem(cursor.getString(cursor.getColumnIndex(EntriesTableContract.TableEntry.COLUMMN_ACTIVITY)),
                        cursor.getInt(cursor.getColumnIndex(EntriesTableContract.TableEntry.COLUMN_TIME)));
                entryItems1.add(entryitem2);
            }
        return entryItems1;
    }

    public int Count(){
        int rows;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor =  db.rawQuery("SELECT ACTIVITY, TIME FROM ActivityEntries ORDER BY TIMESTAMP DESC", null);
        cursor.moveToFirst();
        rows = cursor.getCount();
        return rows;
    }

    public int sumEx(){
        int sum;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT SUM(time) FROM ActivityEntries WHERE Activity LIKE 'Exercise' ", null);
        cursor.moveToFirst();
        sum = cursor.getInt(0);
        return sum;
    }

    public int sumRe(){
        int sum;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT SUM(time) FROM ActivityEntries WHERE Activity LIKE 'Reading' ", null);
        cursor.moveToFirst();
        sum = cursor.getInt(0);

        return sum;
    }

    public int sumSt(){
        int sum;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT SUM(time) FROM ActivityEntries WHERE Activity LIKE 'Studying' ", null);
        cursor.moveToFirst();
        sum = cursor.getInt(0);
        return sum;
    }

    public int sumLe(){
        int sum;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT SUM(time) FROM ActivityEntries WHERE Activity LIKE 'Leisure' ", null);
        cursor.moveToFirst();
        sum = cursor.getInt(0);
        return sum;
    }

    public int sumOth(){
        int sum;
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT SUM(time) FROM ActivityEntries WHERE Activity LIKE 'Other' ", null);
        cursor.moveToFirst();
        sum = cursor.getInt(0);
        return sum;
    }

    public void addValues(String key, int val){
        db = dbHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(key, val);

        db.insert(EntriesTableContract.TableEntry.TABLE_NAME, null, cv);
    }

}
