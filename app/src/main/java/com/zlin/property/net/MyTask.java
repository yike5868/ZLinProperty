// Copyright (C) 2012-2013 UUZZ All rights reserved
package com.zlin.property.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zlin.property.Constant;
import com.zlin.property.control.HttpConstansType;
import com.zlin.property.db.po.Entry;
import com.zlin.property.tools.encrypt.AES;
import com.zlin.property.tools.encrypt.AESKeyUtils;
import com.zlin.property.tools.encrypt.RSA;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static okhttp3.RequestBody.create;

public class MyTask {

    public static final int UP_LOAD_FILE = 157;//上传图片
    public static final int UP_LOAD_PERSONINFO_FILE = 158;//上传人员图片
    public static final int DOWNLOAD_FILE = 100;

    public static final int ERROR = 2;

    public static final int LOGIN = 1;//登录

    public static final int BANNER = 3;//获取banner

    public static final int SAVE_REPAIR = 4;//保存服务

    public static final int REGISTER = 5;//注册

    public static final int FIND_ROOM = 6;//
    public static final int GET_REPAIR = 7;//获取全部列表
    public static final int GET_FEE = 8;//获取支付类型
    public static final int GET_VERSION = 9;//获取版本信息


    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_PUT = "PUT";

    public int mTaskId; // 任务标志位

    public String mUrl; // 网络请求的地址

    public boolean mIsEncryption; // 是否加密解密

    public NetCallBack mCallBack; // 回调接口

    public Entry mRequestData; // 请求数据的对象

    public String method = METHOD_POST;//请求方式

    public File[] files;//需要上传的文件

    //	public List<Object> mRequestList;//请求数据的列表
    public Map<String, Object> paramMap; //请求数据的列表

    public HashMap<String, String> mRequestHeader; // 请求数据的请求头

//	public boolean post_get = true;//post true  get false

    public Handler handler;//uihandler

    private String aesKey;

    protected String stringOfPostBody(Object object) {

        String jsonStr = JSON.toJSONString(object);
        jsonStr = jsonStr.replaceAll("chdSymptoms2", "chdSymptoms");
        jsonStr = jsonStr.replaceAll("manageGroup2", "manageGroup");
        if (Constant.isEncrypt) {
            //AES加密
            aesKey = AESKeyUtils.getKey();
            byte[] data = jsonStr.getBytes();
            String encrypt_data = AES.encryptToBase64(jsonStr, aesKey);
            //RSA加密
            try {
                String encrypt_key = RSA.encrypt(aesKey, Constant.PUBLIC_KEY);
                HashMap<String, String> strHashMap = new HashMap<>();
                strHashMap.put("data", encrypt_data);
                strHashMap.put("encryptKey", encrypt_key);
                String json = JSON.toJSONString(strHashMap);
                return URLEncoder.encode(json, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("WTRequest", "Something is wrong with RSA");
            }
        } else {
            Log.e("aaaaaaa",jsonStr);
            return jsonStr;
        }


        return null;
    }


    public Request getFileRequest() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (paramMap != null)
            for (String key : paramMap.keySet()) {
                builder.addFormDataPart(key, paramMap.get(key).toString());
            }
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"" + files[i].getName() + "\""),
                        RequestBody.create(MediaType.parse("image/png"), files[i]));
            }
        builder.addFormDataPart("androidFileRecord", stringOfPostBody(mRequestData));

        RequestBody body = builder.build();

        return new Request.Builder().url(mUrl).addHeader("Content-Type", "application/json").addHeader("charset", "UTF-8").post(body).build();

    }


    public Request buildRequest() {

        if (mTaskId == UP_LOAD_FILE || mTaskId == UP_LOAD_PERSONINFO_FILE) {
            return getFileRequest();
        }

        RequestBody body = create(HttpConstansType.MEDIA_TYPE_JSON, stringOfPostBody(mRequestData));

        if (paramMap != null) {

            body = create(HttpConstansType.MEDIA_TYPE_JSON, stringOfPostBody(paramMap));
        }

        Request request;
        if (method == METHOD_GET)
            request = new Request.Builder().url(mUrl)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("charset", "UTF-8").method(method, null).build();
        else
            request = new Request.Builder().url(mUrl)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("charset", "UTF-8").method(method, body).build();

        return request;
    }

    public String getAesKey() {
        return aesKey;
    }

}
