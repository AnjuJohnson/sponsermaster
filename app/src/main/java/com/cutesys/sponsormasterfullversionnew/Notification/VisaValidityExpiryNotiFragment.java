package com.cutesys.sponsormasterfullversionnew.Notification;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.RecruitmentNotiAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;
import com.cutesys.sponsermasterlibrary.Calendar.CompactCalendarView;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 3/22/2017.
 */

public class VisaValidityExpiryNotiFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForText = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Switcher switcher;
    private RecyclerView mrecyclerview;
    private ImageView mBackpresscalendar;
    private TextView error_label_retry, empty_label_retry, mNotificationTitle, mTitle;
    private FrameLayout popuplayoutcalendar;
    private LinearLayout progress_view;
    private CompactCalendarView mcalendarview;
    private TextView mMonth;
    private ImageView ic_previous, ic_next;

    private RecruitmentNotiAdapter mRecruitmentNotiAdapter;
    private VisaBottomMenuNotificationFragment mvisabottom;

    private Calendar cal;
    private int day, year, month;
    private ArrayList<RecruitmentListItem> dataItem;
    private String mDate;
    private String mday, mmonth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recruitment_notification, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.mrecyclerview))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        mMonth = (TextView)rootView.findViewById(R.id.mMonth);
        ic_previous = (ImageView) rootView.findViewById(R.id.ic_previous);
        ic_next = (ImageView)rootView.findViewById(R.id.ic_next);
        mcalendarview = (CompactCalendarView)rootView.findViewById(R.id.calendarview);
        ic_previous.setOnClickListener(this);
        ic_next.setOnClickListener(this);
        progress_view = ((LinearLayout) rootView.findViewById(R.id.progress_view));
        mBackpresscalendar = ((ImageView) rootView.findViewById(R.id.mBackpresscalendar));
        popuplayoutcalendar = ((FrameLayout) rootView.findViewById(R.id.popuplayoutcalendar));
        mBackpresscalendar.setOnClickListener(this);
        popuplayoutcalendar.setVisibility(View.GONE);

        mvisabottom = (VisaBottomMenuNotificationFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_bottom_menu);

        mNotificationTitle = ((TextView) rootView.findViewById(R.id.mNotificationTitle));
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTitle.setText("Visa Validity Expiry");
        InitView();
    }

    @Override
    public void onStart() {
        super.onStart();

        InitGetData(false);
    }

    private void InitView(){
        try {
            new AsyncTask<String, String, String>() {
                protected void onPreExecute() {
                }

                @Override
                protected String doInBackground(String... arg0) {

                    cal = Calendar.getInstance();
                    day = cal.get(Calendar.DAY_OF_MONTH);
                    month = cal.get(Calendar.MONTH);
                    year = cal.get(Calendar.YEAR);
                    mday=String.valueOf(day);
                    mmonth=String.valueOf(month+1);
                    if(mday.length()==1){
                        mday="0"+String.valueOf(mday);
                    }
                    if(mmonth.length()==1){
                        mmonth="0"+String.valueOf(mmonth);
                    }

                    mDate = year + "-" + mmonth + "-" + mday;

                    return null;
                }

                protected void onPostExecute(String result) {

                    mcalendarview.setUseThreeLetterAbbreviation(false);
                    mcalendarview.setFirstDayOfWeek(Calendar.SUNDAY);
                    mcalendarview.invalidate();
                    mcalendarview.shouldDrawIndicatorsBelowSelectedDays(true);
                    mMonth.setText(dateFormatForMonth.format(mcalendarview.getFirstDayOfCurrentMonth()));

                    mNotificationTitle.setText(""+mday + "-" + mmonth + "-" + year);
                };
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }
        });

        mvisabottom.listdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress_view.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getActivity(),"Please wait while we process your request").show();
                }else {
                    Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
                    popuplayoutcalendar.startAnimation(slide);
                    popuplayoutcalendar.setVisibility(View.VISIBLE);
                }
            }
        });


        mMonth.setText(dateFormatForMonth.format(mcalendarview.getFirstDayOfCurrentMonth()));

        mcalendarview.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mMonth.setText(dateFormatForMonth.format(dateClicked));

                mDate = dateFormatForText.format(dateClicked);
                mNotificationTitle.setText(dateFormatForDisplaying.format(dateClicked));
                LoadCalendarItem();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));

                /*if(year != Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth))){

                    year = Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth));
                    InitGetData(false);
                }*/
            }
        });
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadAllNotificationInitiate mLoadAllNotificationInitiate = new LoadAllNotificationInitiate
                    (sPreferences.getString("ID", ""),
                            sPreferences.getString("AUTHORIZATION", ""),mDate, temp);
            mLoadAllNotificationInitiate.execute((Void) null);
        }else {
            switcher.showErrorView("No Internet Connection");
        }
    }


    @Override
    public void onClick(View view) {

        int buttonId = view.getId();
        switch (buttonId){

            case R.id.error_label_retry:
                InitGetData(false);
                break;
            case R.id.empty_label_retry:
                InitGetData(false);
                break;

            case R.id.mBackpresscalendar:
                Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
                popuplayoutcalendar.startAnimation(slide);
                popuplayoutcalendar.setVisibility(View.GONE);
                break;

            case R.id.ic_previous:
                mcalendarview.showPreviousMonth();
                break;
            case R.id.ic_next:
                mcalendarview.showNextMonth();
                break;
        }
    }


    public void LoadCalendarItem() {
        Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
        popuplayoutcalendar.startAnimation(slide);
        popuplayoutcalendar.setVisibility(View.GONE);
        InitGetData(false);
    }

    public class LoadAllNotificationInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization,mday;
        private boolean mStatus;

        LoadAllNotificationInitiate(String id, String authorization,String day, boolean status) {
            mId = id;
            mAuthorization = authorization;
            mday = day;
            mStatus = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataItem = new ArrayList<>();
            switcher.showProgressView();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.dovisavalidityexpiryday(mId, mAuthorization,mday);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("visa_validity_expiry_list");

                        for (int i = 0; i < feedArray.length(); i++) {

                            RecruitmentListItem item = new RecruitmentListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("notification_data").equals(""))
                                    && (!feedObj.getString("notification_data").equals("null"))) {

                                if (feedObj.getString("notification_id").trim().equals("")) {
                                    item.setnotification_id("None");
                                } else {
                                    item.setnotification_id((feedObj.getString("notification_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("notification_data").trim().equals("")) {
                                    item.setnotification_data("None");
                                } else {
                                    item.setnotification_data(StringUtils.capitalize(feedObj.getString("notification_data")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_name").trim().equals("")) {
                                    item.setvisa_name("None");
                                } else {
                                    item.setvisa_name(StringUtils.capitalize(feedObj.getString("visa_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_end_date").trim().equals("")) {
                                    item.setvisa_end_date("None");
                                } else {
                                    item.setvisa_end_date((feedObj.getString("visa_end_date")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("cand_id").trim().equals("")) {
                                    item.setcand_id("None");
                                } else {
                                    item.setcand_id((feedObj.getString("cand_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("notification_start_date").trim().equals("")) {
                                    item.setnotification_start_date("None");
                                } else {
                                    item.setnotification_start_date((feedObj.getString("notification_start_date")
                                            .toLowerCase().trim()));
                                }
                                dataItem.add(item);
                            }
                        }
                        mRecruitmentNotiAdapter = new RecruitmentNotiAdapter(getActivity(), dataItem);
                        mrecyclerview.setAdapter(mRecruitmentNotiAdapter);

                    }else {
                        switcher.showEmptyView();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                switcher.showErrorView("Please Try Again");
            } catch (NullPointerException e) {
                switcher.showErrorView("No Internet Connection");
            } catch (Exception e) {
                switcher.showErrorView("Please Try Again");
            }
        }
    }
}
