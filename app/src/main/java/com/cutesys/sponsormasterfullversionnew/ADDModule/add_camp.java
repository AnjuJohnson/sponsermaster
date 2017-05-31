package com.cutesys.sponsormasterfullversionnew.ADDModule;

/**
 * Created by user on 3/30/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Edittext.MaterialEditText;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.CompanySpinner;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.CampListItem;
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

public class add_camp extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    ArrayList<CampListItem> dataItem;
    String mSpinnerItem = "";

    private Spinner spinner_1;
    private ImageView progressrestart;
    private TextView spinnererror;
    private MaterialEditText location,camp_name,add_ress,manager,contact_no,email,capacity;
    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_camp);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
        InitIdView();
    }

    private void InitIdView(){

        location = (MaterialEditText)findViewById(R.id.location);
        camp_name = (MaterialEditText)findViewById(R.id.camp_name);
        add_ress = (MaterialEditText)findViewById(R.id.add_ress);
        manager = (MaterialEditText)findViewById(R.id.manager);
        contact_no = (MaterialEditText)findViewById(R.id.contact_no);
        email= (MaterialEditText)findViewById(R.id.email);
        capacity= (MaterialEditText)findViewById(R.id.capacity);

        mTitle = ((TextView) findViewById(R.id.mTitle));
        done = (ImageView) findViewById(R.id.done);
        close = (ImageView) findViewById(R.id.close);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        progressrestart = (ImageView) findViewById(R.id.progressrestart);
        progressrestart.setOnClickListener(this);
        spinnererror = (TextView) findViewById(R.id.spinnererror);
        spinnererror.setVisibility(View.GONE);
        spinner_1 = (Spinner)findViewById(R.id.spinner_1);
        dataItem = new ArrayList<>();
        loading.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Camp");

        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadCampListInitiate mLoadCampListInitiate = new  LoadCampListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
            mLoadCampListInitiate.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).getcompany_id();
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
                    location.getText().toString().trim(),
                    camp_name.getText().toString().trim(),
                    add_ress.getText().toString().trim(),
                    manager.getText().toString().trim(),
                    contact_no.getText().toString().trim(),
                    email.getText().toString().trim(),
                    capacity.getText().toString().trim());

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
                }
                else if (mSpinnerItem.toString().trim().equals("")){

                    spinnererror.setVisibility(View.VISIBLE);
                } else {
                    if (location.getText().toString().trim().equals("")) {

                        location.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
                    }
                    else if (camp_name.getText().toString().trim().equals("")) {

                        camp_name.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
                    }
                    else if (add_ress.getText().toString().trim().equals("")) {

                        add_ress.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
                    }
                    else if (manager.getText().toString().trim().equals("")) {

                        manager.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
                    }
                    else if (contact_no.getText().toString().trim().equals("")) {

                        contact_no.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
                    }
                    else if (email.getText().toString().trim().equals("")) {

                        email.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
                    }
                    else if (capacity.getText().toString().trim().equals("")) {

                        capacity.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
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
                    dataItem.clear();
                    LoadCampListInitiate  mLoadCampListInitiate =
                            new   LoadCampListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
                    mLoadCampListInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;

        }
    }

    public class  LoadCampListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;

        LoadCampListInitiate(String id, String authorization, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mSpinnerItem = "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // dataItem = new ArrayList<>();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.docampList(mId, mAuthorization,mStart);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        JSONArray feedArray = jsonObj.getJSONArray("camp_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            CampListItem item = new CampListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("company_name").equals(""))
                                    && (!feedObj.getString("company_name").equals("null"))) {

                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.setcompany_name("None");
                                } else {
                                    item.setcompany_name(StringUtils.capitalize(feedObj.getString("company_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_id").trim().equals("")) {
                                    item.setcamp_id("None");
                                } else {
                                    item.setcamp_id((feedObj.getString("camp_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("company_id").trim().equals("")) {
                                    item.setcompany_id("None");
                                } else {
                                    item.setcompany_id((feedObj.getString("company_id")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("camp_location").trim().equals("")) {
                                    item.setcamp_location("None");
                                } else {
                                    item.setcamp_location(StringUtils.capitalize(feedObj.getString("camp_location")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_address").trim().equals("")) {
                                    item.setcamp_address("None");
                                } else {
                                    item.setcamp_address(StringUtils.capitalize(feedObj.getString("camp_address")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_manager").trim().equals("")) {
                                    item.setcamp_manager("None");
                                } else {
                                    item.setcamp_manager(StringUtils.capitalize(feedObj.getString("camp_manager")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("camp_contact").trim().equals("")) {
                                    item.setcamp_contact("None");
                                } else {
                                    item.setcamp_contact(StringUtils.capitalize(feedObj.getString("camp_contact")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_email").trim().equals("")) {
                                    item.setcamp_email("None");
                                } else {
                                    item.setcamp_email(StringUtils.capitalize(feedObj.getString("camp_email")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_intake").trim().equals("")) {
                                    item.setcamp_intake("None");
                                } else {
                                    item.setcamp_intake(StringUtils.capitalize(feedObj.getString("camp_intake")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_name").trim().equals("")) {
                                    item.setcamp_name("None");
                                } else {
                                    item.setcamp_name(StringUtils.capitalize(feedObj.getString("camp_name")
                                            .toLowerCase().trim()));
                                }



                                dataItem.add(item);
                            }
                        }
                        CompanySpinner mSpinnerAdapter = new CompanySpinner(getApplicationContext(),
                                dataItem);
                        spinner_1.setAdapter(mSpinnerAdapter);

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

        private String mId, mAuthorization,mcompany_id,
                mcamp_location, mcamp_name, mcamp_address, mcamp_manager, mcamp_contact, mcamp_email,mcamp_intake;

        LoadAddEvents(String id, String authorization,
                      String company_id,String camp_location,String camp_name,String camp_address,String camp_manager,String camp_contact,String camp_email,String camp_intake ) {
            mId = id;
            mAuthorization = authorization;
            mcompany_id=company_id;
            mcamp_location=camp_location;
            mcamp_name=camp_name;
            mcamp_address=camp_address;
            mcamp_manager=camp_manager;
            mcamp_contact=camp_contact;
            mcamp_email=camp_email;
            mcamp_intake=camp_intake;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doAddCamp(mId, mAuthorization,
                    mcompany_id,mcamp_location,mcamp_name,mcamp_address,mcamp_manager,mcamp_contact,mcamp_email,mcamp_intake);
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

                        location.setText("");
                        camp_name.setText("");
                        add_ress.setText("");
                        manager.setText("");
                        contact_no.setText("");
                        email.setText("");
                        capacity.setText("");
                        CustomToast.info(getApplicationContext(), "Camp Already exist").show();


                    } else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        location.setText("");
                        camp_name.setText("");
                        add_ress.setText("");
                        manager.setText("");
                        contact_no.setText("");
                        email.setText("");
                        capacity.setText("");
                        CustomToast.success(getApplicationContext(), "Camp Added Successfully").show();
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
        Intent intent = new Intent(add_camp.this,DashboardActivity.class);
        intent.putExtra("PAGE","CAMP DETAILS LIST");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}