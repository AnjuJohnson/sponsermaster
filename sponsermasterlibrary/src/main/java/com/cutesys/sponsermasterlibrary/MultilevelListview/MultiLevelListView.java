package com.cutesys.sponsermasterlibrary.MultilevelListview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.cutesys.sponsermasterlibrary.R;

public class MultiLevelListView extends FrameLayout {

    private ListView mListView;

    private boolean mAlwaysExpanded;
    private NestType mNestType;

    private MultiLevelListAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    public MultiLevelListView(Context context) {
        super(context);

        initView(null);
    }

    public MultiLevelListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(attrs);
    }

    public MultiLevelListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(attrs);
    }

    public boolean isAlwaysExpanded() {
        return mAlwaysExpanded;
    }

    public void setAlwaysExpanded(boolean alwaysExpanded) {
        if (mAlwaysExpanded == alwaysExpanded) {
            return;
        }
        mAlwaysExpanded = alwaysExpanded;
        if (mAdapter != null) {
            mAdapter.reloadData();
        }
    }

    public void setNestType(NestType nestType) {
        if (mNestType == nestType) {
            return;
        }
        mNestType = nestType;
        notifyDataSetChanged();
    }

    public NestType getNestType() {
        return mNestType;
    }

    private void initView(AttributeSet attrs) {
        confWithAttributes(attrs);

        addView(mListView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mListView.setOnItemClickListener(new OnProxyItemClickListener());
    }

    private void setList(int listLayoutId) {
        if (listLayoutId == 0) {
            mListView = new ListView(getContext());
        } else {
            mListView = (ListView) LayoutInflater.from(getContext()).inflate(listLayoutId, null);
        }
    }

    private void confWithAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MultiLevelListView, 0, 0);
        try {
            setAlwaysExpanded(typedArray.getBoolean(R.styleable.MultiLevelListView_alwaysExtended, false));
            setNestType(NestType.fromValue(typedArray.getInt(R.styleable.MultiLevelListView_nestType, NestType.SINGLE.getValue())));
            setList(typedArray.getResourceId(R.styleable.MultiLevelListView_list, 0));
        } finally {
            typedArray.recycle();
        }
    }

    public void setAdapter(MultiLevelListAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterView(this);
        }

        mAdapter = adapter;

        if (adapter == null) {
            return;
        }

        adapter.registerView(this);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    ListView getListView() {
        return mListView;
    }

    class OnProxyItemClickListener implements AdapterView.OnItemClickListener {

        private void notifyItemClicked(View view, Node node) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClicked(MultiLevelListView.this, view, node.getObject(), node.getItemInfo());
            }
        }

        private void notifyGroupItemClicked(View view, Node node) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onGroupItemClicked(MultiLevelListView.this, view, node.getObject(), node.getItemInfo());
            }
        }

        private void onItemClicked(View view, Node node) {
            notifyItemClicked(view, node);
        }

        private void scrollToItemIfNeeded(int itemIndex) {
            int first = mListView.getFirstVisiblePosition();
            int last = mListView.getLastVisiblePosition();

            if ((itemIndex < first) || (itemIndex > last)) {
                mListView.smoothScrollToPosition(itemIndex);
            }
        }

        private void onGroupItemClicked(View view, Node node) {
            boolean isExpanded = node.isExpanded();
            if (!isAlwaysExpanded()) {
                if (isExpanded) {
                    mAdapter.collapseNode(node);
                } else {
                    mAdapter.extendNode(node, mNestType);
                }
            }

            if (mNestType == NestType.SINGLE) {
                scrollToItemIfNeeded(mAdapter.getFlatItems().indexOf(node));
            }

            notifyGroupItemClicked(view, node);
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Node node = mAdapter.getFlatItems().get(position);
            if (node.isExpandable()) {
                onGroupItemClicked(view, node);
            } else {
                onItemClicked(view, node);
            }
        }
    }
}