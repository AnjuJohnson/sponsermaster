package com.cutesys.sponsormasterfullversionnew.Calender;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.R;

import static com.cutesys.sponsormasterfullversionnew.R.id.mTitle;

/**
 * Created by Kris on 2/27/2017.
 */

public class CalendarFragment extends Fragment implements View.OnClickListener{

    private TextView mTitle;
    private ImageView changetype;

    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);

        InitIdView(rootView);
        return rootView;
    }

    private void InitIdView(View rootView){

        mHandler = new Handler();

        changetype = ((ImageView) rootView.findViewById(R.id.changetype));
        changetype.setImageResource(R.mipmap.ic_list);
        changetype.setTag("list");

        mTitle = ((TextView) rootView.findViewById(R.id.mTitle));
        mTitle.setText("Calendar");

        changetype.setOnClickListener(this);

        CalendarViewFragment mCalendarViewFragment = new CalendarViewFragment();
        loadFragment(mCalendarViewFragment);
    }

    @Override
    public void onClick(View view) {

        int buttonId = view.getId();
        switch (buttonId){

            case R.id.changetype:
                if (changetype.getTag().equals("list")){

                    changetype.setImageResource(R.mipmap.ic_grid);
                    changetype.setTag("calendar");

                    CalendarListFragment mCalendarListFragment = new CalendarListFragment();
                    loadFragment(mCalendarListFragment);

                } else {
                    changetype.setTag("list");
                    changetype.setImageResource(R.mipmap.ic_list);

                    CalendarViewFragment mCalendarViewFragment = new CalendarViewFragment();
                    loadFragment(mCalendarViewFragment);
                }
                break;
        }
    }

    private void loadFragment(final Fragment fragment) {

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }
}