package com.cutesys.sponsormasterfullversionnew.Recruitment;

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

/**
 * Created by Owner on 5/06/2017.
 */

public class InterviewPROFILEAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;
    int[] mImages;

    String mStatus;

    public InterviewPROFILEAdapter(Context context, ArrayList<ListItem> listitem,
                                   String status, int[] images){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
        mImages = images;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, subtitle;
        ImageView img;

        public ViewHolder(View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.img);

            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.otherprofileitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        if(mStatus.equals("profile")) {
            mViewHolder.title.setText(item.get_name());
            mViewHolder.subtitle.setText(item.get_email());
            mViewHolder.img.setImageResource(mImages[position]);

        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}
