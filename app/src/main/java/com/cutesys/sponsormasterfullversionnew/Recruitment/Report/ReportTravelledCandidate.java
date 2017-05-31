package com.cutesys.sponsormasterfullversionnew.Recruitment.Report;

/**
 * Created by user on 3/29/2017.
 */

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.VisaProcessAdapter;
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
 * Created by Owner on 29/03/2017.
 */

public class ReportTravelledCandidate extends Fragment {


    private SharedPreferences sPreferences;

    private static final String[] topTitles = new String[]
            {"ID", "Candidate Name", "Nationality",
                    "Entry Date", "Visa Type", "Visa No"};
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;
    private static RecyclerView mrecyclerview;
    LinearLayout swipeview;
    private static ArrayList<RecruitmentListItem> data;
    static View.OnClickListener myOnClickListener;
    Context c;
    VisaProcessAdapter mVisaProcessAdapter;
    private TextView title1,title2,title3, title4, title5 ,title6,title7,title8,title9,mTitle;
    int start = 0;
    int side;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report_selection_status_new, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        myOnClickListener = new ReportTravelledCandidate.MyOnClickListener(getActivity());
        InitIdView(rootView);
        return rootView;

    }
    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }


        @Override
        public void onClick(View view) {

        }
    }

    private void InitIdView(View rootView) {
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.mrecyclerview))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        mrecyclerview = (RecyclerView) rootView.findViewById(R.id.mrecyclerview);
        title1 = ((TextView) rootView.findViewById(R.id.title1));
        title2 = ((TextView) rootView.findViewById(R.id.title2));
        title3 = ((TextView) rootView.findViewById(R.id.title3));
        title4 = ((TextView) rootView.findViewById(R.id.title4));
        title5 = ((TextView) rootView.findViewById(R.id.title5));
        title6 = ((TextView) rootView.findViewById(R.id.title6));
        title7 = ((TextView) rootView.findViewById(R.id.title7));
        title8 = ((TextView) rootView.findViewById(R.id.title8));
        title9 = ((TextView) rootView.findViewById(R.id.title9));
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        title1.setText("ID");
        title2.setText("Candidate Name");
        title4.setText("Nationality");
        title7.setText("Entry Date");
        title8.setText("Visa Type");
        title9.setText("Visa No");
        title3.setVisibility(View.GONE);
        title5.setVisibility(View.GONE);
        title6.setVisibility(View.GONE);

        mrecyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(linearLayoutManager);

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        //swipeview=(LinearLayout) rootView.findViewById(R.id.swipeview);

        error_label_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitGetData(false);
            }
        });
        empty_label_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitGetData(false);
            }
        });
        mTitle.setText("Travelled Candidates");
        data = new ArrayList<>();
        InitGetData(false);
    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadTravelledcandidatereportInitiate mLoadTravelledcandidatereportInitiate= new LoadTravelledcandidatereportInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadTravelledcandidatereportInitiate.execute((Void) null);
        }else {
            switcher.showErrorView();
        }
    }
    //    @Override
//    public void onStart() {
//        super.onStart();
//        //read username and password from SharedPreferences
//
//    }
    public class  LoadTravelledcandidatereportInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadTravelledcandidatereportInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = web.doreporttravelledcandidate(mId, mAuthorization,mStart);;;

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
                        JSONArray feedArray = jsonObj.getJSONArray("report_travelled_candidate_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            RecruitmentListItem item = new RecruitmentListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("fullname").equals(""))
                                    && (!feedObj.getString("fullname").equals("null"))) {
                                if (feedObj.getString("candidate_id").trim().equals("")) {
                                    item.setcandidate_id("None");
                                } else {
                                    item.setcandidate_id(StringUtils.capitalize(feedObj.getString("candidate_id")
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
                                if (feedObj.getString("entry_date").trim().equals("")) {
                                    item.setcandidate_entry_date("None");
                                } else {
                                    item.setcandidate_entry_date((feedObj.getString("entry_date")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("candidate_nationality").trim().equals("")) {
                                    item.setcandidate_nationality("None");
                                } else {
                                    item.setcandidate_nationality(StringUtils.capitalize(feedObj.getString("candidate_nationality")
                                            .toLowerCase().trim()));
                                }
                                data.add(item);
                            }
                        }
                        start = data.size();
                        mVisaProcessAdapter = new VisaProcessAdapter(getActivity(), data,"reporttravelledprocess");
                        mrecyclerview.setAdapter(mVisaProcessAdapter);

                    }
                    else {
                        switcher.showEmptyView();
                    }
                }

            }catch(JSONException e){
                e.printStackTrace();
            }catch (NullPointerException e){
                switcher.showErrorView();
            }


        }
    }
}