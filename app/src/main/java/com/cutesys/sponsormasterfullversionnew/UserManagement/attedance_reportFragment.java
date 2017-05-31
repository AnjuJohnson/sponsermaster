package com.cutesys.sponsormasterfullversionnew.UserManagement;

/**
 * Created by Owner on 13/05/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Edittext.MaterialEditText;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.attendanceitem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.logindetailsitem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Kris on 3/22/2017.
 */

public class attedance_reportFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    ArrayList<attendanceitem> dataItem;
    String mSpinnerItem = "",mSpinnerItem1 = "",mSpinnerItem2 = "";

    private Spinner spinner_desig, spinner_name, spinner_id;
    private ImageView progressrestart_desig,progressrestart_name,progressrestart_id,datepicker;
    private TextView spinnererror_desig,spinnererror_name,spinnererror_id;

    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading, progress_desig,progress_name,progress_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.attedance_report, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){

        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        done = (ImageView) rootView.findViewById(R.id.done);
        close = (ImageView) rootView.findViewById(R.id.close);
        loading = (AVLoadingIndicatorView) rootView.findViewById(R.id.loading);

        progress_desig = (AVLoadingIndicatorView) rootView.findViewById(R.id.progress_desig);
        progress_name = (AVLoadingIndicatorView) rootView.findViewById(R.id.progress_name);
        progress_id = (AVLoadingIndicatorView) rootView.findViewById(R.id.progress_id);

        progressrestart_desig = (ImageView) rootView.findViewById(R.id.progressrestart_desig);
        progressrestart_desig.setOnClickListener(this);

       progressrestart_name = (ImageView) rootView.findViewById(R.id.progressrestart_name);
        progressrestart_name.setOnClickListener(this);

       progressrestart_id = (ImageView) rootView.findViewById(R.id.progressrestart_id);
        progressrestart_id.setOnClickListener(this);

        spinnererror_desig= (TextView) rootView.findViewById(R.id.spinnererror_desig);
        spinnererror_desig.setVisibility(View.GONE);

        spinnererror_name = (TextView) rootView.findViewById(R.id.spinnererror_name);
        spinnererror_name.setVisibility(View.GONE);

        spinnererror_id = (TextView) rootView.findViewById(R.id.spinnererror_id);
        spinnererror_id.setVisibility(View.GONE);

        spinner_desig = (Spinner)rootView.findViewById(R.id.spinner_desig);
        spinner_name = (Spinner)rootView.findViewById(R.id.spinner_name);
        spinner_id= (Spinner)rootView.findViewById(R.id.spinner_id);
        loading.setVisibility(View.GONE);
        progress_desig.setVisibility(View.GONE);
        progress_name.setVisibility(View.GONE);
        progress_id.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Attendance Report");

        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadReportInitiate mLoadReportInitiate  = new LoadReportInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),sPreferences.getString("START", ""),
                    sPreferences.getString("DESIGNATION", ""),sPreferences.getString("EMPLOYEE_ID", ""),
                    sPreferences.getString("YEAR", ""),sPreferences.getString("MONTH", ""),
                    sPreferences.getString("DAY", ""));
            mLoadReportInitiate .execute((Void) null);
        }else {
            CustomToast.error(getActivity(),"No Internet Connection.").show();
        }
        Config mConfig1 = new Config(getActivity());
        if(mConfig1.isOnline(getActivity())){
            LoadReportInitiate mLoadReportInitiate  = new LoadReportInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),sPreferences.getString("START", ""),
                    sPreferences.getString("DESIGNATION", ""),sPreferences.getString("EMPLOYEE_ID", ""),
                    sPreferences.getString("YEAR", ""),sPreferences.getString("MONTH", ""),
                    sPreferences.getString("DAY", ""));
            mLoadReportInitiate .execute((Void) null);
        }else {
            CustomToast.error(getActivity(),"No Internet Connection.").show();
        }
        Config mConfig2 = new Config(getActivity());
        if(mConfig2.isOnline(getActivity())){
            LoadReportInitiate mLoadReportInitiate  = new LoadReportInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),sPreferences.getString("START", ""),
                    sPreferences.getString("DESIGNATION", ""),sPreferences.getString("EMPLOYEE_ID", ""),
                    sPreferences.getString("YEAR", ""),sPreferences.getString("MONTH", ""),
                    sPreferences.getString("DAY", ""));
            mLoadReportInitiate .execute((Void) null);
        }else {
            CustomToast.error(getActivity(),"No Internet Connection.").show();
        }

        spinner_desig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).getemployee_designation();
                //System.out.println("zzzzzzzzz"+mSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem1 = dataItem.get(i).getfull_name();
                //System.out.println("zzzzzzzzz"+mSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem2 = dataItem.get(i).getemployee_employment_id();
                //System.out.println("zzzzzzzzz"+mSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
/*
    private void AddVisaType(){
        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    mSpinnerItem.toString(),
                    mSpinnerItem1.toString(),
                    mSpinnerItem2.toString());

            mLoadAddEvents.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
        Config mConfig1 = new Config(getApplicationContext());
        if(mConfig1.isOnline(getApplicationContext())){
            LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    mSpinnerItem.toString(),
                    mSpinnerItem1.toString(),
                    mSpinnerItem2.toString());


            mLoadAddEvents.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
        Config mConfig2 = new Config(getApplicationContext());
        if(mConfig2.isOnline(getApplicationContext())){
            LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    mSpinnerItem.toString(),
                    mSpinnerItem1.toString(),
                    mSpinnerItem2.toString());


            mLoadAddEvents.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
    }*/

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();

        switch (buttonId) {
            case R.id.done:

                if(loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getActivity(),"Please wait while we process your request").show();
                }else {
                    if (mSpinnerItem.toString().trim().equals("")){

                        spinnererror_desig.setVisibility(View.VISIBLE);

                    }
                    if (mSpinnerItem1.toString().trim().equals("")){

                        spinnererror_name.setVisibility(View.VISIBLE);

                    }
                    if (mSpinnerItem2.toString().trim().equals("")){

                        spinnererror_id.setVisibility(View.VISIBLE);

                    }


                }
                break;
            case R.id.close:
                if(loading.getVisibility() == View.VISIBLE) {


                    CustomToast.info(getActivity(),"Please wait while we process your request").show();
                }else {
                    Close_view();
                }
                break;

            case R.id.progressrestart_desig:
                Config mConfig = new Config(getActivity());
                if(mConfig.isOnline(getActivity())){
                    LoadReportInitiate  mLoadReportInitiate =
                            new  LoadReportInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""),sPreferences.getString("START", ""),
                                    sPreferences.getString("DESIGNATION", ""),sPreferences.getString("EMPLOYEE_ID", ""),
                                    sPreferences.getString("YEAR", ""),sPreferences.getString("MONTH", ""),
                                    sPreferences.getString("DAY", ""));
                    mLoadReportInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getActivity(),"No Internet Connection.").show();
                }
                break;
            case R.id.progressrestart_name:
                Config mConfig1 = new Config(getActivity());
                if(mConfig1.isOnline(getActivity())){
                    LoadReportInitiate  mLoadReportInitiate =
                            new  LoadReportInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""),sPreferences.getString("START", ""),
                                    sPreferences.getString("DESIGNATION", ""),sPreferences.getString("EMPLOYEE_ID", ""),
                                    sPreferences.getString("YEAR", ""),sPreferences.getString("MONTH", ""),
                                    sPreferences.getString("DAY", ""));
                    mLoadReportInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getActivity(),"No Internet Connection.").show();
                }
                break;
            case R.id.progressrestart_id:
                Config mConfig2 = new Config(getActivity());
                if(mConfig2.isOnline(getActivity())){
                    LoadReportInitiate  mLoadReportInitiate =
                            new  LoadReportInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""),sPreferences.getString("START", ""),
                                    sPreferences.getString("DESIGNATION", ""),sPreferences.getString("EMPLOYEE_ID", ""),
                                    sPreferences.getString("YEAR", ""),sPreferences.getString("MONTH", ""),
                                    sPreferences.getString("DAY", ""));
                    mLoadReportInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getActivity(),"No Internet Connection.").show();
                }
                break;

        }
    }

    public class   LoadReportInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart,designation,employee_id,year,month,day;

        LoadReportInitiate(String id, String authorization, String Start,String designation,String employee_id,String year,String month,String day) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            designation=designation;
            employee_id=employee_id;
            year=year;
            month=month;
            day=day;
            mSpinnerItem = "";
            mSpinnerItem1 = "";
            mSpinnerItem2 = "";

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataItem = new ArrayList<>();
            progress_desig.setVisibility(View.VISIBLE);
            progress_name.setVisibility(View.VISIBLE);
            progress_id.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doAttendanceReport(mId, mAuthorization,mStart,designation,employee_id,year,month,day);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            progress_desig.setVisibility(View.GONE);
            progress_name.setVisibility(View.GONE);
            progress_id.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        JSONArray feedArray = jsonObj.getJSONArray("admin_details");
                        for (int i = 0; i < feedArray.length(); i++) {

                            attendanceitem item = new attendanceitem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("full_name").equals(""))
                                    && (!feedObj.getString("full_name").equals("null"))) {
                                if (feedObj.getString("employee_id").trim().equals("")) {
                                    item.setemployee_id("None");
                                } else {
                                    item.setemployee_id(StringUtils.capitalize(feedObj.getString("employee_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("employee_employment_id").trim().equals("")) {
                                    item.setemployee_employment_id("None");
                                } else {
                                    item.setemployee_employment_id(StringUtils.capitalize(feedObj.getString("employee_employment_id")
                                            .toLowerCase().trim()));
                                }


                                if (feedObj.getString("full_name").trim().equals("")) {
                                    item.setfull_name("None");
                                } else {
                                    item.setfull_name(StringUtils.capitalize(feedObj.getString("full_name")
                                            .toLowerCase().trim()));
                                }


                                if (feedObj.getString("attendance_date").trim().equals("")) {
                                    item.setattendance_date("None");
                                } else {
                                    item.setattendance_date(StringUtils.capitalize(feedObj.getString("attendance_date")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("employee_designation").trim().equals("")) {
                                    item.setemployee_designation("None");
                                } else {
                                    item.setemployee_designation(StringUtils.capitalize(feedObj.getString("employee_designation")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("time_in").trim().equals("")) {
                                    item.settime_in("None");
                                } else {
                                    item.settime_in(StringUtils.capitalize(feedObj.getString("time_in")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("time_out").trim().equals("")) {
                                    item.settime_out("None");
                                } else {
                                    item.settime_out(StringUtils.capitalize(feedObj.getString("time_out")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("attendance_punch_in_time").trim().equals("")) {
                                    item.setattendance_punch_in_time("None");
                                } else {
                                    item.setattendance_punch_in_time(StringUtils.capitalize(feedObj.getString("attendance_punch_in_time")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("attendance_punch_in_location").trim().equals("")) {
                                    item.setattendance_punch_in_location("None");
                                } else {
                                    item.setattendance_punch_in_location(StringUtils.capitalize(feedObj.getString("attendance_punch_in_location")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("attendance_punch_out_time").trim().equals("")) {
                                    item.setattendance_punch_out_time("None");
                                } else {
                                    item.setattendance_punch_out_time(StringUtils.capitalize(feedObj.getString("attendance_punch_out_time")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("attendance_punch_out_location").trim().equals("")) {
                                    item.setattendance_punch_out_location("None");
                                } else {
                                    item.setattendance_punch_out_location(StringUtils.capitalize(feedObj.getString("attendance_punch_out_location")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("attendance_punch_in_ip").trim().equals("")) {
                                    item.setattendance_punch_in_ip("None");
                                } else {
                                    item.setattendance_punch_in_ip(StringUtils.capitalize(feedObj.getString("attendance_punch_in_ip")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("attendance_punch_out_ip").trim().equals("")) {
                                    item.setattendance_punch_out_ip("None");
                                } else {
                                    item.setattendance_punch_out_ip(StringUtils.capitalize(feedObj.getString("attendance_punch_out_ip")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("report_id").trim().equals("")) {
                                    item.setreport_id("None");
                                } else {
                                    item.setreport_id(StringUtils.capitalize(feedObj.getString("report_id")
                                            .toLowerCase().trim()));
                                }


                                dataItem.add(item);
                            }
                        }
                        reportspinner mSpinnerAdapter = new reportspinner(getActivity(),
                                dataItem);
                        spinner_desig.setAdapter(mSpinnerAdapter);
                       reportspinner1 mSpinnerAdapter1 = new reportspinner1(getActivity(),
                                dataItem);
                        spinner_name.setAdapter(mSpinnerAdapter1);
                        reportspinner2 mSpinnerAdapter2 = new reportspinner2(getActivity(),
                                dataItem);
                        spinner_id.setAdapter(mSpinnerAdapter2);

                    }else {
                        CustomToast.info(getActivity(),"" +
                                "No item found").show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                CustomToast.info(getActivity(),"Please try again").show();
            } catch (NullPointerException e) {
                CustomToast.error(getActivity(),"No Internet Connection.").show();
            } catch (Exception e) {
                CustomToast.info(getActivity(),"Please try again").show();
            }
        }
    }

   /* public class LoadAddEvents extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization,mcompany, mdesignation,memployee_id,memployee_name,musername,mpassword;

        LoadAddEvents(String id, String authorization,
                      String company,String designation,String employee_id,String employee_name,String username,String password ) {
            mId = id;
            mAuthorization = authorization;
            mcompany=company;
            mdesignation=designation;


            memployee_id=employee_id;
            memployee_name=employee_name;
            musername=username;
            mpassword=password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());

            System.out.println("zzzz"+mId+ "zzzz"+mAuthorization+
                    "zzzz"+mcompany+"zzzz"+mdesignation+"zzzz"+memployee_id+"zzz"+memployee_name+
                    "zzzz"+musername+"zzzz"+mpassword);
            StringBuilder result = httpOperations.doAddLogin(mId, mAuthorization,
                    mcompany,mdesignation,memployee_id,memployee_name.replaceAll(" ","%20"),musername.replaceAll(" ","%20"),mpassword);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            loading.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(404))) {

                        emp_id.setText("");
                        emp_username.setText("");
                        emp_password.setText("");
                        CustomToast.info(getApplicationContext(), " Already exist").show();


                    } else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        emp_id.setText("");
                        emp_username.setText("");
                        emp_password.setText("");
                        CustomToast.success(getApplicationContext(), " Added Successfully").show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                CustomToast.info(getApplicationContext(), "Please Try Again").show();
            } catch (NullPointerException e) {
                CustomToast.error(getApplicationContext(), "No Internet Connection.").show();
            } catch (Exception e) {
                CustomToast.info(getApplicationContext(), "Please Try Again").show();
            }
        }
    }*/

    private void Close_view(){
        Intent intent = new Intent(getActivity(),DashboardActivity.class);
        intent.putExtra("PAGE","LOGIN LIST");
        startActivity(intent);
       /* overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();*/
    }

    /*@Override
    public void onBackPressed() {
    }*/
}
