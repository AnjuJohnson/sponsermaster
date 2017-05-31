package com.cutesys.sponsermasterlibrary.MultilevelListview;

class NodeItemInfo implements ItemInfo {

    private Node mNode;

    public NodeItemInfo(Node node) {
        mNode = node;
    }

    @Override
    public int getLevel() {
        return mNode.getLevel();
    }

    @Override
    public int getIdxInLevel() {
        return mNode.getIdxInLevel();
    }

    @Override
    public int getLevelSize() {
        return mNode.getLevelSize();
    }

    @Override
    public boolean isExpanded() {
        return mNode.isExpanded();
    }

    @Override
    public boolean isExpandable() {
        return mNode.isExpandable();
    }

}