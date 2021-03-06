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
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.ArchiveAdapter;
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
 * Created by user on 3/15/2017.
 */

public class CompanyArchiveFragment extends Fragment {


    private SharedPreferences sPreferences;
//    private static RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
private static final String[] topTitles = new String[]
        {"ID", "Company Name", "Address",
                "E-Mail"};

    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;
    private static RecyclerView mrecyclerview;
    LinearLayout swipeview;
    private static ArrayList<ArchiveListItem> data;
    public static View.OnClickListener myOnClickListener;
    Context c;
    ArchiveAdapter mArchiveAdapter;
    private TextView title1,title2,title4, title5 ,title6,title7,title8,title9,title10,title11,title12,title13,title14,mTitle;
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
        View rootView = inflater.inflate(R.layout.archive_fragment, container, false);
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
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        title1.setText("ID");
        title2.setText("Company Name");
        title4.setText("Address");
        title5.setText("E-Mail");
        title6.setVisibility(View.GONE);
        title7.setVisibility(View.GONE);
        title8.setVisibility(View.GONE);
        title9.setVisibility(View.GONE);
        title10.setVisibility(View.GONE);
        title11.setVisibility(View.GONE);
        title12.setVisibility(View.GONE);
        title13.setVisibility(View.GONE);
        title14.setVisibility(View.GONE);


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
        mTitle.setText("Company Archive");
        data = new ArrayList<>();
        InitGetData(false);
    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadCompanyArchiveInitiate mLoadCompanyArchiveInitiate= new  LoadCompanyArchiveInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadCompanyArchiveInitiate.execute((Void) null);
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
    public class  LoadCompanyArchiveInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadCompanyArchiveInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = web.docompanyarchive(mId, mAuthorization,mStart);;;

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
                        JSONArray feedArray = jsonObj.getJSONArray("company_archive_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ArchiveListItem item = new ArchiveListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("company_name").equals(""))
                                    && (!feedObj.getString("company_name").equals("null"))) {
                                if (feedObj.getString("company_id").trim().equals("")) {
                                    item.setcompany_id("None");
                                } else {
                                    item.setcompany_id((feedObj.getString("company_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("company_pid").trim().equals("")) {
                                    item.setcompany_pid("None");
                                } else {
                                    item.setcompany_pid((feedObj.getString("company_pid")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.setcompany_name("None");
                                } else {
                                    item.setcompany_name(StringUtils.capitalize(feedObj.getString("company_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("company_address1").trim().equals("")) {
                                    item.setcompany_address1("None");
                                } else {
                                    item.setcompany_address1(StringUtils.capitalize(feedObj.getString("company_address1")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("company_email").trim().equals("")) {
                                    item.setcompany_email("None");
                                } else {
                                    item.setcompany_email((feedObj.getString("company_email")
                                            .toLowerCase().trim()));
                                }

                                data.add(item);
                            }
                        }
                        start = data.size();
                        mArchiveAdapter = new ArchiveAdapter(getActivity(), data,"companyarchive");
                        mrecyclerview.setAdapter(mArchiveAdapter);

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
