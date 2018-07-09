package com.zlin.property.function;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zlin.property.FuApp;
import com.zlin.property.activity.FuContentActivity;
import com.zlin.property.control.FragmentParent;
import com.zlin.property.control.FuEventCallBack;
import com.zlin.property.control.FuResponse;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.Photo;
import com.zlin.property.db.po.Repair;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.ToolUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglin03 on 2018/4/23.
 */

public class FuServerFragment  extends FragmentParent {
    FuServerView fuView;
    Repair repair;
    ArrayList<Photo> photoList;
    UserInfo userInfo;
    private boolean canEdit = true;
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
        photoList = new ArrayList<>();

        fuView.setCanEdit(canEdit);
        fuView.setPhotoList(photoList);
        userInfo = getSP("userInfo",UserInfo.class);
        Bundle lBundle = ((FuContentActivity) getActivity()).getIntentBundle();
        if(lBundle!=null) {
            repair = (Repair) lBundle.getSerializable("repair");
            if(repair!=null)
            fuView.setData(repair);
        }
        return mModel.getFuView();
    }

    @Override
    public void onResume() {
        super.onResume();

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
                    bundle.putSerializable("photoList",photoList);
                        ((FuContentActivity) getActivity()).replaceFragment(
                                FuUiFrameManager.FU_CONTENT_ID, FuUiFrameManager.FU_VIEW_PHOTO, null);
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
        if(AppConfig.CamerPhotoFile!=null&&AppConfig.CamerPhotoFile.exists()){
            luBanUpload(AppConfig.CamerPhotoFile);
        }

    }

    @Override
    public void openGallery(Intent data) {
        super.openGallery(data);
        try {
            Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
            File file = new File(path);
            luBanUpload(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FILE:
                    ToastUtil.showToast("上传成功！");
                    fuView.setPhotoList(photoList);
                    break;
            }
            super.handleMessage(msg);

        }
    };


    @Override
    protected void loadDataChild(int taskId, FuResponse rspObj) {
        ToolUtil.hidePopLoading();
        switch (taskId){
            case MyTask.SAVE_REPAIR:
                if(rspObj !=null){
                ToastUtil.showToast(rspObj.getMessage());
                if(rspObj!=null && rspObj.getSuccess()){
                    parentHandler.sendEmptyMessage(MSG_CONTENT_FINISH);
                }else{

                }}
                else
                    ToastUtil.showToast("失败");
                break;
            case MyTask.UP_LOAD_FILE:
                if(rspObj!=null && rspObj.getSuccess()){
                    Photo photo = new Photo();
                    photo.setPath(rspObj.getData().toString());
                    photo.setUserId(userInfo.getUserId());
                    photoList.add(photo);
                    handler.sendEmptyMessage(MSG_FILE);
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
        if(bundle!=null)
        repair =(Repair) bundle.getSerializable("repair");
        else {

        }
    }
}
