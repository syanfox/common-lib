package com.guo.common.retrofit;



import com.guo.common.retrofit.base.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */
public abstract class RetrofitCallback<T extends BaseResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        //200是服务器有合理响应
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
