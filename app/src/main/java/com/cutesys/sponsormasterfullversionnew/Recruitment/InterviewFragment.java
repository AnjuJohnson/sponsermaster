package com.cutesys.sponsormasterfullversionnew.Recruitment;

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
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.InterviewAdapter;
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

public class InterviewFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry, mTitile;
    private RecyclerView emptravelled;
    ArrayList<AllListItem> dataItem;
    InterviewAdapter mInterviewAdapter;
    int start = 0;
    String status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.emp_travel_recycler, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {


        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.travel_recycler))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        emptravelled = (RecyclerView) rootView.findViewById(R.id.travel_recycler);

        mTitile=(TextView)rootView.findViewById(R.id.mTitle);
        mTitile.setText("Interview List");
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
            InterviewFragment.LoadInterviewDetails mLoadInterviewDetails = new InterviewFragment.LoadInterviewDetails(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
            mLoadInterviewDetails.execute((Void) null);
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
        }

    }

    public class LoadInterviewDetails extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadInterviewDetails(String id, String authorization, String Start) {
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
            StringBuilder result = httpOperations.doInterviewlist(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("interview_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.setEmployee_id(feedObj.getString("interview_id"));
                            if ((!feedObj.getString("interview_name").equals(""))
                                    && (!feedObj.getString("interview_name").equals("null"))) {
                                item.setEmployee_name(StringUtils.capitalize(feedObj.getString("interview_name")
                                        .toLowerCase().trim()));
                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.setEmployee_name("None");
                                } else {
                                    item.setEmployee_name(feedObj.getString("company_name"));
                                }
                           /* item.setEmployee_name(feedObj.getString("company_name"));*/
                                if (feedObj.getString("interview_name").trim().equals("")) {
                                    item.setcompany_address("None");
                                } else {
                                    item.setcompany_address(feedObj.getString("interview_name"));
                                }

                                if (feedObj.getString("interview_auto_id").trim().equals("")) {
                                    item.setinterview_auto_id("None");
                                } else {
                                    item.setinterview_auto_id(feedObj.getString("interview_auto_id"));
                                }
                                // item.setcompany_address(feedObj.getString("interview_name"));
                                if (feedObj.getString("place").trim().equals("")) {
                                    item.setcompany_code("None");
                                } else {
                                    item.setcompany_code(feedObj.getString("place"));
                                }
                                // item.setcompany_code(feedObj.getString("place"));
                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mInterviewAdapter = new InterviewAdapter(getActivity(),getActivity(),dataItem,"interview");
                        emptravelled.setAdapter(mInterviewAdapter);

                    }else {
                        switcher.showEmptyView();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

                switcher.showErrorView();
            }
        }
    }
}
