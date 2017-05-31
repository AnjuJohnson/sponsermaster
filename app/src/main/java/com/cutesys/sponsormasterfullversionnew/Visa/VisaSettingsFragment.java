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
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.R;

/**
 * Created by athira on 15/11/16.
 */
public class VisaSettingsFragment extends Fragment {

    private ViewPager viewpager;
    private TabLayout tablayout;
    private TextView mTitle;
private ImageView icon;

    private ViewpagerAdapter adapter;

    private int ActionPage;

  /*  @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        Bundle args = getArguments();
        ActionPage = args.getInt("ACTION");
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listpager_fragment, container, false);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {

       /* icon = (ImageView) rootView.findViewById(R.id.icon);
        icon.setVisibility(View.GONE);*/
        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mTitle.setText("Visa Settings");

        setupViewPager(viewpager);
        tablayout.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewpagerAdapter(getActivity(), getChildFragmentManager());
        viewpager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        if(ActionPage == 0){
            viewpager.setCurrentItem(0);
        } else if(ActionPage == 1){
            viewpager.setCurrentItem(1);
        } else if(ActionPage == 2){
            viewpager.setCurrentItem(2);
        } else if(ActionPage == 3){
            viewpager.setCurrentItem(3);
        }
    }

    public class ViewpagerAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 4;
        private String[] titles = {"Visa Type List", "Category List",
                "Sponsor List", "Client List"};

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
                    f = new VisaTypeChildFragment();
                    return f;
                case 1:
                    f = new VisaCategoryChildFragment();
                    return f;
                case 2:
                    f = new VisaSponsorChildFragment();
                    return f;
                case 3:
                    f = new VisaClientChildFragment();
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