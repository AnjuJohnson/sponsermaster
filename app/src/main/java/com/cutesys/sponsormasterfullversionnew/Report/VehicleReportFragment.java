package com.cutesys.sponsormasterfullversionnew.Report;

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
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.CompanyReportAdapter;
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
 * Created by Kris on 2/27/2017.
 */

public class VehicleReportFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private static final String[] topTitles = new String[]
            {"ID", "Company", "Assigned Company",
                    "Assigned Employee"};

    private Switcher switcher;
    private RecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle,
            title1,title2,title3, title4, title5, title6;

    private CompanyReportAdapter mCompanyReportAdapter;

    ArrayList<ListItem> dataItem;
    int start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reportfragment, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.ScrollView))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        title1 = ((TextView) rootView.findViewById(R.id.title1));
        title2 = ((TextView) rootView.findViewById(R.id.title2));
        title3 = ((TextView) rootView.findViewById(R.id.title3));
        title4 = ((TextView) rootView.findViewById(R.id.title4));
        title5 = ((TextView) rootView.findViewById(R.id.title5));
        title6 = ((TextView) rootView.findViewById(R.id.title6));
        title2.setText("Vehicle ID");
        title4.setText("Company");
        title5.setText("Assigned Company");
        title6.setText("Assigned Employee");
        title1.setVisibility(View.GONE);
        title3.setVisibility(View.GONE);

        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mrecyclerview = (RecyclerView) rootView.findViewById(R.id.mrecyclerview);

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataItem = new ArrayList<>();
        mTitle.setText("Vehicle Report");
        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadVehReportInitiate mLoadVehReportInitiate = new LoadVehReportInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadVehReportInitiate.execute((Void) null);
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

    public class LoadVehReportInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadVehReportInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = httpOperations.doVehicleReport(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("vehicle_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("vehicle_company").equals(""))
                                    && (!feedObj.getString("vehicle_company").equals("null"))) {


                                item.set_id(feedObj.getString("vehicle_code"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("vehicle_company")
                                        .toLowerCase().trim()));

                               if(feedObj.getString("vehicle_assigned_company").trim().equals("")){
                                    item.set_designation("None");
                                }else {
                                    item.set_designation(feedObj.getString("vehicle_assigned_company"));
                                }
                                if(feedObj.getString("vehicle_assigned_employee").trim().equals("")){
                                    item.set_email("None");
                                }else {
                                    item.set_email(feedObj.getString("vehicle_assigned_employee"));
                                }


                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mCompanyReportAdapter = new CompanyReportAdapter(getActivity(), dataItem,
                                "vehicle");
                        mrecyclerview.setAdapter(mCompanyReportAdapter);
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