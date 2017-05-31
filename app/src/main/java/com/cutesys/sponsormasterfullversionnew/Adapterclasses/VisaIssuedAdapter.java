package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 4/1/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 21/03/2017.
 */

public class VisaIssuedAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Activity mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public VisaIssuedAdapter(Activity context, ArrayList<ListItem> listitem,
                           String status){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,visa_ref_no1,visa_number,visa_number1,amount,amount1,status,status1,entry,entry1,expiry,expiry1;
        ImageView profile;

        public ViewHolder(View view) {
            super(view);
            profile = (ImageView) view.findViewById(R.id.profile);


            name = (TextView) view.findViewById(R.id.name);
            visa_ref_no1 = (TextView) view.findViewById(R.id.visa_ref_no1);
            visa_number = (TextView) view.findViewById(R.id.visa_number);
            visa_number1 = (TextView) view.findViewById(R.id.visa_number1);
            amount = (TextView) view.findViewById(R.id.amount);
            amount1 = (TextView) view.findViewById(R.id.amount1);
            status = (TextView) view.findViewById(R.id.status);
            status1 = (TextView) view.findViewById(R.id.status1);

            entry = (TextView) view.findViewById(R.id.entry);
            entry1 = (TextView) view.findViewById(R.id.entry1);
            expiry = (TextView) view.findViewById(R.id.expiry);
            expiry1 = (TextView) view.findViewById(R.id.expiry1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.visa_list_item1, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.name.setText(item.get_name());

        if(mStatus.equals("issued")){
            mViewHolder.visa_ref_no1.setText(item.get_refno());
            mViewHolder.visa_number.setText("Visa Number :");
            mViewHolder.visa_number1.setText(item.get_visanum());
            mViewHolder.amount.setText("Balance :");
            mViewHolder.amount1.setText(item.get_amount());
            mViewHolder.status.setText("Status :");
            mViewHolder.status1.setText(item.get_email());
            mViewHolder.entry.setText("Entry Date");
            mViewHolder.entry1.setText(item.get_address());
            mViewHolder.expiry.setText("Expiry Date");
            mViewHolder.expiry1.setText(item.get_expiry());

            if(item.getImage().equals("false")){

                mViewHolder.profile.setImageResource(R.drawable.profile);
            } else {
                try {
                    Picasso.with(mContext)
                            .load(item.getImage().replaceAll(" ","%20"))
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(mViewHolder.profile);
                }catch (Exception e){
                }
                mViewHolder.profile.setImageResource(R.drawable.profile);
            }
            mViewHolder.itemView.findViewById(R.id.mcardview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,OtherProfileActivity.class);
                    intent.putExtra("NAME",mListItem.get(position).get_name());
                    intent.putExtra("STATUS","issued");
                    intent.putExtra("ID",mListItem.get(position).get_id());

                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.bottom_up,
                            android.R.anim.fade_out);
                    mContext.finish();

                }
            });
        }

    }


    @Override
    public void onClick(View view) {
      /*  int position = mRecyclerView.getChildAdapterPosition((View) view.getParent().getParent());
        Toast toast = Toast.makeText(mContext, "Clicked item at position " + position, Toast.LENGTH_SHORT);
        toast.show();*/
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}