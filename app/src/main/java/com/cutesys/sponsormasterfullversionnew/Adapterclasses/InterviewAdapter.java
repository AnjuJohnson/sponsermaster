package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Recruitment.AddTravelDetailsFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Interviewprofile;

import java.util.List;

/**
 * Created by Owner on 21/03/2017.
 */

public class InterviewAdapter extends RecyclerView.Adapter {

    private List<AllListItem> travelitems;
    public Context mContext;
    public Activity mActivity;
    String mStatus;

    public InterviewAdapter(Activity mActivity,Context mContext,List<AllListItem> travelitems,String Status) {
        this.travelitems = travelitems;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mStatus = Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.interview, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new InterviewAdapter.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AllListItem dataItem = travelitems.get(position);
        InterviewAdapter.MyViewHolder myViewHolder = (InterviewAdapter.MyViewHolder) viewHolder;

        myViewHolder.mInterviewName.setText(dataItem.getcompany_address());
        myViewHolder.mInterviewCode.setText(dataItem.getinterview_auto_id());
        myViewHolder.mCompanyName.setText(dataItem.getEmployee_name());
        myViewHolder.mPlace.setText(dataItem.getcompany_code());
        myViewHolder.mInterviewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Interviewprofile.class);
                intent.putExtra("ID",dataItem.getemployee_id());
                intent.putExtra("STATUS",mStatus);
                intent.putExtra("NAME",dataItem.getEmployee_name());
                mContext.startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount() {
        return travelitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mInterviewDetails;
        public TextView mInterviewName,mPlace,mCompanyName,mInterviewCode;
        public LinearLayout mCompanyLayout;
        public MyViewHolder(View itemView) {
            super(itemView);

            mInterviewName = (TextView) itemView.findViewById(R.id.interview_name);
            mInterviewCode = (TextView) itemView.findViewById(R.id.interview_code);
            mPlace = (TextView) itemView.findViewById(R.id.place);
            mCompanyName=(TextView)itemView.findViewById(R.id.company);
            mInterviewDetails=(CardView) itemView.findViewById(R.id.profileview);

        }
    }
}

