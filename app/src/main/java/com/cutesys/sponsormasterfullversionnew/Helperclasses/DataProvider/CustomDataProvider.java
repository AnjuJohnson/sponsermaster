package com.cutesys.sponsormasterfullversionnew.Helperclasses.DataProvider;

import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;
import java.util.List;

public class CustomDataProvider {

    private static final int MAX_LEVELS = 3;

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;

    private static List<BaseItem> mMenu = new ArrayList<>();

    public static List<BaseItem> getInitialItems() {

        List<BaseItem> rootMenu = new ArrayList<>();

        rootMenu.add(new Item("Home", R.mipmap.home, 0));
        rootMenu.add(new GroupItem("Company",R.mipmap.ic_comany, 1));
        rootMenu.add(new GroupItem("Employee",R.mipmap.ic_employee, 2));
        rootMenu.add(new GroupItem("User Management",R.mipmap.ic_usermanagment, 3));
        rootMenu.add(new GroupItem("Payroll",R.mipmap.ic_payroll, 4));
        rootMenu.add(new GroupItem("Accounts",R.mipmap.ic_accounts, 5));
        rootMenu.add(new GroupItem("Vehicle",R.mipmap.ic_vehicle, 6));
       // rootMenu.add(new GroupItem("Visa",R.mipmap.ic_visa, 7));
        rootMenu.add(new GroupItem("Notification",R.mipmap.ic_alarm, 8));
        rootMenu.add(new GroupItem("Reports",R.mipmap.ic_reports, 9));
        rootMenu.add(new GroupItem("Recruitment",R.mipmap.ic_employee, 10));
        rootMenu.add(new GroupItem("Archive",R.mipmap.ic_arcvive, 11));
        rootMenu.add(new Item("Calendar",R.mipmap.ic_cal, 12));
        rootMenu.add(new Item("My Profile",R.mipmap.ic_profile, 13));
        rootMenu.add(new Item("Logout",R.mipmap.ic_logout, 14));

        return rootMenu;
    }

    public static List<BaseItem> getSubItems(BaseItem baseItem) {

        List<BaseItem> result = new ArrayList<>();
        int level = ((GroupItem) baseItem).getLevel() + 1;
        String menuItem = baseItem.getName();

        if (!(baseItem instanceof GroupItem)) {
            throw new IllegalArgumentException("GroupItem required");
        }

        GroupItem groupItem = (GroupItem)baseItem;
        if(groupItem.getLevel() >= MAX_LEVELS){
            return null;
        }

        switch (level){
            case LEVEL_1 :
                switch (menuItem.toUpperCase()){
                    case "COMPANY" :
                        result = getListCompany();
                        break;
                    case "EMPLOYEE" :
                        result = getListEmployee();
                        break;
                    case "USER MANAGEMENT" :
                        result = getListUser();
                        break;
                    case "PAYROLL" :
                        result = getListPayroll();
                        break;
                    case "ACCOUNTS" :
                        result = getListAccounts();
                        break;
                    case "VEHICLE" :
                        result = getListVehicle();
                        break;
                    case "VISA" :
                        result = getListVisa();
                        break;
                    case "NOTIFICATION" :
                        result = getListNotification();
                        break;
                    case "REPORTS" :
                        result = getListReport();
                        break;
                    case "RECRUITMENT" :
                        result = getListRecruitment();
                        break;
                    case "ARCHIVE" :
                        result = getListArchive();
                        break;
                }
                break;

            case LEVEL_2 :
                switch (menuItem){
                    case "Settings" :
                        result = getListGroupCompanySetting();
                        break;
                    case "Time Tracker" :
                        result = getListGroupEmployeeTimeTracker();
                        break;
                    case "Leave Tracker" :
                        result = getListGroupEmployeeLeaveTracker();
                        break;
                    case "Travel Log" :
                        result = getListGroupEmployeeTravelLog();
                        break;
                    case "Settings " :
                        result = getListGroupEmployeeSetting();
                        break;
                    case "Reports" :
                        result = getListGroupPayrollReport();
                        break;
                    case "Settings  " :
                        result = getListGroupPayrollSetting();
                        break;
                    case "Travel Log " :
                        result = getListGroupAccountsTravelLog();
                        break;
                    case "Reports " :
                        result = getListGroupAccountsReport();
                        break;
                    case "Settings   " :
                        result = getListGroupAccountsSetting();
                        break;
                    case "Settings    " :
                        result = getListGroupVehicleSetting();
                        break;
                    case "Visa Details" :
                        result = getListGroupvisadetails();
                        break;
                    case "Settings     " :
                        result = getListGroupvisasettings();
                        break;
                    case "Reports  " :
                        result = getListGroupRecruitmentReport();
                        break;
                    case "Interview Process" :
                        result = getListGroupRecruitmentInterview();
                        break;
                    case "Settings      " :
                        result = getListGroupRecruitmentSettings();
                        break;
                    case "Recruitment Archive" :
                        result = getListGroupArchiveRecruitment();
                        break;
                    case "Recruitment" :
                        result = getListGroupRecruitmentNotification();
                        break;
                }
                break;
        }

        return result;
    }

    public static boolean isExpandable(BaseItem baseItem) {
        return baseItem instanceof GroupItem;
    }

    private static List<BaseItem> getListCompany(){

        List<BaseItem> list = new ArrayList<>();

        GroupItem groupItem = new GroupItem("Settings",R.mipmap.ic_empty, 15);
        groupItem.setLevel(groupItem.getLevel() + 1);

        list.add(new Item("Company List",R.mipmap.ic_empty, 16));
        //list.add(new Item("Add Company",R.mipmap.ic_empty, 17));
        list.add(new Item("Document Expiry",R.mipmap.ic_empty, 18));
        list.add(new Item("Document Status",R.mipmap.ic_empty, 19));
        list.add(groupItem);

        return list;
    }
    private static List<BaseItem> getListEmployee(){

        List<BaseItem> list = new ArrayList<>();

        GroupItem groupItem = new GroupItem("Time Tracker",R.mipmap.ic_empty, 20);
        groupItem.setLevel(groupItem.getLevel() + 1);

        GroupItem groupItem1 = new GroupItem("Leave Tracker",R.mipmap.ic_empty, 21);
        groupItem1.setLevel(groupItem1.getLevel() + 1);

        GroupItem groupItem2 = new GroupItem("Travel Log",R.mipmap.ic_empty, 22);
        groupItem2.setLevel(groupItem2.getLevel() + 1);

        GroupItem groupItem3 = new GroupItem("Settings ",R.mipmap.ic_empty, 23);
        groupItem3.setLevel(groupItem3.getLevel() + 1);

        list.add(new Item("Employee List",R.mipmap.ic_empty, 24));
        //list.add(new Item("Add Employee",R.mipmap.ic_empty, 25));
        list.add(new Item("Document Expiry",R.mipmap.ic_empty, 26));
        list.add(new Item("Document Status",R.mipmap.ic_empty, 27));
        list.add(groupItem);
        list.add(groupItem1);
       // list.add(new Item("Loan",R.mipmap.ic_empty, 28));
        list.add(groupItem2);
        list.add(groupItem3);
        return list;
    }
    private static List<BaseItem> getListUser(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("Login Details",R.mipmap.ic_empty, 29));
        //list.add(new Item("Add Login",R.mipmap.ic_empty, 30));
        list.add(new Item("Attendence List",R.mipmap.ic_empty, 31));
        list.add(new Item("Attendence Report",R.mipmap.ic_empty, 32));
        list.add(new Item("Work Report",R.mipmap.ic_empty, 33));
        return list;
    }
    private static List<BaseItem> getListPayroll(){
        List<BaseItem> list = new ArrayList<>();

        GroupItem groupItem = new GroupItem("Reports",R.mipmap.ic_empty, 34);
        groupItem.setLevel(groupItem.getLevel() + 1);

        GroupItem groupItem1 = new GroupItem("Settings  ",R.mipmap.ic_empty, 35);
        groupItem1.setLevel(groupItem1.getLevel() + 1);

        list.add(new Item("Basic Salary",R.mipmap.ic_empty, 36));
        list.add(new Item("Advance Salary",R.mipmap.ic_empty, 37));
        list.add(groupItem);
        list.add(groupItem1);
        return list;
    }
    private static List<BaseItem> getListAccounts(){
        List<BaseItem> list = new ArrayList<>();

        GroupItem groupItem1 = new GroupItem("Travel Log ",R.mipmap.ic_empty, 39);
        groupItem1.setLevel(groupItem1.getLevel() + 1);

        GroupItem groupItem2 = new GroupItem("Reports ",R.mipmap.ic_empty, 40);
        groupItem2.setLevel(groupItem2.getLevel() + 1);

        GroupItem groupItem3 = new GroupItem("Settings   ",R.mipmap.ic_empty, 41);
        groupItem3.setLevel(groupItem3.getLevel() + 1);

        list.add(new Item("Employee List",R.mipmap.ic_empty, 42));
        list.add(new Item("Leave List",R.mipmap.ic_empty, 43));
        list.add(groupItem1);
        list.add(groupItem2);
        list.add(groupItem3);
        return list;
    }
    private static List<BaseItem> getListVehicle(){

        List<BaseItem> list = new ArrayList<>();

        GroupItem groupItem = new GroupItem("Settings    ",R.mipmap.ic_empty, 44);
        groupItem.setLevel(groupItem.getLevel() + 1);

        list.add(new Item("Vehicle List",R.mipmap.ic_empty, 45));
        //list.add(new Item("Add Vehicle",R.mipmap.ic_empty, 46));
        list.add(new Item("Document Status",R.mipmap.ic_empty, 47));
        list.add(groupItem);
        return list;
    }

    private static List<BaseItem> getListVisa(){

        List<BaseItem> list = new ArrayList<>();

        GroupItem groupItem = new GroupItem("Settings     ",R.mipmap.ic_empty, 48);
        groupItem.setLevel(groupItem.getLevel() + 1);

        GroupItem groupdetails = new GroupItem("Visa Details",R.mipmap.ic_empty, 49);
        groupdetails.setLevel(groupdetails.getLevel() + 1);

        list.add(groupdetails);
        list.add(new Item("Visa Payment",R.mipmap.ic_empty, 50));
        list.add(new Item("Travel Details",R.mipmap.ic_empty, 51));
        list.add(groupItem);

        return list;
    }
    private static List<BaseItem> getListNotification(){

        List<BaseItem> list = new ArrayList<>();
        GroupItem groupdetails = new GroupItem("Recruitment",R.mipmap.ic_empty, 56);
        groupdetails.setLevel(groupdetails.getLevel() + 1);
        list.add(new Item("Company",R.mipmap.ic_empty, 52));
        list.add(new Item("Employee",R.mipmap.ic_empty, 53));
        list.add(new Item("Vehicle",R.mipmap.ic_empty, 54));
        list.add(new Item("Visa",R.mipmap.ic_empty, 55));
        list.add(groupdetails);
        return list;
    }
    private static List<BaseItem> getListReport(){

        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("Company List",R.mipmap.ic_empty, 57));
        list.add(new Item("Employee List",R.mipmap.ic_empty, 58));
        list.add(new Item("Vehicle List",R.mipmap.ic_empty, 59));
       // list.add(new Item("Visa List",R.mipmap.ic_empty, 60));
        return list;
    }
    private static List<BaseItem> getListRecruitment(){

        List<BaseItem> list = new ArrayList<>();

        GroupItem groupItem = new GroupItem("Interview Process",R.mipmap.ic_empty, 62);
        groupItem.setLevel(groupItem.getLevel() + 1);

        GroupItem groupItem2 = new GroupItem("Reports  ",R.mipmap.ic_empty, 61);
        groupItem2.setLevel(groupItem2.getLevel() + 1);

        GroupItem groupItem1 = new GroupItem("Settings      ",R.mipmap.ic_empty, 63);
        groupItem1.setLevel(groupItem1.getLevel() + 1);

        list.add(new Item("Agent List",R.mipmap.ic_empty, 64));
        list.add(groupItem);
        list.add(groupItem2);
        list.add(groupItem1);
        return list;
    }
    private static List<BaseItem> getListArchive(){

        List<BaseItem> list = new ArrayList<>();
        GroupItem groupItem = new GroupItem("Recruitment Archive",R.mipmap.ic_empty, 69);
        groupItem.setLevel(groupItem.getLevel() + 1);
        list.add(new Item("Company Archive",R.mipmap.ic_empty, 65));
        list.add(new Item("Employee Archive",R.mipmap.ic_empty, 66));
        list.add(new Item("Vehicle Archive",R.mipmap.ic_empty, 67));
        //list.add(new Item("Visa Archive",R.mipmap.ic_empty, 68));
        list.add(groupItem);
        return list;
    }

    private static List<BaseItem> getListGroupCompanySetting(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tBank Details",R.mipmap.ic_empty, 70));
        list.add(new Item("\tCamp Details",R.mipmap.ic_empty, 71));
        list.add(new Item("\tDesignation",R.mipmap.ic_empty, 72));
        return list;
    }
    private static List<BaseItem> getListGroupEmployeeTimeTracker(){
        List<BaseItem> list = new ArrayList<>();
        //list.add(new Item("\tTime Sheet",R.mipmap.ic_empty, 73));
        list.add(new Item("\tAttendence",R.mipmap.ic_empty, 74));
        return list;
    }
    private static List<BaseItem> getListGroupEmployeeLeaveTracker(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tLeave List",R.mipmap.ic_empty, 75));
        return list;
    }
    private static List<BaseItem> getListGroupEmployeeTravelLog(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tAnnual",R.mipmap.ic_empty, 76));
        list.add(new Item("\tOther",R.mipmap.ic_empty, 77));
        return list;
    }
    private static List<BaseItem> getListGroupEmployeeSetting(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tManage Holiday",R.mipmap.ic_empty, 78));
       /* list.add(new Item("\tAllowance",R.mipmap.ic_empty, 79));
        list.add(new Item("\tReason",R.mipmap.ic_empty, 130));*/
        return list;
    }
    private static List<BaseItem> getListGroupPayrollReport(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tSalary List",R.mipmap.ic_empty, 80));
        list.add(new Item("\tAdvance Paid",R.mipmap.ic_empty, 81));
        list.add(new Item("\tAdvance Pending",R.mipmap.ic_empty, 82));
        return list;
    }
    private static List<BaseItem> getListGroupPayrollSetting(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tManage Holiday",R.mipmap.ic_empty, 83));
        list.add(new Item("\tDeductions",R.mipmap.ic_empty, 84));
        list.add(new Item("\tAllowance",R.mipmap.ic_empty, 79));
        return list;
    }
    private static List<BaseItem> getListGroupAccountsTravelLog(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tAnnual",R.mipmap.ic_empty, 85));
        list.add(new Item("\tOther",R.mipmap.ic_empty, 86));
        return list;
    }
    private static List<BaseItem> getListGroupAccountsReport(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tAnnual Travel Booked",R.mipmap.ic_empty, 87));
        list.add(new Item("\tAnnual Travelled",R.mipmap.ic_empty, 88));
        list.add(new Item("\tOther Travel Booked",R.mipmap.ic_empty, 89));
        list.add(new Item("\tOther Travelled",R.mipmap.ic_empty, 90));
        list.add(new Item("\tSalary List",R.mipmap.ic_empty, 91));
        list.add(new Item("\tAdvance Paid",R.mipmap.ic_empty, 92));
        list.add(new Item("\tAdvance Pending",R.mipmap.ic_empty, 93));
        return list;
    }
    private static List<BaseItem> getListGroupAccountsSetting(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tManage Holiday",R.mipmap.ic_empty, 94));
        return list;
    }

    private static List<BaseItem> getListGroupVehicleSetting(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tManufacturer",R.mipmap.ic_empty, 95));
        list.add(new Item("\tModel",R.mipmap.ic_empty, 96));
        list.add(new Item("\tInsurance Company",R.mipmap.ic_empty, 97));
        list.add(new Item("\tInsurance Type",R.mipmap.ic_empty, 98));
        //list.add(new Item("\tExpenses Type",R.mipmap.ic_empty, 99));
        return list;
    }

    private static List<BaseItem> getListGroupvisadetails(){
        List<BaseItem> list = new ArrayList<>();
       // list.add(new Item("\tAdd New visa",R.mipmap.ic_empty, 41));
        list.add(new Item("\tNew Visa list",R.mipmap.ic_empty, 100));
        list.add(new Item("\tIssued Visa List",R.mipmap.ic_empty, 101));

        return list;
    }
    private static List<BaseItem> getListGroupvisasettings(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tVisa Settings",R.mipmap.ic_empty, 102));
       // list.add(new Item("\tReport",R.mipmap.ic_empty, 103));
        return list;
    }
    private static List<BaseItem> getListGroupRecruitmentInterview(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tInterview List",R.mipmap.ic_empty, 104));
        list.add(new Item("\tSelection Status",R.mipmap.ic_empty, 105));
        list.add(new Item("\tMedical Status",R.mipmap.ic_empty, 106));
        list.add(new Item("\tVisa Process",R.mipmap.ic_empty, 107));
        list.add(new Item("\tTravel Status",R.mipmap.ic_empty, 108));
        list.add(new Item("\tCandidate To Employee",R.mipmap.ic_empty, 109));
        return list;
    }

    private static List<BaseItem> getListGroupRecruitmentReport(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tSelection Status",R.mipmap.ic_empty, 123));
        list.add(new Item("\tMedical Status",R.mipmap.ic_empty, 124));
        list.add(new Item("\tVisa Process",R.mipmap.ic_empty, 125));
        list.add(new Item("\tTravelling Candidates",R.mipmap.ic_empty, 126));
        list.add(new Item("\tTravelled Candidates",R.mipmap.ic_empty, 127));
        return list;
    }

    private static List<BaseItem> getListGroupRecruitmentSettings(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tCountry",R.mipmap.ic_empty, 110));
        list.add(new Item("\tState",R.mipmap.ic_empty, 111));
        list.add(new Item("\tQualification",R.mipmap.ic_empty, 112));
        list.add(new Item("\tJob Position",R.mipmap.ic_empty, 113));
        list.add(new Item("\tCategory",R.mipmap.ic_empty, 114));
        list.add(new Item("\tSkill",R.mipmap.ic_empty, 115));
        //list.add(new Item("\tVisa",R.mipmap.ic_empty, 116));
        return list;
    }
    private static List<BaseItem> getListGroupArchiveRecruitment(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tAgent Archive",R.mipmap.ic_empty, 117));
        list.add(new Item("\tInterview Archive",R.mipmap.ic_empty, 118));
        list.add(new Item("\tCandidate Archive",R.mipmap.ic_empty, 119));
        list.add(new Item("\tSelected Archive",R.mipmap.ic_empty, 120));
        list.add(new Item("\tRetest Archive",R.mipmap.ic_empty, 121));
        list.add(new Item("\tRejected Archive",R.mipmap.ic_empty, 122));
        return list;
    }
    private static List<BaseItem> getListGroupRecruitmentNotification(){
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("\tVisa Validity Expiry",R.mipmap.ic_empty, 128));
        list.add(new Item("\tRP Visa Medical",R.mipmap.ic_empty, 129));
        return list;
    }
}