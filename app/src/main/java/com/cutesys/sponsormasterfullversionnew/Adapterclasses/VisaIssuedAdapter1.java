package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/1/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;

import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Owner on 21/03/2017.
 */

public class VisaIssuedAdapter1 extends RecyclerView.Adapter {

    private List<AllListItem> travelitems;
    public Context mContext;
    public Activity mActivity;
    String mStatus;

    public VisaIssuedAdapter1(Activity mActivity,Context mContext,List<AllListItem> travelitems) {
        this.travelitems = travelitems;
        this.mContext = mContext;
        this.mActivity = mActivity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.visa_issued_list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new VisaIssuedAdapter1.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AllListItem dataItem = travelitems.get(position);
        final VisaIssuedAdapter1.MyViewHolder myViewHolder = (VisaIssuedAdapter1.MyViewHolder) viewHolder;
       /* if(position%2==1){
            myViewHolder.mloancard.setBackgroundResource(R.color.Vehicle_background);
        }
        else {
            myViewHolder.mloancard.setBackgroundResource(R.color.app_arrow_grey);
        }*/

        myViewHolder.mApplicant_name.setText(dataItem.getEmployee_name());
       /* myViewHolder.mEmp_ID.setText("Employee ID:"+" "+dataItem.getemployee_id());*/
        myViewHolder.mRefernce.setText(dataItem.getCompany_name());
        myViewHolder.mVisa_no.setText(dataItem.getCompany_model());
        myViewHolder.mEntry.setText(dataItem.getVehicle_status());
        myViewHolder.mMoredata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.mLoanlayout.setVisibility(View.VISIBLE);
                myViewHolder.mMoredata.setVisibility(View.GONE);
                myViewHolder.mExpiry.setText(dataItem.getEmployee_company());
                myViewHolder.mStatus_.setText(dataItem.getCompany_manufacture());
                myViewHolder.mLowdata.setVisibility(View.VISIBLE);
            }
        });

        myViewHolder.mLowdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.mLoanlayout.setVisibility(View.GONE);
                myViewHolder.mMoredata.setVisibility(View.VISIBLE);
                myViewHolder.mLowdata.setVisibility(View.GONE);
            }
        });




    }
    @Override
    public int getItemCount() {
        return travelitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mRefernce,mVisa_no,mEntry,mExpiry,mBalance,mStatus_,mApplicant_name;
        public LinearLayout mLoanlayout;
        ImageView mdown,mUp;
        FrameLayout mMoredata,mLowdata;
        LinearLayout mloancard;
        public MyViewHolder(View itemView) {
            super(itemView);
            mloancard=(LinearLayout) itemView.findViewById(R.id.swipe);
            mMoredata=(FrameLayout)itemView.findViewById(R.id.moredata);
            mLowdata=(FrameLayout)itemView.findViewById(R.id.lowdata);
            mApplicant_name=(TextView)itemView.findViewById(R.id.applicant_name);
            mRefernce = (TextView) itemView.findViewById(R.id.company);
            mVisa_no=(TextView)itemView.findViewById(R.id.salary);
            mEntry=(TextView)itemView.findViewById(R.id.entry_date);
            mExpiry=(TextView)itemView.findViewById(R.id.ex_date);
            mStatus_=(TextView)itemView.findViewById(R.id.status);
            mLoanlayout=(LinearLayout)itemView.findViewById(R.id.loan_layout);
            mdown=(ImageView)itemView.findViewById(R.id.down);
            mUp=(ImageView)itemView.findViewById(R.id.up);


        }
    }
}

