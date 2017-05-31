package com.cutesys.sponsormasterfullversionnew.Employee;

/**
 * Created by user on 3/28/2017.
 */

import android.content.Intent;
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

import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.ADDModule.AddHoliday;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.EmpManageholidayAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Owner on 18/03/2017.
 */

public class EmpManageHolidayFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private FloatingActionButton mAdd;
    private RecyclerView emptravelled;
    ArrayList<ListItem> dataItem;
    EmpManageholidayAdapter mEmpManageHolidayAdapter;
    int start = 0;
    String status;
    private TextView error_label_retry, empty_label_retry, mTitle, msubtext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.emp_manage_holiday_recycler, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {


        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.Holiday_recycler))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        msubtext = ((TextView) rootView.findViewById(R.id.msubtext));
        emptravelled = (RecyclerView) rootView.findViewById(R.id.Holiday_recycler);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        mAdd=(FloatingActionButton)rootView.findViewById(R.id.add_holiday);
        mAdd.setOnClickListener(this);
        initRecyclerView();
        mTitle.setText("Holiday List");
        msubtext.setText("Holiday");
        dataItem = new ArrayList<>();
        InitGetData(false);
    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            EmpManageHolidayFragment.LoadEmployeeManageHolidayDetails mLoadEmployeeManageHolidayDetails = new EmpManageHolidayFragment.LoadEmployeeManageHolidayDetails(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
            mLoadEmployeeManageHolidayDetails.execute((Void) null);
        } else {
            switcher.showErrorView();
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
        int buttonId = view.getId();
        switch (buttonId) {

            case R.id.error_label_retry:
                InitGetData(false);
                break;
            case R.id.empty_label_retry:
                InitGetData(false);
                break;
            case R.id.add_holiday:
                Intent intent=new Intent(getActivity(),AddHoliday.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                getActivity().finish();
        }

    }

    public class LoadEmployeeManageHolidayDetails extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadEmployeeManageHolidayDetails(String id, String authorization, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switcher.showProgressView();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doEmpHolidayList(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("holiday_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.set_id(feedObj.getString("id"));
                            item.set_name(feedObj.getString("holiday"));
                            dataItem.add(item);
                        }
                        start = dataItem.size();
                        mEmpManageHolidayAdapter = new EmpManageholidayAdapter(getActivity(),getActivity(),dataItem,"holiday");
                        emptravelled.setAdapter(mEmpManageHolidayAdapter);

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

