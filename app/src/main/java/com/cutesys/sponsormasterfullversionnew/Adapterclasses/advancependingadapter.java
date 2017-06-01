package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by Owner on 15/03/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.advancependinglist;
import com.cutesys.sponsormasterfullversionnew.Payroll.AdvancePendingListFragment;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class advancependingadapter extends RecyclerView.Adapter<advancependingadapter.ViewHolder>  {
    ArrayList<advancependinglist> mListItem;
    Context mContext;
    String status;
    View v;


    public advancependingadapter(Context context, ArrayList<advancependinglist> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView employeename2;
        TextView  salary3;


        TextView  approved_loan_amount;
        TextView  repay_status;
        TextView  typepending;








        public ViewHolder(View itemView) {
            super(itemView);
            this.employeename2 = (TextView) itemView.findViewById(R.id.employeename2);
            this.salary3 = (TextView) itemView.findViewById(R.id.salary3);
        //    this.typepending = (TextView) itemView.findViewById(R.id.typepending);


            this.approved_loan_amount = (TextView) itemView.findViewById(R.id.advance_amount2);
            this.repay_status= (TextView) itemView.findViewById(R.id.advance_status2);






        }
    }

    public advancependingadapter(ArrayList<advancependinglist> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advancepending_card, parent, false);

        view.setOnClickListener(AdvancePendingListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView employeename2=holder.employeename2;
        TextView  salary3=holder.salary3;
      //  TextView  typepending=holder.typepending;


        TextView  approved_loan_amount=holder.approved_loan_amount;
        TextView  repay_status=holder.repay_status;



        employeename2.setText(mListItem.get(position).getemployee_name());
        salary3.setText(mListItem.get(position).getemployee_salary());
     //   typepending.setText("Type: " +mListItem.get(position).gettype());


       approved_loan_amount.setText(mListItem.get(position).getapproved_loan_amount());
        repay_status.setText("Status: " +mListItem.get(position).getrepay_status());




    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}


