package com.cutesys.sponsormasterfullversionnew.Company;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.ADDModule.add_camp;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.CampListAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.CampListItem;
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

public class CampListFragment  extends Fragment implements View.OnClickListener {
    private SharedPreferences sPreferences;
    private SharedPreferences.Editor prefEditor;
    RecyclerView cmpy_recycler12;
    Context c;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry, mTitle;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    ArrayList<CampListItem> dataItem;


    CampListAdapter mAdapter;
    private ProgressDialog dialog;
    int start = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camp_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        myOnClickListener = new MyOnClickListener(getActivity());


        cmpy_recycler12 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler12);
        cmpy_recycler12.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler12.setLayoutManager(linearLayoutManager);


        dataItem = new ArrayList<CampListItem>();
        mAdapter = new CampListAdapter(dataItem);
        cmpy_recycler12.setAdapter(mAdapter);


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

    private void InitIdView(View rootView) {
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.cmpy_recycler12))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();
        FloatingActionButton add = (FloatingActionButton) rootView.findViewById(R.id.fabbank);
        add.setOnClickListener(this);
        cmpy_recycler12= (RecyclerView) rootView.findViewById(R.id.cmpy_recycler12);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        dataItem = new ArrayList<>();
        InitGetData(false);

    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            LoadCampListInitiate mLoadCampListInitiate = new LoadCampListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadCampListInitiate.execute((Void) null);
        } else {
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

            case R.id.fabbank:

                Intent intent = new Intent(getActivity(),add_camp.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                getActivity().finish();

                break;




        }
    }

    public class LoadCampListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadCampListInitiate(String id, String authorization, boolean status, String Start) {
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

            StringBuilder result = web.docampList(mId, mAuthorization, mStart);
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

                        JSONArray feedArray = jsonObj.getJSONArray("camp_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            CampListItem item = new CampListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("company_name").equals(""))
                                    && (!feedObj.getString("company_name").equals("null"))) {

                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.setcompany_name("None");
                                } else {
                                    item.setcompany_name(StringUtils.capitalize(feedObj.getString("company_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_id").trim().equals("")) {
                                    item.setcamp_id("None");
                                } else {
                                    item.setcamp_id((feedObj.getString("camp_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("company_id").trim().equals("")) {
                                    item.setcompany_id("None");
                                } else {
                                    item.setcompany_id((feedObj.getString("company_id")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("camp_location").trim().equals("")) {
                                    item.setcamp_location("None");
                                } else {
                                    item.setcamp_location(StringUtils.capitalize(feedObj.getString("camp_location")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_address").trim().equals("")) {
                                    item.setcamp_address("None");
                                } else {
                                    item.setcamp_address(StringUtils.capitalize(feedObj.getString("camp_address")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_manager").trim().equals("")) {
                                    item.setcamp_manager("None");
                                } else {
                                    item.setcamp_manager(StringUtils.capitalize(feedObj.getString("camp_manager")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("camp_contact").trim().equals("")) {
                                    item.setcamp_contact("None");
                                } else {
                                    item.setcamp_contact(StringUtils.capitalize(feedObj.getString("camp_contact")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_email").trim().equals("")) {
                                    item.setcamp_email("None");
                                } else {
                                    item.setcamp_email(StringUtils.capitalize(feedObj.getString("camp_email")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_intake").trim().equals("")) {
                                    item.setcamp_intake("None");
                                } else {
                                    item.setcamp_intake(StringUtils.capitalize(feedObj.getString("camp_intake")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_name").trim().equals("")) {
                                    item.setcamp_name("None");
                                } else {
                                    item.setcamp_name(StringUtils.capitalize(feedObj.getString("camp_name")
                                            .toLowerCase().trim()));
                                }


                                dataItem.add(item);
                            }
                            /*Log.d("11111111111", feedObj.getString("bank_name"));*/
                        }

                        start = dataItem.size();
                        mAdapter = new CampListAdapter(getActivity(), dataItem, "cpy");
                        cmpy_recycler12.setAdapter(mAdapter);
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