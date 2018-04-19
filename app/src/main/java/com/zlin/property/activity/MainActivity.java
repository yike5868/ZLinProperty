package com.zlin.property.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zlin.property.R;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
