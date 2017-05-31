package com.cutesys.sponsormasterfullversionnew.Employee;

/**
 * Created by user on 3/28/2017.
 */
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

import com.cutesys.sponsermasterlibrary.RippleView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.LoanAdapter;
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
 * Created by Owner on 17/03/2017.
 */

public class EmployeeLoanFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry, mTitle, msubtext;

    private RecyclerView emptravelled;
    ArrayList<ListItem> dataItem;
    LoanAdapter mEmpTravelledAdapter;
    int start = 0;
    String status;
   // CustomTextBold txt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.loan_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {


        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.loan_recycler))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        emptravelled = (RecyclerView) rootView.findViewById(R.id.loan_recycler);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        initRecyclerView();

        dataItem = new ArrayList<>();
        InitGetData(false);
    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            EmployeeLoanFragment.LoadEmployeeLoanDetails mLoadEmployeeLoanDetails = new EmployeeLoanFragment.LoadEmployeeLoanDetails(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
            mLoadEmployeeLoanDetails.execute((Void) null);
        } else {
            switcher.showErrorView();
        }
    }

    private void initRecyclerView() {
        emptravelled.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        emptravelled.setLayoutManager(linearLayoutManager);
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

    public class LoadEmployeeLoanDetails extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadEmployeeLoanDetails(String id, String authorization, String Start) {
            mId = id;
            mAuthorization = authorization;
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
            StringBuilder result = httpOperations.doLoanList(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("employee_loan_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.set_id(feedObj.getString("employee_id"));
                            item.set_name(feedObj.getString("employee_name"));

                            if ((!feedObj.getString("employee_name").equals(""))
                                    && (!feedObj.getString("employee_name").equals("null"))) {

                                /*item.set_id(feedObj.getString("company_id"));*/
                                item.set_name(StringUtils.capitalize(feedObj.getString("employee_name")
                                        .toLowerCase().trim()));
                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.set_code("None");
                                } else {
                                    item.set_code(feedObj.getString("company_name"));
                                }
                           /* item.setCompany_name(feedObj.getString("company_name"));*/
                                if (feedObj.getString("employee_salary").trim().equals("")) {
                                    item.set_advance_fee("None");
                                } else {
                                    item.set_advance_fee(feedObj.getString("employee_salary"));
                                }
                                if (feedObj.getString("employee_employment_id").trim().equals("")) {
                                    item.set_employee_employment_id("None");
                                } else {
                                    item.set_employee_employment_id(feedObj.getString("employee_employment_id"));
                                }
                                if (feedObj.getString("type").trim().equals("")) {
                                    item.set_balance_fee("None");
                                } else {
                                    item.set_balance_fee(feedObj.getString("type"));
                                }
                                if (feedObj.getString("requested_loan_amount").trim().equals("")) {
                                    item.set_sponsor_fee("None");
                                } else {
                                    item.set_sponsor_fee(feedObj.getString("requested_loan_amount"));
                                }
                                if (feedObj.getString("apply_date").trim().equals("")) {
                                    item.set_bank_fee("None");
                                } else {
                                    item.set_bank_fee(feedObj.getString("apply_date"));
                                }
                                if (feedObj.getString("approved_loan_amount").trim().equals("")) {
                                    item.set_manufacture("None");
                                } else {
                                    item.set_manufacture(feedObj.getString("approved_loan_amount"));
                                }
                                if (feedObj.getString("approval_date").trim().equals("")) {
                                    item.set_email("None");
                                } else {
                                    item.set_email(feedObj.getString("approval_date"));
                                }
                                if (feedObj.getString("payment_date").trim().equals("")) {
                                    item.set_phone("None");
                                } else {
                                    item.set_phone(feedObj.getString("payment_date"));
                                }
                                if (feedObj.getString("loan_id").trim().equals("")) {
                                    item.set_model("None");
                                } else {
                                    item.set_model(feedObj.getString("loan_id"));
                                }
                                if (feedObj.getString("employee_designation").trim().equals("")) {
                                    item.set_company_fee("None");
                                } else {
                                    item.set_company_fee(feedObj.getString("employee_designation"));
                                }
                                if (feedObj.getString("status").trim().equals("")) {
                                    item.set_sponsor_fee("None");
                                } else {
                                    item.set_sponsor_fee(feedObj.getString("status"));
                                }
                           /* item.setCompany_model(feedObj.getString("employee_salary"));*/
                           /* item.setcompany_code(feedObj.getString("type"));*/
                            /*item.setVehicle_status(feedObj.getString("requested_loan_amount"));*/
                           /* item.setEmployee_company(feedObj.getString("apply_date"));*/
                           /* item.setCompany_manufacture(feedObj.getString("approved_loan_amount"));*/
                           /* item.setEmployee_mail(feedObj.getString("approval_date"));*/
                           /* item.setCompany_phone(feedObj.getString("payment_date"));*/
                           /* item.setVehicle_id(feedObj.getString("loan_id"));*/
                           /* item.setEmployee_designation(feedObj.getString("employee_designation"));*/
                           /* item.setVehicle_status(feedObj.getString("status"));*/
                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mEmpTravelledAdapter = new LoanAdapter(getActivity(),getActivity(),dataItem);
                        emptravelled.setAdapter(mEmpTravelledAdapter);

                    }else {
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
