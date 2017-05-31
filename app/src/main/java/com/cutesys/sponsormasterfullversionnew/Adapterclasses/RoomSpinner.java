package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

/**
 * Created by user on 3/30/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.camproomlist;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Kris on 12/29/2016.
 */
public class RoomSpinner extends BaseAdapter {
    private Context mcontext;
    private ArrayList<camproomlist> listitem;

    public RoomSpinner(Context context,ArrayList<camproomlist> mListitem) {
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

        camproomlist item = listitem.get(position);
        View v = LayoutInflater.from(mcontext).inflate(R.layout.spinneritem, null);

        TextView name = (TextView) v.findViewById(R.id.cmpname);
        name.setText(item.getcamp_name());

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