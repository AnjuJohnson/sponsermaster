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
import com.cutesys.sponsormasterfullversionnew.Recruitment.Travelstatus.TravelledCandidateFragment;


import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class CandidateTravelledAdapter extends RecyclerView.Adapter<CandidateTravelledAdapter.ViewHolder>  {
    ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String status;
    View v;
    public CandidateTravelledAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView candidatename;
        TextView  visatypename;
        TextView  visanumber;
        TextView  entrydate;
        TextView  nationality;
        TextView candi_code;
        TextView status;


        public ViewHolder(View itemView) {
            super(itemView);

            this.candidatename = (TextView) itemView.findViewById(R.id.candidate_name1);
            this.visatypename= (TextView) itemView.findViewById(R.id.visatypename2);
            this.visanumber= (TextView) itemView.findViewById(R.id.visa_number1);
            this.entrydate = (TextView) itemView.findViewById(R.id.entrydate);
            this.nationality = (TextView) itemView.findViewById(R.id.nationality2);
            this.candi_code = (TextView) itemView.findViewById(R.id.candi_code2);
            this.status = (TextView) itemView.findViewById(R.id.status1);


        }
    }

    public CandidateTravelledAdapter(ArrayList<RecruitmentListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travelledcandidate_card, parent, false);

        view.setOnClickListener(TravelledCandidateFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView candidatename=holder.candidatename;
        TextView  visatypename=holder.visatypename;
        TextView  visanumber=holder.visanumber;
        TextView  entrydate=holder.entrydate;
        TextView  nationality=holder.nationality;
        TextView  candi_code=holder.candi_code;
        TextView  status=holder.status;




        candidatename.setText(mListItem.get(position).getfullname());
        visatypename.setText(mListItem.get(position).getvisa_type_name());
        visanumber.setText(mListItem.get(position).getvisa_no());
        entrydate.setText(mListItem.get(position).getentry_date());
        nationality.setText(mListItem.get(position).getcandidate_nationality());
        candi_code.setText(mListItem.get(position).getcandidate_code());
       status.setText(mListItem.get(position).getmedical_status());

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}
