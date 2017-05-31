package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/28/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Owner on 17/03/2017.
 */

public class LoanAdapter extends RecyclerView.Adapter {

    private List<ListItem> travelitems;
    public Activity mContext;
    public Activity mActivity;
    String mStatus;

    public LoanAdapter(Activity mActivity,Activity mContext,List<ListItem> travelitems) {
        this.travelitems = travelitems;
        this.mContext = mContext;
        this.mActivity = mActivity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.loan_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new LoanAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ListItem dataItem = travelitems.get(position);
        final LoanAdapter.MyViewHolder myViewHolder = (LoanAdapter.MyViewHolder) viewHolder;
       /* if(position%2==1){
            myViewHolder.mloancard.setBackgroundResource(R.color.Vehicle_background);
        }
        else {
            myViewHolder.mloancard.setBackgroundResource(R.color.app_arrow_grey);
        }*/

        myViewHolder.itemView.findViewById(R.id.pro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",travelitems.get(position).get_name());
                intent.putExtra("ID",travelitems.get(position).get_employment_id());
                intent.putExtra("STATUS","employee");


                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                mContext.finish();

            }
        });

        myViewHolder.mEmployeename.setText(dataItem.get_name());
        myViewHolder.mEmployee_empid.setText(dataItem.get_employee_employment_id());
        // myViewHolder.mEmp_ID.setText("Employee ID:"+" "+dataItem.getemployee_id());
        myViewHolder.mCompanyName.setText(dataItem.get_code());
        myViewHolder.mSalary.setText(dataItem.get_advance_fee());
        myViewHolder.mReq_amount.setText(dataItem.get_sponsor_fee());
        myViewHolder.mMoredata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.mLoanlayout.setVisibility(View.VISIBLE);
                myViewHolder.mMoredata.setVisibility(View.GONE);
                myViewHolder.mReq_Date.setText(dataItem.get_bank_fee());
                myViewHolder.mApprov_amount.setText(dataItem.get_manufacture());
                myViewHolder.mApprov_date.setText(dataItem.get_email());
                myViewHolder.mType.setText(dataItem.get_balance_fee());
                myViewHolder.mStatus_.setText(dataItem.get_sponsor_fee());
                myViewHolder.mPay_date.setText(dataItem.get_phone());
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

        myViewHolder.mProfileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mActivity,OtherProfileActivity.class);
                intent.putExtra("ID",dataItem.get_id());
                mActivity.startActivity(intent);*/
               /*Intent intent=new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("ID",dataItem.get_id());
                mContext.startActivity(intent);*/
            }
        });


    }
    @Override
    public int getItemCount() {
        return travelitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mEmployeename,mEmp_ID,mDesignation,mCompanyName,mSalary,mReq_amount,mReq_Date,mApprov_amount,mApprov_date,mType,mStatus_,
                mPay_date,mEmployee_empid;
        public LinearLayout mLoanlayout;
        ImageView mdown,mUp;
        FrameLayout mMoredata,mLowdata;
        LinearLayout mloancard;
        CardView mProfileview;
        public MyViewHolder(View itemView) {
            super(itemView);
            mloancard=(LinearLayout) itemView.findViewById(R.id.swipe);
            mMoredata=(FrameLayout)itemView.findViewById(R.id.moredata);
            mLowdata=(FrameLayout)itemView.findViewById(R.id.lowdata);
            mEmployeename = (TextView) itemView.findViewById(R.id.employee_name);
            mEmployee_empid= (TextView) itemView.findViewById(R.id.employee_empid);
           /* mEmp_ID = (TextView) itemView.findViewById(R.id.emp_ID);*/
           // mDesignation=(TextView)itemView.findViewById(R.id.designation);
            mCompanyName=(TextView)itemView.findViewById(R.id.company);
            mSalary=(TextView)itemView.findViewById(R.id.salary);
            mReq_amount=(TextView)itemView.findViewById(R.id.req_amount);
            mReq_Date=(TextView)itemView.findViewById(R.id.req_date);
            mApprov_amount=(TextView)itemView.findViewById(R.id.approv_amount);
            mApprov_date=(TextView)itemView.findViewById(R.id.approv_date);
            mType=(TextView)itemView.findViewById(R.id.type);
            mPay_date=(TextView)itemView.findViewById(R.id.pay_date);
            mStatus_=(TextView)itemView.findViewById(R.id.status);
            mLoanlayout=(LinearLayout)itemView.findViewById(R.id.loan_layout);
            mdown=(ImageView)itemView.findViewById(R.id.down);
            mUp=(ImageView)itemView.findViewById(R.id.up);
            mProfileview=(CardView)itemView.findViewById(R.id.profileview);


        }
    }
}
