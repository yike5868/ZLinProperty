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
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zlin.property.Constant;
import com.zlin.property.FuApp;
import com.zlin.property.db.helper.ALocalSqlHelper;
import com.zlin.property.net.MyTask;
import com.zlin.property.net.NetCallBack;
import com.zlin.property.net.NetManager;
import com.zlin.property.net.TaskManager;
import com.zlin.property.tools.AppConfig;
import com.zlin.property.tools.MediaTools;
import com.zlin.property.tools.ToastUtil;
import com.zlin.property.tools.ToolUtil;
import com.zlin.property.uview.ChosePhotoDialog;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public abstract class FragmentParent extends Fragment {

    protected FuUiFrameModel mModel;

    protected String PAGE_SIZE = "10"; // 加载条数

    public final static int MSG_CAMEAR = 1001;
    public final static int MSG_CHOSE = 1002;
    public final static int MSG_CANCEL = 1003;
    public final static int MSG_CONTENT_FINISH = 99999;
    public final static int MSG_ERROR = 999998;
    public final static int MSG_FILE = 1;//上传图片



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

    public abstract void initData(Bundle bundle);

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

        if (animRefresh) {

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

    public String getSp(String name){
        SharedPreferences lPreferences = activity.getSharedPreferences(
                Constant.LOGIN_CONFIG, activity.MODE_PRIVATE);
        String str = lPreferences.getString(name, "");
        return str;
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
                if (rspObj != null)
                    error.obj = rspObj.getMessage();
                else
                    error.obj = "网络访问错误！";
                error.sendToTarget();
            }
            if(!rspObj.getSuccess()){
                Message message = parentHandler.obtainMessage();
                message.what = MSG_ERROR;
                message.obj = rspObj.getMessage();
                parentHandler.sendMessage(message);
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

    public Handler parentHandler = new Handler() {

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
//                    ToastUtil.showToast(getContext(), "保存成功!", Toast.LENGTH_LONG);
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
                case MSG_CONTENT_FINISH:
                    getActivity().finish();
                    break;

                case MSG_ERROR:
                    ToastUtil.showToast(msg.obj.toString());
                    break;


            }
        }
    };


    public void allFinish() {
        getActivity().finish();
    }

    public void luBanUpload(File file) {
        File dirFile = new File(AppConfig.IMAGE_SD_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        if (!file.exists())
            return;

//        try {
//            File compressedImageFile = new Compressor(getActivity()).compressToFile(file);
//            uploadFile(compressedImageFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            Luban.with(getActivity())
                    .load(file)
                    .ignoreBy(100)
                    .setTargetDir(AppConfig.IMAGE_SD_PATH)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            uploadFile(file);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showToast("资源获取失败!");
                        }
                    }).launch();
        } catch (Exception e) {
            ToastUtil.showToast("获取资源失败！");

        }
    }
        //上传图片

    public void uploadFile(File file) {
        File[] files = new File[1];
        files[0] = file;
        MyTask bannerTask = TaskManager.getInstace().upLoadFile(getCallBackInstance(), files);
        excuteNetTask(bannerTask, true);
    }


}
