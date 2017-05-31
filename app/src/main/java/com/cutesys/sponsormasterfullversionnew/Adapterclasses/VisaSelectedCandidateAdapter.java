package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Recruitment.SelectionStatus.VisaSelectedCandidateList;

import java.util.ArrayList;

/**
 * Created by user on 3/28/2017.
 */

public class VisaSelectedCandidateAdapter extends RecyclerView.Adapter<VisaSelectedCandidateAdapter.MyViewHolder>  {
    private ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String Status;
    RecruitmentListItem item;
    View v;

    public VisaSelectedCandidateAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //        ImageView profilePicname;
        TextView fullname,candidate_nationality,candidate_job;


        public MyViewHolder(View itemView){
            super(itemView);
            this.fullname=(TextView) itemView.findViewById(R.id.fullname);
            this.candidate_nationality=(TextView) itemView.findViewById(R.id.candidate_nationality);
            this.candidate_job=(TextView) itemView.findViewById(R.id.candidate_job);
        }

    }
    public VisaSelectedCandidateAdapter(ArrayList<RecruitmentListItem> data){
        this.mListItem=data;
    }


    @Override
    public VisaSelectedCandidateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.visa_selected_list,parent,false);
        view.setOnClickListener(VisaSelectedCandidateList.myOnClickListener);
        VisaSelectedCandidateAdapter.MyViewHolder myViewHolder = new VisaSelectedCandidateAdapter.MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final VisaSelectedCandidateAdapter.MyViewHolder holder, final int listPosition) {
//        ImageView profilePicname = holder.profilePicname;
//        ImageView profile = holder.profile;
        TextView fullname = holder.fullname;
        TextView candidate_nationality = holder.candidate_nationality;
        TextView candidate_job = holder.candidate_job;
//        LinearLayout swipeview=holder.swipeview;
//        CardView cardview4=holder.cardview4;

//        holder.profile.setImageResource(R.mipmap.ic_launcher);
//        holder.olinear.setBackgroundResource(R.color.blueimage);
//        fullname.setTextColor(ContextCompat.getColor(mContext, R.color.blueimage));



//        profilePicname.setImageResource(R.mipmap.ic_launcher);
//        profile.setImageResource(R.mipmap.ic_launcher);
        item = mListItem.get(listPosition);
        if (Status.equals("visaselectedlist")) {
            holder.fullname.setText(item.getfullname());
            holder.candidate_job.setText(item.getcandidate_job());
            holder.candidate_nationality.setText(item.getcandidate_nationality());

//

        }

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }


}