package com.zlin.property.function;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.zlin.property.FuApp;
import com.zlin.property.activity.FuAliPayActivity;
import com.zlin.property.activity.FuMainActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.FeeUser;
import com.zlin.property.db.po.PropertyFee;
import com.zlin.property.db.po.Repair;
import com.zlin.property.db.po.Room;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.Tool;
import com.zlin.property.tools.ToolUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglin03 on 2018/7/10.
 */

public class FuMineFragment extends FragmentParent {

    private int pageNo = 1;
    private String pageState = AppConfig.PAY_NO;
    UserInfo userInfo;
    List<PropertyFee> feeList;

    private static final int SDK_PAY_FLAG = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_MINE, getActivity(),
                new OnEventClick());
        userInfo = getSP("userInfo", UserInfo.class);
        feeList = new ArrayList<>();
        getFee();
        return mModel.getFuView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppConfig.tempRoom != null) {
            Room room = new Room();
            room.setRoomId(AppConfig.tempRoom.getRoomId());
            room.setBuildingId(AppConfig.tempRoom.getBuildingId());
            room.setUnitId(AppConfig.tempRoom.getUnitId());
            room.setRoomName(AppConfig.tempRoom.getRoomName());
            room.setAddress(AppConfig.tempRoom.getBuildingName() + AppConfig.tempRoom.getUnitName() + AppConfig.tempRoom.getRoomName());
            userInfo.getRoomList().add(room);
            MyTask bannerTask = TaskManager.getInstace().addRoomUser(getCallBackInstance(), userInfo);
            excuteNetTask(bannerTask, true);
        }
    }

    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        ToolUtil.hidePopLoading();
        switch (taskId) {
            case MyTask.GET_FEE:
                if (rspObj.getData() != null) {
                    List<PropertyFee> feeList22 = JSON.parseArray(rspObj.getData().toString(), PropertyFee.class);
                    feeList.addAll(feeList22);
                }
                handler.sendEmptyMessage(MSG_FINISH);
                break;
            case MyTask.GET_ORDER_INFO:
                aliPay(rspObj.getData().toString());
                break;

        }
    }

    private void aliPay(final String orderInfo){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
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
    private void getFee() {
        FeeUser feeUser = new FeeUser();
        feeUser.setPageIndex(pageNo);
        feeUser.setPageSize(AppConfig.PAGE_SIZE);
        Room room = getSP("selectRoom", Room.class);
        feeUser.setRoomId(room.getRoomId());
        feeUser.setPayState(pageState);
        feeUser.setUserId(userInfo.getUserId());
        MyTask bannerTask = TaskManager.getInstace().getFee(getCallBackInstance(), feeUser);
        excuteNetTask(bannerTask, true);
    }

    /**
     * 获取支付宝订单信息
     */

    private void getOrderInfo(Map<String, PropertyFee> selectMap) {
        ArrayList<PropertyFee> propertyFeeArrayList = new ArrayList<>();
        for (Map.Entry<String, PropertyFee> entry : selectMap.entrySet()) {
            propertyFeeArrayList.add(entry.getValue());
        }
        MyTask bannerTask = TaskManager.getInstace().getOrderInfo(getCallBackInstance(), propertyFeeArrayList);
        excuteNetTask(bannerTask, true);
    }

    public static final int EVENT_REFRESH = 1;
    public static final int EVENT_LOADMORE = 2;
    public static final int MSG_FINISH = 3;
    public static final int EVENT_ADD_ROOM = 4;
    public static final int EVENT_GET_ORDER_INFO = 5;

    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {
            Bundle bundle;
            switch (event) {
                case EVENT_REFRESH:
                    bundle = (Bundle) object;
                    pageState = bundle.getString("selectType");
                    pageNo = 1;
                    feeList.clear();
                    getFee();
                    break;
                case EVENT_LOADMORE:
                    bundle = (Bundle) object;
                    pageNo++;
                    pageState = bundle.getString("selectType");
                    getFee();
                    break;
                case EVENT_ADD_ROOM:
                    ((FuMainActivity) getActivity()).replaceFragment(
                            FuUiFrameManager.FU_CONTENT_ID, FuUiFrameManager.FU_CHOSE_ROOM, null);
                    break;
                case EVENT_GET_ORDER_INFO:
                    Map<String ,PropertyFee> propertyFeeMap = (Map<String ,PropertyFee>)object;
                    getOrderInfo(propertyFeeMap);
                    break;

            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FINISH:
                    ((FuMineView) mModel).setPropertyFeeList(feeList);
                    ((FuMineView) mModel).loadFinish();
                    break;
                case SDK_PAY_FLAG:
                    ToastUtil.showToast("发起了支付宝支付！");
                    break;
            }
        }
    };


}
