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

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Athira on 3/2/2017.
 */
public class VisaListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Activity mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public VisaListAdapter(Activity context, ArrayList<ListItem> listitem,
                           String status) {
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, first, status, second, second1, refno, third, third1, fourth, fourth1;
        ImageView profile, img1, img2;

        public ViewHolder(View view) {
            super(view);
            profile = (ImageView) view.findViewById(R.id.profile);

            img1 = (ImageView) view.findViewById(R.id.img1);
            img2 = (ImageView) view.findViewById(R.id.img2);

            name = (TextView) view.findViewById(R.id.name);
            first = (TextView) view.findViewById(R.id.first);
            status = (TextView) view.findViewById(R.id.status);
            second = (TextView) view.findViewById(R.id.second);
            second1 = (TextView) view.findViewById(R.id.second1);
            refno = (TextView) view.findViewById(R.id.refno);
            third = (TextView) view.findViewById(R.id.third);
            third1 = (TextView) view.findViewById(R.id.third1);
            fourth = (TextView) view.findViewById(R.id.fourth);
            fourth1 = (TextView) view.findViewById(R.id.fourth1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.visa_list_item, null, false);
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

        if (mStatus.equals("new")) {
            mViewHolder.first.setText(item.get_manufacture());
            mViewHolder.status.setText(item.get_email());
            mViewHolder.refno.setText(item.get_refno());
            mViewHolder.third.setText("Category");
            mViewHolder.third1.setText(item.get_code());
            mViewHolder.fourth.setText("Type");
            mViewHolder.fourth1.setText(item.get_address());
            mViewHolder.img1.setImageResource(R.mipmap.f_client);


            mViewHolder.second.setVisibility(View.GONE);
            mViewHolder.second1.setVisibility(View.GONE);
            mViewHolder.img2.setVisibility(View.GONE);

            if (item.getImage().equals("false")) {

                mViewHolder.profile.setImageResource(R.drawable.profile);
            } else {
                try {
                    Picasso.with(mContext)
                            .load(item.getImage().replaceAll(" ", "%20"))
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(mViewHolder.profile);
                } catch (Exception e) {
                }
                mViewHolder.profile.setImageResource(R.drawable.profile);
            }
            mViewHolder.itemView.findViewById(R.id.mcardview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,OtherProfileActivity.class);
                    intent.putExtra("NAME",mListItem.get(position).get_name());
                    intent.putExtra("STATUS","new");
                    intent.putExtra("ID",mListItem.get(position).get_id());

                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.bottom_up,
                            android.R.anim.fade_out);
                    mContext.finish();

                }
            });

        }




//        else if(mStatus.equals("issued")){
//
//            mViewHolder.first.setText(item.get_code());
//            mViewHolder.third.setText("Entry");
//            mViewHolder.third1.setText(item.get_address());
//            mViewHolder.fourth.setText("Expiry");
//            mViewHolder.fourth1.setText(item.get_email());
//            mViewHolder.img1.setImageResource(R.mipmap.f_visa);
//
//            mViewHolder.second.setVisibility(View.GONE);
//            mViewHolder.second1.setVisibility(View.GONE);
//            mViewHolder.img2.setVisibility(View.GONE);
//
//            if(item.getImage().equals("false")){
//
//                mViewHolder.profile.setImageResource(R.drawable.profile);
//            } else {
//                try {
//                    Picasso.with(mContext)
//                            .load(item.getImage().replaceAll(" ","%20"))
//                            .placeholder(R.drawable.profile)
//                            .error(R.drawable.profile)
//                            .into(mViewHolder.profile);
//                }catch (Exception e){
//                }
//                mViewHolder.profile.setImageResource(R.drawable.profile);
//            }
//        }
        else if ((mStatus.equals("enter")) || (mStatus.equals("notenter"))) {

            mViewHolder.refno.setText("Category :" + item.get_code());
            mViewHolder.first.setText(item.get_visanum());
            mViewHolder.third.setText("Entry");
            mViewHolder.third1.setText(item.get_entry());
            mViewHolder.fourth.setText("Expiry");
            mViewHolder.fourth1.setText(item.get_expiry());
            mViewHolder.status.setText("Status :" + item.get_email());
            mViewHolder.img1.setImageResource(R.mipmap.f_visa);
            mViewHolder.second.setText(item.get_manufacture());
            mViewHolder.img2.setImageResource(R.mipmap.f_client);
            mViewHolder.second1.setText("Type :" + item.get_address());


            // mViewHolder.img1.setImageResource(R.mipmap.f_visa);
            // mViewHolder.second.setText(item.get_manufacture());
            //mViewHolder.img2.setImageResource(R.mipmap.f_client);
            //mViewHolder.second1.setText("Type : "+item.get_code());

            if (item.getImage().equals("false")) {

                mViewHolder.profile.setImageResource(R.drawable.profile);
            } else {
                try {
                    Picasso.with(mContext)
                            .load(item.getImage().replaceAll(" ", "%20"))
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(mViewHolder.profile);
                } catch (Exception e) {
                }
                mViewHolder.profile.setImageResource(R.drawable.profile);
            }
            mViewHolder.itemView.findViewById(R.id.mcardview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,OtherProfileActivity.class);
                    intent.putExtra("NAME",mListItem.get(position).get_name());
                    intent.putExtra("STATUS","enter");
                    intent.putExtra("ID",mListItem.get(position).get_id());

                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.bottom_up,
                            android.R.anim.fade_out);
                    mContext.finish();

                }
            });

        } else if (mStatus.equals("report")) {

            mViewHolder.first.setText(item.get_manufacture());
            mViewHolder.img1.setImageResource(R.mipmap.f_client);
            mViewHolder.third.setText("Entry");
            mViewHolder.third1.setText(item.get_email());
            mViewHolder.fourth.setText("Expiry");
            mViewHolder.fourth1.setText(item.get_phone());
            mViewHolder.second.setText("Sponsor : " + item.get_model());
            mViewHolder.img2.setImageResource(R.mipmap.f_cpy);
            mViewHolder.second1.setText("Type : " + item.get_address());

            if (item.getImage().equals("false")) {

                mViewHolder.profile.setImageResource(R.drawable.profile);
            } else {
                try {
                    Picasso.with(mContext)
                            .load(item.getImage().replaceAll(" ", "%20"))
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(mViewHolder.profile);
                } catch (Exception e) {
                }
                mViewHolder.profile.setImageResource(R.drawable.profile);
            }

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
