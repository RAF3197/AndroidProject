package com.jediupc.helloandroid;


import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jediupc.helloandroid.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final OnItemClickListener mListener;
    private ArrayList<AudioModel> mDataset;
    private Set<Integer> mSelectedPositions = new HashSet<>();

    public Set<Integer> getSelectedPositions() {
        return mSelectedPositions;
    }

    private boolean mContextEnabled;

    public void setContextMode(boolean mode) {
        mContextEnabled = mode;
        notifyDataSetChanged();
        if (!mContextEnabled) {
            mSelectedPositions = new HashSet<>();
        }
    }



    public interface OnItemClickListener {
        void onItemClick(View v, int pos);

        boolean onItemLongClick(View v, int pos);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public RelativeLayout mRoot;
        public TextView mTextView;
        public TextView mTextView2;

        public MyViewHolder(RelativeLayout v) {
            super(v);

            mRoot = v;
            mTextView = v.findViewById(R.id.title);
            mTextView2 = v.findViewById(R.id.duration);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<AudioModel> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.records_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, holder.getAdapterPosition());
            }
        });

        holder.mRoot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!mContextEnabled) {
                    mSelectedPositions.add(position);
                }

                return mListener.onItemLongClick(v, position);
            }

        });

        if (mDataset.get(position).removed==0){
            holder.mTextView.setText(mDataset.get(position).creationTime);


            holder.mTextView2.setText(msToString(mDataset.get(position).duration));
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @SuppressLint("DefaultLocale")
    private String msToString(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = ms / 1000 / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}