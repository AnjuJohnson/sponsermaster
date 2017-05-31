package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/4/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Owner on 14/03/2017.
 */

public class EmpTravelledAdapter extends RecyclerView.Adapter {

    private List<AllListItem> travelitems;
    public Context mContext;
    public Activity mActivity;
    String mStatus;

    public EmpTravelledAdapter(Activity mActivity, Context mContext, List<AllListItem> travelitems, String Status) {
        this.travelitems = travelitems;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mStatus = Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.employee_travelcancel_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new EmpTravelledAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AllListItem dataItem = travelitems.get(position);
        EmpTravelledAdapter.MyViewHolder myViewHolder = (EmpTravelledAdapter.MyViewHolder) viewHolder;
        if(mStatus.equals("travel")){
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mCompanyLayout.setVisibility(View.GONE);
            myViewHolder.mCompanyName.setVisibility(View.GONE);
            myViewHolder.mEmployeeid.setText("ID : "+""+dataItem.getemployee_employment_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Travelled Date : "+" "+dataItem.getcompany_address());
        }
        else if(mStatus.equals("confirmed")){
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mCompanyLayout.setVisibility(View.GONE);
            myViewHolder.mCompanyName.setVisibility(View.GONE);
            myViewHolder.mEmployeeid.setText("ID : "+""+dataItem.getemployee_employment_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Travel Date : "+" "+dataItem.getcompany_address());
        }
        else if(mStatus.equals("otherticketbooked")){
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mCompanyLayout.setVisibility(View.GONE);
            myViewHolder.mCompanyName.setVisibility(View.GONE);
            myViewHolder.mEmployeeid.setText("ID : "+""+dataItem.getemployee_employment_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Travel Date : "+" "+dataItem.getcompany_address());
        }
        else if(mStatus.equals("othertravelled")){
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mCompanyLayout.setVisibility(View.GONE);
            myViewHolder.mCompanyName.setVisibility(View.GONE);
            myViewHolder.mEmployeeid.setText("ID : "+""+dataItem.getemployee_employment_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Travelled Date : "+" "+dataItem.getcompany_address());
        }
        else if(mStatus.equals("othertravelcancel")){
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mCompanyLayout.setVisibility(View.GONE);
            myViewHolder.mCompanyName.setVisibility(View.GONE);
            myViewHolder.mEmployeeid.setText("ID : "+""+dataItem.getemployee_employment_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Travel Date : "+" "+dataItem.getcompany_address());
        }
       else if(mStatus.equals("annualtravelbooked")){
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mCompanyLayout.setVisibility(View.GONE);
            myViewHolder.mCompanyName.setVisibility(View.GONE);
            myViewHolder.mEmployeeid.setText("ID : "+""+dataItem.getemployee_employment_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Travelled Date : "+" "+dataItem.getcompany_address());
        }

        else if(mStatus.equals("eligible")){
            myViewHolder.mEmployeeid.setText(dataItem.getemployee_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Travelled Date : "+" "+dataItem.getcompany_address());
            myViewHolder.mDesignation.setVisibility(View.VISIBLE);
            myViewHolder.mDesignation.setText("Designation : "+" "+dataItem.getAssigned_employee());
            myViewHolder.mCompanyLayout.setVisibility(View.VISIBLE);
            myViewHolder.mCompanyName.setText(dataItem.getCompany_name());
            myViewHolder.mTravelDate.setText("No of Leaves : "+" "+dataItem.getcompany_address());

        }
        else {
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mDesignation.setVisibility(View.GONE);
            myViewHolder.mCompanyLayout.setVisibility(View.GONE);
            myViewHolder.mCompanyName.setVisibility(View.GONE);
            myViewHolder.mEmployeeid.setText("ID : "+""+dataItem.getemployee_employment_id());
            myViewHolder.mEmployeename.setText(dataItem.getEmployee_name());
            myViewHolder.mTravelDate.setText("Cancelled Date : "+" "+dataItem.getcompany_address());

        }
       /*myViewHolder.mProfileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,EmployeeProfile.class);
                intent.putExtra("ID",dataItem.getemployee_id());
                mContext.startActivity(intent);
            }
        });
*/
    }
    @Override
    public int getItemCount() {
        return travelitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mProfileview;
        public TextView mEmployeeid,mEmployeename,mTravelDate,mDesignation,mCompanyName;
        public LinearLayout mCompanyLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mEmployeeid = (TextView) itemView.findViewById(R.id.employee_id);
            mEmployeename = (TextView) itemView.findViewById(R.id.employee_name);
            mTravelDate = (TextView) itemView.findViewById(R.id.travel_date);
            mDesignation=(TextView)itemView.findViewById(R.id.designation);
            mCompanyName=(TextView)itemView.findViewById(R.id.company_name);
            mCompanyLayout=(LinearLayout)itemView.findViewById(R.id.company_layout);
            mProfileview=(CardView)itemView.findViewById(R.id.profileview);
        }
    }
}