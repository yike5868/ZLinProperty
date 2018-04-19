package com.zlin.property.control;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zlin.property.Constant;
import com.zlin.property.net.NetWorkManager;
import com.zlin.property.tools.encrypt.AES;
import com.zlin.property.tools.encrypt.AESKeyUtils;
import com.zlin.property.tools.encrypt.RSA;


import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import static okhttp3.RequestBody.create;

public class FuRequset {

	public final static int kNetWork_MethodType_POST = 0;
	public final static int kNetWork_MethodType_GET = 1;

	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
	public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
	public static final MediaType MEDIA_TYPE_FORM = MediaType.parse("multipart/form-data");
	public static final MediaType MEDIA_TYPE_FORM_URL = MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");


	private String baseURLString;
	private int methodType;
	private String methodName;
	private HashMap<String, Object> paramMap;
	private String aesKey;

	public FuRequset() {
		this.baseURLString = NetWorkManager.kNetWork_BASE_URL;
		this.methodType = kNetWork_MethodType_POST;
		this.paramMap = new HashMap<>();
	}

	private String stringOfAPI() {
		if (kNetWork_MethodType_POST == this.methodType) {
			return this.baseURLString + methodName;
		}
		return this.baseURLString;
	}

//	protected RequestBody buildFormBody() {
//		JSONObject jsonObject = new JSONObject(paramMap);
//		RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, jsonObject.toString());
//		return body;
//	}


	/**
	 * String转化为请求体
	 *
	 * @return
	 */
	protected String stringOfPostBody() {

		String jsonStr = JSON.toJSONString(paramMap);
		if(Constant.isEncrypt){
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
		}else{
			return jsonStr;
		}


		return null;
	}

	public void setValue(String key, Object val) {
		if (key == null || val == null || TextUtils.isEmpty(key)) {
			return;
		}
		this.paramMap.put(key, val);
	}

	//  生成请求用Request
	public Request buildRequest(){
		RequestBody body = create(HttpConstansType.MEDIA_TYPE_FORM_URL, ("c=" + stringOfPostBody()));
		Request request = new Request.Builder().url("http://10.100.101.108:8001/test/encrypt").post(body).build();
//        RequestBody body = this.buildFormBody();
//        Request request = new Request.Builder().url(stringOfAPI()).post(body).build();
		return request;
	}


	public String getBaseURLString() {
		return baseURLString;
	}

	public void setBaseURLString(String baseURLString) {
		this.baseURLString = baseURLString;
	}

	public int getMethodType() {
		return methodType;
	}

	public void setMethodType(int methodType) {
		this.methodType = methodType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public HashMap<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(HashMap<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
}
