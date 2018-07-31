package com.zlin.property.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.zlin.property.Constant;
import com.zlin.property.R;
import com.zlin.property.activity.FuLoginActivity;
import com.zlin.property.control.FuUiFrameManager;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.net.NetManager;
import com.zlin.property.view.CustomGuideDialog;
import com.zlin.property.view.CustomLoadingDialog;


public class ToolUtil {


    private static CustomLoadingDialog mLoadingDialog; // Dialog加载动画

    public static void showPopWindowLoading(final Context context) {

        if (mLoadingDialog == null)
            mLoadingDialog = new CustomLoadingDialog(context);

        mLoadingDialog.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    NetManager manager = NetManager.getInstance(context);
                    manager.cnacelAllNetTask();

                }

                return false;
            }
        });

        if (!mLoadingDialog.isShowing()) {

            mLoadingDialog.show();
        }
    }

    // Dialog新手指导
    public static void showGuideDialog(final Context context, int resId) {

        CustomGuideDialog mGuideDialog = new CustomGuideDialog(context, resId);

        if (!mGuideDialog.isShowing()) {
            mGuideDialog.show();
        }
    }

    /**
     * 隐藏Loading加载效果
     */
    public static boolean hidePopLoading() {

        if (mLoadingDialog == null || !mLoadingDialog.isShowing()) {
            return false;
        }

        mLoadingDialog.dismiss();

        mLoadingDialog = null;

        return true;
    }

    /***************************** 判断View是否画完 ********************************/
    // 检测时间间隔
    public static final int DRAW_OVER_LISTVIEW = 8000;
    public static final int DRAW_OVER = 9000;
    private static final int detchTime = 5;

    public static void chechLoadOver(final View view, final Handler handler) {

        /***************** 以下代码用来循环检测activity是否初始化完毕 ***************/
        Runnable showPopWindowRunnable = new Runnable() {

            @Override
            public void run() {
                // 得到activity中的根元素
                // 如何根元素的width和height大于0说明activity已经初始化完毕
                if (view != null && view.getWidth() > 0 && view.getHeight() > 0) {

                    // 停止检测
                    handler.removeCallbacks(this);

                    handler.sendEmptyMessage(DRAW_OVER);

                } else {
                    // 如果activity没有初始化完毕则等待5毫秒再次检测
                    handler.postDelayed(this, detchTime);
                }
            }
        };
        // 开始检测
        handler.post(showPopWindowRunnable);
        /****************** 以上代码用来循环检测activity是否初始化完毕 *************/
    }

    public static void chechViewLoadOver(final View view,
                                         final Handler handler, final int what) {

        /***************** 以下代码用来循环检测activity是否初始化完毕 ***************/
        Runnable showPopWindowRunnable = new Runnable() {

            @Override
            public void run() {
                // 得到activity中的根元素
                // 如何根元素的width和height大于0说明activity已经初始化完毕
                if (view != null && view.getWidth() > 0 && view.getHeight() > 0) {

                    // 停止检测
                    handler.removeCallbacks(this);

                    handler.sendEmptyMessage(what);

                } else {
                    // 如果activity没有初始化完毕则等待5毫秒再次检测
                    handler.postDelayed(this, detchTime);
                }
            }
        };
        // 开始检测
        handler.post(showPopWindowRunnable);
        /****************** 以上代码用来循环检测activity是否初始化完毕 *************/
    }


    public static boolean checkLogin(Context context){
        UserInfo userInfo = getSP(context,"userInfo",UserInfo.class);
        if(userInfo == null){
            Intent intent = new Intent(context, FuLoginActivity.class);
            context.startActivity(intent);
            return false;
        }
        return true;
    }

    public static <T> T getSP(Context context,String name, Class<T> clazz){
        SharedPreferences lPreferences =context.getSharedPreferences(
                Constant.LOGIN_CONFIG,Context.MODE_PRIVATE);
        String str = lPreferences.getString(name,"");
        if(StringUtil.isEmpty(str))
            return null;
        return JSON.parseObject(str,clazz);
    }

    public static String hidePhone(String args) {
        if(StringUtil.isEmpty(args))
            return "";
        String phoneNumber = args.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        return phoneNumber;
    }
}
