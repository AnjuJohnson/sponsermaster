package com.cutesys.sponsormasterfullversionnew.Adapterclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cutesys.sponsormasterfullversionnew.Helperclasses.ListItem;
import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;

/**
 * Created by Kris on 12/29/2016.
 */
public class SpinnerAdapter1 extends BaseAdapter {
    private Context mcontext;
    private ArrayList<ListItem> listitem;

    public SpinnerAdapter1(Context context, ArrayList<ListItem> mListitem) {
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

        ListItem item = listitem.get(position);
        View v = LayoutInflater.from(mcontext).inflate(R.layout.spinneritem, null);

        TextView cmpname = (TextView) v.findViewById(R.id.cmpname);
        cmpname.setText(item.get_code());

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