package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScroller;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by Athira on 3/2/2017.
 */
public class CpyEmpListAdapter extends RecyclerView.Adapter implements FastScroller.SectionIndexer {
    Animation animFadein,animFadeout;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    int clickedPos = -1;
    private ArrayList<ListItem> mListItem;
    private ListItem item;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    String mStatus;

    public CpyEmpListAdapter(Activity activity, ArrayList<ListItem> listitem,
                             RecyclerView recyclerView, String status){
        mActivity = activity;
        mListItem = listitem;
        mStatus = status;
    }

    @Override
    public String getSectionText(int position) {
        return String.valueOf(mListItem.get(position).get_name().charAt(0));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, location, mail, call,desig,eid,cmpid;
        ImageView profile,action_more,f_designation,f_call,f_emp1,f_emp;
        LinearLayout visible,cardview_linearlayout,action_morelayout,desig_layout;
        CardView cmpy_empy_card;
        FloatingActionButton calloption,mailoption;
        public ViewHolder(View view) {
            super(view);
            profile = (ImageView) view.findViewById(R.id.profile);
            f_designation = (ImageView) view.findViewById(R.id.f_designation);
            f_call = (ImageView) view.findViewById(R.id.f_call);
            f_emp1 = (ImageView) view.findViewById(R.id.f_emp1);
            f_emp = (ImageView) view.findViewById(R.id.f_emp);
            eid= (TextView) view.findViewById(R.id.eid);
//            age = (TextView) view.findViewById(R.id.age);
//          duration = (TextView) view.findViewById(R.id.duration);
//            rating= (TextView) view.findViewById(R.id.rating);
            name = (TextView) view.findViewById(R.id.name);
          cmpid = (TextView) view.findViewById(R.id.cmpid);
            location = (TextView) view.findViewById(R.id.location);
            call = (TextView) view.findViewById(R.id.call);
            mail = (TextView) view.findViewById(R.id.mail);
            desig = (TextView) view.findViewById(R.id.desig);
            action_more = (ImageView) view.findViewById(R.id.action_more);
            action_morelayout = (LinearLayout) view.findViewById(R.id.action_morelayout);
            visible = ((LinearLayout) view.findViewById(R.id.visible));
            cmpy_empy_card = (CardView) view.findViewById(R.id.cmpy_empy_card);
            cardview_linearlayout = (LinearLayout) view.findViewById(R.id.cardview_linearlayout);
            calloption = (FloatingActionButton) view.findViewById(R.id.calloption);
            mailoption = (FloatingActionButton) view.findViewById(R.id.mailoption);
        }
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.
                from(mActivity).inflate(R.layout.cpy_emp_list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {
        final ViewHolder mViewHolder = (ViewHolder) holder;
        item = mListItem.get(i);

        mViewHolder.action_morelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedPos = i;
                notifyDataSetChanged();

            }
        });

        if (clickedPos == i) {
            mViewHolder.visible.setVisibility(View.VISIBLE);
            animFadein = AnimationUtils.loadAnimation(mActivity,R.anim.fab_slide_in_from_left);
            animFadeout = AnimationUtils.loadAnimation(mActivity,R.anim.fab_slide_in_from_right);
            mViewHolder.calloption.startAnimation(animFadein);
            mViewHolder.mailoption.startAnimation(animFadeout);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewHolder.visible.setVisibility(View.GONE);
                }
            }, 3000);
        } else {

            mViewHolder.visible.setVisibility(View.GONE);
        }


        mViewHolder.calloption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListItem.get(i).get_phone().toString().trim().equals("None")){
                    CustomToast.info(mActivity,"Invalid Phone Number").show();

                } else if(mListItem.get(i).get_phone().toString().trim().equals("")){
                    CustomToast.info(mActivity,"Invalid Phone Number").show();

                } else {
                    Action_Call(mListItem.get(i).get_phone().toString().trim());

                }
            }
        });
        mViewHolder.mailoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListItem.get(i).get_email().toString().trim().equals("None")){
                    CustomToast.info(mActivity,"Invalid Mail ID").show();

                } else if(mListItem.get(i).get_email().toString().trim().equals("")){
                    CustomToast.info(mActivity,"Invalid Mail ID").show();

                } else {
                    Action_Mail(mListItem.get(i).get_email().toString().trim());

                }

            }
        });
        if(mStatus.equals("company")) {
            mViewHolder.name.setText(item.get_name());
            mViewHolder.cmpid.setText(item.get_code());
            mViewHolder.location.setText(item.get_address());
           // mViewHolder.call.setText(item.get_phone());
            mViewHolder.mail.setText(item.get_email());

            // mViewHolder.age.setText(item.get_employee_age());
            // mViewHolder.duration.setText(item.get_employment_duration());
            // mViewHolder.rating.setText(item.get_employee_status());
            mViewHolder.desig.setVisibility(View.GONE);
            mViewHolder.call.setVisibility(View.GONE);
            mViewHolder.f_call.setVisibility(View.GONE);
            mViewHolder.f_designation.setVisibility(View.GONE);
            mViewHolder.eid.setVisibility(View.GONE);
            mViewHolder.f_emp.setVisibility(View.GONE);

        }
        if(mStatus.equals("employee")) {
            mViewHolder.name.setText(item.get_name());
            mViewHolder.eid.setText(item.get_employee_employment_id());
            mViewHolder.desig.setText(item.get_designation());
            mViewHolder.location.setText(item.get_address());
            mViewHolder.call.setText(item.get_phone());
            mViewHolder.mail.setText(item.get_email());

            mViewHolder.cmpid.setVisibility(View.GONE);
            mViewHolder.f_emp1.setVisibility(View.GONE);

            // mViewHolder.age.setText(item.get_employee_age());
            // mViewHolder.duration.setText(item.get_employment_duration());
            // mViewHolder.rating.setText(item.get_employee_status());




        }

        mViewHolder.name.setText(item.get_name());
        mViewHolder.cmpid.setText(item.get_code());
        mViewHolder.location.setText(item.get_address());
        mViewHolder.call.setText(item.get_phone());
        mViewHolder.mail.setText(item.get_email());
        mViewHolder.desig.setText(item.get_designation());
        mViewHolder.eid.setText(item.get_employee_employment_id());
//        mViewHolder.age.setText(item.get_employee_age());
//        mViewHolder.duration.setText(item.get_employment_duration());
//        mViewHolder.rating.setText(item.get_employee_status());

        if(item.getImage().equals("false")){

            mViewHolder.profile.setImageResource(R.drawable.profile);

        } else {
            try {
                Picasso.with(mActivity)
                        .load(mListItem.get(i).getImage().replaceAll(" ","%20"))
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .into(mViewHolder.profile);
            }catch (Exception e){
            }
        }

        mViewHolder.itemView.findViewById(R.id.cardview_linearlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mActivity,OtherProfileActivity.class);
                intent.putExtra("NAME",mListItem.get(i).get_name());
                intent.putExtra("ID",mListItem.get(i).get_id());

                if(mStatus.equals("company")) {

                    intent.putExtra("STATUS","company");

                } else if(mStatus.equals("employee")){

                    intent.putExtra("STATUS","employee");

                }else if(mStatus.equals("flipcompany")){

                    intent.putExtra("STATUS","flipcompany");

                }else if(mStatus.equals("flipemployee")){

                    intent.putExtra("STATUS","flipemployee");
                }

                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                mActivity.finish();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    private void Action_Call(String phoneno) {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            mActivity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
        } else {
            //Open call function
            String number = new String(phoneno.trim());
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            mActivity.startActivity(intent);
        }
    }


    private void Action_Mail(String mail) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        i.putExtra(Intent.EXTRA_SUBJECT, 0);
        i.putExtra(Intent.EXTRA_TEXT, 0);
        try {
            mActivity.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }
}
