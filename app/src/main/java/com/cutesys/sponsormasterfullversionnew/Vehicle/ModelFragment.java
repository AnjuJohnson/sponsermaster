package com.cutesys.sponsormasterfullversionnew.Vehicle;

import android.content.Intent;
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

import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.ADDModule.AddManufacturerActivity;
import com.cutesys.sponsormasterfullversionnew.ADDModule.AddModelActivity;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.ListHeadingTwoAdapter;
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
 * Created by Kris on 3/18/2017.
 */

public class ModelFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private RecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle, msubtext, msubtitle;

    private ListHeadingTwoAdapter mListHeadingTwoAdapter;

    ArrayList<ListItem> dataItem;
    private FloatingActionButton mAdd;
    int start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listviewheadingtwo_fragment, container, false);
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
        msubtext = ((TextView) rootView.findViewById(R.id.msubtext));
        msubtitle = ((TextView) rootView.findViewById(R.id.msubtitle));
        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        mAdd = ((FloatingActionButton) rootView.findViewById(R.id.mAdd));
        mAdd.setOnClickListener(this);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataItem = new ArrayList<>();
        mTitle.setText("Model List");
        msubtext.setText("Model");
        msubtitle.setText("Manufacturer");
        InitGetData(false);
    }
    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadModelInitiate mLoadModelInitiate = new LoadModelInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadModelInitiate.execute((Void) null);
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
            case R.id.mAdd:
                Intent intent = new Intent(getActivity(),AddModelActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                getActivity().finish();
                break;
        }
    }
    public class LoadModelInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadModelInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = httpOperations.doModelList(mId, mAuthorization,mStart);
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
                        JSONArray feedArray = jsonObj.getJSONArray("model_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("model_name").equals(""))
                                    && (!feedObj.getString("model_name").equals("null"))) {

                                item.set_id(feedObj.getString("model_id"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("model_name")
                                        .toLowerCase().trim()));
                                item.set_manufacture(StringUtils.capitalize(feedObj.getString("manufacturer_name")
                                        .toLowerCase().trim()));
                                dataItem.add(item);
                            }
                        }
                        start = dataItem.size();
                        mListHeadingTwoAdapter = new ListHeadingTwoAdapter(getActivity(), dataItem);
                        mrecyclerview.setAdapter(mListHeadingTwoAdapter);

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