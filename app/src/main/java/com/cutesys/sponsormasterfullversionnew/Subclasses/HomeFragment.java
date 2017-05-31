package com.cutesys.sponsormasterfullversionnew.Subclasses;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.CustomToast;
import com.cutesys.sponsermasterlibrary.Expandablelayout.ExpandableLayout;
import com.cutesys.sponsermasterlibrary.Progress.AVLoadingIndicatorView;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.Listener;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 3/17/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sPreferences;
    private SqliteHelper helper;
    private boolean flipcompany = true, flipemployee = true,
            flipvehicle = true, flipalert = true;

    private DashboardTask mDashboardTask;
    private DrawerLayout drawerLayout;

    private ExpandableLayout expandable_company, expandable_employee,
            expandable_visa_vehicle, expandable_notification;

    private TextView cpy_count, empy_count,
            visavehicle_count, noti_count;

    private TextView mTitle;
    private ImageView closepopup,img_drawer;

    private TextView flip_cmpy_cmpy, flip_cmpy_empy, flip_cmpy_alert,
            flip_empy_empy, flip_empy_list, flip_empy_alert,
            flip_v_v,flip_v_list,flip_v_alert,flip_noti_cpy,
            flip_noti_empy, flip_noti_v;

    private LinearLayout popuplayout;

    private SwipeRefreshLayout homerefresh;

    private AVLoadingIndicatorView alertindicator, visaindicator, empindicator,
            cpyindicator;

    Handler mHandler;
    Listener mListener;

    private int ActionPage;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        ActionPage = args.getInt("ACTION");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        helper = new SqliteHelper(getActivity(), "SponsorMaster", null, 1);
        mHandler = new Handler();


        flipcompany = true; flipemployee = true;
        flipvehicle = true; flipalert = true;

        InitIdView(rootView);
        return rootView;
    }
    private void InitIdView(View rootView) {

        mListener = (Listener)getActivity();

        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        closepopup = ((ImageView) rootView.findViewById(R.id.closepopup));
        img_drawer=(ImageView) rootView.findViewById(R.id.img_drawer);
        drawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        popuplayout = (LinearLayout) rootView.findViewById(R.id.popuplayout);
        popuplayout.setVisibility(View.GONE);

        cpyindicator = (AVLoadingIndicatorView) rootView.findViewById(R.id.cpyindicator);
        empindicator = (AVLoadingIndicatorView) rootView.findViewById(R.id.empindicator);
        visaindicator = (AVLoadingIndicatorView) rootView.findViewById(R.id.visaindicator);
        alertindicator = (AVLoadingIndicatorView) rootView.findViewById(R.id.alertindicator);

        expandable_company = (ExpandableLayout) rootView.findViewById(R.id.expandable_company);
        expandable_employee = (ExpandableLayout) rootView.findViewById(R.id.expandable_employee);
        expandable_visa_vehicle = (ExpandableLayout) rootView.findViewById(R.id.expandable_visa_vehicle);
        expandable_notification = (ExpandableLayout) rootView.findViewById(R.id.expandable_notification);

        expandable_company.setOnClickListener(this);
        expandable_employee.setOnClickListener(this);
        expandable_visa_vehicle.setOnClickListener(this);
        expandable_notification.setOnClickListener(this);

        closepopup.setOnClickListener(this);

        homerefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.homerefresh);
        homerefresh.setColorSchemeResources(
                R.color.cpygreen,
                R.color.empred,
                R.color.visablue,
                R.color.alertyellow);

        cpy_count = (TextView) rootView.findViewById(R.id.cpy_count);
        empy_count = (TextView) rootView.findViewById(R.id.empy_count);
        visavehicle_count = (TextView) rootView.findViewById(R.id.visavehicle_count);
        noti_count = (TextView) rootView.findViewById(R.id.noti_count);

        flip_cmpy_cmpy = (TextView) rootView.findViewById(R.id.flip_cmpy_cmpy);
        flip_cmpy_empy = (TextView) rootView.findViewById(R.id.flip_cmpy_empy);
        flip_cmpy_alert = (TextView) rootView.findViewById(R.id.flip_cmpy_alert);

        flip_empy_empy = (TextView) rootView.findViewById(R.id.flip_empy_empy);
        flip_empy_list = (TextView) rootView.findViewById(R.id.flip_empy_list);
        flip_empy_alert = (TextView) rootView.findViewById(R.id.flip_empy_alert);

        flip_v_v = (TextView) rootView.findViewById(R.id.flip_v_v);
        flip_v_list = (TextView) rootView.findViewById(R.id.flip_v_list);
        flip_v_alert = (TextView) rootView.findViewById(R.id.flip_v_alert);

        flip_noti_cpy = (TextView) rootView.findViewById(R.id.flip_noti_cpy);
        flip_noti_empy = (TextView) rootView.findViewById(R.id.flip_noti_empy);
        flip_noti_v = (TextView) rootView.findViewById(R.id.flip_noti_v);

        flip_cmpy_cmpy.setOnClickListener(this);
        flip_cmpy_empy.setOnClickListener(this);
        flip_cmpy_alert.setOnClickListener(this);

        flip_empy_empy.setOnClickListener(this);
        flip_empy_list.setOnClickListener(this);
        flip_empy_alert.setOnClickListener(this);

        flip_v_v.setOnClickListener(this);
        flip_v_list.setOnClickListener(this);
        flip_v_alert.setOnClickListener(this);

        flip_noti_cpy.setOnClickListener(this);
        flip_noti_empy.setOnClickListener(this);
        flip_noti_v.setOnClickListener(this);
        img_drawer.setOnClickListener(this);


        Visible_Progress();
        InitGetData(false);
         /*Fragment mfragment = new HomeFragment();
        mListener.LoadItem(mfragment);*/
       /*
       img_drawer = (ImageView) rootView.findViewById(R.id.img_drawer);
              img_drawer.setOnClickListener(this);
      */
    }
    @Override
    public void onStart() {
        super.onStart();

        homerefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                InitGetData(true);
            }
        });

        switch (ActionPage){
            case 0:
                popuplayout.setVisibility(View.VISIBLE);

                mTitle.setText("Select Company");
                Fragment mFragment = new FlipFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("ACTION",0);
                mFragment.setArguments(bundle);

                Load_Flip_Actions(mFragment);
                break;
            case 1:
                popuplayout.setVisibility(View.VISIBLE);

                mTitle.setText("Select Designation");
                Fragment mEmpFragment = new FlipFragment();
                Bundle empbundle = new Bundle();
                empbundle.putInt("ACTION",1);
                mEmpFragment.setArguments(empbundle);

                Load_Flip_Actions(mEmpFragment);
                break;
            case 2:
                popuplayout.setVisibility(View.VISIBLE);

                mTitle.setText("Select Company");
                Fragment mFragmen = new FlipFragment();
                Bundle bundl = new Bundle();
                bundl.putInt("ACTION",2);
                mFragmen.setArguments(bundl);

                Load_Flip_Actions(mFragmen);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        switch (buttonId){

            case R.id.expandable_company:
                if( view.getId() ==  R.id.expandable_company){
                    if (flipcompany == true) {
                        expandable_company.expand();
                        if (expandable_employee.isExpanded()) {
                            expandable_employee.close();
                            flipemployee = true;
                        }
                        if (expandable_visa_vehicle.isExpanded()) {
                            expandable_visa_vehicle.close();
                            flipvehicle = true;
                        }
                        if (expandable_notification.isExpanded()) {
                            expandable_notification.close();
                            flipalert = true;
                        }
                        flipcompany = false;
                    } else {
                        expandable_company.close();
                        flipcompany = true;
                    }
                }
                break;
            case R.id.expandable_employee:

                if( view.getId() ==  R.id.expandable_employee){
                    if (flipemployee == true) {
                        expandable_employee.expand();

                        if (expandable_company.isExpanded()) {
                            expandable_company.close();
                            flipcompany = true;
                        }
                        if (expandable_visa_vehicle.isExpanded()) {
                            expandable_visa_vehicle.close();
                            flipvehicle = true;
                        }
                        if (expandable_notification.isExpanded()) {
                            expandable_notification.close();
                            flipalert = true;
                        }
                        flipemployee = false;
                    } else {
                        expandable_employee.close();
                        flipemployee = true;
                    }
                }
                break;

            case R.id.expandable_visa_vehicle:
                if( view.getId() ==  R.id.expandable_visa_vehicle){
                    if(flipvehicle == true) {
                        expandable_visa_vehicle.expand();

                        if (expandable_company.isExpanded()) {
                            expandable_company.close();
                            flipcompany = true;
                        }
                        if (expandable_employee.isExpanded()) {
                            expandable_employee.close();
                            flipemployee = true;
                        }
                        if (expandable_notification.isExpanded()) {
                            expandable_notification.close();
                            flipalert = true;
                        }
                        flipvehicle = false;
                    } else {
                        expandable_visa_vehicle.close();
                        flipvehicle = true;
                    }
                }
                break;

            case R.id.expandable_notification:
                if( view.getId() ==  R.id.expandable_notification){
                    if(flipalert == true) {
                        expandable_notification.expand();

                        if (expandable_company.isExpanded()) {
                            expandable_company.close();
                            flipcompany = true;
                        }
                        if (expandable_employee.isExpanded()) {
                            expandable_employee.close();
                            flipemployee = true;
                        }
                        if (expandable_visa_vehicle.isExpanded()) {
                            expandable_visa_vehicle.close();
                            flipvehicle = true;
                        }
                        flipalert = false;
                    }else {
                        expandable_notification.close();
                        flipalert = true;
                    }
                }
                break;
            case R.id.flip_cmpy_cmpy:
               /* Fragment mfragment = new HomeFragment();*/
                mListener.LoadItem(16);
                break;

            case R.id.flip_cmpy_empy:
                Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
                popuplayout.startAnimation(slide);
                popuplayout.setVisibility(View.VISIBLE);

                mTitle.setText("Select Company");
                Fragment mFragment = new FlipFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("ACTION",0);
                mFragment.setArguments(bundle);

                Load_Flip_Actions(mFragment);
                break;

            case R.id.flip_cmpy_alert:
                mListener.LoadItem(52);
                break;

            case R.id.flip_empy_empy:
                mListener.LoadItem(24);
                break;

            case R.id.flip_empy_list:

                Animation mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
                popuplayout.startAnimation(mAnimation);
                popuplayout.setVisibility(View.VISIBLE);

                mTitle.setText("Select Designation");
                Fragment mEmpFragment = new FlipFragment();
                Bundle empbundle = new Bundle();
                empbundle.putInt("ACTION",1);
                mEmpFragment.setArguments(empbundle);

                Load_Flip_Actions(mEmpFragment);
                break;

            case R.id.flip_empy_alert:
                mListener.LoadItem(53);
                break;

            case R.id.flip_v_v:
                mListener.LoadItem(45);
                break;

            case R.id.flip_v_list:
                Animation mAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_up);
                popuplayout.startAnimation(mAnim);
                popuplayout.setVisibility(View.VISIBLE);

                mTitle.setText("Select Company");
                Fragment mEmpFrag = new FlipFragment();
                Bundle vehbundle = new Bundle();
                vehbundle.putInt("ACTION",2);
                mEmpFrag.setArguments(vehbundle);

                Load_Flip_Actions(mEmpFrag);
                break;

            case R.id.flip_v_alert:
                mListener.LoadItem(54);
                break;

            case R.id.flip_noti_cpy:

                mListener.LoadItem(52);
                break;
            case R.id.flip_noti_empy:
                mListener.LoadItem(53);
                break;
            case R.id.flip_noti_v:
                mListener.LoadItem(54);
                break;

            case R.id.closepopup:
                Close_Popup();
                break;
            case R.id.img_drawer:
                mListener.OpenDrawer();
                break;
        }
    }
    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            mDashboardTask = new DashboardTask(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp);
            mDashboardTask.execute((Void) null);
        } else {

            homerefresh.setRefreshing(false);
            if(getDataDB() > 0) {
                getDataDB();
                CustomToast.error(getActivity(),"No Internet Connection.").show();
            } else {
                CustomToast.error(getActivity(),"No Internet Connection.").show();
            }
        }
    }
    public class DashboardTask extends AsyncTask<Void, String, StringBuilder> {

        private String mId, mAuthorization;
        private boolean mStatus;

        DashboardTask(String id, String authorization, boolean status) {
            mId = id;
            mAuthorization = authorization;
            mStatus = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Visible_Progress();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doDashboardLogin(mId, mAuthorization);
            Log.d("Athira", "API_DASHBOARD_RESPONSE " + result);

            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            Visible_Count();

            if (mStatus == true) {
                homerefresh.setRefreshing(false);
            }
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        helper.Delete_home_details();
                        if (jsonObj.has("company_count")) {
                            cpy_count.setText(jsonObj.getString("company_count"));
                        }
                        if (jsonObj.has("employee_count")) {
                            empy_count.setText(jsonObj.getString("employee_count"));
                        }
                        if (jsonObj.has("vehicle_count")) {
                            visavehicle_count.setText(jsonObj.getString("vehicle_count"));
                        }
                        if (jsonObj.has("total_notification_count")) {
                            noti_count.setText(jsonObj.getString("total_notification_count"));
                        }

                        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
                        HashMap<String, String> map;
                        map = new HashMap<String, String>();

                        map.put("home_cmpy", jsonObj.getString("company_count"));
                        map.put("home_empy", jsonObj.getString("employee_count"));
                        map.put("home_vehicle_visa", jsonObj.getString("vehicle_count"));
                        map.put("home_cpy_notification", jsonObj.getString("company_notification_count"));
                        map.put("home_emp_notification", jsonObj.getString("employee_notification_count"));
                        map.put("home_vehicle_notification", jsonObj.getString("vehicle_notification_count"));
                        map.put("home_visa_notification", jsonObj.getString("visa_notification_count"));
                        map.put("home_total_notification", jsonObj.getString("total_notification_count"));
                        fillMaps.add(map);
                        helper.Insert_home_details(fillMaps);
                    } else {
                        if(getDataDB() > 0) {
                            getDataDB();
                            CustomToast.info(getActivity(),"No data Found").show();
                        } else {
                            CustomToast.info(getActivity(),"No data Found").show();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
                if(getDataDB() > 0) {
                    getDataDB();
                    CustomToast.error(getActivity(),"No Internet Connection.").show();
                } else {
                    CustomToast.error(getActivity(),"No Internet Connection.").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
                if(getDataDB() > 0) {
                    getDataDB();
                    CustomToast.info(getActivity(),"Please try again").show();
                } else {
                    CustomToast.info(getActivity(),"Please try again").show();
                }
            }
        }
    }
    private int getDataDB(){
        final List<HashMap<String, String>> Data_Item;
        Data_Item = helper.gethomedetails();
        try {
            cpy_count.setText(Data_Item.get(0).get("home_cmpy"));
            empy_count.setText(Data_Item.get(0).get("home_empy"));
            visavehicle_count.setText(Data_Item.get(0).get("home_vehicle"));
            noti_count.setText(Data_Item.get(0).get("home_total_notification"));

        }catch (Exception e){}
        return Data_Item.size();
    }

    private void Visible_Count(){
        cpyindicator.setVisibility(View.GONE);
        empindicator.setVisibility(View.GONE);
        visaindicator.setVisibility(View.GONE);
        alertindicator.setVisibility(View.GONE);
        cpy_count.setVisibility(View.VISIBLE);
        empy_count.setVisibility(View.VISIBLE);
        visavehicle_count.setVisibility(View.VISIBLE);
        noti_count.setVisibility(View.VISIBLE);
    }

    private void Visible_Progress(){
        cpyindicator.setVisibility(View.VISIBLE);
        empindicator.setVisibility(View.VISIBLE);
        visaindicator.setVisibility(View.VISIBLE);
        alertindicator.setVisibility(View.VISIBLE);
        cpy_count.setVisibility(View.GONE);
        empy_count.setVisibility(View.GONE);
        visavehicle_count.setVisibility(View.GONE);
        noti_count.setVisibility(View.GONE);
    }
    public void Load_Flip_Actions(final Fragment fragment){
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.popup_container, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    public void Close_Popup() {
        Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_down);
        popuplayout.startAnimation(slide);
        popuplayout.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            mDashboardTask.cancel(true);
        }catch (Exception e){}
    }

}