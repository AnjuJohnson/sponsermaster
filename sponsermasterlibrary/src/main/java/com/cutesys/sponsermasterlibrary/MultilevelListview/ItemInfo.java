package com.cutesys.sponsermasterlibrary.MultilevelListview;

public interface ItemInfo {

    int getLevel();
    int getLevelSize();
    int getIdxInLevel();
    boolean isExpanded();
    boolean isExpandable();
}
