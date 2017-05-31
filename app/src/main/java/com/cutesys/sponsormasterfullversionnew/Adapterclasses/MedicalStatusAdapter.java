package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/1/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 3/14/2017.
 */

public class MedicalStatusAdapter extends RecyclerView.Adapter<MedicalStatusAdapter.MyViewHolder>  {
    private ArrayList<RecruitmentListItem> mListItem;
    Activity mContext;
    String Status;
    RecruitmentListItem item;
    View v;

    public MedicalStatusAdapter(Activity context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fullname,candidate_email,candidate_phone,candidate_job,candidate_nationality,candidate_ID;
        ImageView profile;


        public MyViewHolder(View itemView){
            super(itemView);
            this.profile=(ImageView) itemView.findViewById(R.id.profile);
            this.fullname=(TextView) itemView.findViewById(R.id.fullname);
            this.candidate_email=(TextView) itemView.findViewById(R.id.candidate_email);
            this.candidate_phone=(TextView) itemView.findViewById(R.id.candidate_phone);
            this.candidate_job=(TextView) itemView.findViewById(R.id.candidate_job);
            this.candidate_nationality=(TextView) itemView.findViewById(R.id.candidate_nationality);
            this.candidate_ID=(TextView) itemView.findViewById(R.id.candidate_ID);
        }

    }
    public MedicalStatusAdapter(ArrayList<RecruitmentListItem> data){
        this.mListItem=data;
    }


    @Override
    public MedicalStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_cleared_list,parent,false);
       // view.setOnClickListener(SelectedCandidateList.myOnClickListener);
        MedicalStatusAdapter.MyViewHolder myViewHolder = new MedicalStatusAdapter.MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MedicalStatusAdapter.MyViewHolder holder, final int listPosition) {
        ImageView profile = holder.profile;
        TextView fullname = holder.fullname;
        TextView candidate_email = holder.candidate_email;
        TextView candidate_phone = holder.candidate_phone;
        TextView candidate_job = holder.candidate_job;
        TextView candidate_nationality = holder.candidate_nationality;
        TextView candidate_ID = holder.candidate_ID;
        //fullname.setTextColor(ContextCompat.getColor(mContext, R.color.greenimage));

        item = mListItem.get(listPosition);
        if (Status.equals("medicalclearedlist")) {
            holder.fullname.setText(item.getfullname());
            holder.candidate_email.setText(item.getcandidate_email());
            holder.candidate_phone.setText(item.getcandidate_phone());
            holder.candidate_job.setText(item.getcandidate_job());
            holder.candidate_nationality.setText(item.getcandidate_nationality());
            holder.candidate_ID.setText(item.getcandidate_code());


            holder.itemView.findViewById(R.id.cmpy_empy_card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, OtherProfileActivity.class);
                    intent.putExtra("NAME", mListItem.get(listPosition).get_name());
                    intent.putExtra("STATUS", "medicalclearedlist");
                    intent.putExtra("ID", mListItem.get(listPosition).getcandidate_id());

                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.bottom_up,
                            android.R.anim.fade_out);
                    mContext.finish();

                }
            });


        }
        else if(Status.equals("medicalfailedlist")){
            holder.fullname.setText(item.getfullname());
            holder.candidate_email.setText(item.getcandidate_email());
            holder.candidate_phone.setText(item.getcandidate_phone());
            holder.candidate_job.setText(item.getcandidate_job());
            holder.candidate_nationality.setText(item.getcandidate_nationality());
            holder.candidate_ID.setText(item.getcandidate_code());


        }
        else if(Status.equals("updatemedicalstatus")){
            holder.fullname.setText(item.getfullname());
            holder.candidate_email.setText(item.getcandidate_email());
            holder.candidate_phone.setText(item.getcandidate_phone());
            holder.candidate_job.setText(item.getcandidate_job());
            holder.candidate_nationality.setText(item.getcandidate_nationality());
            holder.candidate_ID.setText(item.getcandidate_code());



        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

}