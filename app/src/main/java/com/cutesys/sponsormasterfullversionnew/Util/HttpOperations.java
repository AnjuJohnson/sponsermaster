/*
 * Copyright 2016 Cutesys Technologies Pvt Ltd as an unpublished work. All Rights
 * Reserved.
 *
 * The information contained herein is confidential property of Cutesys Technologies
 * Pvt Ltd. The use, copying,transfer or disclosure of such information is prohibited
 * except by express written agreement with Company.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * File Name 					: HttpOperations
 * Since 						: 23/02/17
 * Version Code & Project Name	: v 1.0 Sponser Master
 * Author Name					: Aiswarya Saju
 */

package com.cutesys.sponsormasterfullversionnew.Util;

/**
 * Created by anju on 23/02/17.
 */

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpOperations {

    public final String SERVER_URL = "http://technosteelqatar.com/review/technosteelapp/index.php/";
    //public final String SERVER_URL = "https://technosteelqatar.com/app/index.php/";
    public final String SERVER_PROFILE = "http://dev.sponsormaster.com.au/admin_uploads/";



    public Context ctx;

    public HttpOperations(Context ctx) {
        this.ctx = ctx;
    }

    enum APIS {

        API_LOGIN("LoginApi", "POST"),
        API_DASHBOARD("LoginApi/dashboard", "POST"),
        API_COMPANYLIST("CompanyApi/companylist", "POST"),
        API_ADDBANK("CompanyApi/addbank","POST"),
        API_ADDDESIGNATION("CompanyApi/adddesignation", "POST"),
       // API_ELIGIBLE_EMPLOYEE_LIST("EmployeeApi/eligibleemployeelist","POST"),

        API_EMPLOYEELIST("EmployeeApi/employeelist", "POST"),
        API_EMPLOYEEDESLIST("EmployeeApi/designationlist", "POST"),
        API_EMPLOYEEDESLEMPIST("EmployeeApi/designationemployeelist", "POST"),
        API_ALLOWANCE_LIST("EmployeeApi/allowancecategorylist","POST"),
        API_LOAN_LIST("EmployeeApi/employeeloanlist","POST"),
        API_ATTENDANCE_LIST("EmployeeApi/employeeattendancelisttoday","POST"),
        API_EMP_HOLIDAY_LIST("EmployeeApi/holidaylist","POST"),
        API_ADD_HOLIDAY("EmployeeApi/addholiday","POST"),
        API_ADD_ALLOWANCE("EmployeeApi/addallowancecategory","POST"),

        API_COMPANYVEHICLELIST("VehicleApi/companyvehiclelist", "POST"),
        API_VEHICLELIST("VehicleApi/Vehiclelist", "POST"),
        API_VEHICLE_MANUFACTURE_LIST("VehicleApi/manufacturerlist", "POST"),
        API_VEHICLE_MODEL_LIST("VehicleApi/modellist", "POST"),
        API_VEHICLE_INSURANCE_COMPANY_LIST("VehicleApi/insurancecompanylist", "POST"),
        API_VEHICLE_INSURANCE_TYPE_LIST("VehicleApi/insurancetypelist", "POST"),
        API_VEHICLE_EXPENSE_TYPE_LIST("VehicleApi/vehicleexpenselist", "POST"),


        API_USER_LOGINDETAILS("userApi/logindetailslist","POST"),
        API_USER_ATTENDANCE("userApi/attendancelist","POST"),
        API_USER_WORK("userApi/workreportlist","POST"),
        API_USER_WORKREPORT("userApi/attendancelistreport","POST"),

        API_PAYROLL_ADVANCELIST("payrollApi/advancelist","POST"),



        API_INTERVIEW_LIST("RecruitmentApi/interviewlist","POST"),
        API_VISALIST("VisaApi/visalist", "POST"),
        API_CMPY_DOC_EXPIRY_LIST("CompanyApi/company_doc_exp_list", "POST"),
        API_EMPY_DOC_EXPIRY_LIST("EmployeeApi/employee_doc_exp_list", "POST"),
        API_CMPY_DOC_STATUS_LIST("CompanyApi/company_doc_status_list", "POST"),
        API_EMPY_DOC_STATUS_LIST("EmployeeApi/employee_doc_status_list", "POST"),
        API_VISA_TYPE_LIST("VisaApi/visatypelist", "POST"),
        API_VISA_CATEGORY_LIST("VisaApi/visacategorylist", "POST"),
        API_VISA_SPONSOR_LIST("VisaApi/visasponsorlist", "POST"),
        API_VISA_CLIENT_LIST("VisaApi/visaclientlist", "POST"),
        API_VISA_PAYMENT_LIST("VisaApi/visapaymentlist", "POST"),
        API_VISA_ISSUED_LIST("VisaApi/issuedvisalist", "POST"),
        API_VISA_ENTERED_LIST("VisaApi/visaenteredlist", "POST"),
        API_VISA_NOTENTERED_LIST("VisaApi/visanotenteredlist", "POST"),
        API_VISA_REPORT_LIST("VisaApi/visareportlist", "POST"),
        API_VEHICLE_DOC_STATUS_LIST("VehicleApi/vehicle_doc_status_list", "POST"),

        API_ADDMANUFACTURER("VehicleApi/addmanufacturer", "POST"),
        API_ADD_MODEL("VehicleApi/addmodel", "POST"),
        API_ADDINSURANCECOMPANY("VehicleApi/addinsurancecompany", "POST"),
        API_ADDINSURANCETYPE("VehicleApi/addinsurancetype", "POST"),
        API_ADDEXPENSETYPE("VehicleApi/addvehicleexpense", "POST"),

        API_ADD_EVENTS("LoginApi/addevents", "POST"),

        API_ADD_COUNTRY("RecruitmentApi/addcountry", "POST"),
        API_ADD_JOB("RecruitmentApi/addjob", "POST"),
        API_ADD_STATE("RecruitmentApi/addstate", "POST"),
        API_ADD_CATEGORY("RecruitmentApi/addcategory", "POST"),
        API_ADD_SKILL("RecruitmentApi/addskill", "POST"),
        API_ADDLOGIN("userApi/addlogin","POST"),
        API_CANDIDATE_TO_EMPLOYEE_LIST("RecruitmentApi/candidatetoemployeelist","POST"),

        API_ADD_QUALIFICATION("RecruitmentApi/addqualification", "POST"),

        API_LIST_EVENTS("LoginApi/eventlist", "POST"),

        API_COMPANY_NOTIFICATION("CompanyApi/companynotification", "POST"),
        API_VISA_NOTIFICATION("VisaApi/visanotification", "POST"),
        API_EMPLOYEE_NOTIFICATION("EmployeeApi/employeenotification", "POST"),
        API_COMPANYEMPLOYEELIST("EmployeeApi/companyemployeelist", "POST"),
        API_EMPLOYEE_DETAILS("EmployeeApi/employeedetails", "POST"),
        API_COMPANY_DETAILS("CompanyApi/companydetails", "POST"),
        API_VISA_DETAILS("VisaApi/visadetails", "POST"),
        API_VEHICLE_NOTIFICATION("VehicleApi/vehiclenotification", "POST"),
        API_VEHICLE_DETAILS("VehicleApi/vehicledetails", "POST"),

        API_COMPANI_ALL_NOTIFICATION("CompanyApi/companyallnotification", "POST"),
        API_EMPLOYEE_ALL_NOTIFICATION("EmployeeApi/employeeallnotificationday", "POST"),
        API_VEHICLE_ALL_NOTIFICATION("VehicleApi/vehicleallnotification", "POST"),
        API_VISA_ALL_NOTIFICATION("VisaApi/visanotification", "POST"),
        API_INTERVIEW_DETAILS("RecruitmentApi/interviewdetails","POST"),

        API_PAYROLL_SALARYLIST("payrollApi/basicsalarylist","POST"),
        API_REPORTSALARYLIST("payrollApi/reportsalarylist","POST"),
        API_PAYROLL_ADVANCELPAIDLIST("payrollApi/reportadvancepaidlist","POST"),
        API_PAYROLL_DEDUCTIONCATEGORYLIST("payrollApi/deductioncategorylist","POST"),
        API_ADDDEDUCTIONCATEGORY("payrollApi/adddeductioncategory","POST"),
        API_COMPANY_ADVANCEPENDINGLIST("payrollApi/reportadvancependinglist","POST"),
        API_PAYROLL_HOLIDAYLIST("payrollApi/manageholidaylist","POST"),

        API_RECRUITMENT_AGENT_LIST("RecruitmentApi/agentlist","POST"),
        API_MEDICAL_CLEARED_LIST("RecruitmentApi/medicalclearedlist","POST"),
        API_MEDICAL_FAILED_LIST("RecruitmentApi/medicalfailedlist","POST"),
        API_UPDATE_MEDICAL_STATUS_LIST("RecruitmentApi/finalcandidatelist","POST"),
        API_SELECTED_CANDIDATE_LIST("RecruitmentApi/selectedcandidatelist","POST"),
        API_VISAPROCESS_VISA_APPLIED_LIST("RecruitmentApi/visaappliedlist","POST"),
        API_VISAPROCESS_VISA_ISSUED_LIST("RecruitmentApi/visaapprovedlist","POST"),
        API_VISAREJECTEDLIST("RecruitmentApi/visarejectedlist","POST"),

        API_REPORT_SELECTION_STATUS_LIST("RecruitmentApi/reportcandidateselected","POST"),
        API_REPORT_MEDICAL_STATUS_LIST("RecruitmentApi/reportmedicalstatus","POST"),
        API_REPORT_TRAVELING_CANDIDATES_LIST("RecruitmentApi/reporttravellingcandidatelist","POST"),
        API_REPORT_TRAVELLED_CANDIDATE("RecruitmentApi/reporttravelledcandidatelist","POST"),
        API_REPORT_VISA_PROCESS("RecruitmentApi/reportprocessvisa","POST"),
        API_RECRUITMENT_COUNTRY_LIST("RecruitmentApi/countrylist","POST"),
        API_RECRUITMENT_STATE_LIST("RecruitmentApi/statelist","POST"),
        API_RECRUITMENT_QUALIFICATION_LIST("RecruitmentApi/qualficationlist","POST"),
        API_RECRUITMENT_JOB_LIST("RecruitmentApi/joblist","POST"),
        API_RECRUITMENT_CATEGORY_LIST("RecruitmentApi/categorylist","POST"),
        API_RECRUITMENT_SKILL_LIST("RecruitmentApi/skilllist","POST"),
        API_CANDIDATELIST("RecruitmentApi/candidatelist","POST"),
        API_VISA_SELECTED_CANDIDATE_LIST("RecruitmentApi/processvisacandidatelist","POST"),
        API_RETESTCANDIDATELIST("RecruitmentApi/retestcandidatelist","POST"),
        API_REJECTEDCANDIDATELIST("RecruitmentApi/rejectcandidatelist","POST"),
        API_ONHOLDCANDIDATELIST("RecruitmentApi/onholdcandidatelist","POST"),
        API_NOTIFIEDCANDIDATELIST("RecruitmentApi/notifiedcandidatelist","POST"),
        API_TRAVELLINGCANDIDATELIST("RecruitmentApi/travellingcandidatelist","POST"),
        API_TRAVELLEDCANDIDATELIST("RecruitmentApi/travelledcandidatelist","POST"),

        API_EMPLOYEE_REPORT("EmployeeApi/reportemployee", "POST"),
        API_COMPANY_REPORT("CompanyApi/companylistreport", "POST"),
        API_VEHICLE_REPORT("VehicleApi/reportvehiclelist", "POST"),
        API_VISA_REPORT("VisaApi/visareportlist", "POST"),
        API_ADDVISATYPE("VisaApi/addvisatype", "POST"),
        API_ADD_VISA_CATEGORY("VisaApi/addvisacategory", "POST"),
        API_ADDVISASPONSOR("VisaApi/addvisasponsor", "POST"),
        API_ADDVISACLIENT("VisaApi/addvisaclient", "POST"),

        API_COMPANY_ARCHIVE("CompanyApi/companyarchive","POST"),
        API_EMPLOYEE_ARCHIVE("EmployeeApi/employeearchive","POST"),
        API_VEHICLE_ARCHIVE("VehicleApi/vehiclearchive","POST"),
        API_AGENT_ARCHIVE("RecruitmentApi/agentarchive","POST"),
        API_INTERVIEW_ARCHIVE("RecruitmentApi/interviewarchivelist","POST"),
        API_CANDIDATE_ARCHIVE("RecruitmentApi/candidatearchivelist","POST"),
        API_SELECTED_CANDIDATE_ARCHIVE("RecruitmentApi/selectedcandidatearchivelist","POST"),
        API_RETEST_CANDIDATE_ARCHIVE("RecruitmentApi/retestcandidatearchivelist","POST"),
        API_REJECTED_CANDIDATE_ARCHIVE("RecruitmentApi/rejectcandidatearchivelist","POST"),
        API_RP_VISA_MEDICAL_NOTI("RecruitmentApi/rpvisamedicalnotificationday","POST"),
        API_VISA_VALIDITY_EXPIRY_NOTI("RecruitmentApi/visavalidityexpiryday","POST"),

        API_ELIGIBLE_EMPLOYEE_LIST("EmployeeApi/eligibleemployeelist","POST"),
        API_CONFIMED_TRAVEL_LIST("EmployeeApi/confirmedtravellist","POST"),
        API_EMPLOYEE__ANNUAL_CANCELLED("EmployeeApi/travelcancelledlist","POST"),
        API_EMPLOYEE__ANNUAL_TRAVELLED("EmployeeApi/employeetravelledlist","POST"),
        API_EMP_OTHER_TRAVELLED_LIST("EmployeeApi/othertravelled","POST"),
        API_EMP_OTHER_CANCELLED_LIST("EmployeeApi/othertravellcancelled","POST"),
        API_EMP_OTHER_TICKETBOOKED_LIST("EmployeeApi/othertravellbookedist","POST"),
        API_REPORT_ANNUAL_TRAVELLEED_BOOKED("AccountsApi/accountsreportannualtravellist","POST"),

        API_COMPANY_CAMPLIST("companyApi/camplist","POST"),
        API_LEAVE_LIST("EmployeeApi/employeeleavelist","POST"),
        API_ADDCAMP("CompanyApi/addcamp","POST"),
        API_COMPANY_CAMPROOMLIST("companyApi/camproomlist","POST"),
        API_COMPANY_ADDCAMPROOM("companyApi/addcamproom","POST"),
        API_COMPANY_BANKDETAILS("CompanyApi/banklist","POST"),
        API_SELECTEDCANDIDATERECRUITMENT("RecruitmentApi/selectedcandidatelist","POST"),
        API_ATTENDANCEREPORT("userApi/reportattendancelist","POST"),
        API_EMPLOYEE_REASON("employeeApi/leavereasonlist","POST"),
        API_ADD_REASON("employeeApi/addleavereason","POST"),
        API_AGENT_PROFILE("RecruitmentApi/agentprofile","POST"),
        API_CANDIDATE_DETAILS("RecruitmentApi/candidatedetails","POST"),
        API_ATTENDANCEREPORT_ORIGINAL("UserApi/reportattendancelist","POST");




        private final String api_name;
        private final String method;

        private APIS(final String text, String method) {
            this.api_name = text;
            this.method = method;
        }

        @Override
        public String toString() {
            return api_name;
        }
    }

    // Login


    public StringBuilder doLogin(final String userName,final  String Password) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", userName);
        params.put("password", Password);
        return sendRequest(params, APIS.API_LOGIN, "?username="+userName+"&password="+Password);

    }

    // leave list
    public StringBuilder doLeaveList(final String id, final String authorization, final String start,final String comapny_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_LEAVE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50"+"&company_id="+comapny_id);
    }

    //Other ticket booked list
    public StringBuilder doOtherTicketBookedList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMP_OTHER_TICKETBOOKED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //candidate details
    public StringBuilder doCANDIDATE_DETAILS(final String id, final String authorization, final String candidate_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("candidate_id", candidate_id);
        return sendRequest(params, APIS.API_CANDIDATE_DETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&candidate_id="+candidate_id);
    }

    public StringBuilder doOtherCancelledList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMP_OTHER_CANCELLED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder doOtherTravelledList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMP_OTHER_TRAVELLED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doEmployeeAnnualTravelledList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE__ANNUAL_TRAVELLED, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doReportAnnualTravelledBookedList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REPORT_ANNUAL_TRAVELLEED_BOOKED, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder doEmployeeAnnualCancelledlledList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE__ANNUAL_CANCELLED, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doEligibleEmployeeList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ELIGIBLE_EMPLOYEE_LIST, "?admin_id=" + id + "&Authorization=" +
                authorization + "&start=" + start + "&limit=50");
    }
    public StringBuilder doConfirmedTravelList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_CONFIMED_TRAVEL_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
// agent profile
    public StringBuilder doAgentProfile(final String id, final String authorization, final String agent_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
       /* params.put("agent_id", agent_id);*/
        return sendRequest(params, APIS.API_AGENT_PROFILE, "?admin_id="+id+"&Authorization="+
                authorization+"&agent_id="+agent_id);
    }


    public StringBuilder dosalaryList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_PAYROLL_SALARYLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doholidayList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_PAYROLL_HOLIDAYLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }


    // Dashboard
    public StringBuilder doDashboardLogin(final String id, final String authorization) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_DASHBOARD, "?admin_id="+id+"&Authorization="+
                authorization);
    }

    public StringBuilder doadvancependingList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_ADVANCEPENDINGLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doadvancepaidList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_PAYROLL_ADVANCELPAIDLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doreportsalaryList(final String id, final String authorization,final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REPORTSALARYLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doadddeductioncategory(final String id, final String authorization, final String deduction_category
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("deduction_category", deduction_category);
        return sendRequest(params, APIS.API_ADDDEDUCTIONCATEGORY, "?admin_id="+id+"&Authorization="+
                authorization+"&deduction_category="+deduction_category);
    }
    public StringBuilder dodeductioncategoryList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_PAYROLL_DEDUCTIONCATEGORYLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //recruitment rp visa medical notification
    public StringBuilder dorpvisamedicalnotificationday(final String id, final String authorization,final String day
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("day", day);
        return sendRequest(params, APIS.API_RP_VISA_MEDICAL_NOTI, "?admin_id="+id+"&Authorization="+
                authorization+"&day="+day);
    }
    //recruitment visa validity expiry noti list
    public StringBuilder dovisavalidityexpiryday(final String id, final String authorization,final String day
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("day", day);
        return sendRequest(params, APIS.API_VISA_VALIDITY_EXPIRY_NOTI, "?admin_id="+id+"&Authorization="+
                authorization+"&day="+day);
    }

    // visa type list load
    public StringBuilder doVisaTypeListLoad(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_TYPE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=5000");
    }

    // add visa type
    public StringBuilder doAddVisaType(final String id, final String authorization, final String data) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDVISATYPE, "?admin_id="+id+"&Authorization="+
                authorization+"&visa_type="+data);
    }

    /*// add visa Category
    public StringBuilder doAddVisaCategory(final String id, final String authorization, final String data
            , final String vid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDVISACATEGORY, "?admin_id="+id+"&Authorization="+
                authorization+"&visa_category="+data+"&visa_type_id="+vid);
    }*/

    public StringBuilder doAddVisaCategory(final String id, final String authorization,final String visa_category,final String visa_type_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("visa_category", visa_category);
        params.put("visa_type_id", visa_type_id);
        return sendRequest(params, APIS.API_ADD_VISA_CATEGORY, "?admin_id="+id+"&Authorization="+
                authorization+"&visa_category="+visa_category+"&visa_type_id="+visa_type_id);
    }

    // add visa Sponsor
    public StringBuilder doAddSponsor(final String id, final String authorization, final String data,
                                      final String mail) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDVISASPONSOR, "?admin_id="+id+"&Authorization="+
                authorization+"&name="+data+"&email="+mail);
    }

    // add visa client
    public StringBuilder doAddClient(final String id, final String authorization, final String data,
                                     final String mail) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDVISACLIENT, "?admin_id="+id+"&Authorization="+
                authorization+"&name="+data+"&email="+mail);
    }


    // All companylist
    public StringBuilder doCompanyFullList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANYLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50000");
    }

    // All employeelist
    public StringBuilder doEmployeeFullList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50000");
    }

    public StringBuilder docandidatetoemployee(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_CANDIDATE_TO_EMPLOYEE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder doNotifiedcandidateList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_NOTIFIEDCANDIDATELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doTravellingcandidateList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_TRAVELLINGCANDIDATELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doTravelledcandidateList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_TRAVELLEDCANDIDATELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //recruitment medical cleared list
    public StringBuilder domedicalclearedList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_MEDICAL_CLEARED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    //recruitment medical failed list
    public StringBuilder domedicalfailedList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_MEDICAL_FAILED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //recruitment update medical status list
    public StringBuilder doupdatemedicalstatusList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_UPDATE_MEDICAL_STATUS_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder doselectedcandidateList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_SELECTED_CANDIDATE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder dorecruitmentvisaappliededList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISAPROCESS_VISA_APPLIED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder dorecruitmentvisaissuedList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISAPROCESS_VISA_ISSUED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doVisaRejectedList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISAREJECTEDLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //interview details
    public StringBuilder doInterviewDetails(final String id, final String authorization,final String interview_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("interview_id", interview_id);
        return sendRequest(params, APIS.API_INTERVIEW_DETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&interview_id="+interview_id);
    }

    // INTERVIEW list
    public StringBuilder doInterviewlist(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_INTERVIEW_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doCandidateList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_CANDIDATELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder dovisaselectedcandidatelist(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_SELECTED_CANDIDATE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doRetestCandidateList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RETESTCANDIDATELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doRejectedCandidateList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REJECTEDCANDIDATELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doOnholdCandidateList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ONHOLDCANDIDATELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doSelectedCandidateListRecruitment(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_SELECTEDCANDIDATERECRUITMENT, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder docamproomList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_CAMPROOMLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder docampList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_CAMPLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder doAddCampRoom(final String id, final String authorization, final String camp_id,final String camp_room
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("camp_id", camp_id);
        params.put("camp_room", camp_room);

        return sendRequest(params, APIS.API_COMPANY_ADDCAMPROOM, "?admin_id="+id+"&Authorization="+
                authorization+"&camp_id="+camp_id+"&camp_room="+camp_room);
    }

    public StringBuilder doAddCamp(final String id, final String authorization, final String company_id,final String camp_location,final  String camp_name,final  String camp_address,final String camp_manager,final String camp_contact,final String camp_email,final String  camp_intake
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("company_id", company_id);
        params.put("camp_location", camp_location);
        params.put("camp_name", camp_name);
        params.put("camp_address", camp_address);
        params.put("camp_manager", camp_manager);
        params.put("camp_contact", camp_contact);
        params.put("camp_email", camp_email);
        params.put("camp_intake", camp_intake);
        return sendRequest(params, APIS.API_ADDCAMP, "?admin_id="+id+"&Authorization="+
                authorization+"&company_id="+company_id+"&camp_location="+camp_location+"&camp_name="+camp_name+"&camp_address="+camp_address+"&camp_manager="+camp_manager+"&camp_contact="+camp_contact+"&camp_email="+camp_email+"&camp_intake="+camp_intake);
    }


    // report Medical status list
    public StringBuilder doReportMedicalStatus(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REPORT_MEDICAL_STATUS_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //report visa process
    public StringBuilder doreportvisaprocess(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REPORT_VISA_PROCESS, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //report travelled candidate
    public StringBuilder doreporttravelledcandidate(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REPORT_TRAVELLED_CANDIDATE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    // report traveling candidates status list
    public StringBuilder doReportTravelingCandidatesStatus(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REPORT_TRAVELING_CANDIDATES_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }


    //add Bank
    public StringBuilder doAddBank(final String id, final String authorization, final String bname,final String bbranch,final  String bcode,final  String bcountry
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("bank_name", bname);
        params.put("bank_branch", bbranch);
        params.put("bank_code", bcode);
        params.put("bank_country", bcountry);
        return sendRequest(params, APIS.API_ADDBANK, "?admin_id=" + id + "&Authorization=" +
                authorization + "&bank_name=" + bname + "&bank_branch=" + bbranch + "&bank_code=" + bcode + "&bank_country=" + bcountry);
    }

    //attendance list
    public StringBuilder doattendanceList(final String id, final String authorization, final String start,final String date) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("today",date);
        return sendRequest(params, APIS.API_ATTENDANCE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50"+"&today="+date);
    }
    ////attendance report
    public StringBuilder doAttendanceReport(final String id, final String authorization,
                                          final String start,final String designation,
                                          final String employee_id,final String year,final String month,final String day) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("designation",designation);
        params.put("employee_id",employee_id);
        params.put("year",year);
        params.put("month",month);
        params.put("day",day);
        return sendRequest(params, APIS.API_ATTENDANCEREPORT, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50"+"&designation="+designation+"&employee_id="+employee_id+"&year="+year+"&month="+month+"&day="+day);
    }
    ////attendance report ORIGINAL
    public StringBuilder doAttendanceReportoriginal(final String id, final String authorization,
                                           final String designation,
                                            final String employee_id,final String year,final String month,final String day,final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("designation",designation);
        params.put("employee_id",employee_id);
        params.put("year",year);
        params.put("month",month);
        params.put("day",day);
        return sendRequest(params, APIS.API_ATTENDANCEREPORT_ORIGINAL, "?admin_id="+id+"&Authorization="+
                authorization+"&designation="+designation+"&employee_id="+employee_id+"&year="+year+"&month="+month+"&day="+day+"&start="+start+"&limit=50");
    }



    //company archive
    public StringBuilder docompanyarchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //employee archive
    public StringBuilder doemployeearchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doReasonList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE_REASON, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //vehicle archive
    public StringBuilder dovehiclearchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //agent archive
    public StringBuilder doagentarchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_AGENT_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //interview archive
    public StringBuilder dointerviewarchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_INTERVIEW_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }


    //candidate archive
    public StringBuilder docandidatearchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_CANDIDATE_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //selected candidate archive
    public StringBuilder doselectedcandidatearchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_SELECTED_CANDIDATE_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }


    //retest candidate archive
    public StringBuilder doretestcandidatearchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RETEST_CANDIDATE_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //reject candidate archive
    public StringBuilder dorejectcandidatearchive(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REJECTED_CANDIDATE_ARCHIVE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder doattendanceList1(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_USER_ATTENDANCE, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // report selection status list
    public StringBuilder doReportSelectionStatus(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_REPORT_SELECTION_STATUS_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // Vehicle Manufacture List
    public StringBuilder doVehicleManufactureList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_MANUFACTURE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // Vehicle Model List
    public StringBuilder doVehicleModelList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_MODEL_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // Vehicle Insurance Company List
    public StringBuilder doVehicleInsuranceCompanyList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_INSURANCE_COMPANY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // Vehicle Insurance Type List
    public StringBuilder doVehicleInsuranceTypeList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_INSURANCE_TYPE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // Vehicle Expense Type List
    public StringBuilder doVehicleExpenseTypeList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_EXPENSE_TYPE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //allowance list
    public StringBuilder doAllowanceList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ALLOWANCE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }


    // add allowance
    public StringBuilder doAddAllowance(final String id, final String authorization,
                                        String Allowancename) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADD_ALLOWANCE, "?admin_id="+id+"&Authorization="+
                authorization+"&allowance_category="+Allowancename);
    }
    public StringBuilder doAddReason(final String id, final String authorization,
                                        String reason) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("reason", reason);
        return sendRequest(params, APIS.API_ADD_REASON, "?admin_id="+id+"&Authorization="+
                authorization+"&reason="+reason);
    }

    // companylist
    public StringBuilder doCompanyList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANYLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doCompanyBankList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_BANKDETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // companylist
    public StringBuilder doCompanyeventList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANYLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=30000");
    }

    // add designation
    public StringBuilder doAddDesignation(final String id, final String authorization, final String data) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDDESIGNATION, "?admin_id="+id+"&Authorization="+
                authorization+"&designation="+data);
    }

    //recruitment agent list
    public StringBuilder dorecruitmentagentList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RECRUITMENT_AGENT_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //country list
    public StringBuilder dorecruitmentcountryList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RECRUITMENT_COUNTRY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //state list
    public StringBuilder dorecruitmentstateList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RECRUITMENT_STATE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //Skill list
    public StringBuilder dorecruitmentskillList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RECRUITMENT_SKILL_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    //Qualification
    public StringBuilder dorecruitmentqualificationList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RECRUITMENT_QUALIFICATION_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    //job list
    public StringBuilder dorecruitmentjobList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RECRUITMENT_JOB_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    //category list
    public StringBuilder dorecruitmentcategorylistList(final String id, final String authorization, final String start
    ) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_RECRUITMENT_CATEGORY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }



    //company vehicle list
    public StringBuilder docompanyvehicleList(final String id, final String authorization, final String start,
                                              final String cpyid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANYVEHICLELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50"+"&company_id="+cpyid);
    }

    // employee designation list
    public StringBuilder doEMPDESGList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEEDESLIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=30000");
    }

    // employeelist
    public StringBuilder doEmployeeList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // employeelist
    public StringBuilder doEmployeeSearchList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=30000");
    }

    // employee designation list
    public StringBuilder doEmployeedesList(final String id, final String authorization, final String start,
                                           final String des) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEEDESLEMPIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50"+"&designation="+des);
    }
    //vehicle List
    public StringBuilder doVehicleList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa list
    public StringBuilder doVisaList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISALIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa issued list
    public StringBuilder doVisaIssuedList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_ISSUED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa entered list
    public StringBuilder doVisaEnteredList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_ENTERED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa not entered list
    public StringBuilder doVisaNotEnteredList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_NOTENTERED_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa not entered list
    public StringBuilder doVisaReportList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_REPORT_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa type list
    public StringBuilder doVisaTypeList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_TYPE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa category list
    public StringBuilder doVisacategoryList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_CATEGORY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa sponsor list
    public StringBuilder doVisasponsorList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_SPONSOR_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa client list
    public StringBuilder doVisaclientList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_CLIENT_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // visa payment list
    public StringBuilder doVisaPaymentList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_PAYMENT_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // cmpy doc expiry list
    public StringBuilder doCMPY_DOC_EXPIRY_LIST(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_CMPY_DOC_EXPIRY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // empy doc expiry list
    public StringBuilder doEMPY_DOC_EXPIRY_LIST(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPY_DOC_EXPIRY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // cmpy doc status list
    public StringBuilder doCMPY_DOC_STATUS_LIST(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_CMPY_DOC_STATUS_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // empy doc status list
    public StringBuilder doEMPY_DOC_STATUS_LIST(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPY_DOC_STATUS_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // vehicle doc status list
    public StringBuilder doVEHICLE_DOC_STATUS_LIST(final String id, final String authorization,
                                                   final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_DOC_STATUS_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // company details
    public StringBuilder doCOMAPNY_DETAILS(final String id, final String authorization,
                                           final String cid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_DETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&company_id="+cid);
    }
    // vehicle details
    public StringBuilder doVEHICLE_DETAILS(final String id, final String authorization,
                                           final String vid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_DETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&vehicle_id="+vid);
    }
    // visa details
    public StringBuilder doVISA_DETAILS(final String id, final String authorization,
                                        final String vid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_DETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&visa_id="+vid);
    }
    // employee details
    public StringBuilder doEMPLOYEE_DETAILS(final String id, final String authorization,
                                            final String eid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE_DETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&employee_id="+eid);
    }
    // company all notification
    public StringBuilder doCOMPANYALLNOTIFICATIONLIST(final String id, final String authorization,
                                                      final String notification_start_date) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANI_ALL_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&notification_start_date="+notification_start_date);
    }

    // employee all notification
    public StringBuilder doEMPLOYEEALLNOTIFICATIONLIST(final String id, final String authorization,
                                                       final String day) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE_ALL_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&day="+day);
    }

    // vehicle all notification
    public StringBuilder doVEHICLEALLNOTIFICATIONLIST(final String id, final String authorization,
                                                      final String day) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_ALL_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&day="+day);
    }

    // visa all notification
    public StringBuilder doVISAALLNOTIFICATIONLIST(final String id, final String authorization,
                                                   final String notification_start_date) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_ALL_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&notification_start_date="+notification_start_date);
    }
    // comapny notification
    public StringBuilder doCMPYNOTIFICATIONLIST(final String id, final String authorization,
                                                final String notification_start_date,
                                                final String title) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&notification_start_date="+notification_start_date
                +"&title="+title);
    }
    // visa notification
    public StringBuilder doVISANOTIFICATIONLIST(final String id, final String authorization,
                                                final String notification_start_date,
                                                final String title) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&notification_start_date="+notification_start_date
                +"&title="+title);
    }

    // employee notification
    public StringBuilder doEMPLOYEENOTIFICATIONLIST(final String id, final String authorization,
                                                    final String notification_start_date,
                                                    final String title) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&notification_start_date="+notification_start_date
                +"&title="+title);
    }
    // vehicle notification
    public StringBuilder doVEHICLENOTIFICATIONLIST(final String id, final String authorization,
                                                   final String notification_start_date,
                                                   final String title) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_NOTIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&notification_start_date="+notification_start_date
                +"&title="+title);
    }
    // add events
    public StringBuilder doAddEvents(final String id, final String authorization,
                                     String Coordinator, String Email,
                                     String Date, String Time, String Company,
                                     String Title, String Description) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADD_EVENTS, "?admin_id="+id+"&Authorization="+
                authorization+"&event_coordinator="+Coordinator+"&contact_email="
                +Email+"&event_date="+Date+"&event_time="+Time+"&company_name="+Company
                +"&event_title="+Title+"&description="+Description);
    }

    // event list
    public StringBuilder doEVENT_LIST(final String id, final String authorization, final String year) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_LIST_EVENTS, "?admin_id="+id+"&Authorization="+
                authorization+"&year="+year);
    }

    // insurance type list
    public StringBuilder doInsuranceTypeList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_INSURANCE_TYPE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // insurance company list
    public StringBuilder doInsuranceCompanyList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_INSURANCE_COMPANY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    // Expenses type list
    public StringBuilder doExpensesTypeList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_EXPENSE_TYPE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    // model list
    public StringBuilder doModelList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_MODEL_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // Manufacturer list
    public StringBuilder doManufacturerList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_MANUFACTURE_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // add Manufacturer
    public StringBuilder doAddManufacturer(final String id, final String authorization, final String data) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDMANUFACTURER, "?admin_id="+id+"&Authorization="+
                authorization+"&manufacturer="+data);
    }

    //add model
    public StringBuilder doAddModel(final String id, final String authorization, final String model,final String manufacturer) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("model",model);
        params.put("manufacturer",manufacturer);
        return sendRequest(params, APIS.API_ADD_MODEL, "?admin_id="+id+"&Authorization="+
                authorization+"&model_name="+model+"&manufacturer_id="+manufacturer);
    }

    // add Insurance Company
    public StringBuilder doAddInsuranceCompany(final String id, final String authorization, final String data) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDINSURANCECOMPANY, "?admin_id="+id+"&Authorization="+
                authorization+"&insurance_company="+data);
    }

    // add Insurance Type
    public StringBuilder doAddInsuranceType(final String id, final String authorization, final String data) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDINSURANCETYPE, "?admin_id="+id+"&Authorization="+
                authorization+"&insurance="+data);
    }

    // add Insurance Type
    public StringBuilder doAddExpensesType(final String id, final String authorization, final String data) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADDEXPENSETYPE, "?admin_id="+id+"&Authorization="+
                authorization+"&vehicle_expense="+data);
    }

    //add skill
    public StringBuilder doAddSkill(final String id, final String authorization, final String job,final String category,final String skill) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("job",job);
        params.put("category",category);
        params.put("skill",skill);
        return sendRequest(params, APIS.API_ADD_SKILL, "?admin_id="+id+"&Authorization="+
                authorization+"&job="+job+"&category="+category+"&skill="+skill);
    }

    //add category
    public StringBuilder doAddCategory(final String id, final String authorization, final String job,final String category) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("job",job);
        params.put("category",category);
        return sendRequest(params, APIS.API_ADD_CATEGORY, "?admin_id="+id+"&Authorization="+
                authorization+"&job="+job+"&category="+category);
    }

    //add state
    public StringBuilder doAddState(final String id, final String authorization, final String country,final String state) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("country",country);
        params.put("state",state);
        return sendRequest(params, APIS.API_ADD_STATE, "?admin_id="+id+"&Authorization="+
                authorization+"&country="+country+"&state="+state);
    }

    //add country
    public StringBuilder doAddCountry(final String id, final String authorization, final String country) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("country",country);
        return sendRequest(params, APIS.API_ADD_COUNTRY, "?admin_id="+id+"&Authorization="+
                authorization+"&country="+country);
    }

    //add job
    public StringBuilder doAddJobposition(final String id, final String authorization, final String job) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("job",job);
        return sendRequest(params, APIS.API_ADD_JOB, "?admin_id="+id+"&Authorization="+
                authorization+"&job="+job);
    }


    //add Qualification
    public StringBuilder doAddQualification(final String id, final String authorization, final String qualification) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("qualification",qualification);
        return sendRequest(params, APIS.API_ADD_QUALIFICATION, "?admin_id="+id+"&Authorization="+
                authorization+"&qualification="+qualification);
    }

    // company employee list
    public StringBuilder docompanyemployeeList(final String id, final String authorization, final String start,
                                               final String cpyid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANYEMPLOYEELIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50"+"&company_id="+cpyid);
    }

    //loan list
    public StringBuilder doLoanList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_LOAN_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    // add holiday
    public StringBuilder doAddHoliday(final String id, final String authorization,
                                      String Holiday) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_ADD_HOLIDAY, "?admin_id="+id+"&Authorization="+
                authorization+"&holiday="+Holiday);
    }

    //emp holiday list
    public StringBuilder doEmpHolidayList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMP_HOLIDAY_LIST, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=100");
    }

    // company report
    public StringBuilder doCompanyReport(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_COMPANY_REPORT, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50000");
    }

    // EMPLOYEE report
    public StringBuilder doEmployeeReport(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_EMPLOYEE_REPORT, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50000");
    }


    // vehicle report
    public StringBuilder doVehicleReport(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VEHICLE_REPORT, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50000");
    }

    // visa report
    public StringBuilder doVisaReport(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_VISA_REPORT, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50000");
    }

    public StringBuilder doUserLoginList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_USER_LOGINDETAILS, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }

    public StringBuilder doworkList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_USER_WORK, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }
    public StringBuilder doAddLogin(final String id, final String authorization,
                                    final String company,final String designation,
                                    final  String employee_id,final  String employee_name,
                                    final String username,final String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        params.put("company", company);
        params.put("designation", designation);


        params.put("employee_id", employee_id);
        params.put("employee_name", employee_name);
        params.put("username", username);
        params.put("password", password);

        return sendRequest(params, APIS.API_ADDLOGIN, "?admin_id="+id+"&Authorization="+
                authorization+"&company="+company+"&designation="+designation+
                "&employee_id="+employee_id+"&employee_name="+employee_name.replaceAll(" ","%20")+"&username="+username+"&password="+password);
    }

    public StringBuilder doattendancereportList(final String id, final String authorization, final String start) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("admin_id", id);
        params.put("Authorization", authorization);
        return sendRequest(params, APIS.API_USER_WORKREPORT, "?admin_id="+id+"&Authorization="+
                authorization+"&start="+start+"&limit=50");
    }





    public StringBuilder sendRequest(HashMap<String, String> params, APIS api, String URL) {

        // Create a new HttpClient and Post Header
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
        HttpClient httpclient = new DefaultHttpClient(httpParams);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
            }
            if (api.method.equals("POST")) {

                try {

                    HttpPost httppost = new HttpPost(SERVER_URL + api.toString()+URL.replaceAll(" ","%20").trim());
                    httppost.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
                    // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.d("111111111",SERVER_URL+api.toString()+URL.replaceAll(" ","%20").trim());
                    HttpResponse response = httpclient.execute(httppost);
                    return readStream(response.getEntity().getContent());
                } catch (HttpHostConnectException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    public StringBuilder sendRequest(HashMap<String, String> params, APIS api, String URL) {



        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        DefaultHttpClient client = new DefaultHttpClient();

        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((org.apache.http.conn.ssl.X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
        DefaultHttpClient httpclient = new DefaultHttpClient(mgr, client.getParams());

        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
            }
            if (api.method.equals("POST")) {

                try {

                    HttpPost httppost = new HttpPost(SERVER_URL + api.toString()+URL
                            .replace(" ", "%20"));
                    httppost.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
                    // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.d("111111111",SERVER_URL+api.toString()+URL);
                    HttpResponse response = httpclient.execute(httppost);
                    return readStream(response.getEntity().getContent());
                } catch (HttpHostConnectException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public StringBuilder readStream(InputStream in) throws Exception {
        StringBuilder stringBuilder = null;

        if (in == null) {
            return null;
        }

        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
        stringBuilder = new StringBuilder();
        String line = null;

        while ((line = inReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        in.close();
        return stringBuilder;
    }
}