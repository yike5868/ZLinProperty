package com.zlin.property.function;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlin.property.FuApp;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.FeeUser;
import com.zlin.property.db.po.PropertyFee;
import com.zlin.property.db.po.Repair;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.Tool;

import java.util.List;

/**
 * Created by zhanglin03 on 2018/7/10.
 */

public class FuMineFragment extends FragmentParent {

    private int pageNo = 1;
    private String pageState = AppConfig.PAY_NO;
    UserInfo userInfo;
    List<PropertyFee> feeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_MINE, getActivity(),
                new OnEventClick());
        userInfo = getSP("userInfo",UserInfo.class);

        return mModel.getFuView();
    }

    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        switch (taskId){
            case MyTask.GET_FEE:
                feeList = (List)rspObj.getData();
                handler.sendEmptyMessage(MSG_FINISH);
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
    /**
     * 获取
     */
    private void getFee(){
        FeeUser feeUser = new FeeUser();
        feeUser.setPageIndex(pageNo);
        feeUser.setPageSize(AppConfig.PAGE_SIZE);
        feeUser.setRoomId(userInfo.getRoomId());
        feeUser.setPayState(pageState);
        feeUser.setUserId(userInfo.getUserId());
        MyTask bannerTask = TaskManager.getInstace().getFee(getCallBackInstance(), feeUser);
        excuteNetTask(bannerTask,true);
    }
    public static final int EVENT_REFRESH = 1;
    public static final int EVENT_LOADMORE = 2;
    public static final int MSG_FINISH = 3;
    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {
            switch (event){
                case EVENT_REFRESH:
                    pageNo = 1;
                    getFee();
                    break;
                case EVENT_LOADMORE:
                    getFee();
                    pageNo ++;
                    break;

            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_FINISH:
                    ((FuMineView)mModel).loadFinish();
                    break;
            }
        }
    };


}
