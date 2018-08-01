package com.zlin.property.function;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.youth.banner.listener.OnBannerListener;
import com.zlin.property.R;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.PropertyFee;
import com.zlin.property.db.po.Room;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.tools.ToolUtil;
import com.zlin.property.view.FuButton;
import com.zlin.property.view.FuCheckBox;
import com.zlin.property.view.FuImageView;
import com.zlin.property.view.FuTextView;
import com.zlin.property.view.pullToRefresh.PullToRefreshLayout;
import com.zlin.property.view.pullToRefresh.PullableListView;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/7/10.
 */

public class FuMineView extends FuUiFrameModel implements OnBannerListener,View.OnClickListener,AdapterView.OnItemClickListener {
    FuTextView tv_title;
    FuImageView iv_right;
    FuImageView iv_head;
    FuTextView tv_name;
    FuTextView tv_phone;
    FuTextView tv_room;
    FuButton btn_has;
    FuButton btn_no;
    FuButton btn_pay;

    UserInfo userInfo;

    PullableListView lv_body;
    PullToRefreshLayout pullToRefreshLayout;

    List<PropertyFee> propertyFeeList;

    MyAdapter myAdapter;
    Room selectRoom;


    public FuMineView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }

    @Override
    protected void createFuLayout() {

        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_mine_view, null);

        userInfo = getSP("userInfo",UserInfo.class);
        selectRoom = getSP("selectRoom",Room.class);
    }


    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    protected void initFuData() {
        if(selectRoom!=null)
        tv_title.setText(selectRoom.getRoomName());
        iv_right.setOnClickListener(this);
        Glide.with(mContext).load(userInfo.getHeadPath()).error(R.mipmap.head).into(iv_head);
        tv_name.setText("姓名："+userInfo.getRealName());
        tv_phone.setText("手机："+ToolUtil.hidePhone(userInfo.getPhone()));
        if(selectRoom!=null)
        tv_room.setText("房间：" + selectRoom.getRoomName());
    }

    @Override
    protected void initWidget() {
        tv_title = (FuTextView)mFuView.findViewById(R.id.tv_title);
        iv_right = (FuImageView)mFuView.findViewById(R.id.iv_right);
        tv_title.setVisibility(View.VISIBLE);
        iv_right.setVisibility(View.VISIBLE);

        iv_head = (FuImageView) mFuView.findViewById(R.id.iv_head);
        tv_name = (FuTextView) mFuView.findViewById(R.id.tv_name);
        tv_phone = (FuTextView)mFuView.findViewById(R.id.tv_phone);
        tv_room = (FuTextView)mFuView.findViewById(R.id.tv_room);
        btn_has = (FuButton) mFuView.findViewById(R.id.btn_has);
        btn_no = (FuButton)mFuView.findViewById(R.id.btn_no);
        btn_pay = (FuButton)mFuView.findViewById(R.id.btn_pay);

        lv_body = (PullableListView)mFuView.findViewById(R.id.lv_body);
        initXR();
    }

    private void initXR() {
        myAdapter = new MyAdapter(mContext);
        lv_body.setAdapter(myAdapter);
        lv_body.setOnItemClickListener(this);

        pullToRefreshLayout = ((PullToRefreshLayout) mFuView.findViewById(R.id.refresh_view));
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshListener());

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public class PullToRefreshListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            mEventCallBack.EventClick(FuMineFragment.EVENT_REFRESH, null);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            mEventCallBack.EventClick(FuMineFragment.EVENT_LOADMORE, null);
        }

    }

    public void setPropertyFeeList(List<PropertyFee> propertyFeeList){
        this.propertyFeeList = propertyFeeList;
        myAdapter.notifyDataSetChanged();
    }


    public void loadFinish() {
        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }



    @Override
    public void onClick(View v) {

    }

    class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

            return propertyFeeList == null?0:propertyFeeList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fu_mine_item,
                        null);
                holder = new Holder();
                holder.cb_item = (FuCheckBox) convertView.findViewById(R.id.cb_item);
                holder.tv_money = (FuTextView) convertView.findViewById(R.id.tv_money);
                holder.tv_type = (FuTextView)convertView.findViewById(R.id.tv_type);
                holder.tv_state = (FuTextView)convertView.findViewById(R.id.tv_state);
                holder.tv_time = (FuTextView)convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.tv_money.setText(propertyFeeList.get(position).getPayMoney().toString());
            holder.tv_time.setText(propertyFeeList.get(position).getBeginDate(),propertyFeeList.get(position).getEndDate());


            return convertView;
        }

        class Holder {
            FuCheckBox cb_item;
            FuTextView tv_money;
            FuTextView tv_type;
            FuTextView tv_state;
            FuTextView tv_time;
        }
    }

}
