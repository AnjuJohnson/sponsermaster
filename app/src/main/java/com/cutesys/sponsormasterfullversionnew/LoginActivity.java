package com.cutesys.sponsormasterfullversionnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.cutesys.sponsermasterlibrary.Button.LoadingButton;
import com.cutesys.sponsermasterlibrary.CheckBox;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Edittext.MaterialEditText;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,Animation.AnimationListener {
    private SharedPreferences sPreferences;
    private SqliteHelper helper;

    ImageView logo;
    MaterialEditText mUsename, mPassword;
    LinearLayout rememberlayout;
    CheckBox mRemember;
    Button buttonLogin;
    LoadingButton mLogin;
    ToggleButton mpwdtoggle;
    AVLoadingIndicatorView indicator;

    private Config mConfig;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new SqliteHelper(getApplicationContext(), "SponsorMaster", null, 1);
        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);

        mHandler = new Handler();
        mConfig = new Config(getApplicationContext());
        InitIdViews();
    }

    private void InitIdViews(){

        logo = (ImageView) findViewById(R.id.logo);
        mUsename = (MaterialEditText) findViewById(R.id.username);
        mPassword = (MaterialEditText) findViewById(R.id.password);
        rememberlayout = (LinearLayout) findViewById(R.id.rememberlayout);
        mRemember = (CheckBox) findViewById(R.id.rememberpwd);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        mpwdtoggle = (ToggleButton) findViewById(R.id.mpwdtoggle);
        indicator = (AVLoadingIndicatorView) findViewById(R.id.indicator);

        indicator.setVisibility(View.GONE);
        buttonLogin.setOnClickListener(this);
        mConfig.scale(logo, 100);

        if(sPreferences.getString("signchecked", "").equals("true")){
            mRemember.setChecked(true);
            mPassword.setText(sPreferences.getString("signinpwd", ""));
            mUsename.setText(sPreferences.getString("signinuname", ""));
        } else {
            mRemember.setChecked(false);
            mPassword.setText("");
            mUsename.setText("");
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
                move.setFillAfter(true);
                logo.startAnimation(move);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUsename.setVisibility(View.VISIBLE);
                        mPassword.setVisibility(View.VISIBLE);
                        buttonLogin.setVisibility(View.VISIBLE);
                        indicator.setVisibility(View.GONE);
                        rememberlayout.setVisibility(View.VISIBLE);
                        mpwdtoggle.setVisibility(View.VISIBLE);
                        mpwdtoggle.setChecked(true);
                        mConfig.scale(mUsename,150);
                        mConfig.scale(mPassword,150);
                        mConfig.scale(rememberlayout,150);
                        mConfig.scale(buttonLogin,100);
                    }
                },1200);
            }
        },3000);

        mRemember.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onChange(boolean checked) {
                if(checked){

                    mConfig.savePreferences(getApplicationContext(),"signchecked",
                            "true");
                }else {

                    mConfig.savePreferences(getApplicationContext(),"signchecked",
                            "false");
                }
            }
        });

        mpwdtoggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else{
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        int mId = view.getId();
        switch (mId){
            case R.id.buttonLogin:

                buttonGone();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(mUsename.getText().toString().trim().equals("")) {

                            buttonVisible();
                            mUsename.setError("Field cannot be blank");

                        }else if(mUsename.getText().toString().trim().length() < 3) {

                            buttonVisible();
                            mUsename.setError("Please enter valid username");

                        } else if(mPassword.getText().toString().trim().equals("")) {

                            buttonVisible();
                            mPassword.setError("Field cannot be blank");

                        } else if(mPassword.getText().toString().trim().length() < 2) {

                            buttonVisible();
                            mPassword.setError("Please enter valid username");

                        } else {

                            if (mConfig.isOnline(LoginActivity.this)) {
                                UserLoginTask userLoginTask = new UserLoginTask(mUsename.getText().toString().trim(),
                                        mPassword.getText().toString().trim());
                                userLoginTask.execute((Void) null);
                            } else {
                                buttonVisible();
                                CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
                            }
                        }
                    }
                }, 200);
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public class UserLoginTask extends AsyncTask<Void, String, String> {

        JSONObject jsondata;
        private String mUname, mPwd;

        UserLoginTask(String uname, String pwd) {
            mUname = uname;
            mPwd = pwd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mConfig.savePreferences(getApplicationContext(),"signinpwd",
                    mPwd.toString().trim());
            mConfig.savePreferences(getApplicationContext(),"signinuname",
                    mUname.toString().trim());

            helper.Delete_admin_details();
        }

        @Override
        protected String doInBackground(Void... params) {

            String Status = "";
            HttpOperations httpOperations = new HttpOperations(getBaseContext());
            StringBuilder result = httpOperations.doLogin(mUname, mPwd);
            Log.d("Athira", "API_LOGIN_RESPONSE " + result);

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {

                    if (jsonObj.getString("status").equals(String.valueOf(404))) {

                        Status = "404";

                    } else if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        JSONArray mJSONArray = jsonObj.getJSONArray("admin_data");
                        jsondata = (JSONObject) mJSONArray.get(0);

                        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
                        HashMap<String, String> map;
                        map = new HashMap<String, String>();

                        map.put("id", jsondata.getString("id").trim());
                        if(jsondata.has("qatar_id")){
                            map.put("qatar_id", jsondata.getString("qatar_id").trim());
                        } else {
                            map.put("qatar_id", "none");
                        }
                        map.put("firstname", jsondata.getString("firstname").trim());
                        map.put("lastname", jsondata.getString("lastname").trim());
                        if(jsondata.getString("address").trim().equals("")){
                            map.put("address", "none");
                        }else {
                            map.put("address", jsondata.getString("address").trim());
                        }
                        map.put("email", jsondata.getString("email").trim());
                        map.put("phone", jsondata.getString("phone").trim());
                        map.put("image", jsondata.getString("image").trim());
                        map.put("country", jsondata.getString("country").trim());
                        map.put("office", jsondata.getString("office").trim());
                        map.put("issueddate", jsondata.getString("issueddate").trim());
                        map.put("expirydate", jsondata.getString("expirydate").trim());
                        map.put("username", jsondata.getString("username").trim());
                        map.put("authentication_key", jsonObj.getString("authentication_key").trim());
                        fillMaps.add(map);
                        helper.Insert_admin_details(fillMaps);

                        final List<HashMap<String, String>> Data_Item;
                        Data_Item = helper.getadmindetails();

                        mConfig.savePreferences(getApplicationContext(),"ID",Data_Item.get(0).get("admin_id"));
                        mConfig.savePreferences(getApplicationContext(),"AUTHORIZATION",
                                Data_Item.get(0).get("admin_authentication_key"));
                        mConfig.savePreferences(getApplicationContext(),"NAME",Data_Item.get(0).get("admin_f_name")
                                +" "+Data_Item.get(0).get("admin_l_name"));

                        Status = "200";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Status = "400";
            }catch (NullPointerException e) {
                e.printStackTrace();
                Status = "401";
            }catch (Exception e) {
                e.printStackTrace();
                Status = "400";
            }
            return Status;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.equals("404")){
                mUsename.setError("Invalid Username or Password");
                mPassword.setError("Invalid Username or Password");
                buttonVisible();
            }else if(result.equals("401")){
                buttonVisible();
                CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
            }else if(result.equals("400")){
                buttonVisible();
                CustomToast.info(getApplicationContext(),"Please try again").show();
            }else if(result.equals("200")){
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                                intent.putExtra("PAGE","HOME");
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                }, 1000);
            }
        }
    }

    private void buttonVisible(){
        Animation animFadeIn;
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                android.R.anim.fade_in);
        animFadeIn.setAnimationListener(this);

        buttonLogin.setVisibility(View.VISIBLE);
        buttonLogin.startAnimation(animFadeIn);
        buttonLogin.setClickable(true);
        indicator.setVisibility(View.GONE);
    }

    private void buttonGone(){

        Animation animFadeIn;
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                android.R.anim.fade_in);
        animFadeIn.setAnimationListener(this);

        indicator.setVisibility(View.VISIBLE);
        indicator.startAnimation(animFadeIn);

        buttonLogin.setClickable(false);
        buttonLogin.setVisibility(View.GONE);
    }
}