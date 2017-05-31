package com.cutesys.sponsormasterfullversionnew.Recruitment.SelectionStatus;

/**
 * Created by user on 3/30/2017.
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
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.CandidateListAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.CandidateListItem;
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

public class CandidateListFragment  extends Fragment implements View.OnClickListener {
    private SharedPreferences sPreferences;
    private SharedPreferences.Editor prefEditor;
    RecyclerView cmpy_recycler9;
    Context c;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry, mTitle, msubtext;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    ArrayList<CandidateListItem> dataItem;



    CandidateListAdapter mAdapter;
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


        dataItem = new ArrayList<CandidateListItem>();
        mAdapter = new CandidateListAdapter(dataItem);
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
            LoadCandidateListInitiate mLoadCandidateListInitiate = new LoadCandidateListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadCandidateListInitiate.execute((Void) null);
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

    public class LoadCandidateListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadCandidateListInitiate(String id, String authorization, boolean status, String Start) {
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

            StringBuilder result = web.doCandidateList(mId, mAuthorization,mStart);
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


                        JSONArray feedArray = jsonObj.getJSONArray("candidate_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            CandidateListItem item = new CandidateListItem();
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

                                if (feedObj.getString("candidate_email").trim().equals("")) {
                                    item.setcandidate_email("None");
                                } else {
                                    item.setcandidate_email(StringUtils.capitalize(feedObj.getString("candidate_email")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("candidate_phone").trim().equals("")) {
                                    item.setcandidate_phone("None");
                                } else {
                                    item.setcandidate_phone(StringUtils.capitalize(feedObj.getString("candidate_phone")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("application_job_position").trim().equals("")) {
                                    item.setapplication_job_position("None");
                                } else {
                                    item.setapplication_job_position(StringUtils.capitalize(feedObj.getString("application_job_position")
                                            .toLowerCase().trim()));
                                }

                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mAdapter = new CandidateListAdapter(getActivity(), dataItem, "cpy");
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