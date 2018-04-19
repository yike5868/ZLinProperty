package com.zlin.property.net;


import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zlin.property.control.FuResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
//        OkHttpClient.Builder httpbuild = new OkHttpClient.Builder();
        client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request request = task.buildRequest();


        Response response = client.newCall(request).execute();
        FuResponse fuResponseBase = null;
        if (response.isSuccessful()) {
            data = response.body().string();

            Log.e("response", data);

            fuResponseBase = JSON.parseObject(data, FuResponse.class);


            return fuResponseBase;
        } else {
            return null;
//            throw new IOException("Unexpected code " + response);
        }
    }

}