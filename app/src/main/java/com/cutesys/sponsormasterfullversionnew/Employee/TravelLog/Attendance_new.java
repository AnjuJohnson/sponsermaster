package com.cutesys.sponsormasterfullversionnew.Employee.TravelLog;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsermasterlibrary.Calendar.CompactCalendarView;
import com.cutesys.sponsermasterlibrary.Calendar.Event;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.LeaveListAdapter;
import com.cutesys.sponsormasterfullversionnew.Calender.CalendarViewFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Owner on 4/04/2017.
 */

public class Attendance_new extends Fragment implements View.OnClickListener {
    FrameLayout mFrame;
    AVLoadingIndicatorView indicator;
    private CompactCalendarView mcalendarview;
    private SharedPreferences sPreferences;
    private RecyclerView.LayoutManager layoutManager;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry, mTitle,mHead;
    Button changetype;
    private ImageView ic_previous, ic_next;
    private static RecyclerView loan_recycler;
    ArrayList<AllListItem> dataItem;
    LeaveListAdapter mLeaveListAdapter;
    int start=0;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForYear = new SimpleDateFormat("yyyy", Locale.getDefault());

    String status;
    CalendarView calendar;
    private String curDate;
    Button submit;
    LinearLayout visible_layout,mError,mEmpty;
    private Calendar mCalendar;
    int year = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_new, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {


        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.loan_recycler))
               // .addErrorView(rootView.findViewById(R.id.error_view))
               /* .addProgressView(rootView.findViewById(R.id.progress_view))*/
              //  .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();
        mError=(LinearLayout)rootView.findViewById(R.id.empty_view);
        mFrame=(FrameLayout) rootView.findViewById(R.id.frame);
      //  mEmpty=(LinearLayout)rootView.findViewById(R.id.error_view);
        loan_recycler = (RecyclerView) rootView.findViewById(R.id.loan_recycler);
       // error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
       // error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        ic_previous = (ImageView) rootView.findViewById(R.id.ic_previous);
        ic_next = (ImageView)rootView.findViewById(R.id.ic_next);
        calendar=(CalendarView)rootView.findViewById(R.id.calendar);
        ic_next.setOnClickListener(this);
        ic_previous.setOnClickListener(this);
        visible_layout=(LinearLayout)rootView.findViewById(R.id.visible_layout);
        mcalendarview = (CompactCalendarView)rootView.findViewById(R.id.calendarview);

       /* indicator = (AVLoadingIndicatorView) rootView.findViewById(R.id.indicator);
*/
       /* indicator.setVisibility(View.GONE);*/
        submit=(Button)rootView.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        initRecyclerView();


        changetype = ((Button) rootView.findViewById(R.id.changetype));
        changetype.setOnClickListener(this);
        changetype.setVisibility(View.GONE);
       /* changetype.setImageResource(R.mipmap.ic_cal);*/
        mTitle = ((TextView) rootView.findViewById(R.id.mTitles));
        mHead = ((TextView) rootView.findViewById(R.id.mhead));
        mHead.setText("Choose Date");
        mTitle.setText("Calendar");


        mcalendarview.setUseThreeLetterAbbreviation(false);
        mcalendarview.setFirstDayOfWeek(Calendar.SUNDAY);
        mcalendarview.invalidate();
        mcalendarview.shouldDrawIndicatorsBelowSelectedDays(true);
        mTitle.setText(dateFormatForMonth.format(mcalendarview.getFirstDayOfCurrentMonth()));

        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        curDate = df.format(mCalendar.getTime());
        System.out.println("Current time => " + curDate);

        InitGetData(false);
    }

    private void InitGetData(boolean temp) {

        Config mConfig=new Config(getActivity());
        mTitle.setText(dateFormatForMonth.format(mcalendarview.getFirstDayOfCurrentMonth()));

        mcalendarview.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mTitle.setText(dateFormatForMonth.format(dateClicked));
                curDate=dateFormatForDisplaying.format(dateClicked);
              //  Toast.makeText(getActivity(),"date"+ dateFormatForDisplaying.format(dateClicked),Toast.LENGTH_SHORT).show();
               /* List<Event> bookingsFromMap = mcalendarview.getEvents(dateClicked);
                Log.d("Athira", "inside onclick " + dateFormatForDisplaying.format(dateClicked));
*/

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mTitle.setText(dateFormatForMonth.format(firstDayOfNewMonth));

                if(year != Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth))){

                    year = Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth));
                    InitGetData(false);
                }
            }
        });
    }

    private void initRecyclerView() {
        loan_recycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        loan_recycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        switch (buttonId) {
            case R.id.ic_previous:
                mcalendarview.showPreviousMonth();
                break;
            case R.id.ic_next:
                mcalendarview.showNextMonth();
                break;
            case R.id.error_label_retry:
                InitGetData(false);
                break;
            case R.id.empty_label_retry:
                InitGetData(false);
                break;
            case R.id.submit:
                int start=0;
                visible_layout.setVisibility(View.VISIBLE);
               /* indicator.setVisibility(View.VISIBLE);*/
                Config mConfig=new Config(getActivity());
                if (mConfig.isOnline(getActivity())) {
                    Attendance_new.LoadAttendanceList mLoadAttendanceList = new Attendance_new.LoadAttendanceList(sPreferences.getString("ID", ""),
                            sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start),curDate);
                    mLoadAttendanceList.execute((Void) null);
                } else {
                   /* visible_layout.setVisibility(View.GONE);*/
                    CustomToast.error(getActivity(), "No Internet Connection.").show();
                   // switcher.showErrorView("No Internet Connection");
                }
            case R.id.changetype:
                mHead.setText("Choose Date");
                visible_layout.setVisibility(View.VISIBLE);
                mError.setVisibility(View.GONE);
                loan_recycler.setVisibility(View.GONE);
                 changetype.setVisibility(View.GONE);
                //mEmpty.setVisibility(View.GONE);

        }

    }

    public class LoadAttendanceList extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart,mDate;

        private boolean mStatus;

        LoadAttendanceList(String id, String authorization, String Start,String date) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mDate=date;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            visible_layout.setVisibility(View.GONE);

           // switcher.showProgressView();
            dataItem = new ArrayList<>();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doattendanceList(mId, mAuthorization,mStart,mDate);
            System.out.println(result);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            visible_layout.setVisibility(View.GONE);

            try {
                JSONArray jsonarray=new JSONArray(result.toString());
                JSONObject jsonObj=(JSONObject)jsonarray.get(0);
               /* JSONObject jsonObj = new JSONObject(result.toString());
*/
                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        changetype.setVisibility(View.VISIBLE);
                        switcher.showContentView();
                        mHead.setText("Attendance List");
//                        System.out.println("hello"+jsonObj.toString());
                        JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.setEmployee_id(feedObj.getString("employee_id"));
                            item.setEmployee_name(feedObj.getString("employee_name"));
                            if(feedObj.getString("designation").equals("")){
                                item.setEmployee_designation("None");
                            }
                            else {
                                item.setEmployee_designation(feedObj.getString("designation"));
                            }
                            /*item.setEmployee_designation(feedObj.getString("designation"));*/
                            item.setCompany_name(feedObj.getString("company"));
                            item.setVehicle_status(feedObj.getString("employment id"));
                            dataItem.add(item);
                        }
                        start = dataItem.size();
                        mLeaveListAdapter = new LeaveListAdapter(getActivity(), getActivity(), dataItem, "attendance");
                        loan_recycler.setAdapter(mLeaveListAdapter);


                    } else  {


                        mHead.setText("gfdfds");
                        changetype.setVisibility(View.VISIBLE);
                        switcher.showEmptyView();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

                mHead.setText("");
                changetype.setVisibility(View.VISIBLE);
                switcher.showEmptyView();
            //    switcher.showErrorView("Please Try Again");;

            } catch (NullPointerException e) {
                changetype.setVisibility(View.VISIBLE);
                visible_layout.setVisibility(View.GONE);
                CustomToast.error(getActivity(), "No Internet Connection.").show();
               // switcher.showErrorView("No Internet Connection");
            } catch (Exception e) {
                CustomToast.error(getActivity(), "No Internet Connection.").show();
               // switcher.showErrorView("Please Try Again");
            }
        }
    }
}