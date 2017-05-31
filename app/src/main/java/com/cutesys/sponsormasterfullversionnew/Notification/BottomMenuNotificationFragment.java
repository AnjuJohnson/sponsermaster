/*
 *Copyright 2014 Neona Embedded Labz as an unpublished work. All Rights Reserved.
 *
 * The information contained herein is confidential property of Neona Embedded Labz.
 * The use, copying,transfer or disclosure of such information is prohibited except
 * by express written agreement with Company.
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
 * File Name 					: BottomMenuFragment
 * Since 						: 04/12/14
 * Version Code & Project Name	: v 1.5 TiaTel(Doctor)
 * Author Name					: Athira Santhosh  athira.santhosh@neonainnovation.com
 */
package com.cutesys.sponsormasterfullversionnew.Notification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.R;

/**
 * Created by athira on 04/12/14.
 */
public class BottomMenuNotificationFragment extends Fragment implements OnClickListener {

    private long lastClickTime = 0;

    SharedPreferences sPreferences;

    TextView filter_text, calendar_text;
    ImageView calendar_icon, filter_icon;
    LinearLayout listdate, listfilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottommennotificationu, container,false);

        sPreferences = getActivity().getSharedPreferences("TiaTel_Doctor", getActivity().MODE_PRIVATE);

        listdate =(LinearLayout)view.findViewById(R.id.listdate);
        listfilter =(LinearLayout)view.findViewById(R.id.listfilter);
        filter_text = (TextView) view.findViewById(R.id.filter_text);
        calendar_text = (TextView)view.findViewById(R.id.calendar_text);
        calendar_icon = (ImageView)view.findViewById(R.id.calendar_icon);
        filter_icon = (ImageView)view.findViewById(R.id.filter_icon);
        listdate.setOnClickListener(this);
        listfilter.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.listdate:

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();


                break;
            case R.id.listfilter:

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();


                break;
        }
    }
}