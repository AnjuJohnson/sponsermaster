package com.cutesys.sponsormasterfullversionnew.Calender;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Calendar.CompactCalendarView;
import com.cutesys.sponsermasterlibrary.Calendar.Event;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
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
 * Created by Kris on 2/27/2017.
 */

public class CalendarViewFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForYear = new SimpleDateFormat("yyyy", Locale.getDefault());

    private boolean shouldShow = false;
    ArrayAdapter adapter;

    private ListView mListView;
    private CompactCalendarView mcalendarview;
    private TextView mTitle;
    private ImageView ic_previous, ic_next;
    private AVLoadingIndicatorView indicator;

    List<String> mEventlist;
    private Handler mHandler;
    private Calendar mCalendar;
    int year = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_view_fragment, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){

        mHandler = new Handler();
        mEventlist = new ArrayList<>();

        mListView = (ListView)rootView.findViewById(R.id.event_listview);
        mTitle = (TextView)rootView.findViewById(R.id.mTitle);
        ic_previous = (ImageView) rootView.findViewById(R.id.ic_previous);
        ic_next = (ImageView)rootView.findViewById(R.id.ic_next);
        mcalendarview = (CompactCalendarView)rootView.findViewById(R.id.calendarview);
        indicator = (AVLoadingIndicatorView) rootView.findViewById(R.id.indicator);
        indicator.setVisibility(View.GONE);
        ic_previous.setOnClickListener(this);
        ic_next.setOnClickListener(this);

        mcalendarview.setUseThreeLetterAbbreviation(false);
        mcalendarview.setFirstDayOfWeek(Calendar.SUNDAY);
        mcalendarview.invalidate();
        mcalendarview.shouldDrawIndicatorsBelowSelectedDays(true);
        mTitle.setText(dateFormatForMonth.format(mcalendarview.getFirstDayOfCurrentMonth()));

        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);

        InitGetData(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<>(getContext(), R.layout.calendarvieweventitem, mEventlist);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTitle.setText(dateFormatForMonth.format(mcalendarview.getFirstDayOfCurrentMonth()));

        mcalendarview.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mTitle.setText(dateFormatForMonth.format(dateClicked));

                List<Event> bookingsFromMap = mcalendarview.getEvents(dateClicked);
                Log.d("Athira", "inside onclick " + dateFormatForDisplaying.format(dateClicked));

                if (bookingsFromMap != null) {
                    Log.d("Athira", bookingsFromMap.toString());
                    mEventlist.clear();
                    for (Event booking : bookingsFromMap) {
                        mEventlist.add((String) booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }
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

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){

            CalendarEventsInitiate mCalendarEventsInitiate = new CalendarEventsInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(year));
            mCalendarEventsInitiate.execute((Void) null);
        }else {
            CustomToast.error(getActivity(),"No Internet Connection.").show();
        }
    }

    @Override
    public void onClick(View view) {

        int buttonId = view.getId();
        switch (buttonId){

            case R.id.ic_previous:
                mcalendarview.showPreviousMonth();
                break;
            case R.id.ic_next:
                mcalendarview.showNextMonth();
                break;
        }
    }

    public class CalendarEventsInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mYear;
        private boolean mStatus;

        CalendarEventsInitiate(String id, String authorization, boolean status, String Year) {
            mId = id;
            mAuthorization = authorization;
            mStatus = status;
            mYear = Year;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indicator.setVisibility(View.VISIBLE);
            mcalendarview.removeAllEvents();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doEVENT_LIST(mId, mAuthorization,mYear);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            indicator.setVisibility(View.GONE);

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        JSONArray feedArray = jsonObj.getJSONArray("event_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("name").equals(""))
                                    && (!feedObj.getString("name").equals("null"))
                                    && (!feedObj.getString("date").equals(""))
                                    && (!feedObj.getString("date").equals(""))) {

                                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                                String dateInString = feedObj.getString("date").trim().
                                        replaceAll("/","-")+" 00:00:00";
                                Date date = null;
                                try {
                                    date = sdf.parse(dateInString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());

                                mcalendarview.addEvent(new Event(Color.RED,
                                        calendar.getTimeInMillis(), "\t"+
                                        StringUtils.capitalize(feedObj.getString("title")
                                                .toLowerCase().trim())+"\n"+
                                        "\t"+feedObj.getString("description")+" "+
                                        "at "+feedObj.getString("time")));
                            }
                        }
                    }else {
                        CustomToast.info(getActivity(),"No Events Found").show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                CustomToast.warning(getActivity(),"Please Try Again").show();
            } catch (NullPointerException e) {
                try {
                    CustomToast.error(getActivity(), "No Internet Connection.").show();
                }catch (Exception e1){}
            } catch (Exception e) {
                CustomToast.warning(getActivity(),"Please Try Again").show();
            }
        }
    }
}