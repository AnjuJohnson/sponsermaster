package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Athira on 3/2/2017.
 */
public class ListHeadingTwoAdapter extends RecyclerView.Adapter {

    private Activity mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public ListHeadingTwoAdapter(Activity context, ArrayList<ListItem> listitem){
        mContext = context;
        mListItem = listitem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView slno, title, msubtitle;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            layout = (LinearLayout) view.findViewById(R.id.layout);
            title = (TextView) view.findViewById(R.id.title);
            msubtitle = (TextView) view.findViewById(R.id.subtitle);
            slno = (TextView) view.findViewById(R.id.slno);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.listviewheadingtwo_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        if (position%2 == 0){
            mViewHolder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            mViewHolder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        mViewHolder.slno.setText(""+(position+1));
        mViewHolder.title.setText(item.get_name());
        mViewHolder.msubtitle.setText(item.get_manufacture());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}