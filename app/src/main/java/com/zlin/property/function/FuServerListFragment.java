package com.zlin.property.function;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.zlin.property.FuApp;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.Repair;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;

import java.util.List;

/**
 * Created by zhanglin on 2018/4/24.
 */

public class FuServerListFragment extends FragmentParent {
    FuServerListView fuView;
    List<Repair> repairList;
    UserInfo userInfo;
    public static final int MSG_REPAIR_LIST = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("activity","FuMainFragment");
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_SERVER_LIST, getActivity(),
                null);
        fuView = (FuServerListView) mModel;


        getRepairs();

        return mModel.getFuView();
    }
    public void getRepairs() {
        userInfo = getSP("userInfo",UserInfo.class);
        Repair repair;
        repair = new Repair();
        repair.setUserId(userInfo.getUserId());
        repair.setRoomId(userInfo.getRoomId());
        MyTask lTask = TaskManager.getInstace(getActivity())
                .getRepairs(getCallBackInstance(), repair);

        excuteNetTask(lTask, true);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_REPAIR_LIST:
                    fuView.setRepairList(repairList);
                    break;
            }
            super.handleMessage(msg);

        }
    };
    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        if(rspObj!=null&&rspObj.getSuccess()){
            repairList= JSON.parseArray(rspObj.getData().toString(),Repair.class);
            handler.sendEmptyMessage(MSG_REPAIR_LIST);
        }
    }

    @Override
    protected void netErrorChild(int taskId, String msg) {

    }

    @Override
    protected void cancelChild(int taskId) {

    }
}
