package com.zlin.property.view;

/**
 * Created by zhanglin03 on 2018/7/10.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义封装android.support.v4.view.ViewPager，重写onInterceptTouchEvent事件，捕获系统级别异常
 */
public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return false;
    }
}