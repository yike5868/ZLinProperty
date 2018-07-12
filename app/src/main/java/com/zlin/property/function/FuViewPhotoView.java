package com.zlin.property.function;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.zlin.property.view.CustomViewPager;
import com.zlin.property.view.FuButton;
import com.zlin.property.view.FuEditText;
import com.zlin.property.view.FuImageView;
import com.zlin.property.view.FuTextView;
import com.zlin.property.view.SweetAlert.SweetAlertDialog;
import com.zlin.property.view.TimePicker.Type;
import com.zlin.property.view.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/23.
 */

public class FuViewPhotoView extends FuUiFrameModel implements View.OnClickListener {

    ArrayList<Photo> photoList;
    CustomViewPager img_viewpager;
    TextView text_num;

    public FuViewPhotoView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void createFuLayout() {
        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_view_photo_view, null);
        text_num = (TextView)mFuView.findViewById(R.id.text_num);
        img_viewpager = (CustomViewPager) mFuView.findViewById(R.id.img_viewpager);
        img_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                text_num.setText((position+1)+"/"+photoList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    public ArrayList<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(ArrayList<Photo> photoList) {
        this.photoList = photoList;
        LargePicturesPargeAdapter  largePicturesPargeAdapter = new LargePicturesPargeAdapter(mContext);
        img_viewpager.setAdapter(largePicturesPargeAdapter);
        text_num.setText("1/"+photoList.size());
    }

    public class LargePicturesPargeAdapter extends PagerAdapter {
        private Context mContext;

        public LargePicturesPargeAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return photoList == null ? 0 : photoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 必须要实现的方法
         * 每次滑动的时实例化一个页面,ViewPager同时加载3个页面,假如此时你正在第二个页面，向左滑动，
         * 将实例化第4个页面
         **/
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            PhotoView imageView = new PhotoView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            ((ViewPager) container).addView(imageView);
            Glide.with(mContext)
                    .load(photoList.get(position).getPath())
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
            return imageView;
        }

        /**
         * 必须要实现的方法
         * 滑动切换的时销毁一个页面，ViewPager同时加载3个页面,假如此时你正在第二个页面，向左滑动，
         * 将销毁第1个页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub;
            ImageView imageView = (PhotoView) object;
            if (imageView == null)
                return;
            Glide.clear(imageView);        //核心，解决OOM
            ((ViewPager) container).removeView(imageView);
        }

    }
}