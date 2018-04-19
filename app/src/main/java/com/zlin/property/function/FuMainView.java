package com.zlin.property.function;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zlin.property.R;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.BannerDto;

import java.util.ArrayList;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class FuMainView extends FuUiFrameModel implements OnBannerListener {

    Banner banner;
    BannerDto bannerDto;
    public FuMainView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void createFuLayout() {

        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_main_view, null);
    }

    @Override
    protected void initFuData() {

    }

    @Override
    protected void initWidget() {

    }

    private void initBanner() {
        banner = (Banner) mFuView.findViewById(R.id.banner);

        ArrayList<String> list_path = new ArrayList<String>();
        ArrayList<String> list_title = new ArrayList<String>();
        if(bannerDto==null||bannerDto.getData()==null||bannerDto.getData().size()<1)
            return;
        for (int i = 0; i < bannerDto.getData().size(); i++) {
            list_path.add(bannerDto.getData().get(i).getBannerUrl());
            list_title.add(bannerDto.getData().get(i).getBannerMessage());
        }
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();


    }
    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第"+position+"张轮播图");
    }
    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
