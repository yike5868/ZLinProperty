package com.zlin.property.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by zhanglin on 16/8/29.
 */

public class FuCheckBox extends CheckBox {
    public FuCheckBox(Context context) {
        super(context);
        setSize();
    }

    public FuCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSize();
    }

    public FuCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSize();
    }

    public void setSize(){
//        Resources r = getResources();
//        int size = r.getDimensionPixelSize(R.dimen.check_small);
//
//        setTextSize(size);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
    }

    public void setChecked(Integer i, Integer j){
        if(i ==null || j ==null){
            setChecked(false);
        }else{
            if(i == j){
                setChecked(true);
            }else
                setChecked(false);
        }
    }

    public void setChecked(String i, String j){
        if(i ==null || j ==null){
            setChecked(false);
        }else{
            if(i.equals(j)){
                setChecked(true);
            }else
                setChecked(false);
        }
    }
}
