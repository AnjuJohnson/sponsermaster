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

public class InterviewReqAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public InterviewReqAdapter(Context context, ArrayList<ListItem> listitem,
                               String status){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reqnumber,reqjob,reqcategory,reqcode,reqid;

        public ViewHolder(View view) {
            super(view);

            reqnumber = (TextView) view.findViewById(R.id.reqnumber);
            reqjob = (TextView) view.findViewById(R.id.reqjob);
            reqcategory = (TextView) view.findViewById(R.id.reqcategory);
            reqcode = (TextView) view.findViewById(R.id.reqcode);
            reqid = (TextView) view.findViewById(R.id.reqid);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.requirmentxml, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.reqnumber.setText(item.get_name());
        mViewHolder.reqjob.setText(item.get_designation());
        mViewHolder.reqcategory.setText(item.get_amount());
        mViewHolder.reqcode.setText(item.get_code());
        mViewHolder.reqid.setText(item.get_advance_fee());


    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}
