package com.smbms.utils;


import com.smbms.exceptions.ParamsException;

/**
 * 判断参数
 */
public class AssertUtil {

    /**
     *  判断参数是否符合逻辑，不符合返回参数异常
     * @param flag
     * @param msg
     */
    public  static void isTrue(Boolean flag,String msg){
        if(flag){
            throw  new ParamsException(msg);
        }
    }
}
