package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Athira on 3/2/2017.
 */
public class Experiencedetailsadapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public Experiencedetailsadapter(Context context, ArrayList<ListItem> listitem,
                                    String status){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView company, designation, Fromtext, Totext,To,assettype,expirydate,expirydatetext,designationtext,From;

        public ViewHolder(View view) {
            super(view);

            company = (TextView) view.findViewById(R.id.company);
            designation = (TextView) view.findViewById(R.id.designation);
            Fromtext = (TextView) view.findViewById(R.id.Fromtext);
            Totext = (TextView) view.findViewById(R.id.Totext);
            assettype = (TextView) view.findViewById(R.id.assettype);
            expirydate = (TextView) view.findViewById(R.id.expirydate);
            expirydatetext = (TextView) view.findViewById(R.id.expirydatetext);
            designationtext = (TextView) view.findViewById(R.id.designationtext);
            From = (TextView) view.findViewById(R.id.From);
            To = (TextView) view.findViewById(R.id.To);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.otrexperienceitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        if (mStatus.equals("candidate")){
            Toast.makeText(mContext,mStatus,Toast.LENGTH_SHORT).show();
            mViewHolder.company.setText(item.get_name());
            mViewHolder.designationtext.setText(item.get_address());
            mViewHolder.designation.setText("Designation:");
            mViewHolder.From.setText("From:");
            mViewHolder.To.setText("To:");
            mViewHolder.Fromtext.setText(item.get_code());
            mViewHolder.Totext.setText(item.get_email());
            mViewHolder.assettype.setVisibility(View.GONE);
            mViewHolder.expirydate.setVisibility(View.GONE);
            mViewHolder.expirydatetext.setVisibility(View.GONE);

        }


    }



    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}