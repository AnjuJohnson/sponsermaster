package com.cutesys.sponsormasterfullversionnew.Employee;

/**
 * Created by user on 3/30/2017.
 */

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;

import com.cutesys.sponsormasterfullversionnew.Adapterclasses.LeaveListAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Owner on 25/03/2017.
 */

public class AttandanceFragment extends Fragment implements View.OnClickListener {
    AVLoadingIndicatorView indicator;
    private SharedPreferences sPreferences;
    private RecyclerView.LayoutManager layoutManager;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry, mTitle;
    Button changetype;
    private static RecyclerView loan_recycler;
    ArrayList<AllListItem> dataItem;
    LeaveListAdapter mLeaveListAdapter;
    int start=0;

    String status;
    CalendarView calendar;
    private String curDate;
    Button submit;
    LinearLayout visible_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.attendance_recycler, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {


        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.loan_recycler))
                .addErrorView(rootView.findViewById(R.id.error_view))

                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        loan_recycler = (RecyclerView) rootView.findViewById(R.id.loan_recycler);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        calendar=(CalendarView)rootView.findViewById(R.id.calendar);
        visible_layout=(LinearLayout)rootView.findViewById(R.id.visible_layout);

       /* indicator = (AVLoadingIndicatorView) rootView.findViewById(R.id.indicator);
*/
       /* indicator.setVisibility(View.GONE);*/
        submit=(Button)rootView.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        initRecyclerView();


        changetype = ((Button) rootView.findViewById(R.id.changetype));
        changetype.setOnClickListener(this);
       /* changetype.setImageResource(R.mipmap.ic_cal);*/
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mTitle.setText("Calendar");

        dataItem = new ArrayList<>();
        InitGetData(false);
    }

    private void InitGetData(boolean temp) {

        Config mConfig=new Config(getActivity());
//        Config mConfig=new Config(getActivity());
//        if (mConfig.isOnline(getActivity())) {
//            AttandanceFragment.LoadAttendanceList mLoadAttendanceList = new AttandanceFragment.LoadAttendanceList(sPreferences.getString("ID", ""),
//                    sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start),curDate);
//            mLoadAttendanceList.execute((Void) null);
//        } else {
//            switcher.showErrorView();
//        }

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                int d = dayOfMonth;
                int m=month;
                int len=String.valueOf(m).length();
                int length = String.valueOf(d).length();
                if((length==2)&&(len==2)){
                    curDate=year+"-"+month+"-"+String.valueOf(d);
                    //curDate =String.valueOf(d)+"-"+month+"-"+year;
                }
                else if((length==1)&&(len==2)) {
                    curDate=year+"-"+month+"-"+"0"+String.valueOf(d);
                    //  curDate ="0"+String.valueOf(d)+"-"+month+"-"+year;
                }
                else if((len==1)&&(length==2)) {
                    curDate=year+"-"+"0"+month+"-"+String.valueOf(d);
                    // curDate =String.valueOf(d)+"-"+"0"+month+"-"+year;
                }
                else {
                    curDate=year+"-"+"0"+month+"-"+"0"+String.valueOf(d);
                    // curDate ="0"+String.valueOf(d)+"-"+"0"+month+"-"+year;
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
                    AttandanceFragment.LoadAttendanceList mLoadAttendanceList = new AttandanceFragment.LoadAttendanceList(sPreferences.getString("ID", ""),
                            sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start),curDate);
                    mLoadAttendanceList.execute((Void) null);
                } else {
                    switcher.showErrorView();
                }
            case R.id.changetype:
                visible_layout.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
                loan_recycler.setVisibility(View.GONE);
                changetype.setVisibility(View.GONE);

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
           // switcher.showProgressView();
            visible_layout.setVisibility(View.GONE);
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
//                        System.out.println("hello"+jsonObj.toString());
                        JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.setEmployee_id(feedObj.getString("employee_id"));
                            item.setEmployee_name(feedObj.getString("employee_name"));
                            item.setEmployee_designation(feedObj.getString("designation"));
                            item.setCompany_name(feedObj.getString("company"));
                            item.setVehicle_status(feedObj.getString("employment id"));
                            dataItem.add(item);
                        }
                        start = dataItem.size();
                        mLeaveListAdapter = new LeaveListAdapter(getActivity(), getActivity(), dataItem, "attendance");
                        loan_recycler.setAdapter(mLeaveListAdapter);


                    } else if(jsonObj.getString("status").equals(String.valueOf(404))) {
                        switcher.showEmptyView();
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
                switcher.showEmptyView();

            } catch (NullPointerException e) {
//                visible_layout.setVisibility(View.GONE);
                switcher.showEmptyView();
            }
        }
    }
}