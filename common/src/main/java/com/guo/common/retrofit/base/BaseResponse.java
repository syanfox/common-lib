package com.guo.common.retrofit.base;

import java.io.Serializable;


public class BaseResponse<T> implements Serializable {

    //成功
    private static final int SUCCESS = 0;

    /**
     * <p>
     * 服务器响应码
     * </p>
     */
    private int code;
    /**
     * <p>
     * 服务器返回信息
     * </p>
     */
    private String message;

    /**
     * <p>
     * 服务器返回数据
     * </p>
     */
    private T result;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess(){

        if(code==SUCCESS){
            return true;
        }
        else{
            return false;
        }

    }
}
