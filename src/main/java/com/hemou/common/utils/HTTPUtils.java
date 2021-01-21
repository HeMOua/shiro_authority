package com.hemou.common.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HTTPUtils {

    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    public static void out(ServletResponse servletResponse, Result result){
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try(PrintWriter writer = response.getWriter()) {
            writer.print(JSONObject.toJSONString(result));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtils.error(HTTPUtils.class, "JSON 转字符串错误！");
        }
    }
}
