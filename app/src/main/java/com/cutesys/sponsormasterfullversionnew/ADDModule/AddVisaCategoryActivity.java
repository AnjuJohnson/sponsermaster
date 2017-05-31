package com.cutesys.sponsormasterfullversionnew.ADDModule;

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
 * Created by Kris on 3/22/2017.
 */

public class AddVisaCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    ArrayList<ListItem> dataItem;
    String mSpinnerItem = "";

    private Spinner spinner;
    private ImageView progressrestart;
    private TextView spinnererror;
    private MaterialEditText category;
    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvisacategory);

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
        progressrestart = (ImageView) findViewById(R.id.progressrestart);
        progressrestart.setOnClickListener(this);
        spinnererror = (TextView) findViewById(R.id.spinnererror);
        spinnererror.setVisibility(View.GONE);
        spinner = (Spinner)findViewById(R.id.spinner);

        loading.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Visa Category");

        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadExpiryListInitiate mLoadExpiryListInitiate = new LoadExpiryListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
            mLoadExpiryListInitiate.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).get_name();
                System.out.println("zzzzzzzzz"+mSpinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void AddVisaType(){
        if (category.getText().toString().trim().equals("")){
            category.setError("Field cannot be blank");
            spinnererror.setVisibility(View.GONE);
        }
        else if (mSpinnerItem.toString().trim().equals("")){
            spinnererror.setVisibility(View.VISIBLE);
        } else {
            spinnererror.setVisibility(View.GONE);
            Config mConfig = new Config(getApplicationContext());

            if(mConfig.isOnline(getApplicationContext())){
                LoadAddVisaCategoryInitiate mLoadAddVisaCategoryInitiate = new LoadAddVisaCategoryInitiate(sPreferences.getString("ID", ""),
                        sPreferences.getString("AUTHORIZATION", ""),
                        mSpinnerItem.replaceAll(" ",""),
                        category.getText().toString().trim());
                mLoadAddVisaCategoryInitiate.execute((Void) null);
            }else {
                CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
            }
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
                    } else if (mSpinnerItem.toString().trim().equals("")){

                        spinnererror.setVisibility(View.VISIBLE);
                    } else {
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
                    LoadExpiryListInitiate mLoadExpiryListInitiate =
                            new LoadExpiryListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
                    mLoadExpiryListInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;

        }
    }

    public class LoadExpiryListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;

        LoadExpiryListInitiate(String id, String authorization, String Start) {
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
            StringBuilder result = httpOperations.doVisacategoryList(mId, mAuthorization,mStart);
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

                        JSONArray feedArray = jsonObj.getJSONArray("visa_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("visa_type_name").equals(""))
                                    && (!feedObj.getString("visa_type_name").equals("null"))) {


                                item.set_code(feedObj.getString("visa_category"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("visa_type_name")
                                        .toLowerCase().trim()));

                                dataItem.add(item);
                            }
                        }
                        SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(getApplicationContext(),
                                dataItem);
                        spinner.setAdapter(mSpinnerAdapter);

                    }else {
                        CustomToast.info(getApplicationContext(),"" +
                                "No Visa Type found").show();
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

    public class LoadAddVisaCategoryInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization,mvisa_category,mvisa_type_id;

        LoadAddVisaCategoryInitiate(String id, String authorization,
                                    String visa_category, String visa_type_id) {
            mId = id;
            mAuthorization = authorization;
            mvisa_category = visa_category;;
            mvisa_type_id = visa_type_id;;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doAddVisaCategory(mId, mAuthorization,
                    mvisa_category,mvisa_type_id);
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
                        CustomToast.info(getApplicationContext(), "Visa Category Already exist").show();

                    } else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        category.setText("");
                        CustomToast.success(getApplicationContext(), "Visa Category Added Successfully").show();
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
        Intent intent = new Intent(AddVisaCategoryActivity.this,DashboardActivity.class);
        intent.putExtra("PAGE","VISACATEGORY");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
