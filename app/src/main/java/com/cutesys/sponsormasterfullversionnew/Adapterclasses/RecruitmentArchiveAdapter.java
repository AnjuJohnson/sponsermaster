package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Archive.AgentArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ArchiveListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by user on 3/18/2017.
 */

public class RecruitmentArchiveAdapter extends RecyclerView.Adapter<RecruitmentArchiveAdapter.MyViewHolder>  {
    private ArrayList<ArchiveListItem> mListItem;
    Activity mContext;
    String Status;
    ArchiveListItem item;
    View v;

    public RecruitmentArchiveAdapter(Activity context, ArrayList<ArchiveListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //        ImageView profilePicname;
        TextView title1,title2,title4,title5,title6,title7,title8,title9,title10,title11,
                title12,title13,title14,title15,title16;
//        ImageView profile;
public LinearLayout layout;


        public MyViewHolder(View itemView){
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
//            this.profilePicname=(ImageView) itemView.findViewById(R.id.profilePicname);
//            this.profile=(ImageView) itemView.findViewById(R.id.profile);
            this.title1=(TextView) itemView.findViewById(R.id.title1);
            this.title2=(TextView) itemView.findViewById(R.id.title2);
            this.title4=(TextView) itemView.findViewById(R.id.title4);
            this.title5=(TextView) itemView.findViewById(R.id.title5);
            this.title6=(TextView) itemView.findViewById(R.id.title6);
            this.title7=(TextView) itemView.findViewById(R.id.title7);
            this.title8=(TextView) itemView.findViewById(R.id.title8);
            this.title9=(TextView) itemView.findViewById(R.id.title9);
            this.title10=(TextView) itemView.findViewById(R.id.title10);
            this.title11=(TextView) itemView.findViewById(R.id.title11);
            this.title12=(TextView) itemView.findViewById(R.id.title12);
            this.title13=(TextView) itemView.findViewById(R.id.title13);
            this.title14=(TextView) itemView.findViewById(R.id.title14);
            this.title15=(TextView) itemView.findViewById(R.id.title15);
            this.title16=(TextView) itemView.findViewById(R.id.title16);
        }

    }
    public RecruitmentArchiveAdapter(ArrayList<ArchiveListItem> data){
        this.mListItem=data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recruitment_archive_item,parent,false);
        view.setOnClickListener(AgentArchiveFragment.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
//        ImageView profilePicname = holder.profilePicname;
//        ImageView profile = holder.profile;
        TextView title1 = holder.title1;
        TextView title2 = holder.title2;
        TextView title4 = holder.title4;
        TextView title5 = holder.title5;
        TextView title6 = holder.title6;
//        TextView title1 = holder.title1;
        if (listPosition % 2 == 0) {
            holder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

//        holder.profile.setImageResource(R.mipmap.ic_launcher);
//        holder.olinear.setBackgroundResource(R.color.blueimage);
//        agent_company.setTextColor(ContextCompat.getColor(mContext, R.color.blueimage));


//        profilePicname.setImageResource(R.mipmap.ic_launcher);
//        profile.setImageResource(R.mipmap.ic_launcher);
        item = mListItem.get(listPosition);
        if (Status.equals("agentarchive")) {
            holder.title1.setText(item.getagent_area_id());
            holder.title2.setText(item.getagent_company());
            holder.title4.setVisibility(View.GONE);
            holder.title5.setText(item.getagent_place());
            holder.title6.setText(item.getagent_country());
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
            holder.title10.setVisibility(View.GONE);
            holder.title11.setVisibility(View.GONE);
            holder.title12.setVisibility(View.GONE);
            holder.title13.setVisibility(View.GONE);
            holder.title14.setVisibility(View.GONE);
            holder.title15.setVisibility(View.GONE);
            holder.title16.setVisibility(View.GONE);

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
        if (Status.equals("interviewarchive")) {
            holder.title1.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.title4.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title8.setText(item.getinterview_auto_id());
            holder.title7.setVisibility(View.GONE);
            holder.title9.setText(item.getcompany_name());
            holder.title10.setText(item.getinterview_name());
            holder.title11.setText(item.getplace());
            holder.title12.setVisibility(View.GONE);
            holder.title13.setVisibility(View.GONE);
            holder.title14.setVisibility(View.GONE);
            holder.title15.setVisibility(View.GONE);
            holder.title16.setVisibility(View.GONE);

        }
        if (Status.equals("candidatearchive")) {
            holder.title1.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.title4.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
            holder.title10.setVisibility(View.GONE);
            holder.title11.setVisibility(View.GONE);
            holder.title12.setText(item.getcandidate_code());
            holder.title13.setText(item.getfullname());
            holder.title14.setText(item.getapplication_job_position());
            holder.title15.setText(item.getcandidate_phone());
            holder.title16.setText(item.getcandidate_email());
        }
        else if(Status.equals("selectedarchive")){
            holder.title1.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.title4.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
            holder.title10.setVisibility(View.GONE);
            holder.title11.setVisibility(View.GONE);
            holder.title12.setText(item.getcandidate_code());
            holder.title13.setText(item.getfullname());
            holder.title14.setText(item.getcandidate_job());
            holder.title15.setText(item.getcandidate_phone());
            holder.title16.setText(item.getcandidate_email());
        }
        else if(Status.equals("retestarchive")){
            holder.title1.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.title4.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
            holder.title10.setVisibility(View.GONE);
            holder.title11.setVisibility(View.GONE);
            holder.title12.setText(item.getcandidate_code());
            holder.title13.setText(item.getfullname());
            holder.title14.setText(item.getcandidate_job());
            holder.title15.setText(item.getcandidate_phone());
            holder.title16.setText(item.getcandidate_email());
        }

        else if(Status.equals("rejectarchive")){
            holder.title1.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.title4.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
            holder.title10.setVisibility(View.GONE);
            holder.title11.setVisibility(View.GONE);
            holder.title12.setText(item.getcandidate_code());
            holder.title13.setText(item.getfullname());
            holder.title14.setText(item.getcandidate_job());
            holder.title15.setText(item.getcandidate_phone());
            holder.title16.setText(item.getcandidate_email());
        }


//        holder.swipeview.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext,Other_Profile.class);
//                intent.putExtra("ID",item.getcompany_id());
//                intent.putExtra("IMG",item.getImage().replaceAll(" ","%20"));
//                intent.putExtra("PAGE","visa");
//                mContext.startActivity(intent);
////                mContext.overridePendingTransition(R.anim.activity_open_translate,
////                        R.anim.activity_close_scale);
////                mContext.finish();
//
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return mListItem.size();
    }


}

