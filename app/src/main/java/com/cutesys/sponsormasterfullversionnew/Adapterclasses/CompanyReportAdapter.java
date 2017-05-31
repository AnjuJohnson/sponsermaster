package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Kris on 3/3/2017.
 */

public class CompanyReportAdapter extends RecyclerView.Adapter{

    private List<ListItem> mListitem;
    public Context mContext;
    String mStatus;

    public CompanyReportAdapter(Context mContext, List<ListItem> listitem,
                                String Status) {
        this.mListitem = listitem;
        this.mContext = mContext;
        this.mStatus = Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.report_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        final ListItem dataItem = mListitem.get(position);
        ViewHolder myViewHolder = (ViewHolder) viewHolder;

        if (position%2 == 0){
            myViewHolder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            myViewHolder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if(mStatus.equals("company")) {
            myViewHolder.title6.setVisibility(View.GONE);

            if(dataItem.get_phone().equals("1")){
                myViewHolder.title3.setImageResource(R.mipmap.ic_active);
            } else {
                myViewHolder.title3.setImageResource(R.mipmap.ic_fail);
            }
            myViewHolder.title4.setText(dataItem.get_address());
            myViewHolder.title5.setText(dataItem.get_email());
            myViewHolder.title1.setText(dataItem.get_pid());
            myViewHolder.title2.setText(dataItem.get_name());

        } else if(mStatus.equals("employee")) {
            myViewHolder.status.setVisibility(View.GONE);
            myViewHolder.title6.setText(dataItem.get_email());
            myViewHolder.title5.setText(dataItem.get_address());
            myViewHolder.title4.setText(dataItem.get_designation());
            myViewHolder.title1.setText(dataItem.get_employment_id());
            myViewHolder.title2.setText(dataItem.get_name());
        }
        else if(mStatus.equals("vehicle")) {
            myViewHolder.status.setVisibility(View.GONE);
            myViewHolder.title1.setVisibility(View.GONE);
            myViewHolder.title6.setText(dataItem.get_email());
            myViewHolder.title5.setText(dataItem.get_designation());
            myViewHolder.title2.setText(dataItem.get_id());
            myViewHolder.title4.setText(dataItem.get_name());
        }
    }

    @Override
    public int getItemCount() {

        return mListitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title1, title2,
                title4,
                title5, title6;
        private ImageView title3;

        public LinearLayout layout,status;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            status = (LinearLayout) itemView.findViewById(R.id.status);
            title1 = (TextView) itemView.findViewById(R.id.title1);
            title2 = (TextView) itemView.findViewById(R.id.title2);
            title3 = (ImageView) itemView.findViewById(R.id.title3);
            title4 = (TextView) itemView.findViewById(R.id.title4);
            title5 = (TextView) itemView.findViewById(R.id.title5);
            title6 = (TextView) itemView.findViewById(R.id.title6);
        }
    }
}