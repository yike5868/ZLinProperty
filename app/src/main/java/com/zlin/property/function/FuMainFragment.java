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
import com.zlin.property.activity.FuMainActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.Banner;
import com.zlin.property.db.po.BannerDto;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class FuMainFragment extends FragmentParent {

    FuMainView fuMainView;

    public static final int MSG_BANNER = 0;



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
        fuMainView = (FuMainView) mModel;
        return mModel.getFuView();
    }
    List bannerList;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_BANNER:
                    fuMainView.initBanner(bannerList);
                    break;
            }
            super.handleMessage(msg);

        }
    };

    private void findBannerByVersion() {
        Banner banner = new Banner();
        banner.setVersion(getLocalVersion()+"");
        MyTask bannerTask = TaskManager.getInstace().findBannerByVersion(getCallBackInstance(), banner);
        excuteNetTask(bannerTask,true);
    }

    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        switch (taskId){
            case MyTask.BANNER:
                if(rspObj!=null&&rspObj.getData()!=null)
                bannerList= JSON.parseArray(rspObj.getData().toString(),Banner.class);
                handler.sendEmptyMessage(MSG_BANNER);
                break;
        }
    }
    @Override
    protected void netErrorChild(int taskId, String msg) {

    }

    @Override
    protected void cancelChild(int taskId) {

    }
    public static final int EVENT_GRID = 1;
    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {
            switch (event){
                case EVENT_GRID:
                    ((FuMainActivity) getActivity()).replaceFragment(
                            FuUiFrameManager.FU_CONTENT_ID, FuUiFrameManager.FU_SERVER, null);
                    break;
            }
        }
    }
}
