package com.zlin.property.net;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zlin.property.control.AppRuntimeException;
import com.zlin.property.control.FuRequset;
import com.zlin.property.control.FuResponse;
import com.zlin.property.tools.encrypt.AES;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by weitong on 16/7/4.
 */
public class NetWorkManager {

    public static final String kNetWork_Code_Success = "0";
    public static final String kNetWork_Code_Fail = "2";
    public final static String kNetWork_BASE_URL = "http://www.baidu.com";
    public final static String kNetWork_Tag = "NetWorkManager";

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    /**
     * 请求成功回调
     */
    public interface ISuccessResponse {
        void onSuccess(FuResponse fuResponse);
    }

    /**
     * 请求失败回调
     */
    public interface IErrorResponse {
        void onError(FuResponse fuResponse, String errorMessage);
    }

    /**
     * 请求结束回调
     */
    public interface IFinishedResponse {
        void onFinished(FuResponse fuResponse, String message);
    }

    public void request(final FuRequset fuRequest, final ISuccessResponse successResponse,
                        final IErrorResponse errorResponse, final IFinishedResponse finishedResponse) {

        final Request request = fuRequest.buildRequest();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException error) {
                Log.d(kNetWork_Tag, JSONObject.toJSON(error).toString());
                FuResponse response = error("发生错误");
                if (errorResponse != null) {
                    errorResponse.onError(response, response.getErrMessage());
                }
                if (finishedResponse != null) {
                    finishedResponse.onFinished(response, response.getErrMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String string = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(string);
                FuResponse fuResponse = null;
                try {
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    String data = jsonObject.getString("data");
                    String s = AES.decryptFromBase64(data,fuRequest.getAesKey());
                    if (!TextUtils.equals(code, kNetWork_Code_Success)) {
                        throw new AppRuntimeException(msg, code);
                    }

                    String requestClassName = fuRequest.getClass().getName();
                    String responseClassName = requestClassName.replaceFirst("Request", "Response");
                    Class responseClass = Class.forName(responseClassName);
                    fuResponse = (FuResponse) JSON.parseObject(jsonObject.toJSONString(), responseClass);

                    if (successResponse != null) {
                        successResponse.onSuccess(fuResponse);
                    }
                } catch (Exception ex) {
                    if (ex instanceof AppRuntimeException) {
                        fuResponse = error(((AppRuntimeException) ex).getMsg(), ((AppRuntimeException) ex).getCode());
                    } else {
                        fuResponse = error("发生错误");
                    }
                    if (errorResponse != null) {
                        errorResponse.onError(fuResponse, fuResponse.getErrMessage());
                    }
                } finally {
                    Log.d(kNetWork_Tag, JSONObject.toJSON(fuResponse).toString());
                    if (finishedResponse != null) {
                        finishedResponse.onFinished(fuResponse, fuResponse.getErrMessage());
                    }
                }
            }
        });


    }


    public FuResponse error(String msg) {
        return new FuResponse(msg, kNetWork_Code_Fail);
    }

    public FuResponse error(String msg, String code) {
        return new FuResponse(msg, code);
    }


}
