package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Owner on 22/03/2017.
 */

public class LeaveListAdapter2 extends RecyclerView.Adapter {

    private List<AllListItem> travelitems;
    public Activity mContext;
    public Activity mActivity;
    String mStatus;

    public LeaveListAdapter2(Activity mActivity, Activity mContext, List<AllListItem> travelitems, String status) {
        this.travelitems = travelitems;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mStatus = status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.attendance_list_item1, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final AllListItem dataItem = travelitems.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.itemView.findViewById(R.id.profileview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",travelitems.get(position).getEmployee_name());
                intent.putExtra("ID",travelitems.get(position).getemployee_id());
                intent.putExtra("STATUS","employee");


                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                mContext.finish();

            }
        });
        if(mStatus.equals("leave")){
//           myViewHolder.mVisiblelayout.setVisibility(View.VISIBLE);
            myViewHolder.mEmployee_ID.setText(dataItem.getemployee_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mDesignation.setText(dataItem.getEmployee_designation());
            myViewHolder.mCompanyName.setText(dataItem.getCompany_name());
            myViewHolder.mLeaveStatus.setText(dataItem.getVehicle_status());
            if(dataItem.getcompany_code().equals("none")){
                myViewHolder.mCasual.setVisibility(View.GONE);
            }
            else{
                myViewHolder.mCasual.setText("Casual Leave:"+" "+dataItem.getcompany_code());
            }


            if(dataItem.getCompany_manufacturer().equals("none")){
                myViewHolder.mEmergency.setVisibility(View.GONE);
            }
            else{
                myViewHolder.mEmergency.setText("Emergency Leave:"+" "+dataItem.getCompany_manufacturer());
            }


            if(dataItem.getEmployee_mail().equals("none")){
                myViewHolder.mOthers.setVisibility(View.GONE);
            }
            else {
                myViewHolder.mOthers.setText("Other Leave:"+" "+dataItem.getEmployee_mail());

            }

            if(dataItem.getAssigned_company().equals("none")){
                myViewHolder.mAnnual.setVisibility(View.GONE);
            }
            else {
                myViewHolder.mAnnual.setText("Annual Leave:"+" "+dataItem.getAssigned_company());
            }


            if(dataItem.getcompany_email().equals("none")){
                myViewHolder.mMediacal.setVisibility(View.GONE);
            }
            else {
                myViewHolder.mMediacal.setText("Medical Leave:"+" "+dataItem.getcompany_email());
            }


            if(dataItem.getcompany_address().equals("none")){
                myViewHolder.mAbsent.setVisibility(View.GONE);
            }
            else {
                myViewHolder.mAbsent.setText("Absent Leave:"+" "+dataItem.getcompany_address());

            }
            if((dataItem.getcompany_code().equals("none"))&&(dataItem.getCompany_manufacturer().equals("none"))&&(dataItem.getCompany_manufacturer().equals("none"))&&
                    (dataItem.getAssigned_company().equals("none"))&&(dataItem.getAssigned_company().equals("none"))&&(dataItem.getcompany_address().equals("none")))
            {
                myViewHolder.mNone.setVisibility(View.VISIBLE);
            }
        }
        if(mStatus.equals("attendance")){
            myViewHolder.mVisiblelayout.setVisibility(View.GONE);
            myViewHolder.mEmployee_ID.setVisibility(View.VISIBLE);
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            if(dataItem.getEmployee_designation().equals("none")){
                myViewHolder.mDesignation.setText("Designation:" + " "+"null");
            }else {
                myViewHolder.mDesignation.setText("Designation:" + " " + dataItem.getEmployee_designation());
            }
            myViewHolder.mCompanyName.setText("Company:"+" "+dataItem.getCompany_name());
            myViewHolder.mEmployee_ID.setText("Employee ID"+" "+dataItem.getVehicle_status());
        }


    }
    @Override
    public int getItemCount() {
        return travelitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mEmployee_ID,mEmployeename,mLeaveStatus,mDesignation,mCompanyName,mLeaveType,mAbsent,mCasual,mMediacal,mAnnual,mOthers,mEmergency,mNone;
        public LinearLayout mInvisiblelayout,mVisiblelayout;
        ImageView changetype;
        public MyViewHolder(View itemView) {
            super(itemView);

            mEmployeename = (TextView) itemView.findViewById(R.id.employee_name);
            mLeaveStatus = (TextView) itemView.findViewById(R.id.leave_status);
            mDesignation=(TextView)itemView.findViewById(R.id.designation);
            mCompanyName=(TextView)itemView.findViewById(R.id.company);
            mVisiblelayout=(LinearLayout) itemView.findViewById(R.id.visible_layout);
            mCasual = (TextView) itemView.findViewById(R.id.casual);
            mMediacal = (TextView) itemView.findViewById(R.id.medical);
            mOthers = (TextView) itemView.findViewById(R.id.other);
            mAnnual = (TextView) itemView.findViewById(R.id.annual);
            mEmergency = (TextView) itemView.findViewById(R.id.emergency);
            mAbsent = (TextView) itemView.findViewById(R.id.absence);
            mNone = (TextView) itemView.findViewById(R.id.none);
            mEmployee_ID = (TextView) itemView.findViewById(R.id.employee_ID);


        }
    }
}
