package com.cutesys.sponsormasterfullversionnew;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.RippleView;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SpinnerAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Kris on 12/12/2016.
 */
public class AddWithOneSpinnerActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    EditText state_name;
    RippleView done;
    Spinner spinner;
    ImageView progressrestart;
    TextView spinnererror;

    ArrayList<ListItem> dataItem;
    int start = 0;
    String mSpinnerItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_state_spinner);
        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
        InitIdView();
    }

    private void InitIdView() {
        dataItem = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.eventtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressrestart = (ImageView) findViewById(R.id.progressrestart);
        progressrestart.setOnClickListener(this);
        state_name = (EditText) findViewById(R.id.state_name);
        spinnererror = (TextView) findViewById(R.id.spinnererror);
        spinnererror.setVisibility(View.GONE);
        spinner = (Spinner)findViewById(R.id.spinner);

        done = (RippleView) findViewById(R.id.done);
        done.setOnClickListener(this);



        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadCompyListInitiate mLoadCompyListInitiate =
                    new LoadCompyListInitiate(sPreferences.getString("ID", ""),
                            sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start));
            mLoadCompyListInitiate.execute((Void) null);
        }else {
           // new SnackBar(AddWithOneSpinnerActivity.this, R.string.internet_error).show();
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

    public void addEvents(){

        if (state_name.getText().toString().trim().equals("")){
            state_name.setError("Field cannot be blank");
            spinnererror.setVisibility(View.GONE);
        }  else if (mSpinnerItem.toString().trim().equals("")){
            spinnererror.setVisibility(View.VISIBLE);
        } else {
            spinnererror.setVisibility(View.GONE);
            Config mConfig = new Config(getApplicationContext());

            if(mConfig.isOnline(getApplicationContext())){
                LoadAddEvents mLoadAddEvents = new LoadAddEvents(sPreferences.getString("ID", ""),
                        sPreferences.getString("AUTHORIZATION", ""),
                        mSpinnerItem.replaceAll(" ",""),
                        state_name.getText().toString().trim());
                mLoadAddEvents.execute((Void) null);
            }else {
                CustomToast.info(getApplicationContext(),"Error").show();
            }
        }
    }

    @Override
    public void onClick(View view) {

        int buttonId = view.getId();
        switch (buttonId){
            case R.id.done:
                addEvents();
                break;
            case R.id.progressrestart:
                Config mConfig = new Config(getApplicationContext());
                if(mConfig.isOnline(getApplicationContext())){
                    LoadCompyListInitiate mLoadCompyListInitiate =
                            new LoadCompyListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start));
                    mLoadCompyListInitiate.execute((Void) null);
                }else {
                    CustomToast.info(getApplicationContext(),"Error").show();
                }
                break;
        }
    }

    public class LoadCompyListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        ProgressDialog progressBar;
        private String mId, mAuthorization, mStart;

        LoadCompyListInitiate(String id, String authorization, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mSpinnerItem = "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(AddWithOneSpinnerActivity.this);
            progressBar.setCancelable(false);
            progressBar.setMessage("Please Wait");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.dorecruitmentcountryList(mId, mAuthorization,mStart);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            progressBar.dismiss();
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        JSONArray feedArray = jsonObj.getJSONArray("country_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            item.set_id(feedObj.getString("country_id"));
                            item.set_name(feedObj.getString("country_name"));
                            dataItem.add(item);
                        }
                        //start = dataItem.size();
                        SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter(getApplicationContext(),
                                dataItem);
                        spinner.setAdapter(mSpinnerAdapter);

                    }else {
                        CustomToast.info(getApplicationContext(),"Load Error").show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

                CustomToast.info(getApplicationContext(),"Error").show();
            }
        }
    }

    public class LoadAddEvents extends AsyncTask<Void, StringBuilder, StringBuilder> {

        ProgressDialog progressBar;
        private String mId, mAuthorization, mCountry,mState;

        LoadAddEvents(String id, String authorization,
                      String Country, String State) {
            mId = id;
            mAuthorization = authorization;
            mCountry = Country;
            mState = State;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar = new ProgressDialog(AddWithOneSpinnerActivity.this);
            progressBar.setCancelable(false);
            progressBar.setMessage("Please Wait");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doAddState(mId, mAuthorization,
                    mCountry,mState);
            System.out.println("xxxxxxxxState"+mState+"yyyyyyyyyCountry"+mCountry);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            Log.d("2222222222222222",result.toString());
            progressBar.dismiss();
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        CustomToast.info(getApplicationContext(),"Country sucessfully added").show();

                        state_name.setText("");

                    } else {
                        if (jsonObj.getString("message").equals("Event Already Exist")) {
                            CustomToast.info(getApplicationContext(),"Country Already Exist").show();
                        } else {
                            CustomToast.info(getApplicationContext(),"Country Already Exist").show();
                        }
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
                CustomToast.info(getApplicationContext(),"Error").show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(AddWithOneSpinnerActivity.this,DashboardA.class);
        intent.putExtra("PAGE","");
        startActivity(intent);overridePendingTransition(R.anim.activity_close_translate,
                R.anim.activity_open_scale);
        finish();*/
    }
}
