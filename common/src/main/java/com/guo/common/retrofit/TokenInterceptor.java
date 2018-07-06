package com.guo.common.retrofit;


import com.guo.common.constant.Constants;
import com.guo.common.util.SPUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;



/**
 * Created by guoq on 2017/8/1.
 * Token拦截器
 */

public class TokenInterceptor implements Interceptor {


    private final String TAG = TokenInterceptor.class.getName();

    private static final Charset UTF8 = Charset.forName("UTF-8");


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);

        String token= SPUtils.getInstance().getString(Constants.SP_KEY.KEY_TOKEN);

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = request.url()
                .newBuilder()
                .scheme(request.url().scheme())
                .host(request.url().host());
                //.setQueryParameter("accessToken", accessToken)
                //.setQueryParameter("devid", devid)
                //.setQueryParameter("sessionId", sessionId);

        Request compressedRequest = request.newBuilder()
                .header("Content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Authorization","Bearer "+token)
                .method(request.method(), request.body())
                .url(authorizedUrlBuilder.build())
                .build();

        response.body().close();
        response = chain.proceed(compressedRequest);


        return response;
    }




}
