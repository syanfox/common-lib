package com.guo.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 * @author Administrator
 */
public final class Utils {


    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static String LOG_PATH= "";
    public static String APP_PATH="";

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    public static void inti(@NonNull final Context context, String log_path){
        Utils.context = context.getApplicationContext();
        LOG_PATH= FileUtils.getSdcardPath(context)+"/"+log_path;
        APP_PATH=log_path;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}