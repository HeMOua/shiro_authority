package com.hemou.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

public class UserUtils {

    public static String md5Password(String credentials){
        String hashAlgorithmName = "MD5";
        int hashIterations = 1024;
        return String.valueOf(new SimpleHash(hashAlgorithmName, credentials, null, hashIterations));
    }

}
