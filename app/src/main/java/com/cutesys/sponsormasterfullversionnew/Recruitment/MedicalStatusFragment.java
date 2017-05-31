package com.cutesys.sponsormasterfullversionnew.Recruitment;

/**
 * Created by user on 4/1/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.R;


/**
 * Created by user on 3/24/2017.
 */

public class MedicalStatusFragment extends Fragment {

    private ViewPager viewpager;
    private TabLayout tablayout;
    private TextView mTitle;

    private ViewpagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listpager_fragment, container, false);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView) {

        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mTitle.setText("Medical Status");

        setupViewPager(viewpager);
        tablayout.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewpagerAdapter(getActivity(), getChildFragmentManager());
        viewpager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    public class ViewpagerAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 3;
        private String[] titles = {"Update Medical Status", "Medical Cleared List","Medical Failed List"};

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
                    f = new UpdateMedicalStatusList();
                    return f;
                case 1:
                    f = new MedicalClearedList();
                    return f;
                case 2:
                    f = new MedicalFailedList();
                    return f;
            }
            return f;
        }
        @Override
        public CharSequence getPageTitle(int position)
        {
            return titles[position];
        }

        @Override
        public int getCount() {
            return totalPage;
        }
    }
}