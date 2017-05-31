package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/29/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScroller;
import com.cutesys.sponsormasterfullversionnew.Company.BankListFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class BankItemsAdapter extends RecyclerView.Adapter<BankItemsAdapter.ViewHolder> implements FastScroller.SectionIndexer {
    ArrayList<ListItem> mListItem;
    Context mContext;
    String status;
    View v;


    public BankItemsAdapter(Context context, ArrayList<ListItem> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(mListItem.get(position).get_name().charAt(0));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namebank;
        TextView  code;
        TextView  branch;
        TextView  country;
        public ViewHolder(View itemView) {
            super(itemView);
            this.namebank = (TextView) itemView.findViewById(R.id.namebank);
            this.code = (TextView) itemView.findViewById(R.id.code);
            this.branch = (TextView) itemView.findViewById(R.id.branch);
            this.country = (TextView) itemView.findViewById(R.id.country);
        }
    }

    public BankItemsAdapter(ArrayList<ListItem> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_card, parent, false);
        view.setOnClickListener(BankListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextView namebank = holder.namebank;
        TextView code = holder.code;
        TextView branch = holder.branch;
        TextView country = holder.country;
        namebank.setText(mListItem.get(position).get_name());
        code.setText("Code: "+mListItem.get(position).get_code());
        branch.setText("Branch: " +mListItem.get(position).get_address());
        country.setText("Country : " +mListItem.get(position).get_email());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}