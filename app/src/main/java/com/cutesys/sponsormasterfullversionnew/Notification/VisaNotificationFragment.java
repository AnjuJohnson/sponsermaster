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

import com.cutesys.sponsermasterlibrary.Calendar.CompactCalendarView;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.NotificationAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecyclerItemClickListener;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

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
 * Created by Kris on 3/13/2017.
 */

public class VisaNotificationFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForText = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private FrameLayout fragement_framelayout,fragement_callayout;
    private Switcher switcher;
    private RecyclerView mrecyclerview, mrecyclerfilter;
    private ImageView mBackpresscalendar, mBackpressfilter;
    private TextView error_label_retry, empty_label_retry, mNotificationTitle, mTitle;
    private FrameLayout popuplayoutcalendar, popuplayoutfilter;
    private LinearLayout progress_view;
    private CompactCalendarView mcalendarview;
    private TextView mMonth, empty_label;
    private ImageView ic_previous, ic_next;

    private NotificationAdapter mNotificationAdapter;

    private VisaBottomMenuNotificationFragment mvisabottom;

    private Calendar cal;
    private int day, year, month;
    private ArrayList<ListItem> dataItem;
    private String mDate, mFilter;
    private String mday, mmonth;

    String [] filterdata ={
            "all"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.visanotification_fragment, container, false);
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
        fragement_framelayout = ((FrameLayout) rootView.findViewById(R.id.fragement_framelayout));
        progress_view = ((LinearLayout) rootView.findViewById(R.id.progress_view));
        mBackpresscalendar = ((ImageView) rootView.findViewById(R.id.mBackpresscalendar));
        mBackpressfilter = ((ImageView) rootView.findViewById(R.id.mBackpressfilter));
        popuplayoutcalendar = ((FrameLayout) rootView.findViewById(R.id.popuplayoutcalendar));
        popuplayoutfilter = ((FrameLayout) rootView.findViewById(R.id.popuplayoutfilter));
        mBackpresscalendar.setOnClickListener(this);
        mBackpressfilter.setOnClickListener(this);
        popuplayoutcalendar.setVisibility(View.GONE);
        popuplayoutfilter.setVisibility(View.GONE);

        mvisabottom = (VisaBottomMenuNotificationFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_bottom_menu);

        mNotificationTitle = ((TextView) rootView.findViewById(R.id.mNotificationTitle));
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mrecyclerfilter = ((RecyclerView)rootView.findViewById(R.id.mrecyclerfilter));
        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

        empty_label = ((TextView) rootView.findViewById(R.id.empty_label));
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mrecyclerfilter.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFilter = "all";
        mTitle.setText("Visa - All Notification");
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
       /* mrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    fragement_framelayout.setVisibility(View.VISIBLE);
                } else {
                    fragement_framelayout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }
        });*/
        mrecyclerfilter.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        LoadFilterItem(filterdata[position]);

                    }
                })
        );

        mvisabottom.listdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress_view.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getActivity(),"Please wait while we process your request").show();
                }else {
                    Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
                    popuplayoutcalendar.startAnimation(slide);
                    popuplayoutcalendar.setVisibility(View.VISIBLE);
                    popuplayoutfilter.setVisibility(View.GONE);
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


            }
        });
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadAllNotificationInitiate mLoadAllNotificationInitiate = new LoadAllNotificationInitiate
                    (sPreferences.getString("ID", ""),
                            sPreferences.getString("AUTHORIZATION", ""), temp);
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
                popuplayoutfilter.setVisibility(View.GONE);
                break;

            case R.id.ic_previous:
                mcalendarview.showPreviousMonth();
                break;
            case R.id.ic_next:
                mcalendarview.showNextMonth();
                break;
        }
    }

    private void LoadFilterItem(String data) {

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
        mNotificationTitle.setText(mday + "-" + mmonth + "-" + year);

        Animation mslide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
        popuplayoutfilter.startAnimation(mslide);

        popuplayoutcalendar.setVisibility(View.GONE);

        if(data.toLowerCase().equals("all")){
            mTitle.setText("Visa - All Notification");
        }
        mFilter = data.toLowerCase();

        InitGetData(false);
    }

    public void LoadCalendarItem() {
        Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
        popuplayoutcalendar.startAnimation(slide);
        popuplayoutcalendar.setVisibility(View.GONE);


        InitGetData(false);
    }

    public class LoadAllNotificationInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization;
        private boolean mStatus;

        LoadAllNotificationInitiate(String id, String authorization, boolean status) {
            mId = id;
            mAuthorization = authorization;
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
            StringBuilder result = null;
            if(mFilter.equals("all")){
                result = httpOperations.doVISAALLNOTIFICATIONLIST(mId, mAuthorization,
                        mDate);
            }
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            if(mStatus == true){

            }
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("company_notification");

                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("notification_text").equals(""))
                                    && (!feedObj.getString("notification_text").equals("null"))) {

                                item.set_id(feedObj.getString("notification_id"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("notification_text")
                                        .toLowerCase().trim()));

                                dataItem.add(item);
                            }
                        }
                        mNotificationAdapter = new NotificationAdapter(getActivity(), dataItem);
                        mrecyclerview.setAdapter(mNotificationAdapter);

                    }else {
                        switcher.showEmptyView();
                        empty_label.setText("No Notifications Found");
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
