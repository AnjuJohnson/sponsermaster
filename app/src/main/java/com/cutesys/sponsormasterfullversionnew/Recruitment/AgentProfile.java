package com.cutesys.sponsormasterfullversionnew.Recruitment;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScrollRecyclerView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.agentprofileadapter;
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
 * Created by Owner on 1/06/2017.
 */

public class AgentProfile extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private FastScrollRecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle;

    private agentprofileadapter magentprofileadapter;

    ArrayList<RecruitmentListItem> dataItem;
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
        mrecyclerview = ((FastScrollRecyclerView)rootView.findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataItem = new ArrayList<>();
        mTitle.setText("Agent List");
        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadCompyStatusInitiate mLoadCompyStatusInitiate = new LoadCompyStatusInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadCompyStatusInitiate.execute((Void) null);
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

    public class LoadCompyStatusInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadCompyStatusInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = httpOperations.dorecruitmentagentList(mId, mAuthorization,mStart);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            if(mStatus == true){

            }
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("agent_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            RecruitmentListItem item = new RecruitmentListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("agent_company").equals(""))
                                    && (!feedObj.getString("agent_company").equals("null"))) {
                                if (feedObj.getString("agent_id").trim().equals("")) {
                                    item.set_id("None");
                                } else {
                                    item.set_id((feedObj.getString("agent_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_company").trim().equals("")) {
                                    item.set_name("None");
                                } else {
                                    item.set_name(StringUtils.capitalize(feedObj.getString("agent_company")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_area_id").trim().equals("")) {
                                    item.setagent_area_id("None");
                                } else {
                                    item.setagent_area_id((feedObj.getString("agent_area_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_place").trim().equals("")) {
                                    item.setagent_place("None");
                                } else {
                                    item.setagent_place(StringUtils.capitalize(feedObj.getString("agent_place")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_country").trim().equals("")) {
                                    item.setagent_country("None");
                                } else {
                                    item.setagent_country(StringUtils.capitalize(feedObj.getString("agent_country")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_email").trim().equals("")) {
                                    item.setcandidate_email("None");
                                } else {
                                    item.setcandidate_email(StringUtils.capitalize(feedObj.getString("agent_email")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_phone").trim().equals("")) {
                                    item.setcandidate_phone("None");
                                } else {
                                    item.setcandidate_phone(StringUtils.capitalize(feedObj.getString("agent_phone")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_zipcode").trim().equals("")) {
                                    item.setcandidate_code("None");
                                } else {
                                    item.setcandidate_code(StringUtils.capitalize(feedObj.getString("agent_zipcode")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("agent_state").trim().equals("")) {
                                    item.setcandidate_status("None");
                                } else {
                                    item.setcandidate_status(StringUtils.capitalize(feedObj.getString("agent_state")
                                            .toLowerCase().trim()));
                                } if (feedObj.getString("agent_address").trim().equals("")) {
                                    item.setcandidate_designation("None");
                                } else {
                                    item.setcandidate_designation(StringUtils.capitalize(feedObj.getString("agent_address")
                                            .toLowerCase().trim()));
                                }

                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        magentprofileadapter = new agentprofileadapter(getActivity(), getActivity(),dataItem,"agent");
                        mrecyclerview.setAdapter(magentprofileadapter);

                    }
                    else {
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
