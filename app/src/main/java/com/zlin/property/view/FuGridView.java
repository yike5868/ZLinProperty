package com.zlin.property.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zhanglin03 on 2018/4/26.
 */

public class FuGridView extends GridView {
    public FuGridView(Context context) {
        super(context);
    }

    public FuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(
                    0x3fffffff, MeasureSpec.AT_MOST);
        }
        else {
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

}
