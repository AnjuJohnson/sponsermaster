package com.cutesys.sponsormasterfullversionnew.Employee.TravelLog;

/**
 * Created by Owner on 30/03/2017.
 */

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

import com.cutesys.sponsormasterfullversionnew.R;


/**
 * Created by user on 3/24/2017.
 */

public class Other extends Fragment {

    private ViewPager viewpager;
    private TabLayout tablayout;
    private TextView mTitle;

    private ViewpagerAdapter adapter;

//    private int ActionPage;

//    @Override
//    public void onCreate(Bundle state) {
//        super.onCreate(state);
//        Bundle args = getArguments();
//        ActionPage = args.getInt("ACTION");
//    }

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
        mTitle.setText("Other");

        setupViewPager(viewpager);
        tablayout.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewpagerAdapter(getActivity(), getChildFragmentManager());
        viewpager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
//        if(ActionPage == 0){
//            viewpager.setCurrentItem(0);
//        } else if(ActionPage == 1){
//            viewpager.setCurrentItem(1);
//        }
//        else if(ActionPage == 2){
//            viewpager.setCurrentItem(2);
//        }
    }

    public class ViewpagerAdapter extends FragmentPagerAdapter {

        private Context _context;
        private int totalPage = 3;
        private String[] titles = {"Ticket Booked", "Travelled","Cancelled"};

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
                    f = new EmpOtherTicketBooked();
                    return f;
                case 1:
                    f = new EmpOtherTravelled();
                    return f;
                case 2:
                    f = new EmpOtherCancelled();
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