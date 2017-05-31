package com.cutesys.sponsormasterfullversionnew.Recruitment.Visa;

/**
 * Created by user on 4/1/2017.
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
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.VisaIssuedAdapter;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.VisaIssuedAdapter1;
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
 * Created by Owner on 21/03/2017.
 */

public class VisaIssuedList extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;
    TextView mTitile;
    private Switcher switcher;


    private RecyclerView cmpy_recycler9;
    ArrayList<AllListItem> dataItem;
    VisaIssuedAdapter1 mVisaIssuedAdapter1;
    int start = 0;
    String status;
    private TextView error_label_retry, empty_label_retry;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.visarejected_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {


        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.cmpy_recycler9))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        cmpy_recycler9 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler9);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        initRecyclerView();

        dataItem = new ArrayList<>();
        InitGetData(false);
    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            VisaIssuedList.LoadVisaIssuedList mLoadVisaIssuedList = new VisaIssuedList.LoadVisaIssuedList(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
            mLoadVisaIssuedList.execute((Void) null);
        } else {
            switcher.showErrorView("No Internet Connection");
        }
    }

    private void initRecyclerView() {
        cmpy_recycler9.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler9.setLayoutManager(linearLayoutManager);
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

    public class LoadVisaIssuedList extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadVisaIssuedList(String id, String authorization, String Start) {
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
            StringBuilder result = httpOperations.dorecruitmentvisaissuedList(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("visa_approved_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.setEmployee_id(feedObj.getString("candidate_id"));
                            item.setEmployee_name(feedObj.getString("fullname"));
                            if ((!feedObj.getString("fullname").equals(""))
                                    && (!feedObj.getString("fullname").equals("null"))) {


                                item.setEmployee_name(StringUtils.capitalize(feedObj.getString("fullname")
                                        .toLowerCase().trim()));
                                if (feedObj.getString("candidate_job").trim().equals("")) {
                                    item.setCompany_name("None");
                                } else {
                                    item.setCompany_name(feedObj.getString("candidate_job"));
                                }

                                if (feedObj.getString("candidate_code").trim().equals("")) {
                                    item.setcandidate_code("None");
                                } else {
                                    item.setcandidate_code(feedObj.getString("candidate_code"));
                                }

                                // item.setCompany_name(feedObj.getString("candidate_job"));
                                if (feedObj.getString("visa_no").trim().equals("")) {
                                    item.setCompany_model("None");
                                } else {
                                    item.setCompany_model(feedObj.getString("visa_no"));
                                }
                                //  item.setCompany_model(feedObj.getString("visa_no"));
                                if (feedObj.getString("visa_type_name").trim().equals("")) {
                                    item.setVehicle_status("None");
                                } else {
                                    item.setVehicle_status(feedObj.getString("visa_type_name"));
                                }
                                // item.setVehicle_status(feedObj.getString("visa_type_name"));
                                if (feedObj.getString("visa_expiry").trim().equals("")) {
                                    item.setEmployee_company("None");
                                } else {
                                    item.setEmployee_company(feedObj.getString("visa_expiry"));
                                }
                                //item.setEmployee_company(feedObj.getString("visa_expiry"));
                                if (feedObj.getString("notified_status").trim().equals("")) {
                                    item.setCompany_manufacture("None");
                                } else {
                                    item.setCompany_manufacture(feedObj.getString("notified_status"));
                                }
                                // item.setCompany_manufacture(feedObj.getString("notified_status"));
                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mVisaIssuedAdapter1 = new VisaIssuedAdapter1(getActivity(),getActivity(),dataItem);
                        cmpy_recycler9.setAdapter(mVisaIssuedAdapter1);

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