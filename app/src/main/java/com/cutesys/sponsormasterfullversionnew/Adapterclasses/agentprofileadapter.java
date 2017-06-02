package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScroller;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.List;

/**
 * Created by Owner on 1/06/2017.
 */

public class agentprofileadapter extends RecyclerView.Adapter implements FastScroller.SectionIndexer{

    private List<RecruitmentListItem> mListitem;
    public Context mContext;
    public Activity mActivity;
    String mStatus;
    RecruitmentListItem dataItem;
    public agentprofileadapter(Activity mActivity, Context mContext, List<RecruitmentListItem> listitem,
                               String Status) {
        this.mListitem = listitem;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mStatus = Status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.
                from(mContext).inflate(R.layout.agentprofilecard, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        dataItem = mListitem.get(position);
        ViewHolder myViewHolder = (ViewHolder) viewHolder;

        myViewHolder.agentid.setText(mListitem.get(position).getagent_area_id());
        myViewHolder.agentname.setText(mListitem.get(position).get_name());
        myViewHolder.agentplace.setText(mListitem.get(position).getagent_place());
        myViewHolder.agentcountry.setText(mListitem.get(position).getagent_place());
        myViewHolder.agentmail.setText(mListitem.get(position).getcandidate_email());
        myViewHolder.agentphone.setText(mListitem.get(position).getcandidate_phone());
        myViewHolder.agentzipcodetext.setText(mListitem.get(position).getcandidate_code());
        myViewHolder.agentstate.setText(mListitem.get(position).getcandidate_status());
        myViewHolder.buildno.setText(mListitem.get(position).getcandidate_designation());




    }

    @Override
    public int getItemCount() {

        return mListitem.size();
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(mListitem.get(position).get_name().charAt(0));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  CardView documentstatuscard;
        public    ImageView building,agentphoneimg,agentmailimg;
        public   TextView agentname,agentid,agentplace,agentstate,agentcountry,agentphone,agentmail,buildno,agentzipcode,agentzipcodetext;

        public ViewHolder(View itemView) {
            super(itemView);


            documentstatuscard = (CardView) itemView.findViewById(R.id.documentstatuscard);

            agentname=(TextView) itemView.findViewById(R.id.agentname);
            agentid=(TextView) itemView.findViewById(R.id.agentid);
            agentplace=(TextView) itemView.findViewById(R.id.agentplace);
            agentstate=(TextView) itemView.findViewById(R.id.agentstate);
            agentcountry=(TextView) itemView.findViewById(R.id.agentcountry);
            buildno=(TextView) itemView.findViewById(R.id.buildno);
            agentphone=(TextView) itemView.findViewById(R.id.agentphone);
            agentmail=(TextView) itemView.findViewById(R.id.agentmail);
            agentzipcode=(TextView) itemView.findViewById(R.id.agentzipcode);
            agentzipcodetext=(TextView) itemView.findViewById(R.id.agentzipcodetext);


            building=(ImageView) itemView.findViewById(R.id.building);

            agentphoneimg=(ImageView) itemView.findViewById(R.id.agentphoneimg);
            agentmailimg=(ImageView) itemView.findViewById(R.id.agentmailimg);
        }
    }
}
