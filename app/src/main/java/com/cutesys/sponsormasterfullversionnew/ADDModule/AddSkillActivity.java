package com.cutesys.sponsormasterfullversionnew.ADDModule;

/**
 * Created by user on 3/28/2017.
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
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Edittext.MaterialEditText;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SpinnerAdapter;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SpinnerAdapter1;
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

public class AddSkillActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    ArrayList<ListItem> dataItem;
    ArrayList<ListItem> dataItem1;
    String mSpinnerItem = "";
    String mSpinnerItem1 = "";
    int start = 0;

    private Spinner spinner,spinner1;
    private ImageView progressrestart,progressrestart1;
    private TextView spinnererror,spinnererror1;
    private MaterialEditText category;
    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading, progress,progress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addskill);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
        InitIdView();
    }

    private void InitIdView(){

        category = (MaterialEditText)findViewById(R.id.category);
        mTitle = ((TextView) findViewById(R.id.mTitle));
        done = (ImageView) findViewById(R.id.done);
        close = (ImageView) findViewById(R.id.close);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        progress1 = (AVLoadingIndicatorView) findViewById(R.id.progress1);
        progressrestart = (ImageView) findViewById(R.id.progressrestart);
        progressrestart.setOnClickListener(this);
        progressrestart1 = (ImageView) findViewById(R.id.progressrestart1);
        progressrestart1.setOnClickListener(this);
        spinnererror = (TextView) findViewById(R.id.spinnererror);
        spinnererror.setVisibility(View.GONE);
        spinnererror1 = (TextView) findViewById(R.id.spinnererror1);
        spinnererror1.setVisibility(View.GONE);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner1 = (Spinner)findViewById(R.id.spinner1);

        loading.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Skill");

        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadSkillJobListInitiate mLoadSkillJobListInitiate = new LoadSkillJobListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
            mLoadSkillJobListInitiate.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).get_name();
                System.out.println("zzzzzzzzz" + mSpinnerItem);
                Config mConfig = new Config(getApplicationContext());
                if(mConfig.isOnline(getApplicationContext())){
                    LoadSkillCategoryListInitiate mLoadSkillCategoryListInitiate = new LoadSkillCategoryListInitiate(sPreferences.getString("ID", ""),
                            sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start),mSpinnerItem);
                    mLoadSkillCategoryListInitiate.execute((Void) null);
                }/*else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem1 = dataItem1.get(i).get_code();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void AddVisaType(){
        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadAddModelInitiate mLoadAddModelInitiate = new LoadAddModelInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    mSpinnerItem.toString(),
                    mSpinnerItem1.toString(),
                    category.getText().toString().trim());
            mLoadAddModelInitiate.execute((Void) null);
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
                    if (category.getText().toString().trim().equals("")) {

                        category.setError("Field cannot be blank");
                        spinnererror.setVisibility(View.GONE);
                        spinnererror1.setVisibility(View.GONE);
                    } else if (mSpinnerItem.toString().trim().equals("")){
                        spinnererror.setVisibility(View.VISIBLE);
                    } else if(mSpinnerItem1.toString().trim().equals("")) {
                        spinnererror1.setVisibility(View.VISIBLE);
                    }else
                        AddVisaType();
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
                    LoadSkillJobListInitiate mLoadSkillJobListInitiate =
                            new LoadSkillJobListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
                    mLoadSkillJobListInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;
            case R.id.progressrestart1:
                Config mConfig1 = new Config(getApplicationContext());
                if(mConfig1.isOnline(getApplicationContext())){
                    LoadSkillCategoryListInitiate mLoadSkillCategoryListInitiate =
                            new LoadSkillCategoryListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0),mSpinnerItem);
                    mLoadSkillCategoryListInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;

        }
    }

    public class LoadSkillJobListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;

        LoadSkillJobListInitiate(String id, String authorization, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mSpinnerItem = "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataItem = new ArrayList<>();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.dorecruitmentjobList(mId, mAuthorization,mStart);
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

                        JSONArray feedArray = jsonObj.getJSONArray("job_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("job_name").equals(""))
                                    && (!feedObj.getString("job_name").equals("null"))) {


                                item.set_id(feedObj.getString("job_id"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("job_name")
                                        .toLowerCase().trim()));

                                dataItem.add(item);
                            }
                        }
                        SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(getApplicationContext(),
                                dataItem);
                        spinner.setAdapter(mSpinnerAdapter);

                    }else {
                        CustomToast.info(getApplicationContext(),"" +
                                "No Job found").show();
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

    public class LoadSkillCategoryListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart,mJob;

        LoadSkillCategoryListInitiate(String id, String authorization, String Start,String Job) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mJob=Job;
            mSpinnerItem1 = "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataItem = new ArrayList<>();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.dorecruitmentcategorylistList(mId, mAuthorization,mStart/*,mJob*/);
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

                        JSONArray feedArray = jsonObj.getJSONArray("category_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("job_category_name").equals(""))
                                    && (!feedObj.getString("job_category_name").equals("null"))) {

                                item.set_id(feedObj.getString("job_category_id"));
                                item.set_code(StringUtils.capitalize(feedObj.getString("job_category_name")
                                        .toLowerCase().trim()));
                                item.set_name(StringUtils.capitalize(feedObj.getString("job_position")
                                        .toLowerCase().trim()));
                                dataItem1.add(item);
                            }
                        }
                        SpinnerAdapter1 mSpinnerAdapter = new SpinnerAdapter1(getApplicationContext(),
                                dataItem1);
                        spinner1.setAdapter(mSpinnerAdapter);

                    }else {
                        CustomToast.info(getApplicationContext(),"" +
                                "No Category found").show();
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

    public class LoadAddModelInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mJob,mCategory,mSkill;

        LoadAddModelInitiate(String id, String authorization,
                      String Job, String Category,String Skill) {
            mId = id;
            mAuthorization = authorization;
            mJob = Job;
            mCategory = Category;
            mSkill=Skill;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doAddSkill(mId, mAuthorization,
                    mJob,mCategory,mSkill);
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

                        category.setText("");
                        CustomToast.info(getApplicationContext(), "Skill Already exist").show();

                    } else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        category.setText("");
                        CustomToast.success(getApplicationContext(), "Skill Added Successfully").show();
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
        Intent intent = new Intent(AddSkillActivity.this,DashboardActivity.class);
        intent.putExtra("PAGE","SKILL");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
