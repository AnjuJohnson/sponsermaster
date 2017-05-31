package com.cutesys.sponsermasterlibrary.MultilevelListview;

import java.util.List;

class Node {

    private Object mObject;
    private int mLevel;
    private Node mParent;
    private List<Node> mSubNodes;
    private int mIdxInLevel;
    private int mLevelSize;
    private boolean mIsExpandable;
    private NodeItemInfo mNodeItemInfo;

    Node(Object object, Node parent) {
        mObject = object;
        mParent = parent;
        mLevel = parent.mLevel + 1;
    }

    Node() {
        mLevel = -1;
    }

    Object getObject() {
        return mObject;
    }

    int getLevel() {
        return mLevel;
    }

    Node getParent() {
        return mParent;
    }

    void clearSubNodes() {
        mSubNodes = null;
    }

    void setSubNodes(List<Node> nodes) {
        mSubNodes = nodes;

        final int NODES = nodes.size();
        for (int i = 0; i < NODES; ++i) {
            Node node = nodes.get(i);
            node.mLevelSize = NODES;
            node.mIdxInLevel = i;
        }
    }

    boolean isExpanded() {
        return mSubNodes != null;
    }

    int getIdxInLevel() {
        return mIdxInLevel;
    }

    int getLevelSize() {
        return mLevelSize;
    }

    List<Node> getSubNodes() {
        return mSubNodes;
    }

    NodeItemInfo getItemInfo() {
        if (mNodeItemInfo == null) {
            mNodeItemInfo = new NodeItemInfo(this);
        }
        return mNodeItemInfo;
    }

    void setExpandable(boolean isExpandable) {
        mIsExpandable = isExpandable;
    }

    boolean isExpandable() {
        return mIsExpandable;
    }
}