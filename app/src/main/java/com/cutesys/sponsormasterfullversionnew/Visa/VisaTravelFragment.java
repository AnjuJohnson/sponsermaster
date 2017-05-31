/*
 * Copyright 2016 Cutesys Technologies Pvt Ltd as an unpublished work. All Rights
 * Reserved.
 *
 * The information contained herein is confidential property of Cutesys Technologies
 * Pvt Ltd. The use, copying,transfer or disclosure of such information is prohibited
 * except by express written agreement with Company.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * File Name 					: Cmpy_Noti_Fragment
 * Since 						: 15/11/16
 * Version Code & Project Name	: v 1.0 Sponser Master
 * Author Name					: Athira Santhosh
 */
package com.cutesys.sponsormasterfullversionnew.Visa;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.Badge;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.SqliteHelper;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by athira on 15/11/16.
 */
public class VisaTravelFragment extends Fragment {

    private SqliteHelper helper;

    private Badge mBadge;
    private ViewPager viewpager;
    private TabLayout tablayout;
    private TextView mTitle;

    private ViewpagerAdapter adapter;

    List<HashMap<String, String>> Data_Item ;
    private int ActionPage;

   /* @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        ActionPage = args.getInt("ACTION");
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listpager_fragment, container, false);
        helper = new SqliteHelper(getActivity(), "SponserMaster", null, 1);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {

        Data_Item = helper.gethomedetails();
        //mBadge = (Badge)rootView.findViewById(R.id.badge);
       // mBadge.setText(Data_Item.get(0).get("home_visa_notification"));

        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mTitle.setText("Visa Travel");

        setupViewPager(viewpager);
        tablayout.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewpagerAdapter(getActivity(), getChildFragmentManager());
        viewpager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        if(ActionPage == 0){
            viewpager.setCurrentItem(0);
        } else if(ActionPage == 1){
            viewpager.setCurrentItem(1);
        }
    }

    public class ViewpagerAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 2;
        private String[] titles = {"Entered List", "Not Entered List"};

        public ViewpagerAdapter(Context applicationContext,
                                       FragmentManager fragmentManager) {
            super(fragmentManager);
            _context = applicationContext;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();
            switch (position) {
                case 0:
                    f = new VisaEnterChildFragment();
                    return f;
                case 1:
                    f = new VisaNotEnterChildFragment();
                    return f;
            }
            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return totalPage;
        }
    }
}