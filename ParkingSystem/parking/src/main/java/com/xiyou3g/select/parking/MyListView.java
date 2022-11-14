package com.xiyou3g.select.parking;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;


public class MyListView extends ListView {
    private boolean mHandleOver;
    private int mLastY = 0;
    private int mDy = 0;

    private final static int TOP = 0;

    private final static int MOVE = 1;

    private final static int BOTTOM = 2;

    private static int CURRENT = TOP;

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
                mHandleOver = false;
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                mDy = y - mLastY;
                if (mDy > 0 && CURRENT == TOP){
                    this.mScrollView.requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                //证明手指滑动放在了ListView上面，没有放在
                //ScrollView上面，mHandleOver在ScrollView
                //中进行了处理，用于判断当前滑动是在ScrollView
                //还是ListView
                mHandleOver = true;
                break;
            }
        }
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    public boolean getHandleOver(){
        return mHandleOver;
    }
}
