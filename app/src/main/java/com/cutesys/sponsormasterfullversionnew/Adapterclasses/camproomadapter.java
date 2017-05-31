package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


import com.cutesys.sponsormasterfullversionnew.Company.CampRoomListFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.camproomlist;
import com.cutesys.sponsormasterfullversionnew.R;


import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class camproomadapter extends RecyclerView.Adapter<camproomadapter.ViewHolder>  {
    ArrayList<camproomlist> mListItem;
    Context mContext;
    String status;
    View v;


    public camproomadapter(Context context, ArrayList<camproomlist> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView  camproom;
        TextView  campname;

        TextView  slno;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.camproom = (TextView) itemView.findViewById(R.id.camproom);
            this.campname = (TextView) itemView.findViewById(R.id.campname1);
            slno = (TextView) itemView.findViewById(R.id.slno);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);



        }
    }

    public camproomadapter(ArrayList<camproomlist> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.camproom_cardlayout, parent, false);

        view.setOnClickListener(CampRoomListFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        ViewHolder mViewHolder = (ViewHolder) holder;

        if (position%2 == 0){
            mViewHolder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            mViewHolder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        TextView  camproom=holder.camproom;
        TextView  campname=holder.campname;
        TextView  slno=holder.slno;


        camproom.setText(mListItem.get(position).getcamp_room());
        campname.setText(mListItem.get(position).getcamp_name());
        slno.setText(""+(position+1));


    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}