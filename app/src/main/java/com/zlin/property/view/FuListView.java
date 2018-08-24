package com.zlin.property.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zhanglin on 16/8/29.
 */

public class FuListView extends ListView {
    public FuListView(Context context) {
        super(context);
    }

    public FuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置listView不可滑动
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
}
