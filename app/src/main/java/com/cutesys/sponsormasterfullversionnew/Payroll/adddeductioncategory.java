package com.cutesys.sponsormasterfullversionnew.Payroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Edittext.MaterialEditText;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kris on 3/22/2017.
 */

public class adddeductioncategory extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private MaterialEditText addDeduction;
    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_deductioncategory);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
        InitIdView();
    }

    private void InitIdView(){

        addDeduction = (MaterialEditText)findViewById(R.id.addDeduction);
        mTitle = ((TextView) findViewById(R.id.mTitle));
        done = (ImageView) findViewById(R.id.done);
        close = (ImageView) findViewById(R.id.close);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Deduction Category");


    }

    private void AddDesignation(){
        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    addDeduction.getText().toString().trim());
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
                if (loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getApplicationContext(), "Please wait while we process your request").show();
                } else {

                    if (addDeduction.getText().toString().trim().equals("")) {

                        addDeduction.setError("Field cannot be blank");
                    } else {
                        AddDesignation();
                    }
                }

                break;
            case R.id.close:
                if (loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getApplicationContext(), "Please wait while we process your request").show();
                } else {
                    Close_view();

                }
                break;

        }



    }


    public class LoadAddEvents extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mdeduction_category;

        LoadAddEvents(String id, String authorization, String deduction_category) {
            mId = id;
            mAuthorization = authorization;
            mdeduction_category=deduction_category;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doadddeductioncategory(mId, mAuthorization, mdeduction_category);
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

                        addDeduction.setText("");
                        CustomToast.info(getApplicationContext(),"already exist").show();

                    }else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        addDeduction.setText("");
                        CustomToast.success(getApplicationContext()," Added Successfully").show();

                        //close_reveal();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                CustomToast.info(getApplicationContext(),"Please Try Again").show();
            } catch (NullPointerException e) {
                CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
            } catch (Exception e) {
                CustomToast.info(getApplicationContext(),"Please Try Again").show();
            }
        }
    }
    private void Close_view(){
        Intent intent = new Intent(adddeductioncategory.this,DashboardActivity.class);
        intent.putExtra("PAGE","DEDUCTIONCATEGORY");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }
}


