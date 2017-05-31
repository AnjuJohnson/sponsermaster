package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Kris on 3/3/2017.
 */

public class DocumentStatusAdapter extends RecyclerView.Adapter {

    private List<ListItem> mListitem;
    public Context mContext;
    public Activity mActivity;
    String mStatus;

    public DocumentStatusAdapter(Activity mActivity, Context mContext, List<ListItem> listitem,
                                 String Status) {
        this.mListitem = listitem;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mStatus = Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.documentstatusitem, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        final ListItem dataItem = mListitem.get(position);
        ViewHolder myViewHolder = (ViewHolder) viewHolder;
        myViewHolder.itemView.findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,OtherProfileActivity.class);
                intent.putExtra("NAME",mListitem.get(position).get_name());
                intent.putExtra("ID",mListitem.get(position).get_id());

                if(mStatus.equals("company")) {

                    intent.putExtra("STATUS","company");

                } else if(mStatus.equals("employee")){

                    intent.putExtra("STATUS","employee");

                }else if(mStatus.equals("flipcompany")){

                    intent.putExtra("STATUS","flipcompany");

                }else if(mStatus.equals("flipemployee")){

                    intent.putExtra("STATUS","flipemployee");
                }

                mContext.startActivity(intent);
               /* mContext.overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                mContext.finish();*/

            }
        });


        myViewHolder.name.setText(dataItem.get_name());

       // myViewHolder.mComp_Id.setText((dataItem.get_email()));
        if(mStatus.equals("company")){
            myViewHolder.cmpcode.setText((dataItem.get_employee_code()));
            myViewHolder.municipal_title.setText("Municipal Baladiya");
            myViewHolder.taxcard.setText("Tax Card");
            myViewHolder.computercard.setText("Computer Card");
            myViewHolder.redtion.setText("Commercial Registration");
            myViewHolder.resume.setVisibility(View.GONE);
            myViewHolder.resume_status.setVisibility(View.GONE);
            myViewHolder.id.setVisibility(View.GONE);
            myViewHolder.id_status.setVisibility(View.GONE);
            myViewHolder.visa.setVisibility(View.GONE);
            myViewHolder.visa_status.setVisibility(View.GONE);
            myViewHolder.pic.setVisibility(View.GONE);
            myViewHolder.pic_status.setVisibility(View.GONE);

            myViewHolder.typevisa.setVisibility(View.GONE);


        } else  if(mStatus.equals("employee")){

            myViewHolder.municipal_title.setText("Profile Picture");
            myViewHolder.taxcard.setText("Resume");
            myViewHolder.computercard.setText("ID");
            myViewHolder.redtion.setText("Visa");
            myViewHolder.id.setText("Passport");
            myViewHolder.visa.setText("Health Card");
            myViewHolder.resume.setText("Insurance");
            myViewHolder.pic.setText("License");
            myViewHolder.cmpcode.setText(dataItem.get_employee_code());
            myViewHolder.typevisa.setText("Visa Type: " +dataItem.get_employee_visatype());


            if(dataItem.get_email().contains("Not")){
                myViewHolder.pic_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.pic_status.setBackgroundResource(R.mipmap.ic_success);
            }
        } else if(mStatus.equals("vehicle")){

            myViewHolder.municipal_title.setText("Insurance Documents");
            myViewHolder.taxcard.setText("Qatar ID");
            myViewHolder.computercard.setText("Vehicle Doc Images");
            myViewHolder.redtion.setText("Vehicle Doc Registration");
            myViewHolder.id.setText("Registration Documents");
            myViewHolder.visa.setText("Vehicle Picture");
            myViewHolder.resume.setText("Vehicle Doc Qatar ID");
            myViewHolder.pic.setText("Vehicle Document Insurance");
            myViewHolder.cmpcode.setVisibility(View.GONE);
            myViewHolder.typevisa.setVisibility(View.GONE);

            if(dataItem.get_email().contains("Not")){
                myViewHolder.pic_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.pic_status.setBackgroundResource(R.mipmap.ic_success);
            }
        }

        if(dataItem.get_address().contains(",")){
            String[] addresssplit = dataItem.get_address().split(",");

            if(addresssplit[0].contains("Not")){
                myViewHolder.municipal_title_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.municipal_title_status.setBackgroundResource(R.mipmap.ic_success);
            }

            if(addresssplit[1].contains("Not")){
                myViewHolder.id_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.id_status.setBackgroundResource(R.mipmap.ic_success);
            }

        } else {
            if(dataItem.get_address().contains("Not")){
                myViewHolder.municipal_title_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.municipal_title_status.setBackgroundResource(R.mipmap.ic_success);
            }
        }

        if(dataItem.get_code().contains(",")){
            String[] addresssplit = dataItem.get_code().split(",");
            if(addresssplit[0].contains("Not")){
                myViewHolder.tax_date.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.tax_date.setBackgroundResource(R.mipmap.ic_success);
            }
            if(addresssplit[1].contains("Not")){
                myViewHolder.visa_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.visa_status.setBackgroundResource(R.mipmap.ic_success);
            }

        } else {
            if(dataItem.get_code().contains("Not")){
                myViewHolder.tax_date.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.tax_date.setBackgroundResource(R.mipmap.ic_success);
            }
        }

        if(dataItem.get_model().contains(",")){
            String[] addresssplit = dataItem.get_model().split(",");
            if(addresssplit[0].contains("Not")){
                myViewHolder.computer_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.computer_status.setBackgroundResource(R.mipmap.ic_success);
            }
            if(addresssplit[1].contains("Not")){
                myViewHolder.resume_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.resume_status.setBackgroundResource(R.mipmap.ic_success);
            }

        } else {
            if(dataItem.get_model().contains("Not")){
                myViewHolder.computer_status.setBackgroundResource(R.mipmap.ic_fail);
            } else {
                myViewHolder.computer_status.setBackgroundResource(R.mipmap.ic_success);
            }
        }
        if(dataItem.get_manufacture().contains("Not")){
            myViewHolder.redtion_status.setBackgroundResource(R.mipmap.ic_fail);
        } else {
            myViewHolder.redtion_status.setBackgroundResource(R.mipmap.ic_success);
        }

    }

    @Override
    public int getItemCount() {

        return mListitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,cmpcode, municipal_title, taxcard,
                computercard, redtion,typevisa,mComp_Id,
                resume,  id, visa, pic ;
        ImageView municipal_title_status, tax_date, computer_status, redtion_status,
                resume_status, id_status, visa_status, pic_status;

        public ViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.name);
           cmpcode = (TextView) itemView.findViewById(R.id.cmpcode);
            typevisa=(TextView)itemView.findViewById(R.id.typevisa);

            municipal_title = (TextView) itemView.findViewById(R.id.municipal_title);
            municipal_title_status = (ImageView) itemView.findViewById(R.id.municipal_title_status);
            taxcard = (TextView) itemView.findViewById(R.id.taxcard);
            tax_date = (ImageView) itemView.findViewById(R.id.tax_date);
            computercard = (TextView) itemView.findViewById(R.id.computercard);
            computer_status = (ImageView) itemView.findViewById(R.id.computer_status);
            redtion = (TextView) itemView.findViewById(R.id.redtion);
            redtion_status = (ImageView) itemView.findViewById(R.id.redtion_status);

            resume = (TextView) itemView.findViewById(R.id.resume);
            resume_status = (ImageView) itemView.findViewById(R.id.resume_status);
            id = (TextView) itemView.findViewById(R.id.id);
            id_status = (ImageView) itemView.findViewById(R.id.id_status);
            visa = (TextView) itemView.findViewById(R.id.visa);
            visa_status = (ImageView) itemView.findViewById(R.id.visa_status);
            pic = (TextView) itemView.findViewById(R.id.pic);
            pic_status = (ImageView) itemView.findViewById(R.id.pic_status);
        }
    }
}
