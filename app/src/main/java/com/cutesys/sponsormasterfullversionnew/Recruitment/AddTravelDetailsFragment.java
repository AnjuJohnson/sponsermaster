package com.cutesys.sponsormasterfullversionnew.Recruitment;

/**
 * Created by user on 4/1/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cutesys.sponsermasterlibrary.Helperclasses.ViewPagerIndicator;
import com.cutesys.sponsormasterfullversionnew.DashboardActivity;
import com.cutesys.sponsormasterfullversionnew.R;


/**
 * Created by Owner on 22/03/2017.
 */

public class AddTravelDetailsFragment extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static TextView previous,next,mTitile;
    private ViewPager mViewPager;
    ImageView back;
    Intent intent;

    String id;
    /*public AddTravelDetailsFragment() {
            // Required empty public constructor
            }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_travel_detail);
        intent=getIntent();
        id = intent.getExtras().getString("ID");
        //  Toast.makeText(getApplicationContext(),"id="+id,Toast.LENGTH_SHORT).show();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        ViewPagerIndicator indicator = (ViewPagerIndicator) findViewById(R.id.pager_indicator);

        indicator.setPager(mViewPager);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTravelDetailsFragment.this,DashboardActivity.class);
                intent.putExtra("PAGE","INTERVIEW DETAILS");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,
                        R.anim.bottom_down);
                finish();
            }
        });
        mTitile=(TextView)findViewById(R.id.mTitle);
        mTitile.setText("Interview Details");
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    //interview fragment
                    Fragment f = new TravelDetailBasicPage();
                    Bundle mBundle = new Bundle();
                    mBundle.putString("ID",id);
                    f.setArguments(mBundle);
                    //return new TravelDetailBasicPage(0, "Page # 1",id);
                    return f;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                 //   return new TicketDetailsPage(1, "Page # 2",id);

                Fragment fragment = new TicketDetailsPage();
                Bundle mBundle1 = new Bundle();
                    mBundle1.putString("ID",id);
                fragment.setArguments(mBundle1);
                //return new TravelDetailBasicPage(0, "Page # 1",id);
                return fragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "SECTION "+(position+1);
        }
    }
}