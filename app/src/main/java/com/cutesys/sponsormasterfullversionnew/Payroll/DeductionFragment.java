package com.cutesys.sponsormasterfullversionnew.Payroll;

/**
 * Created by Owner on 15/03/2017.
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.deductioncategoryadapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.deductioncategorytlist;
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

public class DeductionFragment extends Fragment implements View.OnClickListener {
    private SharedPreferences sPreferences;
    private SharedPreferences.Editor prefEditor;
    RecyclerView cmpy_recycler1;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;
    Context c;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    ArrayList<deductioncategorytlist> dataItem;
  TextView  mTitle, msubtext;
    private FloatingActionButton mAdd;


    deductioncategoryadapter mAdapter;
    private ProgressDialog dialog;
    int start = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_card, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        myOnClickListener = new MyOnClickListener(getActivity());


        cmpy_recycler1 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler1);
        cmpy_recycler1.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler1.setLayoutManager(linearLayoutManager);


        dataItem = new ArrayList<deductioncategorytlist>();
        mAdapter = new deductioncategoryadapter(dataItem);
        cmpy_recycler1.setAdapter(mAdapter);


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
                .addContentView(rootView.findViewById(R.id.cmpy_recycler1))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();
       /* FloatingActionButton  add = (FloatingActionButton) rootView.findViewById(R.id.fabbank);
        add.setOnClickListener(this);*/
        msubtext = ((TextView) rootView.findViewById(R.id.msubtext));
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mAdd = ((FloatingActionButton) rootView.findViewById(R.id.mAdd));

        cmpy_recycler1 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler1);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mAdd.setOnClickListener(this);




        dataItem = new ArrayList<>();
        mTitle.setText("Deduction Category List");
        msubtext.setText("Deduction Category");
        InitGetData(false);

    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            LoadDeductionInitiate mLoadDeductionInitiate = new LoadDeductionInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadDeductionInitiate.execute((Void) null);
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

            case R.id.mAdd:
                Intent intent = new Intent(getActivity(),adddeductioncategory.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                getActivity().finish();

                break;


        }
    }



    public class LoadDeductionInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadDeductionInitiate(String id, String authorization, boolean status, String Start) {
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

            StringBuilder result = web.dodeductioncategoryList(mId, mAuthorization, mStart);
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


                        JSONArray feedArray = jsonObj.getJSONArray("deduction_category_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                          deductioncategorytlist item = new deductioncategorytlist();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("deduction_category").equals(""))
                                    && (!feedObj.getString("deduction_category").equals("null"))) {
                                if (feedObj.getString("deduction_category_id").trim().equals("")) {
                                    item.setdeduction_category_id("None");
                                } else {
                                    item.setdeduction_category_id(StringUtils.capitalize(feedObj.getString("deduction_category_id")
                                            .toLowerCase().trim()));
                                }


                                if (feedObj.getString("deduction_category").trim().equals("")) {
                                    item.setdeduction_category("None");
                                } else {
                                    item.setdeduction_category(StringUtils.capitalize(feedObj.getString("deduction_category")
                                            .toLowerCase().trim()));
                                }



                                dataItem.add(item);
                            }
                           /* Log.d("11111111111",feedObj.getString("company_name"));*/
                        }

                        start = dataItem.size();
                        mAdapter = new deductioncategoryadapter(getActivity(), dataItem, "cpy");
                        cmpy_recycler1.setAdapter(mAdapter);
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