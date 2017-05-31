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
import com.cutesys.sponsormasterfullversionnew.Recruitment.Visa.VisaRejectedFragment;
import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class VisaRejectedAdapter extends RecyclerView.Adapter<VisaRejectedAdapter.ViewHolder>  {
    ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String status;
    View v;


    public VisaRejectedAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView candidatename;
        TextView job;
        TextView candi_code;
        TextView  visatype;
        TextView  nationality;
        TextView  reason;

        public ViewHolder(View itemView) {
            super(itemView);

            this.candidatename = (TextView) itemView.findViewById(R.id.candidatename);

            this.job = (TextView) itemView.findViewById(R.id.job);
            this.candi_code= (TextView) itemView.findViewById(R.id.candi_code5);
            this.visatype= (TextView) itemView.findViewById(R.id.visatype1);
            this.nationality = (TextView) itemView.findViewById(R.id.nationality1);
            this.reason= (TextView) itemView.findViewById(R.id.reason);
        }
    }

    public VisaRejectedAdapter(ArrayList<RecruitmentListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visarejected_card, parent, false);

        view.setOnClickListener(VisaRejectedFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView candidatename=holder.candidatename;

        TextView job=holder.job;
        TextView  visatype=holder.visatype;
        TextView  nationality=holder.nationality;
        TextView  reason=holder.reason;
        TextView  candi_code=holder.candi_code;
        candi_code.setText(mListItem.get(position).getcandidate_code());

        candidatename.setText(mListItem.get(position).getfullname());

        job.setText(mListItem.get(position).getcandidate_job());
        visatype.setText(mListItem.get(position).getvisa_type_name());
        nationality.setText(mListItem.get(position).getcandidate_nationality());
        reason.setText(mListItem.get(position).getreason());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}