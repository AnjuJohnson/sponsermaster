package com.cutesys.sponsormasterfullversionnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cutesys.sponsermasterlibrary.CircularImageView;
import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.MultilevelListview.ItemInfo;
import com.cutesys.sponsermasterlibrary.MultilevelListview.MultiLevelListAdapter;
import com.cutesys.sponsermasterlibrary.MultilevelListview.MultiLevelListView;
import com.cutesys.sponsermasterlibrary.MultilevelListview.NestType;
import com.cutesys.sponsermasterlibrary.MultilevelListview.OnItemClickListener;

import com.cutesys.sponsormasterfullversionnew.Accounts.ReportAnnualTravelledBooked;
import com.cutesys.sponsormasterfullversionnew.Accounts.ReportEmpOtherTicketBooked;
import com.cutesys.sponsormasterfullversionnew.Accounts.ReportEmpOtherTravelled;
import com.cutesys.sponsormasterfullversionnew.Accounts.ReportEmployeeAnnualTravelledFragment;
import com.cutesys.sponsormasterfullversionnew.Archive.AgentArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Archive.CandidateArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Archive.CompanyArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Archive.EmployeeArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Archive.InterviewArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Archive.RejectedCandidateArchive;
import com.cutesys.sponsormasterfullversionnew.Archive.RetestCandidateArchive;
import com.cutesys.sponsormasterfullversionnew.Archive.SelectedCandidateArchive;
import com.cutesys.sponsormasterfullversionnew.Archive.VehicleArchiveFragment;
import com.cutesys.sponsormasterfullversionnew.Calender.CalendarFragment;
import com.cutesys.sponsormasterfullversionnew.Company.BankListFragment;
import com.cutesys.sponsormasterfullversionnew.Company.CampDetails;
import com.cutesys.sponsormasterfullversionnew.Company.CompanyExpiryFragment;
import com.cutesys.sponsormasterfullversionnew.Company.CompanyListFragment;
import com.cutesys.sponsormasterfullversionnew.Company.CompanyStatusFragment;

import com.cutesys.sponsormasterfullversionnew.Company.DesignationFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.AllowanceFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.EmpManageHolidayFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.EmployeeExpiryFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.EmployeeListFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.EmployeeLoanFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.EmployeeStatusFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.LeaveListFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.LeaveReasonFragment;
import com.cutesys.sponsormasterfullversionnew.Employee.TravelLog.Annual;
import com.cutesys.sponsormasterfullversionnew.Employee.TravelLog.Attendance_new;
import com.cutesys.sponsormasterfullversionnew.Employee.TravelLog.Other;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.DataProvider.BaseItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.DataProvider.CustomDataProvider;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.Listener;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
import com.cutesys.sponsormasterfullversionnew.Notification.CompanyNotificationFragment;
import com.cutesys.sponsormasterfullversionnew.Notification.EmployeeNotificationFragment;
import com.cutesys.sponsormasterfullversionnew.Notification.RPVisaMedicalNotiFragment;
import com.cutesys.sponsormasterfullversionnew.Notification.VehicleNotificationFragment;
import com.cutesys.sponsormasterfullversionnew.Notification.VisaNotificationFragment;
import com.cutesys.sponsormasterfullversionnew.Notification.VisaValidityExpiryNotiFragment;
import com.cutesys.sponsormasterfullversionnew.Payroll.AdvancePaidListFragment;
import com.cutesys.sponsormasterfullversionnew.Payroll.AdvancePendingListFragment;
import com.cutesys.sponsormasterfullversionnew.Payroll.AdvanceSalaryFragment;
import com.cutesys.sponsormasterfullversionnew.Payroll.DeductionFragment;
import com.cutesys.sponsormasterfullversionnew.Payroll.SalaryListFragment;
import com.cutesys.sponsormasterfullversionnew.Payroll.report_salary;
import com.cutesys.sponsormasterfullversionnew.Recruitment.AgentListFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.AgentProfile;
import com.cutesys.sponsormasterfullversionnew.UserManagement.AttendanceReport;
import com.cutesys.sponsormasterfullversionnew.Recruitment.CandidateEmployee.CandidateToEmployee;
import com.cutesys.sponsormasterfullversionnew.Recruitment.CategoryFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.CountryFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.InterviewFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.JobFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.MedicalStatusFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.QualificationFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Report.ReportMedicalStatus;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Report.ReportSelectionFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Report.ReportTravelledCandidate;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Report.ReportTravellingStatus;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Report.ReportVisaProcess;
import com.cutesys.sponsormasterfullversionnew.Recruitment.SelectionStatus.SelectionStatusFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.SkillFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.StateFragment;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Travelstatus.TravelStatus;
import com.cutesys.sponsormasterfullversionnew.Recruitment.Visa.VisaProcess;
import com.cutesys.sponsormasterfullversionnew.Report.CompanyReportFragment;
import com.cutesys.sponsormasterfullversionnew.Report.EmployeeReportFragment;
import com.cutesys.sponsormasterfullversionnew.Report.VehicleReportFragment;
import com.cutesys.sponsormasterfullversionnew.Report.VisaReportFragment;
import com.cutesys.sponsormasterfullversionnew.Subclasses.HomeFragment;
import com.cutesys.sponsormasterfullversionnew.Subclasses.MyProfileFragment;

import com.cutesys.sponsormasterfullversionnew.UserManagement.AttendanceListFragment;
import com.cutesys.sponsormasterfullversionnew.UserManagement.LoginListFragment;
import com.cutesys.sponsormasterfullversionnew.UserManagement.WorkListFragment;
import com.cutesys.sponsormasterfullversionnew.Vehicle.ExpensesTypeFragment;
import com.cutesys.sponsormasterfullversionnew.Vehicle.InsuranceCompanyFragment;
import com.cutesys.sponsormasterfullversionnew.Vehicle.InsuranceTypeFragment;
import com.cutesys.sponsormasterfullversionnew.Vehicle.ManufacturerFragment;
import com.cutesys.sponsormasterfullversionnew.Vehicle.ModelFragment;
import com.cutesys.sponsormasterfullversionnew.Vehicle.VehicleListFragment;
import com.cutesys.sponsormasterfullversionnew.Vehicle.VehicleStatusFragment;

import com.cutesys.sponsormasterfullversionnew.Visa.VisaIssuedChildFragment;
import com.cutesys.sponsormasterfullversionnew.Visa.VisaNewChildFragment;
import com.cutesys.sponsormasterfullversionnew.Visa.VisaPaymentFragment;
import com.cutesys.sponsormasterfullversionnew.Visa.VisaSettingsFragment;
import com.cutesys.sponsormasterfullversionnew.Visa.VisaTravelFragment;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kris on 2/18/2017.
 */

public class DashboardActivity extends AppCompatActivity implements Listener, View.OnClickListener {

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    private SqliteHelper helper;
    private SharedPreferences sPreferences;

    private Toolbar mToolbar;
    private MultiLevelListView multiLevelListView;
    private CircularImageView circularprofile;
    private TextView profilename;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Button logout;
    private LinearLayout popuplayout;
    private ImageView closepopup;

    ListAdapter listAdapter;

    Handler mHandler;
    Intent mIntent;
    private String mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        helper = new SqliteHelper(getApplicationContext(), "SponserMaster", null, 1);
        sPreferences = getSharedPreferences("SponsorMaster", MODE_PRIVATE);

        mHandler = new Handler();
        InitIdViews();
    }

    private void InitIdViews(){

        mIntent = getIntent();
        mAction = mIntent.getExtras().getString("PAGE");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        multiLevelListView = (MultiLevelListView) findViewById(R.id.multiLevelMenu);
        circularprofile = (CircularImageView)findViewById(R.id.circularprofile);
        profilename = (TextView) findViewById(R.id.profilename);
        mToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        closepopup = (ImageView) findViewById(R.id.closepopup);
        popuplayout = (LinearLayout) findViewById(R.id.popuplayout);
        logout = (Button) findViewById(R.id.logout);
        popuplayout.setVisibility(View.GONE);
        logout.setOnClickListener(this);
        closepopup.setOnClickListener(this);
        int color = Color.parseColor("#000000");
        closepopup.setColorFilter(color);

        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        final List<HashMap<String, String>> Data_Item;
        Data_Item = helper.getadmindetails();

        Picasso.with(getApplicationContext())
                .load(Data_Item.get(0).get("admin_img")
                        .replaceAll(" ","%20"))
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(circularprofile);

        InitDrawerView();
    }

    private void InitDrawerView() {

        try {
            new AsyncTask<String, String, String>() {
                protected void onPreExecute() {

                    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(DashboardActivity.this,
                            drawerLayout, mToolbar,R.string.openDrawer, R.string.openDrawer){

                        @Override
                        public void onDrawerClosed(View drawerView) {
                            super.onDrawerClosed(drawerView);
                        }

                        @Override
                        public void onDrawerOpened(View drawerView) {
                            super.onDrawerOpened(drawerView);
                        }
                    };
                    drawerLayout.setDrawerListener(actionBarDrawerToggle);
                    actionBarDrawerToggle.syncState();

                    listAdapter = new ListAdapter();

                    multiLevelListView.setAdapter(listAdapter);
                    multiLevelListView.setOnItemClickListener(mOnItemClickListener);

                    listAdapter.setDataItems(CustomDataProvider.getInitialItems());
                    setMultipleExpanding(false);
                }

                @Override
                protected String doInBackground(String... arg0) {

                    return null;
                }

                protected void onPostExecute(String result) {

                    profilename.setText(sPreferences.getString("NAME",""));

                    Fragment mFragment = null;

                    switch (mAction){

                        case "HOME":
                            mFragment = new HomeFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("ACTION",9);
                            mFragment.setArguments(bundle);
                            loadFragment(mFragment);
                            break;

                        case "FLIPCOMPANY":
                            mFragment = new HomeFragment();
                            Bundle cpybundle = new Bundle();
                            cpybundle.putInt("ACTION",0);
                            mFragment.setArguments(cpybundle);
                            loadFragment(mFragment);
                            break;

                        case "FLIPEMPLOYEE":
                            mFragment = new HomeFragment();
                            Bundle empbundle = new Bundle();
                            empbundle.putInt("ACTION",1);
                            mFragment.setArguments(empbundle);
                            loadFragment(mFragment);
                            break;

                        case "FLIPVEHICLE":
                            mFragment = new HomeFragment();
                            Bundle vbundle = new Bundle();
                            vbundle.putInt("ACTION",2);
                            mFragment.setArguments(vbundle);
                            loadFragment(mFragment);
                            break;

                        case "COMPANY":
                            mFragment = new CompanyListFragment();
                            loadFragment(mFragment);
                            break;

                        case "BANK LIST":
                            mFragment = new BankListFragment();
                            loadFragment(mFragment);
                            break;

                        case "CAMP DETAILS LIST":
                            mFragment = new CampDetails();
                            loadFragment(mFragment);
                            break;

                        case "CAMP ROOM LIST":
                            mFragment = new CampDetails();
                            loadFragment(mFragment);
                            break;

                        case "EMPLOYEE":
                            mFragment = new EmployeeListFragment();
                            loadFragment(mFragment);
                            break;

                        case "VEHICLE":
                            mFragment = new VehicleListFragment();
                            loadFragment(mFragment);
                            break;

                        case "VISA":
                            mFragment = new VisaPaymentFragment();
                            loadFragment(mFragment);
                            break;

                        case "NEW":
                            mFragment = new VisaNewChildFragment();
                            loadFragment(mFragment);
                            break;

                        case "ISSUED":
                            mFragment = new VisaIssuedChildFragment();
                            loadFragment(mFragment);
                            break;

                        case "ENTER":
                            mFragment = new VisaTravelFragment();
                            Bundle enterbundle = new Bundle();
                            enterbundle.putInt("ACTION",0);
                            mFragment.setArguments(enterbundle);
                            loadFragment(mFragment);
                            break;

                        case "NOTENTER":
                            mFragment = new VisaTravelFragment();
                            Bundle notbundle = new Bundle();
                            notbundle.putInt("ACTION",1);
                            mFragment.setArguments(notbundle);
                            loadFragment(mFragment);
                            break;

                        case "REPORT":
                          //  mFragment = new VisaReportChildFragment();
                            loadFragment(mFragment);
                            break;
                        case "AGENT":
                              mFragment = new AgentListFragment();
                            loadFragment(mFragment);
                            break;
                        case "MEDICALCLEAREDLIST":
                            mFragment = new MedicalStatusFragment();
                            Bundle clearedbundle = new Bundle();
                            clearedbundle.putInt("ACTION",1);
                            mFragment.setArguments(clearedbundle);
                            loadFragment(mFragment);

                        case "DESIGNATION":
                            mFragment = new DesignationFragment();
                            loadFragment(mFragment);
                            break;
                        case "CANDIDATE":
                            mFragment = new SelectionStatusFragment();
                            loadFragment(mFragment);
                            break;
                        case "LOGIN LIST":
                            mFragment = new LoginListFragment();
                            loadFragment(mFragment);
                            break;

                        case "INTERVIEW DETAILS":
                            mFragment = new InterviewFragment();
                            loadFragment(mFragment);
                            break;

                        case "MANUFACTURER":
                            mFragment = new ManufacturerFragment();
                            loadFragment(mFragment);
                            break;

                        case "MODEL":
                            mFragment = new ModelFragment();
                            loadFragment(mFragment);
                            break;
                        case "DEDUCTIONCATEGORY":
                            mFragment = new DeductionFragment();
                            loadFragment(mFragment);
                            break;

                        case "INSURANCE COMPANY":
                            mFragment = new InsuranceCompanyFragment();
                            loadFragment(mFragment);
                            break;

                        case "INSURANCE TYPE":
                            mFragment = new InsuranceTypeFragment();
                            loadFragment(mFragment);
                            break;

                        case "EXPENSES TYPE":
                            mFragment = new ExpensesTypeFragment();
                            loadFragment(mFragment);
                            break;

                        case "COUNTRY":
                            mFragment = new CountryFragment();
                            loadFragment(mFragment);
                            break;

                        case "STATE":
                            mFragment = new StateFragment();
                            loadFragment(mFragment);
                            break;


                        case "QUALIFICATION":
                            mFragment = new QualificationFragment();
                            loadFragment(mFragment);
                            break;

                        case "JOB":
                            mFragment = new JobFragment();
                            loadFragment(mFragment);
                            break;

                        case "CATEGORY":
                            mFragment = new CategoryFragment();
                            loadFragment(mFragment);
                            break;

                        case "SKILL":
                            mFragment = new SkillFragment();
                            loadFragment(mFragment);
                            break;

                        case "HOLIDAY":
                            mFragment = new EmpManageHolidayFragment();
                            loadFragment(mFragment);
                            break;

                        case "LOAN":
                            mFragment = new QualificationFragment();
                            loadFragment(mFragment);
                            break;

                        case "ALLOWANCE":
                            mFragment = new AllowanceFragment();
                            loadFragment(mFragment);
                            break;

                        case "VISATYPE":
                            mFragment = new VisaSettingsFragment();
                            //Bundle bundle1 = new Bundle();
                            //bundle1.putInt("ACTION",0);
                           // mFragment.setArguments(bundle1);
                            loadFragment(mFragment);
                            break;

                        case "VISACATEGORY":
                            mFragment = new VisaSettingsFragment();
                            /*Bundle bundle2 = new Bundle();
                            bundle2.putInt("ACTION",1);
                            mFragment.setArguments(bundle2);*/
                            loadFragment(mFragment);
                            break;

                        case "ADDSPONSOR":
                            mFragment = new VisaSettingsFragment();
                            /*Bundle bundle3 = new Bundle();
                            bundle3.putInt("ACTION",2);
                            mFragment.setArguments(bundle3);*/
                            loadFragment(mFragment);
                            break;

                        case "VISACLIENT":
                            mFragment = new VisaSettingsFragment();
                            /*Bundle bundle4 = new Bundle();
                            bundle4.putInt("ACTION",3);
                            mFragment.setArguments(bundle4);*/
                            loadFragment(mFragment);
                            break;


                        default:
                            mFragment = new HomeFragment();
                            Bundle mbundle = new Bundle();
                            mbundle.putInt("ACTION",9);
                            mFragment.setArguments(mbundle);
                            loadFragment(mFragment);
                            break;
                    }

                };
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {

            displayLoadView(((BaseItem) item).getId());
        }

        @Override
        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
        }
    };

    private void displayLoadView(int mId){
        Fragment fragment = null;
        switch (mId){

            case 0:
                fragment = new HomeFragment();
                Bundle mbundle = new Bundle();
                mbundle.putInt("ACTION", 9);
                fragment.setArguments(mbundle);
                loadFragment(fragment);
                break;

            case 16:
                fragment = new CompanyListFragment();
                loadFragment(fragment);
                break;

            case 18:
                fragment = new CompanyExpiryFragment();
                loadFragment(fragment);
                break;

            case 19:
                fragment = new CompanyStatusFragment();
                loadFragment(fragment);
                break;


            case 70:
                //Bank Details
                fragment = new BankListFragment();
                loadFragment(fragment);
                break;
            case 71:
                //Camp Details
                fragment = new CampDetails();
                loadFragment(fragment);
                break;
            case 72:
                fragment = new DesignationFragment();
                loadFragment(fragment);
                break;

            case 24:
                fragment = new EmployeeListFragment();
                loadFragment(fragment);
                break;

            case 26:
                fragment = new EmployeeExpiryFragment();
                loadFragment(fragment);
                break;
            case 27:
                fragment = new EmployeeStatusFragment();
                loadFragment(fragment);
                break;

            case 65:
                fragment = new CompanyArchiveFragment();
                loadFragment(fragment);
                break;

            case 66:
                fragment = new EmployeeArchiveFragment();
                loadFragment(fragment);
                break;

            case 67:
                fragment = new VehicleArchiveFragment();
                loadFragment(fragment);
                break;

            case 117:
                fragment = new AgentArchiveFragment();
                loadFragment(fragment);
                break;

            case 118:
                fragment = new InterviewArchiveFragment();
                loadFragment(fragment);
                break;

            case 119:
                fragment = new CandidateArchiveFragment();
                loadFragment(fragment);
                break;

            case 120:
                fragment = new SelectedCandidateArchive();
                loadFragment(fragment);
                break;

            case 121:
                fragment = new RetestCandidateArchive();
                loadFragment(fragment);
                break;

            case 122:
                fragment = new RejectedCandidateArchive();
                loadFragment(fragment);
                break;

            case 74:
                //Attendence
                fragment = new Attendance_new();
                loadFragment(fragment);
                break;

            case 75:
                //Leave List
                fragment = new LeaveListFragment();
                loadFragment(fragment);
                break;
            case 28:
                //Loan
                fragment = new EmployeeLoanFragment();
                loadFragment(fragment);
                break;
            case 76:
                //Annual
                fragment = new Annual();
                loadFragment(fragment);
                break;
            case 77:
                //Other
                fragment = new Other();
                loadFragment(fragment);
                break;
            case 78:
                //Manage holiday
                fragment = new EmpManageHolidayFragment();
                loadFragment(fragment);
                break;
            case 79:
                //Allowance
                fragment = new AllowanceFragment();
                loadFragment(fragment);
                break;
            case 130:
                //Allowance
                fragment = new LeaveReasonFragment();
                loadFragment(fragment);
                break;
            case 29:
                //Login details
                fragment = new LoginListFragment();
                loadFragment(fragment);
                break;
            /*case 30:
                //Add login
                fragment = new LoginListFragment();
                loadFragment(fragment);
                break;*/
            case 31:
                //Attendence list
                fragment = new AttendanceListFragment();
                loadFragment(fragment);
                break;
            case 32:
                //Attendence report
                fragment = new AttendanceReport();
                loadFragment(fragment);
                break;
            case 33:
                //work report
                fragment = new WorkListFragment();
                loadFragment(fragment);
                break;
            case 36:
                //Basic salary
                fragment = new SalaryListFragment();
                loadFragment(fragment);
                break;
            case 37:
                //  adavance Salary
                fragment = new AdvanceSalaryFragment();
                loadFragment(fragment);
                break;
            case 80:
                //Salary list
                fragment = new report_salary();
                loadFragment(fragment);
                break;
            case 81:
                //Advance paid
                fragment = new AdvancePaidListFragment();
                loadFragment(fragment);
                break;
            case 82:
                //Advance pending
                fragment = new AdvancePendingListFragment();
                loadFragment(fragment);
                break;
            case 83:
                //Manage holiday
                fragment = new EmpManageHolidayFragment();
                loadFragment(fragment);
                break;
            case 84:
                //Deduction
                fragment = new DeductionFragment();
                loadFragment(fragment);
                break;
            case 42:
                //Employee List
                fragment = new EmployeeListFragment();
                loadFragment(fragment);
                break;
            case 43:
                //Leave list
                fragment = new LeaveListFragment();
                loadFragment(fragment);
                break;
            case 85:
                //Annual
                fragment = new Annual();
                loadFragment(fragment);
                break;
            case 86:
                //Other
                fragment = new Other();
                loadFragment(fragment);
                break;
            case 87:
                //Annual travel booked
                fragment = new ReportAnnualTravelledBooked();
                loadFragment(fragment);
                break;

            case 88:
                //Annual travelled
                fragment = new ReportEmployeeAnnualTravelledFragment();
                loadFragment(fragment);
                break;
            case 89:
                //Other travel booked
                fragment = new ReportEmpOtherTicketBooked();
                loadFragment(fragment);
                break;
            case 90:
                //Other travelled
                fragment = new ReportEmpOtherTravelled();
                loadFragment(fragment);
                break;
            case 91:
                //Salary list
                fragment = new SalaryListFragment();
                loadFragment(fragment);
                break;
            case 92:
                //Advance paid
                fragment = new AdvancePaidListFragment();
                loadFragment(fragment);
                break;
            case 93:
                //Advance pending
                fragment = new AdvancePendingListFragment();
                loadFragment(fragment);
                break;
            case 94:
                //Manage holiday
                fragment = new EmpManageHolidayFragment();
                loadFragment(fragment);
                break;
            case 45:
                fragment = new VehicleListFragment();
                loadFragment(fragment);
                break;
            case 47:
                fragment = new VehicleStatusFragment();
                loadFragment(fragment);
                break;
            case 95:
                fragment = new ManufacturerFragment();
                loadFragment(fragment);
                break;
            case 96:
                fragment = new ModelFragment();
                loadFragment(fragment);
                break;
            case 97:
                fragment = new InsuranceCompanyFragment();
                loadFragment(fragment);
                break;

            case 98:
                fragment = new InsuranceTypeFragment();
                loadFragment(fragment);
                break;

            case 99:
                fragment = new ExpensesTypeFragment();
                loadFragment(fragment);
                break;

            case 50:
                fragment = new VisaPaymentFragment();
                loadFragment(fragment);
                break;

            case 51:
                fragment = new VisaTravelFragment();
                loadFragment(fragment);
                break;

            case 100:
                fragment = new VisaNewChildFragment();
                loadFragment(fragment);
                break;

            case 102:
                fragment = new VisaSettingsFragment();
                loadFragment(fragment);
                break;

            case 101:
                fragment = new VisaIssuedChildFragment();
                loadFragment(fragment);
                break;

            case 52:
                fragment = new CompanyNotificationFragment();
                loadFragment(fragment);
                break;

            case 53:
                fragment = new EmployeeNotificationFragment();
                loadFragment(fragment);
                break;

            case 54:
                fragment = new VehicleNotificationFragment();
                loadFragment(fragment);
                break;

            case 55:
                fragment = new VisaNotificationFragment();
                loadFragment(fragment);
                break;

            case 128:
                //Recruitment Notification
                fragment = new VisaValidityExpiryNotiFragment();
                loadFragment(fragment);
                break;

            case 129:
                //Recruitment Notification
                fragment = new RPVisaMedicalNotiFragment();
                loadFragment(fragment);
                break;


            case 57:
                fragment = new CompanyReportFragment();
                loadFragment(fragment);
                break;

            case 58:
                fragment = new EmployeeReportFragment();
                loadFragment(fragment);
                break;

            case 59:
                fragment = new VehicleReportFragment();
                loadFragment(fragment);
                break;

            case 60:
                fragment = new VisaReportFragment();
                loadFragment(fragment);
                break;

            case 104:
                fragment = new InterviewFragment();
                loadFragment(fragment);
                break;


            case 105:
                fragment = new SelectionStatusFragment();
                loadFragment(fragment);
                break;

            case 106:
                fragment = new MedicalStatusFragment();
                loadFragment(fragment);
                break;

            case 107:
                fragment = new VisaProcess();
                loadFragment(fragment);
                break;

            case 108:
                fragment = new TravelStatus();
                loadFragment(fragment);
                break;

            case 109:
                //candidate to employee
                fragment = new CandidateToEmployee();
                loadFragment(fragment);
                break;

            case 64:
                //agent list
                fragment = new AgentProfile();
                loadFragment(fragment);
                break;



            case 123:
                //Selection
                fragment = new ReportSelectionFragment();
                loadFragment(fragment);
                break;

            case 124:
                //Medical
                fragment = new ReportMedicalStatus();
                loadFragment(fragment);
                break;

            case 125:
                //Visa
                fragment = new ReportVisaProcess();
                loadFragment(fragment);
                break;

            case 126:
                //Travelling
                fragment = new ReportTravellingStatus();
                loadFragment(fragment);
                break;

            case 127:
                //Travelled
                fragment = new ReportTravelledCandidate();
                loadFragment(fragment);
                break;

            case 110:
                fragment = new CountryFragment();
                loadFragment(fragment);
                break;

            case 111:
                fragment = new StateFragment();
                loadFragment(fragment);
                break;

            case 112:
                fragment = new QualificationFragment();
                loadFragment(fragment);
                break;

            case 113:
                fragment = new JobFragment();
                loadFragment(fragment);
                break;

            case 114:
                //category
                fragment = new CategoryFragment();
                loadFragment(fragment);
                break;

            case 115:
                //skill
                fragment = new SkillFragment();
                loadFragment(fragment);
                break;

            case 116:
                //visa
                break;

            case 12:
                fragment = new CalendarFragment();
                loadFragment(fragment);
                break;

            case 13:
                fragment = new MyProfileFragment();
                loadFragment(fragment);
                break;

            case 14:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }

                Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_up);
                popuplayout.startAnimation(slide);
                popuplayout.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void loadFragment(final Fragment fragment) {

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawerLayout.closeDrawers();
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {

            finish();
            Intent closepage = new Intent();
            closepage.setAction(Intent.ACTION_MAIN);
            closepage.addCategory(Intent.CATEGORY_HOME);
            startActivity(closepage);

        } else {
            CustomToast.info(getApplicationContext(),"Press again to exit").show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void LoadItem(int pos) {
        displayLoadView(pos);
    }

    @Override
    public void OpenDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View view) {

        int buttonId = view.getId();

        switch (buttonId){
            case R.id.closepopup:
                Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_down);
                popuplayout.startAnimation(slide);
                popuplayout.setVisibility(View.GONE);
                break;

            case R.id.logout:
                clear();
                finish();
                Intent closepage = new Intent();
                closepage.setAction(Intent.ACTION_MAIN);
                closepage.addCategory(Intent.CATEGORY_HOME);
                startActivity(closepage);
                break;
        }
    }

    public void clear() {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private class ListAdapter extends MultiLevelListAdapter {

        private class ViewHolder {
            TextView nameView;
            ImageView arrowView, ic_img;
        }

        @Override
        public List<?> getSubObjects(Object object) {
            return CustomDataProvider.getSubItems((BaseItem) object);
        }

        @Override
        public boolean isExpandable(Object object) {
            return CustomDataProvider.isExpandable((BaseItem) object);
        }

        @Override
        public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.drawer_list_group, null);
                viewHolder.ic_img = (ImageView) convertView.findViewById(R.id.ic_img);
                viewHolder.nameView = (TextView) convertView.findViewById(R.id.dataItemName);
                viewHolder.arrowView = (ImageView) convertView.findViewById(R.id.dataItemArrow);
                int color = Color.parseColor("#000000");
                viewHolder.arrowView.setColorFilter(Color.parseColor("#000000"));
                viewHolder.ic_img.setColorFilter(color);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.nameView.setText(((BaseItem) object).getName());
            viewHolder.ic_img.setImageResource(((BaseItem) object).getImg());

            if (itemInfo.isExpandable()) {
                viewHolder.arrowView.setVisibility(View.VISIBLE);
                viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                        R.mipmap.ic_collapse : R.mipmap.ic_expand);

            } else {
                viewHolder.arrowView.setVisibility(View.GONE);
            }

            return convertView;
        }
    }

    private void setMultipleExpanding(boolean multipleExpanding) {
        multiLevelListView.setNestType(multipleExpanding ? NestType.MULTIPLE : NestType.SINGLE);
    }
}