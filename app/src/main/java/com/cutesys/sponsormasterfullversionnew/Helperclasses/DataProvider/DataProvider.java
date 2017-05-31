package com.cutesys.sponsormasterfullversionnew.Helperclasses.DataProvider;

import com.cutesys.sponsormasterfullversionnew.R;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private static final int ITEMS_PER_LEVEL = 5;
    private static final int MAX_LEVELS = 3;

    public static List<BaseItem> getInitialItems() {
        return getSubItems(new GroupItem("root", R.mipmap.home, 0));
    }

    public static List<BaseItem> getSubItems(BaseItem baseItem) {
        if (!(baseItem instanceof GroupItem)) {
            throw new IllegalArgumentException("GroupItem required");
        }

        GroupItem groupItem = (GroupItem)baseItem;
        if(groupItem.getLevel() >= MAX_LEVELS){
            return null;
        }

        List<BaseItem> result = new ArrayList<>(ITEMS_PER_LEVEL);
        int nextLevel = groupItem.getLevel() + 1;

        int groupNr = 0;
        int itemNr = 0;
        for (int i = 0; i < ITEMS_PER_LEVEL; ++i) {
            BaseItem item;
            if (i % 2 == 0 && nextLevel != MAX_LEVELS) {
                item = new GroupItem("Group " + Integer.toString(++groupNr),R.mipmap.home, 0);
                ((GroupItem) item).setLevel(nextLevel);
            } else {
                item = new Item("Item " + Integer.toString(++itemNr),R.mipmap.home, 0);
            }
            result.add(item);
        }
        return result;
    }

    public static boolean isExpandable(BaseItem baseItem) {
        return baseItem instanceof GroupItem;
    }
}