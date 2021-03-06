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

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.DocumentStatusAdapter;
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
 * Created by Kris on 3/3/2017.
 */

public class VehicleStatusFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private RecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle;

    private DocumentStatusAdapter mDocumentStatusAdapter;

    ArrayList<ListItem> dataItem;
    int start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.documents_fragment, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){

        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.mrecyclerview))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

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
            LoadVehicleStatusInitiate mLoadVehicleStatusInitiate = new LoadVehicleStatusInitiate
                    (sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadVehicleStatusInitiate.execute((Void) null);
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

    public class LoadVehicleStatusInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadVehicleStatusInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = httpOperations.doVEHICLE_DOC_STATUS_LIST(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("vehicle_doc_status_list");
                        try {
                            if (feedArray.length() > 0) {
                                for (int i = 0; i < feedArray.length(); i++) {

                                    ListItem item = new ListItem();
                                    JSONObject feedObj = (JSONObject) feedArray.get(i);
                                    if((!feedObj.getString("vehicle_insurance_company").equals(""))
                                            && (!feedObj.getString("vehicle_insurance_company").equals("null"))) {

                                        item.set_id(feedObj.getString("vehicleId"));
                                        item.set_name(StringUtils.capitalize(feedObj.getString("vehicle_insurance_company")
                                                .toLowerCase().trim()));

                                        JSONObject statusObj = new JSONObject(feedObj.getString("Document_Status"));

                                        item.set_address(statusObj.getString("Insurance_Documents")
                                                +","+statusObj.getString("Registration_Istamara_Documents"));
                                        item.set_code(statusObj.getString("Owners_Qatar_Id")
                                                +","+statusObj.getString("Vehicle_Picture"));
                                        item.set_model(statusObj.getString("vehicle_document_images")
                                                +","+statusObj.getString("vehicle_document_owner_qatar_id"));
                                        item.set_manufacture(statusObj.getString("vehicle_document_registration"));
                                        item.set_email(statusObj.getString("vehicle_document_insurance"));

                                        dataItem.add(item);
                                    }
                                }
                                start = dataItem.size();
                                mDocumentStatusAdapter = new DocumentStatusAdapter(getActivity(),
                                        getActivity(), dataItem, "vehicle");
                                mrecyclerview.setAdapter(mDocumentStatusAdapter);
                            } else {
                                switcher.showEmptyView();
                            }
                        } catch (Exception e){
                            switcher.showEmptyView();
                        }
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
