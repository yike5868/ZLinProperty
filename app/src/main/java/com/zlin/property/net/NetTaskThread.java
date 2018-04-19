// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.zlin.property.net;

import android.content.Context;
import android.net.ConnectivityManager;


import java.util.HashMap;

import com.zlin.property.control.FuResponse;
import com.zlin.property.net.NetManager.netOverCallBack;

/**
 * @类 名: NetTaskThread
 * @类描述: 网络交互线程
 */

public class NetTaskThread implements Runnable {

    private Context mContext;

    private MyTask mTask; // 当前执行的任务

    private netOverCallBack mCallBack;

    private NetConn mConnect; // 网络联网对象

    public NetTaskThread(Context context, MyTask task, netOverCallBack back) {
        mContext = context;
        mTask = task;
        mCallBack = back;
        mCancel = false;
        mConnect = new NetConn(mContext);
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {

        if (!isOpenNetwork()) {
            if (mTask.mCallBack != null) {
                mTask.mCallBack.netError(mTask.mTaskId, "当前无可用网络,请设置网络");
            }
            mCallBack.NetThreadOver(mTask.mTaskId);
            return;
        }

        if (mCancel) {

            if (mTask.mCallBack != null) {
                mTask.mCallBack.cancel(mTask.mTaskId);
            }

            mCallBack.NetThreadOver(mTask.mTaskId);
            return;
        }

        if (mTask.mUrl == null || mTask.mUrl.equals("")) {

            if (mTask.mCallBack != null) {
                mTask.mCallBack.netError(mTask.mTaskId, "网络地址错误");
            }

            mCallBack.NetThreadOver(mTask.mTaskId);

            return;
        }
        FuResponse lRsp = null;



                HashMap<String, Object> paramMap = getRequestData(mTask);

                try {
                    lRsp = mConnect.httpPost(mTask);
//				lRsp = mConnect.httpPost(mTask.mRequestData);
                } catch (Exception e) {
                    e.printStackTrace();
//				lRspState = NetConnect.EXCEPTION;
                }




        if (mCancel) {

            if (mTask.mCallBack != null) {
                mTask.mCallBack.cancel(mTask.mTaskId);
            }

            mCallBack.NetThreadOver(mTask.mTaskId);
            return;
        }


        if (mCancel) {

            if (mTask.mCallBack != null) {
                mTask.mCallBack.cancel(mTask.mTaskId);
            }
            mCallBack.NetThreadOver(mTask.mTaskId);
            return;
        }

        if (mTask.mCallBack != null) {
            mTask.mCallBack.loadData(mTask.mTaskId, lRsp);
        }

        mCallBack.NetThreadOver(mTask.mTaskId);
    }

    /**
     * 解析网络数据
     *
     * @param data 网络返回的数据
     * @return
     */
    private FuResponse parserNetData(String data) {

        if (data == null) {
            return null;
        }

        FuResponse base = null;


        return base;

    }

    /**
     * 组织请求数据
     *
     * @param task 请求对象
     * @return
     */
    private HashMap<String, Object> getRequestData(MyTask task) {

        HashMap<String, Object> paramMap;

        if (task == null) {

            return null;

        } else {
            paramMap = new HashMap<String, Object>();
        }

        return paramMap;
    }


//    private List<NameValuePair> getRequestData(JkxRequsetBase data) {
//
//        List<NameValuePair> lJect = null;
//
//        if (data == null) {
//
//            return null;
//
//        } else {
//
//            lJect = new ArrayList<NameValuePair>();
//
//            switch (mTask.mTaskId) {
//            }
//        }
//    }



    private boolean mCancel;

    public void cancel() {

        mCancel = true;
    }

    /**
     * 对网络连接状态进行判
     *
     * @return true, 可用 false 不可用
     */
    private boolean isOpenNetwork() {

        ConnectivityManager connManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }

	/*----------------------------------解析-------------------------------------*/



}
