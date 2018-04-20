package com.zlin.property.tools;

/**
 * Created by zhanglin03 on 2018/4/20.
 */

public class StringUtil {
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }else
            return false;
    }
}
