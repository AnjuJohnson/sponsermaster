package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.R;

/**
 * Created by Kris on 12/21/2016.
 */
public class FilterAdapter extends RecyclerView.Adapter {

    Integer selected_position = -1;

    private String[] mitems;
    public Activity mContext;
    private String mStatus;

    public FilterAdapter(Activity mContext, String[] items, String Status) {
        this.mitems = items;
        this.mContext = mContext;
        this.mStatus = Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.filteritem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.title.setText(mitems[position]);

        myViewHolder.filtercheck.setChecked(position == selected_position);

        myViewHolder.filtercheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myViewHolder.filtercheck.isChecked()) {
                    selected_position =  position;
                } else{
                    selected_position = -1;
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {

        return mitems.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public CheckBox filtercheck;

        public MyViewHolder(View itemView) {
            super(itemView);

            filtercheck=(CheckBox)itemView.findViewById(R.id.filtercheck);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}