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
import com.cutesys.sponsormasterfullversionnew.Recruitment.Travelstatus.NotifiedCandidateFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class NotifiedCandidateAdapter extends RecyclerView.Adapter<NotifiedCandidateAdapter.ViewHolder>  {
    ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String status;
    View v;


    public NotifiedCandidateAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView candidatename;
        TextView  visatypename;
        TextView candi_code;

        TextView  visanumber;
        TextView  visaexpiry;
        public ViewHolder(View itemView) {
            super(itemView);


            this.candidatename = (TextView) itemView.findViewById(R.id.candidatename1);
            this.visatypename = (TextView) itemView.findViewById(R.id.visa_typename);
this.candi_code=(TextView)itemView.findViewById(R.id.candi_code);
            this.visanumber= (TextView) itemView.findViewById(R.id.visa_number);
            this.visaexpiry= (TextView) itemView.findViewById(R.id.visa_expiry);
        }
    }

    public NotifiedCandidateAdapter(ArrayList<RecruitmentListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifiedcandidate_card, parent, false);

        view.setOnClickListener(NotifiedCandidateFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        TextView candidatename=holder.candidatename;
        TextView  visatypename=holder.visatypename;
TextView candi_code=holder.candi_code;
        TextView  visanumber=holder.visanumber;
        TextView  visaexpiry=holder.visaexpiry;
        candidatename.setText(mListItem.get(position).getfullname());
        visatypename.setText(mListItem.get(position).getvisa_type_name());
candi_code.setText(mListItem.get(position).getcandidate_code());
        visanumber.setText(mListItem.get(position).getvisa_no());
        visaexpiry.setText(mListItem.get(position).getvisa_expiry());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

}
