package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/28/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Owner on 18/03/2017.
 */

public class EmpManageholidayAdapter extends RecyclerView.Adapter {

    private List<ListItem> travelitems;
    public Context mContext;
    public Activity mActivity;
    String mStatus;


    public EmpManageholidayAdapter(Activity mActivity,Context mContext,List<ListItem> travelitems,String status) {
        this.travelitems = travelitems;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mStatus=status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.emp_holiday_list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new EmpManageholidayAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ListItem dataItem = travelitems.get(position);
        EmpManageholidayAdapter.MyViewHolder myViewHolder = (EmpManageholidayAdapter.MyViewHolder) viewHolder;
        if(mStatus.equals("holiday")) {
            myViewHolder.mSINo.setText(""+(position+1));
            myViewHolder.mHolidayDate.setText(dataItem.get_name());
            if (position%2 == 1){
                myViewHolder.mholi_Layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
            } else {
                myViewHolder.mholi_Layout.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
        /*else if(mStatus.equals("allowance")){
           *//* myViewHolder.mSINo.setText(dataItem.getemployee_id());*//*
            myViewHolder.mSINo.setText(""+position+1);
            myViewHolder.mHolidayDate.setText(dataItem.get_name());
            if (position%2 == 1){
                myViewHolder.mholi_Layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
            } else {
                myViewHolder.mholi_Layout.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }*/
    }
    @Override
    public int getItemCount() {
        return travelitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mSINo,mHolidayDate;
        public LinearLayout mholi_Layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mholi_Layout=(LinearLayout)itemView.findViewById(R.id.holi_layout);
            mSINo = (TextView) itemView.findViewById(R.id.SINo);
            mHolidayDate = (TextView) itemView.findViewById(R.id.holiday_date);
        }
    }
}

