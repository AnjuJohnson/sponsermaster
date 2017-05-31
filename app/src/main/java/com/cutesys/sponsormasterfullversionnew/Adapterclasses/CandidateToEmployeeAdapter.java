package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/1/2017.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Recruitment.CandidateEmployee.CandidateToEmployee;

import java.util.ArrayList;

/**
 * Created by user on 3/20/2017.
 */

public class CandidateToEmployeeAdapter extends RecyclerView.Adapter<CandidateToEmployeeAdapter.MyViewHolder>  {
    private ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String Status;
    RecruitmentListItem item;
    View v;

    public CandidateToEmployeeAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView ce_fullname,ce_email,ce_visa_name,ce_doc_details,candi_code;



        public MyViewHolder(View itemView){
            super(itemView);

            this.ce_fullname=(TextView) itemView.findViewById(R.id.ce_fullname);
            this.ce_email=(TextView) itemView.findViewById(R.id.ce_email);
            this.ce_visa_name=(TextView) itemView.findViewById(R.id.ce_visa_name);
            this.ce_doc_details=(TextView) itemView.findViewById(R.id.doc_details);

            this.candi_code=(TextView) itemView.findViewById(R.id.candi_code3);

        }

    }
    public CandidateToEmployeeAdapter(ArrayList<RecruitmentListItem> data){
        this.mListItem=data;
    }


    @Override
    public CandidateToEmployeeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_to_employee,parent,false);
        view.setOnClickListener(CandidateToEmployee.myOnClickListener);
        CandidateToEmployeeAdapter.MyViewHolder myViewHolder = new CandidateToEmployeeAdapter.MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final CandidateToEmployeeAdapter.MyViewHolder holder, final int position) {

        TextView ce_fullname = holder.ce_fullname;
        TextView ce_email = holder.ce_email;
        TextView ce_visa_name = holder.ce_visa_name;
        TextView ce_doc_details = holder.ce_doc_details;

        TextView candi_code = holder.candi_code;


        ce_fullname.setText(mListItem.get(position).getfullname());
        ce_email.setText(mListItem.get(position).getcandidate_email());
        ce_visa_name.setText(mListItem.get(position).getvisa_type_name());
        ce_doc_details.setText(mListItem.get(position).getdoc_details());

        candi_code.setText(mListItem.get(position).getcandidate_code());

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }


}
