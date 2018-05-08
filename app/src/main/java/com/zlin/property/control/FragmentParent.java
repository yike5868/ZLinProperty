package com.zlin.property.control;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zlin.property.Constant;
import com.zlin.property.FuApp;
import com.zlin.property.activity.FuContentActivity;
import com.zlin.property.db.helper.ALocalSqlHelper;
import com.zlin.property.db.po.Entry;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.NetCallBack;
import com.zlin.property.net.NetManager;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.MediaTools;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.ToolUtil;
import com.zlin.property.uview.ChosePhotoDialog;

import java.io.File;

public abstract class FragmentParent extends Fragment {

    protected FuUiFrameModel mModel;

    protected String PAGE_SIZE = "10"; // 加载条数

    public final static int MSG_CAMEAR = 1001;
    public final static int MSG_CHOSE = 1002;
    public final static int MSG_CANCEL = 1003;


    /**
     * 数据库
     */
    protected ALocalSqlHelper sqlHelper;


    protected int type;//分类
    Activity activity = null;//主页面

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlHelper = ((FuApp) getActivity().getApplication()).getSqlHelper();
        activity = getActivity();

    }

    public ALocalSqlHelper getSqlHelper() {
        if (sqlHelper == null)
            sqlHelper = ((FuApp) getActivity().getApplication()).getSqlHelper();
        return sqlHelper;
    }

    protected abstract void loadDataChild(int taskId, FuResponse rspObj); //

    protected abstract void netErrorChild(int taskId, String msg); //

    protected abstract void cancelChild(int taskId); //

    protected mNetCallBack mNetBaseCallBack;

    public final int NET_ERROR = 500; // 网络错误

    public mNetCallBack getCallBackInstance() {

        if (mNetBaseCallBack == null) {
            mNetBaseCallBack = new mNetCallBack();
        }

        return mNetBaseCallBack;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void excuteNetTask(final MyTask lTask, boolean animRefresh) {

        if (!animRefresh) {

            ToolUtil.showPopWindowLoading(getActivity());
        }

        Runnable netRunnable = new Runnable() {
            @Override
            public void run() {
                NetManager manager = NetManager.getInstance(getActivity());
                manager.addNetTask(lTask);
                manager.excuteNetTask(lTask);
            }
        };
        parentHandler.post(netRunnable);
    }

    //保存
    public void saveSP(String name, Object entry) {
        SharedPreferences lPreferences = activity.getSharedPreferences(
                Constant.LOGIN_CONFIG, activity.MODE_PRIVATE);
        lPreferences.edit().putString(name, JSON.toJSONString(entry)).commit();
    }

    public <T> T getSP(String name, Class<T> clazz) {
        SharedPreferences lPreferences = activity.getSharedPreferences(
                Constant.LOGIN_CONFIG, activity.MODE_PRIVATE);
        String str = lPreferences.getString(name, "");
        return JSON.parseObject(str, clazz);
    }


    class mNetCallBack implements NetCallBack {

        @Override
        public void cancel(int taskId) {
            Log.e("mNet", mModel.getClass().getName());
            if (taskId == MyTask.UP_LOAD_FILE) {
                ToolUtil.hidePopLoading();
                ToastUtil.showToast(getContext(), "已取消!", Toast.LENGTH_LONG);
                return;
            }
            cancelChild(taskId);
        }

        @Override
        public void loadData(int taskId, FuResponse rspObj) {

            if (taskId == MyTask.UP_LOAD_FILE) {
                Message error = Message.obtain(parentHandler);
                error.what = MyTask.UP_LOAD_FILE;
                if(rspObj!=null)
                error.obj = rspObj.getErrMessage();
                else
                    error.obj = "网络访问错误！";
                error.sendToTarget();
                return;
            }

            Log.e("mNet", mModel.getClass().getName());
            loadDataChild(taskId, rspObj);
        }

        @Override
        public void netError(int taskId, String msg) {
            Log.e("mNet", mModel.getClass().getName());

            if (taskId == MyTask.UP_LOAD_FILE) {
                ToastUtil.showToast(getContext(), "保存失败!" + msg, Toast.LENGTH_LONG);
                return;
            }

            netErrorChild(taskId, msg);
        }
    }

    /**
     * 获取本地软件版本号
     */
    public int getLocalVersion() {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = activity
                    .getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    ChosePhotoDialog chosePhotoDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppConfig.PHOTO_GALLERY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    openGallery(data);
                }
                break;
            case AppConfig.FROM_CAMERA:
                openCamear(data);
                break;
        }
    }

    public void openCamear(Intent data) {

    }

    public void openGallery(Intent data) {

    }

    /**
     * 上传图片 弹窗
     */
    public void showChoseDialog() {
        if (chosePhotoDialog == null) {
            chosePhotoDialog = new ChosePhotoDialog(getContext(), parentHandler);
        }
        chosePhotoDialog.show();
    }

    private Handler parentHandler = new Handler() {

        public void handleMessage(Message msg) {
            ToolUtil.hidePopLoading();
            switch (msg.what) {

                case MyTask.ERROR:
                    ToastUtil.showToast(getContext(), msg.obj.toString(), Toast.LENGTH_LONG);
                    break;

                case MyTask.UP_LOAD_FILE:
//                    File dir = new File(Constant.PIC_DIR);
//                    File[] pics = dir.listFiles();
//                    for (int i = 0; i < pics.length; i++) {
//                        pics[i].delete();
//                    }
                    ToastUtil.showToast(getContext(), "保存成功!", Toast.LENGTH_LONG);
//                    ((FuContentActivity) getActivity()).goToPrePage();
                    break;
                case MSG_CAMEAR:
                    MediaTools.chooseCamera(FragmentParent.this);
                    if (chosePhotoDialog != null && chosePhotoDialog.isShowing())
                        chosePhotoDialog.dismiss();
                    break;
                case MSG_CHOSE:
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    try {
                        startActivityForResult(Intent.createChooser(intent, "请选择一张图片"),
                                AppConfig.PHOTO_GALLERY_CODE);
                    } catch (Exception e) {
                        ToastUtil.showToast("请安装文件管理器");
                    }
                    chosePhotoDialog.dismiss();
                    break;
                case MSG_CANCEL:
                    chosePhotoDialog.dismiss();
                    break;

            }
        }
    };


    public void allFinish(){
        getActivity().finish();
    }


}
