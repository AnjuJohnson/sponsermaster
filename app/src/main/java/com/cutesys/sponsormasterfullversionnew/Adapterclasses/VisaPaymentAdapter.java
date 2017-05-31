package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScroller;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Athira on 3/2/2017.
 */
public class VisaPaymentAdapter extends RecyclerView.Adapter implements FastScroller.SectionIndexer {

    private Activity mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    public VisaPaymentAdapter(Activity context, ArrayList<ListItem> listitem){
        mContext = context;
        mListItem = listitem;
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(mListItem.get(position).get_name().charAt(0));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, first, second, second1, third, third1, fourth, fourth1,month,month1,year,year1,
                total, bank, sponsorfee, companyfee, advancefee, balfee;
        ImageView profile, img1,  img2;

        public ViewHolder(View view) {
            super(view);
            profile = (ImageView) view.findViewById(R.id.profile);

            img1 = (ImageView) view.findViewById(R.id.img1);
            img2 = (ImageView) view.findViewById(R.id.img2);

            name = (TextView) view.findViewById(R.id.name);
            first = (TextView) view.findViewById(R.id.first);
            second = (TextView) view.findViewById(R.id.second);
            second1 = (TextView) view.findViewById(R.id.second1);
            third = (TextView) view.findViewById(R.id.third);
            third1 = (TextView) view.findViewById(R.id.third1);
            fourth = (TextView) view.findViewById(R.id.fourth);
            fourth1 = (TextView) view.findViewById(R.id.fourth1);
            month = (TextView) view.findViewById(R.id.month);
            month1 = (TextView) view.findViewById(R.id.month1);
            year = (TextView) view.findViewById(R.id.year);
            year1 = (TextView) view.findViewById(R.id.year1);

            bank = (TextView) view.findViewById(R.id.bank);
            total = (TextView) view.findViewById(R.id.total);
            sponsorfee = (TextView) view.findViewById(R.id.sponsorfee);
            companyfee = (TextView) view.findViewById(R.id.companyfee);
            advancefee = (TextView) view.findViewById(R.id.advancefee);
            balfee = (TextView) view.findViewById(R.id.balfee);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.visapaymentitem, null, false);
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
        mViewHolder.first.setText(item.get_manufacture());
        mViewHolder.img1.setImageResource(R.mipmap.f_client);
        mViewHolder.second.setText("Sponsor Name : "+item.get_phone());

        mViewHolder.third.setText("Category");
        mViewHolder.third1.setText(item.get_email());
        mViewHolder.fourth.setText("Type");
        mViewHolder.fourth1.setText(item.get_address());
        mViewHolder.month.setText("Month :");
        mViewHolder.month1.setText(item.get_month());
        mViewHolder.year.setText("Year :");
        mViewHolder.year1.setText(item.get_year());

        mViewHolder.total.setText(item.get_model());
        mViewHolder.bank.setText(item.get_bank_fee());
        mViewHolder.sponsorfee.setText(item.get_sponsor_fee());
        mViewHolder.companyfee.setText(item.get_company_fee());
        mViewHolder.advancefee.setText(item.get_advance_fee());
        mViewHolder.balfee.setText(item.get_balance_fee());

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
        }

        mViewHolder.itemView.findViewById(R.id.mcardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",mListItem.get(position).get_name());
                intent.putExtra("STATUS","visa");
                intent.putExtra("ID",mListItem.get(position).get_id());

                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                mContext.finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}