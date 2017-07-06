package com.cutesys.sponsormasterfullversionnew.Recruitment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Switcher.Switcher;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ProfileItem;
import com.cutesys.sponsormasterfullversionnew.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Owner on 5/06/2017.
 */

public class InterviewQUALchildFragment extends Fragment implements View.OnClickListener{

    private Switcher switcher;
    private RecyclerView mrecyclerview;
    private TextView error_label_retry, empty_label_retry;

    private Interviewprofile mOtherProfileActivity;
    private InterviewQUALAdapter mDocDetailsAdapter;
    BroadcastReceiver receiver;
    ArrayList<ListItem> dataItem;
    ProfileItem mProfileItem ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.otherprofilechild_fragment, container, false);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){
        mOtherProfileActivity = (Interviewprofile)getActivity();
        switcher = new Switcher.Builder(getActivity())
                .addContentView(rootView.findViewById(R.id.mrecyclerview))
                .addErrorView(rootView.findViewById(R.id.error_view))
                .addProgressView(rootView.findViewById(R.id.progress_view))
                .setErrorLabel((TextView) rootView.findViewById(R.id.error_label))
                .setEmptyLabel((TextView) rootView.findViewById(R.id.empty_label))
                .addEmptyView(rootView.findViewById(R.id.empty_view))
                .build();

        mrecyclerview = ((RecyclerView)rootView.findViewById(R.id.mrecyclerview));

        error_label_retry = ((TextView) rootView.findViewById(R.id.error_label_retry));
        empty_label_retry = ((TextView)rootView.findViewById(R.id.empty_label_retry));
        error_label_retry.setOnClickListener(this);
        empty_label_retry.setOnClickListener(this);

        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataItem  = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter("INTERVIEWGET");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    mProfileItem = Interviewprofile.dataItem.get(0);

                    if (intent.getAction().equals("EMPTYGET")) {
                        switcher.showEmptyView();

                    } else if (intent.getAction().equals("ERRORGET")) {
                        switcher.showErrorView("Please Try Again");

                    } else if (intent.getAction().equals("ERRORNETGET")) {
                        switcher.showErrorView("No Internet Connection");

                    }

                    else if (mProfileItem.getDatatitle().length == 0) {
                        switcher.showEmptyView();

                    } else {
                        switcher.showContentView();

                        String status = "";
                        dataItem = new ArrayList<>();


                        if (intent.getAction().equals("INTERVIEWGET")) {

                            status = "interview";

                            for (int i = 0; i < mProfileItem.getDatatitle().length; i++) {

                                ListItem item = new ListItem();
                                item.set_company_fee(StringUtils.capitalize(mProfileItem.getDatatitle()[i]
                                        .toLowerCase().trim()));
                                item.set_advance_fee(mProfileItem.getDatadls()[i]);
                                item.set_email(mProfileItem.getDataowner()[i]);

                                dataItem.add(item);
                            }

                        }


                        mDocDetailsAdapter = new InterviewQUALAdapter(getActivity(), dataItem, status);
                        mrecyclerview.setAdapter(mDocDetailsAdapter);

                    }

                } catch (Exception e) {
                }
            }
        };
        getActivity().registerReceiver(receiver, filter);
    }


    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        switch (buttonId){

            case R.id.error_label_retry:
                mOtherProfileActivity.InitGetData(false);
                break;
            case R.id.empty_label_retry:
                mOtherProfileActivity.InitGetData(false);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (receiver != null) {
                getActivity().unregisterReceiver(receiver);
            }
        } catch (Exception e){
        }
    }
}
