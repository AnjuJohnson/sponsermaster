package com.cutesys.sponsormasterfullversionnew.Subclasses;

/**
 * Created by user on 4/3/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScrollRecyclerView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;

import com.cutesys.sponsormasterfullversionnew.Adapterclasses.CpyVehicleAdapter;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.HomeEmpListAdapter;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.VehicleListAdapter;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
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
 * Created by Owner on 3/04/2017.
 */

public class FlipChildActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private FastScrollRecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle;
    private ImageView mBackpress;

    Intent intent;
    ArrayList<ListItem> dataItem;
    int start = 0;
    private String mAction, mID, mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flip_child_activity);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);

        InitIdView();
    }

    private void InitIdView(){

        intent = getIntent();
        mAction = intent.getExtras().getString("ACTION");
        mData = intent.getExtras().getString("DATA");
        mID = intent.getExtras().getString("ID");

        switcher = new Switcher.Builder(getApplicationContext())
                .addContentView(findViewById(R.id.mrecyclerview))
                .addErrorView(findViewById(R.id.error_view))
                .addProgressView(findViewById(R.id.progress_view))
                .setErrorLabel((TextView) findViewById(R.id.error_label))
                .setEmptyLabel((TextView) findViewById(R.id.empty_label))
                .addEmptyView(findViewById(R.id.empty_view))
                .build();

        mTitle = ((TextView) findViewById(R.id.mTitle));
        mBackpress = ((ImageView) findViewById(R.id.mBackpress));
        mBackpress.setOnClickListener(this);

        mrecyclerview = ((FastScrollRecyclerView)findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dataItem = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mAction.equals("vehicle")){
            mTitle.setText("Vehicle - "+mData);

        }else if(mAction.equals("company")){
            mTitle.setText("Company - "+mData);

        } else if(mAction.equals("employee")){
            mTitle.setText("Employee - "+mData);

        }
        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadFlipLoadInitiate mLoadFlipLoadInitiate = new LoadFlipLoadInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadFlipLoadInitiate.execute((Void) null);
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
            case R.id.mBackpress:

                System.out.println("1111111111111"+mAction);
                if(mAction.equals("vehicle")){
                    CloseView("FLIPVEHICLE");

                }else if(mAction.equals("company")){
                    CloseView("FLIPCOMPANY");

                } else if(mAction.equals("employee")){
                    CloseView("FLIPEMPLOYEE");

                }
                break;
        }
    }

    public class LoadFlipLoadInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadFlipLoadInitiate(String id, String authorization, boolean status, String Start) {
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

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = null;

            if(mAction.equals("vehicle")){
                result = httpOperations.docompanyvehicleList(mId, mAuthorization, mStart,mID);

            }else if(mAction.equals("company")){
                result = httpOperations.docompanyemployeeList(mId, mAuthorization, mStart,mID);

            } else if(mAction.equals("employee")){
                result = httpOperations.doEmployeedesList(mId, mAuthorization, mStart,mData.
                        replaceAll(" ","%20"));
            }
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

                        if(mAction.equals("company")){

                            JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                            for (int i = 0; i < feedArray.length(); i++) {

                                ListItem item = new ListItem();
                                JSONObject feedObj = (JSONObject) feedArray.get(i);

                                if((!feedObj.getString("employee_name").equals(""))
                                        && (!feedObj.getString("employee_name").equals("null"))) {

                                    item.set_id(feedObj.getString("employee_id"));
                                    item.set_name(StringUtils.capitalize(feedObj.getString("employee_name")
                                            .toLowerCase().trim()));

                                    if(feedObj.getString("designation").trim().equals("")){
                                        item.set_designation("None");
                                    }else {
                                        item.set_designation(feedObj.getString("designation"));
                                    }
                                    if(feedObj.getString("employee_email").trim().equals("")){
                                        item.set_email("None");
                                    }else {
                                        item.set_email(feedObj.getString("employee_email"));
                                    }
                                    if(feedObj.getString("employee_phone").trim().equals("")){
                                        item.set_phone("None");
                                    }else {
                                        item.set_phone(feedObj.getString("employee_phone"));
                                    }
                                    if(feedObj.getString("company").trim().equals("")){
                                        item.set_address("None");
                                    }else {
                                        item.set_address(feedObj.getString("company"));
                                    }
                                   /* if (feedObj.getString("employee_pic").equals("")) {
                                        item.setImage("false");
                                    } else {
                                        item.setImage(feedObj.getString("employee_pic").trim());
                                    }*/
                                    dataItem.add(item);
                                }
                            }
                            start = dataItem.size();
                            HomeEmpListAdapter mCpyEmpListAdapter = new HomeEmpListAdapter(FlipChildActivity.this, dataItem,
                                    mrecyclerview ,"flipcompany");
                            mrecyclerview.setAdapter(mCpyEmpListAdapter);
                        } else if(mAction.equals("employee")){

                            JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                            for (int i = 0; i < feedArray.length(); i++) {

                                ListItem item = new ListItem();
                                JSONObject feedObj = (JSONObject) feedArray.get(i);

                                if((!feedObj.getString("employee_name").equals(""))
                                        && (!feedObj.getString("employee_name").equals("null"))) {

                                    item.set_id(feedObj.getString("employee_id"));
                                    item.set_name(StringUtils.capitalize(feedObj.getString("employee_name")
                                            .toLowerCase().trim()));

                                    if(feedObj.getString("company").trim().equals("")){
                                        item.set_address("None");
                                    }else {
                                        item.set_address(feedObj.getString("company"));
                                    }
                                    if(feedObj.getString("employee_email").trim().equals("")){
                                        item.set_email("None");
                                    }else {
                                        item.set_email(feedObj.getString("employee_email"));
                                    }
                                    if(feedObj.getString("employee_phone").trim().equals("")){
                                        item.set_phone("None");
                                    }else {
                                        item.set_phone(feedObj.getString("employee_phone"));
                                    }
                                    if(feedObj.getString("designation").trim().equals("")){
                                        item.set_designation("None");
                                    }else {
                                        item.set_designation(feedObj.getString("designation"));
                                    }
                                    /*if (feedObj.getString("employee_pic").equals("")) {
                                        item.setImage("false");
                                    } else {
                                        item.setImage(feedObj.getString("employee_pic").trim());
                                    }*/
                                    dataItem.add(item);
                                }
                            }
                            start = dataItem.size();
                            HomeEmpListAdapter mCpyEmpListAdapter = new HomeEmpListAdapter(FlipChildActivity.this, dataItem,
                                    mrecyclerview ,"flipemployee");
                            mrecyclerview.setAdapter(mCpyEmpListAdapter);
                        }  if(mAction.equals("vehicle")){

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
                                   /* if (feedObj.getString("vehicle_picture").equals("")) {
                                        item.setImage("false");
                                    } else {
                                        item.setImage(feedObj.getString("vehicle_picture").trim());
                                    } *///add pic
                                    dataItem.add(item);
                                }
                            }
                            start = dataItem.size();
                            CpyVehicleAdapter mVehicleListAdapter = new CpyVehicleAdapter(FlipChildActivity.this
                                    , dataItem,"flipvehicle");
                            mrecyclerview.setAdapter(mVehicleListAdapter);
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

    private void CloseView(String PAGE){
        Intent intent = new Intent(FlipChildActivity.this,DashboardActivity.class);
        intent.putExtra("PAGE",PAGE);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}