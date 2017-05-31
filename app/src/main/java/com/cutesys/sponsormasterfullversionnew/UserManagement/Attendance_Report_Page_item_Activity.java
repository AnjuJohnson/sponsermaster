package com.cutesys.sponsormasterfullversionnew.UserManagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;

/**
 * Created by Owner on 30/05/2017.
 */

public class Attendance_Report_Page_item_Activity extends AppCompatActivity{
Switcher switcher;
    Intent intent;
    String mDesignation,mEmployee_id,year,month,day;
    private SharedPreferences sPreferences;
    private TextView mPunchIn,mPunchOut,mPunchInLocation,mPunchInIP,mPunchOutLocation,mPunchOutIP;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend_report_list);
        ItIdView();

    }
    public void ItIdView(){
        intent=getIntent();
        mDesignation=intent.getExtras().getString("Designation");
        mEmployee_id=intent.getExtras().getString("Employyee_id");
        year=intent.getExtras().getString("year");
        month=intent.getExtras().getString("month");
        day=intent.getExtras().getString("day");
       /* switcher = new Switcher.Builder(getApplicationContext())
                .addContentView(findViewById(R.id.cmpy_recycler2))
                .addErrorView(findViewById(R.id.error_view))
                .addProgressView(findViewById(R.id.progress_view))
                .setErrorLabel((TextView)findViewById(R.id.error_label))
                .setEmptyLabel((TextView) findViewById(R.id.empty_label))
                .addEmptyView(findViewById(R.id.empty_view))
                .build();*/
        mPunchIn=(TextView)findViewById(R.id.punch_in);
        mPunchInIP=(TextView)findViewById(R.id.punch_in_ip);
        mPunchInLocation=(TextView)findViewById(R.id.punch_in_loc);
        mPunchOut=(TextView)findViewById(R.id.punch_out);
        mPunchOutIP=(TextView)findViewById(R.id.punch_out_ip);
        mPunchOutLocation=(TextView)findViewById(R.id.punch_out_loc);
        System.out.println(mDesignation+mEmployee_id+year+month+day);


//InItGetdata();
    }
    public void InItGetdata() {
        Config mConfig = new Config(getApplicationContext());
       /* if (mConfig.isOnline(getApplicationContext())) {
            LoginListFragment.LoadLoginInitiate mLoadLoginInitiate = new LoginListFragment.LoadLoginInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadLoginInitiate.execute((Void) null);
        } else {
            switcher.showErrorView("No Internet Connection");
        }*/
    }
    public class LoadAttendanceReport extends AsyncTask<Void,StringBuilder,StringBuilder>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            return null;
        }


        @Override
        protected void onPostExecute(StringBuilder stringBuilder) {
            super.onPostExecute(stringBuilder);
        }
    }
}
