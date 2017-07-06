package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

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
 * Created by Athira on 3/2/2017.
 */
public class BankDocDetailsAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public BankDocDetailsAdapter(Context context, ArrayList<ListItem> listitem,
                                 String status){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Bank,IBAN,Code,Account;

        public ViewHolder(View view) {
            super(view);

            Bank = (TextView) view.findViewById(R.id.Bank);
            Code = (TextView) view.findViewById(R.id.Code);
            Account = (TextView) view.findViewById(R.id.Account);
            IBAN = (TextView) view.findViewById(R.id.IBAN);



        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.bankdocdetails, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.Bank.setText(item.get_name());
        mViewHolder.Code.setText(item.get_address());
        mViewHolder.Account.setText(item.get_designation());
        mViewHolder.IBAN.setText(item.get_in());


         if (mStatus.equals("company")){
             mViewHolder.Bank.setText(item.get_name());
             mViewHolder.Code.setText(item.get_address());
             mViewHolder.Account.setText(item.get_designation());
             mViewHolder.IBAN.setText(item.get_in());

        }
        else if (mStatus.equals("branch")){
             mViewHolder.Bank.setText(item.get_name());
             mViewHolder.Code.setText(item.get_address());
             mViewHolder.Account.setText(item.get_designation());
             mViewHolder.IBAN.setText(item.get_in());
        }


    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}