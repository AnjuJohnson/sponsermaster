package com.cutesys.sponsermasterlibrary.MultilevelListview;

public enum NestType {

    SINGLE(0),
    MULTIPLE(1);

    private int mValue;

    NestType(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public static NestType fromValue(int value) {
        switch (value) {
            case 0:
                return SINGLE;
            case 1:
            default:
                return MULTIPLE;
        }
    }
}