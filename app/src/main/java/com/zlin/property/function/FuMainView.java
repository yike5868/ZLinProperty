package com.zlin.property.function;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zlin.property.R;
import com.zlin.property.activity.FuMainActivity;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.BannerDto;
import com.zlin.property.db.po.Repair;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.NetManager;
import com.zlin.property.net.TaskManager;
import com.zlin.property.view.FuGridView;
import com.zlin.property.view.FuTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class FuMainView extends FuUiFrameModel implements OnBannerListener {

    Banner banner;
    FuGridView gv_main;
    ListView lv_items;

    public static final String[] mainTitles = new String[]{"报修","保洁","快腿","医疗服务"};
    List<Repair> repairList;
    ServerAdapter serverAdapter;
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

    public void setRepair(List<Repair> repairs){
        this.repairList = repairs;
        serverAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(lv_items);
    }

    @Override
    protected void initWidget() {
        lv_items = (ListView)mFuView.findViewById(R.id.lv_items);
        gv_main = (FuGridView)mFuView.findViewById(R.id.gv_main);
        gv_main.setAdapter(new MainAdapter());
        gv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("listPosition", position);
                mEventCallBack.EventClick(
                        FuMainFragment.EVENT_GRID, bundle);
            }
        });

        serverAdapter = new ServerAdapter();
        lv_items.setAdapter(serverAdapter);
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("repair", repairList.get(position));
                mEventCallBack.EventClick(
                        FuMainFragment.EVENT_REPAIR, bundle);
            }
        });
    }

    class MainAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public MainAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }
        @Override
        public int getCount() {
            return mainTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return mainTitles[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fu_main_grid_item, null);
                holder = new Holder();
                holder.tv_body = (FuTextView) convertView.findViewById(R.id.tv_body);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv_body.setText(mainTitles[position]);
            return convertView;
        }
        class Holder {
            FuTextView tv_body;
        }
    }

    class ServerAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public ServerAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }
        @Override
        public int getCount() {
            return repairList == null?0:repairList.size();
        }

        @Override
        public Object getItem(int position) {
            return repairList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fu_server_item, null);
                holder = new Holder();
                holder.tv_type = (FuTextView) convertView.findViewById(R.id.tv_type);
                holder.tv_body = (FuTextView) convertView.findViewById(R.id.tv_body);
                holder.tv_state = (FuTextView)convertView.findViewById(R.id.tv_state);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv_type.setText(repairList.get(position).getType());
            holder.tv_body.setText(repairList.get(position).getMessage());
            holder.tv_state.setText(repairList.get(position).getState());
            return convertView;
        }
        class Holder {
            FuTextView tv_type;
            FuTextView tv_body;
            FuTextView tv_state;
        }
    }
    public void initBanner(List<com.zlin.property.db.po.Banner> bannerList) {
        banner = (Banner) mFuView.findViewById(R.id.banner);

        ArrayList<String> list_path = new ArrayList<String>();
        ArrayList<String> list_title = new ArrayList<String>();
        if(bannerList==null||bannerList.size()<1)
            return;
        for (int i = 0; i < bannerList.size(); i++) {
            list_path.add(bannerList.get(i).getBannerUrl());
            list_title.add(bannerList.get(i).getBannerMessage());
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
