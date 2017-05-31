package com.cutesys.sponsormasterfullversionnew.Recruitment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.LeaveListAdapter2;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SpinnerAdapter2;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SpinnerAdapter_;
import com.cutesys.sponsormasterfullversionnew.Employee.LeaveListFragment;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.UserManagement.Attendance_Report_Page_item_Activity;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Owner on 27/05/2017.
 */

public class AttendanceReport  extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;
Button mSubmit;
    private Switcher switcher;
    TextView error_label_retry, empty_label_retry, mTitle;
    int day;
    int month;
    int year;
    Calendar cal;
public String YEAR,MONTH,DAY;
    private RecyclerView emptravelled;
    ArrayList<AllListItem> dataItem;
    ArrayList<AllListItem> dataIt;

    AVLoadingIndicatorView progress,progress_2;
    LeaveListAdapter2 mLeaveListAdapter;
    int start = 0;
    String status;
    TextView txt,mEmp_id;
    String mSpinnerItem = "", designation_ID,mSpinnerEmployeeName,mEmployee_id;
    Spinner mSpinnerDesig,mSpinner_2;
    TextView spinnererror,spinnererror_2,spinnererror_3,mDate;
    ImageView progressrestart,progressrestart_2,mCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.attendance_report_recycler, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
       //
         InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {

        cal = Calendar.getInstance();
        mDate = (TextView) rootView.findViewById(R.id.date);

       // emptravelled = (RecyclerView) rootView.findViewById(R.id.loan_recycler);
        mTitle = (TextView) rootView.findViewById(R.id.mTitle);
        mEmp_id = (TextView) rootView.findViewById(R.id.emp_id);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView) rootView.findViewById(R.id.empty_label_retry));
        progressrestart = (ImageView) rootView.findViewById(R.id.progressrestart);
        mCalendar = (ImageView) rootView.findViewById(R.id.calendar);
        mCalendar.setOnClickListener(this);
        progressrestart.setOnClickListener(this);
        progressrestart_2 = (ImageView) rootView.findViewById(R.id.progressrestart_2);
        progressrestart_2.setOnClickListener(this);
        mSpinnerDesig = (Spinner) rootView.findViewById(R.id.spinner);
        mSpinner_2 = (Spinner) rootView.findViewById(R.id.spinner_2);
       /* error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);*/
        spinnererror = (TextView) rootView.findViewById(R.id.spinnererror);
        spinnererror.setVisibility(View.GONE);
        spinnererror_2 = (TextView) rootView.findViewById(R.id.spinnererror_2);
        spinnererror_2.setVisibility(View.GONE);
        spinnererror_3 = (TextView) rootView.findViewById(R.id.spinnererror_3);
        spinnererror_3.setVisibility(View.GONE);
        progress = (AVLoadingIndicatorView) rootView.findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);
        progress_2 = (AVLoadingIndicatorView) rootView.findViewById(R.id.progress_2);
        progress_2.setVisibility(View.INVISIBLE);
mSubmit=(Button)rootView.findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
      //  initRecyclerView();
        mTitle.setText("Attendance Report");
        dataItem = new ArrayList<>();
        dataIt = new ArrayList<>();
        InitGetData(false);


        mSpinnerDesig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerItem = dataItem.get(i).getEmployee_designation();
                designation_ID = dataItem.get(i).getcompany_id();
                System.out.println("designation=" + mSpinnerItem);
                System.out.println("designation id..." + designation_ID);
                Config mConfig = new Config(getActivity());
                if (mConfig.isOnline(getActivity())) {


                    AttendanceReport.LoadEmpDesignationListInitiate mLoadEmpDesignationListInitiate =
                            new LoadEmpDesignationListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start), mSpinnerItem.replaceAll(" ","%20"));
                    mLoadEmpDesignationListInitiate.execute((Void) null);
                  // dataIt.clear();
                    dataIt.clear();
                    mSpinner_2.setAdapter(null);
                    mEmp_id.setText(" ");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mSpinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mSpinnerEmployeeName = dataIt.get(i).getEmployee_name();
                mEmployee_id = dataIt.get(i).getemployee_id();
                System.out.println("selected employee name" + mSpinnerEmployeeName);
                System.out.println("employee id.." + mEmployee_id);
                mEmp_id.setText(mEmployee_id);
                Config mConfig = new Config(getActivity());
                if (mConfig.isOnline(getActivity())) {

                    AttendanceReport.LoadEmpDesignationListInitiate mLoadEmpDesignationListInitiate =
                            new LoadEmpDesignationListInitiate(sPreferences.getString("ID", ""),
                                    sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start), mSpinnerEmployeeName.replaceAll(" ","%20"));
                    mLoadEmpDesignationListInitiate.execute((Void) null);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            AttendanceReport.LoadDesignationListInitiate mLoadDesignationListInitiate = new LoadDesignationListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
            mLoadDesignationListInitiate.execute((Void) null);

        }
        else {
            CustomToast.error(getActivity(),"No Internet Connection.").show();
        }
    }

  /*  private void initRecyclerView() {
        emptravelled.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        emptravelled.setLayoutManager(linearLayoutManager);
    }
*/
    @Override
    public void onClick(View view) {
        Config mConfig = new Config(getActivity());
        int buttonId = view.getId();
        switch (buttonId) {
            case R.id.calendar:

                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity()
                        ,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String day=String.valueOf(dayOfMonth);
                        String month=String.valueOf(monthOfYear+1);
                        if(day.length()==1){
                            day="0"+String.valueOf(dayOfMonth);
                        }
                        if(month.length()==1){
                            month="0"+String.valueOf(monthOfYear+1);
                        }
                        YEAR=String.valueOf(year);
                        MONTH=month;
                        DAY=day;

                        mDate.setText(year + "-" + month + "-" + day);

                    }
                }, year, month, day);
                datePickerDialog.show();
                spinnererror_3.setVisibility(View.GONE);
                break;


            case R.id.progressrestart:
                if (mConfig.isOnline(getActivity())) {
                    dataItem.clear();

                        AttendanceReport.LoadDesignationListInitiate mLoadDesignationListInitiate = new LoadDesignationListInitiate(sPreferences.getString("ID", ""),
                                sPreferences.getString("AUTHORIZATION", ""),String.valueOf(start));
                        mLoadDesignationListInitiate.execute((Void) null);
                    }
                    else {
                        CustomToast.error(getActivity(),"No Internet Connection.").show();
                    }
                break;
            case R.id.progressrestart_2:
                if (mConfig.isOnline(getActivity())) {

                        AttendanceReport.LoadEmpDesignationListInitiate mLoadEmpDesignationListInitiate =
                                new LoadEmpDesignationListInitiate(sPreferences.getString("ID", ""),
                                        sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start), mSpinnerItem.replaceAll(" ","%20"));
                        mLoadEmpDesignationListInitiate.execute((Void) null);
                    dataIt.clear();
                    mSpinner_2.setAdapter(null);
                    }
                else {
                    CustomToast.error(getActivity(),"No Internet Connection.").show();
                }
break;
            case R.id.submit:

              if((mDate.getText().toString().trim().equals(null))||(mDate.getText().toString().trim().equals(""))){
                    spinnererror_3.setVisibility(View.VISIBLE);
                }
                else {
                  Intent i=new Intent(getActivity(), Attendance_Report_Page_item_Activity.class);
                  i.putExtra("Designation",mSpinnerItem.toString().replaceAll(" ","%20"));
                  i.putExtra("Employyee_id",mEmp_id.getText().toString().trim());
                  i.putExtra("year",YEAR);
                  i.putExtra("month",MONTH);
                  i.putExtra("day",DAY);

                  startActivity(i);
              }

        }
    }
    public class LoadDesignationListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadDesignationListInitiate(String id, String authorization,String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dataItem = new ArrayList<>();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations web=new HttpOperations(getActivity());

            StringBuilder result = web.doEMPDESGList(mId, mAuthorization,mStart);
            System.out.println(result);
            return result;
        }
        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            System.out.println(result);

            progress.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                      //  switcher.showContentView();

                        JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("designation_name").equals(""))
                                    && (!feedObj.getString("designation_name").equals("null"))) {
                                item.setcompany_id(feedObj.getString("designation_id"));

                                item.setEmployee_designation(feedObj.getString("designation_name"));
                                dataItem.add(item);

                                ///for first designation
                            mSpinnerItem=dataItem.get(0).getEmployee_designation();
                            }
                            Log.d("designation",feedObj.getString("designation_name"));
                        }

                        SpinnerAdapter2 mAdapter = new SpinnerAdapter2(getActivity(),dataItem,"Designation");
                       // mAdapter.notifyDataSetChanged();
                        mSpinnerDesig.setAdapter(mAdapter);
                     //   System.out.println(result);

                        AttendanceReport.LoadEmpDesignationListInitiate mLoadEmpDesignationListInitiate =
                                new LoadEmpDesignationListInitiate(sPreferences.getString("ID", ""),
                                        sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start), mSpinnerItem.replaceAll(" ","%20"));
                        mLoadEmpDesignationListInitiate.execute((Void) null);

                    }else {
                       // switcher.showErrorView();
                    }
                }
                else {
                  //  switcher.showErrorView();
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
              //  switcher.showErrorView();

            } catch (NullPointerException e) {
              //  switcher.showErrorView();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public class LoadEmpDesignationListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mId, mAuthorization, mStart,mDesignation;
        private boolean mStatus;

        LoadEmpDesignationListInitiate(String id, String authorization,String Start,String designation) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
            mDesignation=designation;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            dataItem = new ArrayList<>();
           // dataIt.clear();
           // dataIt = new ArrayList<>();
            progress_2.setVisibility(View.VISIBLE);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations web=new HttpOperations(getActivity());

            StringBuilder resultt = web.doEmployeedesList(mId, mAuthorization,mStart,mDesignation);
            System.out.println(resultt);
            return resultt;
        }
        @Override
        protected void onPostExecute(StringBuilder resultt) {
            super.onPostExecute(resultt);
            System.out.println(resultt);

            progress_2.setVisibility(View.GONE);
            try {
                JSONObject jsonObj = new JSONObject(resultt.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        //  switcher.showContentView();

                        JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("employee_name").equals(""))
                                    && (!feedObj.getString("employee_name").equals("null"))) {
                                item.setEmployee_id(feedObj.getString("employee_id"));

                                item.setEmployee_name(feedObj.getString("employee_name"));
                                dataIt.add(item);

                            }
                            Log.d("employeename",feedObj.getString("employee_name"));
                        }
                        // start = dataIt.size();


                        SpinnerAdapter_ mAdapter = new SpinnerAdapter_(getActivity(),dataIt,"employee");
                        mAdapter.notifyDataSetChanged();
                        mSpinner_2.setAdapter(mAdapter);

                        System.out.println(dataIt);


                    }else {
                       // dataIt.clear();

                       /* SpinnerAdapter_ mAdapter = new SpinnerAdapter_(getActivity(),dataIt,"no_data");
                        mSpinner_2.setAdapter(mAdapter);
                        System.out.println(resultt);*/

                      //  switcher.showErrorView();
                    }
                }
                else {
                    dataIt.clear();
                    mSpinner_2.setAdapter(null);

                    Toast.makeText(getActivity(),"helllo",Toast.LENGTH_SHORT).show();
                    //   dataIt.clear();

                   // switcher.showErrorView();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                dataIt.clear();
                mSpinner_2.setAdapter(null);

                Toast.makeText(getActivity(),"helllo",Toast.LENGTH_SHORT).show();
              //  switcher.showErrorView();

            } catch (NullPointerException e) {
//                switcher.showErrorView();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}