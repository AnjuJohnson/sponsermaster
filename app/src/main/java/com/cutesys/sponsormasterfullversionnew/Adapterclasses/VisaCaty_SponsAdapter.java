package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Kris on 12/21/2016.
 */
public class VisaCaty_SponsAdapter extends RecyclerView.Adapter {

    private List<ListItem> mitems;
    public Activity mContext;

    String mstatus;

    public VisaCaty_SponsAdapter(Activity mContext, List<ListItem> items, String status) {
        this.mitems = items;
        this.mContext = mContext;
        mstatus = status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.visacategorysponitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        final ListItem dataItem = mitems.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.title.setText(dataItem.get_name());
        myViewHolder.subtitle.setText(dataItem.get_id());
        if(mstatus.equals("category")){
            myViewHolder.title.setTextSize(16);
            myViewHolder.title.setTextColor(Color.parseColor("#000000"));
            myViewHolder.subtitle.setTextSize(20);
            myViewHolder.subtitle.setTextColor(Color.parseColor("#299e83"));

            if (position % 3 == 0) {
                myViewHolder.imagelayout.setBackgroundResource(R.color.blueimage);
                myViewHolder.image.setImageResource(R.mipmap.ic_working);
            } else {
                myViewHolder.imagelayout.setBackgroundResource(R.color.greenimage);
                myViewHolder.image.setImageResource(R.mipmap.ic_business);
            }

        } else if(mstatus.equals("sponsor")){
            myViewHolder.title.setTextSize(20);
            myViewHolder.title.setTextColor(Color.parseColor("#299e83"));
            myViewHolder.subtitle.setTextSize(16);
            myViewHolder.subtitle.setTextColor(Color.parseColor("#000000"));

            myViewHolder.imagelayout.setBackgroundResource(R.color.sponsorcard);
            myViewHolder.image.setImageResource(R.mipmap.ic_handshake);

        }
    }

    @Override
    public int getItemCount() {

        return mitems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, subtitle;
        public ImageView image;
        public LinearLayout imagelayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            imagelayout=(LinearLayout)itemView.findViewById(R.id.imagelayout);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        }
    }
}