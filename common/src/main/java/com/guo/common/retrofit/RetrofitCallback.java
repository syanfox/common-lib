package com.guo.common.retrofit;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */
public abstract class RetrofitCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onFail(response.body());
        }

      /*  if (response.raw().code() == 200) {

        } else {//失败响应
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
        }*/

        onFinish();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String message = RetrofitUtils.convertRetrofitExceptionMessage(t);
        onError(message);
        onFinish();
    }

    public abstract void onSuccess(T response);

    public abstract void onFail(T failResponse);

    public abstract void onError(String errorMessage);

    public abstract void onFinish();
}
