package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by Owner on 14/03/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.advancepaidlist;
import com.cutesys.sponsormasterfullversionnew.Payroll.AdvancePaidListFragment;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class advancepaidadapter extends RecyclerView.Adapter<advancepaidadapter.ViewHolder>  {
    ArrayList<advancepaidlist> mListItem;
    Context mContext;
    String status;
    View v;


    public advancepaidadapter(Context context, ArrayList<advancepaidlist> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView employeename1;
        TextView  salary1;
        TextView type;


        TextView  approved_loan_amount;
        TextView  repay_status;







        public ViewHolder(View itemView) {
            super(itemView);
            this.employeename1 = (TextView) itemView.findViewById(R.id.employeename1);
            this.salary1 = (TextView) itemView.findViewById(R.id.salary2);
            this.type = (TextView) itemView.findViewById(R.id.typepaid);


            this.approved_loan_amount = (TextView) itemView.findViewById(R.id.advance_amount1);
            this.repay_status = (TextView) itemView.findViewById(R.id.advance_status1);






        }
    }

    public advancepaidadapter(ArrayList<advancepaidlist> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advancepaid_card, parent, false);

        view.setOnClickListener(AdvancePaidListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView employeename1=holder.employeename1;
        TextView  salary1=holder.salary1;
        TextView  type=holder.type;


        TextView  approved_loan_amount=holder.approved_loan_amount;
        TextView  repay_status=holder.repay_status;



        employeename1.setText(mListItem.get(position).getemployee_name());
        salary1.setText(mListItem.get(position).getemployee_salary());
        type.setText("Type: " +mListItem.get(position).gettype());


        approved_loan_amount.setText(mListItem.get(position).getapproved_loan_amount());
       repay_status.setText("Status: " +mListItem.get(position).getrepay_status());




    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}

