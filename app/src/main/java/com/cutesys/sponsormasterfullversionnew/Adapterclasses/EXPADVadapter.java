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
 * Created by Mariya on 3/2/2017.
 */
public class EXPADVadapter extends RecyclerView.Adapter {

    private Context mContext;

    private ArrayList<ListItem> mListItem;
    private ListItem item;

    String mStatus;

    public EXPADVadapter(Context context, ArrayList<ListItem> listitem,
                         String status){
        mContext = context;
        mListItem = listitem;
        mStatus = status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView qualification, statustext,qstatus;

        public ViewHolder(View view) {
            super(view);

            qualification = (TextView) view.findViewById(R.id.qualification);
            statustext = (TextView) view.findViewById(R.id.statustext);
            qstatus = (TextView) view.findViewById(R.id.Status);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.otrexpadvitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        item = mListItem.get(position);
        ViewHolder mViewHolder = (ViewHolder) viewHolder;

        mViewHolder.qualification.setText(item.get_name());
        if(item.get_address().equals("")){
            mViewHolder.statustext.setText("None");
        }
        else {
            mViewHolder.statustext.setText(item.get_address());
        }

        if (mStatus.equals("candidate")){

            //mViewHolder.qstatus.setText("Status");

        } else  if (mStatus.equals("accomodation")){
            mViewHolder.qstatus.setText("In take Capacity:");

        }


    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}