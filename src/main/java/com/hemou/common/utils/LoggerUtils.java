package com.hemou.common.utils;


import org.apache.log4j.Logger;

public class LoggerUtils {

    public static void debug(Class<? extends Object> clazz, String format, Object... value){
        Logger logger = Logger.getLogger(clazz);
        logger.debug(String.format(format, value));
    }

    public static void debug(Class<? extends Object> clazz, String format){
        Logger logger = Logger.getLogger(clazz);
        logger.debug(format);
    }

    public static void info(Class<? extends Object> clazz, String format, Object... value){
        Logger logger = Logger.getLogger(clazz);
        logger.info(String.format(format, value));
    }

    public static void info(Class<? extends Object> clazz, String format){
        Logger logger = Logger.getLogger(clazz);
        logger.info(format);
    }

    public static void error(Class<? extends Object> clazz, String format, Object... value){
        Logger logger = Logger.getLogger(clazz);
        logger.error(String.format(format, value));
    }


}
