package com.cutesys.sponsormasterfullversionnew.Helperclasses.DataProvider;

/**
 * Created by awidiyadew on 12/09/16.
 */
public class BaseItem {
    private String mName;
    private int img;
    private int id;

    public BaseItem(String name, int image, int mid) {
        mName = name;
        img = image;
        id = mid;
    }

    public String getName() {
        return mName;
    }

    public int getImg() {
        return img;
    }

    public int getId() {
        return id;
    }
}
