package com.guo.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class UncaughtExpLog implements Thread.UncaughtExceptionHandler{


    public static final String TAG = "UncaughtExpLog";


    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static UncaughtExpLog INSTANCE = new UncaughtExpLog();
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    private boolean isSend;

    private static int count=0;

    /** 保证只有一个CrashHandler实例 */
    private UncaughtExpLog() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static UncaughtExpLog getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG,null,ex);
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /***
     *
     * time //时间
     * versionName //应用程序版本名称
     * versionCode //应用程序版本号
     * model //手机型号，api号，分辨率
     * throwable //异常信息
     *
     */

    public static String VERSIONNAME="versionName";
    public static String VERSIONCODE="versionCode";
    public static String MODEL="model";

    /**
     * 收集设备参数信息
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put(VERSIONNAME, versionName);
                infos.put(VERSIONCODE, versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String name=field.getName();
                if(name.equalsIgnoreCase("MODEL")){
                    infos.put(MODEL, field.get(null).toString());
                }
                Log.d(TAG, field.getName() + " : " + field.get(null));

            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();

        Log.e(TAG,"", ex);
        String time = TimeUtils.getNowString();

        sb.append("***"+time+"***\n");

        String appVersion="";
        String logsource="";
        String logcontent="";

        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if(key.equalsIgnoreCase(VERSIONCODE)){
                appVersion=value;
            }else if(key.equalsIgnoreCase(MODEL)){
                logsource=value;
            }

            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        logcontent=result;

        updateServer(appVersion,logsource,logcontent);

        String fileName=saveLocalUncaughtExp(sb);

        return fileName;
    }

    public String saveLocalUncaughtExp(StringBuffer sb){

        String version= AppUtils.getAppVersionName();

        int versize=version.length();
        String vend=version.substring(versize-1, versize);
        String fileName="";

        try {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            fileName = "/"+Utils.APP_PATH+"_uncaughtExp_"+TimeUtils.getNowString(format)+".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                File dirFile = new File(Utils.LOG_PATH);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }

                FileOutputStream fos = new FileOutputStream(Utils.LOG_PATH+fileName,true);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
//    	}

        return fileName;
    }

    /**
     * 上传所有异常到服务器
     * @param appVersion
     * @param logsource
     * @param logcontent
     */
    public void updateServer(String appVersion, String logsource, final String logcontent){
    }
}
