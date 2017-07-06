package com.cutesys.sponsormasterfullversionnew.Recruitment;

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
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ProfileItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Owner on 5/06/2017.
 */

public class Interviewprofile extends AppCompatActivity implements View.OnClickListener{

    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private static String[] PERMISSIONS_PHONECALL = {Manifest.permission.CALL_PHONE};

    private static final int PERMISSION_REQUEST_CODE = 1;

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
        setContentView(R.layout.interview_profile_activity);

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
        if ((Status.equals("interview")) ) {

            setupViewPagerother(pf_pager);

        }

        tablayout.setupWithViewPager(pf_pager);

        dataItem = new ArrayList<>();
        InitGetData(false);
    }


    private void setupViewPagerother(ViewPager viewPager) {
        ProfileViewpagerOtherAdapter pf_adapter = new ProfileViewpagerOtherAdapter(getApplicationContext(),
                getSupportFragmentManager());
        pf_pager.setOffscreenPageLimit(3);
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
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
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

            if ((Status.equals("interview"))) {
                result = httpOperations.doInterviewDetails(mId, mAuthorization, mStatusID);

            }
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
                        if ((Status.equals("interview"))) {
                            try {
                                profile.setImageResource(R.drawable.interview);

                                JSONArray interviewarray = jsonObj.getJSONArray("interview_details");
                                String[] profiletitle = new String[10];
                                String[] profilesubtitle = new String[10];


                                for (int i = 0; i < interviewarray.length(); i++) {

                                    JSONObject companyArrayObj = (JSONObject) interviewarray.get(i);

                                    profiletitle[0] = "Interview name";
                                    profiletitle[9] = "Company name";
                                    profiletitle[1] = "Interviewer";
                                    profiletitle[2] = "Date From";
                                    profiletitle[3] = "Date To";
                                    profiletitle[4] = "Time From";
                                    profiletitle[5] = "Time To";
                                    profiletitle[6] = "Country";
                                    profiletitle[7] = "State";
                                    profiletitle[8] = "Place";




                                    if (companyArrayObj.getString("interview_name").equals("")) {
                                        profilesubtitle[0] = "None";
                                    } else {
                                        profilesubtitle[0] = companyArrayObj.getString("interview_name");
                                    }

                                    if (companyArrayObj.getString("company_name").equals("")) {
                                        profilesubtitle[9] = "None";
                                    } else {
                                        profilesubtitle[9] = companyArrayObj.getString("company_name");
                                    }

                                    if (companyArrayObj.getString("interview_interviewer").equals("")) {
                                        profilesubtitle[1] = "None";
                                    } else {
                                        profilesubtitle[1] = companyArrayObj.getString("interview_interviewer");
                                    }

                                    if (companyArrayObj.getString("interview_date_from").equals("")) {
                                        profilesubtitle[2] = "None";
                                    } else {
                                        profilesubtitle[2] = companyArrayObj.getString("interview_date_from");
                                    }
                                    if (companyArrayObj.getString("interview_date_to").equals("")) {
                                        profilesubtitle[3] = "None";
                                    } else {
                                        profilesubtitle[3] = companyArrayObj.getString("interview_date_to");
                                    }
                                    if (companyArrayObj.getString("interview_time_from").equals("")) {
                                        profilesubtitle[4] = "None";
                                    } else {
                                        profilesubtitle[4] = companyArrayObj.getString("interview_time_from");
                                    }
                                    if (companyArrayObj.getString("interview_time_to").equals("")) {
                                        profilesubtitle[5] = "None";

                                    } else {
                                        profilesubtitle[5] = companyArrayObj.getString("interview_time_to");

                                    }
                                    if (companyArrayObj.getString("interview_country").equals("")) {
                                        profilesubtitle[6] = "None";

                                    } else {
                                        profilesubtitle[6] = companyArrayObj.getString("interview_country");

                                    }
                                    if (companyArrayObj.getString("interview_state").equals("")) {
                                        profilesubtitle[7] = "None";
                                    } else {
                                        profilesubtitle[7] = companyArrayObj.getString("interview_state");
                                    }
                                    if (companyArrayObj.getString("interview_place").equals("")) {
                                        profilesubtitle[8] = "None";
                                    } else {
                                        profilesubtitle[8] = companyArrayObj.getString("interview_place");
                                    }
                                }



                                JSONArray docdataarray = jsonObj.getJSONArray("qualification");

                                String[] QUALINAME = new String[docdataarray.length()];
                                String[] QUALISTATUS = new String[docdataarray.length()];
                                String[] QUALIID = new String[docdataarray.length()];


                                for (int k = 0; k < docdataarray.length(); k++) {

                                    JSONObject docdatadetails = (JSONObject) docdataarray.get(k);
                                    QUALINAME[k] = docdatadetails.getString("qualifications_name");
                                    QUALISTATUS[k] = docdatadetails.getString("qualifications_status");
                                    QUALIID[k] = docdatadetails.getString("interview_id");

                                }
                                JSONArray requirdataarray = jsonObj.getJSONArray("requirement");

                                String[] REQUNUMBER = new String[requirdataarray.length()];
                                String[] REQUJOB = new String[requirdataarray.length()];
                                String[] REQUCATEGORY = new String[requirdataarray.length()];
                                String[] REQUSKILL = new String[requirdataarray.length()];
                                String[] REQUID = new String[requirdataarray.length()];


                                for (int k = 0; k < requirdataarray.length(); k++) {

                                    JSONObject REQdocdatadetails = (JSONObject) requirdataarray.get(k);
                                    REQUNUMBER[k] = REQdocdatadetails.getString("requirement_number");
                                    REQUJOB[k] = REQdocdatadetails.getString("requirements_job");
                                    REQUCATEGORY[k] = REQdocdatadetails.getString("requirements_category");
                                    REQUSKILL[k] = REQdocdatadetails.getString("requirements_skils");
                                    REQUID[k] = REQdocdatadetails.getString("interview_id");

                                }
                                ProfileItem mitem = new ProfileItem();
                                mitem.setProfile_title(profiletitle);
                                mitem.setProfile_subtitle(profilesubtitle);

                                mitem.setDatatitle(QUALINAME);
                                mitem.setDatadls(QUALISTATUS);
                                mitem.setDataowner(QUALIID);
                                mitem.setDatastart(REQUNUMBER);
                                mitem.setDataend(REQUJOB);
                                mitem.setfile_companyname(REQUCATEGORY);
                                mitem.setfile_designation(REQUSKILL);
                                mitem.setfile_from(REQUID);

                                dataItem.add(mitem);

                                Intent intent = new Intent("INTERVIEWGET");
                                sendBroadcast(intent);
                            }catch (JSONException e) {
                                e.printStackTrace();

                            }
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



    public class ProfileViewpagerOtherAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 3;
        private String[] titles = {"Profile", "Qualification", "Requirement"};

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
                    f = new InterviewProfilechid();
                    break;
                case 1:
                    f = new InterviewQUALchildFragment();
                    break;
                case 2:
                    f = new InterviewREQchildFragmnet();
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
        Intent intent = new Intent(Interviewprofile.this,DashboardActivity.class);

        switch (Status){
            case "interview":
                intent.putExtra("PAGE","interview");
                break;


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
