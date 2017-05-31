package com.cutesys.sponsormasterfullversionnew.Helperclasses;

/**
 * Created by user on 4/1/2017.
 */

public class basicsalarylist
{
    private String employee_id;
    private String full_name;
    private String employee_designation;
    private String employee_salary;
    private String company_name;


    public basicsalarylist(){}

    public String getemployee_id() {
        return  employee_id;
    }

    public void setemployee_id(String  employee_id) {
        this. employee_id =  employee_id;
    }

    public String getfull_name() {
        return full_name;
    }

    public void setfull_name(String full_name) {
        this.full_name= full_name;
    }

    public String getemployee_salary() {
        return employee_salary;
    }

    public void setemployee_salary(String employee_salary) {
        this.employee_salary= employee_salary;
    }
    public String getemployee_designation() {
        return employee_designation;
    }

    public void setemployee_designation(String employee_designation) {
        this.employee_designation= employee_designation;
    }
    public String getcompany_name() {
        return company_name;
    }

    public void setcompany_name(String company_name) {
        this.company_name= company_name;

    }

}