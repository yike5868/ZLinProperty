package com.zlin.property.activity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.zlin.property.R;
import com.zlin.property.control.CustomFragmentManager;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.services.DownloadService;

import java.util.ArrayList;

/**
 * 初始化 页面
 *
 * @author Administrator
 */
public class FuInitNavActivity extends FragmentActivity {

    private boolean mExitApp;

    private CustomFragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fu_public_no_bottom);

        mManager = CustomFragmentManager.getInstance(this);

        Log.e("activity", "FuInitNavActivity");

        mExitApp = false;
        mManager.initFragmentManager(CustomFragmentManager.INIT_NAV,
                getSupportFragmentManager());

        mManager.addFragment(R.id.fu_fragment_contain,
                FuUiFrameManager.FU_WELCOME);// 初始化的ID
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }

    public void replaceFragment(int FragmentId, Bundle bundle) {

        mManager.replaceFragment(R.id.fu_fragment_contain, FragmentId, bundle);
    }

    /**
     * 去登录页面
     */
    public void goToLoginPage() {

		startActivity(new Intent(this, FuMainActivity.class));
//        startActivity(new Intent(this, FuLoginRegistActivity.class));
        finish();
    }

    /**
     * type 1: 表示下载升级包，2 表示下载banner轮播图 启动下载服务
     */
    public void startDownloadService(int type, String url) {

        if (url == null || url.trim().length() < 1) {
            return;
        }

        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("type", type);
        intent.putExtra("Url", url);

        startService(intent);

    }

    /**
     * 判断下载服务是否存活
     */
    public boolean isWorkedDownloadService() {
        ActivityManager myManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals("com.original.client.service.DownloadService")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭APP
     */
    public void finishApp() {
        mExitApp = true;
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mExitApp) {

            mManager.ExitFreeResource();

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } else {

//            mManager.clearBackFragmentAll(CustomFragmentManager.INIT_NAV);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finishApp();
        }

        return false;
    }

    ;
}
