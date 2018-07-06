package com.zlin.property.function;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zlin.property.R;
import com.zlin.property.activity.FuParentActivity;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.Photo;
import com.zlin.property.db.po.Repair;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.tools.StringUtil;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.uview.HorizontalListView;
import com.zlin.property.view.FuButton;
import com.zlin.property.view.FuEditText;
import com.zlin.property.view.FuImageView;
import com.zlin.property.view.FuTextView;
import com.zlin.property.view.SweetAlert.SweetAlertDialog;
import com.zlin.property.view.TimePicker.Type;

import java.util.ArrayList;
import java.util.Date;
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
    HorizontalListView horizontalListView;

    FuImageView iv_right;
    List<Photo> photoList;
    Repair repair;
    UserInfo userInfo;

    public FuServerView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void createFuLayout() {
        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_server_view, null);
        userInfo = getSP("userInfo", UserInfo.class);
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
        horizontalListView = (HorizontalListView) mFuView.findViewById(R.id.horizontalListView);
        iv_right = (FuImageView) mFuView.findViewById(R.id.iv_right);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(this);
        tv_begin_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_begin_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        btn_save.setOnClickListener(this);
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
                ((FuParentActivity) mContext).showTimeDialog((TextView) v, "HH时mm分", Type.HOURS_MINS)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "hour-minute");

                break;
            case R.id.tv_end_time:
                ((FuParentActivity) mContext).showTimeDialog((TextView) v, "HH时mm分", Type.HOURS_MINS)
                        .show(((FuParentActivity) mContext).getSupportFragmentManager(), "hour-minute");
                break;
            case R.id.iv_right:
                listRight = new ArrayList<>();
                listRight.add("提交记录");
                setPopup(v, 2, listOnItemClickListener, listRight);
                break;
            case R.id.btn_save:
                saveRepair();
                break;
        }
    }

    public void setData(Repair repair){
        this.repair = repair;
        et_body.setText(repair.getMessage());
        tv_begin_date.setText(repair.getBeginDate(),"MM月dd日");
        tv_end_date.setText(repair.getEndDate(),"MM月dd日");
        tv_begin_time.setText(repair.getBeginTime(),"HH时mm分");
        tv_end_time.setText(repair.getEndTime(),"HH时mm分");
        setPhotoList(repair.getPhotoList());
    }

    private void saveRepair() {
        if(repair == null)
            repair = new Repair();
        String message = et_body.getText().toString().trim();
        Date beginDate = tv_begin_date.getDate();
        Date endDate = tv_end_date.getDate();
        Date beginTime = tv_begin_time.getDate();
        Date endTime = tv_end_time.getDate();

        repair.setMessage(message);
        repair.setBeginTime(beginTime);
        repair.setEndTime(endTime);
        repair.setBeginDate(beginDate);
        repair.setEndDate(endDate);
        repair.setPhotoList(photoList);
        repair.setUserId(userInfo.getUserId());
        repair.setRoomId(userInfo.getRoomId());
        repair.setType("维修");

        if (StringUtil.isEmpty(message)) {
            ToastUtil.showToast("请填写维修内容!");
            return;
        }
        if (beginDate == null || endDate == null || beginTime == null || endTime == null) {
            showAlertDailog();
            return;
        }

        mEventCallBack.EventClick(
                FuServerFragment.EVENT_SAVE, repair);
    }

    AdapterView.OnItemClickListener listOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            mEventCallBack.EventClick(
                    FuServerFragment.EVENT_LIST, bundle);
        }
    };

    AdapterView.OnItemClickListener photoonItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            mEventCallBack.EventClick(
                    FuServerFragment.EVENT_PHOTO, bundle);

        }
    };

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
        PhotoAdapter photoAdapter = new PhotoAdapter();
        horizontalListView.setAdapter(photoAdapter);
        horizontalListView.setOnItemClickListener(photoonItemClickListener);
    }

    public void setCanEdit(boolean canEdit){
        et_body.setEnabled(canEdit);
        tv_begin_date.setEnabled(canEdit);
        tv_end_date.setEnabled(canEdit);
        tv_begin_time.setEnabled(canEdit);
        tv_end_time.setEnabled(canEdit);
        btn_save.setEnabled(canEdit);
        horizontalListView.setEnabled(canEdit);
    }



    public class PhotoAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public PhotoAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return photoList == null ? 1 : photoList.size() + 1;
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
            } else
                Glide.with(mContext).load(photoList.get(position - 1).getPath()).error(R.mipmap.ic_logo_app).into(holder.iv_body);
            return convertView;
        }

        class Holder {
            FuImageView iv_body;
        }
    }

    public void showAlertDailog() {
        new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确认")
                .setContentText("维修时间填写不完整，确定是否提交?")
                .setCancelText("取消")
                .setConfirmText("是的")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        mEventCallBack.EventClick(
                                FuServerFragment.EVENT_SAVE, repair);
                    }
                }).show();
    }

}
