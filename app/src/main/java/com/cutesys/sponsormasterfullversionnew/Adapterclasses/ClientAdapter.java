package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Kris on 12/21/2016.
 */
public class ClientAdapter extends RecyclerView.Adapter {

    private ArrayList<ListItem> mListitem;
    private ListItem item;
    public Activity mContext;

    public ClientAdapter(Activity mContext, ArrayList<ListItem> listitem) {
        mListitem = listitem;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.notification_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListitem.get(position);

        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.title.setText(item.get_name());
        myViewHolder.imagelayout.setBackgroundResource(R.color.clientcard);
        myViewHolder.image.setImageResource(R.mipmap.ic_client);
    }

    @Override
    public int getItemCount() {

        return mListitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView image;
        public LinearLayout imagelayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            imagelayout = (LinearLayout) itemView.findViewById(R.id.imagelayout);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}