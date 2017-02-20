package com.mumu.common.widget;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import java.util.HashSet;

/**
 * Created by seek on 2017/1/3.
 */

public class AbsorbNavigationLayout extends LinearLayout implements NestedScrollingParent {
    private final NestedScrollingParentHelper mNestedScrollingParentHelper =
            new NestedScrollingParentHelper(this);
    private OnScrollProgressListener onScrollProgressListener;
    private View mTopView;
    private View mNavigationView;
    private ViewPager mViewPager;
    private int mTopViewHeight;
    private OverScroller mScroller;
    private int mRetainHeight = 0;
    private int mMoveHeight = 0;
    private HashSet<View> mNeedSetPaddingHashSet = new HashSet<>();
    private boolean mNeedSetPadding;


    public AbsorbNavigationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childrenCount = getChildCount();
        if (childrenCount != 3) {
            throw new RuntimeException(String.format(
                    "only support 3 three children,current children count is ", childrenCount));
        }
        mTopView = getChildAt(0);
        mNavigationView = getChildAt(1);
        View view = getChildAt(2);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException(
                    "the third child should be ViewPager!");
        }
        mViewPager = (ViewPager) view;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
    }

    @Override
    public void onStopNestedScroll(View target) {

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        dispatchScrollProgress();
        checkIfNeedSetPadding(target);
        if (dy > 0) {// scroll up
            if (getScrollY() < mMoveHeight) {
                consumed[1] = dy;
                scrollBy(0, dy);
            }
        } else { //scroll down
            if (getScrollY() <= mMoveHeight && getScrollY() > 0) {
                boolean targetCanScroll = ViewCompat.canScrollVertically
                        (target, -1);
                if (target instanceof SwipeRefreshLayout) { // just support system's SwipeRefreshLayout, you can
                    // override to support other layout which you use!
                    SwipeRefreshLayout parent = ((SwipeRefreshLayout) target);
                    if (parent.getChildCount() > 0) {
                        View child = parent.getChildAt(0);
                        targetCanScroll = ViewCompat.canScrollVertically
                                (child, -1);
                    }
                }
                if (!targetCanScroll) {
                    consumed[1] = dy;
                    scrollBy(0, dy);
                }
            }
        }
    }

    private void checkIfNeedSetPadding(View target) {
        if(mNeedSetPadding) {
            boolean needSetPadding = mNeedSetPaddingHashSet.contains(target);
            if (!needSetPadding) {
                mNeedSetPaddingHashSet.add(target);
                View paddingView = target;
                if (target instanceof SwipeRefreshLayout) {
                    SwipeRefreshLayout parent = ((SwipeRefreshLayout) target);
                    if (parent.getChildCount() > 0) {
                        View child = parent.getChildAt(0);
                        paddingView = child;
                    }
                }
                paddingView.setPadding(paddingView.getPaddingLeft(), paddingView.getTop(), paddingView.getPaddingRight(),
                        paddingView.getPaddingBottom() + mRetainHeight);
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (getScrollY() >= mMoveHeight) return false;
        fling((int) velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNavigationView.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTopView.getMeasuredHeight() + mNavigationView.getMeasuredHeight() +
                mViewPager
                        .getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
        mMoveHeight = mTopViewHeight - mRetainHeight;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mMoveHeight);
        dispatchScrollProgress();
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        y = amendY(y);
        dispatchScrollProgress();
        if (y != getScrollY()) {
            super.scrollTo(0, y);
        }
    }

    private int amendY(int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mMoveHeight) {
            y = mMoveHeight;
        }
        return y;
    }

    private void dispatchScrollProgress() {
        if (onScrollProgressListener != null) {
            float progress = getScrollY() / (float) mMoveHeight;
            onScrollProgressListener.onScrollProgress(progress);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public int getRetainHeight() {
        return mRetainHeight;
    }

    public void setRetainHeight(int mRetainHeight) {
        this.mRetainHeight = mRetainHeight;
        mNeedSetPadding = true;
        mNeedSetPaddingHashSet.clear();
        mMoveHeight = mTopViewHeight - mRetainHeight;
    }

    public void setOnScrollProgressListener(OnScrollProgressListener onScrollProgressListener) {
        this.onScrollProgressListener = onScrollProgressListener;
    }

    public interface OnScrollProgressListener {
        void onScrollProgress(@FloatRange(from = 0.0, to = 1.0) float progress);
    }

}
