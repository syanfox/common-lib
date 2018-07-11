package com.guo.common.retrofit;


import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.guo.common.util.LogUtils;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * http 请求
 */
public class RetrofitUtils {


    private static String TAG=RetrofitUtils.class.getSimpleName();

    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIMEOUT = 10;
    /**
     * 读超时时间
     */
    public static final int READ_TIMEOUT = 15;
    /**
     * 写数据超时时间
     */
    public static final int WRITE_TIMEOUT = 15;
    /**
     * 是否显示请求日志
     */
    public static final boolean SHOW_LOG = true;


    private static String baseUrl;

    private static RetrofitUtils retrofitUtils;
    private static Retrofit retrofit;
    public RetrofitUtils() {
    }

    public static RetrofitUtils getInstance(){

        if(retrofitUtils == null){
            synchronized (RetrofitUtils.class){
                if(retrofitUtils == null){
                    retrofitUtils = new RetrofitUtils();

                }
            }

        }
        return retrofitUtils;
    }


    public static synchronized Retrofit getRetrofit(){
        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.i(TAG, message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new TokenInterceptor())
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .build();


                retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(httpClient).addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        }

        return retrofit;
    }

    public <T>T getApiService(Class<T> cl){
        if(TextUtils.isEmpty(baseUrl)){
            throw  new RuntimeException("baseUrl uninitialized ");

        }
        Retrofit retrofit = getRetrofit();
        return retrofit.create(cl);
    }


    /**
     * 设置全局url
     * @param globalBaseUrl
     */
    public void setGlobalBaseUrl(String globalBaseUrl){
        baseUrl=globalBaseUrl;
        RetrofitUrlManager.getInstance().setGlobalDomain(globalBaseUrl);
    }


    public void setServerUrl(String domainName,String url){
        RetrofitUrlManager.getInstance().putDomain(domainName, url);
    }


    /**
     * 异常详情
     *
     * @param t 异常
     * @return 异常详情
     */
    public static String convertRetrofitExceptionMessage(Throwable t) {
        String message;
        if (t instanceof ConnectException) {
            message = "连接失败，请检查您的网络是否良好！";
        } else if (t instanceof ConnectTimeoutException) {
            message = "连接超时，请检查您的网络是否良好！";
        } else if (t instanceof UnknownHostException) {
            message = "连接服务器失败，请检查您的服务器或网络设置！";
        } else if (t instanceof SocketTimeoutException) {
            message = "连接到服务器超时，请检查您的服务器或网络设置！";
        } else if (t instanceof NumberFormatException) {
            message = "数据格式解析异常！";
        }else if(t instanceof HttpRetryException){
            message="服务器拒绝请求";
        }
        else if (t instanceof JsonSyntaxException) {
            message = "数据解析异常！";
        } else if (t instanceof SocketException) {
            message = "连接服务器失败！";
        } else {
            message = t.getMessage();
        }
        return message;
    }
}
