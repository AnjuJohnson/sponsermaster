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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Owner on 22/03/2017.
 */
public class TravelDetailBasicPage extends Fragment {
    LinearLayout mContainer;
    String Status,defaulValue="a";
    public String mId;
    TextView mTitile;
    ScrollView mScrollview;
    // int id=1;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;
    ArrayList<AllListItem> dataItem;
    private Intent intent;
    private SharedPreferences sPreferences;
    public TextView mInterview_id, mInterview_name, mCompany, mInterviewer, mDatafrom, mDateto, mTimefrom, mTimeto, mCountry, mPlace, mState;


   /* public TravelDetailBasicPage(int page, String title,String id){
        this.mId=id;
    }

  */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       Bundle args = getArguments();
       mId = args.getString("ID");
   }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.travel_basic_page, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

       /* intent = getIntent();
        Status = intent.getExtras().getString("PAGE");
        id = intent.getExtras().getString("ID");*/
        InitIdView(rootview);
        return rootview;
    }

    private void InitIdView(View rootview) {
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootview.findViewById(R.id.scroll_view))
                .addErrorView(rootview.findViewById(R.id.error_view))
                .addProgressView(rootview.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootview.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootview.findViewById(R.id.empty_label))
                .addEmptyView(rootview.findViewById(R.id.empty_view))
                .build();
        mScrollview=(ScrollView)rootview.findViewById(R.id.scroll_view);
        mContainer = (LinearLayout) rootview.findViewById(R.id.container);
        mInterview_id = (TextView) rootview.findViewById(R.id.interview_id);
        mInterview_name = (TextView) rootview.findViewById(R.id.interview_name);
        mCompany = (TextView) rootview.findViewById(R.id.company_name);
        mInterviewer = (TextView) rootview.findViewById(R.id.interviewer);
        mDatafrom = (TextView) rootview.findViewById(R.id.datefrom);
        mDateto = (TextView) rootview.findViewById(R.id.dateto);
        mTimefrom = (TextView) rootview.findViewById(R.id.timefrom);
        mTimeto = (TextView) rootview.findViewById(R.id.timeto);
        mCountry = (TextView) rootview.findViewById(R.id.country);
        mPlace = (TextView) rootview.findViewById(R.id.place);
        mState = (TextView) rootview.findViewById(R.id.state);
        error_label_retry = ((TextView) rootview.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootview.findViewById(R.id.empty_label_retry));
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


       /* mTitile=(TextView)rootview.findViewById(R.id.mTitle);
        mTitile.setText("Interview Details");*/
        dataItem = new ArrayList<>();
        InitGetData(false);

    }
    private void getdata(){
        mInterview_id.setText(dataItem.get(0).getcompany_address());
        mInterview_name.setText(dataItem.get(0).getEmployee_name());
        mCompany.setText(dataItem.get(0).getEmployee_designation());
        mInterviewer.setText(dataItem.get(0).getCompany_model());
        mDatafrom.setText(dataItem.get(0).getVehicle_status());
        mDateto.setText(dataItem.get(0).getEmployee_company());
        mTimefrom.setText(dataItem.get(0).getCompany_manufacture());
        mTimeto.setText(dataItem.get(0).getEmployee_mail());
        mCountry.setText(dataItem.get(0).getHoliday());
        mPlace.setText(dataItem.get(0).getvisa_balance_fee());
        mState.setText(dataItem.get(0).getvisa_advance_fee());

    }

    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            TravelDetailBasicPage.LoadEmployeeLoanDetails mLoadEmployeeLoanDetails = new TravelDetailBasicPage.LoadEmployeeLoanDetails(sPreferences.getString("ID", ""),
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
                        // mContainer.setVisibility(View.VISIBLE);
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("interview_details");
                        for (int i = 0; i < feedArray.length(); i++) {

                            AllListItem item = new AllListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            item.setEmployee_id(feedObj.getString("interview_id"));
                            item.setEmployee_name(feedObj.getString("interview_name"));
                            if ((!feedObj.getString("interview_name").equals(""))
                                    && (!feedObj.getString("interview_name").equals("null"))) {


                                item.setEmployee_name(StringUtils.capitalize(feedObj.getString("interview_name")
                                        .toLowerCase().trim()));
                                if (feedObj.getString("interview_auto_id").trim().equals("")) {
                                    item.setcompany_address("None");
                                } else {
                                    item.setcompany_address(feedObj.getString("interview_auto_id"));
                                }
                                if (feedObj.getString("interview_interviewer").trim().equals("")) {
                                    item.setCompany_model("None");
                                } else {
                                    item.setCompany_model(feedObj.getString("interview_interviewer"));
                                }
                                //  item.setCompany_model(feedObj.getString("visa_no"));
                                if (feedObj.getString("interview_date_from").trim().equals("")) {
                                    item.setVehicle_status("None");
                                } else {
                                    item.setVehicle_status(feedObj.getString("interview_date_from"));
                                }
                                // item.setVehicle_status(feedObj.getString("visa_type_name"));
                                if (feedObj.getString("interview_date_to").trim().equals("")) {
                                    item.setEmployee_company("None");
                                } else {
                                    item.setEmployee_company(feedObj.getString("interview_date_to"));
                                }
                                //item.setEmployee_company(feedObj.getString("visa_expiry"));
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
                                }
                                dataItem.add(item);

                                getdata();


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