package com.example.raimu_000.time_organizer_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Activity_Tab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Activity_Tab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Activity_Tab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RadarChart chart;
    private View v;

    private SQLiteDatabase db;
    private EntriesDBH dbHelper;

    public Activity_Tab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Activity_Tab.
     */
    // TODO: Rename and change types and number of parameters
    public static Activity_Tab newInstance(String param1, String param2) {
        Activity_Tab fragment = new Activity_Tab();
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
        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.fragment_activity__tab, container, false);

       //ArrayList<EntryItem> radarEntryItems = getEntryItems();


        Description desc = new Description();
        desc.setTextColor(Color.WHITE);
        desc.setTextSize(10f);
        desc.setText("Total time spent in each activity");

        chart = (RadarChart) v.findViewById(R.id.RadarChart);
        chart.setBackgroundColor(Color.rgb(60, 65, 82));
        chart.setDrawWeb(true);
        chart.setWebColor(Color.WHITE);
        chart.setWebLineWidth(1f);
        chart.setWebColorInner(Color.WHITE);
        chart.setDescription(desc);

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad, Easing.EaseInOutQuad);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        final String qualities[] = new String[] {"Exercise", "Reading", "Leisure", "Studying", "Other"};

        //Y axis
        YAxis yAxis = chart.getYAxis();
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(9f);


        //X axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return qualities[(int) value % qualities.length];
            }
        });

        xAxis.setTextColor(Color.WHITE);

        if(Count() != 0) {
            ArrayList<RadarEntry> entryItems = addArrayData();
            setData(entryItems);
        }

        return v;
    }


    private void setData(ArrayList<RadarEntry> entryItems) {

        RadarDataSet dataset1 = new RadarDataSet(entryItems, "data");
        dataset1.setColor(Color.BLUE);
        dataset1.setFillColor(Color.BLUE);
        dataset1.setDrawFilled(true);
        dataset1.setFillAlpha(50);
        dataset1.setLineWidth(2f);


        RadarData data = new RadarData(dataset1);


        chart.setData(data);
        chart.invalidate();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ArrayList<EntryItem> getEntryItems() {
        EntriesDBH entriesDBH = new EntriesDBH(getContext());
        SQLiteDatabase db = entriesDBH.getReadableDatabase();
        ArrayList<EntryItem> entryItems1 = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT ACTIVITY, TIME FROM ActivityEntries", null);
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

    public ArrayList<RadarEntry> addArrayData(){
        ArrayList<RadarEntry> arrayList = new ArrayList<>();
        dbHelper = new EntriesDBH(getContext());
        db = dbHelper.getReadableDatabase();

        //Ex
        Cursor cursor;
        cursor = db.rawQuery("SELECT MAX_EX FROM ActivityEntries", null);
        cursor.moveToFirst();

        RadarEntry ei = new RadarEntry(cursor.getInt(cursor.getColumnIndex(EntriesTableContract.TableEntry.MAX_EX)), "Exercise");
        arrayList.add(ei);

        //St
        cursor = db.rawQuery("SELECT MAX_ST FROM ActivityEntries", null);
        cursor.moveToFirst();

        ei = new RadarEntry(cursor.getInt(cursor.getColumnIndex(EntriesTableContract.TableEntry.MAX_ST)), "Studying");
        arrayList.add(ei);

        //Re
        cursor = db.rawQuery("SELECT MAX_RE FROM ActivityEntries", null);
        cursor.moveToFirst();

        ei = new RadarEntry(cursor.getInt(cursor.getColumnIndex(EntriesTableContract.TableEntry.MAX_RE)), "Reading");
        arrayList.add(ei);

        //Le
        cursor = db.rawQuery("SELECT MAX_LE FROM ActivityEntries", null);
        cursor.moveToFirst();

        ei = new RadarEntry(cursor.getInt(cursor.getColumnIndex(EntriesTableContract.TableEntry.MAX_LE)), "Leisure");
        arrayList.add(ei);

        //Ex
        cursor = db.rawQuery("SELECT MAX_OTH FROM ActivityEntries", null);
        cursor.moveToFirst();

        ei = new RadarEntry(cursor.getInt(cursor.getColumnIndex(EntriesTableContract.TableEntry.MAX_OTH)), "Other");
        arrayList.add(ei);


        return arrayList;
    }

    public int Count(){
        int rows;
        dbHelper = new EntriesDBH(getContext());
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor =  db.rawQuery("SELECT ACTIVITY, TIME FROM ActivityEntries ORDER BY TIMESTAMP DESC", null);
        cursor.moveToFirst();
        rows = cursor.getCount();
        return rows;
    }
}
