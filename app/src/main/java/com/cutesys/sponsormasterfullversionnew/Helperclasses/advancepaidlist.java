package com.cutesys.sponsormasterfullversionnew.Helperclasses;

/**
 * Created by Owner on 14/03/2017.
 */

public class advancepaidlist {
    private String employee_id;
    private String employee_employment_id;

    private String employee_name;

    private String employee_salary;


    private String approved_loan_amount;
    private String repay_status;
    private String type;


    public advancepaidlist(){}

    public String getemployee_id() {
        return  employee_id;
    }

    public void setemployee_id(String  employee_id) {
        this. employee_id =  employee_id;
    }

    public String getemployee_employment_id() {
        return  employee_employment_id;
    }

    public void setemployee_employment_id(String  employee_employment_id) {
        this. employee_employment_id =  employee_employment_id;
    }

    public String getemployee_name() {
        return employee_name;
    }

    public void setemployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getemployee_salary() {
        return employee_salary;
    }

    public void setemployee_salary(String employee_salary) {
        this.employee_salary= employee_salary;
    }

    public String getapproved_loan_amount() {
        return approved_loan_amount;
    }

    public void setapproved_loan_amount(String approved_loan_amount) {
        this.approved_loan_amount= approved_loan_amount;
    }

    public String getrepay_status() {
        return repay_status;
    }

    public void setrepay_status(String repay_status) {
        this.repay_status= repay_status;
    }
    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type= type;
    }



}
