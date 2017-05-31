package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by Owner on 15/03/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.deductioncategorytlist;
import com.cutesys.sponsormasterfullversionnew.Payroll.DeductionFragment;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class deductioncategoryadapter extends RecyclerView.Adapter<deductioncategoryadapter.ViewHolder>  {
    ArrayList<deductioncategorytlist> mListItem;
    Context mContext;
    String status;
    View v;


    public deductioncategoryadapter(Context context, ArrayList<deductioncategorytlist> listItem, String status) {
        mListItem = listItem;
        mContext = context;
        this.status = status;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        TextView slno;
        LinearLayout layout;







        public ViewHolder(View itemView) {
            super(itemView);
            this.category = (TextView) itemView.findViewById(R.id.dcategory);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
          /*  title = (TextView) itemView.findViewById(R.id.title);*/
            slno = (TextView) itemView.findViewById(R.id.slno);






        }
    }

    public deductioncategoryadapter(ArrayList<deductioncategorytlist> data) {
        this.mListItem = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_cardlayout, parent, false);

        view.setOnClickListener(DeductionFragment.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        TextView category=holder.category;


        if (position%2 == 0){
            holder.layout.setBackgroundColor(Color.parseColor("#EDE7F6"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
       holder.slno.setText(""+(position+1));



       category.setText(mListItem.get(position).getdeduction_category());





    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }



}
