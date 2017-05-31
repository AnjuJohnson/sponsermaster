package com.cutesys.sponsormasterfullversionnew.Company;

/**
 * Created by user on 3/29/2017.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.cutesys.sponsermasterlibrary.Button.FloatingActionButton;
import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScrollRecyclerView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.ADDModule.AddBank;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.BankItemsAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;


import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*import static com.cutesys.sm_fullversion.R.id.bankbranch;
import static com.cutesys.sm_fullversion.R.id.bankcode;
import static com.cutesys.sm_fullversion.R.id.bankname;
import static com.cutesys.sm_fullversion.R.id.fabbank;*/

/**
 * Created by Owner on 2/03/2017.
 */

public class BankListFragment extends Fragment implements View.OnClickListener {
    private SharedPreferences sPreferences;
    private SharedPreferences.Editor prefEditor;
    FastScrollRecyclerView cmpy_recycler1;
    Context c;
    private Switcher switcher;
    private TextView error_label_retry, empty_label_retry;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    ArrayList<ListItem> dataItem;
    BankItemsAdapter mAdapter;
    private ProgressDialog dialog;
    int start = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bank_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);
        myOnClickListener = new BankListFragment.MyOnClickListener(getActivity());
        cmpy_recycler1 = (FastScrollRecyclerView) rootView.findViewById(R.id.cmpy_recycler1);
        cmpy_recycler1.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cmpy_recycler1.setLayoutManager(linearLayoutManager);
        dataItem = new ArrayList<ListItem>();
        mAdapter = new BankItemsAdapter(dataItem);
        cmpy_recycler1.setAdapter(mAdapter);


        InitIdView(rootView);
        return rootView;
    }
    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
        }
    }
    private void InitIdView(View rootView){
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.cmpy_recycler1))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();


        FloatingActionButton add = (FloatingActionButton) rootView.findViewById(R.id.fabbank);
        add.setOnClickListener(this);

        cmpy_recycler1= (FastScrollRecyclerView) rootView.findViewById(R.id.cmpy_recycler1);
        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);



        dataItem = new ArrayList<>();
        InitGetData(false);

    }


    private void InitGetData(boolean temp) {
        Config mConfig = new Config(getActivity());
        if (mConfig.isOnline(getActivity())) {
            LoadCompyListInitiate mLoadCompyListInitiate = new LoadCompyListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadCompyListInitiate.execute((Void) null);
        } else {
            switcher.showErrorView("No Internet Connection");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //read username and password from SharedPreferences

    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        switch (buttonId) {

            case R.id.error_label_retry:
                InitGetData(false);
                break;
            case R.id.empty_label_retry:
                InitGetData(false);
                break;
            case R.id.fabbank:
                Intent intent = new Intent(getActivity(),AddBank.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                getActivity().finish();

                break;


        }
    }


    public class LoadCompyListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadCompyListInitiate(String id, String authorization, boolean status, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStatus = status;
            mStart = Start;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switcher.showProgressView();


        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            HttpOperations web=new HttpOperations(getActivity());

            StringBuilder result = web.doCompanyBankList(mId, mAuthorization,mStart);
            System.out.println(result);
            return result;

        }



        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);
            System.out.println(result);

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();

                        JSONArray feedArray = jsonObj.getJSONArray("bank_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);
                            if ((!feedObj.getString("bank_name").equals(""))
                                    && (!feedObj.getString("bank_name").equals("null"))) {

                                if (feedObj.getString("bank_id").trim().equals("")) {
                                    item.set_id("None");
                                } else {
                                    item.set_id((feedObj.getString("bank_id")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("bank_code").trim().equals("")) {
                                    item.set_code("None");
                                } else {
                                    item.set_code((feedObj.getString("bank_code")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("bank_name").trim().equals("")) {
                                    item.set_name("None");
                                } else {
                                    item.set_name(StringUtils.capitalize(feedObj.getString("bank_name")
                                            .toLowerCase().trim()));
                                }

                                if (feedObj.getString("bank_branch").trim().equals("")) {
                                    item.set_address("None");
                                } else {
                                    item.set_address(StringUtils.capitalize(feedObj.getString("bank_branch")
                                            .toLowerCase().trim()));
                                }
                                if (feedObj.getString("bank_country").trim().equals("")) {
                                    item.set_email("None");
                                } else {
                                    item.set_email(StringUtils.capitalize(feedObj.getString("bank_country")
                                            .toLowerCase().trim()));
                                }


                                dataItem.add(item);
                                // Log.d("11111111111",feedObj.getString("bank_name"));
                            }
                        }

                        start = dataItem.size();
                        mAdapter = new BankItemsAdapter(getActivity(), dataItem,"cpy");
                        cmpy_recycler1.setAdapter(mAdapter);
                        System.out.println(result);

                    }else {
                        switcher.showEmptyView();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                switcher.showErrorView("Please Try Again");
            } catch (NullPointerException e) {
                switcher.showErrorView("No Internet Connection");
            } catch (Exception e) {
                switcher.showErrorView("Please Try Again");
            }
        }
    }


}