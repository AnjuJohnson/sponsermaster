package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Kris on 3/3/2017.
 */

public class VisaReportAdapter extends RecyclerView.Adapter {

    private List<ListItem> mListitem;
    public Context mContext;
    String mStatus;

    public VisaReportAdapter(Context mContext, List<ListItem> listitem,
                             String Status) {
        this.mListitem = listitem;
        this.mContext = mContext;
        this.mStatus = Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.visareport_item, null, false);
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

        myViewHolder.slno.setText("\t"+(position+1));
        myViewHolder.title1.setText("\t"+dataItem.get_name());
        myViewHolder.title2.setText(dataItem.get_address());
        myViewHolder.title3.setText(dataItem.get_id());
        myViewHolder.title4.setText(dataItem.get_email());
        myViewHolder.title5.setText(dataItem.get_phone());
        myViewHolder.title6.setText(dataItem.get_manufacture());
        myViewHolder.title7.setText(dataItem.get_model());
        myViewHolder.title8.setText(dataItem.get_designation());
        myViewHolder.title9.setText(dataItem.get_email());
    }

    @Override
    public int getItemCount() {

        return mListitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView slno,title1, title2,
                title3, title4,
                title5, title6,
                title7, title8,title9;

        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            slno = (TextView) itemView.findViewById(R.id.slno);
            title1 = (TextView) itemView.findViewById(R.id.title1);
            title2 = (TextView) itemView.findViewById(R.id.title2);
            title3 = (TextView) itemView.findViewById(R.id.title3);
            title4 = (TextView) itemView.findViewById(R.id.title4);
            title5 = (TextView) itemView.findViewById(R.id.title5);
            title6 = (TextView) itemView.findViewById(R.id.title6);
            title7 = (TextView) itemView.findViewById(R.id.title7);
            title8 = (TextView) itemView.findViewById(R.id.title8);
            title9 = (TextView) itemView.findViewById(R.id.title9);
        }
    }
}
