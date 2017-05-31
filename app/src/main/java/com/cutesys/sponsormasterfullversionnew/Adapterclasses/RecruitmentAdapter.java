package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.AgentProfileActivity;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
//import com.cutesys.sponsormasterfullversionnew.VisaItems.VisaProfile;
import com.cutesys.sponsormasterfullversionnew.Recruitment.AgentListFragment;

import java.util.ArrayList;

/**
 * Created by user on 3/13/2017.
 */

public class RecruitmentAdapter extends RecyclerView.Adapter<RecruitmentAdapter.MyViewHolder>  {
    private ArrayList<RecruitmentListItem> mListItem;
    Activity mContext;
    String Status;
    RecruitmentListItem item;
    View v;

    public RecruitmentAdapter(Activity context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //        ImageView profilePicname;
        TextView agent_company,agent_place,agent_country,agent_areaid;
        ImageView profile;



        public MyViewHolder(View itemView){
            super(itemView);
//            this.profilePicname=(ImageView) itemView.findViewById(R.id.profilePicname);
            this.profile=(ImageView) itemView.findViewById(R.id.profile);
            this.agent_company=(TextView) itemView.findViewById(R.id.agent_company);
            this.agent_areaid=(TextView) itemView.findViewById(R.id.agent_areaid);
            this.agent_place=(TextView) itemView.findViewById(R.id.agent_place);
            this.agent_country=(TextView) itemView.findViewById(R.id.agent_country);

        }

    }
    public RecruitmentAdapter(ArrayList<RecruitmentListItem> data){
        this.mListItem=data;
    }


    @Override
    public RecruitmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_list,parent,false);
        view.setOnClickListener(AgentListFragment.myOnClickListener);
        RecruitmentAdapter.MyViewHolder myViewHolder = new RecruitmentAdapter.MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final RecruitmentAdapter.MyViewHolder holder, final int listPosition) {
//        ImageView profilePicname = holder.profilePicname;
        ImageView profile = holder.profile;
        TextView agent_company = holder.agent_company;
        TextView agent_areaid = holder.agent_areaid;
        TextView agent_place = holder.agent_place;
        TextView agent_country = holder.agent_country;

        agent_company.setTextColor(ContextCompat.getColor(mContext, R.color.greenimage));

        item = mListItem.get(listPosition);
        if (Status.equals("agent")) {
            holder.agent_company.setText(item.getagent_company());
            holder.agent_areaid.setText(item.getagent_area_id());
            holder.agent_place.setText(item.getagent_place());
            holder.agent_country.setText(item.getagent_country());


        }

       holder.itemView.findViewById(R.id.cmpy_empy_card).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",mListItem.get(listPosition).getagent_company());
                intent.putExtra("STATUS","agent");
                intent.putExtra("ID",item.getagent_id());


               // intent.putExtra("IMG",item.getImage().replaceAll(" ","%20"));
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                mContext.finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }


}