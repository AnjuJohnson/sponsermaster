package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.app.Activity;
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


import com.cutesys.sponsormasterfullversionnew.Company.CampListFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.CampListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;


import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class CampListAdapter extends RecyclerView.Adapter<CampListAdapter.ViewHolder>  {
    ArrayList<CampListItem> mListItem;
    Activity mContext;
    String status;
    View v;


    public CampListAdapter(Activity context, ArrayList<CampListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cname;
        TextView  camplocation;
        TextView  campname;
        TextView  campaddress;
        TextView  campmanager;
        TextView  campcontact;
        TextView  campemail;
        TextView  campintake;




        public ViewHolder(View itemView) {
            super(itemView);
            this.cname = (TextView) itemView.findViewById(R.id.cname1);
            this.camplocation = (TextView) itemView.findViewById(R.id.camplocation);
            this.campname = (TextView) itemView.findViewById(R.id.campname);
            this.campaddress = (TextView) itemView.findViewById(R.id.campaddress);
            this.campmanager= (TextView) itemView.findViewById(R.id.campmanager);
            this.campcontact = (TextView) itemView.findViewById(R.id.campcontact);
            this.campemail = (TextView) itemView.findViewById(R.id.campemail);
            this.campintake= (TextView) itemView.findViewById(R.id.campintake);



        }
    }

    public CampListAdapter(ArrayList<CampListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.camp_card, parent, false);

        view.setOnClickListener(CampListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final CampListItem dataItem = mListItem.get(position);
        ViewHolder myViewHolder = (ViewHolder) holder;

        TextView cname=holder.cname;
        TextView  camplocation=holder.camplocation;
        TextView  campname=holder.campname;
        TextView  campaddress=holder.campaddress;
        TextView  campmanager=holder.campmanager;
        TextView  campcontact=holder.campcontact;
        TextView  campemail=holder.campemail;
        TextView  campintake=holder.campintake;


        cname.setText(mListItem.get(position).getcompany_name());
        camplocation.setText(mListItem.get(position).getcamp_location());
        campname.setText(mListItem.get(position).getcamp_name());
        campaddress.setText(mListItem.get(position).getcamp_address());
        campmanager.setText(mListItem.get(position).getcamp_manager());
        campcontact.setText(mListItem.get(position).getcamp_contact());
        campemail.setText(mListItem.get(position).getcamp_email());
        campintake.setText(mListItem.get(position).getcamp_intake());



        myViewHolder.itemView.findViewById(R.id.cname1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",mListItem.get(position).getcompany_name());
                intent.putExtra("ID",mListItem.get(position).getcompany_id());
                  intent.putExtra("STATUS","company");


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