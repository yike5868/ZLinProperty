package com.zlin.property.function;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.zlin.property.R;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.RoomItem;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.view.FuListView;
import com.zlin.property.view.FuTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/27.
 */

public class FuChoseRoomView extends FuUiFrameModel {
    public FuChoseRoomView(Context cxt, FuEventCallBack callBack) {
        super(cxt, callBack);
    }
    FuListView lv_first;
    FuListView lv_sec;

   List<RoomItem> firstList;
    List<RoomItem> secList;

    RoomAdapter firstAdapter;
    RoomAdapter secAdapter;


    @Override
    protected void createFuLayout() {

        mFuView = LayoutInflater.from(mContext).inflate(
                R.layout.fu_chose_room_view, null);
    }

    @Override
    protected void initFuData() {
        firstAdapter = new RoomAdapter(firstList);
        secAdapter = new RoomAdapter(secList);
        lv_first.setAdapter(firstAdapter);
        lv_sec.setAdapter(secAdapter);
        lv_first.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppConfig.tempRoom.setMicrodistrictId(firstList.get(position).getId());
                AppConfig.tempRoom.setMicrodistrictName(firstList.get(position).getName());
                mEventCallBack.EventClick(FuChoseRoomFragment.EVENT_FIND_ROOM,firstList.get(position));

            }
        });
        lv_sec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("building".equals(secList.get(position).getType())){
                    AppConfig.tempRoom.setBuildingId(secList.get(position).getId());
                    AppConfig.tempRoom.setBuildingName(secList.get(position).getName());
                }else if("unit".equals(secList.get(position).getType())){
                    AppConfig.tempRoom.setUnitId(secList.get(position).getId());
                    AppConfig.tempRoom.setUnitName(secList.get(position).getName());
                }else if("room".equals(secList.get(position).getType())){
                    AppConfig.tempRoom.setRoomId(secList.get(position).getId());
                    AppConfig.tempRoom.setRoomName(secList.get(position).getName());
                    mEventCallBack.EventClick(FuChoseRoomFragment.EVENT_FINISH,null);
                    return;
                }
                mEventCallBack.EventClick(FuChoseRoomFragment.EVENT_FIND_ROOM,secList.get(position));
            }
        });
    }

    public void setFistAdapter(List<RoomItem> roomItems){
        firstList = roomItems;
        firstAdapter.setRoomItems(firstList);
        firstAdapter.notifyDataSetChanged();
    }

    public void setSecAdapter(List<RoomItem> roomItems){
        secList = roomItems;
        secAdapter.setRoomItems(secList);
        secAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initWidget() {
        lv_first = (FuListView)mFuView.findViewById(R.id.lv_first);
        lv_sec = (FuListView)mFuView.findViewById(R.id.lv_sec);
    }

    class RoomAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        List<RoomItem> roomItems;
        public RoomAdapter(List<RoomItem> roomItems) {
            this.roomItems = roomItems;
            mInflater = LayoutInflater.from(mContext);
        }

        public List<RoomItem> getRoomItems() {
            return roomItems;
        }

        public void setRoomItems(List<RoomItem> roomItems) {
            this.roomItems = roomItems;
        }

        @Override
        public int getCount() {
            return roomItems == null? 0:roomItems.size();
        }

        @Override
        public Object getItem(int position) {
            return roomItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fu_chose_room_item, null);
                holder = new Holder();
                holder.tv_body = (FuTextView) convertView.findViewById(R.id.tv_body);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv_body.setText(roomItems.get(position).getName());
            return convertView;
        }
        class Holder {
            FuTextView tv_body;
        }
    }
}
