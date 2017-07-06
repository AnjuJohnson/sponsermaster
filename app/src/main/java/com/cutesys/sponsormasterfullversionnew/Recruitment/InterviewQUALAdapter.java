package com.cutesys.sponsormasterfullversionnew.Recruitment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 5/06/2017.
 */

public class InterviewQUALAdapter  extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public InterviewQUALAdapter(Context context, ArrayList<ListItem> listitem,
                                String status){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView interqualstatus,interqualname,interqualid;

        public ViewHolder(View view) {
            super(view);

            interqualname = (TextView) view.findViewById(R.id.interqualname);
            interqualstatus = (TextView) view.findViewById(R.id.interqualstatus);
            interqualid = (TextView) view.findViewById(R.id.interqualid);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.qualification, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;
if(item.get_company_fee().equals("")){
    mViewHolder.interqualname.setText("None");
}
        else {
    mViewHolder.interqualname.setText(item.get_company_fee());
}
        if(item.get_company_fee().equals("")){
            mViewHolder.interqualstatus.setText("None");
        }
        else {
            mViewHolder.interqualstatus.setText(item.get_advance_fee());
        }
        if(item.get_company_fee().equals("")){
            mViewHolder.interqualid.setText("None");
        }
        else {
            mViewHolder.interqualid.setText(item.get_email());

        }





    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}
