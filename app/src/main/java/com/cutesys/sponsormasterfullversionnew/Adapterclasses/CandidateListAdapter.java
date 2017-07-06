package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.CandidateListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Recruitment.SelectionStatus.CandidateListFragment;


import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.ViewHolder>  {
    ArrayList<CandidateListItem> mListItem;
    Activity mContext;
    String status;
    View v;


    public CandidateListAdapter(Activity context, ArrayList<CandidateListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView  fullname;
        TextView email;
        TextView  phone;
        TextView  job_position;
        TextView candicode;

        CardView candidatecard;



        public ViewHolder(View itemView) {
            super(itemView);

            this.fullname = (TextView) itemView.findViewById(R.id.candidatefullname);
            this.email = (TextView) itemView.findViewById(R.id.candidateemail);
            this.phone = (TextView) itemView.findViewById(R.id.candidatephone);
            this.job_position= (TextView) itemView.findViewById(R.id.job_position);
            this.candicode= (TextView) itemView.findViewById(R.id.candicode);
            this.candidatecard= (CardView) itemView.findViewById(R.id.candidatecard);



        }
    }

    public CandidateListAdapter(ArrayList<CandidateListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_card, parent, false);

        view.setOnClickListener(CandidateListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView  fullname=holder.fullname;
        TextView email=holder.email;
        TextView  phone=holder.phone;
        TextView  job_position=holder.job_position;
        TextView candicode=holder.candicode;
        holder.candidatecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",mListItem.get(position).getfullname());
                intent.putExtra("ID",mListItem.get(position).getcandidate_id());
                if(status.equals("candidate")) {

                    intent.putExtra("STATUS","candidate");

                }

                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                mContext.finish();
            }
        });






        fullname.setText(mListItem.get(position).getfullname());
        email.setText(mListItem.get(position).getcandidate_email());
        phone.setText(mListItem.get(position).getcandidate_phone());
        job_position.setText(mListItem.get(position).getapplication_job_position());
        candicode.setText(mListItem.get(position).getcandidate_code());



    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}

