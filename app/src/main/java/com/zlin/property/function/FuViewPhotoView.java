package com.zlin.property.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.zlin.property.view.photoview.PhotoView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/23.
 */

public class FuViewPhotoView extends FuUiFrameModel implements View.OnClickListener {

    List<String> photoList;

    public FuViewPhotoView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void createFuLayout() {
        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_view_photo_view, null);
    }

    @Override
    protected void initFuData() {

    }

    @Override
    protected void initWidget() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public class PhotoPagerAdapter extends PagerAdapter {
        public PhotoPagerAdapter() {
        }

        @Override
        public int getCount() {
           return photoList == null?0:photoList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(mContext).inflate(
                    R.layout.fu_view_photo_item, null);
            PhotoView photoview = (PhotoView) view.findViewById(R.id.photoview);
            photoview.setAdjustViewBounds(true);
            photoview.setScaleType(ImageView.ScaleType.FIT_CENTER);

            String url = photoList.get(position);
            Glide.with(mContext)
                    .load(url)
                    .skipMemoryCache(true)//不缓存到内存
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(photoview);

            return view;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

}
