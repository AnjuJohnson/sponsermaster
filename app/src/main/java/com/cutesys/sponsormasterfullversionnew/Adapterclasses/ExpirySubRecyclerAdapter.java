package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;
import java.util.List;

public class ExpirySubRecyclerAdapter extends RecyclerView.Adapter {

    private List<ListItem> items;
    public Context mContext;
    ArrayList<Integer> mBackgroundColors;

    public ExpirySubRecyclerAdapter(Context mContext, List<ListItem> items) {
        this.items = items;
        this.mContext = mContext;

        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
        mBackgroundColors.add(R.mipmap.expirycard);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {



        View rootView = LayoutInflater.from(mContext).inflate(R.layout.docitem, null);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        final ListItem dataItem = items.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.clock.setBackgroundResource(mBackgroundColors.get(position));
        myViewHolder.doc_title.setText( dataItem.get_model());
        myViewHolder.doc_title_date.setText( dataItem.get_code());
    }

    @Override
    public int getItemCount() {

        return items.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView clock;
        public TextView doc_title, doc_title_date;

        public MyViewHolder(View itemView) {
            super(itemView);

            clock = (ImageView) itemView.findViewById(R.id.clock);
            doc_title = (TextView) itemView.findViewById(R.id.doc_title);
            doc_title_date=(TextView) itemView.findViewById(R.id.doc_title_date);
        }
    }
}
