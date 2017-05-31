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


import com.cutesys.sponsormasterfullversionnew.Helperclasses.basicsalarylist;
import com.cutesys.sponsormasterfullversionnew.Payroll.SalaryListFragment;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class basicsalaryadapter extends RecyclerView.Adapter<basicsalaryadapter.ViewHolder>  {
    ArrayList<basicsalarylist> mListItem;
    Context mContext;
    String status;
    View v;


    public basicsalaryadapter(Context context, ArrayList<basicsalarylist> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullname;
        TextView  employeedesignation;
        TextView employeesalary;

        TextView  companyname;







        public ViewHolder(View itemView) {
            super(itemView);
            this.fullname = (TextView) itemView.findViewById(R.id.full_name);
            this.employeedesignation = (TextView) itemView.findViewById(R.id.e_designation);
            this.employeesalary = (TextView) itemView.findViewById(R.id.e_salary);

            this.companyname= (TextView) itemView.findViewById(R.id.com_name);







        }
    }

    public basicsalaryadapter(ArrayList<basicsalarylist> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salary_card, parent, false);

        view.setOnClickListener(SalaryListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView fullname=holder.fullname;
        TextView  employeedesignation=holder.employeedesignation;
        TextView employeesalary=holder.employeesalary;

        TextView  companyname=holder.companyname;



        fullname.setText(mListItem.get(position).getfull_name());
        employeedesignation.setText(mListItem.get(position).getemployee_designation());
       employeesalary.setText( mListItem.get(position).getemployee_salary());

        companyname.setText(mListItem.get(position).getcompany_name());




    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}
