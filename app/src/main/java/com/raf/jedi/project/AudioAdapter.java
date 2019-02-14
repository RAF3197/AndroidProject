package com.raf.jedi.project;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {
    private final OnItemClickListener mListener;
    private ArrayList<AudioModel> mDataset;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mRoot;
        public TextView mTextView;
        public TextView mDuration;
        public SeekBar seekBar;

        public MyViewHolder(RelativeLayout v) {
            super(v);

            mRoot = v;
            //mDuration = v.findViewById(R.id.PlayerDuration);
            seekBar = v.findViewById(R.id.seekBar);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AudioAdapter(ArrayList<AudioModel> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AudioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
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

        holder.mTextView.setText(mDataset.get(position).creationTime);
        holder.mDuration.setText(msToString(mDataset.get(position).duration));

    }


    @SuppressLint("DefaultLocale")
    private String msToString(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = ms / 1000 / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}