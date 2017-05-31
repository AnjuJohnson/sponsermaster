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
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.RoomSpinner;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.camproomlist;
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

public class add_camproom extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sPreferences;

    ArrayList<camproomlist> dataItem;
    String mSpinnerItem = "";

    private Spinner spinner_1;
    private ImageView progressrestart;
    private TextView spinnererror;
    private MaterialEditText category;
    private ImageView done ,close;
    private TextView mTitle;
    private AVLoadingIndicatorView loading, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_camproom);

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
        spinner_1 = (Spinner)findViewById(R.id.spinner_1);

        loading.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        mTitle.setText("Add Camp Room");

        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadCampRoomListInitiate mLoadCampRoomListInitiate = new LoadCampRoomListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
            mLoadCampRoomListInitiate.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).getcamp_id();
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
                    category.getText().toString().trim());

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
                    LoadCampRoomListInitiate mLoadCampRoomListInitiate =
                            new  LoadCampRoomListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(0));
                    mLoadCampRoomListInitiate.execute((Void) null);
                }else {
                    CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                }
                break;

        }
    }

    public class  LoadCampRoomListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;

        LoadCampRoomListInitiate(String id, String authorization, String Start) {
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
            StringBuilder result = httpOperations.docamproomList(mId, mAuthorization,mStart);
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

                        JSONArray feedArray = jsonObj.getJSONArray("camp_room_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            camproomlist item = new camproomlist();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if ((!feedObj.getString("camp_name").equals(""))
                                    && (!feedObj.getString("camp_name").equals("null"))) {


                                if (feedObj.getString("camp_room_id").trim().equals("")) {
                                    item.setcamp_room_id("None");
                                } else {
                                    item.setcamp_room_id((feedObj.getString("camp_room_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_id").trim().equals("")) {
                                    item.setcamp_id("None");
                                } else {
                                    item.setcamp_id((feedObj.getString("camp_id")
                                            .toLowerCase().trim()));
                                }


                                if (feedObj.getString("camp_name").trim().equals("")) {
                                    item.setcamp_name("None");
                                } else {
                                    item.setcamp_name(StringUtils.capitalize(feedObj.getString("camp_name")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("camp_room").trim().equals("")) {
                                    item.setcamp_room("None");
                                } else {
                                    item.setcamp_room(StringUtils.capitalize(feedObj.getString("camp_room")
                                            .toLowerCase().trim()));
                                }

                                dataItem.add(item);
                            }
                        }
                        RoomSpinner mSpinnerAdapter = new RoomSpinner(getApplicationContext(),
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

        private String mId, mAuthorization,mcamp_id,
                mcamp_room;

        LoadAddEvents(String id, String authorization,
                      String camp_id,String camp_room) {
            mId = id;
            mAuthorization = authorization;
            mcamp_id=camp_id;
            mcamp_room=camp_room;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doAddCampRoom(mId, mAuthorization,
                    mcamp_id,mcamp_room);
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
                        CustomToast.info(getApplicationContext(), " Already exist").show();

                    } else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        category.setText("");
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
        Intent intent = new Intent(add_camproom.this,DashboardActivity.class);
        intent.putExtra("PAGE","CAMP ROOM LIST");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}