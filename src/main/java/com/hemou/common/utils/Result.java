package com.hemou.common.utils;

import com.hemou.common.constant.HTTPConstant;

public class Result {

    private int status;
    private Object obj;
    private String msg;


    public Result(int status, String msg, Object obj) {
        this.msg = msg;
        this.status = status;
        this.obj = obj;
    }

    public static Result success(String message, Object obj){
        return new Result(HTTPConstant.REQUEST_SUCCESS, message, obj);
    }

    public static Result success(String message){
        return success(message, null);
    }

    public static Result warning(String msg){
        return new Result(HTTPConstant.REQUEST_WARNING, msg, null);
    }

    public static Result warning(){
        return warning(null);
    }

    public static Result error(String msg){
        return new Result(HTTPConstant.REQUEST_ERROR, msg, null);
    }

    public static Result error(){
        return error(null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
