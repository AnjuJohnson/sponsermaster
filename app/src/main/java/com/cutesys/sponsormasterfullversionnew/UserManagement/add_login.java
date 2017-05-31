package com.cutesys.sponsormasterfullversionnew.UserManagement;

/**
 * Created by Owner on 21/03/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Edittext.MaterialEditText;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.logindetailsitem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kris on 3/22/2017.
 */

public class add_login extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    ArrayList<logindetailsitem> dataItem;
    String mSpinnerItem = "",mSpinnerItem1 = "",mSpinnerItem2 = "";

    private Spinner spinner_11, spinner_2, spinner_3;
    private ImageView progressrestart_1,progressrestart1,progressrestart2;
    private TextView spinnererror_1,spinnererror1,spinnererror2;
    private MaterialEditText emp_id,emp_username,emp_password;
    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading, progress_1,progress1,progress2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_login);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
        InitIdView();
    }

    private void InitIdView(){

        emp_id = (MaterialEditText)findViewById(R.id.emp_id);
       emp_username = (MaterialEditText)findViewById(R.id.emp_username);
        emp_password = (MaterialEditText)findViewById(R.id.emp_password);
        mTitle = ((TextView) findViewById(R.id.mTitle));
        done = (ImageView) findViewById(R.id.done);
        close = (ImageView) findViewById(R.id.close);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        progress_1 = (AVLoadingIndicatorView) findViewById(R.id.progress_1);
        progress1 = (AVLoadingIndicatorView) findViewById(R.id.progress1);
        progress2 = (AVLoadingIndicatorView) findViewById(R.id.progress2);
        progressrestart_1 = (ImageView) findViewById(R.id.progressrestart_1);
        progressrestart_1.setOnClickListener(this);
        progressrestart1 = (ImageView) findViewById(R.id.progressrestart1);
        progressrestart1.setOnClickListener(this);
        progressrestart2 = (ImageView) findViewById(R.id.progressrestart2);
        progressrestart2.setOnClickListener(this);
        spinnererror_1 = (TextView) findViewById(R.id.spinnererror_1);
        spinnererror_1.setVisibility(View.GONE);
        spinnererror1 = (TextView) findViewById(R.id.spinnererror1);
        spinnererror1.setVisibility(View.GONE);
        spinnererror2 = (TextView) findViewById(R.id.spinnererror2);
        spinnererror2.setVisibility(View.GONE);
        spinner_11 = (Spinner)findViewById(R.id.spinner_11);
        spinner_2 = (Spinner)findViewById(R.id.spinner_2);
        spinner_3 = (Spinner)findViewById(R.id.spinner_3);
        loading.setVisibility(View.GONE);
        progress_1.setVisibility(View.GONE);
        progress1.setVisibility(View.GONE);
        progress2.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Login");

        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadLoginInitiate mLoadLoginInitiate  = new LoadLoginInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
            mLoadLoginInitiate .execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
        Config mConfig1 = new Config(getApplicationContext());
        if(mConfig1.isOnline(getApplicationContext())){
            LoadLoginInitiate mLoadLoginInitiate  = new LoadLoginInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
            mLoadLoginInitiate .execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
        Config mConfig2 = new Config(getApplicationContext());
        if(mConfig2.isOnline(getApplicationContext())){
            LoadLoginInitiate mLoadLoginInitiate  = new LoadLoginInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
            mLoadLoginInitiate .execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }

        spinner_11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).getcompany_name();
                //System.out.println("zzzzzzzzz"+mSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem1 = dataItem.get(i).getemployee_designation();
                //System.out.println("zzzzzzzzz"+mSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem2 = dataItem.get(i).getfull_name();
                //System.out.println("zzzzzzzzz"+mSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void AddVisaType(){
        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    mSpinnerItem.toString(),
                    mSpinnerItem1.toString(),
                    mSpinnerItem2.toString(),
                   emp_id.getText().toString().trim(),
                    emp_username.getText().toString().trim(),
                    emp_password.getText().toString().trim());
            mLoadAddEvents.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
        Config mConfig1 = new Config(getApplicationContext());
        if(mConfig1.isOnline(getApplicationContext())){
            LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    mSpinnerItem.toString(),
                    mSpinnerItem1.toString(),
                    mSpinnerItem2.toString(),
                    emp_id.getText().toString().trim(),
                    emp_username.getText().toString().trim(),
                    emp_password.getText().toString().trim());

            mLoadAddEvents.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
        Config mConfig2 = new Config(getApplicationContext());
        if(mConfig2.isOnline(getApplicationContext())){
            LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    mSpinnerItem.toString(),
                    mSpinnerItem1.toString(),
                    mSpinnerItem2.toString(),
                    emp_id.getText().toString().trim(),
                    emp_username.getText().toString().trim(),
                    emp_password.getText().toString().trim());

            mLoadAddEvents.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();

        switch (buttonId) {
            case R.id.done:

                if(loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getApplicationContext(),"Please wait while we process your request").show();
                }else {
                   if (mSpinnerItem.toString().trim().equals("")){

                        spinnererror_1.setVisibility(View.VISIBLE);

                    } else {
                        AddVisaType();
                    }
                    if (mSpinnerItem1.toString().trim().equals("")){

                        spinnererror1.setVisibility(View.VISIBLE);

                    } else {
                        AddVisaType();
                    }
                    if (mSpinnerItem2.toString().trim().equals("")){

                        spinnererror2.setVisibility(View.VISIBLE);

                    }
                    if (emp_id.getText().toString().trim().equals("")) {

                        emp_id.setError("Field cannot be blank");
                        spinnererror_1.setVisibility(View.GONE);
                        spinnererror1.setVisibility(View.GONE);
                        spinnererror2.setVisibility(View.GONE);
                    }
                    else if (emp_username.getText().toString().trim().equals("")) {

                        emp_username.setError("Field cannot be blank");
                        spinnererror_1.setVisibility(View.GONE);
                        spinnererror1.setVisibility(View.GONE);
                        spinnererror2.setVisibility(View.GONE);
                    }
                    else if (emp_password.getText().toString().trim().equals("")) {

                        emp_password.setError("Field cannot be blank");
                        spinnererror_1.setVisibility(View.GONE);
                        spinnererror1.setVisibility(View.GONE);
                        spinnererror2.setVisibility(View.GONE);
                    }
                    else {
                        AddVisaType();
                    }
                }
                break;
            case R.id.close:
                if(loading.getVisibility() == View.VISIBLE) {


                    CustomToast.info(getApplicationContext(),"Please wait while we process your request").show();
                }else {
                    Close_view();
                }
                break;

            case R.id.progressrestart:
                Config mConfig = new Config(getApplicationContext());
                if(mConfig.isOnline(getApplicationContext())){
                    LoadLoginInitiate  mLoadLoginInitiate =
                            new  LoadLoginInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
                    mLoadLoginInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;
            case R.id.progressrestart1:
                Config mConfig1 = new Config(getApplicationContext());
                if(mConfig1.isOnline(getApplicationContext())){
                    LoadLoginInitiate  mLoadLoginInitiate =
                            new  LoadLoginInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
                    mLoadLoginInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;
            case R.id.progressrestart2:
                Config mConfig2 = new Config(getApplicationContext());
                if(mConfig2.isOnline(getApplicationContext())){
                    LoadLoginInitiate  mLoadLoginInitiate =
                            new  LoadLoginInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
                    mLoadLoginInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;

        }
    }

    public class   LoadLoginInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;

        LoadLoginInitiate(String id, String authorization, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mSpinnerItem = "";
            mSpinnerItem1 = "";
            mSpinnerItem2 = "";

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataItem = new ArrayList<>();
            progress_1.setVisibility(View.VISIBLE);
            progress1.setVisibility(View.VISIBLE);
            progress2.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doUserLoginList(mId, mAuthorization,mStart);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            progress_1.setVisibility(View.GONE);
            progress1.setVisibility(View.GONE);
            progress2.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        JSONArray feedArray = jsonObj.getJSONArray("login_details_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            logindetailsitem item = new logindetailsitem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("company_name").equals(""))
                                    && (!feedObj.getString("company_name").equals("null"))) {

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


                                if (feedObj.getString("full_name").trim().equals("")) {
                                    item.setfull_name("None");
                                } else {
                                    item.setfull_name(StringUtils.capitalize(feedObj.getString("full_name")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.setcompany_name("None");
                                } else {
                                    item.setcompany_name(StringUtils.capitalize(feedObj.getString("company_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("employee_designation").trim().equals("")) {
                                    item.setemployee_designation("None");
                                } else {
                                    item.setemployee_designation(StringUtils.capitalize(feedObj.getString("employee_designation")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("employee_username").trim().equals("")) {
                                    item.setemployee_username("None");
                                } else {
                                    item.setemployee_username(StringUtils.capitalize(feedObj.getString("employee_username")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("employee_password").trim().equals("")) {
                                    item.setemployee_password("None");
                                } else {
                                    item.setemployee_password(StringUtils.capitalize(feedObj.getString("employee_password")
                                            .toLowerCase().trim()));
                                }

                                dataItem.add(item);
                            }
                        }
                        LoginSpinner mSpinnerAdapter = new LoginSpinner(getApplicationContext(),
                                dataItem);
                        spinner_11.setAdapter(mSpinnerAdapter);
                        Login1Spinner mSpinnerAdapter1 = new Login1Spinner(getApplicationContext(),
                                dataItem);
                        spinner_2.setAdapter(mSpinnerAdapter1);
                        Login2Spinner mSpinnerAdapter2 = new Login2Spinner(getApplicationContext(),
                                dataItem);
                        spinner_3.setAdapter(mSpinnerAdapter2);

                    }else {
                        CustomToast.info(getApplicationContext(),"" +
                                "No item found").show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                CustomToast.info(getApplicationContext(),"Please try again").show();
            } catch (NullPointerException e) {
                CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
            } catch (Exception e) {
                CustomToast.info(getApplicationContext(),"Please try again").show();
            }
        }
    }

    public class LoadAddEvents extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization,mcompany, mdesignation,memployee_id,memployee_name,musername,mpassword;

        LoadAddEvents(String id, String authorization,
                      String company,String designation,String employee_id,String employee_name,String username,String password ) {
            mId = id;
            mAuthorization = authorization;
            mcompany=company;
            mdesignation=designation;


            memployee_id=employee_id;
            memployee_name=employee_name;
            musername=username;
            mpassword=password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());

           System.out.println("zzzz"+mId+ "zzzz"+mAuthorization+
                    "zzzz"+mcompany+"zzzz"+mdesignation+"zzzz"+memployee_id+"zzz"+memployee_name+
                    "zzzz"+musername+"zzzz"+mpassword);
            StringBuilder result = httpOperations.doAddLogin(mId, mAuthorization,
                    mcompany,mdesignation,memployee_id,memployee_name.replaceAll(" ","%20"),musername.replaceAll(" ","%20"),mpassword);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            loading.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(404))) {

                       emp_id.setText("");
                        emp_username.setText("");
                        emp_password.setText("");
                        CustomToast.info(getApplicationContext(), " Already exist").show();


                    } else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        emp_id.setText("");
                        emp_username.setText("");
                        emp_password.setText("");
                        CustomToast.success(getApplicationContext(), " Added Successfully").show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                CustomToast.info(getApplicationContext(), "Please Try Again").show();
            } catch (NullPointerException e) {
                CustomToast.error(getApplicationContext(), "No Internet Connection.").show();
            } catch (Exception e) {
                CustomToast.info(getApplicationContext(), "Please Try Again").show();
            }
        }
    }

    private void Close_view(){
        Intent intent = new Intent(add_login.this,DashboardActivity.class);
        intent.putExtra("PAGE","LOGIN LIST");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
