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

public class AddVisaSponsorActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private MaterialEditText name, email;
    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvisasponsor);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
        InitIdView();
    }

    private void InitIdView(){

        name = (MaterialEditText) findViewById(R.id.name);
        email = (MaterialEditText) findViewById(R.id.email);
        mTitle = ((TextView) findViewById(R.id.mTitle));
        done = (ImageView) findViewById(R.id.done);
        close = (ImageView) findViewById(R.id.close);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Visa Sponsor");

    }

    private void AddSponsor(){
        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadAddSponsorInitiate mLoadAddSponsorInitiate = new LoadAddSponsorInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),
                    name.getText().toString().trim(),
                    email.getText().toString().trim());
            mLoadAddSponsorInitiate.execute((Void) null);
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
                    if (name.getText().toString().trim().equals("")) {
                        name.setError("Field cannot be blank");

                    }else if (email.getText().toString().trim().equals("")) {
                        email.setError("Field cannot be blank");

                    }else if (email.getText().toString().trim().length() < 5) {
                        email.setError("Invalid Email");

                    } else {
                        AddSponsor();
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
        }
    }

    public class LoadAddSponsorInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mname, mmail;

        LoadAddSponsorInitiate(String id, String authorization, String name, String mail) {
            mId = id;
            mAuthorization = authorization;
            mname = name;
            mmail = mail;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doAddSponsor(mId, mAuthorization, mname,
                    mmail);
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

                        if(jsonObj.getString("message").equals("Email Invalid")){

                            email.setError("Invalid Email");
                        }else {
                            name.setText("");
                            email.setText("");
                            CustomToast.info(getApplicationContext(), "Sponsor Already Exist").show();
                        }

                    }else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        name.setText("");
                        email.setText("");
                        CustomToast.success(getApplicationContext(),"Sponsor Added Successfully").show();
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
        Intent intent = new Intent(AddVisaSponsorActivity.this,DashboardActivity.class);
        intent.putExtra("PAGE","ADDSPONSOR");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
