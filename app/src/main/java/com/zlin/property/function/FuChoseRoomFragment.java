package com.zlin.property.function;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.zlin.property.FuApp;
import com.zlin.property.activity.FuMainActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.Banner;
import com.zlin.property.db.po.RoomItem;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.ToastUtil;

import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/27.
 */

public class FuChoseRoomFragment extends FragmentParent {
    FuChoseRoomView fuChoseRoomView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("activity","FuWelcomeFragment");
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_CHOSE_ROOM, getActivity(),
                new OnEventClick());
        RoomItem roomItem = new RoomItem();
        roomItem.setType("");
        roomItem.setId("");
        roomItem.setName("");
        findRoom(roomItem);
        fuChoseRoomView = (FuChoseRoomView)mModel;
        return mModel.getFuView();
    }


    private void findRoom(RoomItem roomItem) {
        MyTask bannerTask = TaskManager.getInstace().findRoom(getCallBackInstance(), roomItem);
        excuteNetTask(bannerTask,true);
    }
    private static final int MSG_ROOM = 1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_ROOM:
                    List<RoomItem> roomItemList = (List<RoomItem>)msg.obj;
                    if(roomItemList!=null&&roomItemList.size()>0){
                        if("microdistrict".equals(roomItemList.get(0).getType())){
                            fuChoseRoomView.setFistAdapter(roomItemList);
                        }else{
                            fuChoseRoomView.setSecAdapter(roomItemList);
                        }
                    }
                    break;
            }
            super.handleMessage(msg);

        }
    };
    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        if(rspObj!=null&&rspObj.getSuccess()){
            List<RoomItem> roomItemList = JSON.parseArray(rspObj.getData().toString(),RoomItem.class);
            Message message = handler.obtainMessage();
            message.what = MSG_ROOM;
            message.obj = roomItemList;
            handler.sendMessage(message);
        }else if(rspObj!=null){
            Looper.prepare();
            ToastUtil.showToast(rspObj.getErrMessage());
        }
    }

    @Override
    protected void netErrorChild(int taskId, String msg) {

    }

    @Override
    protected void cancelChild(int taskId) {

    }

    public static final int EVENT_FIND_ROOM = 1;
    public static final int EVENT_FINISH = 2;

    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {
            switch (event){
                case EVENT_FIND_ROOM:

                    RoomItem temp = (RoomItem)object;
                    findRoom(temp);
                    break;
                case EVENT_FINISH:
                    allFinish();
                    break;
            }
        }
    }
}
