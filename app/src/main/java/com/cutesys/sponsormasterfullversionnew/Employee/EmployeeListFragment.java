package com.cutesys.sponsormasterfullversionnew.Employee;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Badge;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.CpyEmpListAdapter;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.SearchAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.Listener;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
import com.cutesys.sponsormasterfullversionnew.OtherProfileActivity;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kris on 2/27/2017.
 */

public class EmployeeListFragment extends Fragment implements View.OnClickListener{

    private SqliteHelper helper;
    private SharedPreferences sPreferences;

    Toolbar toolbar;
    private Badge mBadge;
    private Switcher switcher;
    private RecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry, mTitle;
    private ImageView opendrawer;

    private CpyEmpListAdapter mCpyEmpListAdapter;

    List<HashMap<String, String>> Data_Item ;
    Listener mListener;
    ArrayList<ListItem> dataItem;
    ArrayList<ListItem> searchitem;
    int start = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cmpy_emp_fragment, container, false);
        setHasOptionsMenu(true);
        helper = new SqliteHelper(getActivity(), "SponserMaster", null, 1);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){
        mListener = (Listener)getActivity();

        Data_Item = helper.gethomedetails();
        mBadge = (Badge)rootView.findViewById(R.id.badge);
        mBadge.setText(Data_Item.get(0).get("home_emp_notification"));
        mBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.LoadItem(53);
            }
        });
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.mrecyclerview))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        opendrawer = ((ImageView) rootView.findViewById(R.id.opendrawer));
        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);
        opendrawer.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataItem = new ArrayList<>();
        searchitem = new ArrayList<>();
        mTitle.setText("Employee");
        InitGetData(false);
        new LoadallData(sPreferences.getString("ID", ""),
                sPreferences.getString("AUTHORIZATION", ""), String.valueOf(start)).execute();

    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadEmpyListInitiate mLoadEmpyListInitiate = new LoadEmpyListInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadEmpyListInitiate.execute((Void) null);
        }else {
            switcher.showErrorView("No Internet Connection");
        }
    }

    @Override
    public void onClick(View view) {

        int buttonId = view.getId();
        switch (buttonId){

            case R.id.error_label_retry:
                InitGetData(false);
                break;
            case R.id.empty_label_retry:
                InitGetData(false);
                break;
            case R.id.opendrawer:
                mListener.OpenDrawer();
                break;
        }
    }

    public class LoadEmpyListInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadEmpyListInitiate(String id, String authorization, boolean status, String Start) {
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

            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doEmployeeList(mId, mAuthorization,mStart);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            if(mStatus == true){

            }
            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();
                        JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("employee_name").equals(""))
                                    && (!feedObj.getString("employee_name").equals("null"))) {

                                item.set_id(feedObj.getString("employee_id"));

                                item.set_employee_employment_id(feedObj.getString("employee_employment_id"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("employee_name")
                                        .toLowerCase().trim()));
                                if(feedObj.getString("company").trim().equals("")){
                                    item.set_address("None");
                                }else {
                                    item.set_address(feedObj.getString("company"));
                                }
                                if(feedObj.getString("employee_email").trim().equals("")){
                                    item.set_email("None");
                                }else {
                                    item.set_email(feedObj.getString("employee_email"));
                                }

                                if(feedObj.getString("designation").trim().equals("")){
                                    item.set_designation("None");
                                }else {
                                    item.set_designation(feedObj.getString("designation"));
                                }
                                if(feedObj.getString("employee_phone").trim().equals("")){
                                    item.set_phone("None");
                                }else {
                                    item.set_phone(feedObj.getString("employee_phone"));
                                }
                                if (feedObj.getString("profile_picture").equals("")) {
                                    item.setImage("false");
                                } else {
                                    item.setImage(feedObj.getString("profile_picture").trim());
                                }
                                dataItem.add(item);
                            }

                        }
                        start = dataItem.size();
                        mCpyEmpListAdapter = new CpyEmpListAdapter(getActivity(), dataItem,
                                mrecyclerview ,"employee");
                        mrecyclerview.setAdapter(mCpyEmpListAdapter);

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

    public class LoadallData extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;

        LoadallData(String id, String authorization, String Start) {
            mId = id;
            mAuthorization = authorization;
            mStart = Start;
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {

            HttpOperations httpOperations = new HttpOperations(getActivity());
            StringBuilder result = httpOperations.doEmployeeFullList(mId, mAuthorization,mStart);
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {

                        JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                        for (int i = 0; i < feedArray.length(); i++) {

                            ListItem item = new ListItem();
                            JSONObject feedObj = (JSONObject) feedArray.get(i);

                            if((!feedObj.getString("employee_name").equals(""))
                                    && (!feedObj.getString("employee_name").equals("null"))) {

                                item.set_id(feedObj.getString("employee_id"));
                                item.set_name(StringUtils.capitalize(feedObj.getString("employee_name")
                                        .toLowerCase().trim()));
                                searchitem.add(item);
                            }
                        }
                    }else {
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                getActivity().finish();
                break;

            case R.id.action_search:
                loadToolBarSearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadToolBarSearch() {
        ArrayList<ListItem> stored = new ArrayList<>();

        View view = getActivity().getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        ImageView imgToolMic = (ImageView) view.findViewById(R.id.img_tool_mic);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);

        Config.setListViewHeightBasedOnChildren(listSearch);

        edtToolSearch.setHint("Search Employee");

        final Dialog toolbarSearchDialog = new Dialog(getActivity(), R.style.MaterialSearch);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        stored = (stored != null && stored.size() > 0) ? stored : new ArrayList<ListItem>();
        final SearchAdapter searchAdapter = new SearchAdapter(getActivity(), stored, false);

        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchAdapter);

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String[] data = String.valueOf(adapterView.getItemAtPosition(position)).split(",");;
                Intent intent = new Intent(getActivity(),OtherProfileActivity.class);
                intent.putExtra("NAME",data[1]);
                intent.putExtra("ID",data[0]);
                intent.putExtra("STATUS","employee");
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.bottom_up,
                        android.R.anim.fade_out);
                getActivity().finish();
                listSearch.setVisibility(View.GONE);
            }
        });

        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(searchitem, true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<ListItem> filterList = new ArrayList<ListItem>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < searchitem.size(); i++) {

                        if (searchitem.get(i).get_name().toLowerCase().startsWith(s.toString().trim().toLowerCase())) {

                            filterList.add(searchitem.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarSearchDialog.dismiss();
            }
        });

        imgToolMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtToolSearch.setText("");

            }
        });
    }
}