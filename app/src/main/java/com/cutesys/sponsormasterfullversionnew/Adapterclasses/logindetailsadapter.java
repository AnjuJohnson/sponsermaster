package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by Owner on 14/03/2017.
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


import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.logindetailsitem;
import com.cutesys.sponsormasterfullversionnew.UserManagement.LoginListFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class logindetailsadapter extends RecyclerView.Adapter<logindetailsadapter.ViewHolder>  {
    ArrayList<logindetailsitem> mListItem;
    Activity mContext;
    String status;
    View v;


    public logindetailsadapter(Activity context, ArrayList<logindetailsitem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        TextView  companyname;
        TextView  designation;
        TextView  username;
        TextView employeeid;
        TextView password;





        public ViewHolder(View itemView) {
            super(itemView);
            this.fullname = (TextView) itemView.findViewById(R.id.fullname);
            this.employeeid = (TextView) itemView.findViewById(R.id.employeeid);
            this.password= (TextView) itemView.findViewById(R.id.password1);
            this.companyname = (TextView) itemView.findViewById(R.id.companyname);
            this.designation = (TextView) itemView.findViewById(R.id.ddesignation);
            this.username = (TextView) itemView.findViewById(R.id.username);




        }
    }

    public logindetailsadapter(ArrayList<logindetailsitem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.login_card, parent, false);

        view.setOnClickListener(LoginListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView fullname = holder.fullname;
        TextView companyname = holder.companyname;
        TextView designation = holder.designation;
        TextView username = holder.username;
        TextView employeeid = holder.employeeid;
        TextView password = holder.password;

       employeeid.setText(mListItem.get(position).getemployee_employment_id());
       fullname.setText(mListItem.get(position).getfull_name());
       companyname.setText(mListItem.get(position).getcompany_name());
        designation.setText(mListItem.get(position).getemployee_designation());
        username.setText(mListItem.get(position).getemployee_username());
        password.setText(mListItem.get(position).getemployee_password());

        final logindetailsitem dataItem = mListItem.get(position);
        ViewHolder myViewHolder = (ViewHolder) holder;


        myViewHolder.itemView.findViewById(R.id.clickprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",mListItem.get(position).getfull_name());
                intent.putExtra("ID",mListItem.get(position).getemployee_id());
                intent.putExtra("STATUS","employee");


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
