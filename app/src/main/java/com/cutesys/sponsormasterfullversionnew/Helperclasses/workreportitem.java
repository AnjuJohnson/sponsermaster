package com.cutesys.sponsormasterfullversionnew.Helperclasses;

/**
 * Created by Owner on 14/03/2017.
 */

public class workreportitem {
    private String employee_id;
    private String employee_employment_id;
    private String full_name;
    private String attendance_date;
    private String employee_designation;
    private String report_id;


    public workreportitem(){}

    public String getemployee_id() {
        return  employee_id;
    }

    public void setemployee_id(String  employee_id) {
        this. employee_id =  employee_id;
    }
    public String getemployee_employment_id() {
        return employee_employment_id;
    }

    public void setemployee_employment_id(String employee_employment_id) {
        this.employee_employment_id= employee_employment_id;
    }
    public String getfull_name() {
        return full_name;
    }

    public void setfull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getattendance_date() {
        return attendance_date;
    }

    public void setattendance_date(String attendance_date) {
        this.attendance_date = attendance_date;
    }
    public String getemployee_designation() {
        return employee_designation;
    }

    public void setemployee_designation(String employee_designation) {
        this.employee_designation= employee_designation;
    }

    public String getreport_id() {
        return report_id;
    }

    public void setreport_id(String report_id) {
        this.report_id= report_id;
    }

}
