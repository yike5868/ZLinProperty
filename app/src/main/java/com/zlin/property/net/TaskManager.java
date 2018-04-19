// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.zlin.property.net;


import android.content.Context;

import com.zlin.property.db.po.DownLoadFile;

/**
 *
 */
public class TaskManager {

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
}
