package com.cutesys.sponsormasterfullversionnew.fragment;

/**
 * Created by user on 3/15/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.Listener;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.holder.IconTreeItemHolder;
import com.cutesys.sponsormasterfullversionnew.model.TreeNode;
import com.cutesys.sponsormasterfullversionnew.view.AndroidTreeView;

public class FolderStructureFragment extends Fragment {
    private TextView statusBar;
    private AndroidTreeView tView;
    Listener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.drawer_fragment_default, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        mListener = (Listener)getActivity();
        statusBar = (TextView) rootView.findViewById(R.id.status_bar);

        TreeNode root = TreeNode.root();

        /*Main Headings*/
        TreeNode homeRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Home"));
        root.addChildren(homeRoot);

        TreeNode companyRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Company"));
        root.addChildren(companyRoot);

        TreeNode employeeRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Employee"));
        root.addChildren(employeeRoot);

        TreeNode usermanagementRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "User Management"));
        root.addChildren(usermanagementRoot);

        TreeNode payrollRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Payroll"));
        root.addChildren(payrollRoot);

        TreeNode accountsRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Accounts"));
        root.addChildren(accountsRoot);

        TreeNode vehicleRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Vehicle"));
        root.addChildren(vehicleRoot);

        TreeNode visaRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Visa"));
        root.addChildren(visaRoot);

        TreeNode NotificationRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Notification"));
        root.addChildren(NotificationRoot);

        TreeNode ReportsRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Reports"));
        root.addChildren(ReportsRoot);

        TreeNode RecruitmentRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Recruitment"));
        root.addChildren(RecruitmentRoot);

        TreeNode ArchiveRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Archive"));
        root.addChildren(ArchiveRoot);

        TreeNode CalenderRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Calender"));
        root.addChildren(CalenderRoot);

        TreeNode myProfileRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "My Profile"));
        root.addChildren(myProfileRoot);

        TreeNode LogoutRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, "Logout"));
        root.addChildren(LogoutRoot);


        //Company
        TreeNode companyList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Company List"));
        TreeNode addCompany = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Add Company"));
        TreeNode companymanageDocuments = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Manage Documents"));
        TreeNode companyexpirylist = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Document Expiry List"));
        TreeNode companydocumentstatus = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Documents Status"));
        companymanageDocuments.addChildren(companyexpirylist,companydocumentstatus);

        TreeNode companySettings = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Settings"));
        TreeNode bank = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_photo, "Bank Details"));
        TreeNode camp = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_photo, "Camp Details"));
        TreeNode companyDesignation = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_photo, "Designation"));
        companySettings.addChildren(bank,camp,companyDesignation);
        companyRoot.addChildren(companyList,addCompany,companymanageDocuments,companySettings);

        //Employee
        TreeNode employeeList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Employee List"));
        TreeNode addnewEmployee = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Add New Employee"));
        TreeNode employeemanageDocuments = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Manage Documents "));
        TreeNode employeeexpirylist = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Document Expiry List "));
        TreeNode employeedocumentstatus = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Documents Status "));
        employeemanageDocuments.addChildren(employeeexpirylist,employeedocumentstatus);

        TreeNode employeeTimetracker = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Time Tracker"));
        TreeNode employeeTimesheet = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Time Sheet"));
        TreeNode employeeAttendence = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Attendence "));
        employeeTimetracker.addChildren(employeeTimesheet,employeeAttendence);

        TreeNode employeeLeavetracker = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Leave Tracker"));
        TreeNode employeeLeavelist = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Leave List"));
        employeeLeavetracker.addChildren(employeeLeavelist);

        TreeNode employeeLoan = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Loan"));
        TreeNode employeeTravelLog = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travel Log"));

        TreeNode employeeAnnual = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Annual"));
        TreeNode employeeEligible = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Eligible Employee List"));
        TreeNode employeeConfirmtravel = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Confirmed Travel"));
        TreeNode employeeTravelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travelled"));
        TreeNode employeeCancelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Cancelled"));
        employeeAnnual.addChildren(employeeEligible,employeeConfirmtravel,employeeTravelled,employeeCancelled);

        TreeNode employeeOther = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Other"));
        TreeNode employeeTravelDetails= new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travel Details "));
        TreeNode employeeTicketbooked = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Ticket Booked"));
        TreeNode employeeOtherTravelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travelled "));
        TreeNode employeeOtherCancelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Cancelled "));
        employeeOther.addChildren(employeeTravelDetails,employeeTicketbooked,employeeOtherTravelled,employeeOtherCancelled);
        employeeTravelLog.addChildren(employeeAnnual,employeeOther);

        TreeNode employeeSettings = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Settings "));
        TreeNode employeemanageholiday = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Manage Holiday"));
        TreeNode employeeAllowance = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Allowance"));
        employeeSettings.addChildren(employeemanageholiday,employeeAllowance);
        employeeRoot.addChildren(employeeList,addnewEmployee,employeemanageDocuments,employeeTimetracker,employeeLeavetracker,employeeLoan,employeeTravelLog,employeeSettings);

        //UserManagement
        TreeNode loginDetails = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Login Details"));
        TreeNode AddLogin = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Add Login"));
        TreeNode userAttendenceList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Attendence List"));
        TreeNode userAttendenceReport = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Attendence Report"));
        TreeNode userWorkReport = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Work Report"));
        usermanagementRoot.addChildren(loginDetails,AddLogin,userAttendenceList,userAttendenceReport,userWorkReport);

        //Payroll
        TreeNode BasicSalary = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Basic Salary"));
        TreeNode SalaryUpdate = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Salary Update"));
        TreeNode Reports = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Reports"));
        TreeNode SalaryList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Salary List"));
        TreeNode AdvancePaid = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Advance Paid"));
        TreeNode AdvancePending = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Advance Pending"));
        Reports.addChildren(SalaryList,AdvancePaid,AdvancePending);
        TreeNode payrollSettings = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Settings  "));
        TreeNode payrollManageHoliday = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Manage Holiday "));
        TreeNode payrollDeductions = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Deductions"));
        payrollSettings.addChildren(payrollManageHoliday,payrollDeductions);
        payrollRoot.addChildren(BasicSalary,SalaryUpdate,Reports,payrollSettings);

        //Accounts
        TreeNode accountsemployeeList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Employee List "));
        TreeNode accountsLeavelist = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Leave List "));
        TreeNode accountsTimetracker = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Time Tracker "));
        TreeNode accountsTimesheet = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Time Sheet "));
        accountsTimetracker.addChildren(accountsTimesheet);

        TreeNode accountsTravelLog = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travel Log "));
        TreeNode accountsAnnual = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Annual"));
        TreeNode accountsEligible = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Eligible Employee List "));
        TreeNode accountsConfirmtravel = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Confirmed Travel "));
        TreeNode accountsTravelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travelled  "));
        TreeNode accountsCancelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Cancelled  "));
        accountsAnnual.addChildren(accountsEligible,accountsConfirmtravel,accountsTravelled,accountsCancelled);

        TreeNode accountsOther = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Other"));
        TreeNode accountsTravelDetails= new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travel Details  "));
        TreeNode accountsTicketbooked = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Ticket Booked "));
        TreeNode accountsOtherTravelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travelled   "));
        TreeNode accountsOtherCancelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Cancelled   "));
        employeeOther.addChildren(accountsTravelDetails,accountsTicketbooked,accountsOtherTravelled,accountsOtherCancelled);
        accountsTravelLog.addChildren(accountsAnnual,accountsOther);

        TreeNode accountsReport = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Report"));
        TreeNode accountsreportAnnualTravelbooked = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Annual Travel booked"));
        TreeNode accountsreportAnnualTravelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Annual Travelled"));
        TreeNode accountsreportOtherTravelbooked = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Other Travel booked"));
        TreeNode accountsreportOtherTravelled = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Other Travelled"));
        TreeNode accountsreportSalaryList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Salary List "));
        TreeNode accountsreportAdvancePaid = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Advance Paid "));
        TreeNode accountsreportAdvancePending = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Advance Pending "));
        accountsReport.addChildren(accountsreportAnnualTravelbooked,accountsreportAnnualTravelled,accountsreportOtherTravelbooked,
                accountsreportOtherTravelled,accountsreportSalaryList,accountsreportAdvancePaid,accountsreportAdvancePending);

        TreeNode accountsSettings = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Settings   "));
        TreeNode accountsmanageholiday = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Manage Holiday  "));
        accountsSettings.addChildren(accountsmanageholiday);

        accountsRoot.addChildren(accountsemployeeList,accountsLeavelist,accountsTimetracker,accountsTravelLog,accountsReport,accountsSettings);

        //Vehicle
        TreeNode vehicleList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Vehicle List"));
        TreeNode Addvehicle = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Add Vehicle"));
        TreeNode vehicleDocumentStatus = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Documents Status   "));
        TreeNode vehicleSettings = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Settings    "));
        TreeNode Manufacturer = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Manufacturer"));
        TreeNode Model = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Model"));
        TreeNode InsuranceCompany = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "InsuranceCompany"));
        TreeNode InsuranceType = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "InsuranceType"));
        TreeNode ExpensesType = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "ExpensesType"));
        vehicleSettings.addChildren(Manufacturer,Model,InsuranceCompany,InsuranceType,ExpensesType);
        vehicleRoot.addChildren(vehicleList,Addvehicle,vehicleDocumentStatus,vehicleSettings);

        //Visa
        TreeNode VisaDetails = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Visa Details"));
        /*TreeNode AddNewVisa = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Add New Visa"));
        TreeNode NewVisaList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "New Visa List"));
        TreeNode IssuedVisaList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Issued Visa List"));
        VisaDetails.addChildren(AddNewVisa,NewVisaList,IssuedVisaList);*/

        TreeNode visamanageDocuments = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Manage Documents"));
        TreeNode visaexpirylist = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Document Expiry List  "));
        TreeNode visadocumentstatus = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Documents Status    "));
        visamanageDocuments.addChildren(visaexpirylist,visadocumentstatus);
        TreeNode visaPaymentList = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Payment List"));
        TreeNode TravelDetails = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Travel Details "));
        TreeNode visaSettings = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Settings     "));
        /*TreeNode VisaType = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Visa Type"));
        TreeNode VisaCategory = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Visa Category"));
        TreeNode Sponsor = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Sponsor List"));
        TreeNode Client = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Client List"));
        visaSettings.addChildren(VisaType,VisaCategory,Sponsor,Client);*/
        visaRoot.addChildren(VisaDetails,visamanageDocuments,visaPaymentList,TravelDetails,visaSettings);

        //Notification
        TreeNode NotificationCompany = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Company "));
        TreeNode NotificationEmployee = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Employee "));
        TreeNode NotificationVehicle = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Vehicle "));
        TreeNode NotificationVisa = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Visa "));
        NotificationRoot.addChildren(NotificationCompany,NotificationEmployee,NotificationVehicle,NotificationVisa);


        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultNodeLongClickListener(nodeLongClickListener);

        containerView.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu, menu);
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.expandAll:
                tView.expandAll();
                break;

            case R.id.collapseAll:
                tView.collapseAll();
                break;
        }
        return true;
    }

    private int counter = 0;

    private void fillDownloadsFolder(TreeNode node) {
        TreeNode downloads = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Downloads" + (counter++)));
        node.addChild(downloads);
        if (counter < 5) {
            fillDownloadsFolder(downloads);
        }
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {

            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            //statusBar.setText("Last clicked: " + item.text);
            Fragment mfragment = null;
            /*switch (item.text){
               case "Home": mfragment = new HomeFragment();
                   mListener.LoadItem(mfragment);
                   break;
               //company
                case "Company List": mfragment = new CompanyListFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Add Company": mfragment = new StateFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Document Expiry List": mfragment = new CompanyExpiryFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Documents Status": mfragment = new CompanyStatusFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Bank Details": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Camp Details": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Designation": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;

                //Employee
                case "Employee List": mfragment = new EmployeeListFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Add New Employee": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Document Expiry List ": mfragment = new EmployeeExpiryFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Documents Status ": mfragment = new EmployeeStatusFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Time Sheet": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Attendence": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Leave List": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Loan": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Eligible Employee List": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Confirmed Travel": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Travelled": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Cancelled": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Travel Details": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Ticket Booked": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Travelled ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Cancelled ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Manage Holiday": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Allowance": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;

                /*//*//**//*UserManagement
                case "Login Details": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Add Login": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Attendence List": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Attendence Report": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Work Report": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;

                //Payroll
                case "Basic Salary": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Salary Update": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Salary List": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Advance Paid": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Advance Pending": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Manage Holiday ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Deduction": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;

                //Accounts
                case "Employee List ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Leave List ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Time Sheet ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Eligible Employee List ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Confirmed Travel ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Travelled  ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Cancelled  ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Travel Details ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Ticket Booked ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Travelled   ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Cancelled   ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Annual Travel Booked": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Annual Travelled": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Other TravelBooked": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Other Travelled": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Salary List ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Advance Paid ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Advance Pending ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Manage Holiday  ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;

                //Vehicle
                case "Vehicle List": mfragment = new VehicleListFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Add Vehicle": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Documents Status  ": mfragment = new VehicleStatusFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Manufacturer": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Model": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Insurance Company": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Insurance Type": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Expenses Type": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;

                //Visa
                case "Visa Details": mfragment = new VisaDetailsFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Document Expiry List  ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Documents Status   ": mfragment = new HomeFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Payment List": mfragment = new VisaPaymentFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Travel Details  ": mfragment = new VisaTravelFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Settings     ": mfragment = new VisaSettingsFragment();
                    mListener.LoadItem(mfragment);
                    break;

                //Calender
                case "Calender": mfragment = new CalendarFragment();
                    mListener.LoadItem(mfragment);
                    break;
                //Calender
                case "My Profile": mfragment = new MyProfileFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Logout": *//*mfragment = new MyProfileFragment();
                    mListener.LoadItem(mfragment);*//*
                    break;

                //Notification
                case "Company ": mfragment = new CompanyNotificationFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Employee ": mfragment = new EmployeeNotificationFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Vehicle ": mfragment = new VehicleNotificationFragment();
                    mListener.LoadItem(mfragment);
                    break;
                case "Visa ": mfragment = new VisaNotificationFragment();
                    mListener.LoadItem(mfragment);
                    break;
            }*/
        }
    };
    /*private void loadFragment(Fragment fragment) {


                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commitAllowingStateLoss();

    }*/
    private TreeNode.TreeNodeLongClickListener nodeLongClickListener = new TreeNode.TreeNodeLongClickListener() {
        @Override
        public boolean onLongClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            //Toast.makeText(getActivity(), "Long click: " + item.text, Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}