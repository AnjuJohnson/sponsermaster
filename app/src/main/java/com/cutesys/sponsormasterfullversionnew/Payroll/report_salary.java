package com.cutesys.sponsormasterfullversionnew.Payroll;

/**
 * Created by Owner on 24/03/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.reportsalaryadapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.basicsalarylist;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Owner on 14/03/2017.
 */

public class report_salary extends Fragment implements View.OnClickListener {
    private SharedPreferences sPreferences;

    private static final String[] topTitles = new String[]
            {"ID", "Name", "Designation",
                    "Salary", "Company"};
    private SharedPreferences.Editor prefEditor;
    RecyclerView cmpy_recycler7;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;

    private TextView mTitle,
            title1,title2,title3, title4, title5;
    Context c;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    ArrayList<basicsalarylist> dataItem;


   reportsalaryadapter mAdapter;
    private ProgressDialog dialog;
    int start = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.salaryreport_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        myOnClickListener = new MyOnClickListener(getActivity());


        cmpy_recycler7 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler7);
        cmpy_recycler7.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler7.setLayoutManager(linearLayoutManager);


        dataItem = new ArrayList<basicsalarylist>();
        mAdapter = new reportsalaryadapter(dataItem);
        cmpy_recycler7.setAdapter(mAdapter);


        InitIdView(rootView);
        return rootView;
    }




    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {


        }
    }

    private void InitIdView(View rootView) {
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.cmpy_recycler7))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();
        title1 = ((TextView) rootView.findViewById(R.id.title1));
        title2 = ((TextView) rootView.findViewById(R.id.title2));
        title3 = ((TextView) rootView.findViewById(R.id.title3));
        title4 = ((TextView) rootView.findViewById(R.id.title4));
        title5 = ((TextView) rootView.findViewById(R.id.title5));

        title1.setText("ID");
        title2.setText("Name");
        title3.setText("Designation");
        title4.setText("Salary");
        title5.setText("Company");

        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        cmpy_recycler7= (RecyclerView) rootView.findViewById(R.id.cmpy_recycler7);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);


        dataItem = new ArrayList<>();
        mTitle.setText("Salary List Report");
        InitGetData(false);

    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            LoadSalaryReportInitiate mLoadSalaryReportInitiate = new LoadSalaryReportInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadSalaryReportInitiate.execute((Void) null);
        } else {
            switcher.showErrorView("No Internet Connection");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //read username and password from SharedPreferences

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

        }
    }

    public class LoadSalaryReportInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadSalaryReportInitiate(String id, String authorization, boolean status, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStatus = status;
            mStart = Start;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switcher.showProgressView();


        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations web = new HttpOperations(getActivity());

            StringBuilder result = web.doreportsalaryList(mId, mAuthorization, mStart);
            System.out.println(result);
            return result;

        }


        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            System.out.println(result);

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();

                        JSONArray feedArray = jsonObj.getJSONArray("report_salary_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            basicsalarylist item = new basicsalarylist();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("full_name").equals(""))
                                    && (!feedObj.getString("full_name").equals("null"))) {

                                if (feedObj.getString("employee_id").trim().equals("")) {
                                    item.setemployee_id("None");
                                } else {
                                    item.setemployee_id(StringUtils.capitalize(feedObj.getString("employee_id")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("full_name").trim().equals("")) {
                                    item.setfull_name("None");
                                } else {
                                    item.setfull_name(StringUtils.capitalize(feedObj.getString("full_name")
                                            .toLowerCase().trim()));
                                }


                                if (feedObj.getString("employee_salary").trim().equals("")) {
                                    item.setemployee_salary("None");
                                } else {
                                    item.setemployee_salary(StringUtils.capitalize(feedObj.getString("employee_salary")
                                            .toLowerCase().trim()));
                                }


                                if (feedObj.getString("employee_designation").trim().equals("")) {
                                    item.setemployee_designation("None");
                                } else {
                                    item.setemployee_designation(StringUtils.capitalize(feedObj.getString("employee_designation")
                                            .toLowerCase().trim()));
                                }


                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.setcompany_name("None");
                                } else {
                                    item.setcompany_name(StringUtils.capitalize(feedObj.getString("company_name")
                                            .toLowerCase().trim()));
                                }


                                dataItem.add(item);
                            }
                           /* Log.d("11111111111",feedObj.getString("company_name"));*/
                        }

                        start = dataItem.size();
                        mAdapter = new reportsalaryadapter(getActivity(), dataItem, "cpy");
                        cmpy_recycler7.setAdapter(mAdapter);
                        System.out.println(result);

                    } else {
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

