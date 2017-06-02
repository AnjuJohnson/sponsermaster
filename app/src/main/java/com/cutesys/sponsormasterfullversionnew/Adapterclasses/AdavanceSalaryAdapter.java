package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.advancepaidlist;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.Payroll.AdvancePaidListFragment;
import com.cutesys.sponsormasterfullversionnew.Payroll.AdvanceSalaryFragment;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 1/06/2017.
 */

public class AdavanceSalaryAdapter extends RecyclerView.Adapter<AdavanceSalaryAdapter.ViewHolder>  {
    ArrayList<advancepaidlist> mListItem;
    Activity mContext;
    View v;


    public AdavanceSalaryAdapter(Activity context, ArrayList<advancepaidlist> listItem) {
        mListItem = listItem;
        mContext = context;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView employeename1;
        TextView  salary1;

TextView req_date;
        TextView emp_id;
        TextView  approved_loan_amount;
        TextView  repay_status;

CardView mProfile;





        public ViewHolder(View itemView) {
            super(itemView);
            this.employeename1 = (TextView) itemView.findViewById(R.id.employeename1);
            this.salary1 = (TextView) itemView.findViewById(R.id.salary2);
               this.req_date = (TextView) itemView.findViewById(R.id.req_date);
            this.emp_id = (TextView) itemView.findViewById(R.id.emp_id);

            this.approved_loan_amount = (TextView) itemView.findViewById(R.id.advance_amount1);
            this.repay_status = (TextView) itemView.findViewById(R.id.advance_status1);
            this.mProfile = (CardView) itemView.findViewById(R.id.clickprofile);






        }
    }

    public AdavanceSalaryAdapter(ArrayList<advancepaidlist> data) {
        this.mListItem = data;
    }

    @Override
    public AdavanceSalaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adavancesalary_item, parent, false);

        view.setOnClickListener(AdvanceSalaryFragment.myOnClickListener);

        AdavanceSalaryAdapter.ViewHolder myViewHolder = new AdavanceSalaryAdapter.ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(AdavanceSalaryAdapter.ViewHolder holder, final int position) {

        TextView employeename1=holder.employeename1;
        TextView  salary1=holder.salary1;
        TextView  req_date=holder.req_date;

        TextView  emp_id=holder.emp_id;

        TextView  approved_loan_amount=holder.approved_loan_amount;
        TextView  repay_status=holder.repay_status;
CardView mProfile=holder.mProfile;


        employeename1.setText(mListItem.get(position).getemployee_name());
        salary1.setText(mListItem.get(position).getemployee_salary());
          req_date.setText(mListItem.get(position).gettype());

        emp_id.setText(mListItem.get(position).getemployee_employment_id());


        approved_loan_amount.setText(mListItem.get(position).getapproved_loan_amount());
        repay_status.setText(mListItem.get(position).getrepay_status());

/*mProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(mContext,OtherProfileActivity.class);
        intent.putExtra("NAME",mListItem.get(position).getemployee_name());
        intent.putExtra("ID",mListItem.get(position).getemployee_id());
        intent.putExtra("STATUS","employee");
        mContext.startActivity(intent);
        mContext.overridePendingTransition(R.anim.bottom_up,
                android.R.anim.fade_out);
        mContext.finish();
    }
});*/


    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}


