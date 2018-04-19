package com.zlin.property.db.po;

import com.alibaba.fastjson.JSON;

/**
 * Created by zhanglin03 on 2018/4/19.
 */

public class Dto extends Entry {
    /**
     * errorMessage :
     * hasErrors : false
     * success : true
     */
    private String errorMessage;
    private boolean hasErrors;
    private boolean success;

    public static <T> T fromJson(Class<T> c, String jsonStr) {
        return JSON.parseObject(jsonStr, c);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public boolean isSuccess() {
        return success;
    }
}
