package com.cutesys.sponsormasterfullversionnew.Employee;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.LeaveListAdapter2;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SpinnerAdapter2;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

/**
 * Created by Owner on 22/03/2017.
 */

public class LeaveListFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;

    private Switcher switcher;
    TextView error_label_retry, empty_label_retry,mTitle;

    private RecyclerView emptravelled;
    ArrayList<AllListItem> dataItem;
    ArrayList<AllListItem> dataIt;

    AVLoadingIndicatorView progress;
    LeaveListAdapter2 mLeaveListAdapter;
    int start = 0;
    String status;
    TextView txt;
    String mSpinnerItem = "",company_ID;
    Spinner mSpinnerCountry;
    TextView spinnererror;
    ImageView progressrestart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.leave_list_recy1, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {

        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.loan_recycler))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        emptravelled = (RecyclerView) rootView.findViewById(R.id.loan_recycler);
        mTitle=(TextView)rootView.findViewById(R.id.mTitle);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView) rootView.findViewById(R.id.empty_label_retry));
        progressrestart = (ImageView) rootView.findViewById(R.id.progressrestart);
        progressrestart.setOnClickListener(this);
        mSpinnerCountry = (Spinner)rootView.findViewById(R.id.spinner);
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        spinnererror = (TextView)rootView.findViewById(R.id.spinnererror);
        spinnererror.setVisibility(View.GONE);
        progress = (AVLoadingIndicatorView)rootView.findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);

        initRecyclerView();
        mTitle.setText("Leave List");
        dataItem = new ArrayList<>();

        InitGetData(false);



        mSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).getCompany_name();
                company_ID=dataItem.get(i).getcompany_id();
                System.out.println("zzzzzzzzz" + mSpinnerItem);
                Config mConfig = new Config(getActivity());
                if (mConfig.isOnline(getActivity())) {

                    LoadLeaveDetails mLoadLeaveDetails =
                            new LoadLeaveDetails(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start), company_ID);
                    mLoadLeaveDetails.execute((Void) null);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }



    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            LoadCompyListInitiate mLoadComapnyDetails = new LoadCompyListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
            mLoadComapnyDetails.execute((Void) null);
        }
        else {
            CustomToast.error(getActivity(),"No Internet Connection.").show();
        }
    }

    private void initRecyclerView() {
        emptravelled.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        emptravelled.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        Config mConfig=new Config(getActivity());
        int buttonId = view.getId();
        switch (buttonId) {

            case R.id.error_label_retry:
                InitGetData(false);
                break;
            case R.id.empty_label_retry:
                InitGetData(false);
                break;
            case R.id.progressrestart:
                if (mConfig.isOnline(getActivity())) {
dataItem.clear();
                    LoadCompyListInitiate mLoadComapnyDetails = new LoadCompyListInitiate(sPreferences.getString("ID", ""),
                            sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
                    mLoadComapnyDetails.execute((Void) null);
                }
                else {
                    CustomToast.error(getActivity(),"No Internet Connection.").show();
                }
        }

    }
    public class LoadCompyListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadCompyListInitiate(String id, String authorization,String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dataItem = new ArrayList<>();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations web=new HttpOperations(getActivity());

            StringBuilder result = web.doCompanyList(mId, mAuthorization,mStart);
            System.out.println(result);
            return result;
        }
        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            System.out.println(result);

           progress.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();

                        JSONArray feedArray = jsonObj.getJSONArray("company_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("company_name").equals(""))
                                    && (!feedObj.getString("company_name").equals("null"))) {
                                item.setcompany_id(feedObj.getString("company_id"));

                                item.setCompany_name(feedObj.getString("company_name"));
                                dataItem.add(item);

                            }
                            Log.d("11111111111",feedObj.getString("company_name"));
                        }
                           // start = dataItem.size();
                            SpinnerAdapter2 mAdapter = new SpinnerAdapter2(getActivity(),dataItem,"cpy");
                            mSpinnerCountry.setAdapter(mAdapter);
                            System.out.println(result);


                    }else {
                        switcher.showErrorView();
                    }
                }
                else {
                    switcher.showErrorView();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                switcher.showErrorView();

            } catch (NullPointerException e) {
                switcher.showErrorView();
            }
        }
    }

    public class LoadLeaveDetails extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart,mCompany_id;
        private boolean mStatus;

        LoadLeaveDetails(String id, String authorization, String Start,String company_id) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mCompany_id=company_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switcher.showProgressView();
            dataIt = new ArrayList<>();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doLeaveList(mId, mAuthorization,mStart,mCompany_id);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            emptravelled.setVisibility(View.VISIBLE);

            try {
//                JSONArray jsonarray=new JSONArray(result.toString());

//                JSONObject jsonObj=(JSONObject)jsonarray.get(0);
//                System.out.println("hello"+jsonObj.toString());
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.setEmployee_id(feedObj.getString("employee_id"));
                            item.setEmployee_name(feedObj.getString("employee_name"));
                            item.setEmployee_designation(feedObj.getString("designation"));
                            item.setCompany_name(feedObj.getString("company"));
                            item.setVehicle_status(feedObj.getString("Status"));
                            if(!feedObj.getString("employee_casual_leave").equals(String.valueOf(0))){
                                item.setcompany_code(feedObj.getString("employee_casual_leave"));
                            }
                            else {
                                item.setcompany_code("none");
                            }
                            if(!feedObj.getString("employee_medical_leave").equals(String.valueOf(0))) {
                                item.setcompany_email(feedObj.getString("employee_medical_leave"));
                            }
                            else {
                                item.setcompany_email("none");
                            }
                            if(!feedObj.getString("employee_annual_leave").equals(String.valueOf(0))) {
                                item.setAssigned_company(feedObj.getString("employee_annual_leave"));
                            }
                            else {
                                item.setAssigned_company("none");
                            }
                            if(!feedObj.getString("employee_emergency_leave").equals(String.valueOf(0))) {
                                item.setCompany_manufacturer(feedObj.getString("employee_emergency_leave"));
                            }
                            else {
                                item.setCompany_manufacturer("none");
                            }
                            if(!feedObj.getString("employee_absence_leave").equals(String.valueOf(0))) {
                                item.setcompany_address(feedObj.getString("employee_absence_leave"));
                            }
                            else {
                                item.setcompany_address("none");
                            }
                            if(!feedObj.getString("employee_other_leave").equals(String.valueOf(0))) {
                                item.setEmployee_mail(feedObj.getString("employee_other_leave"));
                            }
                            else {
                                item.setEmployee_mail("none");
                            }

                            dataIt.add(item);
                        }
                        start = dataIt.size();
                        mLeaveListAdapter = new LeaveListAdapter2(getActivity(),getActivity(),dataIt,"leave");
                        emptravelled.setAdapter(mLeaveListAdapter);

                    }else if(jsonObj.getString("status").equals(String.valueOf(404))) {
                        switcher.showEmptyView();
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
                switcher.showErrorView();
            } catch (NullPointerException e) {
                switcher.showErrorView();
            }
            catch (Exception e) {
                //CustomToast.error(getActivity(), "No Internet Connection.").show();
                 switcher.showErrorView("Please Try Again");
            }
        }
    }
}


