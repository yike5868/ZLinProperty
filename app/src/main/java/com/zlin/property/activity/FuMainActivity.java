package com.zlin.property.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import com.zlin.property.Constant;
import com.zlin.property.FuApp;
import com.zlin.property.R;
import com.zlin.property.control.CustomFragmentManager;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.net.NetManager;
import com.zlin.property.tools.ToolUtil;
import com.zlin.property.view.NavigationMenu;
import com.zlin.property.view.NavigationMenu.EventClick;
import com.zlin.property.view.SweetAlert.SweetAlertDialog;

public class FuMainActivity extends FuParentActivity {


    private CustomFragmentManager mManager;

    private NavigationMenu mBottomMenu; // 底部导航栏

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fu_public_have_bottom);
        mBottomMenu = (NavigationMenu) findViewById(R.id.fu_bottom_menu);
        mBottomMenu.setEventListen(onEventClick);
        mManager = CustomFragmentManager.getInstance(this);
        mManager.initFragmentManager(CustomFragmentManager.MAIN,
                getSupportFragmentManager());
        mManager.deleteAllFragment(CustomFragmentManager.MAIN);
        mManager.addFragment(R.id.fu_fragment_contain,
                FuUiFrameManager.FU_MAIN_HOME);
//                FuUiFrameManager.FU_WELCOME);


    }

    protected void onResume() {
        super.onResume();
        mManager.initFragmentManager(CustomFragmentManager.MAIN,
                getSupportFragmentManager());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        ExitAppAndSaveData();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private void ExitAppAndSaveData() {
        mManager.ExitFreeResource();
        ((FuApp) getApplication()).exitPersonInfo();
        SharedPreferences lPreferences = getSharedPreferences(
                Constant.LOGIN_CONFIG, MODE_PRIVATE);
        lPreferences.edit().putBoolean(Constant.IS_LOGIN_RUNNING, false)
                .commit();
    }

    public void replaceFragment(int ViewActId, int FragmentId, Bundle bundle) {

        switch (ViewActId) {
            case FuUiFrameManager.FU_MAIN_ID:
                mManager.replaceFragment(R.id.fu_fragment_contain, FragmentId,
                        bundle);
                break;
            case FuUiFrameManager.FU_CONTENT_ID:
                Intent intent = new Intent(this, FuContentActivity.class);
                intent.putExtra(FuContentActivity.FRAGMENT_ACTION_KEY, FragmentId);
                intent.putExtra(FuContentActivity.INTENT_BUNDLE, bundle);
                startActivity(intent);
                break;
        }

    }

    EventClick onEventClick = new EventClick() {

        @Override
        public void onEventClick(int event) {

            int lAction = mManager.mcurrentAction();

            if (lAction == event) {
                return;
            }

            mManager.replaceFragment(R.id.fu_fragment_contain, event, null);

        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean lShow = ToolUtil.hidePopLoading();
            if (lShow) {
                NetManager manager = NetManager.getInstance(this);
                manager.cnacelAllNetTask();
            } else {
                int lCurrentAction = mManager.mcurrentAction();
                if (lCurrentAction == FuUiFrameManager.FU_MAIN_HOME) { // 首页在点击返回，直接退出程序
                    openExitAppDialog();
                    return false;
                }
                mManager.gotoBackFragment(CustomFragmentManager.MAIN);
                mBottomMenu.setCurrentBtnState(mManager.mcurrentAction());
            }
        }
        return false;
    }

    public void openExitAppDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("是否退出?")
                .setCancelText("取消")
                .setConfirmText("是的")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                    }
                }).show();
    }
}
