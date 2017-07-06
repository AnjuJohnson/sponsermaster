package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Athira on 3/2/2017.
 */
public class DocDetailsAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public DocDetailsAdapter(Context context, ArrayList<ListItem> listitem,
                             String status){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView data, name1, owner, owner1, date, date1, date12,
                date11;
LinearLayout mvisible;
        public ViewHolder(View view) {
            super(view);
            mvisible = (LinearLayout) view.findViewById(R.id.visible);
            data = (TextView) view.findViewById(R.id.data);
            name1 = (TextView) view.findViewById(R.id.name1);
            owner = (TextView) view.findViewById(R.id.owner);
            owner1 = (TextView) view.findViewById(R.id.owner1);
            date = (TextView) view.findViewById(R.id.date);
            date1 = (TextView) view.findViewById(R.id.date1);
            date12 = (TextView) view.findViewById(R.id.date12);
            date11 = (TextView) view.findViewById(R.id.date11);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.otrdocdetls_fragment, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.name1.setText(item.get_name());
        mViewHolder.data.setText(item.get_address());
        mViewHolder.date1.setText(item.get_code());
        mViewHolder.date11.setText(item.get_email());

        if (mStatus.equals("company")){

            mViewHolder.owner.setText("Owner:");
            mViewHolder.date.setText("Start Date:");
            mViewHolder.date12.setText("End Date:");
            mViewHolder.owner1.setText(item.get_phone());
        } else  if (mStatus.equals("employee")){

            mViewHolder.date.setText("Commencing Date:");
            mViewHolder.date12.setText("End Date:");
            mViewHolder.owner1.setVisibility(View.GONE);
            mViewHolder.owner.setVisibility(View.GONE);
        } else  if (mStatus.equals("vehicle")){

        } else  if (mStatus.equals("visa")){

        }
        else  if (mStatus.equals("candidate")){
            mViewHolder.name1.setText(item.get_name());
            mViewHolder.date.setText("Start Date:");
            mViewHolder.date12.setText("End Date:");
            if(item.get_code().equals("")){
                mViewHolder.date1.setText("None");
            }
            else {
                mViewHolder.date1.setText(item.get_code());
            }
            if(item.get_email().equals("")){
                mViewHolder.date11.setText("None");
            }
            else {
                mViewHolder.date11.setText(item.get_email());
            }


mViewHolder.data.setVisibility(View.GONE);

            mViewHolder.mvisible.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}