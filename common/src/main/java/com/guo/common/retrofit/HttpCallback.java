package com.guo.common.retrofit;

/**
 */

public interface HttpCallback<T> {

    public abstract void onSuccess(T response);

    public abstract void onFail(String message);


    public abstract void onFinish();
}
