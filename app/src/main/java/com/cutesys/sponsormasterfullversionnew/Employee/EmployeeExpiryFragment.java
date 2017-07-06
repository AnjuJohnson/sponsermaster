/*
 * Copyright 2016 Cutesys Technologies Pvt Ltd as an unpublished work. All Rights
 * Reserved.
 *
 * The information contained herein is confidential property of Cutesys Technologies
 * Pvt Ltd. The use, copying,transfer or disclosure of such information is prohibited
 * except by express written agreement with Company.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * File Name 					: EmployeeFragment
 * Since 						: 11/11/16
 * Version Code & Project Name	: v 1.0 Sponser Master
 * Author Name					: Athira Santhosh
 */
package com.cutesys.sponsormasterfullversionnew.Employee;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.ExpiryRecyclerAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by athira on 11/11/16.
 */
public class EmployeeExpiryFragment extends Fragment implements View.OnClickListener{
    private SharedPreferences sPreferences;

    private Switcher switcher;
    private RecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle;

    private ExpiryRecyclerAdapter mExpiryRecyclerAdapter;

    ArrayList<ListItem> dataItem;
    int start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.documents_fragment, container, false);
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

        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataItem = new ArrayList<>();
        mTitle.setText("Employee Documents Expiry");
        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadExpiryListInitiate mLoadExpiryListInitiate = new LoadExpiryListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadExpiryListInitiate.execute((Void) null);
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
        }
    }

    public class LoadExpiryListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadExpiryListInitiate(String id, String authorization, boolean status, String Start) {
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

            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doEMPY_DOC_EXPIRY_LIST(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("employee_document_expiring_date_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("employee_name").equals(""))
                                    && (!feedObj.getString("employee_name").equals("null"))) {

                                item.set_id(feedObj.getString("employee_id"));
                                item.set_email(feedObj.getString("employee_code"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("employee_name")
                                        .toLowerCase().trim()));
                                JSONArray exArray = feedObj.getJSONArray("doc_exp_date");
                               System.out.println("length="+exArray.length());
                                try {
                                    //JSONArray docArray = exArray.getJSONArray(0);
                                    String[] docdata = new String[exArray.length()];
                                    String[] docdatadate = new String[exArray.length()];

if(exArray.length()!=0){
    for (int j = 0; j < exArray.length(); j++) {
        JSONObject expiryObj = (JSONObject) exArray.get(j);

        if(expiryObj.getString("document_title").equals("")){
            docdata[j] = "None";
        } else {
            docdata[j] = expiryObj.getString("document_title");
        }

        if(expiryObj.getString("document_end_date").equals("")){
            docdatadate[j] = "None";
        } else {
            docdatadate[j] = expiryObj.getString("document_end_date");
        }
    }
    item.setTitle(docdata);
    item.setTitle_date(docdatadate);
    dataItem.add(item);
}


                                }catch (Exception e){}
                            }
                        }
                        start = dataItem.size();
                        mExpiryRecyclerAdapter = new ExpiryRecyclerAdapter(getActivity(), dataItem,"employee");
                        mrecyclerview.setAdapter(mExpiryRecyclerAdapter);

                    }else {
                        switcher.showEmptyView();
                    }
                }

            }catch (JSONException e) {
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