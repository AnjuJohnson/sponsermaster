package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by user on 3/18/2017.
 */

public class RecruitSettingsTwoItemAdapter extends RecyclerView.Adapter {

    private List<ListItem> travelitems;
    public Context mContext;
    public Activity mActivity;
    String mStatus;

    public RecruitSettingsTwoItemAdapter(Activity mActivity, Context mContext, List<ListItem> travelitems, String mstatus) {
        this.travelitems = travelitems;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mStatus=mstatus;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.recruitmentsetting_two_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new RecruitSettingsTwoItemAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ListItem dataItem = travelitems.get(position);
        RecruitSettingsTwoItemAdapter.MyViewHolder myViewHolder = (RecruitSettingsTwoItemAdapter.MyViewHolder) viewHolder;

        myViewHolder.mSINo.setText(dataItem.get_id());
        myViewHolder.mState.setText(dataItem.get_name());
        myViewHolder.mCountry.setText(dataItem.get_address());
        if(position%2==1){
            myViewHolder.mholi_Layout.setBackgroundResource(R.color.swipe);
        }
        else {
            myViewHolder.mholi_Layout.setBackgroundResource(R.color.swipe);
        }

    }
    @Override
    public int getItemCount() {
        return travelitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mSINo,mState,mCountry;
        public LinearLayout mholi_Layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mholi_Layout=(LinearLayout)itemView.findViewById(R.id.holi_layout);
            mSINo = (TextView) itemView.findViewById(R.id.SINo);
            mState = (TextView) itemView.findViewById(R.id.holiday_date);
            mCountry = (TextView) itemView.findViewById(R.id.country);
        }
    }
}

