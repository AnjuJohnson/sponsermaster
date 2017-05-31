package com.cutesys.sponsormasterfullversionnew.Recruitment;

/**
 * Created by user on 4/1/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class TicketDetailsPage extends Fragment {
    LinearLayout mContainer,mVisible;
    public String mId,t1;
    int mpage;

    // RippleView error_ripple, empty_ripple;
    // int id=1;
    private Switcher switcher;

    ArrayList<AllListItem> dataItem;
    private Intent intent;
    private SharedPreferences sPreferences;
    private TextView error_label_retry, empty_label_retry;
    public TextView mJob,mCategory,mQualification,mQualificationStatus;
    ImageView mMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mId = args.getString("ID");
    }

    /* TicketDetailsPage(int page, String title,String id){
        this.mId=id;
        this.mpage=page;
        this.t1=title;
    }
*/
    // Store instance variables based on arguments passed


   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int i = bundle.getInt("ID", 0);
        }
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
*/
    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.hiring_page, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

       /* intent = getIntent();
        Status = intent.getExtras().getString("PAGE");
        id = intent.getExtras().getString("ID");*/
        InitIdView(rootview);
        return rootview;
    }

    private void InitIdView(View rootview) {
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootview.findViewById(R.id.content_view))
                .addErrorView(rootview.findViewById(R.id.error_view))
                .addProgressView(rootview.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootview.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootview.findViewById(R.id.empty_label))
                .addEmptyView(rootview.findViewById(R.id.empty_view))
                .build();

        mVisible=(LinearLayout)rootview.findViewById(R.id.content_view);
        error_label_retry = ((TextView) rootview.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootview.findViewById(R.id.empty_label_retry));
        mContainer = (LinearLayout) rootview.findViewById(R.id.container);
        mJob = (TextView) rootview.findViewById(R.id.jobpos_1);
        mCategory = (TextView) rootview.findViewById(R.id.category_1);
        mQualification = (TextView) rootview.findViewById(R.id.quali_1);
        mQualificationStatus = (TextView) rootview.findViewById(R.id.stat_1);
        mMenu=(ImageView)rootview.findViewById(R.id.menu);

        error_label_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitGetData(false);
            }
        });
        empty_label_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitGetData(false);
            }
        });

      /*  mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DashboardActivity.class);
                intent.putExtra("PAGE","INTERVIEW");
            }
        });*/
        dataItem = new ArrayList<>();
        InitGetData(false);

    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            TicketDetailsPage.LoadEmployeeLoanDetails mLoadEmployeeLoanDetails = new TicketDetailsPage.LoadEmployeeLoanDetails(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""),mId);
            mLoadEmployeeLoanDetails.execute((Void) null);
        } else {
            switcher.showErrorView();
        }
    }
    public class LoadEmployeeLoanDetails extends AsyncTask<Void, StringBuilder, StringBuilder> {
        private String mID, mAuthorization, mInterview_id;
        private boolean mStatus;

        LoadEmployeeLoanDetails(String id, String authorization, String interview_id) {
            mID = id;
            mAuthorization = authorization;
            mInterview_id = interview_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switcher.showProgressView();
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doInterviewDetails(mID, mAuthorization,mInterview_id);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        //   mContainer.setVisibility(View.VISIBLE);
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("interview_details");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.setEmployee_id(feedObj.getString("interview_id"));
                            item.setEmployee_name(feedObj.getString("interview_name"));
                            if ((!feedObj.getString("interview_name").equals(""))
                                    && (!feedObj.getString("interview_name").equals("null"))) {


                               /* item.setEmployee_name(StringUtils.capitalize(feedObj.getString("requirements_job")
                                        .toLowerCase().trim()));*/
                                if (feedObj.getString("requirements_job").trim().equals("")) {
                                    mJob.setText("None");


                                    // item.setcompany_address("None");
                                } else {
                                    mJob.setText(feedObj.getString("requirements_job"));
                                    //item.setcompany_address(feedObj.getString("requirements_job"));
                                }
                                if (feedObj.getString("requirements_category").trim().equals("")) {
                                    mCategory.setText("None");
                                    //item.setCompany_model("None");
                                } else {
                                    mCategory.setText(feedObj.getString("requirements_category"));
                                    // item.setCompany_model(feedObj.getString("category"));
                                }
                                //  item.setCompany_model(feedObj.getString("visa_no"));
                                if (feedObj.getString("qualifications_name").trim().equals("")) {
                                    // item.setVehicle_status("None");
                                    mQualification.setText("None");
                                } else {
                                    mQualification.setText(feedObj.getString("qualifications_name"));
                                    // item.setVehicle_status(feedObj.getString("qualifications_name"));
                                }
                                // item.setVehicle_status(feedObj.getString("visa_type_name"));
                                if (feedObj.getString("qualifications_status").trim().equals("")) {
                                    mQualificationStatus.setText("None");
                                    // item.setEmployee_company("None");
                                } else {
                                    mQualificationStatus.setText(feedObj.getString("qualifications_status"));
                                    //item.setEmployee_company(feedObj.getString("qualifications_status"));
                                }
                               /* //item.setEmployee_company(feedObj.getString("visa_expiry"));
                                if (feedObj.getString("interview_time_from").trim().equals("")) {
                                    item.setCompany_manufacture("None");
                                } else {
                                    item.setCompany_manufacture(feedObj.getString("interview_time_from"));
                                }
                                if (feedObj.getString("interview_time_to").trim().equals("")) {
                                    item.setEmployee_mail("None");
                                } else {
                                    item.setEmployee_mail(feedObj.getString("interview_time_to"));
                                }
                                if (feedObj.getString("interview_country").trim().equals("")) {
                                    item.setHoliday("None");
                                } else {
                                    item.setHoliday(feedObj.getString("interview_country"));
                                }
                                if (feedObj.getString("interview_state").trim().equals("")) {
                                    item.setvisa_advance_fee("None");
                                } else {
                                    item.setvisa_advance_fee(feedObj.getString("interview_state"));
                                } if (feedObj.getString("interview_place").trim().equals("")) {
                                    item.setvisa_balance_fee("None");
                                } else {
                                    item.setvisa_balance_fee(feedObj.getString("interview_place"));
                                }
                                if (feedObj.getString("requirements_id").trim().equals("")) {
                                    item.setvisa_bank_fee("None");
                                } else {
                                    item.setvisa_bank_fee(feedObj.getString("requirements_id"));
                                }
                                if (feedObj.getString("requirements_job").trim().equals("")) {
                                    item.setcompany_email("None");
                                } else {
                                    item.setcompany_email(feedObj.getString("requirements_job"));
                                }
                                if (feedObj.getString("requirements_category").trim().equals("")) {
                                    item.setvisa_sponsor_fee("None");
                                } else {
                                    item.setvisa_sponsor_fee(feedObj.getString("requirements_category"));
                                }
                                if (feedObj.getString("requirements_skils").trim().equals("")) {
                                    item.setAssigned_company("None");
                                } else {
                                    item.setAssigned_company(feedObj.getString("requirements_skils"));
                                }
                                if (feedObj.getString("qualifications_name").trim().equals("")) {
                                    item.setAssigned_employee("None");
                                } else {
                                    item.setAssigned_employee(feedObj.getString("qualifications_name"));
                                }
                                if (feedObj.getString("qualifications_status").trim().equals("")) {
                                    item.setCompany_manufacturer("None");
                                } else {
                                    item.setCompany_manufacturer(feedObj.getString("qualifications_status"));
                                }
                                if (feedObj.getString("company_name").trim().equals("")) {
                                    item.setEmployee_designation("None");
                                } else {
                                    item.setEmployee_designation(feedObj.getString("company_name"));
                                }

                                if (feedObj.getString("requirement_number").trim().equals("")) {
                                    item.setCompany_phone("None");
                                } else {
                                    item.setCompany_phone(feedObj.getString("requirement_number"));
                                }*/
                                dataItem.add(item);

                                //  getdata();


                            }
                        }

                    }else {
                        switcher.showEmptyView();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

                switcher.showErrorView();
            }
        }
    }
}