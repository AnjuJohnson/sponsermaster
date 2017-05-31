package com.cutesys.sponsermasterlibrary.MultilevelListview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiLevelListAdapter {

    private MultiLevelListView mView;

    private Node mRoot = new Node();
    private List<Node> mFlatItems = new ArrayList<>();
    private List<Object> mSourceData = new ArrayList<>();
    private ProxyAdapter mProxyAdapter = new ProxyAdapter();

    protected abstract boolean isExpandable(Object object);
    protected abstract List<?> getSubObjects(Object object);
    protected abstract View getViewForObject(Object object, View convertView, ItemInfo itemInfo);

    public void setDataItems(List<?> dataItems) {
        checkState();

        mSourceData = new ArrayList<>();
        mSourceData.addAll(dataItems);

        mRoot.setSubNodes(createNodeListFromDataItems(mSourceData, mRoot));
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        checkState();

        mFlatItems = createItemsForCurrentStat();
        mProxyAdapter.notifyDataSetChanged();
    }

    void reloadData() {
        setDataItems(mSourceData);
    }

    private void checkState() {
        if (mView == null) {
            throw new IllegalStateException("Adapter not connected");
        }
    }

    private List<Node> createNodeListFromDataItems(List<?> dataItems, Node parent) {
        List<Node> result = new ArrayList<>();
        if (dataItems != null) {
            for (Object dataItem : dataItems) {
                boolean isExpandable = isExpandable(dataItem);

                Node node = new Node(dataItem, parent);
                node.setExpandable(isExpandable);
                if (mView.isAlwaysExpanded() && isExpandable) {
                    node.setSubNodes(createNodeListFromDataItems(getSubObjects(node.getObject()), node));
                }
                result.add(node);
            }
        }
        return result;
    }

    private List<Node> createItemsForCurrentStat() {
        List<Node> result = new ArrayList<>();
        collectItems(result, mRoot.getSubNodes());
        return result;
    }

    private void collectItems(List<Node> result, List<Node> nodes) {
        if (nodes != null) {
            for (Node node : nodes) {
                result.add(node);
                collectItems(result, node.getSubNodes());
            }
        }
    }

    List<Node> getFlatItems() {
        return mFlatItems;
    }

    void unregisterView(MultiLevelListView view) {
        if (mView != view) {
            throw new IllegalArgumentException("Adapter not connected");
        }

        if (mView == null) {
            return;
        }

        mView.getListView().setAdapter(null);
        mView = null;
    }

    void registerView(MultiLevelListView view) {
        if ((mView != null) && (mView != view)) {
            throw new IllegalArgumentException("Adapter already connected");
        }

        if (view == null) {
            return;
        }

        mView = view;
        mView.getListView().setAdapter(mProxyAdapter);
    }

    void extendNode(Node node, NestType nestTyp) {
        node.setSubNodes(createNodeListFromDataItems(getSubObjects(node.getObject()), node));
        if (nestTyp == NestType.SINGLE) {
            clearPathToNode(node);
        }
        notifyDataSetChanged();
    }

    void collapseNode(Node node) {
        node.clearSubNodes();
        notifyDataSetChanged();
    }

    private void clearPathToNode(Node node) {
        Node parent = node.getParent();
        if (parent != null) {
            List<Node> nodes = parent.getSubNodes();
            if (nodes != null) {
                for (Node sibling : nodes) {
                    if (sibling != node) {
                        sibling.clearSubNodes();
                    }
                }
            }
            clearPathToNode(parent);
        }
    }

    private class ProxyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mFlatItems == null ? 0 : mFlatItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mFlatItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            Node node = mFlatItems.get(i);
            return getViewForObject(node.getObject(), convertView, node.getItemInfo());
        }
    }
}