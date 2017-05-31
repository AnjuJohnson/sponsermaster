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


import com.cutesys.sponsormasterfullversionnew.Helperclasses.workreportitem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.UserManagement.WorkListFragment;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class workreportadapter extends RecyclerView.Adapter<workreportadapter.ViewHolder>  {
    ArrayList<workreportitem> mListItem;
    Activity mContext;
    String status;
    View v;


    public workreportadapter(Activity context, ArrayList<workreportitem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        TextView  attendancedate;
        TextView designation;
        TextView emp_id;






        public ViewHolder(View itemView) {
            super(itemView);
            this.fullname = (TextView) itemView.findViewById(R.id.fullname2);
            this.attendancedate = (TextView) itemView.findViewById(R.id.attendancedate2);
            this.designation = (TextView) itemView.findViewById(R.id.designation2);
         this.emp_id=(TextView)itemView.findViewById(R.id.emp_id2);






        }
    }

    public workreportadapter(ArrayList<workreportitem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workreport_card, parent, false);

        view.setOnClickListener(WorkListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView fullname=holder.fullname;
        TextView  attendancedate=holder.attendancedate;
        TextView designation=holder.designation;
       TextView emp_id=holder.emp_id;

        fullname.setText(mListItem.get(position).getfull_name());
        attendancedate.setText(mListItem.get(position).getattendance_date());
        designation.setText( mListItem.get(position).getemployee_designation());
    emp_id.setText(mListItem.get(position).getemployee_employment_id());


        final workreportitem dataItem = mListItem.get(position);
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

