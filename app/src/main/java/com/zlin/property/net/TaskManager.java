// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.zlin.property.net;


import android.content.Context;

import com.zlin.property.db.po.Banner;
import com.zlin.property.db.po.DownLoadFile;
import com.zlin.property.db.po.UserInfo;

/**
 *
 */
public class TaskManager {

    public final static String HTTP="http://192.168.159.69:8088";
    public final static String LOGIN = "/user/login";
    public final static String FINDBANNER = "/service/findBannerByVersion";

    private static TaskManager mTaskManager;
    public static TaskManager getInstace(Context context) {

        if (mTaskManager == null) {
            mTaskManager = new TaskManager();
        }


        return mTaskManager;
    }

    public static TaskManager getInstace() {

        if (mTaskManager == null) {
            mTaskManager = new TaskManager();
        }

        return mTaskManager;
    }
    public MyTask getDownLoadApp(NetCallBack mCallBack, String url,
                                 DownLoadFile file) {
        FuDownLoadFileTask task = new FuDownLoadFileTask();
        task.mUrl = url;
        task.mCallBack = mCallBack;
        task.mIsEncryption = false;
        task.mTaskId = MyTask.DOWNLOAD_FILE;
        task.mLoadFile = file;
        return task;

    }

    public MyTask login(NetCallBack mCallBack, UserInfo userInfo){
        MyTask myTask = new MyTask();
        myTask.mUrl = HTTP+LOGIN;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = userInfo;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.LOGIN;
        return myTask;
    }
    public MyTask findBannerByVersion(NetCallBack mCallBack, Banner banner){
        MyTask myTask = new MyTask();
        myTask.mUrl = HTTP+FINDBANNER;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = banner;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.BANNER;
        return myTask;
    }
}
