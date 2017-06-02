package com.cutesys.sponsormasterfullversionnew.Payroll;

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
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.AdavanceSalaryAdapter;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.advancepaidadapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.advancepaidlist;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by anju on 1/06/2017.
 */

public class AdvanceSalaryFragment extends Fragment implements View.OnClickListener {
private SharedPreferences sPreferences;
private SharedPreferences.Editor prefEditor;
        RecyclerView cmpy_recycler6;
        Context c;
private Switcher switcher;
private TextView error_label_retry, empty_label_retry, mTitle;

private RecyclerView.LayoutManager layoutManager;
public static View.OnClickListener myOnClickListener;
        ArrayList<advancepaidlist> dataItem;


    AdavanceSalaryAdapter mAdapter;

        int start = 0;


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.advancepaid_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        myOnClickListener = new MyOnClickListener(getActivity());


        cmpy_recycler6 = (RecyclerView) rootView.findViewById(R.id.cmpy_recycler6);
        cmpy_recycler6.setHasFixedSize(true);
        mTitle=(TextView)rootView.findViewById(R.id.mTitle);
        mTitle.setText("Advance Salary List");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler6.setLayoutManager(linearLayoutManager);


        dataItem = new ArrayList<advancepaidlist>();
        mAdapter = new AdavanceSalaryAdapter(dataItem);
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
           AdvanceSalaryFragment.LoadAdvanceSalaryInitiate mLoadAdvanceSalaryInitiate = new AdvanceSalaryFragment.LoadAdvanceSalaryInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
           mLoadAdvanceSalaryInitiate.execute((Void) null);
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


        }
    }


public class LoadAdvanceSalaryInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

    private String mId, mAuthorization, mStart;
    private boolean mStatus;

    LoadAdvanceSalaryInitiate(String id, String authorization, boolean status, String Start) {
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

        StringBuilder result = web.doadvancesalaryList(mId, mAuthorization, mStart);
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

                    JSONArray feedArray = jsonObj.getJSONArray("advance_list");
                    for (int i = 0; i < feedArray.length(); i++) {

                        advancepaidlist item = new advancepaidlist();
                        JSONObject feedObj = (JSONObject) feedArray.get(i);
                        if ((!feedObj.getString("employee_name").equals(""))
                                && (!feedObj.getString("employee_name").equals("null"))) {
                            if (feedObj.getString("employee_id").trim().equals("")) {
                                item.setemployee_id("None");
                            } else {
                                item.setemployee_id(StringUtils.capitalize(feedObj.getString("employee_id")
                                        .toLowerCase().trim()));
                            }

                            if (feedObj.getString("employee_employment_id").trim().equals("")) {
                                item.setemployee_employment_id("None");
                            } else {
                                item.setemployee_employment_id(StringUtils.capitalize(feedObj.getString("employee_employment_id")
                                        .toLowerCase().trim()));
                            }



                            if (feedObj.getString("employee_name").trim().equals("")) {
                                item.setemployee_name("None");
                            } else {
                                item.setemployee_name(StringUtils.capitalize(feedObj.getString("employee_name")
                                        .toLowerCase().trim()));
                            }

                            if (feedObj.getString("employee_salary").trim().equals("")) {
                                item.setemployee_salary("None");
                            } else {
                                item.setemployee_salary(StringUtils.capitalize(feedObj.getString("employee_salary")
                                        .toLowerCase().trim()));
                            }
                            if (feedObj.getString("advance_amount").trim().equals("")) {
                                item.setapproved_loan_amount("None");
                            } else {
                                item.setapproved_loan_amount(StringUtils.capitalize(feedObj.getString("advance_amount")
                                        .toLowerCase().trim()));
                            }
                                if (feedObj.getString("advance_requested_date").trim().equals("")) {
                                    item.settype("None");
                                } else {
                                    item.settype(StringUtils.capitalize(feedObj.getString("advance_requested_date")
                                            .toLowerCase().trim()));
                                }
                            if (feedObj.getString("advance_status").trim().equals("")) {
                                item.setrepay_status("None");
                            } else {
                                item.setrepay_status(StringUtils.capitalize(feedObj.getString("advance_status")
                                        .toLowerCase().trim()));
                            }

                            dataItem.add(item);

                        }
                    }

                    start = dataItem.size();
                    mAdapter = new AdavanceSalaryAdapter(getActivity(), dataItem);
                    cmpy_recycler6.setAdapter(mAdapter);
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
