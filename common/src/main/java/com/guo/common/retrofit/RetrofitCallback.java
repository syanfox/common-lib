package com.guo.common.retrofit;



import com.google.gson.Gson;
import com.guo.common.retrofit.base.BaseResponse;
import com.guo.common.util.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */
public abstract class RetrofitCallback<T extends BaseResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        LogUtils.json("RetrofitCallback-->请求路径", new Gson().toJson(response.raw().request().url().url().toString()));
        //LogUtils.json("RetrofitCallback-->请求参数", new Gson().toJson(response.raw().request().body()));
        LogUtils.json("RetrofitCallback-->返回数据", new Gson().toJson(response.body()));

        if (response.raw().code() == 200) {
            if (response.body().isSuccess()) {
                onSuccess(response.body());
            } else {
                onFail(response.body().getMessage());
            }

        } else {//失败响应
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
        }

        onFinish();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String message = RetrofitUtils.convertRetrofitExceptionMessage(t);
        onFail(message);
        onFinish();
    }

    public abstract void onSuccess(T response);

    public abstract void onFail(String message);

    public abstract void onFinish();
}
