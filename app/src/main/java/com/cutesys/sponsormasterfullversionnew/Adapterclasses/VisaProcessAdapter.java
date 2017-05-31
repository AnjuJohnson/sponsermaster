package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/29/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Report.ReportVisaProcess;

import java.util.ArrayList;

/**
 * Created by Owner on 29/03/2017.
 */

public class VisaProcessAdapter extends RecyclerView.Adapter<VisaProcessAdapter.MyViewHolder>  {
    private ArrayList<RecruitmentListItem> mListItem;
    Context mContext;
    String Status;
    RecruitmentListItem item;
    View v;

    public VisaProcessAdapter(Context context, ArrayList<RecruitmentListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        Status = status;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title1,title2,title4,title5,title6,title7,title8,title9;
        public  LinearLayout layout,status;
        ImageView title3;


        public MyViewHolder(View itemView){
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            status = (LinearLayout) itemView.findViewById(R.id.status);
            this.title1=(TextView) itemView.findViewById(R.id.title1);
            this.title2=(TextView) itemView.findViewById(R.id.title2);
            this.title3=(ImageView) itemView.findViewById(R.id.title3);
            this.title4=(TextView) itemView.findViewById(R.id.title4);
            this.title5=(TextView) itemView.findViewById(R.id.title5);
            this.title6=(TextView) itemView.findViewById(R.id.title6);
            this.title7=(TextView) itemView.findViewById(R.id.title7);
            this.title8=(TextView) itemView.findViewById(R.id.title8);
            this.title9=(TextView) itemView.findViewById(R.id.title9);
        }

    }
    public VisaProcessAdapter(ArrayList<RecruitmentListItem> data){
        this.mListItem=data;
    }


    @Override
    public VisaProcessAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_selection_item,parent,false);
        view.setOnClickListener(ReportVisaProcess.myOnClickListener);
        VisaProcessAdapter.MyViewHolder myViewHolder = new VisaProcessAdapter.MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final VisaProcessAdapter.MyViewHolder holder, final int listPosition) {
        TextView title1 = holder.title1;
        TextView title2 = holder.title2;
        TextView title4 = holder.title4;
        TextView title5 = holder.title5;
        TextView title6 = holder.title6;
        TextView title7 = holder.title7;
        TextView title8 = holder.title8;
        TextView title9 = holder.title9;

        if (listPosition % 2 == 0) {
            holder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        item = mListItem.get(listPosition);
        if (Status.equals("reportvisaprocess")) {
            holder.title1.setText(item.getcandidate_code());
            holder.title2.setText(item.getfullname());
            holder.title3.setVisibility(View.GONE);
            holder.title4.setText(item.getcandidate_nationality());
            holder.title5.setText(item.getcandidate_designation());
            holder.title6.setText(item.getvisa_status());
            holder.status.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);


        }
        if (Status.equals("reporttravelledprocess")) {
            holder.status.setVisibility(View.GONE);
            holder.title3.setVisibility(View.GONE);
            holder.title5.setVisibility(View.GONE);
            holder.title6.setVisibility(View.GONE);
            holder.title1.setText(item.getcandidate_code());
            holder.title2.setText(item.getfullname());
            holder.title4.setText(item.getcandidate_nationality());
            holder.title7.setText(item.getcandidate_entry_date());
            holder.title8.setText(item.getvisa_type_name());
            holder.title9.setText(item.getvisa_no());



        }
        if(Status.equals("reportselection")){
            holder.title1.setText(item.getcandidate_code());
            holder.title2.setText(item.getfullname());
            holder.title3.setVisibility(View.GONE);
            holder.title4.setText(item.getcandidate_designation());
            holder.title5.setText(item.getcandidate_nationality());
            holder.title6.setText(item.getcandidate_email());
            holder.title7.setText(item.getcandidate_phone());
            holder.title8.setText(item.getvisa_status());
            holder.status.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
        }
        if(Status.equals("reportmedical")){
            holder.title1.setText(item.getcandidate_code());
            holder.title2.setText(item.getfullname());
            holder.title3.setVisibility(View.GONE);
            holder.title4.setText(item.getcandidate_designation());
            holder.title5.setText(item.getcandidate_nationality());
            holder.title6.setText(item.getcandidate_email());
            holder.title7.setText(item.getcandidate_phone());
            holder.title8.setText(item.getvisa_status());
            holder.status.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);
        }
        if (Status.equals("reporttravelling")) {
            holder.title1.setText(item.getcandidate_code());
            holder.title2.setText(item.getfullname());
            holder.title3.setVisibility(View.GONE);
            holder.title4.setText(item.getcandidate_designation());
            holder.title5.setText(item.getcandidate_email());
            holder.title6.setText(item.getcandidate_phone());
            holder.status.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);


        }
        if (Status.equals("visaapplied")) {
            holder.title1.setVisibility(View.GONE);
            holder.title2.setText(item.getfullname());
            holder.title3.setVisibility(View.GONE);
            holder.title4.setText(item.getcandidate_designation());
            holder.title5.setText(item.getcandidate_code());
            holder.title6.setText(item.getcandidate_email());
            holder.status.setVisibility(View.GONE);
            holder.title7.setVisibility(View.GONE);
            holder.title8.setVisibility(View.GONE);
            holder.title9.setVisibility(View.GONE);


        }

    }
    @Override
    public int getItemCount() {
        return mListItem.size();
    }


}