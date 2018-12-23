package com.example.raimu_000.time_organizer_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<EntryItem> mEntryItems;
    private Context mContext;
    private Cursor mCursor;


    public MyAdapter (Context context, ArrayList<EntryItem> entryItems){
        mContext = context;
        mEntryItems = entryItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
      ViewHolder holder = new ViewHolder(view);
      mEntryItems = new ArrayList<>();
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // if(!mCursor.move(position)){
       //  return;
    // }

         holder.activityTextView.setText(mEntryItems.get(position).mActivity);
         holder.text_time.setText(String.valueOf(mEntryItems.get(position).mTime));

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityTextView;
        TextView text_time;
        ImageView mIcon;
        RelativeLayout parent_layout;


        public ViewHolder(View itemView) {
            super(itemView);

            activityTextView = (TextView) itemView.findViewById(R.id.main_textview);
            text_time = (TextView) itemView.findViewById(R.id.time_textview);
            mIcon = (ImageView) itemView.findViewById(R.id.icon_imageView);
            parent_layout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);

        }
    }

    @Override
    public int getItemCount() {
        if(mEntryItems == null){
            return 0;
        }
        return mEntryItems.size();
    }


}
