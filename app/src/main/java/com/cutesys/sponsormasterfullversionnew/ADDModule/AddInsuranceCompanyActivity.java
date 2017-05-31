package com.cutesys.sponsormasterfullversionnew.ADDModule;

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

public class AddInsuranceCompanyActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sPreferences;

    private MaterialEditText InsuranceCompany;
    private ImageView done, close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinsurancecompany);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
        InitIdView();
    }

    private void InitIdView() {

        InsuranceCompany = (MaterialEditText) findViewById(R.id.InsuranceCompany);
        mTitle = ((TextView) findViewById(R.id.mTitle));
        done = (ImageView) findViewById(R.id.done);
        close = (ImageView) findViewById(R.id.close);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Insurance Company");
    }

    private void AddInsuranceCompany(){
        Config mConfig = new Config(getApplication());
        if(mConfig.isOnline(getApplication())){
            LoadAddInsuranceCompanyInitiate mLoadAddInsuranceCompanyInitiate = new
                    LoadAddInsuranceCompanyInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    InsuranceCompany.getText().toString().trim());
            mLoadAddInsuranceCompanyInitiate.execute((Void) null);
        }else {
            CustomToast.error(getApplication(),"No Internet Connection.").show();
        }
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();

        switch (buttonId) {
            case R.id.done:

                if(loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getApplication(),"Please wait while we process your request").show();
                }else {
                    if (InsuranceCompany.getText().toString().trim().equals("")) {

                        InsuranceCompany.setError("Field cannot be blank");
                    } else {
                        AddInsuranceCompany();
                    }
                }
                break;
            case R.id.close:
                if(loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getApplication(),"Please wait while we process your request").show();
                }else {
                    Close_view();
                }
                break;
        }
    }
    private void Close_view(){
        Intent intent = new Intent(AddInsuranceCompanyActivity.this,DashboardActivity.class);
        intent.putExtra("PAGE","INSURANCE COMPANY");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    public class LoadAddInsuranceCompanyInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mdesignation;

        LoadAddInsuranceCompanyInitiate(String id, String authorization, String designation) {
            mId = id;
            mAuthorization = authorization;
            mdesignation = designation;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplication());
            StringBuilder result = httpOperations.doAddInsuranceCompany(mId, mAuthorization, mdesignation);
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

                        InsuranceCompany.setText("");
                        CustomToast.info(getApplication(),"Insurance Company Already Exist").show();

                    }else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        InsuranceCompany.setText("");
                        CustomToast.success(getApplication(),"Insurance Company Added Successfully").show();
                        //close_reveal();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                CustomToast.info(getApplication(),"Please Try Again").show();
            } catch (NullPointerException e) {
                CustomToast.error(getApplication(),"No Internet Connection.").show();
            } catch (Exception e) {
                CustomToast.info(getApplication(),"Please Try Again").show();
            }
        }
    }
}