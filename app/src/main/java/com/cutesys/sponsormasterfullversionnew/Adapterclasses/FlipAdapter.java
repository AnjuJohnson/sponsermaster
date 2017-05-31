package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/3/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScroller;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Subclasses.FlipChildActivity;
import com.cutesys.sponsormasterfullversionnew.Subclasses.HomeFragment;

import java.util.List;

/**
 * Created by Owner on 3/04/2017.
 */

public class FlipAdapter extends RecyclerView.Adapter implements FastScroller.SectionIndexer {

    private List<ListItem> mitems;
    public Activity mContext;

    HomeFragment mHomeFragment;
    String mstatus;

    public FlipAdapter(Activity mContext, List<ListItem> items, String status) {
        this.mitems = items;
        this.mContext = mContext;
        mstatus = status;
        mHomeFragment = new HomeFragment();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.flip_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        final ListItem dataItem = mitems.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.fliptitile.setText(dataItem.get_name());

        if(mstatus.equals("flipcompany")){

            myViewHolder.fliptitile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext,FlipChildActivity.class);
                    intent.putExtra("ACTION","company");
                    intent.putExtra("DATA",dataItem.get_name());
                    intent.putExtra("ID",dataItem.get_id());
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(android.R.anim.fade_in,
                            android.R.anim.fade_out);

                }
            });

        } else if(mstatus.equals("flipvehicle")){

            myViewHolder.fliptitile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext,FlipChildActivity.class);
                    intent.putExtra("ACTION","vehicle");
                    intent.putExtra("DATA",dataItem.get_name());
                    intent.putExtra("ID",dataItem.get_id());
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(android.R.anim.fade_in,
                            android.R.anim.fade_out);

                }
            });

        } else if(mstatus.equals("flipemployee")){

            myViewHolder.fliptitile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,FlipChildActivity.class);
                    intent.putExtra("ACTION","employee");
                    intent.putExtra("DATA",dataItem.get_name());
                    intent.putExtra("ID",dataItem.get_id());
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mitems.size();
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(mitems.get(position).get_name().charAt(0));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView fliptitile;

        public MyViewHolder(View itemView) {
            super(itemView);

            fliptitile = (TextView) itemView.findViewById(R.id.fliptitile);
        }
    }
}