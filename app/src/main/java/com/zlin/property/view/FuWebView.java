package com.zlin.property.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by zhanglin03 on 2018/7/23.
 */

public class FuWebView extends WebView {
    public FuWebView(Context context) {
        super(context);
    }

    public FuWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FuWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FuWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }
}
