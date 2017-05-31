package com.cutesys.sponsormasterfullversionnew.ADDModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsermasterlibrary.Calendar.CompactCalendarView;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Owner on 18/03/2017.
 */

public class AddHoliday extends AppCompatActivity {
    private Button mAddDate;
    private SharedPreferences sPreferences;
    private CalendarView calendar;
    private String curDate;
    int year = 0;
    ImageView done,close;
    private CompactCalendarView mcalendarview;
    private AVLoadingIndicatorView loading;
    private TextView mTitle,mhead;
    private ImageView ic_previous, ic_next;
    private Calendar mCalendar;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForYear = new SimpleDateFormat("yyyy", Locale.getDefault());

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_holiday);
        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);
       /* mAddDate=(Button)findViewById(R.id.add_date);*/
       /* calendar=(CalendarView)findViewById(R.id.calendar);*/
        done=(ImageView)findViewById(R.id.done);
        mhead=(TextView)findViewById(R.id.mhead);
        mhead.setText("Add Holiday");
        mcalendarview = (CompactCalendarView)findViewById(R.id.calendarview);
        loading = (AVLoadingIndicatorView) findViewById(R.id.loading);
        ic_previous = (ImageView) findViewById(R.id.ic_previous);
        ic_next = (ImageView)findViewById(R.id.ic_next);
        mTitle = ((TextView) findViewById(R.id.mTitles));
        loading.setVisibility(View.GONE);
        close = (ImageView) findViewById(R.id.close);
        mcalendarview = (CompactCalendarView)findViewById(R.id.calendarview);
        mcalendarview.setUseThreeLetterAbbreviation(false);
        mcalendarview.setFirstDayOfWeek(Calendar.SUNDAY);
        mcalendarview.invalidate();
        mcalendarview.shouldDrawIndicatorsBelowSelectedDays(true);
        mTitle.setText(dateFormatForMonth.format(mcalendarview.getFirstDayOfCurrentMonth()));

        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);
        mcalendarview.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mTitle.setText(dateFormatForMonth.format(dateClicked));
                curDate=dateFormatForDisplaying.format(dateClicked);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mTitle.setText(dateFormatForMonth.format(firstDayOfNewMonth));

                if(year != Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth))){

                    year = Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth));
                }
            }
        });
/*
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month=month+1;
                int d = dayOfMonth;
                int m=month;
                int len=String.valueOf(m).length();
                int length = String.valueOf(d).length();
                if((length==2)&&(len==2)){
                    curDate =String.valueOf(d)+"/"+month+"/"+year;
                }
                else if((length==1)&&(len==2)) {
                    curDate ="0"+String.valueOf(d)+"/"+month+"/"+year;
                }
                else if((len==1)&&(length==2)) {
                    curDate =String.valueOf(d)+"/"+"0"+month+"/"+year;
                }
                else {
                    curDate ="0"+String.valueOf(d)+"/"+"0"+month+"/"+year;
                }

            }
        });*/
        ic_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcalendarview.showPreviousMonth();
            }
        });
        ic_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcalendarview.showNextMonth();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getApplicationContext(), "Please wait while we process your request").show();
                }  else {
                    AddHoliday();
                }
            }

        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading.getVisibility() == View.VISIBLE) {

                    CustomToast.info(getApplicationContext(), "Please wait while we process your request").show();
                } else {
                    Close_view();
                }
            }
        });
    }
    private void Close_view(){
        Intent intent = new Intent(AddHoliday.this,DashboardActivity.class);
        intent.putExtra("PAGE","HOLIDAY");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                R.anim.bottom_down);
        finish();
    }

    public void AddHoliday(){

        Config mConfig = new Config(getApplicationContext());

        if(mConfig.isOnline(getApplicationContext())){
            LoadAddHoliday mLoadAddHoliday = new LoadAddHoliday(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),curDate);
            mLoadAddHoliday.execute((Void) null);
        }else {
            CustomToast.error(getApplicationContext(),"No Internet Connection.").show();
        }

    }
    public class LoadAddHoliday extends AsyncTask<Void, StringBuilder, StringBuilder> {

        ProgressDialog progressBar;
        private String mId, mAuthorization,mholidaydate;

        LoadAddHoliday(String id, String authorization,
                       String holidaydate) {
            mId = id;
            mAuthorization = authorization;
            mholidaydate = holidaydate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getApplicationContext());
            StringBuilder result = httpOperations.doAddHoliday(mId, mAuthorization,mholidaydate);

            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            loading.setVisibility(View.GONE);

            Log.d("2222222222222222",result.toString());

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        CustomToast.success(getApplicationContext(), "Holiday Added Successfully").show();

                    }
                    else if(jsonObj.getString("status").equals(String.valueOf(404))){
                        CustomToast.info(getApplicationContext(),"Holiday already exist").show();

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
}