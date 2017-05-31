package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Athira on 3/2/2017.
 */
public class CalendarListAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    public CalendarListAdapter(Context context, ArrayList<ListItem> listitem){
        mContext = context;
        mListItem = listitem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView  coordinator, company, title, description, email, date;

        public ViewHolder(View view) {
            super(view);

            coordinator = (TextView) view.findViewById(R.id.coordinator);
            company = (TextView) view.findViewById(R.id.company);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            email = (TextView) view.findViewById(R.id.email);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.event_list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.coordinator.setText(item.get_name());
        mViewHolder.company.setText(item.get_code());
        mViewHolder.title.setText(item.get_phone());
        mViewHolder.description.setText(item.get_manufacture());
        mViewHolder.email.setText(item.get_email());
        mViewHolder.date.setText(item.get_address());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}