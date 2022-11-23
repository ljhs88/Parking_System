package com.xiyou3g.select.parking;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;


public class MyListView extends ListView {
    private int mLastY = 0;
    private int mDy = 0;

    private boolean mIsScrolledToTop = true;
    private boolean mIsScrolledToBottom = false;

    private MyScrollView mScrollView;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollview(MyScrollView my_scrollview) {
        this.mScrollView = my_scrollview;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mScrollView.requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                mDy = y - mLastY;
                if ((mDy > 0 && mIsScrolledToBottom == true) || mIsScrolledToTop == true){
                    this.mScrollView.requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
        }
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (android.os.Build.VERSION.SDK_INT < 9) {
            if (getScrollY() == 0) {
                mIsScrolledToTop = true;
                mIsScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() ==
                    getChildAt(0).getHeight()) {
                mIsScrolledToBottom = true;
                mIsScrolledToTop = false;
            } else {
                mIsScrolledToTop = false;
                mIsScrolledToBottom = false;
            }
        }
    }
}
