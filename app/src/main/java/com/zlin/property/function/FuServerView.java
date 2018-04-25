package com.zlin.property.function;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zlin.property.R;
import com.zlin.property.activity.FuParentActivity;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.Photo;
import com.zlin.property.view.FuButton;
import com.zlin.property.view.FuEditText;
import com.zlin.property.view.FuImageView;
import com.zlin.property.view.FuTextView;
import com.zlin.property.view.TimePicker.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/23.
 */

public class FuServerView extends FuUiFrameModel implements View.OnClickListener {

    FuEditText et_body;
    FuTextView tv_begin_date;
    FuTextView tv_end_date;
    FuTextView tv_begin_time;
    FuTextView tv_end_time;
    FuButton btn_save;
    GridView gv_photos;
    FuImageView iv_right;
    List<Photo> photoList;

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
        tv_begin_date = (FuTextView) mFuView.findViewById(R.id.tv_begin_date);
        tv_end_date = (FuTextView) mFuView.findViewById(R.id.tv_end_date);
        et_body = (FuEditText) mFuView.findViewById(R.id.et_body);
        tv_begin_time = (FuTextView) mFuView.findViewById(R.id.tv_begin_time);
        tv_end_time = (FuTextView) mFuView.findViewById(R.id.tv_end_time);
        btn_save = (FuButton) mFuView.findViewById(R.id.btn_save);
        gv_photos = (GridView) mFuView.findViewById(R.id.gv_photos);
        iv_right = (FuImageView) mFuView.findViewById(R.id.iv_right);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(this);
        tv_begin_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_begin_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
    }

    private ArrayList<String> listRight;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_begin_date:
                ((FuParentActivity) mContext).showTimeDialog((TextView) v, "MM月dd号", Type.YEAR_MONTH_DAY)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "month-day");

                break;
            case R.id.tv_end_date:
                ((FuParentActivity) mContext).showTimeDialog((TextView) v, "MM月dd号", Type.YEAR_MONTH_DAY)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "month-day");
                break;
            case R.id.tv_begin_time:
                ((FuParentActivity) mContext).showTimeDialog((TextView) v, "hh时mm分", Type.HOURS_MINS)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "hour-minute");

                break;
            case R.id.tv_end_time:
                ((FuParentActivity) mContext).showTimeDialog((TextView) v, "hh时mm分", Type.HOURS_MINS)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "hour-minute");
                break;
            case R.id.iv_right:
                listRight = new ArrayList<>();
                listRight.add("提交记录");
                setPopup(v, 2, onItemClickListener, listRight);
                break;
        }
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            mEventCallBack.EventClick(
                    FuServerFragment.EVENT_LIST, bundle);
        }
    };

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
        PhotoAdapter photoAdapter = new PhotoAdapter();
        gv_photos.setAdapter(photoAdapter);
    }

    public class PhotoAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public PhotoAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return photoList==null?1:photoList.size()+1;
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
            Holder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fu_server_photo_item, null);
                holder = new Holder();
                holder.iv_body = (FuImageView) convertView.findViewById(R.id.iv_body);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            if (position == 0) {
                Glide.with(holder.iv_body.getContext()).load(R.mipmap.icon_camera).placeholder(R.mipmap.ic_logo_app).error(R.mipmap.ic_logo_app).dontAnimate().into(holder.iv_body);
            }else
                Glide.with(mContext).load(photoList.get(position - 1).getPath()).into(holder.iv_body);
            return convertView;
        }

        class Holder {
            FuImageView iv_body;
        }
    }


}
