package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by Owner on 14/03/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.manageholidaylist;
import com.cutesys.sponsormasterfullversionnew.Payroll.HolidayFragment;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class manageholidayadapter extends RecyclerView.Adapter<manageholidayadapter.ViewHolder>  {
    ArrayList<manageholidaylist> mListItem;
    Context mContext;
    String status;
    View v;


    public manageholidayadapter(Context context, ArrayList<manageholidaylist> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView holiday;
        ImageView image;
         LinearLayout imagelayout;







        public ViewHolder(View itemView) {
            super(itemView);
            this.holiday = (TextView) itemView.findViewById(R.id.holiday);
            this.imagelayout = (LinearLayout) itemView.findViewById(R.id.imagelayout);
            this.image = (ImageView) itemView.findViewById(R.id.image);






        }
    }

    public manageholidayadapter(ArrayList<manageholidaylist> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_card, parent, false);

        view.setOnClickListener(HolidayFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView holiday=holder.holiday;
        ImageView image=holder.image;
        LinearLayout imagelayout=holder.imagelayout;


      holiday.setText(mListItem.get(position).getholiday());
        imagelayout.setBackgroundResource(R.color.clientcard);
       image.setImageResource(R.mipmap.ic_date);





    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}
