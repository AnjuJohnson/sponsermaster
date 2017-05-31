package com.cutesys.sponsermasterlibrary.MultilevelListview;

import android.view.View;

public interface OnItemClickListener {

    void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo);

    void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo);
}