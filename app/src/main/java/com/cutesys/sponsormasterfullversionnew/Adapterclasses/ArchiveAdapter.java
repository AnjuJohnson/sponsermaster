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
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Archive.CompanyArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ArchiveListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;


import java.util.ArrayList;

/**
 * Created by user on 3/15/2017.
 */

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.MyViewHolder>  {
    private ArrayList<ArchiveListItem> mListItem;
    Activity mContext;
    String Status;
   ArchiveListItem item;
    View v;

    public ArchiveAdapter(Activity context, ArrayList<ArchiveListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //        ImageView profilePicname;
        TextView title1,title2,title4,title5,title6,title7,title8,title9,title10,title11,title12,title13,title14;
//        ImageView title3;
        public LinearLayout layout;


        public MyViewHolder(View itemView){
            super(itemView);
//            this.profilePicname=(ImageView) itemView.findViewById(R.id.profilePicname);
//            this.profile=(ImageView) itemView.findViewById(R.id.profile);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
//            status = (LinearLayout) itemView.findViewById(R.id.status);
            this.title1=(TextView) itemView.findViewById(R.id.title1);
            this.title2=(TextView) itemView.findViewById(R.id.title2);
//            this.title3=(ImageView) itemView.findViewById(R.id.title3);
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
        }

    }
    public ArchiveAdapter(ArrayList<ArchiveListItem> data){
        this.mListItem=data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.archive_item,parent,false);
        view.setOnClickListener(CompanyArchiveFragment.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
//        ImageView profilePicname = holder.profilePicname;
//        ImageView profile = holder.profile;
        TextView title1 = holder.title1;
        TextView title2 = holder.title2;
//        ImageView title3 = holder.title3;
        TextView title4 = holder.title4;
        TextView title5 = holder.title5;
        TextView title6 = holder.title6;
        TextView title7 = holder.title7;
        TextView title8 = holder.title8;
        TextView title9 = holder.title9;
        TextView title10 = holder.title10;
        if (listPosition % 2 == 0) {
            holder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

//        holder.profile.setImageResource(R.mipmap.ic_launcher);
//        holder.olinear.setBackgroundResource(R.color.blueimage);
//        title1.setTextColor(ContextCompat.getColor(mContext, R.color.greenimage));

//holder.type.setText(item.getcompany_address());
//category.setText(item.getcompany_email());
//        client.setText(item.getCompany_manufacture());
//        sponsor.setText(item.getCompany_phone());
//        category.setText(item.getcompany_email());
//        payment.setText(item.getcompany_code());
//        total.setText(item.getCompany_model());
//        bank.setText(item.getvisa_bank_fee());
//        sponsorfee.setText(item.getvisa_sponsor_fee());
//        companyfee.setText(item.getvisa_company_fee());
//        advancefee.setText(item.getvisa_advance_fee());
//        balfee.setText(item.getvisa_balance_fee());


//        profilePicname.setImageResource(R.mipmap.ic_launcher);
//        profile.setImageResource(R.mipmap.ic_launcher);
        item = mListItem.get(listPosition);
        if (Status.equals("companyarchive")) {
            holder.title6.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
            holder.title10.setVisibility(View.GONE);
            holder.title11.setVisibility(View.GONE);
            holder.title12.setVisibility(View.GONE);
            holder.title13.setVisibility(View.GONE);
            holder.title14.setVisibility(View.GONE);
//            holder.title3.setVisibility(View.GONE);
            holder.title1.setText(item.getcompany_pid());
            holder.title2.setText(item.getcompany_name());
            holder.title4.setText(item.getcompany_address1());
            holder.title5.setText(item.getcompany_email());

            holder.itemView.findViewById(R.id.title2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,OtherProfileActivity.class);
                    intent.putExtra("NAME",mListItem.get(listPosition).get_name());
                    intent.putExtra("ID",mListItem.get(listPosition).get_id());

                    if(Status.equals("company")) {

                        intent.putExtra("STATUS","company");

                    } else if(Status.equals("employee")){

                        intent.putExtra("STATUS","employee");

                    }else if(Status.equals("flipcompany")){

                        intent.putExtra("STATUS","flipcompany");

                    }else if(Status.equals("flipemployee")){

                        intent.putExtra("STATUS","flipemployee");
                    }

                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.bottom_up,
                            android.R.anim.fade_out);
                    mContext.finish();

                }
            });
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
        if (Status.equals("employeearchive")) {
            holder.title1.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.title4.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title11.setVisibility(View.GONE);
            holder.title12.setVisibility(View.GONE);
            holder.title13.setVisibility(View.GONE);
            holder.title14.setVisibility(View.GONE);
//            holder.title3.setVisibility(View.GONE);
            holder.title7.setText(item.getemployee_name());
            holder.title8.setText(item.getemployee_designation());
            holder.title9.setText(item.getcompany_name());
            holder.title10.setText(item.getemployee_email());

            holder.itemView.findViewById(R.id.title7).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,OtherProfileActivity.class);
                    intent.putExtra("NAME",mListItem.get(listPosition).get_name());
                    intent.putExtra("ID",mListItem.get(listPosition).get_id());

                     if(Status.equals("employeearchive")){

                        intent.putExtra("STATUS","employee");

                    }else if(Status.equals("flipemployee")){

                        intent.putExtra("STATUS","flipemployee");
                    }

                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.bottom_up,
                            android.R.anim.fade_out);
                    mContext.finish();

                }
            });

        }
        if (Status.equals("vehicle_archive")) {
            holder.title1.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.title4.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
            holder.title10.setVisibility(View.GONE);
            holder.title11.setText(item.getvehicle_id());
            holder.title12.setText(item.getvehicle_company());
            holder.title13.setText(item.getvehicle_assigned_company());
            holder.title14.setText(item.getvehicle_assigned_employee());
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
