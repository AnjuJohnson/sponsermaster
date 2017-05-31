package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/1/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 3/14/2017.
 */

public class SelectedCandidateAdapter extends RecyclerView.Adapter<SelectedCandidateAdapter.MyViewHolder>  {
    private ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String Status;
    RecruitmentListItem item;
    View v;

    public SelectedCandidateAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //        ImageView profilePicname;
        TextView fullname,candidate_email,candidate_phone,candidate_job,candi_code,nationality;
//        ImageView profile;
//        LinearLayout olinear,swipeview;
//        CardView cardview4;


        public MyViewHolder(View itemView){
            super(itemView);
//            this.profilePicname=(ImageView) itemView.findViewById(R.id.profilePicname);
//            this.profile=(ImageView) itemView.findViewById(R.id.profile);
            this.fullname=(TextView) itemView.findViewById(R.id.fullname);
            this.candi_code=(TextView) itemView.findViewById(R.id.candi_code4);
            this.nationality=(TextView) itemView.findViewById(R.id.nationality3);
            this.candidate_email=(TextView) itemView.findViewById(R.id.candidate_email);
            this.candidate_phone=(TextView) itemView.findViewById(R.id.candidate_phone);
            this.candidate_job=(TextView) itemView.findViewById(R.id.candidate_job);
//            this.swipeview=(LinearLayout)itemView.findViewById(R.id.swipeview);
//            this.cardview4=(CardView) itemView.findViewById(R.id.cardview4);
//            olinear=(LinearLayout)itemView.findViewById(R.id.olinear);
        }

    }
    public SelectedCandidateAdapter(ArrayList<RecruitmentListItem> data){
        this.mListItem=data;
    }


    @Override
    public SelectedCandidateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_candidate,parent,false);
        //view.setOnClickListener(SelectedCandidateList.myOnClickListener);
        SelectedCandidateAdapter.MyViewHolder myViewHolder = new SelectedCandidateAdapter.MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final SelectedCandidateAdapter.MyViewHolder holder, final int listPosition) {
//        ImageView profilePicname = holder.profilePicname;
//        ImageView profile = holder.profile;
        TextView fullname = holder.fullname;

        TextView candidate_email = holder.candidate_email;
        TextView candi_code = holder.candi_code;
        TextView nationality = holder.nationality;
        TextView candidate_phone = holder.candidate_phone;
        TextView candidate_job = holder.candidate_job;
//        LinearLayout swipeview=holder.swipeview;
//        CardView cardview4=holder.cardview4;

//        holder.profile.setImageResource(R.mipmap.ic_launcher);
//        holder.olinear.setBackgroundResource(R.color.blueimage);
//        fullname.setTextColor(ContextCompat.getColor(mContext, R.color.blueimage));



//        profilePicname.setImageResource(R.mipmap.ic_launcher);
//        profile.setImageResource(R.mipmap.ic_launcher);
        item = mListItem.get(listPosition);
        if (Status.equals("selectedlist")) {
            holder.fullname.setText(item.getfullname());

            holder.candidate_email.setText(item.getcandidate_email());
            holder.candidate_phone.setText(item.getcandidate_phone());
            holder.candidate_job.setText(item.getcandidate_job());
            holder.candi_code.setText(item.getcandidate_code());
            holder.nationality.setText(item.getcandidate_nationality());

//            holder.profile.setVisibility(View.VISIBLE);
//            try {
//                Picasso.with(mContext)
//                        .load(item.getImage().replaceAll(" ", "%20"))
//                        .placeholder(R.drawable.profile)
//                        .error(R.drawable.profile)
//                        .into(holder.profile);
//            } catch (Exception e) {
//            }
//            holder.cardview4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, VisaProfile.class);
//                    intent.putExtra("ID", mListItem.get(listPosition).getagent_id());
////                    intent.putExtra("IMG", item.getImage().replaceAll(" ", "%20"));
//                    intent.putExtra("PAGE", "visa");
//                    mContext.startActivity(intent);
//                }
//            });


        }

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }


}