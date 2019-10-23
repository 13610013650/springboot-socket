package com.stu.websocket.socketdemo.common;

import java.io.Serializable;

/**
 * @program: socket-demo
 * @description: api调用返回结果封装
 * @author: Mr.Zhang
 * @create: 2019-10-23 23:37
 **/
public class ApiReturnResult<T> implements Serializable{

    private  String msg;

    private T data;

    public ApiReturnResult(){

    }

    public ApiReturnResult(T data){

    }

    public ApiReturnResult(String msg){

    }

    public static ApiReturnResult success(Object data){
        return  new ApiReturnResult(data);
    }

    public static ApiReturnResult error(String msg){
        return new ApiReturnResult(msg);
    }

    public  T getMsg(){
        return this.data;
    }

    public  ApiReturnResult setMsg(String msg){
        this.msg = msg;
        return this;
    }

    public T getData(){
        return this.data;
    }

    public  ApiReturnResult setData(T data){
        this.data = data;
        return this;
    }
}
