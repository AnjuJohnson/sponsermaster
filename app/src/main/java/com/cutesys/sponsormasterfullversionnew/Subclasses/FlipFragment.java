package com.cutesys.sponsormasterfullversionnew.Subclasses;

/**
 * Created by user on 4/3/2017.
 */

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.ScrollSwipe.FastScrollRecyclerView;
import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Adapterclasses.FlipAdapter;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;
import com.cutesys.sponsormasterfullversionnew.Util.Config;
import com.cutesys.sponsormasterfullversionnew.Util.HttpOperations;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Owner on 3/04/2017.
 */

public class FlipFragment  extends Fragment implements View.OnClickListener{

    private SharedPreferences sPreferences;

    private Switcher switcher;
    private FastScrollRecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry;

    private FlipAdapter mFlipAdapter;

    ArrayList<ListItem> dataItem;
    int start = 0;
    private int mAction;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        mAction = args.getInt("ACTION");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flip_fragment, container, false);
        sPreferences = getActivity().getSharedPreferences("SponsorMaster", getActivity().MODE_PRIVATE);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.mrecyclerview))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        mrecyclerview = ((FastScrollRecyclerView)rootView.findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(0xFFEECCCC);

                Rect rect = new Rect();
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                final int childCount = parent.getChildCount() - 1;
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);

                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int top = child.getBottom() + params.bottomMargin;
                    final int itemDividerHeight = 1;
                    rect.set(left + 30, top, right - 30, top + itemDividerHeight);
                    c.drawRect(rect, paint);
                }
            }
        });

        dataItem = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();

        InitGetData(false);
    }

    private void InitGetData(boolean temp){
        Config mConfig = new Config(getActivity());
        if(mConfig.isOnline(getActivity())){
            LoadFlipInitiate mLoadFlipInitiate = new LoadFlipInitiate(sPreferences.getString("ID", ""),
                    sPreferences.getString("AUTHORIZATION", ""), temp, String.valueOf(start));
            mLoadFlipInitiate.execute((Void) null);
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
        }
    }

    public class LoadFlipInitiate extends AsyncTask<Void, StringBuilder, StringBuilder> {

        private String mId, mAuthorization, mStart;
        private boolean mStatus;

        LoadFlipInitiate(String id, String authorization, boolean status, String Start) {
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
            StringBuilder result = null;

            if((mAction == 0) || (mAction == 2)){
                result = httpOperations.doCompanyFullList(mId, mAuthorization, mStart);

            } else if(mAction == 1) {
                result = httpOperations.doEMPDESGList(mId, mAuthorization, mStart);

            }
            return result;
        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObj = new JSONObject(result.toString());

                if (jsonObj.has("status")) {
                    if (jsonObj.getString("status").equals(String.valueOf(200))) {
                        switcher.showContentView();

                        if(mAction == 0) {

                            JSONArray feedArray = jsonObj.getJSONArray("company_list");
                            for (int i = 0; i < feedArray.length(); i++) {

                                ListItem item = new ListItem();
                                JSONObject feedObj = (JSONObject) feedArray.get(i);

                                if((!feedObj.getString("company_name").equals(""))
                                        && (!feedObj.getString("company_name").equals("null"))) {

                                    item.set_id(feedObj.getString("company_id"));
                                    item.set_name(StringUtils.capitalize(feedObj.getString("company_name")
                                            .toLowerCase().trim()));
                                    dataItem.add(item);
                                }
                            }
                            start = dataItem.size();
                            mFlipAdapter = new FlipAdapter(getActivity(), dataItem,"flipcompany");
                            mrecyclerview.setAdapter(mFlipAdapter);
                        } else if(mAction == 2) {

                            JSONArray feedArray = jsonObj.getJSONArray("company_list");
                            for (int i = 0; i < feedArray.length(); i++) {

                                ListItem item = new ListItem();
                                JSONObject feedObj = (JSONObject) feedArray.get(i);

                                if((!feedObj.getString("company_name").equals(""))
                                        && (!feedObj.getString("company_name").equals("null"))) {

                                    item.set_id(feedObj.getString("company_id"));
                                    item.set_name(StringUtils.capitalize(feedObj.getString("company_name")
                                            .toLowerCase().trim()));
                                    dataItem.add(item);
                                }
                            }
                            start = dataItem.size();
                            mFlipAdapter = new FlipAdapter(getActivity(), dataItem,"flipvehicle");
                            mrecyclerview.setAdapter(mFlipAdapter);
                        } else if(mAction == 1) {

                            JSONArray feedArray = jsonObj.getJSONArray("employee_list");
                            for (int i = 0; i < feedArray.length(); i++) {

                                ListItem item = new ListItem();
                                JSONObject feedObj = (JSONObject) feedArray.get(i);

                                if((!feedObj.getString("designation_name").equals(""))
                                        && (!feedObj.getString("designation_name").equals("null"))) {

                                    item.set_id(feedObj.getString("designation_id"));
                                    item.set_name(StringUtils.capitalize(feedObj.getString("designation_name")
                                            .toLowerCase().trim()));
                                    dataItem.add(item);
                                }
                            }
                            start = dataItem.size();
                            mFlipAdapter = new FlipAdapter(getActivity(), dataItem,"flipemployee");
                            mrecyclerview.setAdapter(mFlipAdapter);
                        }
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