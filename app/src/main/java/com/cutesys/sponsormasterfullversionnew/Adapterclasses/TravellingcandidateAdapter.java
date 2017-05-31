package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/1/2017.
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


import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Travelstatus.TravellingCandidateFragment;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class TravellingcandidateAdapter extends RecyclerView.Adapter<TravellingcandidateAdapter.ViewHolder>  {
    ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String status;
    View v;


    public TravellingcandidateAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView candidatename;
        TextView  dateoftravel;
TextView candi_code;
        TextView  arrivaltime;

        TextView  travellingstatus;

        public ViewHolder(View itemView) {
            super(itemView);
            this.candidatename = (TextView) itemView.findViewById(R.id.candidate_name);
            this.dateoftravel = (TextView) itemView.findViewById(R.id.dateoftravel);
            this.arrivaltime = (TextView) itemView.findViewById(R.id.arrivaltime);
            this.travellingstatus= (TextView) itemView.findViewById(R.id.travellingstatus);
            this.candi_code=(TextView)itemView.findViewById(R.id.candi_code1);
        }
    }

    public TravellingcandidateAdapter(ArrayList<RecruitmentListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travellingcandidate_card, parent, false);

        view.setOnClickListener(TravellingCandidateFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView candidatename=holder.candidatename;
        TextView  dateoftravel=holder.dateoftravel;
TextView candi_code=holder.candi_code;
        TextView  arrivaltime=holder.arrivaltime;

        TextView  travellingstatus=holder.travellingstatus;



candi_code.setText(mListItem.get(position).getcandidate_code());
        candidatename.setText(mListItem.get(position).getcandidate_name());
        dateoftravel.setText(mListItem.get(position).getdate_of_travel());
        arrivaltime.setText(mListItem.get(position).getArrival_time());
        travellingstatus.setText(mListItem.get(position).gettravelling_status());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}

