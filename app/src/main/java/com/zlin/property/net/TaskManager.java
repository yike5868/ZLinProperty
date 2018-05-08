// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.zlin.property.net;


import android.content.Context;

import com.zlin.property.db.po.Banner;
import com.zlin.property.db.po.DownLoadFile;
import com.zlin.property.db.po.Repair;
import com.zlin.property.db.po.RoomItem;
import com.zlin.property.db.po.UserInfo;
import com.zlin.property.tools.AppConfig;

import java.io.File;

/**
 *
 */
public class TaskManager {


    public final static String LOGIN = "/user/login";
    public final static String REGISTER = "/user/register";
    public final static String FINDBANNER = "/service/findBannerByVersion";
    public final static String SAVE_REPAIR = "/service/saveRepair";
    public final static String UPLOAD_FILE = "/file/upload";
    public final static String GETREPAIRS = "/service/getRepairs";
    public final static String FINROOM = "/user/findRoom";

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
        myTask.mUrl = AppConfig.HTTP+LOGIN;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = userInfo;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.LOGIN;
        return myTask;
    }

    public MyTask register(NetCallBack mCallBack, UserInfo userInfo){
        MyTask myTask = new MyTask();
        myTask.mUrl = AppConfig.HTTP+REGISTER;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = userInfo;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.REGISTER;
        return myTask;
    }
    public MyTask findBannerByVersion(NetCallBack mCallBack, Banner banner){
        MyTask myTask = new MyTask();
        myTask.mUrl = AppConfig.HTTP+FINDBANNER;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = banner;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.BANNER;
        return myTask;
    }
    public MyTask findRoom(NetCallBack mCallBack, RoomItem roomItem){
        MyTask myTask = new MyTask();
        myTask.mUrl = AppConfig.HTTP+FINROOM;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = roomItem;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.FIND_ROOM;
        return myTask;
    }
    public MyTask saveRepair(NetCallBack mCallBack, Repair repair){
        MyTask myTask = new MyTask();
        myTask.mUrl = AppConfig.HTTP+SAVE_REPAIR;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = repair;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.SAVE_REPAIR;
        return myTask;
    }

    public MyTask upLoadFile(NetCallBack mCallBack, File[] files){
        MyTask myTask = new MyTask();
        myTask.mUrl = AppConfig.HTTP+UPLOAD_FILE;
        myTask.mCallBack = mCallBack;
        myTask.files = files;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.UP_LOAD_FILE;
        return myTask;
    }

    public MyTask getRepairs(NetCallBack mCallBack, Repair repair){
        MyTask myTask = new MyTask();
        myTask.mUrl = AppConfig.HTTP+GETREPAIRS;
        myTask.mCallBack = mCallBack;
        myTask.mRequestData = repair;
        myTask.mIsEncryption = false;
        myTask.mTaskId = MyTask.BANNER;
        return myTask;
    }

}
