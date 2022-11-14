package com.xiyou3g.select.parking;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private MyListView mListView;
    private boolean mIsScrolledToTop = true;
    private boolean mIsScrolledToBottom = false;
    private ISmartScrollChangedListener mSmartScrollChangedListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScrolledToBottom == false){
            return super.onInterceptTouchEvent(ev);
        }else if (mIsScrolledToBottom == true && mListView.getHandleOver()){
            return super.onInterceptTouchEvent(ev);
        }else {
            return false;
        }
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            mIsScrolledToTop = clampedY;
            mIsScrolledToBottom = false;
        } else {
            mIsScrolledToTop = false;
            mIsScrolledToBottom = clampedY;
        }
        notifyScrollChangedListeners();
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
            if (getScrollY() == 0) {
                mIsScrolledToTop = true;
                mIsScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop()-getPaddingBottom() ==
                    getChildAt(0).getHeight()) {
                mIsScrolledToBottom = true;
                mIsScrolledToTop = false;
            } else {
                mIsScrolledToTop = false;
                mIsScrolledToBottom = false;
            }
            notifyScrollChangedListeners();
        }
    }

    private void notifyScrollChangedListeners() {
        if (mIsScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (mIsScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }


    public void setListView(MyListView listView) {
        this.mListView = listView;
    }

}
