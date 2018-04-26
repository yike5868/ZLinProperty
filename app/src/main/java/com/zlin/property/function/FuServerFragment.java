package com.zlin.property.function;

import android.content.Intent;
import android.os.Bundle;
import android.sax.RootElement;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlin.property.FuApp;
import com.zlin.property.R;
import com.zlin.property.activity.FuContentActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.control.FuUiFrameModel;
import com.zlin.property.db.po.Repair;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.ToastUtil;

/**
 * Created by zhanglin03 on 2018/4/23.
 */

public class FuServerFragment  extends FragmentParent {
    FuServerView fuView;
    Repair repair;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("activity","FuMainFragment");
        FuUiFrameManager lFuUiFrameManager = ((FuApp) getActivity()
                .getApplication()).getFuUiFrameManager();

        mModel = lFuUiFrameManager.createFuModel(
                FuUiFrameManager.FU_SERVER, getActivity(),
                new OnEventClick());
        fuView = (FuServerView) mModel;
        fuView.setPhotoList(null);
        return mModel.getFuView();
    }

    public static final int EVENT_LIST = 0;

    public static final int EVENT_PHOTO = 1;

    public static final int EVENT_UP_PHOTO = 2;

    public static final int EVENT_SAVE = 3;

    class OnEventClick implements FuEventCallBack {
        @Override
        public void EventClick(int event, Object object) {
            switch (event){
                case EVENT_PHOTO:

                    Bundle bundle = (Bundle) object;
                    int position = bundle.getInt("position");
                    if(position==0){
                        showChoseDialog();
                    }else
                        ((FuContentActivity) getActivity()).replaceFragment(
                                FuUiFrameManager.FU_CONTENT_ID, FuUiFrameManager.FU_SERVER_LIST, null);
                    break;

                case EVENT_LIST:
                    ((FuContentActivity) getActivity()).replaceFragment(
                            FuUiFrameManager.FU_CONTENT_ID, FuUiFrameManager.FU_SERVER_LIST, null);
                    break;
                case EVENT_UP_PHOTO:
                    break;
                case EVENT_SAVE:
                    repair = (Repair)object;
                    saveRepair();
                    break;
            }
        }
    }
    private void saveRepair() {
        MyTask bannerTask = TaskManager.getInstace().saveRepair(getCallBackInstance(), repair);
        excuteNetTask(bannerTask,true);
    }
    @Override
    public void openCamear(Intent data) {
        super.openCamear(data);
        ToastUtil.showToast("相机");
    }

    @Override
    public void openGallery(Intent data) {
        super.openGallery(data);
        ToastUtil.showToast("相册");
    }

    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        switch (taskId){
            case MyTask.SAVE_REPAIR:
                if(rspObj!=null && rspObj.getSuccess()){
                    ToastUtil.showToast("提交成功！请等待接单！");
                }else{
                    ToastUtil.showToast("提交失败！请重试！");
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
}
