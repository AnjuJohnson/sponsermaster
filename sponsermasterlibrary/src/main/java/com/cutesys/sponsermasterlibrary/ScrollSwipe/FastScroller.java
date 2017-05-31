package com.cutesys.sponsermasterlibrary.ScrollSwipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cutesys.sponsermasterlibrary.R;


public class FastScroller extends LinearLayout {
    public interface SectionIndexer {

        String getSectionText(int position);
    }
    private static final int sBubbleAnimDuration = 100;
    private static final int sScrollbarAnimDuration = 300;
    private static final int sTrackSnapRange = 5;
    @ColorInt private int mBubbleColor;
    @ColorInt private int mHandleColor;
    private int mHeight;
    private SectionIndexer mSectionIndexer;
    private ViewPropertyAnimator mScrollbarAnimator;
    private ViewPropertyAnimator mBubbleAnimator;
    private RecyclerView mRecyclerView;
    private TextView mBubbleView;
    private ImageView mHandleView;
    private ImageView mTrackView;
    private View mScrollbar;
    private Drawable mBubbleImage;
    private Drawable mHandleImage;
    private Drawable mTrackImage;
    private FastScrollStateChangeListener mFastScrollStateChangeListener;
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!mHandleView.isSelected() && isEnabled()) {
                setViewPositions(getScrollProportion(recyclerView));
            }
        }
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (isEnabled()) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        showScrollbar();
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        showScrollbar();
                        break;
                }
            }
        }
    };
    public FastScroller(Context context) {
        super(context);
        layout(context, null);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

    }
    public FastScroller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }
    public FastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layout(context, attrs);
        setLayoutParams(generateLayoutParams(attrs));
    }
    @Override
    public void setLayoutParams(@NonNull ViewGroup.LayoutParams params) {
        params.width = LayoutParams.WRAP_CONTENT;
        super.setLayoutParams(params);
    }
    public void setLayoutParams(@NonNull ViewGroup viewGroup) {
        if (viewGroup instanceof CoordinatorLayout) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
            @IdRes int layoutId = mRecyclerView.getId();

            if (layoutId != NO_ID) {
                layoutParams.setAnchorId(layoutId);
                layoutParams.anchorGravity = GravityCompat.END;
            } else {
                layoutParams.gravity = GravityCompat.END;
            }
            setLayoutParams(layoutParams);
        } else if (viewGroup instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();

            layoutParams.gravity = GravityCompat.END;
            setLayoutParams(layoutParams);
        } else if (viewGroup instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            setLayoutParams(layoutParams);
        } else {
            throw new IllegalArgumentException("Parent ViewGroup must be a CoordinatorLayout," +
                    " FrameLayout, or RelativeLayout");
        }
    }
    public void setSectionIndexer(SectionIndexer sectionIndexer) {
        mSectionIndexer = sectionIndexer;
    }
    public void attachRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.addOnScrollListener(mScrollListener);

    }
    public void detachRecyclerView() {
        if (mRecyclerView != null) {
            mRecyclerView.removeOnScrollListener(mScrollListener);
            mRecyclerView = null;
        }
    }
    public void setTrackVisible(boolean visible) {
        mTrackView.setVisibility(visible ? VISIBLE : GONE);
    }
    public void setTrackColor(@ColorInt int color) {
        @ColorInt int trackColor = color;

        if (mTrackImage == null) {
            mTrackImage = DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), R.drawable.fastscroll_track));
            mTrackImage.mutate();
        }
        DrawableCompat.setTint(mTrackImage, trackColor);
        mTrackView.setImageDrawable(mTrackImage);
    }
    public void setHandleColor(@ColorInt int color) {
        mHandleColor = color;

        if (mHandleImage == null) {
            mHandleImage = DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), R.drawable.fastscroll_handle));
            mHandleImage.mutate();
        }

        DrawableCompat.setTint(mHandleImage, mHandleColor);
        mHandleView.setImageDrawable(mHandleImage);
    }
    public void setBubbleColor(@ColorInt int color) {
        mBubbleColor = color;
        if (mBubbleImage == null) {
            mBubbleImage = DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), R.drawable.fastscroll_bubble));
            mBubbleImage.mutate();
        }
        DrawableCompat.setTint(mBubbleImage, mBubbleColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBubbleView.setBackground(mBubbleImage);
        } else {

            mBubbleView.setBackgroundDrawable(mBubbleImage);
        }
    }
    public void setBubbleTextColor(@ColorInt int color) {
        mBubbleView.setTextColor(color);
    }
    public void setFastScrollStateChangeListener(FastScrollStateChangeListener fastScrollStateChangeListener) {
        mFastScrollStateChangeListener = fastScrollStateChangeListener;
    }
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setVisibility(enabled ? VISIBLE : VISIBLE);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < mHandleView.getX() - ViewCompat.getPaddingStart(mHandleView)) {
                    return false;
                }
                setHandleSelected(true);
                if (!isViewVisible(mScrollbar)) {
                    showScrollbar();
                }
                if (mSectionIndexer != null && !isViewVisible(mBubbleView)) {
                    showBubble();
                }
                if (mFastScrollStateChangeListener != null) {
                    mFastScrollStateChangeListener.onFastScrollStart();
                }
            case MotionEvent.ACTION_MOVE:
                final float y = event.getY();
                setViewPositions(y);
                setRecyclerViewPosition(y);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setHandleSelected(false);
                if (isViewVisible(mBubbleView)) {
                    hideBubble();
                }
                if (mFastScrollStateChangeListener != null) {
                    mFastScrollStateChangeListener.onFastScrollStop();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
    }
    private void setRecyclerViewPosition(float y) {
        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            int itemCount = mRecyclerView.getAdapter().getItemCount();
            float proportion;

            if (mHandleView.getY() == 0) {
                proportion = 0f;
            } else if (mHandleView.getY() + mHandleView.getHeight() >= mHeight - sTrackSnapRange) {
                proportion = 1f;
            } else {
                proportion = y / (float) mHeight;
            }

            int targetPos = getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            mRecyclerView.getLayoutManager().scrollToPosition(targetPos);

            if (mSectionIndexer != null) {
                mBubbleView.setText(mSectionIndexer.getSectionText(targetPos));
            }
        }
    }
    private float getScrollProportion(RecyclerView recyclerView) {
        final int verticalScrollOffset = recyclerView.computeVerticalScrollOffset();
        final int verticalScrollRange = recyclerView.computeVerticalScrollRange();
        float proportion = (float) verticalScrollOffset / ((float) verticalScrollRange - mHeight);
        return mHeight * proportion;
    }
    private int getValueInRange(int min, int max, int value) {
        int minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }
    private void setViewPositions(float y) {
        int bubbleHeight = mBubbleView.getHeight();
        int handleHeight = mHandleView.getHeight();

        mBubbleView.setY(getValueInRange(0, mHeight - bubbleHeight - handleHeight / 2, (int) (y - bubbleHeight)));
        mHandleView.setY(getValueInRange(0, mHeight - handleHeight, (int) (y - handleHeight / 2)));
    }
    private boolean isViewVisible(View view) {
        return view != null && view.getVisibility() == VISIBLE;
    }

    private void showBubble() {
        mBubbleView.setVisibility(VISIBLE);
        mBubbleAnimator = mBubbleView.animate().alpha(1f)
                .setDuration(sBubbleAnimDuration)
                .setListener(new AnimatorListenerAdapter() {

                });
    }
    private void hideBubble() {
        mBubbleAnimator = mBubbleView.animate().alpha(0f)
                .setDuration(sBubbleAnimDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mBubbleView.setVisibility(GONE);
                        mBubbleAnimator = null;
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        mBubbleView.setVisibility(GONE);
                        mBubbleAnimator = null;
                    }
                });
    }
    public void showScrollbar() {
        if (mRecyclerView.computeVerticalScrollRange() - mHeight > 0) {
            float transX = getResources().getDimensionPixelSize(R.dimen.fastscroll_scrollbar_padding);

            mScrollbar.setTranslationX(transX);
            mScrollbar.setVisibility(VISIBLE);
            mScrollbarAnimator = mScrollbar.animate().translationX(0f).alpha(1f)
                    .setDuration(sScrollbarAnimDuration)
                    .setListener(new AnimatorListenerAdapter() {

                    });
        }
    }
    private void setHandleSelected(boolean selected) {
        mHandleView.setSelected(selected);
        DrawableCompat.setTint(mHandleImage, selected ? mBubbleColor : mHandleColor);
    }
    private void layout(Context context, AttributeSet attrs) {
        inflate(context, R.layout.fastscroller, this);
        setClipChildren(false);
        setOrientation(HORIZONTAL);
        mBubbleView = (TextView) findViewById(R.id.fastscroll_bubble);
        mHandleView = (ImageView) findViewById(R.id.fastscroll_handle);
        mTrackView = (ImageView) findViewById(R.id.fastscroll_track);
        mScrollbar = findViewById(R.id.fastscroll_scrollbar);
        @ColorInt int bubbleColor = Color.parseColor("#cf3773");
        @ColorInt int handleColor =Color.parseColor("#cf3773");
        @ColorInt int trackColor =Color.parseColor("#cf3773");
        @ColorInt int textColor = Color.parseColor("#ffffff");
        boolean hideScrollbar = true;
        boolean showTrack = false;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FastScrollRecyclerView, 0, 0);
            if (typedArray != null) {
                try {
                    bubbleColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_bubbleColor, bubbleColor);
                    handleColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_handleColor, handleColor);
                    trackColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_trackColor, trackColor);
                    textColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_bubbleTextColor, textColor);
                    showTrack = typedArray.getBoolean(R.styleable.FastScrollRecyclerView_showTrack, false);
                } finally {
                    typedArray.recycle();
                }
            }
        }
        setTrackColor(trackColor);
        setHandleColor(handleColor);
        setBubbleColor(bubbleColor);
        setBubbleTextColor(textColor);
        setTrackVisible(showTrack);
    }
}