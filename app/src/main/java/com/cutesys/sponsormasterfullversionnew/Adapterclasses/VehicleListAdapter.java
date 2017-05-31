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
public class VehicleListAdapter extends RecyclerView.Adapter implements FastScroller.SectionIndexer {

    private Activity mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public VehicleListAdapter(Activity context, ArrayList<ListItem> listitem, String Status){
        mContext = context;
        mListItem = listitem;
        mStatus = Status;
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(mListItem.get(position).get_name().charAt(0));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, manufacture, mail, call;
        ImageView profile;

        public ViewHolder(View view) {
            super(view);
            profile = (ImageView) view.findViewById(R.id.profile);
            name = (TextView) view.findViewById(R.id.name);
            manufacture = (TextView) view.findViewById(R.id.manufacture);
            call = (TextView) view.findViewById(R.id.call);
            mail = (TextView) view.findViewById(R.id.mail);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.vehicle_list_item, null, false);
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
        mViewHolder.manufacture.setText(item.get_address());
        mViewHolder.call.setText(item.get_email());
        mViewHolder.mail.setText(item.get_phone());

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

        mViewHolder.itemView.findViewById(R.id.vehicle_card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",mListItem.get(position).get_address());
                intent.putExtra("ID",mListItem.get(position).get_id());

                if(mStatus.equals("vehicle")) {

                    intent.putExtra("STATUS","vehicle");

                } else if(mStatus.equals("flipvehicle")){

                    intent.putExtra("STATUS","flipvehicle");
                }

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