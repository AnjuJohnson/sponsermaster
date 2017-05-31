package com.cutesys.sponsormasterfullversionnew.Helperclasses.DataProvider;

public class GroupItem extends BaseItem {
    private int mLevel;

    public GroupItem(String name, int img, int id) {
        super(name, img, id);
        mLevel = 0;
    }

    public void setLevel(int level){
        mLevel = level;
    }

    public int getLevel(){
        return mLevel;
    }
}
