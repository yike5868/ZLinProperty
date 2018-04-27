package com.zlin.property.control;


import android.content.Context;

import com.zlin.property.function.FuChoseRoomView;
import com.zlin.property.function.FuContentView;
import com.zlin.property.function.FuMainView;
import com.zlin.property.function.FuServerListView;
import com.zlin.property.function.FuServerView;
import com.zlin.property.function.FuWelcomeView;

public class FuUiFrameManager {


    public static final int FU_MAIN_ID = 999; // MainActivity 视图ID
    public static final int FU_CONTENT_ID = 998; // ContentActivity 视图ID

    public static final int FU_WELCOME = 1; // 欢迎界面
    public static final int FU_MAIN_HOME = 2; // 首页
    public static final int FU_LOGIN = 3; // 登录
    public static final int FU_CONTENT = 4;//主页列表
    public static final int FU_SERVER = 5;//报修服务
    public static final int FU_SERVER_LIST = 6;//报修服务列表
    public static final int FU_CHOSE_ROOM = 7;//选择房间
    public FuUiFrameModel createFuModel(int type, Context contex,
                                        FuEventCallBack callBack) {
        switch (type) {

            case FU_WELCOME:
                return new FuWelcomeView(contex, callBack);
            case FU_CONTENT:
                return new FuContentView(contex,callBack);
            case FU_MAIN_HOME:
                return new FuMainView(contex,callBack);
            case FU_SERVER:
                return new FuServerView(contex,callBack);
            case FU_SERVER_LIST:
                return new FuServerListView(contex,callBack);
            case FU_CHOSE_ROOM:
                return new FuChoseRoomView(contex,callBack);
        }
        return null;
    }
}
