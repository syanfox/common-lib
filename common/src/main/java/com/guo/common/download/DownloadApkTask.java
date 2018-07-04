package com.guo.common.download;

import android.content.Intent;
import android.os.Environment;


import com.guo.common.constant.Constants;
import com.guo.common.download.bean.ApkDownloadInfo;
import com.guo.common.download.progress.DownloadProgressHandler;
import com.guo.common.download.progress.ProgressHelper;
import com.guo.common.publicinterface.IProgressCallback;
import com.guo.common.retrofit.RetrofitUtils;
import com.guo.common.util.FileIOUtils;
import com.guo.common.util.FileUtils;
import com.guo.common.util.FormatUtil;
import com.guo.common.util.LogUtils;
import com.guo.common.util.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by guoq on 2018/1/5.
 * 下载apk任务
 */

public class DownloadApkTask implements Runnable {

    private final String TAG=DownloadApkTask.class.getName();

    private String mFilePath = "";
    private String mTotalSize = "0";
    private int mProgress = 0;

    private ApkDownloadInfo apkDownloadInfo;

    public DownloadApkTask(ApkDownloadInfo apkDownloadInfo){
       this.apkDownloadInfo=apkDownloadInfo;
    }


    @Override
    public void run() {

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        if(apkDownloadInfo.getApkType()==0) {
            //跟目录
            mFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + apkDownloadInfo.getApkFileName();
        }
        else{
            mFilePath=apkDownloadInfo.getPath()+ "/" + apkDownloadInfo.getApkFileName();
        }
        FileUtils.deleteFile(mFilePath);
        apkDownloadInfo.setApkUrl(mFilePath);
        downloadApk(apkDownloadInfo.getApkDownloadurl());
        LogUtils.d(TAG,"下载apk 名称:"+apkDownloadInfo.getApkName());
    }

    private void downloadApk(String url){

        Retrofit.Builder retrofitBuilder = RetrofitUtils.getRetrofit(url).newBuilder();
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        DownloadApi api = retrofitBuilder
                .client(builder.build())
                .build()
                .create(DownloadApi.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {

                double size=(double) contentLength / 1024 / 1024;
                mTotalSize = FormatUtil.formatNum(size);
                LogUtils.d("mTotalSize-----" + mTotalSize);
                int pro = 0;
                if(contentLength>0) {
                    pro =(int) Math.ceil(bytesRead * 100 / contentLength);
                }
                updateProgress(pro);
            }
        });
        Call<ResponseBody> responseBodyCall = api.download(url);
        try {
            final Response<ResponseBody> response=responseBodyCall.execute();
            if(response.isSuccessful()){
                final InputStream is = response.body().byteStream();
                File file = new File(mFilePath);
                BufferedInputStream bis = new BufferedInputStream(is);
                boolean isSuccessful = FileIOUtils.writeFileFromIS(file, bis, true, new IProgressCallback() {
                    @Override
                    public void onProgress(long bytesRead, boolean done) {
                        double size=(double) response.body().contentLength() / 1024 / 1024;
                        mTotalSize =  FormatUtil.formatNum(size);
                        int pro = (int) Math.ceil(bytesRead * 100 / response.body().contentLength());
                        LogUtils.d("-----" + pro);
                        updateProgress(pro);
                    }
                });

            }
            else{
                LogUtils.d(TAG, "-->下载失败"+response.errorBody().toString());
                downloadFail();
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(TAG, "-->下载失败"+e.toString());
            downloadFail();
        }

    }



    /**
     * 显示进度
     * @param progress
     */
    public void updateProgress(int progress) {

        apkDownloadInfo.setProgress(progress);

        String message="";
        if(apkDownloadInfo.getApkType()==0){
            if(progress!=100) {
                message = "正在下载"+apkDownloadInfo.getApkName()+"--" + apkDownloadInfo.getApkVersion();
            }
            else{
                apkDownloadInfo.setComplete(true);
                message = "下载完成,"+apkDownloadInfo.getApkName()+"--" + apkDownloadInfo.getApkVersion();
            }
        }
        else{
            if(progress!=100) {
                message = "正在下载"+apkDownloadInfo.getApkName()+"--" + apkDownloadInfo.getApkVersion();
            }
            else{
                apkDownloadInfo.setComplete(true);
                message = "下载完成,"+apkDownloadInfo.getApkName()+"--" + apkDownloadInfo.getApkVersion();
            }
        }
        message=message+"("+ mTotalSize + "M)";

        apkDownloadInfo.setApkSize(String.valueOf(mTotalSize));
        apkDownloadInfo.setMessage(message);
        sendBroadcast(apkDownloadInfo);
    }



    private void downloadFail() {
        LogUtils.d(TAG, "-->下载失败");
        String message="";
        message=apkDownloadInfo.getApkFileName()+"下载失败";
        message=message+"("+ mTotalSize + "M)";

        apkDownloadInfo.setMessage(message);
        sendBroadcast(apkDownloadInfo);
    }


    /**
     * 发送广播
     * @param apkDownloadInfo
     */
    private void sendBroadcast(ApkDownloadInfo apkDownloadInfo){
        Intent intent = new Intent(Constants.APKDOWNLOAD_ACTION);
        intent.putExtra(Constants.INTENT_PARAM_KEY.KEY_APKDOWNLOAD, apkDownloadInfo);
        Utils.getContext().sendBroadcast(intent);
    }
}
