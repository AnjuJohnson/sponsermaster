package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

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
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Recruitment.SelectionStatus.RejectedCandidateListFragment;


import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class RejectedCandidateAdapter extends RecyclerView.Adapter<RejectedCandidateAdapter.ViewHolder>  {
    ArrayList<CandidateListItem> mListItem;
    Context mContext;
    String status;
    View v;


    public RejectedCandidateAdapter(Context context, ArrayList<CandidateListItem> listItem, String status) {
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





        public ViewHolder(View itemView) {
            super(itemView);

            this.fullname = (TextView) itemView.findViewById(R.id.candidatefullname);
            this.email = (TextView) itemView.findViewById(R.id.candidateemail);
            this.phone = (TextView) itemView.findViewById(R.id.candidatephone);
            this.job_position= (TextView) itemView.findViewById(R.id.job_position);
            this.candicode=(TextView)itemView.findViewById(R.id.candicode);



        }
    }

    public RejectedCandidateAdapter(ArrayList<CandidateListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_card, parent, false);

        view.setOnClickListener(RejectedCandidateListFragment.myOnClickListener);

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





        fullname.setText(mListItem.get(position).getfullname());
        email.setText(mListItem.get(position).getcandidate_email());
        phone.setText(mListItem.get(position).getcandidate_phone());
        job_position.setText(mListItem.get(position).getcandidate_job());
        candicode.setText(mListItem.get(position).getcandidate_code());




    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}