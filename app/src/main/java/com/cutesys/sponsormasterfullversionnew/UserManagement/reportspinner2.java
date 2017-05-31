package com.cutesys.sponsormasterfullversionnew.UserManagement;

/**
 * Created by Owner on 13/05/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.attendanceitem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.logindetailsitem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Kris on 12/29/2016.
 */
public class reportspinner2 extends BaseAdapter {
    private Context mcontext;
    private ArrayList<attendanceitem> listitem;

    public reportspinner2(Context context,ArrayList<attendanceitem> mListitem) {
        this.listitem = mListitem;
        this.mcontext = context;
    }

    public int getCount() {
        return listitem.size();
    }

    public Object getItem(int i) {
        return listitem.get(i);
    }

    public long getItemId(int i) {
        return (long)i;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        attendanceitem item = listitem.get(position);
        View v = LayoutInflater.from(mcontext).inflate(R.layout.roomspinner, null);

        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(item.getemployee_employment_id());

        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}