package com.zlin.property.function;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zlin.property.R;
import com.zlin.property.activity.FuParentActivity;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.view.FuButton;
import com.zlin.property.view.FuEditText;
import com.zlin.property.view.FuTextView;

/**
 * Created by zhanglin03 on 2018/4/23.
 */

public class FuServerView  extends FuUiFrameModel implements View.OnClickListener{

    FuEditText et_body;
    FuTextView tv_begin_time;
    FuTextView tv_end_time;
    FuButton btn_save;
    GridView gv_photos;

    public FuServerView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }
    @Override
    protected void createFuLayout() {
        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_server_view, null);
    }


    @Override
    protected void initFuData() {

    }

    @Override
    protected void initWidget() {
        et_body = (FuEditText)mFuView.findViewById(R.id.et_body);
        tv_begin_time = (FuTextView) mFuView.findViewById(R.id.tv_begin_time);
        tv_end_time = (FuTextView)mFuView.findViewById(R.id.tv_end_time);
        btn_save = (FuButton)mFuView.findViewById(R.id.btn_save);
        gv_photos = (GridView)mFuView.findViewById(R.id.gv_photos);
        tv_begin_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_begin_time:
                ((FuParentActivity) mContext).showTimeDialog((TextView) v)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "year-month-day-hour");

                break;
            case R.id.tv_end_time:
                ((FuParentActivity) mContext).showTimeDialog((TextView) v)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "year-month-day-hour");
                break;
        }
    }



    public class PhotoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }


}
