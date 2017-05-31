package com.cutesys.sponsormasterfullversionnew.UserManagement;

/**
 * Created by Owner on 21/03/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cutesys.sponsormasterfullversionnew.Helperclasses.logindetailsitem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Kris on 12/29/2016.
 */
public class LoginSpinner extends BaseAdapter {
    private Context mcontext;
    private ArrayList<logindetailsitem> listitem;

    public LoginSpinner(Context context,ArrayList<logindetailsitem> mListitem) {
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

        logindetailsitem item = listitem.get(position);
        View v = LayoutInflater.from(mcontext).inflate(R.layout.roomspinner, null);

        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(item.getcompany_name());

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