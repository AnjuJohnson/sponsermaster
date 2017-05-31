package com.cutesys.sponsormasterfullversionnew.Visa;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Badge;
import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScrollRecyclerView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SearchAdapter;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.VisaListAdapter;
import com.cutesys.sponsormasterfullversionnew.Company.CompanyListFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kris on 2/27/2017.
 */

public class VisaNewChildFragment extends Fragment implements View.OnClickListener{

    private SqliteHelper helper;
    private SharedPreferences sPreferences;

    private Badge mBadge;
    private Switcher switcher;
    private FastScrollRecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle;

    private VisaListAdapter mVisaListAdapter;

    List<HashMap<String, String>> Data_Item ;
    ArrayList<ListItem> dataItem;
    int start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vehiclevisapay_fragment, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        helper = new SqliteHelper(getActivity(), "SponserMaster", null, 1);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){
        Data_Item = helper.gethomedetails();
        mBadge = (Badge)rootView.findViewById(R.id.badge);
        mBadge.setText(Data_Item.get(0).get("home_visa_notification"));

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
        mTitle.setText("Visa List");
        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadVisaNewInitiate mLoadVisaNewInitiate = new LoadVisaNewInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadVisaNewInitiate.execute((Void) null);
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
    public class LoadVisaNewInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadVisaNewInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = httpOperations.doVisaList(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("visa_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("visa_applicant_name").equals(""))
                                    && (!feedObj.getString("visa_applicant_name").equals("null"))) {

                                item.set_id(feedObj.getString("visa_id"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("visa_applicant_name")
                                        .toLowerCase().trim()));

                                if(feedObj.getString("visa_category").trim().equals("")){
                                    item.set_code("None");
                                }else {
                                    item.set_code(StringUtils.capitalize(feedObj.getString("visa_category")));
                                }
                                if (feedObj.getString("visa_type").trim().equals("")) {
                                    item.set_address("None");
                                } else {
                                    item.set_address(StringUtils.capitalize(feedObj.getString("visa_type")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_status").trim().equals("")) {
                                    item.set_email("None");
                                } else {
                                    item.set_email(StringUtils.capitalize(feedObj.getString("visa_status")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_client_name").trim().equals("")) {
                                    item.set_manufacture("None");
                                } else {
                                    item.set_manufacture(StringUtils.capitalize(feedObj.getString("visa_client_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_ref_no").trim().equals("")) {
                                    item.set_refno("None");
                                } else {
                                    item.set_refno(StringUtils.capitalize(feedObj.getString("visa_ref_no")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("visa_profile_picture").equals("")) {
                                    item.setImage("false");
                                } else {
                                    item.setImage(feedObj.getString("visa_profile_picture").trim());
                                }
                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mVisaListAdapter = new VisaListAdapter(getActivity(), dataItem,"new");
                        mrecyclerview.setAdapter(mVisaListAdapter);
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