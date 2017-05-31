package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScroller;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 12/12/2016.
 */
public class ExpiryRecyclerAdapter extends RecyclerView.Adapter implements FastScroller.SectionIndexer {

    private List<ListItem> expiryitems;
    public Activity mContext;
    MyViewHolder myViewHolder;
 String Status;

    public ExpiryRecyclerAdapter(Activity mContext, List<ListItem> expiryitems,String Status) {
        this.expiryitems = expiryitems;
        this.mContext = mContext;
        this.Status=Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.docexpiryitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        final ListItem dataItem = expiryitems.get(position);
        myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.cmpy_name.setText(StringUtils.capitalize(dataItem.get_name().toLowerCase().trim()));
        myViewHolder.mEmp_Id.setText("id :"+dataItem.get_email());
        List<ListItem> data = new ArrayList<>();
        for (int i = 0; i < dataItem.getTitle().length; i++) {
            final ListItem item = new ListItem();
            item.set_model(dataItem.getTitle()[i]);
            item.set_code(dataItem.getTitle_Date()[i]);
            data.add(item);
            myViewHolder.itemView.findViewById(R.id.cmpy_name).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,OtherProfileActivity.class);
                    intent.putExtra("NAME",expiryitems.get(position).get_name());
                    intent.putExtra("ID",expiryitems.get(position).get_id());

                    if(Status.equals("company")) {

                        intent.putExtra("STATUS","company");

                    } else if(Status.equals("employee")){

                        intent.putExtra("STATUS","employee");

                    }else if(Status.equals("flipcompany")){

                        intent.putExtra("STATUS","flipcompany");

                    }else if(Status.equals("flipemployee")){

                        intent.putExtra("STATUS","flipemployee");
                    }

                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.bottom_up,
                            android.R.anim.fade_out);
                    mContext.finish();

                }
            });
        }
        ExpirySubRecyclerAdapter itemListDataAdapter = new ExpirySubRecyclerAdapter(mContext, data);

        myViewHolder.expiry_recycler.setHasFixedSize(true);

        myViewHolder.expiry_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        myViewHolder.expiry_recycler.setAdapter(itemListDataAdapter);


    }

    @Override
    public int getItemCount() {

        return expiryitems.size();
    }

    public void addToList(ListItem item, int position) {
        expiryitems.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItemFromList(int position) {
        expiryitems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(expiryitems.get(position).get_name().charAt(0));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView cmpy_name,mEmp_Id;
        public ImageView left, right, clock;
        public RecyclerView expiry_recycler;

        public MyViewHolder(View itemView) {
            super(itemView);
            mEmp_Id = (TextView) itemView.findViewById(R.id.emp_id);
            clock = (ImageView) itemView.findViewById(R.id.clock);
            left = (ImageView) itemView.findViewById(R.id.left);
            right = (ImageView) itemView.findViewById(R.id.right);
            cmpy_name = (TextView) itemView.findViewById(R.id.cmpy_name);
            expiry_recycler = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        }
    }
}