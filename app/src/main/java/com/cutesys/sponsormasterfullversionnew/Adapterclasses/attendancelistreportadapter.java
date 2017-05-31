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


import com.cutesys.sponsormasterfullversionnew.Helperclasses.attendancelistreportitem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.UserManagement.attendancelistreportfragment;


import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class attendancelistreportadapter extends RecyclerView.Adapter<attendancelistreportadapter.ViewHolder>  {
    ArrayList<attendancelistreportitem> mListItem;
    Activity mContext;
    String status;
    View v;


    public attendancelistreportadapter(Activity context, ArrayList<attendancelistreportitem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        TextView  attendancedate;
        TextView designation;
        TextView emp_id;
        TextView  punch_in_time;
        TextView  punch_in_location;
        TextView  punch_out_time;
        TextView   punch_out_location;
        TextView  punch_in_ip;
        TextView  punch_out_ip;






        public ViewHolder(View itemView) {
            super(itemView);
            this.fullname = (TextView) itemView.findViewById(R.id.fullname1);
            this.attendancedate = (TextView) itemView.findViewById(R.id.attendancedate);
            this.designation = (TextView) itemView.findViewById(R.id.ddesignation1);
         this.emp_id=(TextView)itemView.findViewById(R.id.emp_id1);
            this.punch_in_time = (TextView) itemView.findViewById(R.id.punchin);
            this.punch_in_location = (TextView) itemView.findViewById(R.id.punchloc);
            this.punch_out_time = (TextView) itemView.findViewById(R.id.punchout);
            this.punch_out_location = (TextView) itemView.findViewById(R.id.punchlocout);
            this.punch_in_ip = (TextView) itemView.findViewById(R.id.inip);
            this.punch_out_ip = (TextView) itemView.findViewById(R.id.outip);






        }
    }

    public attendancelistreportadapter(ArrayList<attendancelistreportitem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_card, parent, false);

        view.setOnClickListener(attendancelistreportfragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView fullname=holder.fullname;
        TextView  attendancedate=holder.attendancedate;
        TextView designation=holder.designation;
      TextView emp_id=holder.emp_id;
        TextView  punch_in_time=holder.punch_in_time;
        TextView  punch_in_location=holder.punch_in_location;
        TextView  punch_out_time=holder.punch_out_time;
        TextView   punch_out_location=holder.punch_out_location;
        TextView  punch_in_ip=holder.punch_in_ip;
        TextView  punch_out_ip=holder.punch_out_ip;

        fullname.setText(mListItem.get(position).getfull_name());
        attendancedate.setText(mListItem.get(position).getattendance_date());
        designation.setText( mListItem.get(position).getemployee_designation());
      emp_id.setText(mListItem.get(position).getemployee_employment_id());
        punch_in_time.setText("Time in: " +mListItem.get(position).getattendance_punch_in_time());
        punch_in_location.setText(mListItem.get(position).getattendance_punch_in_location());
        punch_out_time.setText("Time out: " +mListItem.get(position).getattendance_punch_out_time());
        punch_out_location.setText(mListItem.get(position).getattendance_punch_out_location());
       punch_in_ip.setText(mListItem.get(position).getattendance_punch_in_ip());
       punch_out_ip.setText(mListItem.get(position).getattendance_punch_out_ip());

        final attendancelistreportitem dataItem = mListItem.get(position);
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

