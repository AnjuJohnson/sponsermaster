package com.cutesys.sponsormasterfullversionnew.Recruitment.Travelstatus;

/**
 * Created by user on 4/1/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.NotifiedCandidateAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.RecruitmentListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Owner on 2/03/2017.
 */

public class NotifiedCandidateFragment  extends Fragment implements View.OnClickListener {
    private SharedPreferences sPreferences;
    private SharedPreferences.Editor prefEditor;
    RecyclerView cmpy_recycler9;
    Context c;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    ArrayList<RecruitmentListItem> dataItem;



    NotifiedCandidateAdapter mAdapter;
    private ProgressDialog dialog;
    int start = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.candidate_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        myOnClickListener = new MyOnClickListener(getActivity());



        cmpy_recycler9 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler9);
        cmpy_recycler9.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler9.setLayoutManager(linearLayoutManager);


        dataItem = new ArrayList<RecruitmentListItem>();
        mAdapter = new NotifiedCandidateAdapter(dataItem);
        cmpy_recycler9.setAdapter(mAdapter);



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
    private void InitIdView(View rootView){
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

        dataItem = new ArrayList<>();
        InitGetData(false);

    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadNotifiedcandidateInitiate mLoadNotifiedcandidateInitiate = new LoadNotifiedcandidateInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadNotifiedcandidateInitiate.execute((Void) null);
        }else {
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

    public class LoadNotifiedcandidateInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadNotifiedcandidateInitiate(String id, String authorization, boolean status, String Start) {
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
            HttpOperations web=new HttpOperations(getActivity());

            StringBuilder result = web.doNotifiedcandidateList(mId, mAuthorization,mStart);
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


                        JSONArray feedArray = jsonObj.getJSONArray("notified_candidate_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            RecruitmentListItem item = new RecruitmentListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("fullname").equals(""))
                                    && (!feedObj.getString("fullname").equals("null"))) {

                                if (feedObj.getString("candidate_id").trim().equals("")) {
                                    item.setcandidate_id("None");
                                } else {
                                    item.setcandidate_id((feedObj.getString("candidate_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("candidate_code").trim().equals("")) {
                                    item.setcandidate_code("None");
                                } else {
                                    item.setcandidate_code((feedObj.getString("candidate_code")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("fullname").trim().equals("")) {
                                    item.setfullname("None");
                                } else {
                                    item.setfullname(StringUtils.capitalize(feedObj.getString("fullname")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_type_id").trim().equals("")) {
                                    item.setvisa_type_id("None");
                                } else {
                                    item.setvisa_type_id((feedObj.getString("visa_type_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_type_name").trim().equals("")) {
                                    item.setvisa_type_name("None");
                                } else {
                                    item.setvisa_type_name(StringUtils.capitalize(feedObj.getString("visa_type_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_no").trim().equals("")) {
                                    item.setvisa_no("None");
                                } else {
                                    item.setvisa_no((feedObj.getString("visa_no")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_expiry").trim().equals("")) {
                                    item.setvisa_expiry("None");
                                } else {
                                    item.setvisa_expiry((feedObj.getString("visa_expiry")
                                            .toLowerCase().trim()));
                                }

                                dataItem.add(item);
                            }
                        }

                        start = dataItem.size();
                        mAdapter = new NotifiedCandidateAdapter(getActivity(), dataItem, "cpy");
                        cmpy_recycler9.setAdapter(mAdapter);
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