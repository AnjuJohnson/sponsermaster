package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.AllListItem;
import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Owner on 21/03/2017.
 */

public class SpinnerAdapter_ extends BaseAdapter {
    private Context mcontext;
    private ArrayList<AllListItem> listitem;
    String mStatus;

    public SpinnerAdapter_(Context context, ArrayList<AllListItem> mListitem,String status) {
        this.mcontext = context;
        this.listitem = mListitem;
        this.mStatus=status;
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

        AllListItem item = listitem.get(position);
        View v = LayoutInflater.from(mcontext).inflate(R.layout.spinneritem, null);
        if(mStatus.equals("country")){
            TextView cmpname = (TextView) v.findViewById(R.id.cmpname);
            cmpname.setText(item.getCompany_name());

        }
        else if(mStatus.equals("cpy")){
            TextView cmpname = (TextView) v.findViewById(R.id.cmpname);
            cmpname.setText(item.getCompany_name());
        }
        else if(mStatus.equals("employee")){
           // notifyDataSetChanged();
            TextView cmpname = (TextView) v.findViewById(R.id.cmpname);
            cmpname.setText(item.getEmployee_name());
        }
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
