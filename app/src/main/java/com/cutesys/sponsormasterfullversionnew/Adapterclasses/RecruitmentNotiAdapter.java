package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;

import java.util.List;

/**
 * Created by user on 3/22/2017.
 */

public class RecruitmentNotiAdapter extends RecyclerView.Adapter {

    private List<RecruitmentListItem> mitems;
    public Activity mContext;

    String mstatus;

    public RecruitmentNotiAdapter(Activity mContext, List<RecruitmentListItem> items) {
        this.mitems = items;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.visa_validity_expiry_today, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        final RecruitmentListItem dataItem = mitems.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.notification_data.setText(dataItem.getnotification_data());
    }

    @Override
    public int getItemCount() {

        return mitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView notification_data;

        public MyViewHolder(View itemView) {
            super(itemView);

            notification_data = (TextView) itemView.findViewById(R.id.notification_data);
        }
    }
}
