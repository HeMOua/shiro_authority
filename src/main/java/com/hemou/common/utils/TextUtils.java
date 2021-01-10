package com.hemou.common.utils;

import org.springframework.util.StringUtils;

public class TextUtils {

    public static boolean isExistEmpty(String... value){
        boolean flag = false;
        for(String v : value){
            if(StringUtils.isEmpty(v)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
