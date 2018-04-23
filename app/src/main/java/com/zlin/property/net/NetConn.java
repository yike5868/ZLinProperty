package com.zlin.property.net;


import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zlin.property.control.FuResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhanglin on 16/8/1.
 */
public class NetConn {

    public static final int SUCCESSFUL = 100;
    public static final int EXCEPTION = 208; // 未知的异常


    Context mContext;
    String data;

    NetConn(Context mContext) {
        this.mContext = mContext;
    }

    OkHttpClient client;

    public FuResponse httpPost(MyTask task) throws IOException, ClassNotFoundException {
        getClient();
        Request request = task.buildRequest();
        Response response = null;
        FuResponse fuResponseBase = null;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            fuResponseBase = new FuResponse();
            fuResponseBase.setSuccess(false);
            fuResponseBase.setHasSuccess(true);
            fuResponseBase.setErrMessage("服务器连接错误！请检查网络！");
        }
        if (response.isSuccessful()) {
            data = response.body().string();
            Log.e("response", data);
            fuResponseBase = JSON.parseObject(data, FuResponse.class);
            return fuResponseBase;
        } else {
            fuResponseBase = new FuResponse();
            fuResponseBase.setSuccess(false);
            fuResponseBase.setHasSuccess(true);
            fuResponseBase.setErrMessage("服务器连接错误！请检查网络！");
            return fuResponseBase;
        }
    }

    public OkHttpClient getClient(){
        if(client == null){
            client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS).cookieJar(new CookiesManager())
                    .build();
        }
        return client;
    }

}