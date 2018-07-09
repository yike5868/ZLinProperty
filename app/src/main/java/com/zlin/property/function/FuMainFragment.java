package com.zlin.property.function;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zlin.property.FuApp;
import com.zlin.property.activity.FuMainActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.Banner;
import com.zlin.property.db.po.BannerDto;
import com.zlin.property.db.po.Repair;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class FuMainFragment extends FragmentParent {

    FuMainView fuMainView;

    private UserInfo userInfo;

    List<Repair> repairList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("activity","FuMainFragment");
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_MAIN_HOME, getActivity(),
                new OnEventClick());
        findBannerByVersion();

        userInfo = getSP("userInfo",UserInfo.class);
        fuMainView = (FuMainView) mModel;
        getRepairs();
        return mModel.getFuView();
    }
    List bannerList;
    public static final int MSG_BANNER = 0;
    public static final int MSG_GET_REPAIR = 1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_BANNER:
                    fuMainView.initBanner(bannerList);
                    break;
                case MSG_GET_REPAIR:
                    fuMainView.setRepair(repairList);
                    break;
            }
            super.handleMessage(msg);

        }
    };

    /**
     * 获取首页banner图
     */
    private void findBannerByVersion() {
        Banner banner = new Banner();
        banner.setVersion(getLocalVersion()+"");
        MyTask bannerTask = TaskManager.getInstace().findBannerByVersion(getCallBackInstance(), banner);
        excuteNetTask(bannerTask,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getRepairs();
    }

    /**
     * 获取所有订单
     */
    private void getRepairs(){
        Repair repair = new Repair();
        repair.setUserId(userInfo.getUserId());
        MyTask bannerTask = TaskManager.getInstace().getRepairs(getCallBackInstance(), repair);
        excuteNetTask(bannerTask,true);
    }

    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        ToolUtil.hidePopLoading();
        switch (taskId){
            case MyTask.BANNER:
                if(rspObj!=null&&rspObj.getData()!=null)
                bannerList= JSON.parseArray(rspObj.getData().toString(),Banner.class);
                handler.sendEmptyMessage(MSG_BANNER);
                break;
            case MyTask.GET_REPAIR:
                if(rspObj!=null&&rspObj.getSuccess()){
                    repairList = JSONArray.parseArray(rspObj.getData().toString(),Repair.class);
                    handler.sendEmptyMessage(MSG_GET_REPAIR);
                }
                break;
        }
    }
    @Override
    protected void netErrorChild(int taskId, String msg) {

    }

    @Override
    protected void cancelChild(int taskId) {

    }

    @Override
    public void initData(Bundle bundle) {

    }

    public static final int EVENT_GRID = 1;
    public static final int EVENT_REPAIR = 2;
    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {
            switch (event){
                case EVENT_GRID:
                    ((FuMainActivity) getActivity()).replaceFragment(
                            FuUiFrameManager.FU_CONTENT_ID, FuUiFrameManager.FU_SERVER, null);
                    break;
                case EVENT_REPAIR:
                    ((FuMainActivity) getActivity()).replaceFragment(
                            FuUiFrameManager.FU_CONTENT_ID, FuUiFrameManager.FU_SERVER, (Bundle)object);
                    break;
            }
        }
    }
}
