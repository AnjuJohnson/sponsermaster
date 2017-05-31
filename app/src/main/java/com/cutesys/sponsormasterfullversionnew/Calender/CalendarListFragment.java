package com.cutesys.sponsormasterfullversionnew.Calender;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.NumberPicker;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.CalendarListAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kris on 2/27/2017.
 */

public class CalendarListFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private NumberPicker mNumberPicker;
    private ImageView seldate, closepopup;
    private LinearLayout popuplayout;
    private RecyclerView mrecyclerview;
    private Button done;
    private TextView error_label_retry, empty_label_retry, yeartext;

    private CalendarListAdapter mCalendarListAdapter;

    private Calendar mCalendar;
    ArrayList<ListItem> dataItem;
    int year = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendarlist_fragment, container, false);
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

        mNumberPicker = ((NumberPicker) rootView.findViewById(R.id.selectyear));
        done = ((Button) rootView.findViewById(R.id.done));
        done.setOnClickListener(this);

        closepopup = ((ImageView) rootView.findViewById(R.id.closepopup));
        seldate = ((ImageView) rootView.findViewById(R.id.seldate));
        popuplayout = ((LinearLayout) rootView.findViewById(R.id.popuplayout));
        popuplayout.setVisibility(View.GONE);
        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

        yeartext = ((TextView) rootView.findViewById(R.id.yeartext));
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        seldate.setOnClickListener(this);
        closepopup.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);
        mNumberPicker.setMinValue(year-50);
        mNumberPicker.setMaxValue(year+50);
        mNumberPicker.setValue(year);
        yeartext.setText(" - "+year);
        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            CalendarEventsInitiate mCalendarEventsInitiate = new CalendarEventsInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(year));
            mCalendarEventsInitiate.execute((Void) null);
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
            case R.id.seldate:
                Animation mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
                popuplayout.startAnimation(mAnimation);
                popuplayout.setVisibility(View.VISIBLE);
                break;
            case R.id.closepopup:
                Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
                popuplayout.startAnimation(slide);
                popuplayout.setVisibility(View.GONE);
                break;
            case R.id.done:
                year = mNumberPicker.getValue();
                yeartext.setText(" - "+year);
                InitGetData(false);
                Animation slidedown = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
                popuplayout.startAnimation(slidedown);
                popuplayout.setVisibility(View.GONE);
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
            switcher.showProgressView();
            dataItem = new ArrayList<>();
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

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("event_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("name").equals(""))
                                    && (!feedObj.getString("name").equals("null"))) {

                                item.set_name(StringUtils.capitalize(feedObj.getString("name")
                                        .toLowerCase().trim()));

                                String datetime = "";

                                if(feedObj.getString("date").trim().equals("")){
                                    datetime = "";
                                }else {
                                    datetime = feedObj.getString("date");
                                }

                                if(feedObj.getString("time").trim().equals("")){
                                    datetime = datetime.toString()+"\t"+"";
                                }else {
                                    datetime = datetime.toString()+"\t"+feedObj.getString("time");
                                }
                                if(datetime.equals("")){
                                    item.set_address("None");
                                } else {
                                    item.set_address(datetime.toString());
                                }

                                if(feedObj.getString("email").trim().equals("")){
                                    item.set_email("None");
                                }else {
                                    item.set_email(feedObj.getString("email"));
                                }
                                if(feedObj.getString("title").trim().equals("")){
                                    item.set_phone("None");
                                }else {
                                    item.set_phone(feedObj.getString("title"));
                                }
                                if(feedObj.getString("description").trim().equals("")){
                                    item.set_manufacture("None");
                                }else {
                                    item.set_manufacture(feedObj.getString("description"));
                                }
                                if(feedObj.getString("company").trim().equals("")){
                                    item.set_code("None");
                                }else {
                                    item.set_code(feedObj.getString("company"));
                                }

                                dataItem.add(item);
                            }
                        }
                        mCalendarListAdapter = new CalendarListAdapter(getActivity(), dataItem);
                        mrecyclerview.setAdapter(mCalendarListAdapter);

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