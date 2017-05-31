package com.cutesys.sponsormasterfullversionnew.Payroll;

/**
 * Created by Owner on 15/03/2017.
 */

import android.app.ProgressDialog;
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
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.manageholidayadapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.manageholidaylist;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Owner on 14/03/2017.
 */

public class HolidayFragment  extends Fragment implements View.OnClickListener {
    private SharedPreferences sPreferences;
    private SharedPreferences.Editor prefEditor;
    RecyclerView cmpy_recycler6;
    Context c;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry, mTitle;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    ArrayList<manageholidaylist> dataItem;


    manageholidayadapter mAdapter;
    private ProgressDialog dialog;
    int start = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.advancepaid_list1, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        myOnClickListener = new MyOnClickListener(getActivity());


        cmpy_recycler6 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler6);
        cmpy_recycler6.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler6.setLayoutManager(linearLayoutManager);


        dataItem = new ArrayList<manageholidaylist>();
        mAdapter = new manageholidayadapter(dataItem);
        cmpy_recycler6.setAdapter(mAdapter);


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
                .addContentView(rootView.findViewById(R.id.cmpy_recycler6))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();
        cmpy_recycler6 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler6);
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
            LoadHolidayInitiate mLoadHolidayInitiate = new LoadHolidayInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadHolidayInitiate.execute((Void) null);
        } else {
            switcher.showErrorView();
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



    public class LoadHolidayInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadHolidayInitiate(String id, String authorization, boolean status, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStatus = status;
            mStart = Start;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations web = new HttpOperations(getActivity());

            StringBuilder result = web.doholidayList(mId, mAuthorization, mStart);
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

                        JSONArray feedArray = jsonObj.getJSONArray("holiday_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                           manageholidaylist item = new manageholidaylist();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("holiday").equals(""))
                                    && (!feedObj.getString("holiday").equals("null"))) {
                                if (feedObj.getString("id").trim().equals("")) {
                                    item.setid("None");
                                } else {
                                    item.setid(StringUtils.capitalize(feedObj.getString("id")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("holiday").trim().equals("")) {
                                    item.setholiday("None");
                                } else {
                                    item.setholiday(StringUtils.capitalize(feedObj.getString("holiday")
                                            .toLowerCase().trim()));
                                }


                                dataItem.add(item);
                            }
                           /* Log.d("11111111111",feedObj.getString("company_name"));*/
                        }

                        start = dataItem.size();
                        mAdapter = new manageholidayadapter(getActivity(), dataItem, "cpy");
                        cmpy_recycler6.setAdapter(mAdapter);
                        System.out.println(result);

                    } else {
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