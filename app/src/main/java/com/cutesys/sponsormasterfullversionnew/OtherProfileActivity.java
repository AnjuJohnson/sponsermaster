package com.cutesys.sponsormasterfullversionnew;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.Button.FloatingActionMenu;
import com.cutesys.sponsermasterlibrary.CircularImageView;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ProfileItem;
import com.cutesys.sponsormasterfullversionnew.Subclasses.AdvancedChildFragment;
import com.cutesys.sponsormasterfullversionnew.Subclasses.BankDocDetailsChild;
import com.cutesys.sponsormasterfullversionnew.Subclasses.DocumentChildFragment;
import com.cutesys.sponsormasterfullversionnew.Subclasses.ProfileChildFragment;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kris on 3/8/2017.
 */

public class OtherProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private static String[] PERMISSIONS_PHONECALL = {Manifest.permission.CALL_PHONE};

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private CollapsingToolbarLayout collapse_toolbar;
    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager pf_pager;
    private TextView error_label_retry, empty_label_retry, name;
    private CircularImageView profile;
    private FloatingActionMenu menu;
    private FloatingActionButton call,mail;

    public Handler sendHandler;
    private Handler mHandler;
    private Intent intent;

    private String Status, data_id, mName;
    public static ArrayList<ProfileItem> dataItem;
    private String mEmailid = "", mContactno = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherprofile_activity);

        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);

        intent = getIntent();
        mName = intent.getExtras().getString("NAME");
        Status = intent.getExtras().getString("STATUS");
        data_id = intent.getExtras().getString("ID");

        mHandler = new Handler();
        sendHandler = new Handler();
        InitIdView();
    }

    private void InitIdView() {

        switcher = new Switcher.Builder(getApplicationContext())
                .addContentView(findViewById(R.id.ViewPager))
                .addErrorView(findViewById(R.id.error_view))
                .addProgressView(findViewById(R.id.progress_view))
                .setErrorLabel((TextView) findViewById(R.id.error_label))
                .setEmptyLabel((TextView) findViewById(R.id.empty_label))
                .addEmptyView(findViewById(R.id.empty_view))
                .build();

        profile = ((CircularImageView) findViewById(R.id.profile));
        name = ((TextView) findViewById(R.id.name));
        name.setText(mName);

        menu = (FloatingActionMenu) findViewById(R.id.menu);
        call = (FloatingActionButton) findViewById(R.id.call);
        mail = (FloatingActionButton) findViewById(R.id.mail);
        mail.setOnClickListener(this);
        call.setOnClickListener(this);

        if ((Status.equals("vehicle")) || (Status.equals("flipvehicle"))) {
            menu.setVisibility(View.GONE);

        }else  if ((Status.equals("visa")) || (Status.equals("new"))
                || (Status.equals("issued")) || (Status.equals("enter"))
                || (Status.equals("notenter")) || (Status.equals("report"))) {
            menu.setVisibility(View.GONE);

        } else {
            menu.setVisibility(View.VISIBLE);
        }

        tablayout = (TabLayout) findViewById(R.id.tablayout);
        pf_pager = (ViewPager) findViewById(R.id.ViewPager);

        error_label_retry = ((TextView) findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapse_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapse_toolbar.setCollapsedTitleTextColor(Color.WHITE);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapse_toolbar.setTitle(mName);
                    isShow = true;
                } else if(isShow) {
                    collapse_toolbar.setTitle("");
                    isShow = false;
                }
            }
        });

        if ((Status.equals("vehicle")) || (Status.equals("flipvehicle"))) {
            setupViewPagerother(pf_pager);

        }
        else if ((Status.equals("company"))) {
            setupViewPagercompany(pf_pager);
        }
        else if ((Status.equals("candidate")) ) {
            setupViewPagercandidate(pf_pager);

        }else  if ((Status.equals("visa")) || (Status.equals("new"))
                || (Status.equals("issued")) || (Status.equals("enter"))
                || (Status.equals("notenter")) || (Status.equals("report"))) {
            setupViewPagerother(pf_pager);
        } else {
            setupViewPager(pf_pager);
        }
        tablayout.setupWithViewPager(pf_pager);

        dataItem = new ArrayList<>();
        InitGetData(false);
    }

    private void setupViewPager(ViewPager viewPager) {
        ProfileViewpagerAdapter pf_adapter = new ProfileViewpagerAdapter(getApplicationContext(),
                getSupportFragmentManager());
        pf_pager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pf_adapter);
    }
    private void setupViewPagerother(ViewPager viewPager) {
        ProfileViewpagerOtherAdapter pf_adapter = new ProfileViewpagerOtherAdapter(getApplicationContext(),
                getSupportFragmentManager());
        pf_pager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pf_adapter);
    }
    private void setupViewPagercompany(ViewPager viewPager) {
        ProfileViewpagercompanyAdapter pf_adapter = new ProfileViewpagercompanyAdapter(getApplicationContext(),
                getSupportFragmentManager());
        pf_pager.setOffscreenPageLimit(6);
        viewPager.setAdapter(pf_adapter);
    }
    private void setupViewPagercandidate(ViewPager viewPager) {
        ProfileViewpagercandidateAdapter pf_adapter = new ProfileViewpagercandidateAdapter(getApplicationContext(),
                getSupportFragmentManager());
        pf_pager.setOffscreenPageLimit(5);
        viewPager.setAdapter(pf_adapter);
    }
    public void InitGetData(boolean temp){
        Config mConfig = new Config(getApplicationContext());
        if(mConfig.isOnline(getApplicationContext())){
            LoadDetailsInitiate mLoadDetailsInitiate = new LoadDetailsInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), data_id);
            mLoadDetailsInitiate.execute((Void) null);
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
            case R.id.call:

                if(mContactno.toString().trim().equals("None")){
                    CustomToast.info(getApplicationContext(),"Invalid Phone Number").show();

                } else if(mContactno.toString().trim().equals("")){
                    CustomToast.info(getApplicationContext(),"Invalid Phone Number").show();

                } else {
                    Action_Call(mContactno.toString().trim());
                }
                break;
            case R.id.mail:
                if(mEmailid.toString().trim().equals("None")){
                    CustomToast.info(getApplicationContext(),"Invalid Mail ID").show();

                } else if(mEmailid.toString().trim().equals("")){
                    CustomToast.info(getApplicationContext(),"Invalid Mail ID").show();

                } else {
                    Action_Mail(mEmailid.toString().trim());

                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackActions();
            }
        });
    }

    public class LoadDetailsInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStatusID;
        private boolean mStatus;

        LoadDetailsInitiate(String id, String authorization, String StatusID) {
            mId = id;
            mAuthorization = authorization;
            mStatusID = StatusID;
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

            if ((Status.equals("company"))) {
                result = httpOperations.doCOMAPNY_DETAILS(mId, mAuthorization, mStatusID);

            }
            else if ((Status.equals("candidate"))) {
                result = httpOperations.doCANDIDATE_DETAILS(mId, mAuthorization, mStatusID);

            }else if ((Status.equals("employee")) || (Status.equals("flipemployee"))
                    || (Status.equals("flipcompany"))) {
                result = httpOperations.doEMPLOYEE_DETAILS(mId, mAuthorization, mStatusID);

            } else if ((Status.equals("vehicle")) || (Status.equals("flipvehicle"))) {
                result = httpOperations.doVEHICLE_DETAILS(mId, mAuthorization, mStatusID);

            }else  if ((Status.equals("visa")) || (Status.equals("new"))
                    || (Status.equals("issued")) || (Status.equals("enter"))
                    || (Status.equals("notenter")) || (Status.equals("report"))) {
                result = httpOperations.doVISA_DETAILS(mId, mAuthorization, mStatusID);
            }
//            if ((Status.equals("medicalclearedlist"))) {
//                result = httpOperations.doCANDIDATE_DETAILS(mId, mAuthorization, mStatusID);
//
//            }
//            if ((Status.equals("agent"))) {
//                result = httpOperations.doAgentProfile(mId, mAuthorization, mStatusID);
//
//            }
            System.out.println("yyyyyyyy"+result);
            return result;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            if(mStatus == true){

            }
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();
                        if ((Status.equals("company"))) {

                            JSONArray companyArray = jsonObj.getJSONArray("company_details");
                            JSONArray companydocArray = jsonObj.getJSONArray("companyDocuments");

                            if ((!jsonObj.getString("company_logo")
                                    .replaceAll(" ", "%20").equals(""))
                                    && (!jsonObj.getString("company_logo")
                                    .replaceAll(" ", "%20").equals("null"))) {

                                Picasso.with(getApplicationContext())
                                        .load(jsonObj.getString("company_logo")
                                                .replaceAll(" ", "%20"))
                                        .placeholder(R.drawable.profile)
                                        .error(R.drawable.profile)
                                        .into(profile);
                            } else {
                                profile.setImageResource(R.drawable.profile);
                            }
                            String[] profiletitle = new String[10];
                            String[] profilesubtitle = new String[10];
                            String[] advtitle = new String[6];
                            String[] advsubtitle = new String[6];

                            for (int i = 0; i < companyArray.length(); i++) {

                                JSONObject companyArrayObj = (JSONObject) companyArray.get(i);

                                profiletitle[0] = "Company ID";
                                profiletitle[9] = "About";
                                profiletitle[1] = "Owner";
                                profiletitle[2] = "Address Line 1";
                                profiletitle[3] = "Address Line 2";
                                profiletitle[4] = "Area";
                                profiletitle[5] = "City";
                                profiletitle[6] = "Email";
                                profiletitle[7] = "Phone";
                                profiletitle[8] = "Fax";

                                advtitle[0] = "Category";
                                advtitle[1] = "Door";
                                advtitle[2] = "Region";
                                advtitle[3] = "Country";
                                advtitle[4] = "Postbox";
                                advtitle[5] = "Sponsor Fee";
                                if (companyArrayObj.getString("company_pid").equals("")) {
                                    profilesubtitle[0] = "None";
                                } else {
                                    profilesubtitle[0] = companyArrayObj.getString("company_pid");
                                }
                                if (companyArrayObj.getString("company_about").equals("")) {
                                    profilesubtitle[9] = "None";
                                } else {
                                    profilesubtitle[9] = companyArrayObj.getString("company_about");
                                }
                                if (companyArrayObj.getString("company_owner").equals("")) {
                                    profilesubtitle[1] = "None";
                                } else {
                                    profilesubtitle[1] = companyArrayObj.getString("company_owner");
                                }
                                if (companyArrayObj.getString("company_address1").equals("")) {
                                    profilesubtitle[2] = "None";
                                } else {
                                    profilesubtitle[2] = companyArrayObj.getString("company_address1");
                                }
                                if (companyArrayObj.getString("company_address2").equals("")) {
                                    profilesubtitle[3] = "None";
                                } else {
                                    profilesubtitle[3] = companyArrayObj.getString("company_address2");
                                }
                                if (companyArrayObj.getString("company_area").equals("")) {
                                    profilesubtitle[4] = "None";
                                } else {
                                    profilesubtitle[4] = companyArrayObj.getString("company_area");
                                }
                                if (companyArrayObj.getString("company_city").equals("")) {
                                    profilesubtitle[5] = "None";
                                } else {
                                    profilesubtitle[5] = companyArrayObj.getString("company_city");
                                }
                                if (companyArrayObj.getString("company_email").equals("")) {
                                    profilesubtitle[6] = "None";
                                    mEmailid = "None";
                                } else {
                                    profilesubtitle[6] = companyArrayObj.getString("company_email");
                                    mEmailid = companyArrayObj.getString("company_email");
                                }
                                if (companyArrayObj.getString("company_phone").equals("")) {
                                    profilesubtitle[7] = "None";
                                    mContactno = "None";
                                } else {
                                    profilesubtitle[7] = companyArrayObj.getString("company_phone");
                                    mContactno = companyArrayObj.getString("company_phone");
                                }
                                if (companyArrayObj.getString("company_fax").equals("")) {
                                    profilesubtitle[8] = "None";
                                } else {
                                    profilesubtitle[8] = companyArrayObj.getString("company_fax");
                                }
                                if (companyArrayObj.getString("company_category").equals("")) {
                                    advsubtitle[0] = "None";
                                } else {
                                    advsubtitle[0] = companyArrayObj.getString("company_category");
                                }
                                if (companyArrayObj.getString("company_door").equals("")) {
                                    advsubtitle[1] = "None";
                                } else {
                                    advsubtitle[1] = companyArrayObj.getString("company_door");
                                }
                                if (companyArrayObj.getString("company_region").equals("")) {
                                    advsubtitle[2] = "None";
                                } else {
                                    advsubtitle[2] = companyArrayObj.getString("company_region");
                                }
                                if (companyArrayObj.getString("company_country").equals("")) {
                                    advsubtitle[3] = "None";
                                } else {
                                    advsubtitle[3] = companyArrayObj.getString("company_country");
                                }
                                if (companyArrayObj.getString("company_postbox").equals("")) {
                                    advsubtitle[4] = "None";
                                } else {
                                    advsubtitle[4] = companyArrayObj.getString("company_postbox");
                                }
                                if (companyArrayObj.getString("company_sponsor_fee").equals("")) {
                                    advsubtitle[5] = "None";
                                } else {
                                    advsubtitle[5] = companyArrayObj.getString("company_sponsor_fee");
                                }
                            }

                            String[] docdata = new String[companydocArray.length()];
                            String[] docdatadate = new String[companydocArray.length()];

                            for (int j = 0; j < companydocArray.length(); j++) {

                                JSONObject companydocArrayObj = (JSONObject) companydocArray.get(j);
                                if (companydocArrayObj.getString("file_title").equals("")) {
                                    docdata[j] = "None";
                                } else {
                                    docdata[j] = companydocArrayObj.getString("file_title");
                                }
                                if (companydocArrayObj.getString("file_path").equals("")) {
                                    docdatadate[j] = "-";
                                } else {
                                    docdatadate[j] = companydocArrayObj.getString("file_path").replaceAll(" ", "%20");
                                }
                            }
                            JSONArray BANKdataarray = jsonObj.getJSONArray("bank_data");

                            String[] BANKNAME= new String[BANKdataarray.length()];
                            String[] BANKCODE= new String[BANKdataarray.length()];
                            String[] BANKACCOUNT= new String[BANKdataarray.length()];
                            String[] BANKIBNNO= new String[BANKdataarray.length()];




                            for (int k = 0; k < BANKdataarray.length(); k++) {

                                JSONObject BANKdatadetails = (JSONObject) BANKdataarray.get(k);
                                BANKNAME[k] = BANKdatadetails.getString("bank_name");
                                BANKCODE[k] = BANKdatadetails.getString("bank_code");
                                BANKACCOUNT[k] = BANKdatadetails.getString("company_account_no");
                                BANKIBNNO[k] = BANKdatadetails.getString("company_iban_no");



                            }


                            JSONArray docdataarray = jsonObj.getJSONArray("company_doc_data");

                            String[] title = new String[docdataarray.length()];
                            String[] data = new String[docdataarray.length()];
                            String[] owner = new String[docdataarray.length()];
                            String[] start = new String[docdataarray.length()];
                            String[] end = new String[docdataarray.length()];

                            for (int k = 0; k < docdataarray.length(); k++) {

                                JSONObject docdatadetails = (JSONObject) docdataarray.get(k);
                                title[k] = docdatadetails.getString("doc_title");
                                data[k] = docdatadetails.getString("doc_data");
                                owner[k] = docdatadetails.getString("doc_owner");
                                start[k] = docdatadetails.getString("doc_start_date");
                                end[k] = docdatadetails.getString("doc_end_date");
                            }

                            ProfileItem mitem = new ProfileItem();
                            mitem.setProfile_title(profiletitle);
                            mitem.setProfile_subtitle(profilesubtitle);
                            mitem.setAdvanced_title(advtitle);
                            mitem.setAdvanced_subtitile(advsubtitle);
                            mitem.setfile_title(docdata);
                            mitem.setfile_path(docdatadate);
                            mitem.setDatatitle(title);
                            mitem.setDatadls(data);
                            mitem.setDataowner(owner);
                            mitem.setDatastart(start);
                            mitem.setDataend(end);
                            mitem.set_BANKNAME(BANKNAME);
                            mitem.set_BANKCODE(BANKCODE);
                            mitem.set_BANKACCOUNT(BANKACCOUNT);
                            mitem.set_BANKIBNNO(BANKIBNNO);
                            dataItem.add(mitem);

                            Intent intent = new Intent("COMPANYGET");
                            sendBroadcast(intent);



                        }

                        else  if ((Status.equals("candidate"))) {

                            JSONArray companyArray = jsonObj.getJSONArray("candidate_details");
                            JSONArray companydocArray = jsonObj.getJSONArray("candidate_Documents");

                            if((!jsonObj.getString("candidate_profile_pic")
                                    .replaceAll(" ", "%20").equals(""))
                                    && (!jsonObj.getString("candidate_profile_pic")
                                    .replaceAll(" ", "%20").equals("null"))) {

                                Picasso.with(getApplicationContext())
                                        .load(jsonObj.getString("candidate_profile_pic")
                                                .replaceAll(" ", "%20"))
                                        .placeholder(R.drawable.profile)
                                        .error(R.drawable.profile)
                                        .into(profile);
                            } else {
                                profile.setImageResource(R.drawable.profile);
                            }

                            String[] profiletitle = new String[12];
                            String[] profilesubtitle = new String[12];

                            for (int i = 0; i < companyArray.length(); i++) {

                                JSONObject companyArrayObj = (JSONObject) companyArray.get(i);

                                profiletitle[0] = "Candidate ID";
                                profiletitle[1] = "Gender";
                                profiletitle[2] = "Date of Birth";
                                profiletitle[3] = "Nationality";
                                profiletitle[4] = "Address";
                                profiletitle[5] = "City";
                                profiletitle[6] = "Email";
                                profiletitle[7] = "Zipcode";
                                profiletitle[8] = "Phone";
                                profiletitle[9] = "Applied for Interview";
                                profiletitle[10] = "Applied for Job position";
                                profiletitle[11] = "Applied for Job Category";


                                if (companyArrayObj.getString("candidate_code").equals("")) {
                                    profilesubtitle[0] = "None";
                                } else {
                                    profilesubtitle[0] = companyArrayObj.getString("candidate_code");
                                }
                                if (companyArrayObj.getString("candidate_gender").equals("")) {
                                    profilesubtitle[1] = "None";
                                } else {
                                    profilesubtitle[1] = companyArrayObj.getString("candidate_gender");
                                }
                                if (companyArrayObj.getString("candidate_dob").equals("")) {
                                    profilesubtitle[2] = "None";
                                } else {
                                    profilesubtitle[2] = companyArrayObj.getString("candidate_dob");
                                }
                                if (companyArrayObj.getString("candidate_nationality").equals("")) {
                                    profilesubtitle[3] = "None";
                                } else {
                                    profilesubtitle[3] = companyArrayObj.getString("candidate_nationality");
                                }
                                if (companyArrayObj.getString("candidate_doorno").equals("")) {
                                    profilesubtitle[4] = "None";
                                } else {
                                    profilesubtitle[4] = companyArrayObj.getString("candidate_doorno");
                                }
                                if (companyArrayObj.getString("candidate_city").equals("")) {
                                    profilesubtitle[5] = "None";
                                } else {
                                    profilesubtitle[5] = companyArrayObj.getString("candidate_city");
                                }
                                if (companyArrayObj.getString("candidate_email").equals("")) {
                                    profilesubtitle[6] = "None";
                                } else {
                                    profilesubtitle[6] = companyArrayObj.getString("candidate_email");
                                }
                                if (companyArrayObj.getString("candidate_zipcode").equals("")) {
                                    profilesubtitle[7] = "None";
                                } else {
                                    profilesubtitle[7] = companyArrayObj.getString("candidate_zipcode");
                                }
                                if (companyArrayObj.getString("candidate_phone").equals("")) {
                                    profilesubtitle[8] = "None";

                                } else {
                                    profilesubtitle[8] = companyArrayObj.getString("candidate_phone");
                                    //mEmailid = companyArrayObj.getString("company_email");
                                }
                                if (companyArrayObj.getString("interview_name").equals("")) {
                                    profilesubtitle[9] = "None";
                                    //mContactno = "None";
                                } else {
                                    profilesubtitle[9] = companyArrayObj.getString("interview_name");
                                    //mContactno = companyArrayObj.getString("company_phone");
                                }
                                if (companyArrayObj.getString("application_job_position").equals("")) {
                                    profilesubtitle[10] = "None";
                                } else {
                                    profilesubtitle[10] = companyArrayObj.getString("application_job_position");
                                }
                                if (companyArrayObj.getString("application_job_category").equals("")) {
                                    profilesubtitle[11] = "None";
                                } else {
                                    profilesubtitle[11] = companyArrayObj.getString("application_job_category");
                                }

                            }

                            String[] docdata = new String[companydocArray.length()];
                            String[] docdatadate = new String[companydocArray.length()];

                            for (int j = 0; j < companydocArray.length(); j++) {

                                JSONObject companydocArrayObj = (JSONObject) companydocArray.get(j);
                                if (companydocArrayObj.getString("file_name").equals("")) {
                                    docdata[j] = "None";
                                } else {
                                    docdata[j] = companydocArrayObj.getString("file_name");
                                }
                                if (companydocArrayObj.getString("file_path").equals("")) {
                                    docdatadate[j] = "-";
                                } else {
                                    docdatadate[j] = companydocArrayObj.getString("file_path").replaceAll(" ", "%20");
                                }
                            }

//                            String[] title = new String[6];
//                            String[] data = new String[6];
//                            String[] start = new String[6];
//                            String[] end = new String[6];

                            JSONArray docdataarray = jsonObj.getJSONArray("candidate_doc_data");



                            String[] title = new String[docdataarray.length()];
                            // String[] data = new String[docdataarray.length()];
                            //String[] owner = new String[docdataarray.length()];
                            String[] start = new String[docdataarray.length()];
                            String[] end = new String[docdataarray.length()];

                            for (int k = 0; k < docdataarray.length(); k++) {

                                JSONObject docdatadetails = (JSONObject) docdataarray.get(k);
                                title[k] = docdatadetails.getString("doc_title");
                                // data[k] = docdatadetails.getString("document_data");
                                start[k] = docdatadetails.getString("doc_start_date");
                                end[k] = docdatadetails.getString("doc_end_date");
                            }

                            JSONArray docdataarray1 = jsonObj.getJSONArray("candidate_qualification_data");



                            String[] qualification = new String[docdataarray1.length()];
                            String[] qualistatus = new String[docdataarray1.length()];

                            for (int k = 0; k < docdataarray1.length(); k++) {

                                JSONObject docdatadetails = (JSONObject) docdataarray1.get(k);
                                qualification[k] = docdatadetails.getString("qualification_name");
                                // data[k] = docdatadetails.getString("document_data");
                                qualistatus[k] = docdatadetails.getString("qualification_status");
                                // end[k] = docdatadetails.getString("doc_end_date");
                            }

                            JSONArray docdataarray2 = jsonObj.getJSONArray("candidate_exp_data");



                            String[] expcompany = new String[docdataarray2.length()];
                            // String[] data = new String[docdataarray.length()];
                            //String[] owner = new String[docdataarray.length()];
                            String[] expdesign = new String[docdataarray2.length()];
                            String[] expfrom = new String[docdataarray2.length()];
                            String[] expto = new String[docdataarray2.length()];
                            //String[] end = new String[docdataarray1.length()];

                            for (int k = 0; k < docdataarray2.length(); k++) {

                                JSONObject docdatadetails = (JSONObject) docdataarray2.get(k);
                                expcompany[k] = docdatadetails.getString("experience_company");
                                expdesign[k] = docdatadetails.getString("experience_designation");
                                expfrom[k] = docdatadetails.getString("experience_from");
                                expto[k] = docdatadetails.getString("experience_to");
                            }


                            ProfileItem mitem = new ProfileItem();
                            mitem.setProfile_title(profiletitle);
                            mitem.setProfile_subtitle(profilesubtitle);
                            // mitem.setAdvanced_title(advtitle);
                            //mitem.setAdvanced_subtitile(advsubtitle);
                            mitem.setfile_title(docdata);
                            mitem.setfile_path(docdatadate);
                            mitem.setDatatitle(title);
                            //  mitem.setDatadls(data);
                            mitem.setfile_companyname(expcompany);
                            mitem.setfile_designation(expdesign);
                            mitem.setfile_from(expfrom);
                            mitem.setfile_to(expto);
                            mitem.setfile_qualification(qualification);
                            mitem.setfile_qstatus(qualistatus);
                            mitem.setDatastart(start);
                            mitem.setDataend(end);

                            dataItem.add(mitem);

                            Intent intent = new Intent("CANDIDATEGET");
                            sendBroadcast(intent);

                        }

                        else if ((Status.equals("employee")) || (Status.equals("flipemployee"))
                                || (Status.equals("flipcompany")) || (Status.equals("flipbranch"))) {
                            JSONArray companyArray = jsonObj.getJSONArray("employee_list");
                            JSONArray companydocArray = jsonObj.getJSONArray("employeeDocuments");

                            if((!jsonObj.getString("employee_profile_pic")
                                    .replaceAll(" ", "%20").equals(""))
                                    && (!jsonObj.getString("employee_profile_pic")
                                    .replaceAll(" ", "%20").equals("null"))) {

                                Picasso.with(getApplicationContext())
                                        .load(jsonObj.getString("employee_profile_pic")
                                                .replaceAll(" ", "%20"))
                                        .placeholder(R.drawable.profile)
                                        .error(R.drawable.profile)
                                        .into(profile);
                            } else {
                                profile.setImageResource(R.drawable.profile);
                            }

                            String[] profiletitle = new String[11];
                            String[] profilesubtitle = new String[11];
                            String[] advtitle = new String[11];
                            String[] advsubtitle = new String[11];

                            for (int i = 0; i < companyArray.length(); i++) {

                                JSONObject companyArrayObj = (JSONObject) companyArray.get(i);

                                profiletitle[0] = "Company";
                                profiletitle[1] = "Joining Date";
                                profiletitle[2] = "Permanent Address";
                                profiletitle[3] = "Residence Address";
                                profiletitle[4] = "Gender";
                                profiletitle[5] = "Date Of Birth";
                                profiletitle[6] = "Designation";
                                profiletitle[7] = "Email";
                                profiletitle[8] = "City";
                                profiletitle[9] = "State";
                                profiletitle[10] = "Nationality";

                                advtitle[0] = "Phone";
                                advtitle[1] = "Emergency Contact";
                                advtitle[2] = "Employee Fee";
                                advtitle[3] = "Salary";
                                advtitle[4] = "Visa type";
                                advtitle[5] = "Medical Category";
                                advtitle[6] = "Remarks";
                                advtitle[7] = "Bank";
                                advtitle[8] = "Bank Code";
                                advtitle[9] = "Account No";
                                advtitle[10] = "IBAN No";

                                if (companyArrayObj.getString("company").equals("")){
                                    profilesubtitle[0] = "None";
                                } else {
                                    profilesubtitle[0] = companyArrayObj.getString("company");
                                }
                                if (companyArrayObj.getString("employee_joining_date").equals("")){
                                    profilesubtitle[1] = "None";
                                } else {
                                    profilesubtitle[1] = companyArrayObj.getString("employee_joining_date");
                                }
                                if (companyArrayObj.getString("employee_peraddress1").equals("")){
                                    profilesubtitle[2] = "None";
                                } else {
                                    profilesubtitle[2] = companyArrayObj.getString("employee_peraddress1");
                                }
                                if (companyArrayObj.getString("employee_resiaddress1").equals("")){
                                    profilesubtitle[3] = "None";
                                } else {
                                    profilesubtitle[3] = companyArrayObj.getString("employee_resiaddress1");
                                }
                                if (companyArrayObj.getString("employee_gender").equals("")){
                                    profilesubtitle[4] = "None";
                                } else {
                                    profilesubtitle[4] = companyArrayObj.getString("employee_gender");
                                }
                                if (companyArrayObj.getString("employee_dob").equals("")){
                                    profilesubtitle[5] = "None";
                                } else {
                                    profilesubtitle[5] = companyArrayObj.getString("employee_dob");
                                }
                                if (companyArrayObj.getString("designation").equals("")){
                                    profilesubtitle[6] = "None";
                                } else {
                                    profilesubtitle[6] = companyArrayObj.getString("designation");
                                }
                                if (companyArrayObj.getString("employee_email").equals("")){
                                    profilesubtitle[7] = "None";
                                    mEmailid = "None";
                                } else {
                                    profilesubtitle[7] = companyArrayObj.getString("employee_email");
                                    mEmailid = companyArrayObj.getString("employee_email");
                                }
                                if (companyArrayObj.getString("employee_percity").equals("")){
                                    profilesubtitle[8] = "None";
                                } else {
                                    profilesubtitle[8] = companyArrayObj.getString("employee_percity");
                                }
                                if (companyArrayObj.getString("employee_perstate").equals("")){
                                    profilesubtitle[9] = "None";
                                } else {
                                    profilesubtitle[9] = companyArrayObj.getString("employee_perstate");
                                }
                                if (companyArrayObj.getString("employee_nationality").equals("")){
                                    profilesubtitle[10] = "None";
                                } else {
                                    profilesubtitle[10] = companyArrayObj.getString("employee_nationality");
                                }

                                if (companyArrayObj.getString("employee_phone").equals("")){
                                    advsubtitle[0] = "None";
                                    mContactno = "None";
                                } else {
                                    advsubtitle[0] = companyArrayObj.getString("employee_phone");
                                    mContactno = companyArrayObj.getString("employee_phone");
                                }
                                if (companyArrayObj.getString("employee_emcontact").equals("")){
                                    advsubtitle[1] = "None";
                                } else {
                                    advsubtitle[1] = companyArrayObj.getString("employee_emcontact");
                                }
                                if (companyArrayObj.getString("employee_fee").equals("")){
                                    advsubtitle[2] = "None";
                                } else {
                                    advsubtitle[2] = companyArrayObj.getString("employee_fee");
                                }
                                if (companyArrayObj.getString("employee_salary").equals("")){
                                    advsubtitle[3] = "None";
                                } else {
                                    advsubtitle[3] = companyArrayObj.getString("employee_salary");
                                }
                                if (companyArrayObj.getString("employee_visatype").equals("")){
                                    advsubtitle[4] = "None";
                                } else {
                                    advsubtitle[4] = companyArrayObj.getString("employee_visatype");
                                }
                                if (companyArrayObj.getString("employee_medical_category").equals("")){
                                    advsubtitle[5] = "None";
                                } else {
                                    advsubtitle[5] = companyArrayObj.getString("employee_medical_category");
                                }
                                if (companyArrayObj.getString("employee_remarks").equals("")){
                                    advsubtitle[6] = "None";
                                } else {
                                    advsubtitle[6] = companyArrayObj.getString("employee_remarks");
                                }
                                if (companyArrayObj.getString("bank_name").equals("")){
                                    advsubtitle[7] = "None";
                                } else {
                                    advsubtitle[7] = companyArrayObj.getString("bank_name");
                                }
                                if (companyArrayObj.getString("bank_code").equals("")){
                                    advsubtitle[8] = "None";
                                } else {
                                    advsubtitle[8] = companyArrayObj.getString("bank_code");
                                }
                                if (companyArrayObj.getString("employee_account_no").equals("")){
                                    advsubtitle[9] = "None";
                                } else {
                                    advsubtitle[9] = companyArrayObj.getString("employee_account_no");
                                }
                                if (companyArrayObj.getString("employee_iban_no").equals("")){
                                    advsubtitle[10] = "None";
                                } else {
                                    advsubtitle[10] = companyArrayObj.getString("employee_iban_no");
                                }
                            }

                            String[] docdata = new String[companydocArray.length()];
                            String[] docdatadate = new String[companydocArray.length()];
                            for (int j = 0; j < companydocArray.length(); j++) {

                                JSONObject companydocArrayObj = (JSONObject) companydocArray.get(j);
                                if (companydocArrayObj.getString("file_title").equals("")){
                                    docdata[j] = "None";
                                } else {
                                    docdata[j] = companydocArrayObj.getString("file_title");
                                }
                                if (companydocArrayObj.getString("file_path").equals("")){
                                    docdatadate[j] = "-";
                                } else {
                                    docdatadate[j] = companydocArrayObj.getString("file_path").replaceAll(" ","%20");
                                }
                            }

                            /*String[] title = new String[6];
                            String[] data = new String[6];
                            String[] start = new String[6];
                            String[] end = new String[6];*/

                            JSONArray docdataarray = jsonObj.getJSONArray("employee_doc_data");
                            String[] title = new String[docdataarray.length()];
                            String[] data = new String[docdataarray.length()];
//String[] owner = new String[docdataarray.length()];
                            String[] start = new String[docdataarray.length()];
                            String[] end = new String[docdataarray.length()];
                            for (int k = 0; k < docdataarray.length(); k++) {

                                JSONObject docdatadetails = (JSONObject) docdataarray.get(k);
                                title[k] =  docdatadetails.getString("document_title");
                                data[k] =  docdatadetails.getString("document_data");
                                start[k] =  docdatadetails.getString("document_start_date");
                                end[k] =  docdatadetails.getString("document_end_date");
                            }

                            ProfileItem mitem = new ProfileItem();
                            mitem.setProfile_title(profiletitle);
                            mitem.setProfile_subtitle(profilesubtitle);
                            mitem.setAdvanced_title(advtitle);
                            mitem.setAdvanced_subtitile(advsubtitle);
                            mitem.setfile_title(docdata);
                            mitem.setfile_path(docdatadate);
                            mitem.setDatatitle(title);
                            mitem.setDatadls(data);
                            mitem.setDatastart(start);
                            mitem.setDataend(end);
                            dataItem.add(mitem);

                            Intent intent = new Intent("EMPLOYEEGET");
                            sendBroadcast(intent);
                        }

                        else if ((Status.equals("vehicle")) || (Status.equals("flipvehicle"))) {

                            JSONArray companyArray = jsonObj.getJSONArray("vehicle_details");
                            JSONArray companydocArray = jsonObj.getJSONArray("vehicleDocuments");

                            if((!jsonObj.getString("vehicle_pic")
                                    .replaceAll(" ", "%20").equals(""))
                                    && (!jsonObj.getString("vehicle_pic")
                                    .replaceAll(" ", "%20").equals("null"))) {

                                Picasso.with(getApplicationContext())
                                        .load(jsonObj.getString("vehicle_pic")
                                                .replaceAll(" ", "%20"))
                                        .placeholder(R.drawable.profile)
                                        .error(R.drawable.profile)
                                        .into(profile);
                            } else {
                                profile.setImageResource(R.drawable.profile);
                            }

                            String[] profiletitle = new String[9];
                            String[] profilesubtitle = new String[9];
                            String[] advtitle = new String[11];
                            String[] advsubtitle = new String[11];

                            for (int i = 0; i < companyArray.length(); i++) {

                                JSONObject companyArrayObj = (JSONObject) companyArray.get(i);

                                profiletitle[0] = "Vehicle ID";
                                profiletitle[1] = "Vehicle Number";
                                profiletitle[2] = "Model";
                                profiletitle[3] = "Company Name";
                                profiletitle[4] = "Purchase Date";
                                profiletitle[5] = "Engine Number";
                                profiletitle[6] = "Chassis Number";
                                profiletitle[7] = "Assigned Company";
                                profiletitle[8] = "Assigned Employee";

                                advtitle[0] = "Registration Number";
                                advtitle[1] = "Registration Date";
                                advtitle[2] = "Registration Expiry";
                                advtitle[3] = "Registered Owner";
                                advtitle[4] = "Owner Qatar ID";
                                advtitle[5] = "Insurance ID";
                                advtitle[6] = "Insurance Date";
                                advtitle[7] = "Insurance Expiry";
                                advtitle[8] = "Insurance Company";
                                advtitle[9] = "Insurance Type";
                                advtitle[10] = "Insurance Amount";

                                if (companyArrayObj.getString("vehicle_id").equals("")){
                                    profilesubtitle[0] = "None";
                                } else {
                                    profilesubtitle[0] = companyArrayObj.getString("vehicle_id");
                                }
                                if (companyArrayObj.getString("vehicle_number").equals("")){
                                    profilesubtitle[1] = "None";
                                } else {
                                    profilesubtitle[1] = companyArrayObj.getString("vehicle_number");
                                }
                                if (companyArrayObj.getString("model").equals("")){
                                    profilesubtitle[2] = "None";
                                } else {
                                    profilesubtitle[2] = companyArrayObj.getString("model");
                                }
                                if (companyArrayObj.getString("company_name").equals("")){
                                    profilesubtitle[3] = "None";
                                } else {
                                    profilesubtitle[3] = companyArrayObj.getString("company_name");
                                }
                                if (companyArrayObj.getString("vehicle_purchase_date").equals("")){
                                    profilesubtitle[4] = "None";
                                } else {
                                    profilesubtitle[4] = companyArrayObj.getString("vehicle_purchase_date");
                                }
                                if (companyArrayObj.getString("vehicle_engine_number").equals("")){
                                    profilesubtitle[5] = "None";
                                } else {
                                    profilesubtitle[5] = companyArrayObj.getString("vehicle_engine_number");
                                }
                                if (companyArrayObj.getString("vehicle_chassis_number").equals("")){
                                    profilesubtitle[6] = "None";
                                } else {
                                    profilesubtitle[6] = companyArrayObj.getString("vehicle_chassis_number");
                                }
                                if (companyArrayObj.getString("assigned_company").equals("")){
                                    profilesubtitle[7] = "None";
                                } else {
                                    profilesubtitle[7] = companyArrayObj.getString("assigned_company");
                                }
                                if (companyArrayObj.getString("assigned_employee").equals("")){
                                    profilesubtitle[8] = "None";
                                } else {
                                    profilesubtitle[8] = companyArrayObj.getString("assigned_employee");
                                }

                                if (companyArrayObj.getString("vehicle_registration_number").equals("")){
                                    advsubtitle[0] = "None";
                                } else {
                                    advsubtitle[0] = companyArrayObj.getString("vehicle_registration_number");
                                }
                                if (companyArrayObj.getString("vehicle_registration_date").equals("")){
                                    advsubtitle[1] = "None";
                                } else {
                                    advsubtitle[1] = companyArrayObj.getString("vehicle_registration_date");
                                }
                                if (companyArrayObj.getString("vehicle_registration_expiry").equals("")){
                                    advsubtitle[2] = "None";
                                } else {
                                    advsubtitle[2] = companyArrayObj.getString("vehicle_registration_expiry");
                                }
                                if (companyArrayObj.getString("vehicle_registered_owner").equals("")){
                                    advsubtitle[3] = "None";
                                } else {
                                    advsubtitle[3] = companyArrayObj.getString("vehicle_registered_owner");
                                }
                                if (companyArrayObj.getString("vehicle_owner_qatar_id").equals("")){
                                    advsubtitle[4] = "None";
                                } else {
                                    advsubtitle[4] = companyArrayObj.getString("vehicle_owner_qatar_id");
                                }
                                if (companyArrayObj.getString("vehicle_insurance_id").equals("")){
                                    advsubtitle[5] = "None";
                                } else {
                                    advsubtitle[5] = companyArrayObj.getString("vehicle_insurance_id");
                                }
                                if (companyArrayObj.getString("vehicle_insurance_date").equals("")){
                                    advsubtitle[6] = "None";
                                } else {
                                    advsubtitle[6] = companyArrayObj.getString("vehicle_insurance_date");
                                }
                                if (companyArrayObj.getString("vehicle_insurance_expiry").equals("")){
                                    advsubtitle[7] = "None";
                                } else {
                                    advsubtitle[7] = companyArrayObj.getString("vehicle_insurance_expiry");
                                }
                                if (companyArrayObj.getString("vehicle_insurance_company").equals("")){
                                    advsubtitle[8] = "None";
                                } else {
                                    advsubtitle[8] = companyArrayObj.getString("vehicle_insurance_company");
                                }
                                if (companyArrayObj.getString("vehicle_insurance_type").equals("")){
                                    advsubtitle[9] = "None";
                                } else {
                                    advsubtitle[9] = companyArrayObj.getString("vehicle_insurance_type");
                                }
                                if (companyArrayObj.getString("vehicle_insurance_amount").equals("")){
                                    advsubtitle[10] = "None";
                                } else {
                                    advsubtitle[10] = companyArrayObj.getString("vehicle_insurance_amount");
                                }
                            }

                            String[] docdata = new String[companydocArray.length()];
                            String[] docdatadate = new String[companydocArray.length()];
                            for (int j = 0; j < companydocArray.length(); j++) {

                                JSONObject companydocArrayObj = (JSONObject) companydocArray.get(j);
                                if (companydocArrayObj.getString("file_title").equals("")){
                                    docdata[j] = "None";
                                } else {
                                    docdata[j] = companydocArrayObj.getString("file_title");
                                }
                                if (companydocArrayObj.getString("file_path").equals("")){
                                    docdatadate[j] = "-";
                                } else {
                                    docdatadate[j] = companydocArrayObj.getString("file_path").replaceAll(" ","%20");
                                }
                            }
                            ProfileItem mitem = new ProfileItem();
                            mitem.setProfile_title(profiletitle);
                            mitem.setProfile_subtitle(profilesubtitle);
                            mitem.setAdvanced_title(advtitle);
                            mitem.setAdvanced_subtitile(advsubtitle);
                            mitem.setfile_title(docdata);
                            mitem.setfile_path(docdatadate);
                            dataItem.add(mitem);

                            Intent intent = new Intent("VEHICLEGET");
                            sendBroadcast(intent);
                        }else if ((Status.equals("visa")) || (Status.equals("new"))
                                || (Status.equals("issued")) || (Status.equals("enter"))
                                || (Status.equals("notenter")) || (Status.equals("report"))) {

                            JSONArray companyArray = jsonObj.getJSONArray("visa_details");


                            String[] profiletitle = new String[12];
                            String[] profilesubtitle = new String[12];
                            String[] advtitle = new String[13];
                            String[] advsubtitle = new String[13];
                            String[] docdata = new String[1];
                            String[] docdatadate = new String[1];

                            for (int i = 0; i < companyArray.length(); i++) {

                                JSONObject companyArrayObj = (JSONObject) companyArray.get(i);

                                if((!companyArrayObj.getString("visa_profile_picture")
                                        .replaceAll(" ", "%20").equals(""))
                                        && (!companyArrayObj.getString("visa_profile_picture")
                                        .replaceAll(" ", "%20").equals("null"))) {

                                    Picasso.with(getApplicationContext())
                                            .load(companyArrayObj.getString("visa_profile_picture")
                                                    .replaceAll(" ", "%20"))
                                            .placeholder(R.drawable.profile)
                                            .error(R.drawable.profile)
                                            .into(profile);
                                } else {
                                    profile.setImageResource(R.drawable.profile);
                                }
                                profiletitle[0] = "Type";
                                profiletitle[1] = "Category";
                                profiletitle[2] = "Passport No";
                                profiletitle[3] = "Client";
                                profiletitle[4] = "Contact";
                                profiletitle[5] = "Email";
                                profiletitle[6] = "Reference No";
                                profiletitle[7] = "Country";
                                profiletitle[8] = "Number";
                                profiletitle[9] = "Address";
                                profiletitle[10] = "Sponsor";
                                profiletitle[11] = "Status";

                                advtitle[0] = "Type Days";
                                advtitle[1] = "Application Date";
                                advtitle[2] = "Issued Date";
                                advtitle[3] = "Expiry Date";
                                advtitle[4] = "Entry Date";
                                advtitle[5] = "Renewal Date";
                                advtitle[6] = "Renewal Date 1";
                                advtitle[7] = "Renewal Date 2";
                                advtitle[8] = "Payment Date";
                                advtitle[9] = "Company Fee";
                                advtitle[10] = "Sponsor Fee";
                                advtitle[11] = "Total Amount";
                                advtitle[12] = "Advance Amount";

                                if (companyArrayObj.getString("visa_type").equals("")){
                                    profilesubtitle[0] = "None";
                                } else {
                                    profilesubtitle[0] = companyArrayObj.getString("visa_type");
                                }
                                if (companyArrayObj.getString("visa_category").equals("")){
                                    profilesubtitle[1] = "None";
                                } else {
                                    profilesubtitle[1] = companyArrayObj.getString("visa_category");
                                }
                                if (companyArrayObj.getString("visa_passport_no").equals("")){
                                    profilesubtitle[2] = "None";
                                } else {
                                    profilesubtitle[2] = companyArrayObj.getString("visa_passport_no");
                                }
                                if (companyArrayObj.getString("visa_client_name").equals("")){
                                    profilesubtitle[3] = "None";
                                } else {
                                    profilesubtitle[3] = companyArrayObj.getString("visa_client_name");
                                }
                                if (companyArrayObj.getString("visa_contact_no").equals("")){
                                    profilesubtitle[4] = "None";
                                } else {
                                    profilesubtitle[4] = companyArrayObj.getString("visa_contact_no");
                                }
                                if (companyArrayObj.getString("visa_email").equals("")){
                                    profilesubtitle[5] = "None";
                                } else {
                                    profilesubtitle[5] = companyArrayObj.getString("visa_email");
                                }
                                if (companyArrayObj.getString("visa_ref_no").equals("")){
                                    profilesubtitle[6] = "None";
                                } else {
                                    profilesubtitle[6] = companyArrayObj.getString("visa_ref_no");
                                }
                                if (companyArrayObj.getString("visa_country").equals("")){
                                    profilesubtitle[7] = "None";
                                } else {
                                    profilesubtitle[7] = companyArrayObj.getString("visa_country");
                                }
                                if (companyArrayObj.getString("visa_number").equals("")){
                                    profilesubtitle[8] = "None";
                                } else {
                                    profilesubtitle[8] = companyArrayObj.getString("visa_number");
                                }
                                if (companyArrayObj.getString("visa_address").equals("")){
                                    profilesubtitle[9] = "None";
                                } else {
                                    profilesubtitle[9] = companyArrayObj.getString("visa_address");
                                }
                                if (companyArrayObj.getString("visa_sponsor").equals("")){
                                    profilesubtitle[10] = "None";
                                } else {
                                    profilesubtitle[10] = companyArrayObj.getString("visa_sponsor");
                                }
                                if (companyArrayObj.getString("visa_status").equals("")){
                                    profilesubtitle[11] = "None";
                                } else {
                                    profilesubtitle[11] = companyArrayObj.getString("visa_status");
                                }

                                if (companyArrayObj.getString("visa_type_days").equals("")){
                                    advsubtitle[0] = "None";
                                } else {
                                    advsubtitle[0] = companyArrayObj.getString("visa_type_days");
                                }
                                if (companyArrayObj.getString("visa_application_date").equals("")){
                                    advsubtitle[1] = "None";
                                } else {
                                    advsubtitle[1] = companyArrayObj.getString("visa_application_date");
                                }
                                if (companyArrayObj.getString("visa_issued_date").equals("")){
                                    advsubtitle[2] = "None";
                                } else {
                                    advsubtitle[2] = companyArrayObj.getString("visa_issued_date");
                                }
                                if (companyArrayObj.getString("visa_expiry_date").equals("")){
                                    advsubtitle[3] = "None";
                                } else {
                                    advsubtitle[3] = companyArrayObj.getString("visa_expiry_date");
                                }
                                if (companyArrayObj.getString("visa_entry_date").equals("")){
                                    advsubtitle[4] = "None";
                                } else {
                                    advsubtitle[4] = companyArrayObj.getString("visa_entry_date");
                                }
                                if (companyArrayObj.getString("visa_renewal_date").equals("")){
                                    advsubtitle[5] = "None";
                                } else {
                                    advsubtitle[5] = companyArrayObj.getString("visa_renewal_date");
                                }
                                if (companyArrayObj.getString("visa_renewal_date1").equals("")){
                                    advsubtitle[6] = "None";
                                } else {
                                    advsubtitle[6] = companyArrayObj.getString("visa_renewal_date1");
                                }
                                if (companyArrayObj.getString("visa_renewal_date2").equals("")){
                                    advsubtitle[7] = "None";
                                } else {
                                    advsubtitle[7] = companyArrayObj.getString("visa_renewal_date2");
                                }
                                if (companyArrayObj.getString("visa_payment_date").equals("")){
                                    advsubtitle[8] = "None";
                                } else {
                                    advsubtitle[8] = companyArrayObj.getString("visa_payment_date");
                                }
                                if (companyArrayObj.getString("visa_company_fee").equals("")){
                                    advsubtitle[9] = "None";
                                } else {
                                    advsubtitle[9] = companyArrayObj.getString("visa_company_fee");
                                }
                                if (companyArrayObj.getString("visa_sponsor_fee").equals("")){
                                    advsubtitle[10] = "None";
                                } else {
                                    advsubtitle[10] = companyArrayObj.getString("visa_sponsor_fee");
                                }
                                if (companyArrayObj.getString("visa_total_amount").equals("")){
                                    advsubtitle[11] = "None";
                                } else {
                                    advsubtitle[11] = companyArrayObj.getString("visa_total_amount");
                                }
                                if (companyArrayObj.getString("visa_advance_amount").equals("")){
                                    advsubtitle[12] = "None";
                                } else {
                                    advsubtitle[12] = companyArrayObj.getString("visa_advance_amount");
                                }

                                docdata[0] = "None";
                                if (companyArrayObj.getString("visa_passport").equals("")){
                                    docdatadate[0] = "None";
                                } else {
                                    docdatadate[0] = companyArrayObj.getString("visa_passport");
                                }
                            }

                            ProfileItem mitem = new ProfileItem();
                            mitem.setProfile_title(profiletitle);
                            mitem.setProfile_subtitle(profilesubtitle);
                            mitem.setAdvanced_title(advtitle);
                            mitem.setAdvanced_subtitile(advsubtitle);
                            mitem.setfile_title(docdata);
                            mitem.setfile_path(docdatadate);
                            dataItem.add(mitem);

                            Intent intent = new Intent("VISAGET");
                            sendBroadcast(intent);
                        }



                    }else {
                        Intent intent = new Intent("EMPTYGET");
                        sendBroadcast(intent);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Intent intent = new Intent("ERRORGET");
                sendBroadcast(intent);
            } catch (NullPointerException e) {
                Intent intent = new Intent("ERRORNETGET");
                sendBroadcast(intent);
            } catch (Exception e) {
                Intent intent = new Intent("ERRORGET");
                sendBroadcast(intent);
            }
        }
    }

    public class ProfileViewpagerAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 4;
        private String[] titles = {"Profile", "Advanced", "Document Details", "Documents"};

        public ProfileViewpagerAdapter(Context applicationContext,
                                       FragmentManager fragmentManager) {
            super(fragmentManager);
            _context = applicationContext;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();
            switch (position) {
                case 0:
                    f = new ProfileChildFragment();
                    break;
                case 1:
                    f = new AdvancedChildFragment();
                    break;
                case 2:
                    f = new DocDetlChildFragment();
                    break;
                case 3:
                    f = new DocumentChildFragment();
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return totalPage;
        }
    }
    public class ProfileViewpagerOtherAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 3;
        private String[] titles = {"Profile", "Advanced", "Documents"};

        public ProfileViewpagerOtherAdapter(Context applicationContext,
                                            FragmentManager fragmentManager) {
            super(fragmentManager);
            _context = applicationContext;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();
            switch (position) {
                case 0:
                    f = new ProfileChildFragment();
                    break;
                case 1:
                    f = new AdvancedChildFragment();
                    break;
                case 2:
                    f = new DocumentChildFragment();
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return totalPage;
        }
    }
    public class ProfileViewpagercompanyAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 5;
        private String[] titles = {"Profile", "Advanced","Bank Details","Document Details","Documents"};

        public ProfileViewpagercompanyAdapter(Context applicationContext,
                                              FragmentManager fragmentManager) {
            super(fragmentManager);
            _context = applicationContext;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();
            switch (position) {
                case 0:
                    f = new ProfileChildFragment();
                    break;
                case 1:
                    f = new AdvancedChildFragment();
                    break;

                case 2:
                    f = new BankDocDetailsChild();
                    break;
                case 3:
                    f = new DocDetlChildFragment();
                    break;
                case 4:
                    f = new DocumentChildFragment();
                    break;

            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return totalPage;
        }
    }
    public class ProfileViewpagercandidateAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 5;
        private String[] titles = {"Profile", "Advanced", "Experience","Document Details","Documents"};

        public ProfileViewpagercandidateAdapter(Context applicationContext,
                                                FragmentManager fragmentManager) {
            super(fragmentManager);
            _context = applicationContext;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();
            switch (position) {
                case 0:
                    f = new ProfileChildFragment();
                    break;
                case 1:
                    f = new EXPADVchildfragment();
                    break;
                case 2:
                    f = new Experiencechildfragment();
                    break;
                case 3:
                    f = new DocDetlChildFragment();
                    break;
                case 4:
                    f = new DocumentChildFragment();
                    break;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return totalPage;
        }
    }

    private void mBackActions(){
        Intent intent = new Intent(OtherProfileActivity.this,DashboardActivity.class);

        switch (Status){
            case "company":
                intent.putExtra("PAGE","COMPANY");
                break;

            case "employee":
                intent.putExtra("PAGE","EMPLOYEE");
                break;

            case "vehicle":
                intent.putExtra("PAGE","VEHICLE");
                break;

            case "visa":
                intent.putExtra("PAGE","VISA");
                break;

            case "flipemployee":
                intent.putExtra("PAGE","FLIPEMPLOYEE");
                break;

            case "flipcompany":
                intent.putExtra("PAGE","FLIPCOMPANY");
                break;

            case "flipvehicle":
                intent.putExtra("PAGE","FLIPVEHICLE");
                break;

            case "new":
                intent.putExtra("PAGE","NEW");
                break;

            case "issued":
                intent.putExtra("PAGE","ISSUED");
                break;

            case "enter":
                intent.putExtra("PAGE","ENTER");
                break;

            case "notenter":
                intent.putExtra("PAGE","NOTENTER");
                break;

            case "report":
                intent.putExtra("PAGE","REPORT");
                break;

            case "candidate":
                intent.putExtra("PAGE","CANDIDATE");
                break;
//            case "agent":
//                intent.putExtra("PAGE","AGENT");
//                break;
        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    private void Action_Mail(String mail) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mail.toString().trim()});
        i.putExtra(Intent.EXTRA_SUBJECT, 0);
        i.putExtra(Intent.EXTRA_TEXT, 0);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

    private void Action_Call(String call) {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
        } else {
            //Open call function
            String number = new String(call.toString());
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

        mBackActions();
    }
}