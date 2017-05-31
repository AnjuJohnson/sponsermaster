package com.cutesys.sponsormasterfullversionnew.Vehicle;

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

import com.cutesys.sponsermasterlibrary.Badge;
import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScrollRecyclerView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.VehicleListAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
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

public class VehicleListFragment extends Fragment implements View.OnClickListener{

    private SqliteHelper helper;
    private SharedPreferences sPreferences;

    private Badge mBadge;
    private Switcher switcher;
    private FastScrollRecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle;

    private VehicleListAdapter mVehicleListAdapter;

    List<HashMap<String, String>> Data_Item ;
    ArrayList<ListItem> dataItem;
    int start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vehiclevisapay_fragment, container, false);
        helper = new SqliteHelper(getActivity(), "SponserMaster", null, 1);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){

        Data_Item = helper.gethomedetails();
        mBadge = (Badge)rootView.findViewById(R.id.badge);
        mBadge.setText(Data_Item.get(0).get("home_vehicle_notification"));

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
        mTitle.setText("Vehicle");
        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadVehicleListInitiate mLoadVehicleListInitiate = new LoadVehicleListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadVehicleListInitiate.execute((Void) null);
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
    public class LoadVehicleListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadVehicleListInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = httpOperations.doVehicleList(mId, mAuthorization,mStart);
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

                            if((!feedObj.getString("company_name").equals(""))
                                    && (!feedObj.getString("company_name").equals("null"))) {

                                item.set_id(feedObj.getString("vehicle_id"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("company_name")
                                        .toLowerCase().trim()));

                                String address = "";

                                if(feedObj.getString("manufacturer").trim().equals("")){
                                    address = "";
                                }else {
                                    address = feedObj.getString("manufacturer");
                                }

                                if(feedObj.getString("model").trim().equals("")){
                                    address = address.toString()+"\t"+"";
                                }else {
                                    address = address.toString()+"\t"+feedObj.getString("model");
                                }
                                if(address.equals("")){
                                    item.set_address("None");
                                } else {
                                    item.set_address(address.toString());
                                }

                                if(feedObj.getString("assigned_company").trim().equals("")){
                                    item.set_email("None");
                                }else {
                                    item.set_email(feedObj.getString("assigned_company"));
                                }
                                if(feedObj.getString("assigned_employee").trim().equals("")){
                                    item.set_phone("None");
                                }else {
                                    item.set_phone(feedObj.getString("assigned_employee"));
                                }
                                if(feedObj.getString("vehicle_status").trim().equals("")){
                                    item.set_status("None");
                                }else {
                                    item.set_status(feedObj.getString("vehicle_status"));
                                }
                                if(feedObj.getString("vehicle_purchase_date").trim().equals("")){
                                    item.setpurchase_date("None");
                                }else {
                                    item.setpurchase_date(feedObj.getString("vehicle_purchase_date"));
                                }
                                if(feedObj.getString("vehicle_purchase_price").trim().equals("")){
                                    item.setpurchase_price("None");
                                }else {
                                    item.setpurchase_price(feedObj.getString("vehicle_purchase_price"));
                                }
                                if (feedObj.getString("vehicle_picture").equals("")) {
                                    item.setImage("false");
                                } else {
                                    item.setImage(feedObj.getString("vehicle_picture").trim());
                                }
                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mVehicleListAdapter = new VehicleListAdapter(getActivity(), dataItem,
                                "vehicle");
                        mrecyclerview.setAdapter(mVehicleListAdapter);

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
