package com.cutesys.sponsormasterfullversionnew.Recruitment.SelectionStatus;

/**
 * Created by user on 3/30/2017.
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

public class SelectionStatusFragment extends Fragment {

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
        mTitle.setText("Selection Status");

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
        private int totalPage = 5;
        private String[] titles = {"Candidates List", "Selected Candidates","Retest Candidates","Rejected Candidates","Onhold Candidates"};

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
                    f = new CandidateListFragment();
                    return f;
                case 1:
                    f = new SelectedCandidateListRecruitment();
                    return f;
                case 2:
                    f = new RetestCandidateListFragment();
                    return f;
                case 3:
                    f = new RejectedCandidateListFragment();
                    return f;
                case 4:
                    f = new OnholdCandidateFragment();
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