package com.zlin.property.control;

import okhttp3.MediaType;

/**
 * @Author Archy Wang
 * @Date 16/6/15
 * @Description
 */
public class HttpConstansType {
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
    public static final MediaType MEDIA_TYPE_FORM = MediaType.parse("multipart/form-data");
    public static final MediaType MEDIA_TYPE_FORM_URL = MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");
    public static final MediaType MEDIA_TYPE_MARK_DOWN = MediaType.parse("text/x-markdown; charset=utf-8");
}
