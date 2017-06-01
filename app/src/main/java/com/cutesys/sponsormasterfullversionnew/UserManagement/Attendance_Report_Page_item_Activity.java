package com.cutesys.sponsormasterfullversionnew.UserManagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SpinnerAdapter_;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Owner on 30/05/2017.
 */

public class Attendance_Report_Page_item_Activity extends AppCompatActivity{
Switcher switcher;
    int start=0;
    Intent intent;
    String mDesignation,mEmployee_id,year,month,day;
    private SharedPreferences sPreferences;
    private TextView mPunchIn,mPunchOut,mPunchInLocation,mPunchInIP,mPunchOutLocation,mPunchOutIP,mEmployee_name,mSelecteddate,mTitile;
    private CardView mCardview;
    private TextView error_label_retry, empty_label_retry;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend_report_list);
        sPreferences = getApplicationContext().getSharedPreferences("SponsorMaster", getApplicationContext().MODE_PRIVATE);
        ItIdView();

    }
    public void ItIdView(){
        intent=getIntent();
        mDesignation=intent.getExtras().getString("Designation");
        mEmployee_id=intent.getExtras().getString("Employyee_id");
        year=intent.getExtras().getString("year");
        month=intent.getExtras().getString("month");
        day=intent.getExtras().getString("day");
        switcher = new Switcher.Builder(getApplicationContext())
                .addContentView(findViewById(R.id.card))
                .addErrorView(findViewById(R.id.error_view))
                .addProgressView(findViewById(R.id.progress_view))
                .setErrorLabel((TextView)findViewById(R.id.error_label))
                .setEmptyLabel((TextView) findViewById(R.id.empty_label))
                .addEmptyView(findViewById(R.id.empty_view))
                .build();
        mCardview=(CardView) findViewById(R.id.card);
        mCardview.setVisibility(View.GONE);
        mSelecteddate=(TextView)findViewById(R.id.selected_date);
        mTitile=(TextView)findViewById(R.id.mTitle);
        mTitile.setText("Attendance List");
        mEmployee_name=(TextView)findViewById(R.id.emp_name);
        mPunchIn=(TextView)findViewById(R.id.punch_in);
        mPunchInIP=(TextView)findViewById(R.id.punch_in_ip);
        mPunchInLocation=(TextView)findViewById(R.id.punch_in_loc);
        mPunchOut=(TextView)findViewById(R.id.punch_out);
        mPunchOutIP=(TextView)findViewById(R.id.punch_out_ip);
        mPunchOutLocation=(TextView)findViewById(R.id.punch_out_loc);
        System.out.println(mDesignation+mEmployee_id+year+month+day);
        error_label_retry = ((TextView) findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InItGetdata();
            }
        });
        empty_label_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InItGetdata();
            }
        });

InItGetdata();
    }

    public void InItGetdata() {
        Config mConfig = new Config(getApplicationContext());
       if (mConfig.isOnline(getApplicationContext())) {
            Attendance_Report_Page_item_Activity.LoadAttendanceReport mLoadAttendanceReport = new  Attendance_Report_Page_item_Activity.LoadAttendanceReport(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),mDesignation,mEmployee_id,year,month,day,String.valueOf(start));
           mLoadAttendanceReport.execute((Void) null);
        } else {
            switcher.showErrorView("No Internet Connection");
        }
    }
    public class LoadAttendanceReport extends AsyncTask<Void,StringBuilder,StringBuilder>{
private String mid,mstart,mdesignation,myear,mday,mmonth,memployee_id,mAthorization;
        LoadAttendanceReport(String id, String authorization,String designation,String employee_id,String year,String month,String day,String Start){
             mid=id;
            mAthorization=authorization;
            mdesignation=designation;
            myear=year;
            mmonth=month;
            mday=day;
            mstart=Start;
            memployee_id=employee_id;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switcher.showProgressView();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations web=new HttpOperations(getApplicationContext());

            StringBuilder resultt = web.doAttendanceReportoriginal(mid, mAthorization,mdesignation,memployee_id,myear,mmonth,mday,mstart);
            System.out.println(resultt);
            return resultt;
        }


        @Override
        protected void onPostExecute(StringBuilder resultt) {
            super.onPostExecute(resultt);
            try {
                JSONObject jsonObj = new JSONObject(resultt.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                         switcher.showContentView();

                        JSONArray feedArray = jsonObj.getJSONArray("attendance_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("full_name").equals(""))
                                    && (!feedObj.getString("full_name").equals("null"))) {
                               /* item.setEmployee_id(feedObj.getString("employee_id"));

                                item.setEmployee_name(feedObj.getString("employee_name"));
                                dataIt.add(item);*/
                                  mEmployee_name.setText(feedObj.getString("full_name"));
                            }
                            if (feedObj.getString("attendance_date").trim().equals("")) {
                                mSelecteddate.setText("None");
                            } else {
                                mSelecteddate.setText(feedObj.getString("attendance_date"));
                            }
                            if (feedObj.getString("attendance_punch_in_time").trim().equals("")) {
                                mPunchIn.setText("None");
                            } else {
                                mPunchIn.setText(feedObj.getString("attendance_punch_in_time"));
                            }
                            if (feedObj.getString("attendance_punch_in_location").trim().equals("")) {
                                mPunchInLocation.setText("None");
                            } else {
                                mPunchInLocation.setText(feedObj.getString("attendance_punch_in_location"));
                            }
                            if (feedObj.getString("attendance_punch_in_ip").trim().equals("")) {
                                mPunchInIP.setText("None");
                            } else {
                                mPunchInIP.setText(feedObj.getString("attendance_punch_in_ip"));
                            }
                            if (feedObj.getString("attendance_punch_out_time").trim().equals("")) {
                                mPunchOut.setText("None");
                            } else {
                                mPunchOut.setText(feedObj.getString("attendance_punch_out_time"));
                            }
                            if (feedObj.getString("attendance_punch_out_location").trim().equals("")) {
                                mPunchOutLocation.setText("None");
                            } else {
                                mPunchOutLocation.setText(feedObj.getString("attendance_punch_out_location"));
                            }
                            if (feedObj.getString("attendance_punch_out_ip").trim().equals("")) {
                                mPunchOutIP.setText("None");
                            } else {
                                mPunchOutIP.setText(feedObj.getString("attendance_punch_out_ip"));
                            }
                        }
                    }else {


                          switcher.showEmptyView();
                    }
                }
                else {
                     switcher.showEmptyView();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                switcher.showErrorView("Please Try Again");
            } catch (NullPointerException e) {
                switcher.showErrorView("No Internet Connection");
            } catch (Exception e) {
                switcher.showErrorView("Please Try Again");
            }
        }
        }

}
