package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/3/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.basicsalarylist;
import com.cutesys.sponsormasterfullversionnew.Payroll.report_salary;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class reportsalaryadapter extends RecyclerView.Adapter<reportsalaryadapter.ViewHolder>  {
    ArrayList<basicsalarylist> mListItem;
    Context mContext;
    String status;
    View v;


    public reportsalaryadapter(Context context, ArrayList<basicsalarylist> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        TextView title1;
        TextView  title2;
        TextView  title3;
        TextView title4;
        TextView  title5;
        public ViewHolder(View itemView) {
            super(itemView);
            this.layout=(LinearLayout)itemView.findViewById(R.id.layout);
            this.title1 = (TextView) itemView.findViewById(R.id.title1);
            this.title2 = (TextView) itemView.findViewById(R.id.title2);
            this.title3 = (TextView) itemView.findViewById(R.id.title3);
            this.title4 = (TextView) itemView.findViewById(R.id.title4);
            this.title5 = (TextView) itemView.findViewById(R.id.title5);
        }
    }

    public reportsalaryadapter(ArrayList<basicsalarylist> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salaryreport_card, parent, false);

        view.setOnClickListener(report_salary.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView title1=holder.title1;
        TextView title2=holder.title2;
        TextView title3=holder.title3;
        TextView title4=holder.title4;
        TextView title5=holder.title5;


        if (position%2 == 0){
            holder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        title1.setText(mListItem.get(position).getemployee_id());
        title2.setText(mListItem.get(position).getfull_name());
        title3.setText(mListItem.get(position).getemployee_designation());
        title4.setText( mListItem.get(position).getemployee_salary());
        title5.setText(mListItem.get(position).getcompany_name());




    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}
