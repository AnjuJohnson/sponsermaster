package com.cutesys.sponsormasterfullversionnew.Helperclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Athira on 2/17/2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    ContentValues cv;
    public Context mContext;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SponsorMaster.db";

    public static final String ADMIN_DETAILS_TABLE = "admin_details_table";
    public static final String ADMIN_ID = "admin_id";
    public static final String ADMIN_QATAR_ID = "admin_qatar_id";
    public static final String ADMIN_F_NAME = "admin_f_name";
    public static final String ADMIN_L_NAME = "admin_l_name";
    public static final String ADMIN_ADDRESS = "admin_address";
    public static final String ADMIN_EMAIL = "admin_email";
    public static final String ADMIN_PHONE = "admin_phone";
    public static final String ADMIN_IMG = "admin_img";
    public static final String ADMIN_COUNTRY = "admin_country";
    public static final String ADMIN_OFFICE = "admin_office";
    public static final String ADMIN_ISSUE = "admin_issue";
    public static final String ADMIN_EXPIRY = "admin_expiry";
    public static final String ADMIN_USER = "admin_user";
    public static final String ADMIN_AUTHENTICATION = "admin_authentication_key";

    public static final String HOME_DETAILS_TABLE = "home_details_table";
    public static final String HOME_CMPY = "home_cmpy";
    public static final String HOME_EMPY = "home_empy";
    public static final String HOME_VEHICLE_VISA = "home_vehicle_visa";
    public static final String HOME_CPY_NOTIFICATION = "home_cpy_notification";
    public static final String HOME_EMP_NOTIFICATION = "home_emp_notification";
    public static final String HOME_VEHICLE_NOTIFICATION = "home_vehicle_notification";
    public static final String HOME_VISA_NOTIFICATION = "home_visa_notification";
    public static final String HOME_TOTAL_NOTIFICATION = "home_total_notification";

    private static final String CREATE_ADMIN_TABLE =
            "create table " + ADMIN_DETAILS_TABLE + " ("
                    + ADMIN_ID + " text, "
                    + ADMIN_QATAR_ID + " text, "
                    + ADMIN_F_NAME + " text, "
                    + ADMIN_L_NAME + " text, "
                    + ADMIN_ADDRESS + " text, "
                    + ADMIN_EMAIL + " text, "
                    + ADMIN_PHONE + " text, "
                    + ADMIN_IMG + " text, "
                    + ADMIN_COUNTRY + " text, "
                    + ADMIN_OFFICE + " text, "
                    + ADMIN_ISSUE + " text, "
                    + ADMIN_EXPIRY + " text, "
                    + ADMIN_USER + " text, "
                    + ADMIN_AUTHENTICATION + " text)";

    private static final String CREATE_HOME_TABLE =
            "create table " + HOME_DETAILS_TABLE + " ("
                    + HOME_CMPY + " text, "
                    + HOME_EMPY + " text, "
                    + HOME_VEHICLE_VISA + " text, "
                    + HOME_CPY_NOTIFICATION + " text, "
                    + HOME_EMP_NOTIFICATION + " text, "
                    + HOME_VEHICLE_NOTIFICATION + " text, "
                    + HOME_VISA_NOTIFICATION + " text, "
                    + HOME_TOTAL_NOTIFICATION + " text)";

    public SqliteHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_HOME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void Insert_admin_details(List<HashMap<String, String>> list) {
        for(int i=0;i<list.size();i++) {

            HashMap<String, String> map = new HashMap<String, String>();
            map = list.get(i);

            cv = new ContentValues();
            cv.put(SqliteHelper.ADMIN_ID, map.get("id"));
            cv.put(SqliteHelper.ADMIN_QATAR_ID, map.get("qatar_id"));
            cv.put(SqliteHelper.ADMIN_F_NAME, map.get("firstname"));
            cv.put(SqliteHelper.ADMIN_L_NAME, map.get("lastname"));
            cv.put(SqliteHelper.ADMIN_ADDRESS, map.get("address"));
            cv.put(SqliteHelper.ADMIN_EMAIL, map.get("email"));
            cv.put(SqliteHelper.ADMIN_PHONE, map.get("phone"));
            cv.put(SqliteHelper.ADMIN_IMG, map.get("image"));
            cv.put(SqliteHelper.ADMIN_COUNTRY, map.get("country"));
            cv.put(SqliteHelper.ADMIN_OFFICE, map.get("office"));
            cv.put(SqliteHelper.ADMIN_ISSUE, map.get("issueddate"));
            cv.put(SqliteHelper.ADMIN_EXPIRY, map.get("expirydate"));
            cv.put(SqliteHelper.ADMIN_USER, map.get("username"));
            cv.put(SqliteHelper.ADMIN_AUTHENTICATION, map.get("authentication_key"));

            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(SqliteHelper.ADMIN_DETAILS_TABLE, null, cv);
            db.close(); // Closing database connection
        }
    }

    public List<HashMap<String, String>> getadmindetails() {

        String selectQuery = "SELECT * FROM " + ADMIN_DETAILS_TABLE;
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    map = new HashMap<String, String>();
                    map.put("admin_id", cursor.getString(cursor.getColumnIndexOrThrow("admin_id")));
                    map.put("admin_qatar_id", cursor.getString(cursor.getColumnIndexOrThrow("admin_qatar_id")));
                    map.put("admin_f_name", cursor.getString(cursor.getColumnIndexOrThrow("admin_f_name")));
                    map.put("admin_l_name", cursor.getString(cursor.getColumnIndexOrThrow("admin_l_name")));
                    map.put("admin_address", cursor.getString(cursor.getColumnIndexOrThrow("admin_address")));
                    map.put("admin_email", cursor.getString(cursor.getColumnIndexOrThrow("admin_email")));
                    map.put("admin_phone", cursor.getString(cursor.getColumnIndexOrThrow("admin_phone")));
                    map.put("admin_img", cursor.getString(cursor.getColumnIndexOrThrow("admin_img")));
                    map.put("admin_country", cursor.getString(cursor.getColumnIndexOrThrow("admin_country")));
                    map.put("admin_office", cursor.getString(cursor.getColumnIndexOrThrow("admin_office")));
                    map.put("admin_issue", cursor.getString(cursor.getColumnIndexOrThrow("admin_issue")));
                    map.put("admin_expiry", cursor.getString(cursor.getColumnIndexOrThrow("admin_expiry")));
                    map.put("admin_user", cursor.getString(cursor.getColumnIndexOrThrow("admin_user")));
                    map.put("admin_authentication_key", cursor.getString(cursor.getColumnIndexOrThrow("admin_authentication_key")));
                    fillMaps.add(map);
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        } catch (Exception e) {
        }
        return fillMaps;
    }

    public void Delete_admin_details() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SqliteHelper.ADMIN_DETAILS_TABLE, null, null);
        db.close(); // Closing database connection
    }

    public void Insert_home_details(List<HashMap<String, String>> list) {
        for(int i=0;i<list.size();i++) {

            HashMap<String, String> map = new HashMap<String, String>();
            map = list.get(i);

            cv = new ContentValues();
            cv.put(SqliteHelper.HOME_CMPY, map.get("home_cmpy"));
            cv.put(SqliteHelper.HOME_EMPY, map.get("home_empy"));
            cv.put(SqliteHelper.HOME_VEHICLE_VISA, map.get("home_vehicle_visa"));
            cv.put(SqliteHelper.HOME_CPY_NOTIFICATION, map.get("home_cpy_notification"));
            cv.put(SqliteHelper.HOME_EMP_NOTIFICATION, map.get("home_emp_notification"));
            cv.put(SqliteHelper.HOME_VEHICLE_NOTIFICATION, map.get("home_vehicle_notification"));
            cv.put(SqliteHelper.HOME_VISA_NOTIFICATION, map.get("home_visa_notification"));
            cv.put(SqliteHelper.HOME_TOTAL_NOTIFICATION, map.get("home_total_notification"));

            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(SqliteHelper.HOME_DETAILS_TABLE, null, cv);
            db.close(); // Closing database connection
        }
    }

    public List<HashMap<String, String>> gethomedetails() {

        String selectQuery = "SELECT * FROM " + HOME_DETAILS_TABLE;
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    map = new HashMap<String, String>();
                    map.put("home_cmpy", cursor.getString(cursor.getColumnIndexOrThrow("home_cmpy")));
                    map.put("home_empy", cursor.getString(cursor.getColumnIndexOrThrow("home_empy")));
                    map.put("home_vehicle_visa", cursor.getString(cursor.getColumnIndexOrThrow("home_vehicle_visa")));
                    map.put("home_cpy_notification", cursor.getString(cursor.getColumnIndexOrThrow("home_cpy_notification")));
                    map.put("home_emp_notification", cursor.getString(cursor.getColumnIndexOrThrow("home_emp_notification")));
                    map.put("home_vehicle_notification", cursor.getString
                            (cursor.getColumnIndexOrThrow("home_vehicle_notification")));
                    map.put("home_visa_notification", cursor.getString
                            (cursor.getColumnIndexOrThrow("home_visa_notification")));
                    map.put("home_total_notification", cursor.getString
                            (cursor.getColumnIndexOrThrow("home_total_notification")));
                    fillMaps.add(map);
                } while (cursor.moveToNext());
            }
            // closing connection
            cursor.close();
            db.close();
        } catch (Exception e) {
        }
        return fillMaps;
    }

    public void Delete_home_details() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SqliteHelper.HOME_DETAILS_TABLE, null, null);
        db.close(); // Closing database connection
    }
}
