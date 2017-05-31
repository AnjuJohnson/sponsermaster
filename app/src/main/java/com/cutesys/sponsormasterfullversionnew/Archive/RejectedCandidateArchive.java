package com.cutesys.sponsormasterfullversionnew.Archive;

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
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.RecruitmentArchiveAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ArchiveListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 3/20/2017.
 */

public class RejectedCandidateArchive extends Fragment {


    private SharedPreferences sPreferences;
//    private static RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;

    private static final String[] topTitles = new String[]
            {"Candidate ID", "Candidate Name", "Applied For","Phone",
                    "E-Mail"};

    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;

    private static RecyclerView mrecyclerview;
    LinearLayout swipeview;
    private static ArrayList<ArchiveListItem> data;
    private TextView title1,title2,title4,title5,title6,title7,title8,title9,title10,title11,
            title12,title13,title14,title15,title16,mTitle;
    public static View.OnClickListener myOnClickListener;
    Context c;
    RecruitmentArchiveAdapter mRecruitmentArchiveAdapter;
    int start = 0;
    int side;

//    @Override
//    public void onCreate(Bundle state) {
//        super.onCreate(state);
//        Bundle args = getArguments();
//        side = args.getInt("NAME");
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recruitment_archive_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        myOnClickListener = new MyOnClickListener(getActivity());
        InitIdView(rootView);
        return rootView;


//    @Override
//    public void onClick(View view) {
//        int buttonId = view.getId();
//        switch (buttonId){
//
//            case R.id.error_ripple:
//                InitGetData(false);
//                break;
//            case R.id.empty_ripple:
//                InitGetData(false);
//                break;
//        }
//
//    }
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
//        title3 = ((TextView) rootView.findViewById(R.id.title3));
        title4 = ((TextView) rootView.findViewById(R.id.title4));
        title5 = ((TextView) rootView.findViewById(R.id.title5));
        title6 = ((TextView) rootView.findViewById(R.id.title6));
        title7 = ((TextView) rootView.findViewById(R.id.title7));
        title8 = ((TextView) rootView.findViewById(R.id.title8));
        title9 = ((TextView) rootView.findViewById(R.id.title9));
        title10 = ((TextView) rootView.findViewById(R.id.title10));
        title11 = ((TextView) rootView.findViewById(R.id.title11));
        title12 = ((TextView) rootView.findViewById(R.id.title12));
        title13 = ((TextView) rootView.findViewById(R.id.title13));
        title14 = ((TextView) rootView.findViewById(R.id.title14));
        title15 = ((TextView) rootView.findViewById(R.id.title15));
        title16 = ((TextView) rootView.findViewById(R.id.title16));
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        title1.setVisibility(View.GONE);
        title2.setVisibility(View.GONE);
        title4.setVisibility(View.GONE);
        title5.setVisibility(View.GONE);
        title6.setVisibility(View.GONE);
        title7.setVisibility(View.GONE);
        title8.setVisibility(View.GONE);
        title9.setVisibility(View.GONE);
        title10.setVisibility(View.GONE);
        title11.setVisibility(View.GONE);
        title12.setText("Candidate ID");
        title13.setText("Candidate Name");
        title14.setText("Applied For");
        title15.setText("Phone");
        title16.setText("E-Mail");

        mrecyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(linearLayoutManager);

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));

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

        mTitle.setText("Rejected Candidate Archive");
        data = new ArrayList<>();
        InitGetData(false);
    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadRejectedCandidateArchiveInitiate mLoadRejectedCandidateArchiveInitiate= new LoadRejectedCandidateArchiveInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadRejectedCandidateArchiveInitiate.execute((Void) null);
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
    public class  LoadRejectedCandidateArchiveInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadRejectedCandidateArchiveInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = web.dorejectcandidatearchive(mId, mAuthorization,mStart);;;

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
                        JSONArray feedArray = jsonObj.getJSONArray("reject_archive_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ArchiveListItem item = new ArchiveListItem();
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
                                    item.setcandidate_email((feedObj.getString("candidate_email")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("candidate_phone").trim().equals("")) {
                                    item.setcandidate_phone("None");
                                } else {
                                    item.setcandidate_phone((feedObj.getString("candidate_phone")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("candidate_job").trim().equals("")) {
                                    item.setcandidate_job("None");
                                } else {
                                    item.setcandidate_job(StringUtils.capitalize(feedObj.getString("candidate_job")
                                            .toLowerCase().trim()));
                                }

                                data.add(item);
                            }
                        }
                        start = data.size();
                        mRecruitmentArchiveAdapter = new RecruitmentArchiveAdapter(getActivity(), data,"rejectarchive");
                        mrecyclerview.setAdapter(mRecruitmentArchiveAdapter);

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


        }}


}
